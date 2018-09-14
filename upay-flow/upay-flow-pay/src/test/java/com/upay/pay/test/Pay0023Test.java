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
public class Pay0023Test {

    @Resource(name = "SI_PAY0023")
    IDipperHandler<Message> SI_PAY0023;


    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("spbillCreateIp", "179.169.2.125");
        headMap.put("_TRAN_CODE", "_TRAN_CODE");

        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("spbillCreateIp", "172.18.88.95");
//        bodyMap.put("merNo", "MER2017000119");
//        bodyMap.put("userId", "UR000000000469");
        bodyMap.put("stlAcctType","23");
        bodyMap.put("stlAcctNo","5324019901301000058699");
        bodyMap.put("stlAcctName","月绮罗");
        
        bodyMap.put("singlePayType","1");//代付类型 1-内部转账  2-非内部转账
        bodyMap.put("acctNo", "6229807711600013706");// 收款的银行卡号
        bodyMap.put("acctName", "ceshi");
        bodyMap.put("accountType", "11");
        bodyMap.put("transAmt",new BigDecimal(12));
        bodyMap.put("transComments","代付测试");
        bodyMap.put("mobile","13611044561");
        bodyMap.put("notifyUrl","127.0.0.1");
        bodyMap.put("chnlId","02");
        bodyMap.put("transCode", "SI_PAY0023");
        bodyMap.put("orderName", "商户二清结算");
        bodyMap.put("orderName", "orderDes");
        
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_PAY0023.handle(message);
        System.out.println(message);
    }

}
