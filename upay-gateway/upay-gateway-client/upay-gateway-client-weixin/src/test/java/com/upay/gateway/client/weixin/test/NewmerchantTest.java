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
 * 新增商户资料（二类商户）
 */

public class NewmerchantTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        String channel = "weixinhttpscli";
        final String charsetName = "UTF-8";


//      ChannelBeanDefinition def = (ChannelBeanDefinition) ctx.getBean(channel);
//      IDipperHandler<Message> handler = def.getHandlerBeanDefinition().getCommServiceRef();
      IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXinMerchHttpsClientHandler");
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

        map.put("tranCode", "NEWMERCHANT");
        map.put("appId", "wx288339422065bc01");
        map.put("mchId", "1378612302");
        map.put("sign", "");
        
        
        map.put("merchantName", "红塔集团烟草有限公司");       
        map.put("merchantShortname", "红塔集团烟草");
        map.put("servicePhone", "087796522");
        map.put("contact", "张国荣");
        map.put("contactPhone", "15201513057");
        map.put("contactEmail", "66666666@qq.com");
        map.put("business", "209");
        map.put("merchantRemark", "红塔烟草专卖4");

        m = handler.handle(m);
        System.out.println(m);

    }
}
