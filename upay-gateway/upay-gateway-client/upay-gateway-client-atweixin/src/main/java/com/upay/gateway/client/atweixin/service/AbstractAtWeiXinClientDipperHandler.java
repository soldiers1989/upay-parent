/**
 * 
 */
package com.upay.gateway.client.atweixin.service;

import static com.pactera.dipper.core.utils.Constants.Channel._IS_CLIENT;
import static com.pactera.dipper.core.utils.Constants.Channel._TRAN_CODE_NAME;
import static com.pactera.dipper.core.utils.Constants.MDC.STEP;
import static com.pactera.dipper.exception.SysErrCode.SYS001;
import static com.pactera.dipper.exception.SysErrCode.SYS003;
import static com.pactera.dipper.exception.SysErrCode.SYS003_MSG;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;
import com.pactera.dipper.utils.net.ExceptionUtils;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.util.DateUtil;
import com.upay.gateway.client.atweixin.util.SDKConfig;
import com.upay.gateway.client.atweixin.util.WXPay;
import com.upay.gateway.client.atweixin.util.WXPayUtil;

/**
 * 银联at 微信
 * 
 * @author hry
 * 
 */
public abstract class AbstractAtWeiXinClientDipperHandler  extends AbstractClientDipperHandler {
	public static final Logger LOG = LoggerFactory
			.getLogger(AbstractAtWeiXinClientDipperHandler.class);
    private static final String FLAG_FAILURE = "failure";
	private String channelId;// 银联渠道

	@Override
	@SuppressWarnings("all")
	public Message handle(Message m) throws Exception {

		MDC.put(Constants.MDC.ID, m.getId());
		MDC.put(STEP, m.getFlowStep() + "");
		Date clientTime = new Date();

		Map<String, Object> resultMap = null;
		String url = "";

		try {

			Map<String, Object> targetHeaderMap = new HashMap<String, Object>(3);
			targetHeaderMap.put(_IS_CLIENT, true);
			targetHeaderMap.put("PRESYS", "PRESYS");
			Map<String, Object> unionMap = new HashMap<String, Object>();
			Map<String, Object> targetBodyMap = new HashMap<String, Object>();
			targetBodyMap
					.putAll((Map<String, Object>) m.getTarget().getBodys());
			LOG.debug("client[channelId={}", channelId);
			if (null != m.getOther()) {
				// 此处 _TRAN_CODE 只用于在不需要打包时使用
				String headTranCode = (String) m.getOther()
						.get(_TRAN_CODE_NAME);
				if (m.getOther().containsKey(_TRAN_CODE_NAME)
						&& StringUtils.isNotBlank(headTranCode)) {
					targetHeaderMap.put(_TRAN_CODE_NAME, headTranCode);
					m.getOther().remove(_TRAN_CODE_NAME);
				}
				LOG.debug("param-ref[{}]", m.getOther());
				targetBodyMap.putAll(m.getOther());
				unionMap.putAll(m.getOther());
			}
			String tranCode=(String) unionMap.get("tranCode");
			if(StringUtils.isBlank(tranCode)){
				tranCode=(String) targetBodyMap.get("tranCode");
			}
			unionMap.put("notify_url", targetBodyMap.get("notifyUrl"));
			// // 请求地址
			
			if(targetBodyMap.get("orgNo")!=null){
		 		unionMap.put("mch_id", targetBodyMap.get("orgNo"));
		 	}
			if(targetBodyMap.get("appId")!=null){
		 		unionMap.put("appid", targetBodyMap.get("appId"));
		 	}
			
			if(targetBodyMap.get("subAppid")!=null){
		 		unionMap.put("sub_appid", targetBodyMap.get("subAppid"));
		 	}
			if(targetBodyMap.get("subMchId")!=null){
			     unionMap.put("sub_mch_id", targetBodyMap.get("subMchId"));
			}
			if (tranCode.equals("MICROPAY")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getMicropayUrlSuffix();
				unionMap.put("totalFee", targetBodyMap.get("totalFee"));
				unionMap.put("spbillCreateIp", targetBodyMap.get("spbillCreateIp"));
				unionMap.put("body", targetBodyMap.get("body"));
				unionMap.put("authCode", targetBodyMap.get("authCode"));
				unionMap.put("transSubSeq", targetBodyMap.get("transSubSeq"));
				
			}
			if (tranCode.equals("ORDERQUERY")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getOrderqueryUrlSuffix();
				unionMap.remove("notify_url");
				unionMap.put("transSubSeq", targetBodyMap.get("transSubSeq"));
			}

			if (tranCode.equals("ORDERREVERSE")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getOrderreverseUrlSuffix();
			}
			if (tranCode.equals("ORDERREFUND")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getRefundUrlSuffix();
				unionMap.put("oriTransSubSeq", targetBodyMap.get("oriTransSubSeq"));
				unionMap.put("outTradeNo", targetBodyMap.get("outTradeNo"));
				unionMap.put("outerRefundNo", targetBodyMap.get("outerRefundNo"));
				unionMap.put("oriTotalFee", targetBodyMap.get("totalFeeRefund"));
				unionMap.put("totalFeeRefund", targetBodyMap.get("oriTotalFee"));
				unionMap.put("notify_url", targetBodyMap.get("callbackRul"));
			}
			if (tranCode.equals("ORDERCLOSE")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getOrdercloseUrlSuffix();
			}
			if (tranCode.equals("ORDERQRYSINGLE")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getQrysingleUrlSuffix();
				unionMap.remove("notify_url");
				unionMap.put("transSubSeq", targetBodyMap.get("transSubSeq"));
			}
			if (tranCode.equals("ORDERQRYMULTIPLE")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getQrymultipleUrlSuffix();
			}

			if (tranCode.equals("SUBMCHMANAGEADD")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getSubmchaddUrlSuffix();
			 	unionMap.put("merchantName", targetBodyMap.get("merchantName"));
			 	unionMap.put("merchantShortname", targetBodyMap.get("merchantShortname"));
			 	unionMap.put("servicePhone", targetBodyMap.get("servicePhone"));
			 	unionMap.put("merchantRemark", targetBodyMap.get("merchantRemark"));
			 	unionMap.put("channelId", targetBodyMap.get("channelId"));
			 	unionMap.put("business", targetBodyMap.get("business"));
			 	if(targetBodyMap.get("contactEmail")!=null){
			 		unionMap.put("contactEmail", targetBodyMap.get("contactEmail"));
			 	}
			 	if(targetBodyMap.get("contactPhone")!=null){
			 		unionMap.put("contactPhone", targetBodyMap.get("contactPhone"));
			 	}
			 	if(targetBodyMap.get("contact")!=null){
			 		unionMap.put("contact", targetBodyMap.get("contact"));
			 	}
				unionMap.remove("notify_url");
			}
			if (tranCode.equals("SUBMCHQRY")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getSubmchqryUrlSuffix();
			}
			if (tranCode.equals("ORDERPREPAY")) {
				url = SDKConfig.getConfig().getTransUrlPrefix()
						+ SDKConfig.getConfig().getPrepayUrlSuffix();
				unionMap.put("totalFee", targetBodyMap.get("totalFee"));
				unionMap.put("spbillCreateIp", targetBodyMap.get("spbillCreateIp"));
				unionMap.put("body", targetBodyMap.get("body"));
				unionMap.put("transSubSeq", targetBodyMap.get("transSubSeq"));
				unionMap.put("feeType", targetBodyMap.get("feeType"));
				unionMap.put("tradeType", targetBodyMap.get("tradeType"));
				unionMap.put("attach", targetBodyMap.get("attach"));
			 	if(targetBodyMap.get("openId")!=null){
			 		unionMap.put("openid", targetBodyMap.get("openId"));
			 	}
			 	if(targetBodyMap.get("subOpenid")!=null){
			 		unionMap.put("sub_openid", targetBodyMap.get("subOpenid"));
			 	}
			}
			Message message = MessageFactory.create(m.getId(), MessageFactory
					.createSimpleMessage(targetHeaderMap, unionMap),
					FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
			Map<String, Object> initMap=(Map<String, Object>) message.getTarget().getBodys();
			if(!initMap.containsKey("tranCode")){
				initMap.put("tranCode", tranCode);
			}
			setInitParam(initMap);

			LOG.debug("client request message={}", message);
			// send request
			unionMap.put("channel_id", SDKConfig.getConfig()
					.getChannelId());
			if (!tranCode.equals("SUBMCHMANAGEADD")
					&& !tranCode.equals("SUBMCHQRY")) {
				unionMap.put("nonce_str", WXPayUtil.generateUUID());
			}

			unionMap = WXPay.fillRequestData(unionMap,tranCode);
			resultMap = WXPay
					.post(unionMap, url, CmparmConstants.ENCODING,tranCode);

			if (resultMap == null || resultMap.size() == 0) {
				LOG.info("相应报文不能为空!!");
				throw new Exception("response is null!!");
			}
			String respCode = "";
			if (resultMap.containsKey("errCode")) {
				respCode = (String) resultMap.get("errCode");
			} else {
				respCode = (String) resultMap.get("returnCode");
			}
			String respMsg = "";
			if (resultMap.containsKey("errCodeDes")) {
				respMsg = (String) resultMap.get("errCodeDes");
			} else {
				respMsg = (String) resultMap.get("returnMsg");
			}
			if (StringUtils.isBlank(respCode)) {
				LOG.info("响应码不能为空,响应报文[{}]", resultMap);
				throw new Exception("respCode is null!!");
			}
			message.getFault().setCode(respCode);
			message.getFault().setMsg(respMsg);
			message.getFault().setOutCode(respCode);
			message.getFault().setOutMsg(respMsg);

			targetBodyMap.putAll(resultMap);

			message.getTarget().setBody(targetBodyMap);

			LOG.debug("client receive message={}", message);

			if ("SUCCESS".equals(message.getFault()
					.getCode())) {
				doSuccessHandle((Map<String, Object>) message.getTarget()
						.getBodys(), (Map<String, Object>) m.getTarget()
						.getBodys(), message.getFault());
			} else if (null != message.getFault().getE()) {
				throw (Exception) message.getFault().getE();
			} else {
				message.setFault(new Fault(message.getFault().getCode(),
						message.getFault().getMsg()));
				if (message.getTarget().getBodys() instanceof Map) {
					doFailureHandle((Map<String, Object>) message.getTarget()
							.getBodys(), (Map<String, Object>) m.getTarget()
							.getBodys(), message.getFault());
					if (isFailureThrow()) {
						throw new DipperException(message.getFault(),
								FLAG_FAILURE);
					}
				} else {
					throw new DipperException(message.getFault()); // FLAG_ERROR
				}
			}

		} catch (Exception e) {
			exceptionHandle(m, e);
		}

		LOG.debug("clientService waste time:[{}]ms", new Date().getTime()
				- clientTime.getTime());
		return m;
	}

	@SuppressWarnings("unchecked")
	private void exceptionHandle(Message m, Exception e) {

		if (e instanceof DipperException) {
			DipperException de = (DipperException) e;
			LOG.error("{}", de.getObject());
			// failure
			if (FLAG_FAILURE.equals(de.getFlag())) {
				throw de;
			}
			doErrorHandle((Map<String, Object>) m.getTarget().getBodys(),
					(Fault) de.getObject());
			if (isErrorThrow()) {
				throw de;
			}
		} else if (e instanceof RuntimeException) {
			LOG.error("", e);
			Fault fault = FaultFactory.create(SYS001,
					"系统异常[cause:send]:" + e.getMessage() + "!!");
			doErrorHandle((Map<String, Object>) m.getTarget().getBodys(), fault);
			if (isErrorThrow()) {
				throw new DipperException(fault);
			}
		} else {
			LOG.error("", e);
			Fault fault = FaultFactory.create(SYS003,
					ExceptionUtils.convertCN(e, channelId, SYS003_MSG));
			doErrorHandle((Map<String, Object>) m.getTarget().getBodys(), fault);
			if (isErrorThrow()) {
				throw new DipperException(fault);
			}
		}
	}

   

		

}
