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
 * 配置商户支付授权目录
 * 
 */
public class PayCatalogConfTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        
        String channel = "weixinmchconfcli";
        final String charsetName = "UTF-8";


//      ChannelBeanDefinition def = (ChannelBeanDefinition) ctx.getBean(channel);
//      IDipperHandler<Message> handler = def.getHandlerBeanDefinition().getCommServiceRef();
      IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXinMchconfHttpsClientHandler");
//       IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("weiXinHttpsClientHandler");
//       (IDipperHandler<Message>) ctx.getBean("orderQueryPayClientHandler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create("000000", ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();

        map.put("tranCode", "PAYCATALOGCONF");
        map.put("appId", "wx288339422065bc01");
        map.put("mchId", "1378612302");
        map.put("subMchId", "16548004");
        map.put("jsapiPath", "http://epay.test.ynhtbank.com.cn/Wechat/");//微信中的条码或者二维码信息
        
   

        m = handler.handle(m);
        System.out.println(m);
    }

}
