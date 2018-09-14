package com.upay.pay.test;

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
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class Pay2065Test {
    
    @Resource(name = "SI_PAY2005")
    IDipperHandler<Message> SI_PAY2005;

    @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY2005");
       headMap.put("chnlId", "02");
       headMap.put("platform", "01");
       //headMap.put("userId", "UR000000000009");
       headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       //bodyMap.put("payPwd", "123456");
       bodyMap.put("transCode", "SI_PAY2005");
       //bodyMap.put("accNo", "20160809000000000099");
       //bodyMap.put("bankAccNo", "6229807711600003194");
       //bodyMap.put("routeCode", "0003");
       bodyMap.put("orderNo", "UPAY201807200009506634");
       bodyMap.put("spbillCreateIp", "127.4.0.3");
       bodyMap.put("chnlId", "02");
       bodyMap.put("productId", "02");
     
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY2005.handle(message);
       System.out.println(message);
   }
}
