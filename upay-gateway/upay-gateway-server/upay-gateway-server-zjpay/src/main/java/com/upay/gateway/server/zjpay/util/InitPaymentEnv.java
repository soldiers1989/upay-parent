/**
 * 
 */
package com.upay.gateway.server.zjpay.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import payment.api.system.PaymentEnvironment;

/**
 * 
 * 中金支付环境初始化
 * 
 * @author: David
 * @CreateDate:2015年12月18日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2015年12月18日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class InitPaymentEnv {

	private static final Logger log = LoggerFactory
			.getLogger(InitPaymentEnv.class);

	/**
	 * 中金支付环境初始化
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception {

		InitPaymentEnv initService = new InitPaymentEnv();
		FileInputStream inStream = null;
		try {
			// 配置文件读取
			URL url = InitPaymentEnv.class.getClassLoader().getResource(
					"zjpay_config.properties");
			inStream = new FileInputStream(new File(url.toURI()));
			Properties props = new Properties();
			props.load(inStream);
			if (!props.isEmpty()) {
				log.info("**************InitPaymentEnv初始化start**********");
				if (props.get("payment.config.path") != null
						&& !"".equals(props.get("payment.config.path"))) {
					InitParam.PAYMENT_CONFIG_PATH = (String) props
							.get("payment.config.path");
					// 初始化支付环境
					// PaymentEnvironment.initialize((String)
					// props.get("payment.config.path"));
					PaymentEnvironment
							.initialize(InitParam.PAYMENT_CONFIG_PATH);
				} else {
					log.info(
							"**************InitPaymentEnv获取值失败使用默认值[{}]**********",
							"zjpay");
					PaymentEnvironment
							.initialize(InitParam.PAYMENT_CONFIG_PATH);
				}
				log.info("**************InitPaymentEnv初始化end**********");
			} else {
				log.debug("使用默认初始化参数配置[{}]", "zjpay");
				PaymentEnvironment.initialize(InitParam.PAYMENT_CONFIG_PATH);

			}

		} catch (Exception e) {
			// 如果报错 配置文件不整合
			log.error("因配置文件zjpay_config.properties故障，ZJPAY初始化环境失败...[{}]", e);
			throw e;
		} finally {
			if (null != inStream) {
				inStream.close();
			}
		}
	}
}
