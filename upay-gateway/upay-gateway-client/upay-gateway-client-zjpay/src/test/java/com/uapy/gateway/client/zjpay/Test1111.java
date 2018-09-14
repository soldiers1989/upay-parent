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


public class Test1111 {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {

        InitPaymentEnv.init();

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
                    "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
                    "classpath*:META-INF/dubbo/*.xml", "classpath*:META-INF/spring/**/*.xml");
        String channel = "zjpaycli";
        final String charsetName = "GBK";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_ZJPAY_Pay1111Handler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();
        // head
        map.put("_TRAN_CODE", "1111");
        map.put("InstitutionID", "002367"); // 机构编号
        // body 15120915572400654947
        map.put("PaymentNo", "15120915572400654950"); // 支付交易流水号
        map.put("Amount", "1000"); // 订单金额 单位 分
        map.put("Fee", "100"); //支付服务手续费（付款方） 单位 分
        map.put("PayerID", "test1234567890"); //付款人注册ID
        map.put("PayerName", "商户订单支付测试"); //付款方名称
        map.put("SettlementFlag", "0001"); // 结算标识
        map.put("Usage", "test"); //资金用途
        map.put("Remark", "700"); //备注
        map.put("NotificationURL", "127.0.0.1"); //接收支付成功通知的URL
        map.put("BankID", "700"); //银行标识
        map.put("AccountType", "11"); //账户类型
        map.put("CardType", "01"); //卡类型

        System.out.println(m);
        m = handler.handle(m);
        System.out.println(m);
    }

}
