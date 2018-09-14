package com.upay.pay.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class Pay2023Test extends Thread{
	private Logger logger = LoggerFactory.getLogger(Pay2023Test.class);
	
	private static IDipperHandler<Message> SI_PAY2023;
	

	static{
		ApplicationContext ac = new FileSystemXmlApplicationContext(
				"classpath:spring/test-service.xml");
		SI_PAY2023 = (IDipperHandler<Message>) ac
				.getBean("SI_PAY2023");
	}

	@Override
	public void run() {
		 Map<String,Object> headMap=new HashMap<String,Object>();
	       headMap.put("transCode", "SI_PAY2023");
	       headMap.put("chnlId", "02");
	       Map<String,Object> bodyMap=new HashMap<String,Object>();
	       bodyMap.put("transCode", "SI_PAY2023");
	       bodyMap.put("chnlId", "02");
	       
	       bodyMap.put("routeCode", "0005");
	       bodyMap.put("outTradeNo", "20171220000011394121");
	       bodyMap.put("tradeNo", "111111111111");
	       bodyMap.put("tradeStatus", "TRADE_SUCCESS");
	       bodyMap.put("totalAmount", "16");
	       bodyMap.put("gmtPayment", "2017-12-20 15:07:00");
	       
	       Message message =
	               MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
	                   headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
	                   new HashMap<String, Object>(), bodyMap), FaultFactory.create(
	                   Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
	       try {
			message = SI_PAY2023.handle(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	       System.out.println(message);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			Thread test = new Pay2023Test();
			test.start();
		}
	}

}
