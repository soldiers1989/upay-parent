package com.dubhe.common.properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Properties处理器
 */
public class PropertiesUtil {
	private static Log log = LogFactory.getLog(PropertiesUtil.class);
    static PropertiesHelper pHelper =null;
	static {
		pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.APP);
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getpHelper().getValue(key,defaultValue);
	}
	
	public static String getProperty(String key) {
		return getpHelper().getValue(key);
	}

	private static PropertiesHelper getpHelper() {
		return pHelper;
	}

	
	
}
