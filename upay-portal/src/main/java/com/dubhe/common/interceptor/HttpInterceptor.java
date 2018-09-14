package com.dubhe.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dubhe.common.exception.IllegalmessageException;
import com.dubhe.common.json.AppHead;
import com.dubhe.common.json.ChnlMessage;
import com.dubhe.common.json.SysHead;
import com.dubhe.common.properties.PropertiesFactory;
import com.dubhe.common.properties.PropertiesHelper;
import com.dubhe.common.util.GlobalConstants;
import com.dubhe.common.util.StreamUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;

/**
 * Created by dongweizhao on 15-4-28.
 * http挡板
 */
public class HttpInterceptor implements HandlerInterceptor {
    private static final long serialVersionUID = 1358600090729208361L;
    private Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        request.getParameter("");
        // 读取请求报文数据，转换成Json对象
    	String receive = request.getParameter("msg");
    	if (receive == null) {
            throw new IllegalmessageException();
        }
    	logger.info("receive:{}", "\n" + JSON.toJSONString(JSON.parse(receive),true));
        ChnlMessage<SysHead, AppHead,Object> reqMsg =
                JSON.parseObject(receive, new TypeReference<ChnlMessage<SysHead, AppHead, Object>>() {
                });
        String jsonStr = this.getPropertiesHelper().getValue(reqMsg.getSyshead().getTransCode());
        logger.info("获取报文信息:[{}]",jsonStr);
        StreamUtil.writeStream(response, jsonStr);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public PropertiesHelper getPropertiesHelper() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = PropertiesFactory.class.getClassLoader();
        }
        PropertiesHelper ph = null;
        //加载属性文件global.app.properties
        try {
            InputStream is = classLoader.getResourceAsStream("global.test.xml");
            ph = new PropertiesHelper(is, "xml");

        } catch (Exception e1) {
            logger.error(GlobalConstants.Exception_Head + "加载属性文件global.app.properties出错!");
            e1.printStackTrace();
        }
        return ph;
    }

}
