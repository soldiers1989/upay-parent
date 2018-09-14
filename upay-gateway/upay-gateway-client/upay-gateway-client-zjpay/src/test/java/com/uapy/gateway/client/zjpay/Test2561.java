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


public class Test2561 {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {

        InitPaymentEnv.init();

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
                    "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
                    "classpath*:META-INF/dubbo/*.xml", "classpath*:META-INF/spring/**/*.xml");
        String channel = "zjpaycli";
        final String charsetName = "GBK";

        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("SA_ZJPAY_Pay2561Handler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
        Map<String, Object> map = (Map<String, Object>) m.getTarget().getBodys();
        // head
//        map.put("_TRAN_CODE", "2561");
//        map.put("institutionID", "002366");
        // body 201609201117590011224561836
        map.put("txSNBinding", "2016123015021156945528827123"); //绑定流水号 规则： 17 位时间戳+10 位随机数
        map.put("paymentNo", "2016123015021156945513827611"); // 支付交易流水号 规则： 17 位时间戳+10 位随机数
        map.put("bankID", "105"); //银行 ID，参考《银行编码表》 首次支付时必填
        map.put("accountName", "李小青"); //账户名称 首次支付时必填
//        map.put("accountNumber", "6235711235231410110");//银行号码  首次支付时必填
        map.put("accountNumber", "6221551234564510");//银行号码  首次支付时必填
        map.put("identificationType", "0"); //开户证件类型
        map.put("identificationNumber", "542522198106106313");//证件号码
        map.put("phoneNumber", "13012345678"); //手机号 首次支付时必填
        map.put("cardType", "20"); //卡类李型：  10=个人借记 20=个人贷记
        map.put("validDate", "2712"); //信用卡有效期，格式 YYMM 首次支付且卡类型为 20=个人贷记时必填
        map.put("cvn2", "176"); //信用卡背面的末 3 位数字 首次支付且卡类型为 20=个人贷记时必填
        map.put("amount", "100"); //支付金额，单位：分
        map.put("settlementFlag", "0001"); //结算标识
        map.put("remark", "10"); //备注
        

        System.out.println(m);
        m = handler.handle(m);
        System.out.println(m);
    }

}
