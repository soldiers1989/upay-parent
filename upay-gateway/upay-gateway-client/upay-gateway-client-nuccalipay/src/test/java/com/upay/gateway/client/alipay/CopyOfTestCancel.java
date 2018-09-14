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
import com.upay.commons.util.TransUtil;

public class CopyOfTestCancel {
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
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	
	    	map.put("outTradeNo", "20170320010101027");
	    	map.put("subject", "IPHONE 8"); 
		    map.put("totalAmount", "0.01"); 
		    
		    Map<String, Object> subMerchant = new HashMap<String, Object>();
		    subMerchant.put("merchantId", "2088921560866102");
		    map.put("subMerchant", subMerchant);
		    
		   
		    Map<String, Object> goodsDetail = new HashMap<String, Object>();
		    goodsDetail.put("goodsId", "apple-01");//	商品的编号
		    goodsDetail.put("goodsName", "iphone8");//商品名称
		    goodsDetail.put("quantity", "1");//商品数量	
		    goodsDetail.put("price", "0.01");//商品单价，单位为元
		    goodsDetail.put("goodsCategory", "34543238");//商品类目
		    goodsDetail.put("body", "特价手机");//商品描述信息
		    goodsDetail.put("showUrl", "http://www.alipay.com/xxx.jpg");//商品的展示地址
		    List<Object> list = new ArrayList<>();
		    list.add(goodsDetail);
		    Map<String, Object> goodsDetailTwo = new HashMap<String, Object>();
		    goodsDetailTwo.put("goodsId", "10001");//	商品的编号
		    goodsDetailTwo.put("goodsName", "矿泉水");//商品名称
		    goodsDetailTwo.put("quantity", "1");//商品数量	
		    goodsDetailTwo.put("price", "3");//商品单价，单位为元
		    goodsDetailTwo.put("goodsCategory", "34543118");//商品类目
		    goodsDetailTwo.put("body", "特价商品");//商品描述信息
		    goodsDetailTwo.put("showUrl", "http://www.alipay.com/kuangquanshui.jpg");//商品的展示地址
		    list.add(goodsDetailTwo);
		    map.put("goodsDetail", list);
		    
		    Map<String, Object> royaltyInfo = new HashMap<String, Object>();
		    royaltyInfo.put("royaltyType", "ROYALTY");
		    List<Map<String, Object>> royaltyDetailInfos = new ArrayList<Map<String, Object>>();
		    Map<String, Object> royaltyDetail = new HashMap<String, Object>();
		    royaltyDetail.put("serialNo", "1");
		    royaltyDetail.put("transInType", "userId");
		    royaltyDetail.put("batchNo", "123");
		    royaltyDetail.put("outRelationId", "20131124001");
		    royaltyDetail.put("transOutType", "userId");
		    royaltyDetail.put("transOut", "2088101126765726");
		    royaltyDetail.put("transIn", "2088101126708402");
		    royaltyDetail.put("royaltyAmount", "0.1");
		    royaltyDetail.put("royaltyDesc", "分账测试1");
		    royaltyDetail.put("amountPercentage", "100");
		    royaltyDetailInfos.add(royaltyDetail);
		    
		    Map<String, Object> royaltyDetailTwo = new HashMap<String, Object>();
		    royaltyDetailTwo.put("serialNo", "2");
		    royaltyDetailTwo.put("transInType", "userId");
		    royaltyDetailTwo.put("batchNo", "124");
		    royaltyDetailTwo.put("outRelationId", "20131124002");
		    royaltyDetailTwo.put("transOutType", "userId");
		    royaltyDetailTwo.put("transOut", "2088101126765726");
		    royaltyDetailTwo.put("transIn", "2088101126708402");
		    royaltyDetailTwo.put("royaltyAmount", "2");
		    royaltyDetailTwo.put("royaltyDesc", "分账测试2");
		    royaltyDetailTwo.put("amountPercentage", "80");
		    royaltyDetailInfos.add(royaltyDetailTwo);
		    
		    royaltyInfo.put("royaltyDetailInfos", royaltyDetailInfos);
		    map.put("royaltyInfo", royaltyInfo);
		    
		    Map<String, Object> extendParams = new HashMap<String, Object>();
		    extendParams.put("sysServiceProviderId", "2088102174780453");
		    map.put("extendParams", extendParams);
	    	
		    TransUtil.parseValueBeforeTransForAlipay("precreate",map, "IN");

	}

}
