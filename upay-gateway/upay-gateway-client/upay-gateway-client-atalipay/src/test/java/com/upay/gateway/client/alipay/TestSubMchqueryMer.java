package com.upay.gateway.client.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

public class TestSubMchqueryMer {
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws Exception {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath*:META-INF/beans/*.xml",
				"classpath*:META-INF/comm/*.xml",
				"classpath*:META-INF/flow/*.xml",
				"classpath*:META-INF/dubbo/*.xml",
				"classpath*:META-INF/spring/**/*.xml");
		String channel = "alipaycli";
		final String charsetName = "UTF-8";

		IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx
				.getBean("AT_AlipaySubMchQueryMerHandler");

		Message m = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create(Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> map = (Map<String, Object>) m.getTarget()
				.getBodys();
		map.put("_TRAN_CODE", "subMchquery");
		map.put("externalId", "MER3017000120");
		map.put("subMerchantId", "2088621904638490");
		
		m = handler.handle(m);
		System.out.println(m);

	}

}
