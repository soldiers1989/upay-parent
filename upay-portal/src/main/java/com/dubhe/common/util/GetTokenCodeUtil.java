package com.dubhe.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pactera.dipper.core.IDipperCached;
import com.upay.commons.constants.CacheConstants;

/**
 * @author 获取token ID
 */
public class GetTokenCodeUtil extends HttpServlet {

	private Logger logger = Logger.getLogger(GetTokenCodeUtil.class);

	
	private IDipperCached cache;

	private static final long serialVersionUID = -5813134629255375160L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		cache = (IDipperCached) WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext()).getBean(
						"DIPPER_REDIS_CLIENT");
		
		String uuid = G4Utils.getUUID();
		String concat = CacheConstants.TOKEN_CODE.concat(String.valueOf(req.getParameter("sessionId")));
		cache.set(concat, uuid);
		PrintWriter writer = resp.getWriter();
//		System.out.println("设置  token Key:  "+concat+"       Token value:"+uuid);
		logger.debug("设置  token Key:  "+concat+"       Token value:"+uuid);
		
//		System.out.println("获取设置的  token Key:  "+concat+"       Token value:"+cache.get(concat));
//		writer.print("{tokenId:'"+uuid+"'}");
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("tokenId", uuid);
		try {
			StreamUtil.writeStream(resp, jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
