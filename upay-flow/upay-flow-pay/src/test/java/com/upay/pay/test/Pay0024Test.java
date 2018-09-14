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
public class Pay0024Test {

    @Resource(name = "SI_PAY0024")
    IDipperHandler<Message> SI_PAY0024;


    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();
        
        headMap.put("chnlId", "02");
        headMap.put("platform", "01");
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("spbillCreateIp", "179.169.2.125");
        bodyMap.put("chnlId", "02");
        bodyMap.put("transCode", "SI_PAY0024");
        bodyMap.put("payerAcctNo","6229807711500276320");
        bodyMap.put("payerAcctName","小静");
		bodyMap.put("payerCertNo","362523199207306178");
		bodyMap.put("payerMobile","13551326546");
		bodyMap.put("payeeAcctNo","1020111000120195");
		bodyMap.put("payeeAcctName","阿牛哥");
		bodyMap.put("payeeCertNo","370401194401225746");
		bodyMap.put("payeeMobile","13555231546");
		bodyMap.put("outerOrderNo","ESB1234567885410");
		bodyMap.put("outerOrderDate","2017110203023");
		bodyMap.put("transAmt",new BigDecimal("114"));
		bodyMap.put("CURR","CNY");

        
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_PAY0024.handle(message);
        System.out.println(message);
    }

}
