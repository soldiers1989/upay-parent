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
 *新加微信刷卡支付转换短链接API
 * 
 */
public class ShorturlTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        
        String channel = "weixintoolsli";
        final String charsetName = "UTF-8";


//      ChannelBeanDefinition def = (ChannelBeanDefinition) ctx.getBean(channel);
//      IDipperHandler<Message> handler = def.getHandlerBeanDefinition().getCommServiceRef();
      IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXintoolsClientHandler");
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

        // 订单查询
        map.put("tranCode", "SHORTURL");
        map.put("appId", "wxdace645e0bc2c424");
        map.put("mchId", "1900008731");
        map.put("subAppid", "");
        map.put("subMchId", "13968111");
        map.put("deviceInfo", "");// 可以为空
        map.put("nonceStr", "");
        map.put("sign", "");    
        //longUrl 字段需要传二维码链接转成短链接真实的
        map.put("longUrl", "weixin：//wxpay/bizpayurl?sign=3127BD1E8E96D72460832C7870385B30&appid=wxdace645e0bc2c424&mch_id=1900008731&product_id=1008450740201417220000358851&time_stamp=20160819&nonce_str=81ad898e05d1445a9dfefe07c2f44c36");
        
   

        m = handler.handle(m);
        System.out.println(m);
    }

}
