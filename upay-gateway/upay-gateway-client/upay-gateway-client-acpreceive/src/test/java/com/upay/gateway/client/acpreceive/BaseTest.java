package com.upay.gateway.client.acpreceive;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.unionpay.acp.sdk.SDKConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/*/*.xml")
public class BaseTest extends AbstractJUnit4SpringContextTests {

    public Message getMessage(Map<String, Object> bodys, String serviceCode) {

        try {
            URL url =
                    com.pactera.dipper.presys.cp.run.Startup.class.getClassLoader().getResource(
                        "acp_sdk.properties");
            File file = new File(url.getPath());
            SDKConfig.getConfig().loadPropertiesFromPath(file.getAbsoluteFile().getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (serviceCode == null) {
            serviceCode = "USR00001";
        }
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("serviceCode", serviceCode);
        headers.put("chnlId", "02");
        headers.put("platForm", "22");
        headers.put("sessionId", String.valueOf(new Random().nextInt(1000000000)));
        headers.put("address", "192.168.1.1");

        Message m =
                MessageFactory.create("1111", MessageFactory.createSimpleMessageInstance(),
                    MessageFactory.createSimpleMessage(headers, bodys),
                    FaultFactory.create("0000000000", ""), new ArrayList());

        return m;

    }
}
