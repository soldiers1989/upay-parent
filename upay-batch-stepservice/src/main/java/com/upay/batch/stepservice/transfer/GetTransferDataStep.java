package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.*;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.gnr.FileInfoPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.DsConfPo;
import com.upay.dao.po.pay.PayFlowDetailPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 批量转账 - 第二步 转账文件明细入库。 目前只能单线程处理转账明细入库，不能采用分布式批量数据处理或多线程数据处理，
 * 除非能解决单文件多转账明细数据重复问题和多文件转账明细数据重复问题。因为在微信
 * 清分文件中一条清分数据可能重复出现，这种情况下重复数据是不允许再次清分的（除非
 * 之前的清分数据批量转账失败了）。如果在单个文件明细入库时或多个文件明细入库时有
 * 重复数据，而且采用分布式批量数据处理或多线程数据处理，则会可能会导致重复明细同
 * 时入库且状态为未转账处理；但实际处理应变是除第一条数据状态为未转账处理，其它状 态必须为重复数据状。
 *
 * @author
 */
public class GetTransferDataStep extends
        AbstractStepExecutor<Map<String, Object>, FileInfoPo> {

    /**
     * 日志logger
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(GetTransferDataStep.class);

    @Resource
    private IDipperCached iDipperCached;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * 转账文件本地存入路径
     */
    private String localPath;


    /**
     * 获取微信批量转账文件明细总数
     *
     * @param batchParams
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public int getTotalResult(BatchParams batchParams,
                              Map<String, Object> object) throws BatchException {


        // 查询微信转账文件信息
        FileInfoPo fileInfo = new FileInfoPo();
        fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_TRANSFER);
        fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_DOWNLOAD);
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_DOWNLOAD_SUC);
        List<FileInfoPo> winXinFileInfos = daoService.selectList(fileInfo);
        int totalResult = 0;
        if (!winXinFileInfos.isEmpty()) {
            totalResult = winXinFileInfos.size();
        }

        return totalResult;

    }

    /**
     * 分页获取转账明细。
     *
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<FileInfoPo> getDataList(BatchParams batchParams,
                                        int offset, int pageSize, Map<String, Object> object)
            throws BatchException {


        FileInfoPo fileInfo = new FileInfoPo();
        fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_TRANSFER);
        fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_DOWNLOAD);
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_DOWNLOAD_SUC);
        List<FileInfoPo> winXinFileInfos = daoService.selectList(fileInfo, offset, pageSize);
        return winXinFileInfos;


    }

    /**
     * 微信批量转账明细入库。 转账明细入库需确认转账通道及计算通道手续费。
     *
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index,
                        FileInfoPo fileInfo, Map<String, Object> object)
            throws BatchException {

        String name = Thread.currentThread().getName();
        long id = Thread.currentThread().getId();
        logger.info("线程名{}，线城id {}" + name, id);

        if (fileInfo == null) {
            return;
        }

        //获取本地存储路径
        //String localFullPath = (String)batchParams.getParameter().get("localFullPath");

        // 当前交易日期转账文件存放目录
        String transDateStr = DateUtil.format(batchParams.getTranDate(), "yyyyMMdd");
        String localFullPath = localPath + transDateStr + File.separator;

        String fileName = fileInfo.getFileName();
        File winXinTransferFile = new File(localFullPath + fileName);
        if (!winXinTransferFile.exists()) {
            throw new BatchException("批量转账文件[" + fileName + "]文件不存在");
        }
        List<Transfer> dataList = Transfer.getDataList(winXinTransferFile);
        if (dataList != null && dataList.size() > 0) {


            for (Transfer transfer : dataList) {
                transfer.setTrfSeq(index + "");
                // 开户机构号
                String brchNo = null;
                // 转账资金通道代码
                String routeCode = null;
                // 收款账户类型
                String merAcctBankNo = transfer.getMerAcctBankNo();
                String payeeAcctType = transfer.getSecMerAcctType();//(String) data.get("secMerAcctType");
                String payeeBankNo = merAcctBankNo;
                String payeeBankName = transfer.getMerAcctIssuer(); //(String) data.get("merAcctIssuer");
                String accountType = transfer.getAccountType();
                String firstMerNo = transfer.getFirstMerNo();
                String bankNo = transfer.getBankNo();
                String transFlag = transfer.getTransFlag();
                String accountNo = transfer.getAccountNo();
                String secondMerNo = transfer.getSecondMerNo();
                String merAcctNo = transfer.getMerAcctNo();
                String txnAmt = transfer.getTransAmt();
                String bankId = transfer.getBankId();
                String certType = transfer.getCertType();
                String certNo = transfer.getCertNo();
                String certMobile = transfer.getCertMobile();
                String certName = transfer.getCertName();
                String chnlId = transfer.getChnlId();
                String merSeq = transfer.getMerSeq();
                if (StringUtils.isNotBlank(merSeq)) {
                    merSeq = merSeq.trim();
                }
                PayFlowDetailPo payFlowDetailPo = new PayFlowDetailPo();
                payFlowDetailPo.setMerTransSeq(merSeq);
                // 查询是否当前数据已入库完成
                payFlowDetailPo = daoService.selectOne(payFlowDetailPo);
                // 重复数据无需处理
                if (null != payFlowDetailPo) {
                    LOGGER.debug("商户流水号:[" + merSeq + "]数据己存在，不能重复导入");
                    continue;
                }


                //region 非内部转账
                DsConfPo dsConfPo = new DsConfPo();
                dsConfPo.setCallerChnlId(chnlId);
                String payRouteMethod = null;
                if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {
                    dsConfPo.setTransCode(DataBaseConstans_ACC.TRANS_CODE_PAYMENT);
                } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION
                        .equals(transFlag)) {
                    dsConfPo.setTransCode(DataBaseConstans_ACC.TRANS_CODE_COLLECTION);
                }
                dsConfPo = daoService.selectOne(dsConfPo);
                if (null == dsConfPo) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "调用方的代收付渠道未配置！");
                }
                payRouteMethod = dsConfPo.getPayRoute();
                if (StringUtils.isNotBlank(payRouteMethod)) {
                    if (CommonBaseConstans_PAY.UNION_PAY_PRIORITY_PAY.equals(payRouteMethod)
                            || CommonBaseConstans_PAY.UNION_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)
                            || CommonBaseConstans_PAY.UNION_PAY_NON_JUMP_PAY.equals(payRouteMethod)
                            || CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_PAY.equals(payRouteMethod)
                            || CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)) {
                        if (CommonBaseConstans_PAY.UNION_PAY_PRIORITY_PAY.equals(payRouteMethod)
                                || CommonBaseConstans_PAY.UNION_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)
                                || CommonBaseConstans_PAY.UNION_PAY_NON_JUMP_PAY.equals(payRouteMethod)) {
                            routeCode = DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS ;
                        } else if (CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_PAY.equals(payRouteMethod)
                                || CommonBaseConstans_PAY.ZJ_PAY_PRIORITY_COLLECTION.equals(payRouteMethod)) {
                            routeCode = DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY;
                        }
                    } else {
                        logger.info("未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
                    }
                } else {
                    logger.info("未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "未获取支付渠道payRouteMethod参数  值分别为 银联代付（0001）中金代付（0002） 银联代收（0003）无跳转支付（0004）中金代收（0005）");
                }
                logger.info("从系统参数表（T_GNR_PARM） 读取  route  end   route值为[{}]--------------", routeCode);
                //endregion


                //<editor-fold desc="   //内部转账">
                if ((DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(accountType)
                        || DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(accountType)
                        || DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(accountType))
                        && (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAcctType)
                        || DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAcctType)
                        || DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAcctType))) {
                    //当二级商户结算账户为本行内部户是作为 内部转账处理
                    routeCode = DataBaseConstants_PAY.ROUTE_CODE_HOST;
                }
                //</editor-fold>

                //存在商户   不用检查
                if (StringUtils.isBlank(firstMerNo) || StringUtils.isBlank(secondMerNo)) {
                    if (StringUtils.isBlank(accountType)) {
                        if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {
                            throw new BatchException("付款方帐户类型[" + accountType + "]accountType信息不能为空。");
                        } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION.equals(transFlag)) {
                            throw new BatchException("收款方帐户类型[" + accountType + "]accountType信息不能为空。");
                        }
                    }
                    if (StringUtils.isBlank(accountNo)) {
                        if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {
                            throw new BatchException("付款方账号[" + accountNo + "]accountNo信息不能为空。");
                        } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION.equals(transFlag)) {
                            throw new BatchException("收款方账号[" + accountNo + "]accountNo信息不能为空。");
                        }
                    }
                    //本行卡 和本行对公账户  内部账户  不用传 bankNo
                    if (DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(accountType)) {
                        if (StringUtils.isBlank(bankNo)) {
                            if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {
                                throw new BatchException("收款方账号银行编号[" + bankNo + "]bankNo信息不能为空。");
                            } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION.equals(transFlag)) {
                                throw new BatchException("付款方账号银行编号[" + bankNo + "]bankNo信息不能为空。");
                            }
                        }
                    }


                }

                if (DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payeeAcctType)) {
                    if (StringUtils.isBlank(bankId)) {
                        if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {
                            throw new BatchException("收款方账号银行编号[" + bankId + "]bankNo信息不能为空。");
                        } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION.equals(transFlag)) {
                            throw new BatchException("付款方账号银行编号[" + bankId + "]bankNo信息不能为空。");
                        }
                    }
                }
                if (StringUtils.isBlank(merAcctNo)) {
                    if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {
                        throw new BatchException("付款方账号[" + merAcctNo + "]merAcctNo信息不能为空。");
                    } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION.equals(transFlag)) {
                        throw new BatchException("收款方账号[" + merAcctNo + "]merAcctNo信息不能为空。");
                    }
                }


                MerAcctInfoPo merAcctInfo = null;
                //如果不存在  accountNo  说明存在商户  使用商户的结算账户去付款 或者收款
                if (StringUtils.isBlank(accountNo)) {
                    merAcctInfo = new MerAcctInfoPo();
                    merAcctInfo.setMerNo(firstMerNo);
                    merAcctInfo = daoService.selectOne(merAcctInfo);
                    if (null == merAcctInfo) {
                        throw new BatchException("商户[" + firstMerNo + "]账务信息不存在。");
                    }
                }


                String winXinFileName = fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf(File.separator) + 1);
                String[] splitFileName = winXinFileName.split("_");
                for (String a : splitFileName) {
                    LOGGER.info(a + "   ======== 文件名");
                }
                Date merTransDate = DateUtil.parse(splitFileName[2], "yyyyMMdd");


                // 转账明细
                PayFlowDetailPo payFlowDetail = new PayFlowDetailPo();

                // 支付明细，默认为初始状态。
                String transStat = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N;
                // 支付失败原因。
                String payFailReason = null;

                BigDecimal transAmt = BigDecimal.valueOf(Double.parseDouble(txnAmt));
                // 交易金额有效性检查
                if (null == transAmt || transAmt.compareTo(BigDecimal.ZERO) < 0) {
                    transStat = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
                    payFailReason = "交易金额为空或为负数";
                }

                // 商户流水号有效性检查。
                if (StringUtils.isBlank(merSeq)) {
                    transStat = DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
                    payFailReason = "商户流水号为空";
                }


                if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(transFlag)) {// 代付
                    if (null == merAcctInfo) {
                        payFlowDetail.setPayerAcctType(accountType);
                        payFlowDetail.setPayerAcctNo(accountNo);
                        payFlowDetail.setPayerName("云南红塔银行股份有限公司批量代付");
                    } else {
                        payFlowDetail.setPayerAcctType(merAcctInfo.getStlAcctType());
                        payFlowDetail.setPayerName(merAcctInfo.getStlAcctName());
                        payFlowDetail.setPayerAcctNo(merAcctInfo.getStlAcctNo());
                    }
                    payFlowDetail.setPayeeName("云南红塔银行股份有限公司批量代付");
                    payFlowDetail.setPayeeAcctNo(merAcctNo);
                    payFlowDetail.setPayeeAcctType(payeeAcctType);
                    payFlowDetail.setPayeeBankName(payeeBankName);
                    payFlowDetail.setPayeeBankNo(payeeBankNo);
                    payFlowDetail.setOrderDes("批量-代付");

                } else if (DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION
                        .equals(transFlag)) {// 代收
                    if (null == merAcctInfo) {
                        payFlowDetail.setPayeeAcctType(accountType);
                        payFlowDetail.setPayeeName("云南红塔银行股份有限公司批量代收");
                        payFlowDetail.setPayeeAcctNo(accountNo);
                    } else {
                        payFlowDetail.setPayeeAcctNo(merAcctInfo.getStlAcctNo());
                        payFlowDetail.setPayeeName(merAcctInfo.getStlAcctName());
                        payFlowDetail.setPayeeAcctType(merAcctInfo.getStlAcctType());
                    }

                    payFlowDetail.setPayerName("云南红塔银行股份有限公司批量代收");
                    payFlowDetail.setPayerAcctNo(merAcctNo);
                    payFlowDetail.setPayerAcctType(payeeAcctType);
                    payFlowDetail.setPayerBankName(payeeBankName);
                    payFlowDetail.setPayerBankNo(payeeBankNo);
                    payFlowDetail.setOrderDes("批量-代收");


                }


                Date nowTime = new Date();
                payFlowDetail.setTransFlag(transFlag);
                payFlowDetail.setCcy(DataBaseConstants_PAY.T_CORE_CCY_CNY);
                payFlowDetail.setTransAmt(transAmt);
                payFlowDetail.setTransStat(transStat);
                payFlowDetail.setRemark1(payFailReason); // 失败原因
                payFlowDetail.setTransTime(nowTime);
                payFlowDetail.setLastUpdateTime(nowTime);

                payFlowDetail.setMerTransDate(merTransDate);
                payFlowDetail.setMerTransSeq(merSeq);
                payFlowDetail.setBrchNo(brchNo); // 开户机构号
                payFlowDetail
                        .setTransferType(DataBaseConstants_PAY.TRANSFER_TYPE_WAGE);
                payFlowDetail.setSysDate(batchParams.getTranDate());
                payFlowDetail.setRouteCode(routeCode);
                payFlowDetail.setSecMerNo(secondMerNo);
                payFlowDetail.setMerNo(firstMerNo);
                payFlowDetail.setTrfBatchNo(fileInfo.getFileSerino());
                payFlowDetail.setDisBatchNo(batchParams.getBatchNo());
                payFlowDetail.setBankId(bankId);

                payFlowDetail.setCertType(certType);
                payFlowDetail.setCertNo(certNo);
                payFlowDetail.setCertMobile(certMobile);
                payFlowDetail.setCertName(certName);
                payFlowDetail.setChnlid(chnlId);
                // 第几条数据，用于区分同一转账文件中重复的记录
                Integer trfSeq = Integer.valueOf(transfer.getTrfSeq());
                payFlowDetail.setTrfSeq(trfSeq);
                //默认数据是 未处理状态
                payFlowDetail.setProcess(DataBaseConstants_PAY.PAY_PROCESS_N);
                daoService.insert(payFlowDetail);
                LOGGER.debug("----- 批量转账 -- {}文件第{}条转账明细[{}]入库完成", new Object[]{
                        fileInfo.getFileName(), trfSeq, merSeq});
            }
        }
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_IN_DB);
        fileInfo.setUpdateTime(new Date());
        daoService.update(fileInfo);
        LOGGER.info("----- 批量转账 -- {}文件明细入库完成",
                new Object[]{fileInfo.getFileName()});

    }


}