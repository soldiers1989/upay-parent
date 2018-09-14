package com.upay.pay.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PAY0011Test{

    @Resource(name = "SI_PAY0011")
    IDipperHandler<Message> SI_PAY0011;

   @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY0011");
       headMap.put("chnlId", "02");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("chnlId", "02");
       bodyMap.put("transCode", "SI_PAY0011");
       bodyMap.put("merNo", "MER2017000119");
       bodyMap.put("notifyId", "NOTIFY201703140001143236");
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY0011.handle(message);
       System.out.println(message);
   }
}
