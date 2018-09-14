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
public class Pay2010Test {
    
    @Resource(name = "SI_PAY2010")
    IDipperHandler<Message> SI_PAY2010;

    @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY2010");
       headMap.put("chnlId", "02");
       headMap.put("platform", "01");
       //headMap.put("userId", "UR000000000009");
       headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("chnlId", "03");
       bodyMap.put("transCode", "SI_PAY2010");
       
       
       bodyMap.put("outerOrderNo", "20170002255514448");
       bodyMap.put("merNo", "MER2017000122");
       bodyMap.put("tranCode", "SENDREDPACK");
       bodyMap.put("msgappid", "wx45f0822d606bd9f6");
       bodyMap.put("reOpenid", "o4CGdwnl8igAPuIWw9NKoiVW_BUc");
       
       bodyMap.put("sendName", "测试商户");
       bodyMap.put("totalAmount", new BigDecimal("1"));
       bodyMap.put("wishing", "测试活动");
       bodyMap.put("clientIp", "192.168.0.1");
       bodyMap.put("actName", "测试活动");
       bodyMap.put("remark", "测试活动");
       bodyMap.put("sceneId", "");
       bodyMap.put("riskInfo", "");
       bodyMap.put("consumeMchId", "1378612302");
     
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY2010.handle(message);
       System.out.println(message);
   }
}
