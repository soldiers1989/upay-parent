package com.upay.gateway.client.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

public class TestSubMchUpdateMer {
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
				.getBean("AT_AlipaySubMchUpdateMerHandler");

		Message m = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create(Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> map = (Map<String, Object>) m.getTarget()
				.getBodys();
		map.put("_TRAN_CODE", "subMchUpdate");
		map.put("externalId", "MER3017000120");
		map.put("servicePhone", "13222222222");
		map.put("merName", "天天超市");//商户名称
		map.put("aliasName", "天天"); //商户简称 
		map.put("categoryId", "2015050700000000"); //商户经营类目
		map.put("alipaySource", "2088102170346152");//商户来源机构标识，填写机构在支付宝的pid
//		ArrayList<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
//		Map<Object, Object> bankInfo = new HashMap<Object, Object>();
//		bankInfo.put("cardNo", "6226200106341259");
//		bankInfo.put("cardName", "杨辉勇");
//		list.add(bankInfo);
//		map.put("bankcardInfo", list);
		
		m = handler.handle(m);
		System.out.println(m);

	}

}
