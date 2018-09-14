package com.dubhe.common.servlet.listener;

import org.springframework.web.servlet.DispatcherServlet;

import com.alibaba.dubbo.config.ProtocolConfig;

/** 
 * @ClassName: DispatcherServletImpl 
 * @Description: TODO
 * @author dongweizhao 
 * @date 2015年6月3日 下午9:22:40 
 *  
 */

public class DispatcherServletImpl extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		super.destroy();
        ProtocolConfig.destroyAll();
	}

}
