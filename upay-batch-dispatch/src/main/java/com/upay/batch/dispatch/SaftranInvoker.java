package com.upay.batch.dispatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.pactera.dipper.batch.scheduler.JobDataMapKey;
import com.pactera.dipper.batch.scheduler.QuartzJob;
import com.pactera.dipper.context.DipperSysInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import com.pactera.dipper.core.utils.Constants.Transaction;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.dao.service.IDipperSequenceService;
import com.pactera.dipper.po.Order;
import com.pactera.dipper.po.gnr.GnrSafInfoPo;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.AtomStat;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.FlowStat;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.SafStat;
import com.pactera.dipper.transaction.TransactionDefinition;
import com.pactera.dipper.transaction.util.TransactionResourceHolder;
import com.pactera.dipper.utils.codec.KryoUtil;


/**
 * 调起冲正处理
 * 
 * 
 */
public class SaftranInvoker extends QuartzJob implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(SaftranInvoker.class);

    private static DipperHandlerContainer dipperHandlerContainer;
    private static IDipperSequenceService sequenceService;
    private static ApplicationContext applicationContext;

    private final int limitCount = 3;// 一条记录最大的处理失败次数
    private final int queryCount = 5;

    // 一个接口服务里的所有需要冲正的服务，需要按顺序并且作为统一的事务来处理
    private boolean dependencyTransation = false;


    @Override
    protected void process(JobDataMap jobDataMap) {
        IDaoService daoService = (IDaoService) jobDataMap.get(JobDataMapKey.DAO_SERVICE);

        GnrSafInfoPo queryCondition = new GnrSafInfoPo();

        // 接口服务失败，原子服务成功
        queryCondition.setFlowStat(FlowStat.FAULT);
        queryCondition.setAtomStat(AtomStat.SUCESS);
        queryCondition.setSafStat(SafStat.NOT_EXECUTE);
        queryCondition.setSafCount(limitCount); // saf_count < limitCount

        queryCondition.addOrder(Order.asc("sysSeq"));
        queryCondition.addOrder(Order.asc("atomIdx"));
        queryCondition.addOrder(Order.asc("safCount"));

        List<GnrSafInfoPo> safList = daoService.selectList(queryCondition, 0, queryCount);

        // 核心超时（接口服务失败，原子服务失败，核心超时）
        queryCondition.setFlowStat(FlowStat.FAULT);
        queryCondition.setAtomStat(AtomStat.FAULT);
        queryCondition.setCoreStat(CoreStat.TIME_OUT);
        List<GnrSafInfoPo> timeoutSafList = daoService.selectList(queryCondition, 0, queryCount);

        // 将查询结果合并为一个集合
        if (CollectionUtils.isNotEmpty(timeoutSafList)) {
            safList.addAll(timeoutSafList);
        }

        if (safList != null && safList.size() > 0) {
            if (dependencyTransation) {
                // 存在事务依赖, 即一个接口服务里的所有需要冲正的服务按顺序并且作为统一的事务来处理
                Set<String> sysSeqSet = new HashSet<String>();
                HashMap<String, Integer> countMap = new HashMap<String, Integer>();
                for (GnrSafInfoPo po : safList) {
                    sysSeqSet.add(po.getSysSeq());// hash的值能保证不会重复
                    countMap.put(po.getSysSeq(), po.getSafCount());
                }
                Iterator<String> iter = sysSeqSet.iterator();
                while (iter.hasNext()) {
                    String sysSeq = iter.next();
                    new SaftranExecutorTransaction(daoService, sysSeq, countMap.get(sysSeq)).start();
                }
            } else {
                // 互不依赖，每一条t_gnr_saf_info记录并发处理
                for (GnrSafInfoPo po : safList) {
                    new SaftranExecutor(daoService, po, null).start();
                }
            }
        }

        // ################################################################
        // 调起查证服务
        // 统一调起的查证服务处理<br/>
        // 在gateway doErrorHandler里加上
        // target.put(FlowStatus.CONFIRMATION_STATUS,Boolean.TRUE)
        // 并且saf-ref配置为对应的查证服务;
        GnrSafInfoPo confirmationSaf = new GnrSafInfoPo();
        confirmationSaf.setFlowStat(FlowStat.UNKNOW);
        confirmationSaf.setAtomStat(FlowStat.UNKNOW);// AtomStat.UNKNOW
        confirmationSaf.setSafStat(SafStat.NOT_EXECUTE);
        confirmationSaf.setSafCount(limitCount); // saf_count < limitCount

        List<GnrSafInfoPo> confirmationList = daoService.selectList(confirmationSaf, 0, queryCount);

        if (confirmationList != null && confirmationList.size() > 0) {
            for (GnrSafInfoPo confirm : confirmationList) {
                new SaftranExecutor(daoService, confirm, null).start();
            }
        }
    }

    /** 将所有冲正服务作为一个事务统一调用 */
    private class SaftranExecutorTransaction extends Thread {
        private IDaoService daoService;
        private String sysSeq;
        private int safCount;


        SaftranExecutorTransaction(IDaoService daoService, String sysSeq, int safCount) {
            this.daoService = daoService;
            this.sysSeq = sysSeq;
            this.safCount = safCount;
            MDC.put(com.pactera.dipper.core.utils.Constants.MDC.ID, sysSeq + "_" + safCount);
        }


        @Override
        public void run() {

            GnrSafInfoPo querySafCondition = new GnrSafInfoPo();

            // 接口服务失败
            querySafCondition.setFlowStat(FlowStat.FAULT);
            querySafCondition.setSysSeq(sysSeq);
            querySafCondition.setSafStat(SafStat.NOT_EXECUTE);
            querySafCondition.setSafCount(limitCount);
            querySafCondition.addOrder(Order.asc("atomIdx"));

            List<GnrSafInfoPo> safList = daoService.selectList(querySafCondition);

            if (safList != null && safList.size() > 0) {

                GnrSafInfoPo updateSet = new GnrSafInfoPo();
                GnrSafInfoPo updateWhere = new GnrSafInfoPo();
                int currentSafCount = safCount + 1;

                updateSet.setSafStat(SafStat.EXECUTING);
                String safSysSeq = safList.get(0).getSafSysSeq();
                if (safSysSeq == null) {
                    safSysSeq = sequenceService.generateSysSeq();
                    updateSet.setSafSysSeq(safSysSeq);
                } else {
                    updateWhere.setSafSysSeq(safSysSeq);
                }
                updateSet.setSafCount(currentSafCount);
                updateSet.setSafTime(DipperSysInfo.getSysTime());

                updateWhere.setFlowStat(FlowStat.FAULT);
                updateWhere.setSysSeq(sysSeq);
                updateWhere.setSafStat(SafStat.NOT_EXECUTE);
                updateWhere.setSafCount(safCount);

                int rowAffected = daoService.update(updateSet, updateWhere);

                if (rowAffected == safList.size()) {
                    boolean dealResult = false;// 处理结果
                    TransactionDefinition tx = daoService.beginTransaction();
                    try {
                        for (GnrSafInfoPo po : safList) {
                            if ((AtomStat.SUCESS.equals(po.getAtomStat()))
                                    || (AtomStat.FAULT.equals(po.getAtomStat())
                                            && CoreStat.TIME_OUT.equals(po.getCoreStat()))) {
                                po.setSafSysSeq(safSysSeq);
                                po.setSafCount(currentSafCount);
                                po.setSafTime(updateSet.getSafTime());
                                new SaftranExecutor(daoService, po, tx).run();// 必须是run方法，不能异步调起
                            } else {
                                logger.info("[{}]不需要冲正,原流水号[{}],冲正流水号[{}]", po.getId(), sysSeq, safSysSeq);
                                GnrSafInfoPo fault = new GnrSafInfoPo();
                                fault.setId(po.getId());
                                fault.setSafStat(SafStat.NOT_EXECUTE);
                                fault.setSafCount(0);
                                int updateRowAffected = daoService.update(fault);
                                if (updateRowAffected != 1) {
                                    logger.warn("未知错误! 不需要冲正{}", po.getId());
                                }
                            }
                        }
                        TransactionResourceHolder.bound(tx);
                        daoService.commitTransaction(tx);
                        dealResult = true;
                    } catch (Exception e) {
                        logger.warn("统一事务冲正处理失败, 原流水号[{}],冲正流水号[{}]", sysSeq, safSysSeq, e);
                        TransactionResourceHolder.bound(tx);
                        try {
                            daoService.rollbackTransaction(tx);
                        } catch (Exception e1) {
                            logger.warn("回滚事务失败,事务序列号:" + tx.getSerialId(), e1);
                        }
                    } finally {
                        TransactionResourceHolder.unbound();

                        if (!dealResult) {
                            try {
                                // 处理失败, 更新冲正流水状态为失败
                                // （对于处理成功的，已经在SaftranExecutor单独更新为成功）
                                GnrSafInfoPo faultSet = new GnrSafInfoPo();
                                GnrSafInfoPo faultWhere = new GnrSafInfoPo();
                                faultSet.setSafStat(
                                    currentSafCount >= limitCount ? SafStat.FAULT : SafStat.NOT_EXECUTE);
                                faultWhere.setSafSysSeq(safSysSeq);
                                faultWhere.setSysSeq(sysSeq);
                                faultWhere.setSafStat(SafStat.EXECUTING);
                                faultWhere.setSafCount(currentSafCount);
                                int row = daoService.update(faultSet, faultWhere);
                                logger.info("冲正流水号[{}] , 更新记录数[{}], 原处理记录数[{}] ", safSysSeq, row,
                                    rowAffected);
                            } catch (Exception e2) {
                                logger.error("更新状态失败, 原流水[{}] , 冲正流水[{}]", sysSeq, safSysSeq, e2);
                            }
                        }
                    }

                    logger.info("冲正原流水[{}], 冲正流水[{}]处理完成, 处理结果[{}]", sysSeq, safSysSeq, dealResult);
                } else {

                    logger.warn("不作处理 ：[{}] , rowAffected=[{}] , safList.size = [{}]", sysSeq, rowAffected,
                        safList.size());
                }

            }

        }
    }

    /** 单条t_gnr_saf_info记录的处理 */
    private class SaftranExecutor extends Thread {
        private final IDaoService daoService;
        private final GnrSafInfoPo saf;
        private final TransactionDefinition transaction;

        /** 是否有事务依赖 */
        private final boolean transactionDependency;


        SaftranExecutor(IDaoService daoService, GnrSafInfoPo saf, TransactionDefinition transaction) {
            this.daoService = daoService;
            this.saf = saf;
            if (transaction != null
                    && transaction.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRED) {
                this.transaction = transaction;// 可以为空，表示无事务依赖
                transactionDependency = true;
            } else {
                transactionDependency = false;
                this.transaction = null;
            }
            TransactionResourceHolder.bound(transaction);
        }


        @SuppressWarnings("unchecked")
        public void run() {
            int rowAffected = -1;

            if (!transactionDependency) {
                // 如果有事务依赖，这些操作都已经在SaftranExecutorTransaction里统一处理了
                GnrSafInfoPo set = new GnrSafInfoPo();
                set.setSafStat(SafStat.EXECUTING);
                set.setSafCount(saf.getSafCount() + 1);
                set.setSafTime(DipperSysInfo.getSysTime());
                if (saf.getSafSysSeq() == null) {
                    set.setSafSysSeq(sequenceService.generateSysSeq());
                    saf.setSafSysSeq(set.getSafSysSeq());
                }

                GnrSafInfoPo where = new GnrSafInfoPo();
                where.setId(saf.getId());
                where.setSafStat(saf.getSafStat());

                rowAffected = daoService.update(set, where);

                saf.setSafTime(set.getSafTime());
                saf.setSafCount(set.getSafCount());

                MDC.put(com.pactera.dipper.core.utils.Constants.MDC.ID,
                    "SAF_" + saf.getSafSysSeq() + "_" + saf.getSafCount());
            }

            if (transactionDependency || rowAffected == 1) {
                boolean dealResult = false; // 冲正处理状态

                try {

                    logger.info("开始第[{}]次冲正处理[{}] , 原流水[{}] , 冲正流水[{}] , Atom Index[{}] , 冲正服务[{}]",
                        saf.getSafCount(), saf.getId(), saf.getSysSeq(), saf.getSafSysSeq(), saf.getAtomIdx(),
                        saf.getSafSvr());

                    Message m = (Message) KryoUtil.deserialize(saf.getParmData());
                    m.setStores(new LinkedList<Store>());
                    m.getTarget().getHeaders().put(Transaction.LAZY, transaction);
                    m.setId("SAF_" + saf.getSafSysSeq() + "_" + saf.getSafCount());

                    Map<String, Object> body = ((Map<String, Object>) m.getTarget().getBodys());
                    body.put("safSysSeq", saf.getSafSysSeq());
                    body.put("safTime", saf.getSafTime());

                    IDipperHandler<Object> safHandler = null;

                    try {
                        // 原子服务
                        safHandler = (IDipperHandler<Object>) applicationContext.getBean(saf.getSafSvr());
                    } catch (Exception e) {
                        // dubbo服务
                        safHandler = dipperHandlerContainer.getDipperHandler(saf.getSafSvr(), 500000);
                    }
                    m = (Message) safHandler.handle(m);

                    if (m.getFault() != null && !ResponseCode.SUCCESS.equals(m.getFault().getCode())) {
                        logger.error("冲正服务处理失败：{}", m);
                        throw new RuntimeException(m.getFault().getMsg());
                    }

                    logger.info("冲正处理成功 ,{} , 流水号[{}]", saf.getId(), saf.getSysSeq());

                    // 冲正服务为查证服务
                    if (FlowStat.UNKNOW.equals(saf.getFlowStat())
                            && FlowStat.UNKNOW.equals(saf.getAtomStat())) {

                        Object confirmationResult =
                                ((Map<String, Object>) m.getTarget().getBodys()).get("confirmationResult");// 查证服务需要返回此字段
                        logger.info("查证服务处理成功 ,{}, 查证结果为[{}]", saf.getId(), confirmationResult);

                        // 更新冲正流水的flow状态
                        if (confirmationResult != null && confirmationResult instanceof Boolean) {
                            GnrSafInfoPo whereCondition = new GnrSafInfoPo();
                            whereCondition.setSysSeq(saf.getSysSeq());

                            GnrSafInfoPo setCondition = new GnrSafInfoPo();
                            if ((Boolean) confirmationResult) {
                                setCondition.setFlowStat(FlowStat.SUCESS);
                                whereCondition.setFlowStat(FlowStat.UNKNOW);// 一个flow里可能有多个需要查证的情况,避免覆盖
                            } else {
                                setCondition.setFlowStat(FlowStat.FAULT); // 原来查证成功的记录也需要修改为失败
                            }
                            rowAffected = daoService.update(setCondition, whereCondition);
                            logger.info("查证服务处理成功 ,{}, 查证结果为[{}], 更新冲正记录数为[{}]", saf.getId(),
                                confirmationResult, rowAffected);

                            // 更新查证处理状态为成功

                        } else {
                            logger.warn("查证服务 {},流水[{}], 无返回查证结果", saf.getId(), saf.getSysSeq());

                            throw new RuntimeException("查证服务处理失败");// catch里更新冲正状态
                        }
                    } else {
                        // 非查证服务
                        // 更新冲正处理状态为成功
                    }
                    dealResult = true;

                } catch (Exception e) {
                    logger.error("冲正处理失败 ,{} , 服务[{}]", saf.getId(), saf.getSafSvr());
                    logger.error("冲正处理失败", e);

                    if (transactionDependency) {
                        if (e instanceof RuntimeException)
                            throw (RuntimeException) e;
                        throw new RuntimeException(e);
                    }

                    // 更新冲正状态为处理失败

                } finally {
                    // 有事务依赖，处理成功需要修改状态，最后统一提交 ,
                    // 处理失败不修改状态，只最后统一修改为失败SaftranExecutorTransaction
                    try {
                        if (!transactionDependency || dealResult) {
                            GnrSafInfoPo updatePo = new GnrSafInfoPo();
                            updatePo.setId(saf.getId());
                            if (dealResult) {// 处理成功
                                updatePo.setSafStat(SafStat.SUCESS);
                            } else {// 处理失败
                                updatePo.setSafStat(
                                    saf.getSafCount() >= limitCount ? SafStat.FAULT : SafStat.NOT_EXECUTE);
                            }
                            rowAffected = daoService.update(updatePo);
                            if (rowAffected == 1) {
                                logger.info("修改冲正流水[{}]状态成功", saf.getId());
                            } else {
                                logger.info("修改冲正流水[{}]状态错误 , 更新记录数[{}]", saf.getId(), rowAffected);
                            }
                        }
                    } finally {
                        TransactionResourceHolder.unbound();
                    }
                }
            } else {
                logger.warn("不作处理 ：[{}] , rowAffected=[{}] , transactionDependency = [{}]", saf.getId(),
                    rowAffected, transactionDependency);
            }
        }
    }


    public void setDipperHandlerContainer(DipperHandlerContainer dipperHandlerContainer) {
        SaftranInvoker.dipperHandlerContainer = dipperHandlerContainer;
    }


    public static void setSequenceService(IDipperSequenceService sequenceService) {
        SaftranInvoker.sequenceService = sequenceService;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SaftranInvoker.applicationContext = applicationContext;
    }
}
