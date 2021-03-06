package com.dubhe.common.properties;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dubhe.common.datastructure.Dto;
import com.dubhe.common.datastructure.impl.BaseDto;
import com.dubhe.common.util.GlobalConstants;

/**
 * Properties文件静态工厂
 * 
 * @author weizhao.dong
 */
public class PropertiesFactory {
	private static Log log = LogFactory.getLog(PropertiesFactory.class);
	/**
	 * 属性文件实例容器
	 */
	private static Dto container = new BaseDto();
	
	static{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = PropertiesFactory.class.getClassLoader();
				}
		     //加载属性文件global.app.properties
			 try {
				InputStream is = classLoader.getResourceAsStream("global.app.properties");
				PropertiesHelper ph = new PropertiesHelper(is);
				container.put(PropertiesFile.APP, ph);
				
			 } catch (Exception e1) {
				log.error(GlobalConstants.Exception_Head + "加载属性文件global.app.properties出错!");
				e1.printStackTrace();
			}
//			 //加载属性文件methodDes.xml
//			 try {
//					InputStream is = classLoader.getResourceAsStream("methodDes.xml");
//					PropertiesHelper ph = new PropertiesHelper(is,"xml");
//					container.put(PropertiesFile.METHODDES, ph);
//				 } catch (Exception e1) {
//					log.error(GlobalConstants.Exception_Head + "加载属性文件methodDes.xml出错!");
//					e1.printStackTrace();
//				}

	}
	
    /**
     * 获取属性文件实例
     * @param pFile 文件类型
     * @return 返回属性文件实例
     */
	public static PropertiesHelper getPropertiesHelper(String pFile){
		PropertiesHelper ph = (PropertiesHelper)container.get(pFile);
		return ph;
	}
}
