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
 * 公共号申请退款测试类
 */
public class RefundTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        
        String channel = "weixinhttpscli";
        final String charsetName = "UTF-8";


//      ChannelBeanDefinition def = (ChannelBeanDefinition) ctx.getBean(channel);
//      IDipperHandler<Message> handler = def.getHandlerBeanDefinition().getCommServiceRef();
      IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXinHttpsClientHandler");
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

        // 申请退款
        map.put("tranCode", "REFUNDPAY");
        map.put("appId", "wx288339422065bc01");
        map.put("mchId", "1378612302");
        map.put("subMchId", "15234488");
        map.put("nonceStr", "");
        map.put("sign", "");
       

        
        map.put("outTradeNo", "20161207000000002520");//商户订单号   1160150612
        map.put("outRefundNo", "208A3392EBFB408DE050020A273308C5");//商户退款单号    208A3392EBFB408DE050020A273308C4
        map.put("totalFee", "1");//订单金额 
        map.put("refundFee", "1");//申请金额 
        map.put("opUserId", "15234488");//操作员 

        m = handler.handle(m);
        System.out.println(m);
    }

}
