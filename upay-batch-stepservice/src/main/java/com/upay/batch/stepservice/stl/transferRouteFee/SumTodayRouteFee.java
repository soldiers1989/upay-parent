package com.upay.batch.stepservice.stl.transferRouteFee;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.page.QueryResult;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.pay.PayCardbinInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * 统计当日对账后成功的各资金通道的手续费金额
 * Created by LB on 2017/8/7.
 */
public class SumTodayRouteFee extends AbstractStepExecutor<Object, ChkThirdDetailPo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SumTodayRouteFee.class);

    @Override
    public void execute(BatchParams batchParams, int index, ChkThirdDetailPo thirdDetail,Object data)
            throws BatchException {
    	//查询对账成功的各渠道的手续费统计
    	String dateForMat=DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD;
    	Map<String, Object> parm = new HashMap<String, Object>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    	parm.put("startDate", DateUtil.format(batchParams.getTranDate(),dateForMat));
    	parm.put("endDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1),dateForMat));
		List<Map<String,Object>> routeFeeList = daoService.selectList(ChkThirdDetailPo.class.getName()+".sumFeeAmtByOrgCode",parm);
		if(null==routeFeeList||routeFeeList.size()==0){
			LOGGER.info( DateUtil.format(batchParams.getTranDate(),dateForMat)+"    没有支出的资金通道手续费");
			return;
		}
		for(Map<String,Object> routeFeeMap:routeFeeList){
			String reouteCode=(String)routeFeeMap.get("ORG_CODE");
			BigDecimal routeFee=(BigDecimal)routeFeeMap.get("ROUTE_FEE_AMT");
			
			//检查当日是否重复导入
    		HashMap<String,Object> queryRepeatRecParm=new HashMap<String,Object>();
			queryRepeatRecParm.put("flag", DataBaseConstants_PAY.ROUTE_FEE_FLAG);
			queryRepeatRecParm.put("stlDate", batchParams.getTranDate());
			queryRepeatRecParm.put("routeCode", reouteCode);
			StlRouteFeeBookPo stlRouteFeeBookPo = daoService.selectOne(StlRouteFeeBookPo.class.getName()+".queryRepeatRecord",queryRepeatRecParm);
			if(null!=stlRouteFeeBookPo){
				LOGGER.info(DateUtil.format(batchParams.getTranDate(),dateForMat)+"  数据重复导入");
				continue;
			}else{
				PayRouteInfoPo routeInfo=new PayRouteInfoPo();
				routeInfo.setRouteCode(reouteCode);
				routeInfo = daoService.selectOne(routeInfo);
				if(null!=routeInfo){
					StlRouteFeeBookPo routeFeeBook=new StlRouteFeeBookPo();
					routeFeeBook.setFlag(DataBaseConstants_PAY.ROUTE_FEE_FLAG);//资金通道手续费标志
					routeFeeBook.setTransAmt(routeFee);
					routeFeeBook.setPayerAcctNo(routeInfo.getFeeExpenseAcctNo());//付款为手续费支出户
					routeFeeBook.setPayerName(routeInfo.getRouteName());
					routeFeeBook.setPayerCardType(DataBaseConstans_ACC.ACCT_TYPE_INTERNAL_ACCT);//付款方为内部户
					
					routeFeeBook.setPayeeAcctNo(routeInfo.getTransAcctNo());//资金通道往来户
					routeFeeBook.setPayeeName(routeInfo.getRouteName());
					routeFeeBook.setPayeeCardType(DataBaseConstans_ACC.ACCT_TYPE_INTERNAL_ACCT);//付款方为内部户
					
					routeFeeBook.setStlBatchNo(batchParams.getBatchNo());
					routeFeeBook.setStlDate(batchParams.getTranDate());
					routeFeeBook.setRouteCode(reouteCode);
					daoService.insert(routeFeeBook);
				}
			}
			
		}
    }
}
