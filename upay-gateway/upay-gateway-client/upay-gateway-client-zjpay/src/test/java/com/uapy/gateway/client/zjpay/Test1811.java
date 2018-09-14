package com.uapy.gateway.client.zjpay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.upay.gateway.client.zjpay.utils.InitPaymentEnv;


public class Test1811 {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {

        InitPaymentEnv.init();

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
                    "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
                    "classpath*:META-INF/dubbo/*.xml", "classpath*:META-INF/spring/**/*.xml");
        String channel = "zjpaycli";
        final String charsetName = "GBK";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_ZJPAY_Pay1811Handler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();
        // head
//        map.put("_TRAN_CODE", "1811");
        map.put("InstitutionID", "002367");
        // body
        map.put("TxSN", "15120915572400654946");

        map.put("searchDate", "20160709");
        map.put("pageNo", "1");
        map.put("countPerPage", "1");

        System.out.println(m);
        m = handler.handle(m);
        System.out.println(m);
    }

}
