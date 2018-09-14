/**
 *
 */
package com.upay.gateway.client.acp.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.core.bean.Fault;
import com.upay.gateway.client.acp.util.AcpToolUtil;

/**
 * 银联之银联主扫二维码
 * 
 * 
 * @author liudan
 */
public class UnionPayApplyQrCodeDipperHandler extends
		AbstractAcpClientDipperHandler {
	// 超时不抛异常
	@Override
	public boolean isErrorThrow() {
		return false;
	}

	// 失败抛异常
	@Override
	public boolean isFailureThrow() {
		return true;
	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {

	}

	@Override
	protected void setInitParam(Map<String, Object> init) {
		LOG.info("=======银联二维码【线上主扫】（UnionPayApplyQrCodeDipperHandler）============开始");
      	init.put("tranCode","01");
		if(!init.containsKey("orderNo")){
			init.put("orderNo", AcpToolUtil.getOrderId());
		}
		init.put("orderTime", AcpToolUtil.getCurrentTime());
		init.put("reqType", "0510000903");
		if(init.containsKey("limitCount")){
			init.put("limitCount", init.get("limitCount").toString());
		}
		if(init.containsKey("qrValidTime")){
			init.put("qrValidTime",init.get("qrValidTime").toString());
		}
		
		Map<String, String> payeeInfoMap = new HashMap<String, String>();
		payeeInfoMap.put("name", init.get("primaryMerName").toString());
		payeeInfoMap.put("id",init.get("qrMerId").toString());
		payeeInfoMap.put("merCatCode", "5811");// 目前不校验
		payeeInfoMap.put("termId", "49000002");
		init.put("areaInfo", "1562900");
		
		init.put("payeeInfo", AcpToolUtil.getPayerInfoWithEncrpyt(payeeInfoMap, "UTF-8"));
		init.put("reqReserved", "rrrrrr"+AcpToolUtil.getCurrentTime());
		init.put("orderType","10");
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.putAll(source);
		LOG.info("=======银联二维码【线上主扫】（UnionPayApplyQrCodeDipperHandler）============结束");
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {

	}

	
}
