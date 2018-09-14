package com.dubhe.common.servlet.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.servlet.wrapper.XssHttpServletRequestWrapper;
import com.dubhe.common.util.G4Utils;

public class XssFilter implements Filter {
	private Logger logger=LoggerFactory.getLogger(XssFilter.class);
	FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	
    	    HttpServletRequest req = (HttpServletRequest) request;
    	    HttpServletResponse rsp=(HttpServletResponse) response;
       	String refererHeader = req.getHeader("referer");
       	String domainName=PropertiesUtil.getProperty("domainName");
		if(refererHeader != null && !refererHeader.startsWith(domainName)) {
			logger.error("Referer domain " + refererHeader + " does not match regex: " + domainName);
			rsp.sendError(404);
			return;
		}
	    	
		if (refererHeader != null && Boolean.getBoolean(PropertiesUtil.getProperty("refererMatchDomain"))) {
			String url = req.getRequestURL().toString();
			String requestProtocolAndDomain = G4Utils.httpProtocolAndDomain(url);
			String refererProtocolAndDomain = G4Utils.httpProtocolAndDomain(refererHeader);
			if (!refererProtocolAndDomain.equals(requestProtocolAndDomain)) {
				logger.error("Referer domain " + refererHeader + " does not match request domain: " + url);
				rsp.sendError(404);
				return;
			}
			return;
		}
        
		chain.doFilter(new XssHttpServletRequestWrapper(
	                (HttpServletRequest) request), response);
      
    }
}
