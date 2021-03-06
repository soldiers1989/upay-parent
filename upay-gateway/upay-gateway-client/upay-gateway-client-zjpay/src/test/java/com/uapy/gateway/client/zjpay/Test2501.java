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


public class Test2501 {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {

        InitPaymentEnv.init();

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
                    "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
                    "classpath*:META-INF/dubbo/*.xml", "classpath*:META-INF/spring/**/*.xml");
        String channel = "zjpaycli";
        final String charsetName = "GBK";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_ZJPAY_Pay2501Handler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();
        // head
        map.put("_TRAN_CODE", "2501");
        map.put("institutionID", "002366");
        // body 201609201117590011224561836
        map.put("txSNBinding", "201612201117510011222561817"); // 绑定流水号
        map.put("bankID", "105"); // 银行ID 《银行编码表》
        map.put("certName", "张三"); // 账户名称
        map.put("eBindAcctNo", "6227001217260375210"); // 账户号码
        map.put("certType", "0"); // 开户证件类型
        map.put("certNo", "341224199911112310"); // 证件号码
        map.put("mobile", "13333333410"); // 手机号
        map.put("bindAcctType", "11");// 卡类型 11：个人借记卡 12：个人贷记

        System.out.println(m);
        m = handler.handle(m);
        System.out.println(m);
    }

}
