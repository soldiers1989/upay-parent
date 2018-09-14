package com.upay.gateway.client.esb.run;

import com.pactera.dipper.dubbo.startup.StartupUtil;


/**
 * @author Hing
 * @since 2014年4月19日
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil su = StartupUtil.getInstance("upay-gateway-client-esb", args);
        su.setConfigLocations(new String[] { "classpath*:META-INF/spring/**/*.xml",
                                            "classpath*:META-INF/beans/*.xml",
                                            "classpath*:META-INF/comm/*.xml",
                                            "classpath*:META-INF/flow/*.xml",
                                            "classpath*:META-INF/dubbo/*.xml" });
        su.loadxml();
        su.start();
    }
}