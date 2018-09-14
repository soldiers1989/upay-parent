/**
 * 
 */
package com.upay.gateway.client.atalipay.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.pactera.dipper.core.Message;

/**
 * 支付宝授权
 * 
 * 
 */
public class AliPayUtils {

	private static AlipayConfig config;
	private static Logger log = LoggerFactory.getLogger(AliPayUtils.class);
	/**
	 * 刷新token
	 * */
	public static Map<String, Object> getRefreshToken(String appId,String grantType, String refreshToken,Message msg) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = getToken(appId,grantType, null, refreshToken);
			if (alipaySystemOauthTokenResponse.isSuccess()) {
				returnMap.put("userId",
						alipaySystemOauthTokenResponse.getUserId());
				returnMap.put("accessToken",
						alipaySystemOauthTokenResponse.getAccessToken());
				returnMap.put("refreshToken",
						alipaySystemOauthTokenResponse.getRefreshToken());
			} else {
				msg.getFault().setCode(
						alipaySystemOauthTokenResponse.getSubCode());
				msg.getFault().setMsg(
						alipaySystemOauthTokenResponse.getSubMsg());
				returnMap.put("code", alipaySystemOauthTokenResponse.getCode());
				returnMap.put("msg", alipaySystemOauthTokenResponse.getMsg());
				returnMap.put("subCode",
						alipaySystemOauthTokenResponse.getSubCode());
				returnMap.put("subMsg",
						alipaySystemOauthTokenResponse.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 获取userId和accessToken
	 * */
	public static Map<String, Object> getUserId(String appId,String grantType, String code, Message msg) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = getToken(appId,grantType, code, null);
			if (alipaySystemOauthTokenResponse.isSuccess()) {
				returnMap.put("userId",
						alipaySystemOauthTokenResponse.getUserId());
				returnMap.put("accessToken",
						alipaySystemOauthTokenResponse.getAccessToken());
				returnMap.put("refreshToken",
						alipaySystemOauthTokenResponse.getRefreshToken());
			} else {
				msg.getFault().setCode(
						alipaySystemOauthTokenResponse.getSubCode());
				msg.getFault().setMsg(
						alipaySystemOauthTokenResponse.getSubMsg());
				returnMap.put("code", alipaySystemOauthTokenResponse.getCode());
				returnMap.put("msg", alipaySystemOauthTokenResponse.getMsg());
				returnMap.put("subCode",
						alipaySystemOauthTokenResponse.getSubCode());
				returnMap.put("subMsg",
						alipaySystemOauthTokenResponse.getSubMsg());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 获取授权
	 * */
	public static AlipaySystemOauthTokenResponse getToken(String appId,String grantType, String code,
			String refreshToken) throws AlipayApiException {
		AlipayClient alipayClient = config.getAlipayClient(appId);
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		if (StringUtils.isNotBlank(grantType)) {
			request.setGrantType(grantType);
		}
		if (StringUtils.isNotBlank(code)) {
			request.setCode(code);
		}
		if (StringUtils.isNotBlank(refreshToken)) {
			request.setRefreshToken(refreshToken);
		}
		log.info("支付宝授权请求参数--authCode:"+request.getCode()+",grantType:"+request.getGrantType()+",refreshToken:"+request.getRefreshToken());
		AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
		log.info(alipayClient.sdkExecute(request).getBody());
		log.info("支付宝授权返回参数--accessToken:"+response.getAccessToken()+",userId:"+response.getUserId()+",refreshToken:"+response.getRefreshToken());
		return response;
	}

	public static void setConfig(AlipayConfig config) {
		AliPayUtils.config = config;
	}


}
