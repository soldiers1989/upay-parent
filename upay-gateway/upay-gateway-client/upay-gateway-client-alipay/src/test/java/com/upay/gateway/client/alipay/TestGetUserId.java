package com.upay.gateway.client.alipay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

public class TestGetUserId {
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws Exception {

		 ClassPathXmlApplicationContext ctx =
		 new ClassPathXmlApplicationContext("classpath*:META-INF/beans/*.xml",
		 "classpath*:META-INF/comm/*.xml", "classpath*:META-INF/flow/*.xml",
		 "classpath*:META-INF/dubbo/*.xml",
		 "classpath*:META-INF/spring/**/*.xml");
		 String channel = "alipaycli";
		 final String charsetName = "UTF-8";
		
		 IDipperHandler<Message> handler = (IDipperHandler<Message>)
		 ctx.getBean("AliPayGetUserIdHandler");
		
		 Message m =
		 MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML",
		 charsetName,
		 MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
		 new HashMap<String, Object>()),
		 FaultFactory.create(Constants.ResponseCode.SUCCESS,
		 ""));
		 Map<String, Object> map = (Map<String, Object>)
		 m.getTarget().getBodys();
		 map.put("searchFlag", "1"); 
		 map.put("authCode", "64d4351933c847c3a89ef0a4d801NX70");
		 m = handler.handle(m);
		 System.out.println(m.getTarget().getBodys().toString());

	}

}
