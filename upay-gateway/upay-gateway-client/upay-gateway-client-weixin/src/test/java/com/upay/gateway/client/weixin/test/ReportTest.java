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
 * 微信刷卡支付交易保障API 
 * 
 */
public class ReportTest {
	
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        
        String channel = "weixinpayitilli";
        final String charsetName = "UTF-8";


//      ChannelBeanDefinition def = (ChannelBeanDefinition) ctx.getBean(channel);
//      IDipperHandler<Message> handler = def.getHandlerBeanDefinition().getCommServiceRef();
      IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("defaultWeiXinpayitilClientHandler");
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
        
        
		String trades = "" + "!CDATA[[" + "out_trade_no" +":" + "out_trade_no_test_1" +","
			 + "begin_time" +":" + "20160602203256" +"," + "end_time" +":" + "20160602203257"+"," +"state"
			 +":" +"OK" +"," +"err_msg"
			 +":" +" " +"},{" + "out_trade_no" +":" + "out_trade_no_test_2" +","
			 + "begin_time" +":" + "20160602203260" +"," + "end_time" +":" + "20160602203262"+"," +"state"
			 +":" +"FAIL" +"," +"err_msg" +":"+"SYSTEMERROR"+ "}]]";

        // 订单查询
        map.put("tranCode", "REPORT");
        map.put("appId", "wxdace645e0bc2c424");
        map.put("mchId", "1900008731");
        map.put("subAppid", "");
        map.put("subMchId", "13968111");
        map.put("deviceInfo", "");// 可以为空
        map.put("nonceStr", "");
        map.put("sign", ""); 
        
        map.put("interfaceUrl", "https://api.mch.weixin.qq.com/pay/batchreport/micropay/total");
        map.put("userIp", "172.19.246.1");        
        map.put("trades", trades);
   

        m = handler.handle(m);
        System.out.println(m);
    }

}
