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
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class Pay0009Test {
    
    @Resource(name = "SI_PAY0009")
    IDipperHandler<Message> SI_PAY0009;

    @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY0009");
       headMap.put("chnlId", "02");
       headMap.put("platform", "01");
       headMap.put("userId", "UR000001733143");
       headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("chnlId", "02");
       bodyMap.put("platform", "01");
       bodyMap.put("userId", "UR000001733143");
       bodyMap.put("tradePwd", "123456");
       bodyMap.put("transCode", "SI_PAY0009");
       bodyMap.put("accNo", "66140040100005262955");
       bodyMap.put("bankAccNo", "6214831236532510");
       bodyMap.put("payType", "00");
//       bodyMap.put("routeCode", "0004");
       bodyMap.put("orderNo", "UPAY201707220009450615");
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY0009.handle(message);
       System.out.println(message);
   }
}
