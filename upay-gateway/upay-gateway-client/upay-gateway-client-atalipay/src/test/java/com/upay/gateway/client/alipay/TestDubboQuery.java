package com.upay.gateway.client.alipay;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class TestDubboQuery {
	
	@Resource(name = "AlipayQueryHandler")
    IDipperHandler<Message> alipayQueryHandler;
	

	 @org.junit.Test
	    public void test() throws Exception {
			String channel = "alipaycli";
			final String charsetName = "UTF-8";
			
			Message m =
			MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML",
			charsetName,
			MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
			new HashMap<String, Object>()),
			FaultFactory.create(Constants.ResponseCode.SUCCESS,
			""));
			Map<String, Object> map = (Map<String, Object>)
			m.getTarget().getBodys();
			map.put("_TRAN_CODE", "query");
			map.put("outTradeNo", "201708221031022816129239818"); //商户订单号
			map.put("tradeNo", ""); // 支付宝交易号，和商户订单号不能同时为空 trade_no,out_trade_no如果同时存在优先取trade_no
	        m = alipayQueryHandler.handle(m);
	        System.out.println(m.getTarget().getBodys().toString());
	    }

}
