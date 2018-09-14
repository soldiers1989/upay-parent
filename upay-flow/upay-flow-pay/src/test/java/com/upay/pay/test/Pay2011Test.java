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
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class Pay2011Test {
    
    @Resource(name = "SI_PAY2011")
    IDipperHandler<Message> SI_PAY2011;

    @org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY2011");
       Map<String,Object> map=new HashMap<String,Object>();
       map.put("transCode", "SI_PAY2011");
       map.put("chnlId", "02");
       map.put("merNo", "MER2017000119");
       map.put("appid", "wxdace645e0bc2c424");
       map.put("mchId", "1900008971");
       map.put("subMchId", "11386316");
       map.put("msgappid", "1ssa2");
       map.put("reOpenid", "oxTWIuGaIt6gTKsQRLau2M0yL16E");
       
       map.put("mchBillno", "111111111112222");
       map.put("sendName", "测试商户");
       map.put("transAmt", new BigDecimal("1"));
       map.put("totalNum", 1);
       map.put("wishing", "感谢您参加猜灯谜活动，祝您元宵节快乐！");
       map.put("clientIp", "192.168.0.1");
       map.put("actName", "猜灯谜抢红包活动");
       map.put("remark", "猜越多得越多，快来抢！");
       map.put("sceneId", "");
       map.put("riskInfo", "");
       map.put("consumeMchId", "");


       
     
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
            		   map, map), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), map), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY2011.handle(message);
       System.out.println(message);
   }
}
