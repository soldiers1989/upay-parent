package com.upay.batch.stepservice.clearing.calculateSecMerFee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.page.QueryResult;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 计算昨日成功订单的二级商户的手续费
 * Created by LB on 2017/8/7.
 */
public class CalculateSecMerFee extends AbstractStepExecutor<Object, PayOrderListPo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateSecMerFee.class);

    @Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
    	Map<String, Object> parm = new HashMap<String, Object>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    	parm.put("sysDate", batchParams.getTranDate());
    	parm.put("orderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
    	parm.put("orderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
    	parm.put("orderStat3", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
    	parm.put("orderStat4", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
    	parm.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        parm.put("oriClearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        parm.put("startDate", DateUtil.format(batchParams.getPreDate(), "yyyy-MM-dd"));
    	parm.put("endDate", DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
		QueryResult<String> selectQueryResult = daoService.selectQueryResult(PayOrderListPo.class.getName()+".getPaySuccOrderList",parm, 0, 0);
		long totalrecord = selectQueryResult.getTotalrecord();
		return (int)totalrecord;
	}
    
    @Override
	public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
    	HashMap<String,Object> parm=new HashMap<String,Object>();
    	parm.put("sysDate", batchParams.getTranDate());
    	parm.put("orderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
    	parm.put("orderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
        parm.put("orderStat3", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        parm.put("orderStat4", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        parm.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        parm.put("oriClearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        parm.put("startDate", DateUtil.format(batchParams.getPreDate(), "yyyy-MM-dd"));
    	parm.put("endDate", DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
    	QueryResult<PayOrderListPo> queryResult = daoService.selectQueryResult(PayOrderListPo.class.getName()+".getPaySuccOrderList",parm, offset, pageSize);
    	return queryResult.getResultlist();
	}

    @Override
    public void execute(BatchParams batchParams, int index, PayOrderListPo order,Object data)
            throws BatchException {
//        BigDecimal transAmt = flow.getTransAmt();
//        String routeCode = flow.getRouteCode();
        PayOrderListPo setPayOrder=new PayOrderListPo();
        PayOrderListPo wherePayOrder=new PayOrderListPo();
        wherePayOrder.setId(order.getId());
        
        BigDecimal feeAmt=BigDecimal.ZERO;
        BigDecimal transAmt=order.getTransAmt();
        try {
			feeAmt=getFeeAmt(order.getMerNo(),order.getSecMerNo(),order.getTransCode(),transAmt,batchParams.getTranDate());
		} catch (Exception e) {
			feeAmt=BigDecimal.ZERO;
			e.printStackTrace();
		}
        
        setPayOrder.setSecMerFeeAmt(feeAmt);
        daoService.update(setPayOrder,wherePayOrder);
        LOGGER.info("-------------计算昨日成功订单号："+order.getOrderNo()+"    二级商户手续费："+feeAmt);
    }
    
    
    public BigDecimal getFeeAmt(String merNo,String secMerNo,String transCode,BigDecimal transAmt,Date transDate) throws Exception {
    	BigDecimal feeAmt=BigDecimal.ZERO;
    	LOGGER.debug("根据一级和二级商户号计算 二级商户手续费。。。。。。。");
        FeeGetPo feeGetPo = new FeeGetPo();
//        feeGetPo.setTransCode(transCode);
        feeGetPo.setMerNo(merNo);
        feeGetPo.setSecMerNo(secMerNo);
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("merNo", merNo);
        map.put("secMerNo", secMerNo);
        map.put("now", transDate);
//        map.put("transCode", transCode);
        List<Map<String,Object>> feeList = daoService.selectList(FeeGetPo.class.getName().concat(".getOneActive"), map);
        if(feeList!=null&&feeList.size()>1){
        	throw new BatchException("一级商户号："+merNo+"  二级商户号："+secMerNo+"    手续费配置重复,请检查!");
        }
        Map<String,Object> feeInfo = feeList.get(0);
        
        if (null != feeInfo) {
            feeAmt=calculateFeeAmt(transAmt, (String)feeInfo.get("feeCode"),merNo);
        } else {
//        	LOGGER.debug("还未设置交易代码["+transCode+"]资金通道["+routeCode+"]手续费！");
        }
        return feeAmt;
    }

    /**
     * 计算手续费
     *
     * @param transAmt
     * @param feeCode
     * @return
     */
    private BigDecimal calculateFeeAmt(BigDecimal transAmt, String feeCode,String merNo) {
    	if(StringUtils.isBlank(feeCode)){
    		throw new BatchException("计算二级商户手续费收费代码不能为空");
    	}
        BigDecimal routeFeeAmt = BigDecimal.ZERO;
        FeeKindPo feeKindPo = new FeeKindPo();
        feeKindPo.setFeeCode(feeCode);
        feeKindPo.setMemo(merNo);
        feeKindPo = daoService.selectOne(feeKindPo);
        if (null == feeKindPo) {
        	LOGGER.debug("商户："+merNo+"   还未设置手续费计算参数");
            return routeFeeAmt;
        } else {
            String feeMode = feeKindPo.getFeeMode();
            if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED.equals(feeMode)) { //单笔固定
                routeFeeAmt = feeKindPo.getFixFee();
            } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL.equals(feeMode)) { //单笔固定加交易金额比例
                BigDecimal rationFee = feeKindPo.getRationFee().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
                routeFeeAmt = feeKindPo.getFixFee().add(transAmt.multiply(rationFee));
            } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION.equals(feeMode)) { //按交易金额比例
                //先将取出的费率除100 在进行手续费计算
                BigDecimal rationFee = feeKindPo.getRationFee().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
                routeFeeAmt = transAmt.multiply(rationFee);
            }
        }
        //计算完毕后判断是否超过上下线
        if (null != feeKindPo.getLowFee()){
            int result = routeFeeAmt.compareTo(feeKindPo.getLowFee());
            if (result < 0) {
                routeFeeAmt = feeKindPo.getLowFee();
            }
        }
        if (null != feeKindPo.getHighFee()) {
            int result = routeFeeAmt.compareTo(feeKindPo.getHighFee());
            if (result > 0) {
                routeFeeAmt = feeKindPo.getHighFee();
            }
        }
        return routeFeeAmt;
    }
}
