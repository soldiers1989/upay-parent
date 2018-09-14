package com.upay.batch.stepservice.chk.mer;

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
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkMerListPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 生成商户对账明细
 * 
 * 
 * 
 */
public class GenMerChkList extends AbstractStepExecutor<Object, PayOrderListPo> {

    private static final Logger logger = LoggerFactory.getLogger(GenMerChkList.class);


    /**
     * 返回一个Object对象，用于使批量执行updateObject方法
     * 
     * @param batchParams
     * @return
     * @throws BatchException
     */
    // @Override
    // public List<Object> getObjectList(BatchParams batchParams) throws
    // BatchException {
    // List<Object> objList = new ArrayList<Object>();
    // objList.add(new Object());
    // return objList;
    // }

    /**
     * 查询商户需要对账的记录数
     * 
     * @param batchParams
     *            批量参数
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
    	
    	//判断是否是指定日期执行
    	logger.debug("指定日期:"+batchParams.getParameter().containsKey("tranDate"));
    	if(batchParams.getParameter().containsKey("tranDate")){
    		Date tranDate = (Date)batchParams.getParameter().get("tranDate");
    		Date preDate = (Date)batchParams.getParameter().get("preDate");
    		batchParams.setTranDate(tranDate);
    		batchParams.setPreDate(preDate);
    	}
    	logger.debug("执行的日期是："+DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderDate", batchParams.getTranDate()); // 查询前一天的订单
        paramMap.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        paramMap.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y); // 订单状态为成功的
        paramMap.put("orderStatTwo", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP); // 订单状态为的确认收货的
        paramMap.put("orderStatThree", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC); // 订单状态为退款成功的
        paramMap.put("orderStatThour", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO); // 订单状态为部分退款的
        paramMap.put("orderStatFive", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL); // 订单状态为全额退款的
        
        paramMap.put("chkFlag", DataBaseConstants_BATCH.T_CHK_FLAG_NO); // 0 未对账
        paramMap.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        paramMap.put("transTypeTwo", CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        Integer count = daoService.count(PayOrderListPo.class.getName() + ".countOne", paramMap);
        logger.debug("商户对账单明细生成，有{}订单记录需要处理", new Object[] { count });
        return count;
    }


    /**
     * 分页查询商户对账订单
     * 
     * @param batchParams
     *            批量参数
     * @param offset
     *            开始记录索引
     * @param pageSize
     *            每页查询最大记录数
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
    	
    	logger.debug("执行的日期是："+DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderDate", batchParams.getTranDate()); // 查询前一天的订单
        paramMap.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        paramMap.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y); // 订单状态为成功的
        paramMap.put("orderStatTwo", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP); // 订单状态为的确认收货的
        paramMap.put("orderStatThree", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC); // 订单状态为退款成功的
        paramMap.put("orderStatThour", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO); // 订单状态为部分退款的
        paramMap.put("orderStatFive", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL); // 订单状态为全额退款的
        
        paramMap.put("chkFlag", DataBaseConstants_BATCH.T_CHK_FLAG_NO); // 0 未对账
        paramMap.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        paramMap.put("transTypeTwo", CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        // 排序
        List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
        Map<String, String> orderByMerNo = new HashMap<String, String>();
        orderByMerNo.put("columnName", "MER_NO");
        orderByMerNo.put("sort", "asc");
        orderByList.add(orderByMerNo);
        Map<String, String> orderByOrderTime = new HashMap<String, String>();
        orderByOrderTime.put("columnName", "ORDER_TIME");
        orderByOrderTime.put("sort", "asc");
        orderByList.add(orderByOrderTime);
        paramMap.put("orderBy", orderByList);

        return daoService.selectList(PayOrderListPo.class.getName() + ".selectListOne", paramMap, offset,
            pageSize);
    }


    /**
     * 登记商户对账数据
     * 
     * @param batchParams
     *            批量参数
     * @param index
     *            记录索引
     * @param payOrder
     *            订单信息
     * @param object
     * @throws BatchException
     */
    @Override
    @SuppressWarnings("all")
    public void execute(BatchParams batchParams, int index, PayOrderListPo payOrder, Object object)
            throws BatchException {
    	logger.debug("执行的日期是："+DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
        // 续跑处理
        if (batchParams.isContinue()) {
            ChkMerListPo oldChkMerList = new ChkMerListPo();
            oldChkMerList.setMerNo(payOrder.getMerNo());
            oldChkMerList.setMerOrder(payOrder.getOuterOrderNo());
            oldChkMerList = daoService.selectOne(oldChkMerList);
            // 已有商户对账明细数据，清除
            if (oldChkMerList != null) {
                ChkMerListPo delChkMerList = new ChkMerListPo();
                delChkMerList.setMerNo(payOrder.getMerNo());
                delChkMerList.setMerOrder(payOrder.getOuterOrderNo());
                daoService.delete(delChkMerList);
                logger.debug("续跑生成商户[{}]对账明细，清除已有数据，商户订单号[{}]",
                    new Object[] { payOrder.getMerNo(), payOrder.getOuterOrderNo() });
            }
        }
        if(payOrder.getMerNo()==null){
        	return;
        }
        //如果为退款，该退款的原交易订单为01支付的才会生成给商户
        if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(payOrder.getTransType())){
        	Map<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
            paramMap.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y); // 订单状态为成功的
            paramMap.put("orderStatTwo", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP); // 订单状态为的确认收货的
            paramMap.put("orderStatThree", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC); // 订单状态为退款成功的
            paramMap.put("orderStatThour", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO); // 订单状态为部分退款的
            paramMap.put("orderStatFive", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL); // 订单状态为全额退款的
            paramMap.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
            paramMap.put("oriOrderNo",payOrder.getOriOrderNo() );
            List<PayOrderListPo> selectOne = daoService.selectList(PayOrderListPo.class.getName() + ".selectListOneForPayType", paramMap);
        	if(selectOne.size()>0){
        		if(!CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(selectOne.get(0).getTransType())){
        			return;
        		}
        	}
        }
        // 查询资金通道支付流水流水
        Map<String, Object> payFlowParamMap = new HashMap<String, Object>();
        payFlowParamMap.put("orderNo", payOrder.getOrderNo());
        payFlowParamMap.put("merNo", payOrder.getMerNo());
        payFlowParamMap.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        // 通道代码和流水排序
        List<Map<String, String>> orderBy = new ArrayList<Map<String, String>>();
        Map<String, String> orderByRoute = new HashMap<String, String>();
        orderByRoute.put("columnName", "ROUTE_CODE");
        orderByRoute.put("sort", "asc");
        orderBy.add(orderByRoute);
        Map<String, String> orderByTransTime = new HashMap<String, String>();
        orderByTransTime.put("columnName", "TRANS_TIME");
        orderByTransTime.put("sort", "asc");
        orderBy.add(orderByTransTime);
        Map<String, String> orderByTranSeq = new HashMap<String, String>();
        orderByTranSeq.put("columnName", "TRANS_SUB_SEQ");
        orderByTranSeq.put("sort", "asc");
        orderBy.add(orderByTranSeq);
        payFlowParamMap.put("orderBy", orderBy);
        List<PayFlowListPo> payFlowList =
                daoService.selectList(PayFlowListPo.class.getName() + ".selectList", payFlowParamMap);
        
        //如果一条成功的流水没有查到则返回，不返回下面的代码有问题
        if(payFlowList==null||payFlowList.size()==0){
        	logger.debug("订单："+payOrder.getOrderNo()+"  未找到成功流水，不会处理");
        	return;
        }
        
        
        // 确定核心支付流水和其它通道（中金或银联）支付流水，核心可能会有多条支付流水，只取第一条支付流水
        PayFlowListPo corePayFlow = null;
        PayFlowListPo otherPayFlow = null;

        // 多于2笔流水暂不做处理（批量转账为一笔订单对应多笔流水）
       
        for (PayFlowListPo payFlow : payFlowList) {
            if (DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(payFlow.getRouteCode()) && corePayFlow == null) {
                corePayFlow = payFlow;
            }
            if (!DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(payFlow.getRouteCode())) {
                otherPayFlow = payFlow;
            }
        }

        ChkMerListPo chkMer = new ChkMerListPo();
        chkMer.setChkDate(batchParams.getTranDate());
        chkMer.setMerFlag(DateBaseConstants_MER.MER_TYPE_SPECIAL_BUSINESS);
        chkMer.setMerNo(payOrder.getMerNo());
        chkMer.setRecNum(index);
        chkMer.setTxnDate(payOrder.getOrderDate());
        chkMer.setTxnTime(payOrder.getOrderTime());
        chkMer.setOrderNo(payOrder.getOrderNo());
        chkMer.setMerOrder(payOrder.getOuterOrderNo());
        chkMer.setTransType(payOrder.getTransType());
        chkMer.setMerDate(payOrder.getMerDate());
        chkMer.setChkFile(
            payOrder.getMerNo() + "_CHKFILE_" + DateUtil.format(batchParams.getPreDate(), "yyyyMMdd"));
        String transType = payOrder.getTransType();
        // 支付、充值付款方为用户账号
        if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)
                || CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(transType)) {
            if (otherPayFlow != null) {
                chkMer.setAcctNo(otherPayFlow.getPayerAcctNo());
                chkMer.setCardmerCode(otherPayFlow.getPayerBankNo());
                chkMer.setRelAcct(otherPayFlow.getPayeeAcctNo());
            } else {
                chkMer.setAcctNo(corePayFlow.getPayerAcctNo());
                chkMer.setCardmerCode(corePayFlow.getPayerBankNo());
                chkMer.setRelAcct(corePayFlow.getPayeeAcctNo());
            }
        } else if (CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(transType)
                || CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)) { // 提现、退款收款方为用户账号
            if (otherPayFlow != null) {
                chkMer.setAcctNo(otherPayFlow.getPayeeAcctNo());
                chkMer.setCardmerCode(otherPayFlow.getPayeeBankNo());
                chkMer.setRelAcct(otherPayFlow.getPayerAcctNo());
            } else {
                chkMer.setAcctNo(corePayFlow.getPayeeAcctNo());
                chkMer.setCardmerCode(corePayFlow.getPayeeBankNo());
                chkMer.setRelAcct(corePayFlow.getPayerAcctNo());
            }
        } else if (CommonConstants_GNR.SYS_TRANS_TYPE_TRA.equals(transType)) { // 商户转账订单，目前只支持行内转账
            chkMer.setAcctNo(corePayFlow.getPayerAcctNo());
            chkMer.setRelAcct(corePayFlow.getPayeeAcctNo());
        }
        chkMer.setTxnAmt(payOrder.getTransAmt());
        // 订单为退货交易设置原交易日期和原订单号
        if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)) {
            chkMer.setOriOrderNo(payOrder.getOriOrderNo());
            chkMer.setOriTxnDate(payOrder.getOriDate());
        }
        // 生成商户对账明细时设置对账标志为成功
        chkMer.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
        chkMer.setChkBatchNo(batchParams.getBatchNo());
        chkMer.setOrderStat(payOrder.getOrderStat());
        chkMer.setSecMerNo(payOrder.getSecMerNo());
        chkMer.setMerFeeAmt(payOrder.getMerFeeAmt());
        chkMer.setExtensionParty(payOrder.getExtensionParty());
        chkMer.setPromoterDeptNo(payOrder.getPromoterDeptNo());
        
        // 保存对账明细
        daoService.insert(chkMer);

        logger.debug("生成订单[{}]商户对账明细完成", payOrder.getOrderNo());
    }


    /**
     * 批量修改订单状态为对账完成
     * 
     * @param batchParams
     * @param object
     */
    @Override
    public void updateObject(BatchParams batchParams, Object object) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
        paramMap.put("chkDate", batchParams.getTranDate());
        paramMap.put("chkBatchNo", batchParams.getBatchNo());
        paramMap.put("chkFlag", DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
        paramMap.put("lastUpdateTime",new Date());
        
        paramMap.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        paramMap.put("orderDate", batchParams.getTranDate());
        paramMap.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        paramMap.put("orderStatTwo", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP); // 订单状态为的确认收货的
        paramMap.put("orderStatThree", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC); // 订单状态为退款成功的
        paramMap.put("orderStatThour", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO); // 订单状态为部分退款的
        paramMap.put("orderStatFive", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL); // 订单状态为全额退款的
        paramMap.put("chkFlagForFind", DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        paramMap.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        paramMap.put("transTypeTwo", CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        
        daoService.selectList(PayOrderListPo.class.getName() + ".updatePayOrderForChk", paramMap);
        logger.debug("生成[{}]日对账明细完成，更新所有订单对账成功", DateUtil.format(batchParams.getPreDate(), "yyyyMMdd"));
    }
}
