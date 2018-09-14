package com.upay.batch.stepservice.clearing.calculateRouteFee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
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
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.page.QueryResult;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.pay.PayFlowListPo;


/**
 * 计算昨日成功流水的手续费
 * Created by LB on 2017/8/4.
 */
public class CalculateRouteFee extends AbstractStepExecutor<Object, PayFlowListPo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateRouteFee.class);

    @Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
    	HashMap<String,Object> parm=new HashMap<String,Object>();
    	parm.put("startDate", DateUtil.format(batchParams.getPreDate(), "yyyy-MM-dd"));
    	parm.put("endDate", DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
		QueryResult<String> selectQueryResult = daoService.selectQueryResult(PayFlowListPo.class.getName()+".selectSuccFlowList",parm, 0, 0);
		long totalrecord = selectQueryResult.getTotalrecord();
		return (int)totalrecord;
	}
    
    @Override
	public List<PayFlowListPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
    	HashMap<String,Object> parm=new HashMap<String,Object>();
    	parm.put("startDate", DateUtil.format(batchParams.getPreDate(), "yyyy-MM-dd"));
    	parm.put("endDate", DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
    	QueryResult<PayFlowListPo> queryResult = daoService.selectQueryResult(PayFlowListPo.class.getName()+".selectSuccFlowList",parm, offset, pageSize);
    	return queryResult.getResultlist();
	}

    @Override
    public void execute(BatchParams batchParams, int index, PayFlowListPo flow,Object data)
            throws BatchException {
        LOGGER.info("-------------计算昨日成功流水手续费开始"+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
//        BigDecimal transAmt = flow.getTransAmt();
//        String routeCode = flow.getRouteCode();
        PayFlowListPo setPayFlow=new PayFlowListPo();
        PayFlowListPo wherePayFlow=new PayFlowListPo();
        wherePayFlow.setId(flow.getId());
        
        BigDecimal feeAmt=BigDecimal.ZERO;
        String routeCode=flow.getRouteCode();
        String transCode=flow.getRemark3();
        BigDecimal transAmt=flow.getTransAmt();
        try {
			feeAmt=getFeeAmt(routeCode,transCode,transAmt);
		} catch (Exception e) {
			feeAmt=BigDecimal.ZERO;
			e.printStackTrace();
		}
        LOGGER.info("流水号："+flow.getTransSubSeq()+"   通道代码："+routeCode+"   交易代码："+transCode+"   交易金额："+transAmt+"    通道手续费："+feeAmt);
        setPayFlow.setFeeAmt(feeAmt);
        
        daoService.update(setPayFlow,wherePayFlow);
        LOGGER.info("-------------计算昨日成功流水手续费结束"+DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss:sss"));
    }
    
    
    public BigDecimal getFeeAmt(String routeCode,String transCode,BigDecimal transAmt) throws Exception {
    	BigDecimal feeAmt=BigDecimal.ZERO;
    	LOGGER.debug("根据资金通道代码获取手续费。。。。。。。");
        FeeGetPo feeGetPo = new FeeGetPo();
        feeGetPo.setRouteCode(routeCode);
        feeGetPo.setTransCode(transCode);
        feeGetPo.setRouteFeeFlag(DataBaseConstants_FEE.ROUTE_FEE_YES); //通道手续费
        //获取交易码下的手续费收取方法集合
        feeGetPo = daoService.selectOne(feeGetPo);
        if (null != feeGetPo) {
            String feeCode = feeGetPo.getFeeCode();
            feeAmt=calculateFeeAmt(transAmt, feeCode);
        } else {
        	LOGGER.debug("还未设置交易代码["+transCode+"]资金通道["+routeCode+"]手续费！");
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
    private BigDecimal calculateFeeAmt(BigDecimal transAmt, String feeCode) {
        BigDecimal routeFeeAmt = new BigDecimal(0);
        FeeKindPo feeKindPo = new FeeKindPo();
        feeKindPo.setFeeCode(feeCode);
        feeKindPo = daoService.selectOne(feeKindPo);
        if (null == feeKindPo) {
        	LOGGER.debug("该资金通道下还未设置手续费计算参数");
            return new BigDecimal(0);
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
