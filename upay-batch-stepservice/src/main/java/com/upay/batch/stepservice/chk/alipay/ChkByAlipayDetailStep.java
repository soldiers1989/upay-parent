package com.upay.batch.stepservice.chk.alipay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 平台未对账数据对账
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */

public class ChkByAlipayDetailStep extends AbstractStepExecutor<ChkInfoPo, PayFlowListPo> {
    private final static Logger logger = LoggerFactory.getLogger(ChkByAlipayDetailStep.class);
    private String batchNo;// 存储批次号


    @Override
    public List<ChkInfoPo> getObjectList(BatchParams batchParams) throws BatchException {

        // 登记对账批次信息
        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        List<ChkInfoPo> listChkInfo = daoService.selectList(chkInfo);
        return listChkInfo;
    }


    /**
     * 查询未对账的平台支付宝成功支付流水，这些数是为平台多流水
     * 
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<PayFlowListPo> getDataList(BatchParams batchParams, int offset, int pageSize,
            ChkInfoPo object) throws BatchException {

        batchNo = batchParams.getBatchNo();
        Date transDate = DateUtil.getPreDate(object.getChkDate());
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("transStat",DataBaseConstants_PAY.T_PAY_TX_SUCCESS );
        map.put("chkFlag", DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        map.put("routeDate", transDate);
        map.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);
        List<PayFlowListPo> payFlowList = daoService.selectList(PayFlowListPo.class.getName().concat(".getListByWhere"),map);
        logger.info("-----获取未对账的平台支付宝成功支付流水数据结束，交易日期[{}]，未对账记录数[{}]--------",
            new Object[] { DateUtil.format(transDate, "yyyyMMdd"), payFlowList.size() });
        return payFlowList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, PayFlowListPo data, ChkInfoPo object)
            throws BatchException {
        if (data == null) {
            return;
        }
        logger.info("-----平台支付流水[{}]，支付宝无对账明细--------", new Object[] { data.getTransSubSeq() });
        // 获取源表数据
        PayFlowListPo payFlowList = (PayFlowListPo) data;
        // 插入差错信息
        insertChkErrListInfo(payFlowList, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
        // 更新平台支付流水对账状态
        updatePayFlowList(payFlowList, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
    }


    @Override
    public void updateObject(BatchParams batchParams, ChkInfoPo object) {
        String batchNo = batchParams.getBatchNo();
        ChkInfoPo chkInfoPo = new ChkInfoPo();
        chkInfoPo.setBatchNo(batchNo);
        chkInfoPo.setChkDate(object.getChkDate());
        ChkInfoPo updatePo = new ChkInfoPo();
        updatePo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_SUCCESS);
        daoService.update(updatePo, chkInfoPo);
    }


    /***
     * *更新支付平台流水表(T_PAY_FLOW_LIST)
     * 
     * @param payFlowListPo
     * @param chkStat
     */
    public void updatePayFlowList(PayFlowListPo payFlowListPo, String chkStat) {
        payFlowListPo.setChkFlag(chkStat);// 对账标志
        daoService.update(payFlowListPo);
    }


    /**
     * 插入差错信息表（T_CHK_ERR_LIST）
     * 
     * @param payFlowListPo
     * @param batchParams
     * @param errStat
     */
    @SuppressWarnings("all")
    public void insertChkErrListInfo(PayFlowListPo payFlowListPo, BatchParams batchParams, String errStat) {

        if (payFlowListPo != null) {
            ChkErrListPo chkErrListPo = new ChkErrListPo();

            chkErrListPo.setTransDate(payFlowListPo.getSysDate());
            chkErrListPo.setTransTime(payFlowListPo.getTransTime());
            chkErrListPo.setSysSeq(payFlowListPo.getTransSubSeq());// 平台流水号
            chkErrListPo.setBatchNo(batchParams.getBatchNo());
            chkErrListPo.setHostChkBatchNo(batchParams.getBatchNo());
            chkErrListPo.setHostChkDate(batchParams.getTranDate());
            chkErrListPo.setCurrNo(DataBaseConstants_PAY.T_CORE_CCY_CNY);
            chkErrListPo.setPayAcct(payFlowListPo.getPayerAcctNo());
            chkErrListPo.setPayeeAcct(payFlowListPo.getPayeeAcctNo());
            chkErrListPo.setTransAmt(payFlowListPo.getTransAmt());
            chkErrListPo.setFeeAmt(payFlowListPo.getFeeAmt());
            chkErrListPo.setHostErrStat(errStat);
            chkErrListPo.setErrStat(DataBaseConstants_PAY.T_PMT_DEELERR_NOT);
            chkErrListPo.setTransDate(payFlowListPo.getSysDate());
            chkErrListPo.setTransTime(payFlowListPo.getTransTime());
            chkErrListPo.setSysSeq(payFlowListPo.getTransSubSeq());

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

            daoService.insert(chkErrListPo);//
        }

    }

}
