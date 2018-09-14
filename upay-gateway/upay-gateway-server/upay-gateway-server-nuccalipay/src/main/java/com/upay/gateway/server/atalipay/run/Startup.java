/**
 * 
 */
package com.upay.gateway.server.atalipay.run;
import com.pactera.dipper.dubbo.startup.StartupUtil;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.gateway.server.atalipay.util.AlipayConfig;




/**
 * @author hing
 * 
 */
public class Startup {
    public static void main(String[] args) throws Exception {
        StartupUtil su = StartupUtil.getInstance("upay-gateway-server-atalipay", args);
        su.setConfigLocations(new String[] { "classpath*:META-INF/spring/**/*.xml",
                                            "classpath*:META-INF/beans/*.xml",
                                            "classpath*:META-INF/comm/*.xml",
                                            "classpath*:META-INF/flow/*.xml",
                                            "classpath*:META-INF/dubbo/*.xml" });
        su.loadxml();
        AlipayConfig.init();
        Bootstrap.main(new String[] { "upay-gateway-server-atalipay" });
    }
}
