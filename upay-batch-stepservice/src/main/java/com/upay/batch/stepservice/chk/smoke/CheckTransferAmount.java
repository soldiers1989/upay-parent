/**
 * 
 */
package com.upay.batch.stepservice.chk.smoke;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;

/**
 * 检查转账金额是否大于账户余额
 * 
 * @author lb
 * 
 */
public class CheckTransferAmount extends AbstractStepExecutor<Object, Object> {
	private final static Logger logger = LoggerFactory.getLogger(CheckTransferAmount.class);
	@Resource
	IDaoService daoService;

	private BatchCommon batchCommon;
	
//	@Override
//	public int getTotalResult(BatchParams batchParams, Object object)
//			throws BatchException {
//		HashMap<String,Object> whereMap=new HashMap<String,Object>();
//		whereMap.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
//		whereMap.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), "yyyy-MM-dd"));
//		
//		int queryResult = (int)daoService.selectQueryResult(SmokeStlDetailPo.class.getName() + ".totalAmountByMerNo",whereMap,0,0).getTotalrecord();
//		logger.info(queryResult+"=======================");
//		return queryResult;
//	}
//
//	@Override
//	public List getDataList(BatchParams batchParams, int offset,
//			int pageSize, Object object) throws BatchException {
//		HashMap<String,Object> whereMap=new HashMap<String,Object>();
//		whereMap.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), "yyyy-MM-dd"));
//		whereMap.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), "yyyy-MM-dd"));
//		
//		QueryResult<Object> queryList = daoService.selectQueryResult(SmokeStlDetailPo.class.getName() + ".totalAmountByMerNo",whereMap,offset,pageSize);
//		logger.info("======================"+queryList.getResultlist().size());
//		return queryList.getResultlist();
//	}
	
	@Override
	public void execute(BatchParams batchParams, int index, Object data,Object object) throws BatchException {
		logger.debug("检查本次商户转账金额是否大于账户余额====================开始======================");
		HashMap<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		whereMap.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		
		List<Object> selectList = daoService.selectList(SmokeStlDetailPo.class.getName() + ".totalAmountByMerNo",whereMap);
		for(Object objectMap:selectList){
			Map<String,Object> merMap=(Map<String,Object>)objectMap;
			String merNo = (String)merMap.get("PAYER_MER_NO");
			BigDecimal transAmt = merMap.get("TRANS_AMT")==null?BigDecimal.ZERO:new BigDecimal(merMap.get("TRANS_AMT").toString());
			logger.debug("商户号："+merNo+"      本次转账金额为："+transAmt);
			
			MerAcctInfoPo merAcctInfo=new MerAcctInfoPo();
			merAcctInfo.setMerNo(merNo);
			merAcctInfo=daoService.selectOne(merAcctInfo);
			if(null==merAcctInfo){
				throw new BatchException("商户 : "+merNo+"   没有结算账户。请检查!!!");
			}
			
			//因烟草的内部户是余额双向的，可为负数，所以需要检查内部户的余额
			if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(merAcctInfo.getStlAcctType())){
				Map<String, Object> bodyMap=new HashMap<String,Object>();
				bodyMap.put("merNo", merNo);
				Message postPAY1007 = batchCommon.postPAY1007(bodyMap);
				HashMap<String,Object> bodys = (HashMap<String,Object>)postPAY1007.getTarget().getBodys();
				
				String code = postPAY1007.getFault().getCode();
				String msg = postPAY1007.getFault().getMsg();
				logger.debug("调用转账接口：返回代码="+code+"      返回信息="+msg);
				
				if(CommonConstants_GNR.SERVICE_STAT.equals(code)){//查询内部账户余额成功
					String acctBal=(String)bodys.get("acctBalance");
					BigDecimal merAcctBal=BigDecimal.ZERO;
					if(null!=acctBal){
						merAcctBal=new BigDecimal(acctBal);
					}
//					merAcctBal=new BigDecimal("-2");
					logger.debug("商户："+merNo +"    结算账户的余额为 : "+merAcctBal);
					if(merAcctBal==null||merAcctBal.compareTo(BigDecimal.ZERO)==0){
						throw new BatchException("商户结算内部户 "+  merAcctInfo.getStlAcctNo()   +"的余额为0,不允许转账,请检查!!!");
					}
					
					
					
					//检查内部户余额 是否大于0
					if(merAcctBal.signum()>0){
						throw new BatchException("商户结算内部户余额为正数，不能转账，请检查!!!");
					}else{
						//统计当日未转账和 由上一步骤 处理中变成失败的转账明细
						if(transAmt.doubleValue()>Math.abs(merAcctBal.doubleValue())){
							throw new BatchException("商户结算内部户余额不足，不能转账，请检查!!!");
						}
					}
				}else{
					throw new BatchException("调用烟草内部户查询接口【PAY1007】 异常：" + msg);
				}
			}else{
				logger.debug("商户号："+merNo+"      结算账户不为内部户，不需要检查账户余额");
			}
		}
		logger.debug("检查本次商户转账金额是否大于账户余额====================结束======================");
	}
	public void setBatchCommon(BatchCommon batchCommon) {
		this.batchCommon = batchCommon;
	}
}
