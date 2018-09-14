package com.upay.pay.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.core.utils.Constants.ResponseCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class PAY0015 {
    
    @Resource(name = "SI_PAY0015")
    IDipperHandler<Message> SI_PAY0015;

   @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY0015");
       headMap.put("chnlId", "02");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("chnlId", "02");
       bodyMap.put("transCode", "SI_PAY0015");
       bodyMap.put("outerOrderNo", "201807241612164864148624");
       bodyMap.put("outerRefundNo", "201807241612164864148624389"); //外部商户接入使用
//       bodyMap.put("subAppid", "wx2b029c08a6232582");
       bodyMap.put("currencyCode", "156"); 
       bodyMap.put("refundAmt", "10");
//       bodyMap.put("routeCode", "0003");
       
     bodyMap.put("routeCode", "0005");
       bodyMap.put("oriPayType", "25");
//       bodyMap.put("payType", "77"); 
       bodyMap.put("merNo", "MER2017000211");
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY0015.handle(message);
       System.out.println(message);
   }
}
