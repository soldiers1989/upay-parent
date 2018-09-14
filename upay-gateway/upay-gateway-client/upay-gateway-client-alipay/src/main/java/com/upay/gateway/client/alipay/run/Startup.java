package com.upay.gateway.client.alipay.run;

import com.pactera.dipper.dubbo.startup.StartupUtil;


/**
 * 支付宝支付客户端通讯启动类
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil su = StartupUtil.getInstance("upay-gateway-client-alipay", args);
        su.setConfigLocations(new String[] { "classpath*:META-INF/spring/**/*.xml",
                                            "classpath*:META-INF/beans/*.xml",
                                            "classpath*:META-INF/comm/*.xml",
                                            "classpath*:META-INF/flow/*.xml",
                                            "classpath*:META-INF/dubbo/*.xml" });
        su.loadxml();
        su.start();
    }
}