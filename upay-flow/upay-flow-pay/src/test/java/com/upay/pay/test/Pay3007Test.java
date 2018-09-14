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
public class Pay3007Test {

	@Resource(name = "SI_PAY3007")
	IDipperHandler<Message> SI_PAY3007;

	@org.junit.Test
	public void test() throws Exception {
		Map<String, Object> headMap = new HashMap<String, Object>();
		headMap.put("transCode", "SI_PAY3007");
		headMap.put("chnlId", "02");
		headMap.put("platform", "01");
		// headMap.put("userId", "UR000000000009");
//		headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		//交易订单
		bodyMap.put("outerOrderNo", "201809311325012178");
		//交易金额
		bodyMap.put("transAmtStr", "15");
		//商户号
		bodyMap.put("merNo", "MER2017000119");
		//渠道
		bodyMap.put("chnlId", "02");
		//担保渠道
		bodyMap.put("payServicType", "0002");
		//商户订单有效开始日期
		bodyMap.put("outerOrderStartDate", "20180111000000");

		//商户订单有效结束日期
		bodyMap.put("outerOrderEndDate", "20180119235757");
		//币种
		bodyMap.put("CURR", "CNY");

		bodyMap.put("qrMerId", "777290058135880");
		//同步通知路径
//		bodyMap.put("notifyUrl", "https://www.baidu.com/");
		//异步通知路径
//		bodyMap.put("returnUrl", "https://www.baidu.com/");

		// bodyMap.put("payPwd", "123456");
		bodyMap.put("transCode", "SI_PAY3007");
		// bodyMap.put("accNo", "20160809000000000099");
		// bodyMap.put("bankAccNo", "6229807711600003194");

//		bodyMap.put("productId", "02");
		bodyMap.put("spbillCreateIp", "127.0.0.1");
		bodyMap.put("qrNo", "6227380008895492996");
		
		
		Message message = MessageFactory.create(IdGenerateFactory.generateId(),
				MessageFactory.createSimpleMessage(headMap, bodyMap),
				com.pactera.dipper.core.factory.MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								bodyMap), FaultFactory.create(
						Constants.ResponseCode.SUCCESS, "success"),
				new LinkedList<Store>());
		message = SI_PAY3007.handle(message);
		System.out.println(message);
	}
}
