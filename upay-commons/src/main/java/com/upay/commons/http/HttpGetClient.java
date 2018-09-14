package com.upay.commons.http;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













import com.alibaba.fastjson.JSONObject;
import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.commons.dict.AppCodeDict;

import java.io.IOException;

/**
 * Created by bing on 16/11/26. HTTP客户端(get方式)
 */
public class HttpGetClient {
	private final static Logger LOG = LoggerFactory
			.getLogger(HttpGetClient.class);

	private static final int OK = 200;

	private static final int CHK_SUCC = 0;
	
//	public static String checkPassword(String url) {
//		// 调用加密控件返回密码
//		String password = "";
//		RequestConfig defaultRequestConfig = RequestConfig.custom()
//				.setSocketTimeout(5000).setConnectTimeout(5000)
//				.setConnectionRequestTimeout(5000)
//				.setStaleConnectionCheckEnabled(true).build();
//		// 创建HttpClientBuilder
//		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
//				.setDefaultRequestConfig(defaultRequestConfig);
//
//		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//		HttpGet httpGet = new HttpGet(url);
//
//		LOG.debug(httpGet.getRequestLine().toString());
//		try {
//			// 执行get请求
//			HttpResponse response = closeableHttpClient.execute(httpGet);
//			int statusCode = response.getStatusLine().getStatusCode();
//			// 响应状态
//			LOG.debug("调用加密服务返回状态       =======    status:" + response.getStatusLine());
//			if (OK == statusCode) {
//				LOG.debug("调用加密服务返回成功       =======    status:" + response.getStatusLine());
//				// 获取响应消息实体
//				HttpEntity entity = response.getEntity();
//				// 判断响应实体是否为空
//				if (entity != null) {
//					Header contentEncoding = entity.getContentEncoding();
//					String respStr = EntityUtils.toString(entity);
//					LOG.debug("contentEncoding:" + contentEncoding);
//					LOG.debug("response content:" + respStr);
//					if(StringUtils.isNotBlank(respStr)){
//						JSONObject json = (JSONObject) JSONObject.parse(respStr);
//						int status = (int)json.get("status");
//						String msg = (String)json.get("msg");
////						msg=new String(msg.getBytes("ISO-8859-1"),"UTF-8");
//						LOG.debug("加密机返回信息:" + msg);	
//						if(CHK_SUCC==status){
//							password=msg;
//						}else{
//							ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "加密控件异常");
//						}
//					}
//				}else{
//					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "加密控件异常");
//				}
//			}else{
//				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "第三方加密停止服务");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, e.getMessage());
//			
//		} finally {
//			try {
//				// 关闭流并释放资源
//				if (null != closeableHttpClient) {
//					closeableHttpClient.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return password;
//	}
}
