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


/**
 * 账户验证 zhanggr
 * */

public class Test2310 {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {

        InitPaymentEnv.init();

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
                    "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
                    "classpath*:META-INF/dubbo/*.xml", "classpath*:META-INF/spring/**/*.xml");
        String channel = "zjpaycli";
        final String charsetName = "GBK";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_ZJPAY_Pay2310Handler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();
        // head
//        map.put("_TRAN_CODE", "2310");
//        map.put("InstitutionID", "002366");
        // body
        map.put("gateWay_zjpay_sysSeq", "16122775118769469"); // 绑定流水号
        map.put("TxSN", "161227751287694693214568754"); // 绑定流水号
        map.put("bankID", "105"); // 银行ID 《银行编码表》
        map.put("accountType", "11"); // 
        map.put("accountName", "李雨龙"); // 账户名称
        map.put("accountNumber", "621661310024230513"); // 账户号码
        map.put("identificationType", "0"); // 证件类型
        map.put("identificationNumber", "510623198608108436"); // 证件号码
      //  map.put("validDate", "11");// 卡类型 10：个人借记卡 20：个人贷记
      //  map.put("cvn2", "10");// 卡类型 10：个人借记卡 20：个人贷记
        map.put("remark", "11");// 备注
        map.put("phoneNumber", "13551324852");// 手机号
        map.put("email", "lb343601312@126.com");// 邮箱
        System.out.println(m);
        m = handler.handle(m);
        System.out.println(m);
    }

}
