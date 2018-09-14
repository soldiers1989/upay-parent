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
import com.pactera.dipper.presys.cp.xml.definition.ChannelBeanDefinition;
import com.upay.commons.util.DateUtil;


/**
 * 统一下单测试类
 * 
 * @author liu
 * 
 */

public class UnifiedOrderTest {
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

        map.put("tranCode", "UNIFIEDORDER");
        map.put("appId", "wx288339422065bc01");
        map.put("mchId", "1378612302");
        map.put("subMchId", "15234488"); 
        map.put("deviceInfo", "");// 可以为空
        map.put("nonceStr", "");
        map.put("body", "富农汇测试数据");
        map.put("attach", "红塔银行测试数据");
        map.put("outTradeNo", DateUtil.format(new Date(), "yyyyMMddHHmmss")+DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        map.put("productId", "");
        map.put("totalFee", "1");
        map.put("spbillCreateIp", "192.168.9.10");
        map.put("notifyUrl", "http://36.ab95569.com");
        map.put("tradeType", "JSAPI");
        map.put("subOpenid", "oAsfDv3zT7joNJnNqyLdB_2rgDPA");
        //map.put("openId", "oik1kvwI4Fi_OktGOgR_ZFClCB28");
        map.put("subAppid", "wx3950cc559a1244f4");
        //oAsfDv3zT7joNJnNqyLdB_2rgDPA 子商户的openid
        
        m = handler.handle(m);
        System.out.println(m);
        
       // System.out.println(Long.toString(System.currentTimeMillis()/1000));

    }
}
