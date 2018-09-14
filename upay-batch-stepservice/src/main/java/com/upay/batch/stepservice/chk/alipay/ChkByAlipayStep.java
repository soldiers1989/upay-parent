package com.upay.batch.stepservice.chk.alipay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 以支付宝数据为主对账
 * 
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */

public class ChkByAlipayStep extends AbstractStepExecutor<Object, ChkThirdDetailPo> {
    private final static Logger logger = LoggerFactory.getLogger(ChkByAlipayStep.class);



    /**
     * 获取支付宝交易明细与平台支付流水进行对账，对账以平台数据为准
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
        // 获取第三方（支付宝）对账明细表(T_CHK_THIRD_DETAIL)数据
        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
        chkThirdDetailPo.setChkBatchNo(batchNo);
        chkThirdDetailPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);
        chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);

        List<ChkThirdDetailPo> chkThirdDetailPoList = daoService.selectList(chkThirdDetailPo);

        logger.info("-----批次号：{}---获取第三方对账明细表---CPCN---(T_CHK_THIRD_DETAIL)数据流水数据结束---总记录数[{}]-----", batchNo,
            chkThirdDetailPoList.size());

        return chkThirdDetailPoList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, ChkThirdDetailPo chkThirdDetaiPo, Object object)
            throws BatchException {
        logger.info("-----执行批量操作第" + index + "次--------");
        // 获取支付宝源表数据
//        ChkThirdDetailPo chkThirdDetaiPo = (ChkThirdDetailPo) data;

        // 开始进行数据勾兑:根据平台流水号、资金通道代码、平台交易日期查询交易流水
        PayFlowListPo payFlowListPo = queryPmtFlowListInfo(chkThirdDetaiPo.getChnlSeq(),
            chkThirdDetaiPo.getOrgCode(), chkThirdDetaiPo.getChnlDate());

        if (payFlowListPo == null) { // 支付宝多交易明细
            logger.info("支付宝多交易明细，支付宝交易流水：[{}]", chkThirdDetaiPo.getThirdSeq());
            // 记录差错信息
            insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
            // 更新第三方交易明细对账状态
            updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
        } else {

            if (payFlowListPo.getTransStat().equals(DataBaseConstants_PAY.T_PAY_TX_SUCCESS)
                    && chkThirdDetaiPo.getTransStat().equals(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS)) { // 平台交易成功，支付宝交易成功

                if (chkThirdDetaiPo.getTransAmt().compareTo(payFlowListPo.getTransAmt()) != 0) {
                    // 金额不符
                    logger.info("平台支付流水[{}]交易状态与支付宝交易对账状态相符，金额不符", chkThirdDetaiPo.getChnlSeq());
                    // 记录差错信息
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,
                        DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams,
                        DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
                } else {
                    logger.info("平台支付流水[{}]与支付宝对账成功", chkThirdDetaiPo.getChnlSeq());
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
                }
            } else {
                // 状态不同,第三方（支付宝）成功,平台失败
                if (chkThirdDetaiPo.getTransStat().equals(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS)) {
                    logger.info("平台支付流水[{}]交易失败，支付宝交易成功", chkThirdDetaiPo.getChnlSeq());
                    // 插入差错信息
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,
                        DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    // 更新第三方交易明细对账状态
                    updateChkThirdDetai(chkThirdDetaiPo, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                    // 更新平台支付流水对账状态
                    updatePayFlowList(payFlowListPo, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
                } else {
                    logger.info("平台支付流水[{}]交易成功，支付宝交易失败", chkThirdDetaiPo.getChnlSeq());
                    // 插入差错信息
                    insertChkErrListInfo(chkThirdDetaiPo, payFlowListPo,
                        DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
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
//        PayFlowListPo pmtFlowListPo = new PayFlowListPo();
//        // 平台交易日期
//        pmtFlowListPo.setRouteDate(transDate);
//        // 平台交易流水
//        pmtFlowListPo.setTransSubSeq(chkSeq);
//        pmtFlowListPo.setRouteCode(routeCode);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("routeDate", transDate);
        map.put("transSubSeq", chkSeq);
        map.put("routeCode", routeCode);
        PayFlowListPo pay = daoService.selectOne(PayFlowListPo.class.getName().concat(".getOneByWhere"),map);
        return pay;
    }


    /***
     * *更新第三方（支付宝）对账明细对账状态
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
        chkErrListPo.setOrgCode(chkThirdDetaiPo.getOrgCode());// 支付宝资金通道代码
//        chkErrListPo.setThirdChkBatchNo(chkThirdDetaiPo.getChkBatchNo());
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

}
