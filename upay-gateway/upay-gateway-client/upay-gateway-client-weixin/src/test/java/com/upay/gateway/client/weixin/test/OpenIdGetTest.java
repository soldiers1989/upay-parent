package com.upay.gateway.client.weixin.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;

/**
 * 
 * @author Hing
 * @since 2014年11月6日
 */
public class OpenIdGetTest {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath*:META-INF/spring/**/*.xml",
				"classpath*:META-INF/beans/*.xml",
				"classpath*:META-INF/comm/*.xml",
				"classpath*:META-INF/flow/*.xml",
				"classpath*:META-INF/dubbo/*.xml");
		String channel = "esbhostcli";
		final String charsetName = "UTF-8";

		IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx
				.getBean("weiXinGetOpenIdHandler");
		// IDipperHandler<Message> handler = (IDipperHandler<Message>)
		// ctx.getBean("weiXinHttpsClientHandler");
		// (IDipperHandler<Message>) ctx.getBean("orderQueryPayClientHandler");

		Message m = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create("000000", ""));
		Map<String, Object> map = (Map<String, Object>) m.getTarget()
				.getBodys();

		map.put("searchFlag", "1");
		map.put("code", "001azKei2lGgIM0UXsgi2BeLei2azKeW");
		map.put("appId", "wx3950cc559a1244f4");
		map.put("appSecret", "c3c0b9011961fa81b669f7e9fa4114a5");

		// https://api.weixin.qq.com/sns/oauth2/access_token?
		// appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		// 统一下单https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3950cc559a1244f4&secret=c3c0b9011961fa81b669f7e9fa4114a5&code=001azKei2lGgIM0UXsgi2BeLei2azKeW&grant_type=authorization_code
		// map.put("transCode", "UNIFIEDORDER");
		// map.put("appId", "wx9f95a7b0bf66e29f");
		// map.put("body", "统一支付测试");
		// map.put("deviceInfo", "128");// 可以为空
		// map.put("mchId", "1219399001");
		// map.put("outTradeNo", "11601506121442999910943");
		// map.put("productId", "1008450740201417220000358851");
		// map.put("spbillCreateIp", "127.0.0.1");
		// map.put("totalFee", "2");
		// map.put("tradeType", "NATIVE");
		// map.put("notifyUrl", "http://36.ab95569.com");
		// map.put("nonceStr", "no6qegpf11rn136yl2q9izsk60be7fxn");
		// 对账单下载
		// map.put("transCode", "DOWNLOADBILL");
		// map.put("merchantLoginKey", "7a90dbbbdfd771a71646505baaa3f77e");
		// map.put("appId", "wx6cba2be5d7b26f70");
		// map.put("billDate", "20150926");
		// map.put("billType", "SUCCESS");
		// map.put("mchId", "1218236701");
		// map.put("nonceStr", "no6qegpf11rn136yl2q9izsk60be7fxn");

		// 订单查询
		// map.put("transCode", "ORDERQUERY");
		// map.put("appId", "wx79a5e9fc140020eb");
		// map.put("mchId", "1225034102");
		// map.put("merchantLoginKey", "abdfsdgfhgjfy6567dfgbcgfdretab69");
		// 本地测试需要传的
		// map.put("outTradeNo", "208E2DD62F475AB9E050020A273379FF");
		// map.put("seriNo", "208A3392EBFB408DE050020A273308C4");
		// map.put("nonceStr", "no6qegpf11rn136yl2q9izsk60be7fxn");

		// 申请退款
		// map.put("transCode", "REFUND");
		// map.put("appId", "wx79a5e9fc140020eb");
		// map.put("mchId", "1225034102");
		// map.put("outTradeNo", "208E2DD62F475AB9E050020A273379FF");
		// map.put("outRefundNo", "208A3392EBFB408DE050020A273308C4");
		// map.put("totalFee", "123");
		// map.put("refundFee", "123");
		// map.put("opUserId", "999999999999");
		// map.put("nonceStr", "no6qegpf11rn136yl2q9izsk60be7fxn");

		// 退款查询
		/*
		 * map.put("transCode", "REFUNDQUERY"); map.put("appId",
		 * "wx79a5e9fc140020eb"); map.put("mchId", "1225034102");
		 * map.put("outTradeNo", "208E2DD62F475AB9E050020A273379FF");
		 * map.put("nonceStr", "no6qegpf11rn136yl2q9izsk60be7fxn");
		 */

		/*
		 * map.put("transCode", "ORDERQUERY"); map.put("appId",
		 * "wx9f95a7b0bf66e29f"); map.put("merCode", "1219399001");
		 * map.put("merchantLoginKey", "1f6702a914d7a7a1a0a98c953d1fab88");//
		 * 本地测试需要传的 map.put("outTradeNo", "201509011124025200001");
		 * map.put("seriNo", "201509011124025200001");
		 */

		/*
		 * map.put("transCode", "ORDERQUERY"); map.put("appId",
		 * "wx6cba2be5d7b26f70"); map.put("merCode", "1218236701");
		 * map.put("merchantLoginKey", "7a90dbbbdfd771a71646505baaa3f77e ");//
		 * 本地测试需要传的 map.put("outTradeNo", "201509011124025200001");
		 * map.put("seriNo", "201509011124025200001");
		 */

		// 获取token
		/*
		 * map.put("transCode", "TOKEN"); map.put("appId",
		 * "wx6cba2be5d7b26f70"); map.put("grantType", "client_credential");
		 * map.put("secret", "22905f4402bef681f982cad688ae96c6");
		 */

		m = handler.handle(m);
		System.out.println(m);

	}
}
