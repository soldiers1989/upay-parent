package com.upay.gateway.client.alipay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

public class TestQuery {
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
		 ctx.getBean("AT_AlipayQueryHandler");
		
		 Message m =
		 MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML",
		 charsetName,
		 MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
		 new HashMap<String, Object>()),
		 FaultFactory.create(Constants.ResponseCode.SUCCESS,
		 ""));
		 Map<String, Object> map = (Map<String, Object>)
		 m.getTarget().getBodys();
		 map.put("outTradeNo", "201806101015123456123"); //商户订单号
		 map.put("tradeNo", ""); // 支付宝交易号，和商户订单号不能同时为空 trade_no,out_trade_no如果同时存在优先取trade_no
		 m = handler.handle(m);
		 System.out.println(m);

	}

}
