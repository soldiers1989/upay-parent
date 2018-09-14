package com.upay.batch.stepservice.stl.transferRouteFee;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.page.QueryResult;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.fee.FeeAssInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;


/**
 * 资金通道手续费、分润转账
 * Created by LB on 2017/8/7.
 */
public class TransferRouteFee extends AbstractStepExecutor<Object, StlRouteFeeBookPo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferRouteFee.class);
    private BatchCommon batchCommon;
    @Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
    	HashMap<String,Object> map=new HashMap<String,Object>();
		int queryResult = (int) daoService.selectQueryResult(StlRouteFeeBookPo.class.getName() + ".queryTransferList",map,0,0).getTotalrecord();
		logger.info(queryResult+"=======================");
		return queryResult;
	}

	@Override
	public List<StlRouteFeeBookPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		HashMap<String,Object> map=new HashMap<String,Object>();
		QueryResult<StlRouteFeeBookPo> queryList = daoService.selectQueryResult(StlRouteFeeBookPo.class.getName() + ".queryTransferList",map,offset,pageSize);
		logger.info("======================"+queryList.getResultlist().size());
		return queryList.getResultlist();
	}

	@Override
	public void execute(BatchParams batchParams, int index, StlRouteFeeBookPo data,
			Object object) throws BatchException {
			logger.info("分润清算转账开始======================");
			Map<String, Object> singlePayMap=new HashMap<String, Object>();
			String payeeAcctType=data.getPayeeCardType();
			//判断是内部转账还是非内部转账，判断方法是  付款方和收款的结算账户类型判断
			if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAcctType)
							||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAcctType)
							||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAcctType)){
				//当二级商户结算账户为本行内部户是作为 内部转账处理
				singlePayMap.put("singlePayType","1");//代付类型 1-内部转账  2-非内部转账
			}else{
				singlePayMap.put("singlePayType","2");//代付类型 1-内部转账  2-非内部转账
			}
			singlePayMap.put("userId",null);
			FeeAssInfoPo assInfo=new FeeAssInfoPo();
			if(DataBaseConstants_PAY.ASS_AMT_FLAG.equals(data.getFlag())){
				assInfo.setAssId(data.getAssId());
				assInfo=daoService.selectOne(assInfo);
				singlePayMap.put("certType", assInfo.getCertType());// 证件类型
				singlePayMap.put("certNo", assInfo.getCertNo());// 证件号码
				singlePayMap.put("bankId", assInfo.getBankId());// 收款银行ID  他行对公需要传入银行ID
				singlePayMap.put("mobile",assInfo.getMobile());
			}
			
			singlePayMap.put("stlAcctName", data.getPayerName());//付款方姓名
			singlePayMap.put("stlAcctNo", data.getPayerAcctNo());//付款方账号
			singlePayMap.put("stlAcctType", data.getPayeeCardType());//付款方账户类型
			
			singlePayMap.put("transAmt", data.getTransAmt());// 交易金额为资金通道手续费或分润费
			
			singlePayMap.put("acctNo", data.getPayeeAcctNo());// 收款方账号
			singlePayMap.put("acctName", data.getPayeeName());//收款方姓名
			singlePayMap.put("spbillCreateIp", "127.0.0.1");// ip
			
	        
			
			if(DataBaseConstants_PAY.ASS_AMT_FLAG.equals(data.getFlag())){
				singlePayMap.put("orderName", "分润结算");
			}else{
				singlePayMap.put("orderName", "资金通道手续费结算");
			}
			//当收二级商户的结算账户是他行借记卡或本行借记卡时，代付账户类型为  个人账户
			if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(payeeAcctType)||
					DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAcctType)){
				singlePayMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_PERSON);// 代扣的账户类型： 11=个人账户 12=企业账户 。
			}else if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAcctType)||
					DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payeeAcctType)||
					DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAcctType)){//当二级商户结算账户为本行内部户是作为企业账户处理
				singlePayMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_BUSINESS);// 代扣的账户类型： 11=个人账户 12=企业账户。
			}
			
			Message postPay0023 = batchCommon.postPay0023(singlePayMap);
			HashMap<String,Object> bodys = (HashMap<String,Object>)postPay0023.getTarget().getBodys();
			
			String code = postPay0023.getFault().getCode();
			String msg = postPay0023.getFault().getMsg();
			logger.debug("调用SI_PAY0020单笔代付接口：返回代码="+code+"      返回信息="+msg);
			
			if(null!=bodys){
				String orderNo=(String)bodys.get("orderNo");
				StlRouteFeeBookPo setRouteFee =new StlRouteFeeBookPo();
				if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)&&null!=orderNo){
					//根据平台交易流水号查询转账流水是状态
					PayOrderListPo list=new PayOrderListPo();
					list.setOrderNo(orderNo);
					list=daoService.selectOne(list);
					if(null!=list){
						//通过订单状态判断流水状态
						String orderStat = list.getOrderStat();
						setRouteFee.setUpayDate(list.getOrderDate());
						setRouteFee.setUpayOrderNo(list.getOrderNo());
						if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)){
							setRouteFee.setResult(DataBaseConstants_BATCH.STL_STAT_COMPLETED);
							setRouteFee.setRemark1("分润方["+assInfo.getAssName()+"]结算转账订单:["+orderNo+"] 状态支付成功。");
						}else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(orderStat)){
							setRouteFee.setResult(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
							setRouteFee.setRemark1("分润方"+assInfo.getAssName()+"结算转账订单:["+orderNo+"] 状态支付中。");
						}else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(orderStat)){
							setRouteFee.setResult(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
							setRouteFee.setRemark1("分润方"+assInfo.getAssName()+"结算转账订单:["+orderNo+"] 状态支付失败。");
						}else{
							setRouteFee.setResult(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
							setRouteFee.setRemark1("分润方"+assInfo.getAssName()+"结算转账订单:["+orderNo+"] 状态支付异常。"+msg);
						}
					}else{
						setRouteFee.setResult(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
						setRouteFee.setRemark1("分润方"+assInfo.getAssName()+"结算转账订单:["+orderNo+"] 状态支付异常。"+msg);
					}
				}else{
					setRouteFee.setUpayOrderNo(orderNo);
					setRouteFee.setResult(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
					setRouteFee.setRemark1("分润方["+assInfo.getAssName()+"]结算转账状态异常,订单号为：["+orderNo+"]。请检查。。"+msg);
				}
				
				//根据代付状态更新二级商户结算入账状态
				StlRouteFeeBookPo whereRouteFee =new StlRouteFeeBookPo();
				whereRouteFee.setId(data.getId());
				daoService.update(setRouteFee,whereRouteFee);
		   }
			logger.info("分润清算转账结束======================");
		}

	public void setBatchCommon(BatchCommon batchCommon) {
		this.batchCommon = batchCommon;
	}
}
