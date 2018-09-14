package com.upay.gateway.client.sms.util;

/**
 * 启动短信服务
 * 
 * @author 车静
 * 
 */
public class IMSLongConnectionAPIIEventAdapter {

    // 使用者自己定义的上下文管理对象
    protected final static SmsContext SMSCONTEXT = SmsContext.getInstance();
    protected static int CONNECTTIMEOUT = 10000;
    protected static int SOTIMEOUT = 30000;
    protected static String SMSURL = "http://197.3.11.76:8504";
    protected static String CHARSET = "GBK";

}
