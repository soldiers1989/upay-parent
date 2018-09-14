package com.upay.batch.stepservice.schedule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import com.pactera.dipper.core.utils.Constants.Transaction;
import com.pactera.dipper.transaction.TransactionDefinition;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerNotifiyPo;
import com.upay.dao.po.mer.MerPlatSettingPo;

/**
 * Created by Guo on 16/7/27. 支付完成后通知商户(定时任务)
 */
public class NotifyMerAfterPay extends
		AbstractStepExecutor<Object, MerNotifiyPo> {
	private final static Logger LOG = LoggerFactory
			.getLogger(NotifyMerAfterPay.class);

	@Resource(name = "fnhNotifyHandler")
	private IDipperHandler<Message> fnhNotifyHandler;
	@Resource
	private IDipperCached cache;

	private final static int LIMIT_COUNT = 10;// 默认发送通知次数
	
	private String jumpLink;

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", DataBaseConstants_PAY.MER_NOTIFY_STATUS_FALSE);
		map.put("sendTimes", LIMIT_COUNT);
		Integer num = daoService.count(
				MerNotifiyPo.class.getName().concat(".getMerNotifyNum"), map);
		return num == null ? 0 : num;
	}

	@Override
	public List<MerNotifiyPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		/*
		 * 查询3.9.9 商户通知表(T_MER_NOTIFIY)
		 */

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", DataBaseConstants_PAY.MER_NOTIFY_STATUS_FALSE);
		map.put("sendTimes", LIMIT_COUNT);
		List<MerNotifiyPo> merNotifys = daoService.selectList(
				MerNotifiyPo.class.getName().concat(".getMerNotifyPage"), map,
				offset, pageSize);

		return merNotifys;
	}

	@Override
	public void execute(BatchParams batchParams, int index, MerNotifiyPo data,
			Object object) throws BatchException {
		if (null == data)
			return;

		String notifyType = data.getNotifyType();// 通知类型
		String plain = data.getPlain();// 交易数据明文
		Integer sendTimes = data.getSendTimes();// 发送次数
		String returnUrl = data.getMerUrl();// 通知回调url
		String[] paramList = plain.split("&");
		Map<String, Object> map = new HashMap<String, Object>();
		for (String param : paramList) {
			String[] entity = param.split("=");
			map.put(entity[0], entity.length <= 1 ? "" : entity[1]);
		}
		if (StringUtils.isBlank(data.getMerNo())) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付结果通知的商户号 ");
		}
		String key = cache.get(data.getMerNo());
		if (StringUtils.isBlank(key)) {
			MerPlatSettingPo mer = new MerPlatSettingPo();
			mer.setMerPlatNo(data.getMerNo());
			mer = daoService.selectOne(mer);
			if (mer == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户平台系统参数设置信息表配置");
			}
			key = mer.getKey3des();
		}
		if (StringUtils.isBlank(key)) {
			ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户密钥");
		}
		map.put("key", key);
		map.put("notifyUrl", jumpLink+"?" + returnUrl);
		map.put("payServicType", notifyType);
		String notifyStatus = "";
		Message rspMsg = null;
		try {
			rspMsg = fnhNotifyHandler.handle(this.getMessage(map));
		} catch (Exception e) {
			throw new BatchException("调用支付通知接口服务异常", e);
		}
		if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode())) {
			sendTimes += 1;
			notifyStatus = "1";
			LOG.info("通知商户返回结果1" + rspMsg.getFault().getCode());
		} else {
			sendTimes += 1;
			notifyStatus = "0";
			LOG.info("通知商户返回结果0" + rspMsg.getFault().getCode());
		}
		LOG.info("收到通知返回==========" + rspMsg.getTarget().getBodys());
		// 解包后更新状态
		// data.setNotifyStatus(notifyStatus);
		// data.setSendTimes(sendTimes);
		MerNotifiyPo merNotifiyPoSet = new MerNotifiyPo();
		MerNotifiyPo merNotifiyPoWhere = new MerNotifiyPo();
		merNotifiyPoSet.setNotifyStatus(notifyStatus);
		merNotifiyPoSet.setSendTimes(sendTimes);
		merNotifiyPoWhere.setNotifyId(data.getNotifyId());
		merNotifiyPoWhere.setNotifyStatus(data.getNotifyStatus());
		daoService.update(merNotifiyPoSet, merNotifiyPoWhere);
		// daoService.update(data);

		LOG.info("更新商户通知表[notifyStatus]为[{}], [sendTimes]为[{}]", notifyStatus,
				sendTimes);

	}

	public Message getMessage(Map<String, Object> bodys) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(Transaction.LAZY, new TransactionDefinition("", "", 0,
				TransactionDefinition.PROPAGATION_NOT_SUPPORTED));
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				"notifycli", "HTTP", "UTF-8",
				MessageFactory.createSimpleMessage(),
				MessageFactory.createSimpleMessage(headers, bodys),
				FaultFactory.create(ResponseCode.SUCCESS, "success"),
				new LinkedList<Store>());
		return message;
	}

	@Override
	public void processDataList(BatchParams batchParams, Object object,
			int pageIndex, int pageSize, int totalResult, int commitNum,
			int timeout) throws BatchException {

		int start = (pageIndex - 1) * pageSize;
		List<MerNotifiyPo> list = this.getDataList(batchParams, start,
				pageSize, object);

		// 数据量大于1的时候使用多线程并发处理，同时并发的最大线程数在BATCH_STEP表里配置
		if (list == null || list.size() == 0) {
			return;
		}
		int size = list.size();
		int index = 0;
		int startIndex = (pageIndex - 1) * pageSize;
		for (; index < size; index++) {
			this.execute(batchParams, index + startIndex, list.get(index),
					object);
		}
	}

	public String getJumpLink() {
		return jumpLink;
	}

	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}
	
	
}
