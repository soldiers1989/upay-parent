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
 * 公共号退款查询
 * 
 */
public class RefundQueryTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        
        String channel = "weixincli";
        final String charsetName = "UTF-8";


//      ChannelBeanDefinition def = (ChannelBeanDefinition) ctx.getBean(channel);
//      IDipperHandler<Message> handler = def.getHandlerBeanDefinition().getCommServiceRef();
      IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXinClientHandler");
//       IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("weiXinHttpsClientHandler");
//       (IDipperHandler<Message>) ctx.getBean("orderQueryPayClientHandler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create("000000", ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();

        map.put("sysDate", new Date());
        map.put("sysTime", new Date());
        String sysDate = new SimpleDateFormat("yyyyMMdd").format((Date) map.get("sysDate"));
        String sysTime = new SimpleDateFormat("HHmmss").format((Date) map.get("sysTime"));
        map.put("sysSeq", sysDate + sysTime);

        // 退款查询
        map.put("tranCode", "REFUNDQUERY");
        map.put("appId", "wxdace645e0bc2c424");
        map.put("mchId", "1900008951");
        map.put("subAppid", "");
        map.put("subMchId", "26460047");
        map.put("deviceInfo", "");// 可以为空
        map.put("nonceStr", "");
        map.put("sign", "");
        
        
        map.put("outTradeNo", "1160150613");

        m = handler.handle(m);
        System.out.println(m);

    }
}
