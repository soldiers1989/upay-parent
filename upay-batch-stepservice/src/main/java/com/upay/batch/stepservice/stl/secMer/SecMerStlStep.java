package com.upay.batch.stepservice.stl.secMer;

import java.util.ArrayList;
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
import com.pactera.dipper.core.Message;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.util.DateUtil;
import com.upay.dao.PlatFormHolidayContext;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 二级商户结算 Created by LB on 2017/8/1.
 */
public class SecMerStlStep extends AbstractStepExecutor<StlBookPo, Object> {
	private BatchCommon batchCommon;
	private static final Logger LOGGER = LoggerFactory.getLogger(SecMerStlStep.class);
	@Override
	public List<StlBookPo> getObjectList(BatchParams batchParams)
			throws BatchException {
		
		//判断是否是指定日期执行
    	if(batchParams.getParameter().containsKey("tranDate")){
    		Date tranDate = (Date)batchParams.getParameter().get("tranDate");
    		Date preDate = (Date)batchParams.getParameter().get("preDate");
    		batchParams.setTranDate(tranDate);
    		batchParams.setPreDate(preDate);
    	}
    	
		LOGGER.info("-------------商户结算开始");
		List<StlBookPo> stlBookPoList = new ArrayList<StlBookPo>();
		if (!PlatFormHolidayContext.isWorkDay(batchParams.getTranDate(), "0")) {
			LOGGER.info("-------------非工作日，商户不参与结算");
			return stlBookPoList;
		} else {
			Map<String, Object> sqlParam = new HashMap<String, Object>();
			sqlParam.put("stat", DataBaseConstants_BATCH.STL_STAT_COMPLETED);
			stlBookPoList = daoService.selectList(StlBookPo.class.getName()
					+ ".secMerStlQuery", sqlParam);
			LOGGER.info("-------------查询结算记录数[{}]", stlBookPoList.size());
			return stlBookPoList;
		}
	}

	@Override
	public void execute(BatchParams batchParams, int index, Object data,
			StlBookPo stlBook) throws BatchException {
        
		StlBookPo whereStlBook =new StlBookPo();
		whereStlBook.setId(stlBook.getId());
		
		//检查一级商户结算是否转账完成
		Map<String,Object> map=new HashMap<String,Object>();
        map.put("merNo", stlBook.getMerNo());
        map.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
        map.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
        StlBookPo stlBookPo = daoService.selectOne(StlBookPo.class.getName()+".checkFirstMerStlToday",map);
        StlBookPo setBook =new StlBookPo();
        if(null==stlBookPo){
        	LOGGER.info("一级商户【"+stlBook.getMerNo()+"】未清算");
        	setBook.setRemark("一级商户【"+stlBook.getMerNo()+"】未清算");
    		daoService.update(setBook,whereStlBook);
        	return;
        }else{
        	if(DataBaseConstants_BATCH.STL_STAT_CHECK_IN.equals(stlBookPo.getStat())){
        		setBook.setRemark("对应一级商户未结算转账");
        		daoService.update(setBook,whereStlBook);
            	return;
        	}else if(DataBaseConstants_BATCH.STL_STAT_EXCEPTION.equals(stlBookPo.getStat())){
        		setBook.setRemark("对应一级商户结算转账异常");
        		daoService.update(setBook,whereStlBook);
            	return;
        	}
        	
        	//检查转账金额是否大于0
        	if(null==stlBook.getBarAmt()||stlBook.getBarAmt().signum()<=0){
        		setBook.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);
        		setBook.setRemark("转账成功");
        		daoService.update(setBook,whereStlBook);
            	return;
        	}
        }
		        
		
		//检查处理异常的情况  并且备注不为空（备注里是订单号）
		if(StringUtils.isNotBlank(stlBook.getRemark())&&DataBaseConstants_BATCH.STL_STAT_EXCEPTION.equals(stlBook.getStat())){
			PayOrderListPo list=new PayOrderListPo();
			list.setOrderNo(stlBook.getRemark());
			list=daoService.selectOne(list);
			if(null!=list){
				//通过订单状态判断更新结算状态，如果是成功则返回
				String orderStat = list.getOrderStat();
				StlBookPo set=new StlBookPo();
				
				if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)){
					set.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);
					set.setRemark("二级商户["+stlBook.getSecMerNo()+"]结算转账订单:["+stlBook.getRemark()+"] 状态支付成功。");
					daoService.update(set,whereStlBook);
					return;
				}
			}
		}
		
		
		String merNo = stlBook.getMerNo();
		MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
		merBaseInfoPo.setMerNo(merNo);
		merBaseInfoPo.setMerState(DateBaseConstants_MER.MER_STAT_NORMAL);
		merBaseInfoPo = daoService.selectOne(merBaseInfoPo);

		if (null != merBaseInfoPo) {
			//查询一级商户结算账户
			MerAcctInfoPo payerMerAcctInfo = new MerAcctInfoPo();
			payerMerAcctInfo.setMerNo(merNo);
			payerMerAcctInfo.setIsParentMer(DateBaseConstants_MER.IS_PARENT_MER_YES);
			payerMerAcctInfo = daoService.selectOne(payerMerAcctInfo);
			if (payerMerAcctInfo == null) {
				LOGGER.error("-------------一级商户[{}]无结算账户", merNo);
				// 无商户结算账户不结算
				return;
			}
			
			String payerMerStlAcctType=payerMerAcctInfo.getStlAcctType();
			//如果一级商户结算账户是他行卡，他行对公，则不做二清
			if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(payerMerStlAcctType)
					||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payerMerStlAcctType)){
				LOGGER.error("-------------二清暂不支持一级商户结算账户为他行卡或他行对公账户，", merNo);
				StlBookPo setStlBook =new StlBookPo();
				setStlBook.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
				setStlBook.setRemark("二清暂不支持一级商户结算账户为他行卡或他行对公账户");
				
				
				daoService.update(setStlBook,whereStlBook);
			}else{
				LOGGER.info("查询二级商户的结算账户");
				//查询二级商户的基本信息
				MerBaseInfoPo secMerBaseInfo=new MerBaseInfoPo();
				secMerBaseInfo.setMerNo(stlBook.getSecMerNo());
				secMerBaseInfo=daoService.selectOne(secMerBaseInfo);
				if(null==secMerBaseInfo){
					LOGGER.error("-------------二级商户[{}]不存在", merNo);
					return;
				}
				
				// 查询二级商户的结算账户
				MerAcctInfoPo payeeMerAcctInfo = new MerAcctInfoPo();
				payeeMerAcctInfo.setMerNo(stlBook.getSecMerNo());
				payeeMerAcctInfo.setParentMerNo(merNo);
				payeeMerAcctInfo.setIsParentMer(DateBaseConstants_MER.IS_PARENT_MER_NO);
				payeeMerAcctInfo = daoService.selectOne(payeeMerAcctInfo);
				if (payeeMerAcctInfo == null) {
					LOGGER.error("-------------二级商户[{}]无结算账户", merNo);
					// 无商户结算账户不结算
					return;
				}
				
				
				Map<String, Object> singlePayMap=new HashMap<String, Object>();
				
				//判断是内部转账还是非内部转账，判断方法是  付款方和收款的结算账户类型判断
				
				String payeeMerStlAcctType=payeeMerAcctInfo.getStlAcctType();
				if((DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payerMerStlAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payerMerStlAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payerMerStlAcctType))
						&&(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeMerStlAcctType)
								||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeMerStlAcctType)
								||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeMerStlAcctType))){
					//当二级商户结算账户为本行内部户是作为 内部转账处理
					singlePayMap.put("singlePayType","1");//代付类型 1-内部转账  2-非内部转账
				}else{
					singlePayMap.put("singlePayType","2");//代付类型 1-内部转账  2-非内部转账
				}
				singlePayMap.put("userId",merBaseInfoPo.getUserId());  //一级商户的userID
				singlePayMap.put("merNo",merNo);  //一级商户号
				singlePayMap.put("transAmt", stlBook.getBarAmt());// 交易金额为扎差金额
				singlePayMap.put("certType", secMerBaseInfo.getEgalPersonIdType());// 证件类型
				singlePayMap.put("certNo", secMerBaseInfo.getEgalPersonIdNo());// 证件号码
				singlePayMap.put("acctNo", payeeMerAcctInfo.getStlAcctNo());// 收款方账号
				singlePayMap.put("acctName", payeeMerAcctInfo.getStlAcctName());//收款方姓名
				singlePayMap.put("spbillCreateIp", "127.0.0.1");// ip
				singlePayMap.put("bankId", payeeMerAcctInfo.getBankId());// 收款方银行ID  他行对公需要传入银行ID
				singlePayMap.put("transComments", "二级商户结算");
				singlePayMap.put("orderName", "二级商户结算");
				
				//当收二级商户的结算账户是他行借记卡或本行借记卡时，代付账户类型为  个人账户
				if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(payeeMerStlAcctType)||
						DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeMerStlAcctType)){
					singlePayMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_PERSON);// 代扣的账户类型： 11=个人账户 12=企业账户 。
				}else if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeMerStlAcctType)||
						DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payeeMerStlAcctType)||
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeMerStlAcctType)){//当二级商户结算账户为本行内部户是作为企业账户处理
					singlePayMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_BUSINESS);// 代扣的账户类型： 11=个人账户 12=企业账户。
				}
				
//		        singlePayMap.put("stlAcctType","23");
//		        singlePayMap.put("stlAcctNo","5324019901301000058699");
//		        singlePayMap.put("stlAcctName","月绮罗");
		        
				Message postPay0023 = batchCommon.postPay0023(singlePayMap);
				HashMap<String,Object> bodys = (HashMap<String,Object>)postPay0023.getTarget().getBodys();
				
				String code = postPay0023.getFault().getCode();
				String msg = postPay0023.getFault().getMsg();
				logger.debug("调用SI_PAY0023 结算转账接口：返回代码="+code+"      返回信息="+msg);
				
				if(null!=bodys){
					String orderNo=(String)bodys.get("orderNo");
					StlBookPo setStlBook =new StlBookPo();
					if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)&&null!=orderNo){
						//根据平台交易订单号查询转账流水是状态
						PayOrderListPo list=new PayOrderListPo();
						list.setOrderNo(orderNo);
						list=daoService.selectOne(list);
						if(null!=list){
							//通过订单状态判断流水状态
							String orderStat = list.getOrderStat();
							setStlBook.setRemark(orderNo);
							if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)){
								setStlBook.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);
								setStlBook.setRemark("二级商户["+secMerBaseInfo.getMerName()+"]结算转账订单:["+orderNo+"] 状态支付成功。");
							}else{
								setStlBook.setRemark(orderNo);
							}
							
							//根据订单状态更新二级商户结算入账状态
							daoService.update(setStlBook,whereStlBook);
						}else{
							logger.error("二级商户【"+stlBook.getSecMerNo()+"】 日终结算失败，异常："+msg);
						}
					}else{
						logger.error("二级商户【"+stlBook.getSecMerNo()+"】 日终结算失败，异常："+msg);
					}
			   }
			}
		}else{
			LOGGER.error("-------------一级商户[{}]不存在", merNo);
		}
	}

	public BatchCommon getBatchCommon() {
		return batchCommon;
	}

	public void setBatchCommon(BatchCommon batchCommon) {
		this.batchCommon = batchCommon;
	}

}
