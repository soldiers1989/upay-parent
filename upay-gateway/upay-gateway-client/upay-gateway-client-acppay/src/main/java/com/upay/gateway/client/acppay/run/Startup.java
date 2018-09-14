package com.upay.gateway.client.acppay.run;

import com.pactera.dipper.dubbo.startup.StartupUtil;
import com.unionpay.acpmer.sdk.SDKConfig;

import java.io.File;
import java.net.URL;


/**
 * @author Hing
 * @since 2014年4月19日
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        StartupUtil su = StartupUtil.getInstance("upay-gateway-client-acppay", args);
        su.setConfigLocations(new String[] { "classpath*:META-INF/spring/**/*.xml",
                                            "classpath*:META-INF/beans/*.xml",
                                            "classpath*:META-INF/comm/*.xml",
                                            "classpath*:META-INF/flow/*.xml",
                                            "classpath*:META-INF/dubbo/*.xml" });
        su.loadxml();
        URL url = Startup.class.getClassLoader().getResource("acp_sdk.properties");
        File file = new File(url.getPath());
        SDKConfig.getConfig().loadPropertiesFromPath(file.getAbsoluteFile().getParent());
        su.start();
    }
}