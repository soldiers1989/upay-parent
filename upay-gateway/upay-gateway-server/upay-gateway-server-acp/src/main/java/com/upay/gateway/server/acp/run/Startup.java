/**
 * 
 */
package com.upay.gateway.server.acp.run;

import java.io.File;
import java.net.URL;

import com.pactera.dipper.dubbo.startup.StartupUtil;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.gateway.server.acp.util.SDKConfig;


/**
 * @author hing
 * 
 */
public class Startup {
    public static void main(String[] args) {
        StartupUtil su = StartupUtil.getInstance("upay-gateway-server-acp", args);
        su.setConfigLocations(new String[] { "classpath*:META-INF/spring/**/*.xml",
                                            "classpath*:META-INF/beans/*.xml",
                                            "classpath*:META-INF/comm/*.xml",
                                            "classpath*:META-INF/flow/*.xml",
                                            "classpath*:META-INF/dubbo/*.xml" });
        su.loadxml();
        URL url = Startup.class.getClassLoader().getResource("acp_sdk.properties");        
        File file = new File(url.getPath());
        SDKConfig.getConfig().loadPropertiesFromPath(file.getAbsoluteFile().getParent());
        Bootstrap.main(new String[] { "upay-gateway-server-acp" });
    }
}
