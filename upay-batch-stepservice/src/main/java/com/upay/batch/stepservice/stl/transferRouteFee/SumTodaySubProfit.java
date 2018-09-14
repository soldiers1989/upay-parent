package com.upay.batch.stepservice.stl.transferRouteFee;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.po.BasePo;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.fee.FeeAssDetailPo;
import com.upay.dao.po.fee.FeeAssInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;



/**
 * 统计当日成功订单的各方分润汇总
 * Created by LB on 2017/8/7.
 */
public class SumTodaySubProfit extends AbstractStepExecutor<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SumTodaySubProfit.class);

    @Override
    public void execute(BatchParams batchParams, int index, Object payRoute,Object data)
            throws BatchException {
    	LOGGER.info("统计资金通道：分润费用开始");
    	PayRouteInfoPo routeInfo=new PayRouteInfoPo();
		routeInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		routeInfo = daoService.selectOne(routeInfo);
    	
		
    	String dateForMat=DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD;
    	String transDate = DateUtil.format(batchParams.getTranDate(),dateForMat);
    	
    	Map<String, Object> parm = new HashMap<String, Object>();
    	parm.put("txnDate", transDate);
		List<Map<String,Object>> routeFeeList = daoService.selectList(FeeAssDetailPo.class.getName()+".sumAssAmtByOrgCode",parm);
    	for(Map<String,Object> assSumMap:routeFeeList){
    		BigDecimal assAmt = (BigDecimal) assSumMap.get("ASS_AMT");
    		String assId = (String) assSumMap.get("ASS_ID");
    		String routeCode = (String) assSumMap.get("ROUTE_CODE");
    		
    		//检查当日是否重复导入
    		HashMap<String,Object> queryRepeatRecParm=new HashMap<String,Object>();
			queryRepeatRecParm.put("flag", DataBaseConstants_PAY.ASS_AMT_FLAG);
			queryRepeatRecParm.put("stlDate", batchParams.getTranDate());
			queryRepeatRecParm.put("assId", assId);
			queryRepeatRecParm.put("routeCode", routeCode);
			StlRouteFeeBookPo stlRouteFeeBookPo = daoService.selectOne(StlRouteFeeBookPo.class.getName()+".queryRepeatRecord",queryRepeatRecParm);
			if(null!=stlRouteFeeBookPo){
				LOGGER.info(transDate+"  数据重复导入");
				continue;
			}else{
				if(StringUtils.isNotBlank(assId)){
	    			FeeAssInfoPo feeAssInfo=new FeeAssInfoPo();
	    			feeAssInfo.setAssId(assId);
	    			feeAssInfo=daoService.selectOne(feeAssInfo);
	        		if(null!=feeAssInfo){
	        			StlRouteFeeBookPo routeFeeBook=new StlRouteFeeBookPo();
	            		routeFeeBook.setFlag(DataBaseConstants_PAY.ASS_AMT_FLAG);//分润费用标志
	            		routeFeeBook.setAssId(assId);//分润方
	            		routeFeeBook.setTransAmt(assAmt);
	            		routeFeeBook.setPayerAcctNo(routeInfo.getClrAcctNo());//付款为核心待清算账户
	            		routeFeeBook.setPayerName(routeInfo.getRouteName());
	            		routeFeeBook.setPayerCardType(DataBaseConstans_ACC.ACCT_TYPE_INTERNAL_ACCT);//付款方为内部户
	            		
	            		if(DataBaseConstants_PAY.ASS_ROUTE.equals(assId)){//如果分润方为资金通道方时，收款方为资金通道手续费收入账户
	            			PayRouteInfoPo route=new PayRouteInfoPo();
	            			route.setRouteCode(routeCode);
	            			route = daoService.selectOne(routeInfo);
	            			
	            			routeFeeBook.setRouteCode(routeCode);
	            			routeFeeBook.setPayeeAcctNo(route.getFeeIncomeAcctNo());//资金通道手续费收入户
		            		routeFeeBook.setPayeeName(route.getRouteName());
		            		routeFeeBook.setPayeeCardType(DataBaseConstans_ACC.ACCT_TYPE_INTERNAL_ACCT);//付款方为内部户
	            		}else{
	            			//否则，收款方为分润方的收入账户
	            			routeFeeBook.setPayeeAcctNo(feeAssInfo.getAssAcctNo());//分润方收入户
	                		routeFeeBook.setPayeeName(feeAssInfo.getAssAcctName());
	                		routeFeeBook.setPayeeCardType(feeAssInfo.getAssAcctType());//付款方为内部户
	            		}
	            		
	            		routeFeeBook.setStlBatchNo(batchParams.getBatchNo());
	            		routeFeeBook.setStlDate(batchParams.getTranDate());
	            		daoService.insert(routeFeeBook);
	        		}else{
	        			new BatchException("分润方："+assId+"  信息未配置,请检查!");
	        		}
	        		
	    		}
			}
    	}
    	if(routeFeeList.size()>0){
    		//更新分润结算标识，日期和批次号
        	Map<String, Object> sqlStlParams = new HashMap<String, Object>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
            sqlStlParams.put("assId", DataBaseConstants_PAY.ASS_ROUTE);
            sqlStlParams.put("txnDate", transDate);
            
            sqlStlParams.put("stlFlag", DataBaseConstants_BATCH.T_STL_FLAG_END);
            sqlStlParams.put("stlDate", transDate);
            sqlStlParams.put("stlBatchNo", batchParams.getBatchNo());
    		
        	int i = daoService.update(FeeAssDetailPo.class.getName() + ".updateStlFlagByAssID", sqlStlParams);
        	int j = daoService.update(FeeAssDetailPo.class.getName() + ".updateStlFlagByAssID1", sqlStlParams);
        	LOGGER.info(transDate+"-------------分清清算结束--登记", i+j+" 条");
    	}
    }
}
