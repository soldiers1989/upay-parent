package com.upay.batch.stepservice.chk.union;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 以银联数据为主对账
 *
 * @author 张立朋
 * @version v1.0
 * @CreateDate: 2016-11-28
 *
 *
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */

public class ChkByUnionPayStep extends AbstractStepExecutor<Object, ChkThirdDetailPo> {
    private final static Logger logger = LoggerFactory.getLogger(ChkByUnionPayStep.class);


    @Override
    public List<ChkThirdDetailPo> getDataList(BatchParams batchParams, int offset, int pageSize,
                                              Object object) throws BatchException {
        String batchNo = batchParams.getBatchNo();
        logger.info("-----批次号：{}---获取第三方对账明细表---UnionPay---(T_CHK_THIRD_DETAIL)数据流水数据开始--------",
                batchParams.getBatchNo());
        // 获取第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)数据
        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
        chkThirdDetailPo.setChkBatchNo(batchNo);
        chkThirdDetailPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
        chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        List<ChkThirdDetailPo> chkThirdDetailPoList = new ArrayList<ChkThirdDetailPo>();
        chkThirdDetailPoList = daoService.selectList(chkThirdDetailPo);
        logger.info("-----批次号：{}---获取第三方对账明细表---UnionPay---(T_CHK_THIRD_DETAIL)数据流水数据结束--------",
                batchParams.getBatchNo());
        return chkThirdDetailPoList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, ChkThirdDetailPo data, Object object)
            throws BatchException {
        logger.info("-----执行批量操作第" + index + "次--------");
        // 获取银联源表数据
        ChkThirdDetailPo chkThirdDetaiPo = (ChkThirdDetailPo) data;
        // 开始进行数据勾兑:根据平台流水号、资金通道代码、平台交易日期查询交易流水
        PayFlowListPo payFlowListPo = queryPmtFlowListInfo(chkThirdDetaiPo.getThirdSeq(),
                chkThirdDetaiPo.getOrgCode(), chkThirdDetaiPo.getChnlDate());

        if (payFlowListPo == null) {
            // 核心多
            logger.info("银联多");
            // 插入差错信息表（T_CHK_ERR_LIST）
            insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
            logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
            updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
            logger.info("更新第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)結束");
        } else {
            // 流水同时存在
            // 支付流水存在
            if (payFlowListPo.getTransStat().equals(DataBaseConstants_PAY.T_PAY_TX_SUCCESS)
                    && chkThirdDetaiPo.getTransStat().equals(DataBaseConstants_PAY.T_PAY_CORE_TX_SUCCESS)) {
                // 记账状态相同,且都为成功
                if (chkThirdDetaiPo.getTransStat().equals(DataBaseConstants_PAY.T_PAY_CORE_TX_SUCCESS)
                        && chkThirdDetaiPo.getTransAmt().compareTo(payFlowListPo.getTransAmt()) != 0) {
                    // 金额不符
                    logger.info("与第三方（银联）对账状态相符，金额不符");
                    // 插入差错信息表（T_CHK_ERR_LIST）
                    // 插入差错信息表（T_CHK_ERR_LIST）
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,
                            DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    logger.info("更新第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)結束");
                    updatePayFlowList(payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    logger.info("更新平台支付流水表(T_PAY_FLWO_LIST)結束");
                } else {
                    logger.info("对账成功");
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
                    updatePayFlowList(payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
                    logger.info("更新平台支付流水表(T_PAY_FLWO_LIST)和第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)結束");
                }
            } else {
                // 状态不同,第三方（银联）成功,平台失败
                if (chkThirdDetaiPo.getTransStat().equals(DataBaseConstants_PAY.T_PAY_CORE_TX_SUCCESS)) {
                    logger.info("第三方（银联）成功，本系统失败/非终态");
                    // 插入差错信息表（T_CHK_ERR_LIST）
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,
                            DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    logger.info("更新第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)結束");
                    updatePayFlowList(payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    logger.info("更新平台流水表(T_PAY_FLWO_LIST)結束");
                } else {
                    logger.info("第三方（银联）失败，本系统成功");
                    // 插入差错信息表（T_CHK_ERR_LIST）
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,
                            DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
                    logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
                    logger.info("更新第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)結束");
                    updatePayFlowList(payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
                    logger.info("更新平台流水表(T_PAY_FLWO_LIST)結束");
                }

            }
        }
    }


    /***
     * *更新支付平台流水表(T_PAY_FLOW_LIST)
     *
     * @param chkDetailPo
     * @param chkStat
     */
    public void updatePayFlowList(PayFlowListPo payFlowListPo, String chkStat) {
        payFlowListPo.setChkFlag(chkStat);// 对账标志
        daoService.update(payFlowListPo);
    }


    /***
     * *查询支付流水表(T_PMT_FLOW_LIST)
     *
     * @param chkSeq交易流水号
     * @param routeCode通道代码
     * @param chkDate交易日期
     */
    public PayFlowListPo queryPmtFlowListInfo(String chkSeq, String routeCode, Date chkDate) {
        PayFlowListPo pmtFlowListPo = new PayFlowListPo();
//        pmtFlowListPo.setRouteDate(chkDate);
        pmtFlowListPo.setSettleKey(chkSeq);
        pmtFlowListPo.setRouteCode(routeCode);
        pmtFlowListPo = daoService.selectOne(pmtFlowListPo);
        return pmtFlowListPo;
    }


    /***
     * *更新第三方（银联）对账明细表(T_CHK_THIRD_DETAIL)
     *
     * @param chkThirdDetailPo
     * @param chkStat
     */
    public void updateChkThirdDetai(ChkThirdDetailPo chkThirdDetaiPo, String chkStat) {
        chkThirdDetaiPo.setThirdStat(chkStat);// 对账标志
        daoService.update(chkThirdDetaiPo);

    }


    /***
     * *插入差错信息表（T_CHK_ERR_LIST）
     *
     * @param ChkDetailPo
     * @param ChkErrDealOpinionPo
     */
    public void insertChkErrListInfo(ChkThirdDetailPo chkThirdDetaiPo, PayFlowListPo payFlowListPo,
                                     String errStat) {

        ChkErrListPo chkErrListPo = new ChkErrListPo();
        // chkErrListPo.setOrgCode(chkHostDetailPo.getOrgCode());//第三方机构代码

        chkErrListPo.setTransDate(chkThirdDetaiPo.getChnlDate());
        chkErrListPo.setTransTime(chkThirdDetaiPo.getChnlTime());
        chkErrListPo.setSysSeq(chkThirdDetaiPo.getChnlSeq());// 平台流水号
        // chkErrListPo.setHostSeq(chkHostDetailPo.getChnlSeq());// 核心流水号
        chkErrListPo.setThirdSeq(chkThirdDetaiPo.getThirdSeq());
        chkErrListPo.setBatchNo(chkThirdDetaiPo.getChkBatchNo());
        chkErrListPo.setOrgCode(chkThirdDetaiPo.getOrgCode());// 银联资金通道代码
        chkErrListPo.setThirdChkBatchNo(chkThirdDetaiPo.getChkBatchNo());
        chkErrListPo.setThirdChkDate(chkThirdDetaiPo.getChkDate());
        chkErrListPo.setCurrNo(DataBaseConstants_PAY.T_CORE_CCY_CNY);
        chkErrListPo.setPayAcct(chkThirdDetaiPo.getPayAcct());
        chkErrListPo.setPayeeAcct(chkThirdDetaiPo.getPayeeAcct());
        chkErrListPo.setTransAmt(chkThirdDetaiPo.getTransAmt());
        chkErrListPo.setFeeAmt(chkThirdDetaiPo.getFeeAmt());
        chkErrListPo.setThridErrStat(errStat);
        chkErrListPo.setErrStat(DataBaseConstants_PAY.T_PMT_DEELERR_NOT);

        if (payFlowListPo != null) {
            // 获取订单信息
            PayOrderListPo payOrderListPo = new PayOrderListPo();
            payOrderListPo.setOrderNo(payFlowListPo.getOrderNo());
            payOrderListPo = daoService.selectOne(payOrderListPo);

            if (payOrderListPo != null) {
                chkErrListPo.setTransCode(payOrderListPo.getTransCode());
                chkErrListPo.setChnlId(payOrderListPo.getChnlId());
                chkErrListPo.setChnlSeq(payOrderListPo.getMerSeq());// 商户流水号
                chkErrListPo.setOrderNo(payOrderListPo.getOuterOrderNo());// 商户订单号
            }

            // 保存支付流水相关信息
            chkErrListPo.setTransDate(payFlowListPo.getSysDate());
            chkErrListPo.setTransTime(payFlowListPo.getTransTime());
            chkErrListPo.setSysSeq(payFlowListPo.getTransSubSeq());
        }

        daoService.insert(chkErrListPo);//

    }

}
