package com.upay.gateway.client.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

public class TestRefund {
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
				.getBean("AT_AlipayRefundHandler");

		Message m = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create(Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> map = (Map<String, Object>) m.getTarget()
				.getBodys();
		map.put("_TRAN_CODE", "refund");
		map.put("outTradeNo", "20180821010101038123467"); // 商户订单号
		map.put("tradeNo", "");//支付宝交易号，和商户订单号不能同时为空	
		map.put("refundAmount", "0.01");  // 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
		map.put("refundReason", "其他原因"); //退款的原因说明	
		map.put("outRequestNo", ""); // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
		map.put("operatorId", ""); //商户操作员编号
		
		map.put("storeId", "");  // 商户门店编号	
		map.put("terminalId", ""); // 商户机具终端编号
		m = handler.handle(m);
		System.out.println(m.getTarget().getBodys().toString());

	}

}
