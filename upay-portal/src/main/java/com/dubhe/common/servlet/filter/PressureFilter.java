/**
 * 
 */
package com.dubhe.common.servlet.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.util.StreamUtil;


/**
 * 限流过滤器
 * 
 * @author chen.nie
 *
 */
public class PressureFilter extends BaseFilter {

    private long perMaxRequestCount = 0l;// 单个限流请求个数

    private static AtomicLong al = new AtomicLong(0l);


    @Override
    public void init(FilterConfig config) throws ServletException {
        String maxAcitiveRequest = config.getInitParameter("maxAcitiveRequest");
        //Preconditions.checkArgument(StringUtils.isNumeric(maxAcitiveRequest));
        perMaxRequestCount = Long.parseLong(maxAcitiveRequest);
        super.init(config);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (al.incrementAndGet() <= perMaxRequestCount) {
            try {
                chain.doFilter(request, response);
            } finally {
                al.decrementAndGet();
            }
        } else {
            HttpServletResponse rsp = (HttpServletResponse) response;
            Map<String, Object> rspMsg = new HashMap<String, Object>();
            rspMsg.put(ReqRspConstants.RSP_KEY, "0X000");
            rspMsg.put(ReqRspConstants.RSP_MSG, "请求秒杀失败，下次手速快一点");
            try {
                StreamUtil.writeStream(rsp, rspMsg);
            } catch (Exception e) {
                logger.errorLog(e);
            }
        }
    }
}
