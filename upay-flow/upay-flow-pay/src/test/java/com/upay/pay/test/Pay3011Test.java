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
public class Pay3011Test {

	@Resource(name = "SI_PAY3011")
	IDipperHandler<Message> SI_PAY3011;

	@org.junit.Test
	public void test() throws Exception {
		Map<String, Object> headMap = new HashMap<String, Object>();
		headMap.put("transCode", "SI_PAY3011");
		headMap.put("chnlId", "02");
		headMap.put("platform", "01");
		// headMap.put("userId", "UR000000000009");
//		headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("phone", "13552535506");
		bodyMap.put("cvn2", "123");
		bodyMap.put("expired", "2311");
		bodyMap.put("bindacctno", "6221558812340000");
		bodyMap.put("cardBinType", "2");
		
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				MessageFactory.createSimpleMessage(headMap, bodyMap),
				com.pactera.dipper.core.factory.MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								bodyMap), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, "success"),
				new LinkedList<Store>());
		message = SI_PAY3011.handle(message);
		System.out.println(message);
	}
}
