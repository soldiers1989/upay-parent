package com.upay.pay.test;

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
public class Pay0015TestAlipay {
	private Logger logger = LoggerFactory.getLogger(Pay0015TestAlipay.class);
	
	@Resource(name = "SI_PAY0015")
	private IDipperHandler<Message> SI_PAY0015;

	@org.junit.Test
	public void test() throws Exception{
       Map<String,Object> headMap=new HashMap<String,Object>();
       headMap.put("transCode", "SI_PAY0015");
       headMap.put("chnlId", "02");
       Map<String,Object> bodyMap=new HashMap<String,Object>();
       bodyMap.put("transCode", "SI_PAY0015");
       bodyMap.put("chnlId", "02");
       bodyMap.put("merNo", "MER2017000119");
       bodyMap.put("outerOrderNo", "20171122163254935558");
       bodyMap.put("outerRefundNo", "20171122163254935564");
       bodyMap.put("refundAmt", "2");
       
       
       Message message =
               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
       message = SI_PAY0015.handle(message);
       System.out.println(message);
   }
}
