package com.upay.gateway.client.atalipay.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeOrderSettleRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AntMerchantExpandIndirectCreateRequest;
import com.alipay.api.request.AntMerchantExpandIndirectModifyRequest;
import com.alipay.api.request.AntMerchantExpandIndirectQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeOrderSettleResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AntMerchantExpandIndirectCreateResponse;
import com.alipay.api.response.AntMerchantExpandIndirectModifyResponse;
import com.alipay.api.response.AntMerchantExpandIndirectQueryResponse;
import com.pactera.dipper.core.Message;
import com.upay.gateway.client.atalipay.util.AlipayUtil;

public class Alipay {
	private AlipayConfig config;
	private static Logger log = LoggerFactory.getLogger(Alipay.class);

	/**
	 * 被扫支付
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 * @throws Exception
	 */
	public Map pay(Map<String, Object> reqParam, Message message,
			String app_id) throws AlipayApiException {
		log.debug("被扫支付");
		AlipayTradePayRequest request = new AlipayTradePayRequest();
		// request.setNotifyUrl(config.getNotifyUrl());
		request.setBizContent(JSONObject.toJSON(reqParam).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradePayResponse response = null;

		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		// JSONObject body = JSONObject.parseObject(response.getBody());
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_pay_response");
		

	}

	/**
	 * 订单查询
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map query(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("订单查询");
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeQueryResponse response = null;

		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_query_response");
		
	}

	/**
	 * 订单预创建
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map precreate(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("订单预创建");
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setNotifyUrl(config.getNotifyUrl());
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradePrecreateResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_precreate_response");

	}

	/**
	 * 订单创建
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map create(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("订单创建");
		AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
		request.setNotifyUrl(config.getNotifyUrl());
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeCreateResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_create_response");
	}

	/**
	 * 退款查询
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map refundQuery(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("退款查询");
		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeFastpayRefundQueryResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_fastpay_refund_query_response");
	}

	/**
	 * 统一收单交易结算
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map orderSettle(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("收单结算");
		AlipayTradeOrderSettleRequest request = new AlipayTradeOrderSettleRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeOrderSettleResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_order_settle_response");
	}

	/**
	 * 关闭订单
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map close(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("关闭订单");
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		request.setNotifyUrl(config.getNotifyUrl());
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeCloseResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_close_response");
	}

	/**
	 * 交易撤销
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map cancel(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("交易撤销");
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeCancelResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_cancel_response");
	}

	/**
	 * 退款
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException
	 */
	public Map refund(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("退款");
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setNotifyUrl(config.getNotifyUrl());
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayTradeRefundResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("alipay_trade_refund_response");
	}

	/**
	 * 子商户创建
	 * 
	 * @param reqData
	 * @return 
	 * @return
	 * @throws AlipayApiException 
	 */
	public Map subMchAdd(Map<String, Object> targetBodyMap, Message message,
			String app_id) throws AlipayApiException {
		log.info("子商户创建");
		AntMerchantExpandIndirectCreateRequest request = new AntMerchantExpandIndirectCreateRequest();
		request.setNotifyUrl(config.getNotifyUrl());
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		AntMerchantExpandIndirectCreateResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		log.info("请求报文：：" + request.getBizContent());
		response = alipayClient.execute(request);
		//支付宝新增商户时的参数
		byte[] alipayParam = SerializationUtils.serialize(targetBodyMap);
		log.info("存入数据库参数" + targetBodyMap);
		Map<String, Object> resultMap = (Map<String, Object>) SerializationUtils.deserialize(alipayParam);
		log.info("解析后参数" + resultMap);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		Map respMap = (Map) map.get("ant_merchant_expand_indirect_create_response");
		respMap.put("alipay_param", alipayParam);
		return respMap;
	}
	
	/**
	 * 子商户查询
	 * 
	 * @return 
	 * @return
	 * @throws AlipayApiException 
	 */
	public Map subMchQuery(Map<String, Object> targetBodyMap,
			Message message, String appId) throws AlipayApiException {
		log.info("子商户查询");
		AntMerchantExpandIndirectQueryRequest request = new AntMerchantExpandIndirectQueryRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		AntMerchantExpandIndirectQueryResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(appId);
		log.info("请求报文：：" + request.getBizContent());
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("ant_merchant_expand_indirect_query_response");
	}
	
	/**
	 * 子商户修改
	 * 
	 * @return 
	 * @return
	 * @throws AlipayApiException 
	 */
	public Map subMchUpdate(Map<String, Object> targetBodyMap,
			Message message, String appId) throws AlipayApiException {
		log.info("子商户修改");
		AntMerchantExpandIndirectModifyRequest request = new AntMerchantExpandIndirectModifyRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		AntMerchantExpandIndirectModifyResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(appId);
		log.info("请求报文：：" + request.getBizContent());
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		return (Map) map.get("ant_merchant_expand_indirect_modify_response");
	}

	/**
	 * 下载对账单
	 * 
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	public Map billDownload(Map<String, Object> targetBodyMap,
			Message message, String app_id) throws Exception {
		log.info("下载对账单");
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		request.setBizContent(JSONObject.toJSON(targetBodyMap).toString());
		log.info("请求报文：：" + request.getBizContent());
		AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
		AlipayClient alipayClient = config.getAlipayClient(app_id);
		response = alipayClient.execute(request);
		log.info("响应报文：：" + response.getBody());
		Map map = AlipayUtil.parseJSON2Map(response.getBody());
		map = (Map) map
				.get("alipay_data_dataservice_bill_downloadurl_query_response");
		if (AlipayConstants.SUCCESS.equals((String) map.get("code"))) {
			downloadBill(map, message,config.getAlipayFileAddr(),config.getAlipayFileAddrReplace());
		}
		return map;
	}

	/**
	 * 下载对账单
	 * 
	 * @param map
	 * @param message
	 * @param urlStr
	 * @throws Exception
	 */
	private void downloadBill(Map map, Message message,String fileAddr,String fileAddrReplace) throws Exception {

		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		String url = (String) map.get("bill_download_url");
		
		//当走专线的时候，下载对账单数据需要替换支付方返回的域名
		log.debug("支付宝下载对账单的地址替换IP前："+url);
		url=url.replaceAll(fileAddr, fileAddrReplace);
		log.debug("支付宝下载对账单的地址替换IP后："+url);
		
//		url = url.replace("http", "https");
		urlfile = new URL(url);
		httpUrl = (HttpURLConnection) urlfile.openConnection();
		httpUrl.connect();
		bis = new BufferedInputStream(httpUrl.getInputStream());
		bos = new ByteArrayOutputStream();
		int len = 1024;
		byte[] b = new byte[len];
		while ((len = bis.read(b)) != -1) {
			bos.write(b, 0, len);
		}
		bos.flush();
		bis.close();
		httpUrl.disconnect();
		byte[] ba = bos.toByteArray();
		log.debug("支付宝对账文件大小："+ba.length);
		map.put("data", ba);// 将对账单文件的byte数组压入json
		log.debug("支付宝对账数据："+map);
		message.getTarget().setBody(map);

	}

	private String parseSuccessResponse(JSONObject jsonObject) {
		return jsonObject.toJSONString();
	}

	private String parseFailResponse(Exception e) {
		JSONObject jsonObject = new JSONObject();
		if (e.getCause() instanceof UnknownHostException
				|| e.getCause() instanceof SocketTimeoutException) {// 网关超时
			jsonObject.put("code", "");
			jsonObject.put("msg", "网关访问超时");
			jsonObject.put("sub_code", "");
			jsonObject.put("sub_msg", "网关访问超时");
		} else {
			/*
			 * jsonObject.put("code", Constants.ResponseCode.EXCEPTION);
			 * jsonObject.put("msg", e.getMessage()); jsonObject.put("sub_code",
			 * Constants.ResponseCode.EXCEPTION); jsonObject.put("sub_msg",
			 * e.getMessage());
			 */
		}
		return jsonObject.toJSONString();
	}

	public AlipayConfig getConfig() {
		return config;
	}

	public void setConfig(AlipayConfig config) {
		this.config = config;
	}

	public byte[] loopMap(Map<String, Object> targetBodyMap){
		byte[] alipayUpdateParam = null ;
		Iterator<Entry<String, Object>> it = targetBodyMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> next = it.next();
			if("alipayUpdateParam".equals(next.getKey())){
				alipayUpdateParam = (byte[]) next.getValue();
				targetBodyMap.remove(next.getKey());
				break;
			}
		}
		return alipayUpdateParam;
	}
	
//	public static void main(String [] args){
//		String a="http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=X&userId=X&fileType=X&bizDates=X&downloadFileName";
//		System.out.println(a.replaceAll("dwbillcenter.alipay.com", "197.21.4.160"));
//	}
	
	public static String getOutTradeNo() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
}
