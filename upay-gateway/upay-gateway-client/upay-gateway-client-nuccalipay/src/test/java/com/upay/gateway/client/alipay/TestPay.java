package com.upay.gateway.client.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class TestPay {
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
		 ctx.getBean("AT_AlipayPayHandler");
		
		 Message m =
		 MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML",
		 charsetName,
		 MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
		 new HashMap<String, Object>()),
		 FaultFactory.create(Constants.ResponseCode.SUCCESS,
		 ""));
		 Map<String, Object> map = (Map<String, Object>)
		 m.getTarget().getBodys();
		 map.put("_TRAN_CODE", "pay");
		 map.put("outTradeNo", "2018061010151234561234561123"); //商户订单号
		 map.put("merchantId", "2088621897309724"); //间连受理商户的支付宝商户编号
		 map.put("scene", "bar_code"); // 支付场景  条码支付，取值：bar_code  声波支付，取值：wave_code
		 map.put("authCode", "285834478324583426"); //支付授权码
//		 map.put("productCode", "FACE_TO_FACE_PAYMENT"); //销售产品码
		 map.put("subject", "Iphone8 1T"); // 订单标题
//		 map.put("buyerId", "287658716525765862"); // 买家的支付宝用户id
		 
		 map.put("totalAmount", "0.01"); // 订单总金额，单位为元
//		 map.put("disAmount", "10"); //参与优惠计算的金额
//		 map.put("body", "Iphone8 1T"); // 订单描述
		 
		/* //订单包含的商品列表信息.
		 Map<String, Object> goodsDetail = new HashMap<String, Object>();
		 goodsDetail.put("goodsId", "apple-01");//	商品的编号
		 goodsDetail.put("goodsName", "iphone8");//商品名称
		 goodsDetail.put("quantity", "1");//商品数量	
		 goodsDetail.put("price", "5000");//商品单价，单位为元
		 goodsDetail.put("goodsCategory", "34543238");//商品类目
		 goodsDetail.put("body", "特价手机");//商品描述信息
		 goodsDetail.put("showUrl", "http://www.alipay.com/xxx.jpg");//商品的展示地址
		 List<Object> list = new ArrayList<>();
		 list.add(goodsDetail);
		 map.put("goodsDetail", list);
		 map.put("operatorId", ""); //商户操作员编号
		 map.put("storeId", ""); // 商户门店编号	
		 map.put("terminalId", ""); // 商户机具终端编号
		//业务扩展参数
		 Map<String, Object> extendParams = new HashMap<String, Object>();
		 //系统商编号  该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
		 extendParams.put("sysServiceProviderId", "2088102174780453");
		 map.put("extendParams", extendParams);*/
		 
		 // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		 map.put("timeoutExpress", "90m"); 
		 
		 m = handler.handle(m);
		 System.out.println(m);

	}

}
