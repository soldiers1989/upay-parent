package com.upay.pay.test;

import java.math.BigDecimal;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class Pay2125Test {
	private Logger logger = LoggerFactory.getLogger(Pay2125Test.class);
	
	@Resource(name = "SI_PAY2025")
	private IDipperHandler<Message> SI_PAY2025;

	@org.junit.Test
   public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY2025");
       headMap.put("chnlId", "02");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("transCode", "SI_PAY2025");
       bodyMap.put("chnlId", "02");
       bodyMap.put("merNo", "MER2017000211");
//       bodyMap.put("outerOrderNo", "20171122163254945548");
//       bodyMap.put("subject", "测试商品1");
       bodyMap.put("orderNo", "UPAY201807240009506682");
//       bodyMap.put("transAmtStr", new BigDecimal("1022"));
//       bodyMap.put("userId", "UR000000000477");
       bodyMap.put("spbillCreateIp", "127.0.0.1");
       
       
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY2025.handle(message);
       System.out.println(message);
   }
}
