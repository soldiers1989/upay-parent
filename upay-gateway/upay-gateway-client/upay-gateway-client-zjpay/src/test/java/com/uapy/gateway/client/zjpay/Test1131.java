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


public class Test1131 {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {

        InitPaymentEnv.init();

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
                    "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
                    "classpath*:META-INF/dubbo/*.xml", "classpath*:META-INF/spring/**/*.xml");
        String channel = "zjpaycli";
        final String charsetName = "GBK";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_ZJPAY_Pay1131Handler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();
        // head
        map.put("_TRAN_CODE", "1131");
        map.put("InstitutionID", "002366"); // 机构编号
        // body 15120915572400654947
        map.put("SerialNumber", "20170821000011379709"); // 退款交易流水号
        map.put("PaymentNo", "20170821000011379705"); // 原支付交易流水号
        map.put("Amount", "10000"); // 退款金额 单位 分
 //       map.put("Remark", "700"); //备注
        map.put("AccountType", "11"); //账户类型
        map.put("PaymentAccountName", "杜冰"); //账户名称（支付平台账户）
        map.put("PaymentAccountNumber", "622254567899222"); //账户号码（支付平台账户）
        //BankAccount 收款方银行账户信息
        map.put("BankID", "700"); //银行标识
        map.put("AccountName", "商户订单退款测试"); //账户名称
        map.put("AccountNumber", "622254567899333"); // 账户号码
        map.put("BranchName", "商户测试"); //分支行名称  
        map.put("Province", "云南"); //分支行省份
        map.put("City", "玉溪"); //分支行城市
        
        map.put("RefundType", "0"); //退款方式

        System.out.println(m);
        m = handler.handle(m);
        System.out.println(m);
    }

}
