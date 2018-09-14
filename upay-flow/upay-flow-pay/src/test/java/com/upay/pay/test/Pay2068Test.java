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
public class Pay2068Test {
    
    @Resource(name = "SI_PAY2008")
    IDipperHandler<Message> SI_PAY2008;

    @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY2008");
       headMap.put("chnlId", "03");
       headMap.put("platform", "01");
       //headMap.put("userId", "UR000000000009");
       headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("chnlId", "03");
       //bodyMap.put("payPwd", "123456");
       bodyMap.put("transCode", "SI_PAY2008");
       //bodyMap.put("accNo", "20160809000000000099");
       //bodyMap.put("bankAccNo", "6229807711600003194");
       bodyMap.put("merNo", "MER2017000211");
      // bodyMap.put("orderNo", "UPAY201610270000000573");
//       bodyMap.put("userId", "UR000000000068");
       bodyMap.put("outerOrderNo", "201808311108256391251");
//       bodyMap.put("timeEnd", "20180716145821");   
//       bodyMap.put("productId", "02");
//       bodyMap.put("transAmtStr", "0.01");
//       bodyMap.put("spbillCreateIp", "127.0.0.1");
//       bodyMap.put("authCode", "134676914858203899");
//     
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY2008.handle(message);
       System.out.println(message);
   }
}
