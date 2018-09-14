package com.dubhe.common.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.unionpay.acp.sdk.SDKConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import payment.api.system.PaymentEnvironment;

import com.dubhe.common.constants.AcpConstants;
import com.dubhe.common.properties.PropertiesFactory;
import com.dubhe.common.properties.PropertiesFile;
import com.dubhe.common.properties.PropertiesHelper;
import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.util.ArmConstants;
import com.dubhe.common.util.G4Utils;

/**
 * 系统启动监听器
 * 
 * @author weizha.dong
 */
public class SystemInitListener implements ServletContextListener {
	private boolean success = true;
	// 获取spring注入的bean对象
	//private WebApplicationContext springContext;
	private Logger log = LoggerFactory.getLogger(getClass());

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		systemStartup(sce.getServletContext());
	}

	/**
	 * 应用平台启动
	 */
	private void systemStartup(ServletContext servletContext) {
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.APP);
		String forceLoad = pHelper.getValue("forceLoad", ArmConstants.FORCELOAD_N);
		long start = System.currentTimeMillis();
		if (forceLoad.equalsIgnoreCase(ArmConstants.FORCELOAD_N)) {
			log.info("********************************************");
			log.info("中台系统开始启动...");
			log.info("********************************************");
		}
		SDKConfig.getConfig().loadPropertiesFromSrc();//加载银联ACP平台配置文件
		
		try {
            PaymentEnvironment.initialize(PropertiesUtil.getProperty(AcpConstants.ZJ_EBANK_PAY_URL));
        } catch (Exception e) {
            log.error("加载中金网银配置错误 ！！！！！！！！");
        }//加载中金网银配置文件
//		try {
//			springContext = WebApplicationContextUtils
//					.getWebApplicationContext(servletContext);
//
//		} catch (Exception e) {
//			success = false;
//			log.error("WebApplicationContextUtils获取bean出现异常", e);
//		}

		long timeSec = (System.currentTimeMillis() - start) / 1000;
		log.info("********************************************");
		if (success) {
			log.info("中台系统启动成功[" + G4Utils.getCurrentTime()
					+ "]");
			log.info("启动总耗时:"+timeSec+"秒");
		} else {
			log.info("中台系统启动失败[" + G4Utils.getCurrentTime()
					+ "]");
			log.info("启动总耗时:"+timeSec+"秒 ");
		}
		log.info("********************************************");
	}



}
