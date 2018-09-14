package com.dubhe.common.base;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dubhe.common.util.StreamUtil;

/**
 * 后台响应工厂
 * 
 * @author weizhao.dong
 * 
 */
public class ResponseFactory {
	private static Logger log = LoggerFactory.getLogger(ResponseFactory.class);

	public static ResponseVo responseSuccess(String msg) {
		return new ResponseVo(true, msg);
	}
	
	public static void responseSuccess(HttpServletResponse response, String msg) {
		try {
			StreamUtil.writeStream(response, new ResponseVo(true, msg));
		} catch (Exception e) {
			log.error("响应信息发生异常", e);
		}
	}

	public static ResponseVo responseError(String msg) {
		return new ResponseVo(false, msg);
	}
	
	public static void responseError(HttpServletResponse response, String msg) {
		try {
			StreamUtil.writeStream(response, new ResponseVo(false, msg));
		} catch (Exception e1) {
			log.error("响应信息发生异常", e1);
		}
	}
	
	public static void responseError(HttpServletResponse response, String msg,
			Exception e) {
		try {
			StreamUtil.writeStream(response, new ResponseVo(false, msg));
			log.error(msg, e);
		} catch (Exception e1) {
			log.error("响应信息发生异常", e1);
		}
	}
	
	
}
