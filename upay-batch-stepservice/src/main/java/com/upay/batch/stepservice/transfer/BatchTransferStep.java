package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.page.QueryResult;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * 批量转账第三步：处理转账明细数据。
 * 查询支付流水明细，根据明细完成向用户转账。 execute方法调用Dipper
 * Flow流程，调用核心/中金/银联/二代支付向用户转账；由于execute方法
 * 执行是开启事物且设置了事物超时时间（60秒）， 而Dipper Flow流程执
 * 行时长不确定，因此在execute方法中不执行数据库更改操作。
 * 此任务步骤可进行分布式执行或多线程执行。
 * @author zhangjianfeng
 * @since 2017/03/19 22:53
 */
@SuppressWarnings("unused")
public class BatchTransferStep extends AbstractStepExecutor<Map<String, Object>, PayFlowDetailPo> {

    /** 日志记录。 */
    private static final Logger LOG = LoggerFactory.getLogger(BatchTransferStep.class);


    /**
     * 获取本次批要处理的支付明细记录总数。
     * 
     * @param batchParams
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public int getTotalResult(BatchParams batchParams,
                              Map<String, Object> object) throws BatchException {

		PayFlowDetailPo payFlowDetailPo=new PayFlowDetailPo();
		payFlowDetailPo.setProcess(DataBaseConstants_PAY.PAY_PROCESS_N);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		payFlowDetailPo.setMerTransDate(calendar.getTime());
		int queryResult = (int) daoService.selectQueryResult(PayFlowDetailPo.class.getName() + ".getTransferData",payFlowDetailPo,0,0).getTotalrecord();

        String disBatchNo = batchParams.getBatchNo();
        LOG.info("----- 批量转账 -- 批次[{}]有{}个支付明细需要处理",
                new Object[] {disBatchNo, queryResult});
        return queryResult;
    }


    /**
     * 分页获取本次批要处理的支付明细记录。
     * 
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<PayFlowDetailPo> getDataList(BatchParams batchParams, int offset, int pageSize,
            Map<String, Object> object) throws BatchException {
    	logger.info("offset:{},pageSize{}",offset,pageSize);
    	String disBatchNo = batchParams.getBatchNo();
		PayFlowDetailPo payFlowDetailPo=new PayFlowDetailPo();
		payFlowDetailPo.setProcess(DataBaseConstants_PAY.PAY_PROCESS_N);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		payFlowDetailPo.setMerTransDate(calendar.getTime());
		//彩信相册
		QueryResult<PayFlowDetailPo> queryResult =  daoService.selectQueryResult(PayFlowDetailPo.class.getName() + ".getTransferData",payFlowDetailPo,offset,pageSize);
		List<PayFlowDetailPo> resultlist = queryResult.getResultlist();
		LOG.info("----- 批量转账 -- 批次[{}]当前分页查询有{}个转账记录需要处理",
               new Object[] {disBatchNo, resultlist.size()});
		return resultlist;

    }


    /**
     * 调用Dipper Flow流程向用户转账，方法中不执行数据库更改操作；
     * 对于支付明细的更改操作放入Dipper Flow流程执行。防止因
     * execute方法执行事物超时面无法更新支付明细交易状态。
     * 
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @SuppressWarnings("all")
    @Override
    public void execute(BatchParams batchParams, int index, PayFlowDetailPo data, Map<String, Object> object)
            throws BatchException {


    	logger.info("getPayeeName {}",data.getPayeeName());
    	logger.info("getPayerName {}",data.getPayerName());
    	PayFlowDetailPo setDetail=new PayFlowDetailPo();
    	PayFlowDetailPo whereDetail=new PayFlowDetailPo();
    	whereDetail.setId(data.getId());


    	//续跑   除了  支付成功 的  都要处理
    	String orderNo = data.getOrderNo();
    	if(StringUtils.isNotBlank(orderNo)){
    		//同步处理中的情况
    		PayOrderListPo order=new PayOrderListPo();
    		order.setOrderNo(orderNo);
    		//order.setOrderStat( DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);
    		order=daoService.selectOne(order);
    		if(null!=order){
    			//用于处理批量代收代付   失败  重新处理的情况
    			if(!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(order.getOrderStat())
						||!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(order.getOrderStat())){
    				//删除流水
					PayFlowListPo payFlowListPo=new PayFlowListPo();
					payFlowListPo.setOrderNo(order.getOrderNo());
    				daoService.delete(payFlowListPo);
    				//删除订单|
					daoService.delete(order);
				}else{
					setDetail.setTransStat(order.getOrderStat());
					daoService.update(setDetail,whereDetail);
					return;
				}

    		}
    	}
    		String payerAcctType=data.getPayerAcctType();
			String payeeAcctType=data.getPayeeAcctType();
    		Message msssage=null;
    		if(DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_COLLECTION.equals(data.getTransFlag())){
    			//代收
    			Map<String, Object> singleColltionMap=new HashMap<String, Object>();
    			singleColltionMap.put("transCode", "SI_PAY0019");
    			singleColltionMap.put("chnlId", data.getChnlid());
    			singleColltionMap.put("merNo", data.getMerNo());// 商户号
    			singleColltionMap.put("outerOrderNo", data.getMerTransSeq());


    			if((DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payerAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payerAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payerAcctType))
						&&(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAcctType)
								||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAcctType)
								||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAcctType))){
					//当二级商户结算账户为本行内部户是作为 内部转账处理
    				singleColltionMap.put("collectionType","1");//代收类型 1-内部转账  2-非内部转账
				}else{
					singleColltionMap.put("collectionType","2");//代收类型 1-内部转账  2-非内部转账
				}
    	        
    	        singleColltionMap.put("merDate", DateUtil.format(data.getMerTransDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));// 商户时间
    	        singleColltionMap.put("acctNo", data.getPayerAcctNo());// 付款账号
				//收款账号
				singleColltionMap.put("payeeAccountNo", data.getPayeeAcctNo());
				//收款账号类型
				singleColltionMap.put("payeeAccountType", data.getPayeeAcctType());
    	        singleColltionMap.put("singleCollectionFlag", "02");//01本平台上起     02其他第三方商户发起
				singleColltionMap.put("acctName", data.getPayerName());
    	        if(/*DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payerAcctType)
    	        		||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payerAcctType)
    	        			||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payerAcctType)*/
						DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payerAcctType)){
    	        //	singleColltionMap.put("accountType", "12");// 代扣的账户类型： 11=个人账户 12=企业账户 // 代扣个人银行卡时类型为11。
    	        	singleColltionMap.put("bankId", data.getBankId());// 付款银行ID
    	        }else{
    	        	//singleColltionMap.put("accountType", "11");// 代扣的账户类型： 11=个人账户 12=企业账户 // 代扣个人银行卡时类型为11。
    	        }
				singleColltionMap.put("accountType", data.getPayerAcctType());
				//个人账户还款  需要 个人付款方的信息
				singleColltionMap.put("certType", data.getCertType());// 证件类型
				singleColltionMap.put("certNo", data.getCertNo());// 证件号码
				singleColltionMap.put("mobile", data.getCertMobile());// 手机号码
				singleColltionMap.put("acctName", data.getCertName());// 姓名


    	      //  singleColltionMap.put("certType", "01");// 证件类型
    	        //singleColltionMap.put("certNo", "530421198608140321");// 证件号码
    	        singleColltionMap.put("transAmt", data.getTransAmt());// 交易金额
//    	        singleColltionMap.put("mobile", "13887738666");// 手机号码
    	        singleColltionMap.put("spbillCreateIp", "127.0.0.1");// ip
//    	        singleColltionMap.put("validate", "202712");// 卡有效期
//    	        singleColltionMap.put("cvn2", "176");// CVN
    	        singleColltionMap.put("notifyUrl", "http://www.baidu.com");// 接收支付成功通知的URL
				logger.info("singleColltionMap {}",singleColltionMap);
    	        msssage = batchCommon.postPay0019(singleColltionMap);
    		}else if(DataBaseConstants_BATCH.BATCH_TRANSFER_FLAG_PAYMENT.equals(data.getTransFlag())){
	    			//代付
    			Map<String, Object> singlePayMap=new HashMap<String, Object>();
				singlePayMap.put("chnlId", data.getChnlid());//渠道
				//判断是内部转账还是非内部转账，判断方法是  付款方和收款的结算账户类型判断
				
				if((DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payerAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payerAcctType)
						||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payerAcctType))
						&&(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAcctType)
								||DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAcctType)
								||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAcctType))){
					//当二级商户结算账户为本行内部户是作为 内部转账处理
					singlePayMap.put("singlePayType","1");//代付类型 1-内部转账  2-非内部转账
				}else{
					singlePayMap.put("singlePayType","2");//代付类型 1-内部转账  2-非内部转账
				}
//				singlePayMap.put("userId",merBaseInfoPo.getUserId());  //一级商户的userID
				singlePayMap.put("merNo",data.getMerNo());  //一级商户号
				singlePayMap.put("transAmt", data.getTransAmt());// 交易金额为扎差金额
//				singlePayMap.put("certType", secMerBaseInfo.getEgalPersonIdType());// 证件类型
//				singlePayMap.put("certNo", secMerBaseInfo.getEgalPersonIdNo());// 证件号码
				singlePayMap.put("acctNo", data.getPayeeAcctNo());// 收款方账号
				singlePayMap.put("acctName", data.getPayeeName());//收款方姓名
				singlePayMap.put("spbillCreateIp", "127.0.0.1");// ip
				singlePayMap.put("bankId", data.getBankId());// 收款方银行ID  他行对公需要传入银行ID
				singlePayMap.put("transComments", "批量代付");
				singlePayMap.put("orderName", "批量代付");
				singlePayMap.put("outerOrderNo", data.getMerTransSeq());
				singlePayMap.put("merDate", DateUtil.format(data.getMerTransDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));// 商户时间
				singlePayMap.put("notifyUrl", "http://www.baidu.com");// 接收支付成功通知的URL
				singlePayMap.put("payerAccountNo", data.getPayerAcctNo());// 付款账号
				singlePayMap.put("payerAccountType", data.getPayerAcctType());// 付款账号类型



				//代付 需要个人 收款方的信息
				singlePayMap.put("payeeCertType", data.getCertType());// 证件类型
				singlePayMap.put("payeeCertNo", data.getCertNo());// 证件号码
				singlePayMap.put("payeeMobile", data.getCertMobile());// 手机号码
				singlePayMap.put("payeeAccountName", data.getCertName());// 姓名

				//当收二级商户的结算账户是他行借记卡或本行借记卡时，代付账户类型为  个人账户
				/*if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(payeeAcctType)||
						DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(payeeAcctType)){
					singlePayMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_PERSON);// 代扣的账户类型： 11=个人账户 12=企业账户 。
				}else if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(payeeAcctType)||
						DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(payeeAcctType)||
						DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(payeeAcctType)){//当二级商户结算账户为本行内部户是作为企业账户处理
					singlePayMap.put("accountType", DataBaseConstants_PAY.ACCT_TYPE_BUSINESS);// 代扣的账户类型： 11=个人账户 12=企业账户。
				}*/
				singlePayMap.put("accountType", payeeAcctType);
				logger.info("singleColltionMap {}",singlePayMap);
				msssage = batchCommon.postPay0020(singlePayMap);

    		}
    		
    		HashMap<String,Object> bodys = (HashMap<String,Object>)msssage.getTarget().getBodys();
			
			String code = msssage.getFault().getCode();
			String msg = msssage.getFault().getMsg();
			logger.debug("调用转账接口：返回代码="+code+"      返回信息="+msg);
			if(bodys!=null){
				String transferOrderNo=(String)bodys.get("orderNo");
				String orderStat=(String)bodys.get("orderStat");
				if(StringUtils.isNotBlank(transferOrderNo)){
					if(StringUtils.isNotBlank(orderStat)){
						setDetail.setOrderNo(transferOrderNo);
						setDetail.setTransStat(orderStat);
					}else{
						//根据平台交易订单号查询转账流水是状态
						PayOrderListPo list=new PayOrderListPo();
						list.setOrderNo(transferOrderNo);
						list=daoService.selectOne(list);
						if(null!=list){
							//通过订单状态判断流水状态
							orderStat = list.getOrderStat();
							setDetail.setOrderNo(transferOrderNo);
							setDetail.setTransStat(orderStat);
						}
					}
					
					if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)){
						setDetail.setRemark1("转账成功。");
					}else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(orderStat)){
						setDetail.setRemark1("转账处理中");
					}else{
						setDetail.setRemark1("转账失败!"+msg);
					}
					//根据订单状态更新转账状态
					daoService.update(setDetail,whereDetail);
				}else{
					setDetail.setOrderNo(transferOrderNo);
					setDetail.setTransStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL);
					setDetail.setRemark1("转账失败!"+msg);
					//根据订单状态更新转账状态
					daoService.update(setDetail,whereDetail);
				}
			}
    }
    private BatchCommon batchCommon;
    public BatchCommon getBatchCommon() {
		return batchCommon;
	}

	public void setBatchCommon(BatchCommon batchCommon) {
		this.batchCommon = batchCommon;
	}
}
