package com.upay.gateway.client.sms;

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
import com.pactera.dipper.core.utils.Constants;


public class SmsTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        String channel = "smscli";
        final String charsetName = "GBK";
        @SuppressWarnings("unchecked")
        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_GNR_smsSendService");
        Date date = new Date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        
        HashMap<String, Object> bodyMap=new HashMap<String, Object>();
        bodyMap.put("transCode", "610006");
        bodyMap.put("svcCd", "60120001");
        bodyMap.put("svcScn", "01");
        bodyMap.put("machineDate", dateFormat.format(date));
        bodyMap.put("machineTime", timeFormat.format(date));
        bodyMap.put("channelId", "74");
        bodyMap.put("Flg4", "0");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
//        System.out.println(dateFormat1.format(date));
        bodyMap.put("bizSerialNo", "UPP0000120180305183055320041041235");
        
        
//        bodyMap.put("termDate", dateFormat.format(date));
//        bodyMap.put("termTime", timeFormat.format(date));
//        bodyMap.put("branchNo", "1010");
        bodyMap.put("phoneNo", "13888013467");
        bodyMap.put("sendMsg", "测试测试");
        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),bodyMap), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        "交易成功"));
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
       
//        initReconciliation(body);

        m = handler.handle(m);
        System.out.println(m);

    }


    /**
     * 对账请求参数初始化
     * 
     * @param body
     */
    public static void initReconciliation(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "610006");// 内部交易代码，获取模板用
        body.put("termDate", dateFormat.format(date));
        body.put("termTime", timeFormat.format(date));
        body.put("branchNo", "1010");
        body.put("phoneNo", "15201513057");
        body.put("sendMsg", "zhangguorong测试");
    }


    /**
     * 记账请求参数初始化
     * 
     * @param body
     */
    public static void initAccount(Map<String, Object> body) {

    }
}
