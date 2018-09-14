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
public class Pay0020Test {

    @Resource(name = "SI_PAY0020")
    IDipperHandler<Message> SI_PAY0020;


    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("spbillCreateIp", "179.169.2.125");
        headMap.put("_TRAN_CODE", "_TRAN_CODE");

        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("spbillCreateIp", "172.18.88.95");
//        bodyMap.put("merNo", "MER2017000119");
//        bodyMap.put("outerOrderNo", "PAY0020OrderNo0041");
//        bodyMap.put("orderNo", "UPAY201706270009449583");
//        bodyMap.put("merDate", "20170626160000");
        bodyMap.put("singlePayType","1");//代付类型 1-内部转账  2-非内部转账
        bodyMap.put("singlePaymentFlag","02");  //01收银台发起交易    02 商户发起交易
        bodyMap.put("acctNo", "5324019901301000058699");// 收款的银行卡号
//        bodyMap.put("acctNo", "6229807711600013706");// 收款的银行卡号
//        bodyMap.put("acctNo", "6214832021352110");// 收款的银行卡号
        bodyMap.put("acctName", "ceshi");
        bodyMap.put("accountType", "12");
        bodyMap.put("transAmt",new BigDecimal(77));
        bodyMap.put("transComments","代付测试");
        bodyMap.put("notifyUrl","127.0.0.1");
        bodyMap.put("chnlId","02");
        bodyMap.put("transCode", "SI_PAY0020");
        
      bodyMap.put("payerAccountNo", "5324019901301000058699");//付款人
      bodyMap.put("payerAccountType", "23");// 付款人账户类型
      bodyMap.put("payerAccountName", "许霞");// 付款人账户名称
      bodyMap.put("payerMobile", "13551324852");// 付款人手机号
      bodyMap.put("payerCertNo", "510623188608108436");// 付款人身份证号
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_PAY0020.handle(message);
        System.out.println(message);
    }

}
