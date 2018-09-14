/**
 * 
 */
package com.upay.gateway.server.zjpay.run;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import payment.api.system.PaymentEnvironment;

import com.pactera.dipper.dubbo.startup.StartupUtil;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.gateway.server.zjpay.util.InitPaymentEnv;




/**
 * @author hing
 * 
 */
public class Startup {
    public static void main(String[] args) throws Exception {
        StartupUtil su = StartupUtil.getInstance("upay-gateway-client-zjpay", args);
        su.setConfigLocations(new String[] { "classpath*:META-INF/spring/**/*.xml",
                                            "classpath*:META-INF/beans/*.xml",
                                            "classpath*:META-INF/comm/*.xml",
                                            "classpath*:META-INF/flow/*.xml",
                                            "classpath*:META-INF/dubbo/*.xml" });
        su.loadxml();
        InitPaymentEnv.init();
        Bootstrap.main(new String[] { "upay-gateway-server-zhongjin" });
    }
}
