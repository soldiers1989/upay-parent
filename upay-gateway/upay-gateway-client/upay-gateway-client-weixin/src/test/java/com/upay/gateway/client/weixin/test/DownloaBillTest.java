package com.upay.gateway.client.weixin.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;


/**
 * 对账单下载测试
 * 
 */
public class DownloaBillTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        String channel = "weixincli";
        final String charsetName = "UTF-8";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXinClientHandler");
        // IDipperHandler<Message> handler = (IDipperHandler<Message>)
        // ctx.getBean("weiXinHttpsClientHandler");
        // (IDipperHandler<Message>) ctx.getBean("orderQueryPayClientHandler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create("000000", ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();

        map.put("sysDate", new Date());
        map.put("sysTime", new Date());
        String sysDate = new SimpleDateFormat("yyyyMMdd").format((Date) map.get("sysDate"));
        String sysTime = new SimpleDateFormat("HHmmss").format((Date) map.get("sysTime"));

        // 对账单下载
        map.put("tranCode", "DOWNLOADBILL");
        map.put("billDate", "20170306");
        map.put("billType", "ALL");       
        map.put("nonceStr", "");
        
        
        map.put("appId", "wx288339422065bc01");
        map.put("mchId", "1378612302");

        m = handler.handle(m);
        Map<String, Object> body = ((Map<String, Object>) m.getTarget().getBodys());
        
        System.out.println("---------------------:"+body.get("fileName"));
       
    }

}
