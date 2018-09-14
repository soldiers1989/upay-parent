package com.upay.pay.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class Pay0019Test {
    @Resource(name = "SI_PAY0019")
    IDipperHandler<Message> SI_PAY0019;


    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();

        headMap.put("chnlId", "02");
        headMap.put("platform", "01");
        headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("transCode", "SI_PAY0019");
        // bodyMap.put("bankAccNo", "6229807711600003194);
        bodyMap.put("merNo", "MER2017000119");// 商户号
//        bodyMap.put("merNo", "MER2017000144");// 商户号
        bodyMap.put("outerOrderNo", "2018011400000312");
        bodyMap.put("collectionType", "1");//代付类型 1-内部转账  2-非内部转账
        bodyMap.put("note", "30");// 备注 测试时 10-返回成功状态 20-返回失败状态 30-返回处理中状态
        bodyMap.put("merDate", "20171205000000");// 商户时间
        bodyMap.put("bankId", "05591750");// 付款银行ID
//        bodyMap.put("acctNo", "5324019901301000058699");// 付款银行ID
//        bodyMap.put("acctNo", "6217809088800327324");// 收款的银行卡号
//        bodyMap.put("acctNo", "6226200106136162");// 收款的银行卡号
        bodyMap.put("acctNo", "6229807711600013706");// 付款的银行卡号
        
        bodyMap.put("chnlId", "02");
        bodyMap.put("singleCollectionFlag", "02");//01本平台上起     02其他第三方商户发起
//        bodyMap.put("orderNo", "UPAY201706220009449370");
        
        bodyMap.put("acctName", "许霞");
//        bodyMap.put("acctName", "温春英");// 收款账户的账户名称
        bodyMap.put("accountType", "11");// 代扣的账户类型： 11=个人账户 12=企业账户
                                         // 代扣个人银行卡时类型为11。
        bodyMap.put("certType", "01");// 证件类型
        bodyMap.put("certNo", "530421198608140321");// 证件号码
//        bodyMap.put("tranCode", "sendSms");
        bodyMap.put("transAmt", new BigDecimal(1));// 交易金额
        bodyMap.put("mobile", "13887738666");// 手机号码
//        bodyMap.put("smsCode", "9999");// 短信验证码
        bodyMap.put("spbillCreateIp", "192.168.1.1");// ip

//        bodyMap.put("validate", "202712");// 卡有效期
//        bodyMap.put("cvn2", "176");// CVN

        bodyMap.put("notifyUrl", "aaa");// 接收支付成功通知的URL
        
        bodyMap.put("payeeAccountNo", "5324019901301000058699");//收款人
        bodyMap.put("payeeAccountType", "23");// 接收支付成功通知的URL
        bodyMap.put("payeeAccountName", "许霞");// 接收支付成功通知的URL
        bodyMap.put("payeeMobile", "13551324852");// 接收支付成功通知的URL
        bodyMap.put("payeeCertNo", "510623188608108436");// 接收支付成功通知的URL

        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_PAY0019.handle(message);
        System.out.println(message);
    }
}
