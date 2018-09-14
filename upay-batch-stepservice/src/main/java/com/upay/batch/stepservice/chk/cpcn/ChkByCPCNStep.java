package com.upay.batch.stepservice.chk.cpcn;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 以平台数据为主对账
 * 
 * @author 张立朋
 * @version v1.0
 * @CreateDate: 2016-11-28
 * 
 * 第三步
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */

public class ChkByCPCNStep extends AbstractStepExecutor<Object, ChkThirdDetailPo> {
    private final static Logger logger = LoggerFactory.getLogger(ChkByCPCNStep.class);
    // 不需要参与对账的交易码
    private String[] transCodes = { "2310", "2320", "2340","2501","2502","2503" };


    /**
     * 中金节假日不参于对账 如果在节假日，则返回null；
     * 
     * @param batchParams
     * @return
     * @throws BatchException
     */
//    @Override
//    public List<Object> getObjectList(BatchParams batchParams) throws BatchException {
//        // 中金节假日也参于对账
//        // if(!PlatFormHolidayContext.isWorkDay(batchParams.getTranDate(), "0"))
//        // {
//        // return null;
//        // } else {
//        // 返回包含一个null元素的list，用于使用本step继续执行
//		List list = new ArrayList();
//		list.add(null);
//		return list;
//        // }
//    }
    @Override
   	public int getTotalResult(BatchParams batchParams, Object object)
   			throws BatchException {
    	String batchNo = batchParams.getBatchNo();
        logger.info("-----批次号：{}---获取第三方对账明细表---CPCN---(T_CHK_THIRD_DETAIL)数据流水数据开始--------", batchNo);
        // 获取第三方（中金）对账明细表(T_CHK_THIRD_DETAIL)数据
        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
        chkThirdDetailPo.setChkBatchNo(batchNo);
        chkThirdDetailPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
        chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);

        Long total = daoService.selectQueryResult(chkThirdDetailPo, 0	, 0).getTotalrecord();

        logger.info("-----批次号：{}---获取第三方对账明细表---CPCN---(T_CHK_THIRD_DETAIL)数据流水数据结束---总记录数[{}]-----", batchNo,total);

        return total.intValue();
   	}
    
    
    /**
     * 获取中金交易明细与平台支付流水进行对账，对账以平台数据为准
     * 
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<ChkThirdDetailPo> getDataList(BatchParams batchParams, int offset, int pageSize,
            Object object) throws BatchException {

        String batchNo = batchParams.getBatchNo();
        logger.info("-----批次号：{}---获取第三方对账明细表---CPCN---(T_CHK_THIRD_DETAIL)数据流水数据开始--------", batchNo);
        // 获取第三方（中金）对账明细表(T_CHK_THIRD_DETAIL)数据
        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
        chkThirdDetailPo.setChkBatchNo(batchNo);
        chkThirdDetailPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
        chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);

        List<ChkThirdDetailPo> chkThirdDetailPoList = daoService.selectList(chkThirdDetailPo,offset,pageSize);

        logger.info("-----批次号：{}---获取第三方对账明细表---CPCN---(T_CHK_THIRD_DETAIL)数据流水数据结束---分页记录数[{}]-----", batchNo,  chkThirdDetailPoList.size());

        return chkThirdDetailPoList;
    }


	@Override
    public void execute(BatchParams batchParams, int index, ChkThirdDetailPo data, Object object)
            throws BatchException {
        logger.info("-----执行批量操作第" + index + "次--------");
        // 获取中金源表数据
        ChkThirdDetailPo chkThirdDetaiPo = (ChkThirdDetailPo) data;

        // 非金融类交易直接退回
        if (this.isNotFinanceTrans(data.getRemark())) {
            return;
        }

        // 开始进行数据勾兑:根据平台流水号、资金通道代码、平台交易日期查询交易流水
        PayFlowListPo payFlowListPo = queryPmtFlowListInfo(chkThirdDetaiPo.getChnlSeq(),
            chkThirdDetaiPo.getOrgCode(), chkThirdDetaiPo.getChnlDate());
        
        // 中金多交易明细
        if (payFlowListPo == null) { 
        	logger.info("查询平台流水为空======================");
            logger.info("中金多交易明细，中金交易流水：[{}]", data.getThirdSeq());
            // 记录差错信息
            insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
            // 更新第三方交易明细对账状态
            updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
        } else {
        	// 平台交易成功，中金交易成功
            if (payFlowListPo.getTransStat().equals(DataBaseConstants_PAY.T_PAY_TX_SUCCESS)
                    && chkThirdDetaiPo.getTransStat().equals(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS)) { //TODO 变量值检查
            	logger.info("平台金额："+payFlowListPo.getTransAmt()+"      中金金额："+chkThirdDetaiPo.getTransAmt()+"      "+(chkThirdDetaiPo.getTransAmt().compareTo(payFlowListPo.getTransAmt())==0?"账平":"账不平"));
            	// 金额不符
                if (chkThirdDetaiPo.getTransAmt().compareTo(payFlowListPo.getTransAmt()) != 0) {
                    logger.info("平台支付流水[{}]交易状态与中金交易对账状态相符，金额不符", data.getChnlSeq());
                    // 记录差错信息
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                } else {
                    logger.info("平台支付流水[{}]与中金对账成功", data.getChnlSeq());
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
                }
            } else {// 状态不同,第三方（中金）成功,平台失败
                if (chkThirdDetaiPo.getTransStat().equals(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS)) {  //TODO 确认状态判断是否合理
                    logger.info("平台支付流水[{}]交易失败，中金交易成功", data.getChnlSeq());
                    // 插入差错信息
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                } else {
                    logger.info("平台支付流水[{}]交易成功，中金交易失败", data.getChnlSeq());
                    // 插入差错信息
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
                }

            }
        }
    }


    /***
     * *更新支付平台流水对账状态
     * 
     * @param payFlow
     * @param chkStat
     */
    public void updatePayFlowList(PayFlowListPo payFlow, BatchParams batchParams, String chkStat) {
        // 变更值
        PayFlowListPo setPayFlow = new PayFlowListPo();
        setPayFlow.setChkFlag(chkStat);
        setPayFlow.setChkDate(batchParams.getTranDate());
        setPayFlow.setChkBatchNo(batchParams.getBatchNo());
        // 条件
        PayFlowListPo wherePayFlow = new PayFlowListPo();
        wherePayFlow.setSysDate(payFlow.getSysDate());
        wherePayFlow.setTransSubSeq(payFlow.getTransSubSeq());
        daoService.update(setPayFlow, wherePayFlow);
    }


    /***
     * *查询支付流水表(T_PMT_FLOW_LIST)
     * 
     * @param chkSeq
     * @param routeCode
     * @param transDate
     */
    @SuppressWarnings("all")
    public PayFlowListPo queryPmtFlowListInfo(String chkSeq, String routeCode, Date transDate) {
        PayFlowListPo pmtFlowListPo = new PayFlowListPo();
        // 平台交易日期
//        pmtFlowListPo.setRouteDate(transDate);
        // 平台交易流水
        pmtFlowListPo.setRouteSeq(chkSeq);
        pmtFlowListPo.setRouteCode(routeCode);
        logger.info("查询平台流水         日期："+transDate+"   渠道流水："+chkSeq+"     资金通道："+routeCode);
        pmtFlowListPo = daoService.selectOne(pmtFlowListPo);
        return pmtFlowListPo;
    }


    /***
     * *更新第三方（中金）对账明细对账状态
     * 
     * @param chkThirdDetaiPo
     * @param chkStat
     */
    public void updateChkThirdDetai(ChkThirdDetailPo chkThirdDetaiPo, String chkStat) {
        chkThirdDetaiPo.setThirdStat(chkStat);// 对账标志
        daoService.update(chkThirdDetaiPo);

    }


    /**
     * 插入差错信息表
     * 
     * @param chkThirdDetaiPo
     *            第三方对账明细
     * @param payFlowListPo
     *            平台支付流水
     * @param errStat
     *            对账标志
     */
    @SuppressWarnings("all")
    public void insertChkErrListInfo(ChkThirdDetailPo chkThirdDetaiPo, PayFlowListPo payFlowListPo,
            String errStat) {

        ChkErrListPo chkErrListPo = new ChkErrListPo();

        chkErrListPo.setTransDate(chkThirdDetaiPo.getChnlDate());
        chkErrListPo.setTransTime(chkThirdDetaiPo.getChnlTime());
        chkErrListPo.setSysSeq(chkThirdDetaiPo.getChnlSeq());// 平台流水号
        chkErrListPo.setThirdSeq(chkThirdDetaiPo.getThirdSeq());// 增加第三方流水号
        chkErrListPo.setBatchNo(chkThirdDetaiPo.getChkBatchNo());
        chkErrListPo.setOrgCode(chkThirdDetaiPo.getOrgCode());// 中金资金通道代码
        chkErrListPo.setThirdChkBatchNo(chkThirdDetaiPo.getChkBatchNo());
        chkErrListPo.setThirdChkDate(chkThirdDetaiPo.getChkDate());
        chkErrListPo.setCurrNo(DataBaseConstants_PAY.T_CCY_CNY);
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

        daoService.insert(chkErrListPo);

    }


    public boolean isNotFinanceTrans(String transCode) {
        boolean isNotFinaceTrans = false;
        List<String> transCodeList = Arrays.asList(transCodes);
        if (transCodeList.contains(transCode)) {
            isNotFinaceTrans = true;
        }
        return isNotFinaceTrans;
    }

}
