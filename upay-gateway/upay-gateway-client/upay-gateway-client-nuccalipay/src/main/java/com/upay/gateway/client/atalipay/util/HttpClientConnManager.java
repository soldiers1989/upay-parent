package com.upay.gateway.client.atalipay.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.commons.dict.AppCodeDict;

/**
 * @description: HTTPS请求
 */
public class HttpClientConnManager {
	/**
	 * 作为客户端通讯使用协议
	 */
	private static final String[] EPCC_PROTOCOLS = new String[] { "TLSv1.2" };
	/**
	 * 作为客户端通讯使用算法套件TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
	 * TLS_RSA_WITH_AES_256_GCM_SHA384
	 */
	private static final String[] EPCC_CIPHERS = new String[] {
			"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
			"TLS_RSA_WITH_AES_256_GCM_SHA384" };
	private ConnectionKeepAliveStrategy keepAliveStrategy;
	// /**
	// * 日志
	// */
	private static final Logger logger = LoggerFactory
			.getLogger(HttpClientConnManager.class);
	/**
	 * httpclient连接池管理器
	 */
	protected PoolingHttpClientConnectionManager connMgr = null;
	/**
	 * 请求配置
	 */
	protected RequestConfig requestConfig = null;
	/**
	 * 通信连接超时
	 */
	protected int connectTimeout = 10000;
	/**
	 * 通信读取超时
	 */
	protected int readTimeout = 30000;
	/**
	 * 通信连接池获取连接超时
	 */
	protected int requestTimeout = 10000;
	protected int httpKeepAliveTimeout = 60000;
	/**
	 * 连接池大小
	 */
	protected int connPoolSize = 100;
	/**
	 * 字符编码
	 */
	protected static final String SEND_ENCODING = "UTF-8";

	/**
	 * 发送 SSL POST 请求（HTTPS）
	 * 
	 * @param url
	 *            请求url
	 * @param reqXml
	 *            请求报文
	 * @param paramMap
	 *            请求参数map
	 * @return String
	 */
	public String send(String url, String reqXml, Map<String, Object> paramMap) {
		CloseableHttpClient httpClient = getHttpClient();
		// 创建HttpPost,加载url
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.setEntity(new StringEntity(reqXml, SEND_ENCODING));
		// 设置http头部域
		httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
		CloseableHttpResponse response = null;
		String httpStr = "";
		try {
			Iterator<Entry<String, Object>> it = paramMap.entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				httpPost.setHeader(entry.getKey(), (String) entry.getValue());
			}
			// 通讯
			logger.info("http通讯开始", url, null);
			response = httpClient.execute(httpPost);
			// 获取通讯状态码
			int statusCode = response.getStatusLine().getStatusCode();
			// 如果返回非200，抛出通讯异常错误
			if (HttpStatus.SC_OK != statusCode) {
				logger.info("http通讯状态码非200：", String.valueOf(statusCode), null);
			}
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, SEND_ENCODING);
			logger.info(httpStr);
		} catch (ConnectTimeoutException | SocketTimeoutException e) {
			((Throwable) e).printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(response);
		}
		return httpStr;
	}

	/**
	 * 项目启动时运行，初始化连接池及连接配置
	 */
	public HttpClientConnManager() {
		SSLConnectionSocketFactory sslConnFactory = null;
		try {
			@SuppressWarnings("deprecation")
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						@Override
						public boolean isTrusted(
								java.security.cert.X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			logger.info("create http connection pool",
					"创建sslContext成功，并设置信任所有证书", null);

			sslConnFactory = new SSLConnectionSocketFactory(sslContext,
					EPCC_PROTOCOLS, EPCC_CIPHERS, new HostnameVerifier() {
						@Override
						public boolean verify(String s, SSLSession sslSession) {
							return true;
						}
					});

			logger.info("create http connection pool",
					"创建sslConnFactory成功，并设置hostname校验为true", null);

		} catch (Exception e) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "初始化连接池失败");
			// throw new ChannelCoreException(
			// ChannelCoreErrorCode.HTTP_COMMUNICATION_ERROR
			// .getErrorCode(),
			// e.getMessage(), "初始化连接池失败", e);
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("https", sslConnFactory)
				.register("http", new PlainConnectionSocketFactory()).build();
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// 设置连接池大小
		connMgr.setMaxTotal(connPoolSize);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时时间
		configBuilder.setConnectTimeout(connectTimeout);
		// 设置读取超时时间
		configBuilder.setSocketTimeout(readTimeout);
		// 设置从连接池获取连接实例超时时间
		configBuilder.setConnectionRequestTimeout(requestTimeout);

		requestConfig = configBuilder.build();
		keepAliveStrategy = new EpccConnectionKeepAliveStrategy(
				httpKeepAliveTimeout);

		logger.info("create http connection pool", "初始化连接池成功，并成功设置连接属性", null);
	}

	/**
	 * 清除过期连接线程
	 */
	@Scheduled(initialDelayString = "12000", fixedRateString = "30000")
	public void closeExpiredConnections() {
		logger.info("closeExpiredConnections", "closeExpiredConnections", "");
		// 清除过期连接
		connMgr.closeExpiredConnections();
	}

	/**
	 * 获取httpclient连接
	 * 
	 * @return CloseableHttpClient
	 */
	protected CloseableHttpClient getHttpClient() {
		// 创建httpclient
		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setConnectionManager(connMgr)
				// 设置连接池
				.disableAutomaticRetries()
				// 禁止重试
				.setConnectionReuseStrategy(
						DefaultConnectionReuseStrategy.INSTANCE)
				.setKeepAliveStrategy(keepAliveStrategy).build();
		logger.debug("build httpClient", "创建httpClient成功", null);
		return httpClient;
	}

	/**
	 * 关闭连接
	 * 
	 * @param response
	 */
	private void close(CloseableHttpResponse response) {
		try {
			if (null != response) {
				// 销毁
				EntityUtils.consume(response.getEntity());
				response.close();
			}
		} catch (IOException e) {
			logger.error("", "", "", e);
		}
	}

	private static class EpccConnectionKeepAliveStrategy implements
			ConnectionKeepAliveStrategy {

		private int httpKeepAliveTimeout;

		public EpccConnectionKeepAliveStrategy(int httpKeepAliveTimeout) {
			this.httpKeepAliveTimeout = httpKeepAliveTimeout;
		}

		@Override
		public long getKeepAliveDuration(HttpResponse response,
				org.apache.http.protocol.HttpContext context) {
			// TODO Auto-generated method stub
			return httpKeepAliveTimeout;
		}
	}

}
