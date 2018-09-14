package com.upay.batch.stepservice.chk.unionmer;

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
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 平台未对账数据对账
 * 
 * @author 张立朋
 * @version v1.0
 * @CreateDate: 2015-06-12
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */

public class ChkByDetailUnionStepToMer extends AbstractStepExecutor<ChkInfoPo, PayFlowListPo> {
    private final static Logger logger = LoggerFactory.getLogger(ChkByDetailUnionStepToMer.class);
    private String batchNo;// 存储批次号




    @Override
    public List<ChkInfoPo> getObjectList(BatchParams batchParams) throws BatchException {
        // 登记对账批次信息
        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        List<ChkInfoPo> listChkInfo = daoService.selectList(chkInfo);
        return listChkInfo;
    }


    @Override
    public List<PayFlowListPo> getDataList(BatchParams batchParams, int offset, int pageSize, ChkInfoPo object)
            throws BatchException {

        batchNo = batchParams.getBatchNo();
        // 获取平台对账流水表(T_PAY_FLOW_LIST)数据
        PayFlowListPo payFlowListPo = new PayFlowListPo();
        payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        payFlowListPo.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        payFlowListPo.setRouteDate(object.getChkDate());
        payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
        List<PayFlowListPo> payFlowList = daoService.selectList(payFlowListPo);
        logger.info("银联对账----获取平台对账流水表(T_PAY_FLOW_LIST)数据流水数据结束--------");
        return payFlowList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, PayFlowListPo payFlowList, ChkInfoPo object)
            throws BatchException {
        logger.info("银联对账----执行批量操作第" + index + "次--------");
        if (payFlowList == null) {
            return;
        }
        // 获取源表数据
        //PayFlowListPo payFlowList = data;
        PayOrderListPo payOrderList = new PayOrderListPo();
        payOrderList.setOrderNo(payFlowList.getOrderNo());
        payOrderList = daoService.selectOne(payOrderList);

        logger.info("银联对账----银联失败，本系统成功");

        // 插入差错信息表（T_CHK_ERR_LIST）
        insertChkErrListInfo(payFlowList, batchParams, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
        logger.info("银联对账----插入差错信息表（T_CHK_ERR_LIST）結束");
        updatePayFlowList(payFlowList, DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
        logger.info("银联对账----更新平台流水表(T_PAY_FLOW_LIST)結束");
        payOrderList.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
        daoService.update(payOrderList);
        logger.info("银联对账----更新平台订单表(T_PAY_ORDER_LIST)結束");
    }


    @Override
    public void updateObject(BatchParams batchParams, ChkInfoPo object) {
        // TODO Auto-generated method stub
        String batchNo = batchParams.getBatchNo();
        ChkInfoPo chkInfoPo = new ChkInfoPo();
        chkInfoPo.setBatchNo(batchNo);
        ChkInfoPo updatePo = new ChkInfoPo();
        updatePo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_SUCCESS);
        daoService.update(updatePo, chkInfoPo);
        super.updateObject(batchParams, object);
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
     * @param chkSeq
     * @param routeCode
     * @param chkDate
     */
    public PayFlowListPo queryPmtFlowListInfo(String chkSeq, String routeCode, Date chkDate) {
        PayFlowListPo pmtFlowListPo = new PayFlowListPo();
        pmtFlowListPo.setChkDate(chkDate);
        pmtFlowListPo.setTransSubSeq(chkSeq);
        pmtFlowListPo.setRouteCode(routeCode);
        pmtFlowListPo = daoService.selectOne(pmtFlowListPo);
        return pmtFlowListPo;
    }


    /***
     * *更新核心对账明细表(T_CHK_HOST_DETAIL)
     * 
     * @param chkThirdDetailPo
     * @param chkStat
     */
    public void updateChkHostDetailInfo(ChkHostDetailPo chkHostDetailPo, String chkStat) {
        chkHostDetailPo.setChkFlag(chkStat);// 对账标志
        daoService.update(chkHostDetailPo);

    }


    /***
     * *插入差错信息表（T_CHK_ERR_LIST）
     * 
     * @param ChkDetailPo
     * @param ChkErrDealOpinionPo
     */
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
