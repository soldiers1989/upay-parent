package com.upay.batch.stepservice.schedule;

import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BatchCommon {

	private IDipperHandler<Message> SI_ACC1006;
	private IDipperHandler<Message> SI_ACC1007;
	//单笔代扣
		private IDipperHandler<Message> SI_PAY0019;
	//单笔代付
	private IDipperHandler<Message> SI_PAY0020;
	//结算转账
	private IDipperHandler<Message> SI_PAY0023;
	//烟草内部户查询
	private IDipperHandler<Message> SI_PAY1007;
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 调用商户转账
	 */
	public Message postAcc1006(Map<String, Object> actmap) {
		actmap.put("chnlId", "02");
		actmap.put("transCode", "SI_ACC1006");
		Message message=null;
		try {
			 Message actEnterPostMsg = getMessage(actmap);
			 message = this.SI_ACC1006.handle(actEnterPostMsg);
//			 checkSecondResponseException(message);
			 //提交事务
			 //this.daoService.executeDaoList(actEnterPostMsg.getStores());
		} catch (Exception e) {
			// 【 异常】
			throw new BatchException("调用商户转账:" + e.getMessage());
		}
		return message;
	}
	/**
	 * 调用单笔代收
	 */
	public Message postPay0019(Map<String, Object> singleCollectionMap) {
	//	singleCollectionMap.put("chnlId", "02");
		singleCollectionMap.put("transCode", "SI_PAY0019");
		singleCollectionMap.put("platform", "01");
		Message message=null;
		try {
			 Message actEnterPostMsg = getMessage(singleCollectionMap);
			 message = this.SI_PAY0019.handle(actEnterPostMsg);
//			 checkSecondResponseException(message);
			 //提交事务
			 //this.daoService.executeDaoList(actEnterPostMsg.getStores());
		} catch (Exception e) {
			// 【 异常】
			throw new BatchException("调用单笔代收:" + e.getMessage());
		}
		return message;
	}
	/**
	 * 调用单笔代付
	 */
	public Message postPay0020(Map<String, Object> singleCollectionMap) {
		//singleCollectionMap.put("chnlId", "02");
		singleCollectionMap.put("transCode", "SI_PAY0020");
		singleCollectionMap.put("platform", "01");
		Message message=null;
		try {
			 Message actEnterPostMsg = getMessage(singleCollectionMap);
			 message = this.SI_PAY0020.handle(actEnterPostMsg);
//			 checkSecondResponseException(message);
			 //提交事务
			 //this.daoService.executeDaoList(actEnterPostMsg.getStores());
		} catch (Exception e) {
			// 【 异常】
			throw new BatchException("调用单笔代付:" + e.getMessage());
		}
		return message;
	}
	/**
	 * 调用 结算转账
	 */
	public Message postPay0023(Map<String, Object> singleCollectionMap) {
		singleCollectionMap.put("chnlId", "02");
		singleCollectionMap.put("transCode", "SI_PAY0023");
		singleCollectionMap.put("platform", "01");
		Message message=null;
		try {
			 Message actEnterPostMsg = getMessage(singleCollectionMap);
			 message = this.SI_PAY0023.handle(actEnterPostMsg);
//			 checkSecondResponseException(message);
			 //提交事务
			 //this.daoService.executeDaoList(actEnterPostMsg.getStores());
		} catch (Exception e) {
			// 【 异常】
			throw new BatchException("调用结算转账:" + e.getMessage());
		}
		return message;
	}
	
	/**
	 * 调用商户内部户查询接口
	 */
	public Message postPAY1007(Map<String, Object> actmap) {
		actmap.put("chnlId", "02");
		actmap.put("transCode", "SI_PAY1007");
		Message message=null;
		try {
			 Message actEnterPostMsg = getMessage(actmap);
			 message = this.SI_PAY1007.handle(actEnterPostMsg);
//			 checkSecondResponseException(message);
			 //提交事务
			 //this.daoService.executeDaoList(actEnterPostMsg.getStores());
		} catch (Exception e) {
			// 【 异常】
			throw new BatchException("调用烟草内部户查询:" + e.getMessage());
		}
		return message;
	}
	
	/**
	 * 调用转账到二级商户银行卡号
	 */
	public Message postAcc1007(Map<String, Object> actmap) {
		actmap.put("chnlId", "02");
		actmap.put("transCode", "SI_ACC1007");
		Message message=null;
		try {
			 Message actEnterPostMsg = getMessage(actmap);
			 message = this.SI_ACC1007.handle(actEnterPostMsg);
//			 checkSecondResponseException(message);
			 //提交事务
			 //this.daoService.executeDaoList(actEnterPostMsg.getStores());
		} catch (Exception e) {
			// 【 异常】
			throw new BatchException("调用商户转账:" + e.getMessage());
		}
		return message;
	}

	/**
	 * 创建第三方服务MSG对象
	 * @param param
	 * @return
	 */
	public Message getMessage(Map<String, Object> param) {
		String channel = "04";
		final String charsetName = "UTF-8";
		Map<String, Object> headMap = new HashMap<String, Object>();
		//事务开启
		//headMap.put(com.pactera.dipper.core.utils.Constants.Transaction.LAZY, true);
		Message msg = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessageInstance(), MessageFactory
						.createSimpleMessage(headMap,
								param), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, ""));
		return msg;
	}
	
	
	

	/**
	 * 校验返回结果是否存在异常
	 * @param repMsg
	 */
	public void checkSecondResponseException(Message repMsg){
		if (!Constants.ResponseCode.SUCCESS.equals(repMsg.getFault().getCode())) {
			logger.debug(repMsg.getFault().getOutMsg());
			throw new BatchException(repMsg.getFault().getOutMsg());
		}
	}

	public void setSI_ACC1006(IDipperHandler<Message> sI_ACC1006) {
		SI_ACC1006 = sI_ACC1006;
	}

	public void setSI_ACC1007(IDipperHandler<Message> sI_ACC1007) {
		SI_ACC1007 = sI_ACC1007;
	}

	public void setSI_PAY1007(IDipperHandler<Message> sI_PAY1007) {
		SI_PAY1007 = sI_PAY1007;
	}

	public void setSI_PAY0020(IDipperHandler<Message> sI_PAY0020) {
		SI_PAY0020 = sI_PAY0020;
	}

	public void setSI_PAY0023(IDipperHandler<Message> sI_PAY0023) {
		SI_PAY0023 = sI_PAY0023;
	}
	public void setSI_PAY0019(IDipperHandler<Message> sI_PAY0019) {
		SI_PAY0019 = sI_PAY0019;
	}
	
}
