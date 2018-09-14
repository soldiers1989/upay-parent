package com.upay.gateway.client.atalipay.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class AlipayUtil {
	public static String converAmtToFen(String amt) {
		if (null == amt) {
			amt = "0";
		}
		BigDecimal tranAmt = new BigDecimal(amt);
		tranAmt = tranAmt.multiply(new BigDecimal("100"));
		amt = tranAmt.toEngineeringString();
		if (amt.indexOf(".") != -1) {
			amt = amt.substring(0, amt.lastIndexOf("."));
		}
		return amt;
	}

	public static String converAmtToYuan(String amt) {
		if (null == amt) {
			return "0";
		}
		Double num = Double.valueOf(amt);
		return (num / 100.0) + "";
	}


	public static void main(String[] args) {
		String data ="{" +"\"test\":"+ "{" + "\"out_trade_no\":\"20150320010101001\","
				+ "\"scene\":\"bar_code\","
				+ "\"auth_code\":\"28763443825664394\","
				+ "\"product_code\":\"FACE_TO_FACE_PAYMENT\","
				+ "\"subject\":\"Iphone616G\","
				+ "\"buyer_id\":\"2088202954065786\","
				+ "\"seller_id\":\"2088102146225135\","
				+ "\"total_amount\":88.88," + "\"discountable_amount\":8.88,"
				+ "\"body\":\"Iphone616G\"," + "\"goods_detail\":[{"
				+ "\"goods_id\":\"apple-01\"," + "\"goods_name\":\"ipad\","
				+ "\"quantity\":1," + "\"price\":2000,"
				+ "\"goods_category\":\"34543238\"," + "\"body\":\"特价手机\","
				+ "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" + "}],"
				+ "\"operator_id\":\"yx_001\"," + "\"store_id\":\"NJ_001\","
				+ "\"terminal_id\":\"NJ_T_001\"," + "\"extend_params\":{"
				+ "\"sys_service_provider_id\":\"2088511833207846\"" + "},"
				+ "\"timeout_express\":\"90m\"" + "}" + "}";

		Map map = parseJSON2Map(data);
		map = (Map) map.get("test");
		System.out.println(((Map) ((List) map.get("goods_detail")).get(0))
				.get("goods_id"));
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		// 最外层解析
		if (jsonStr != null && jsonStr.startsWith("{") && jsonStr.endsWith("}")) {
			Map<String, Object> map = new HashMap<String, Object>();

			JSONObject json = JSONObject.fromObject(jsonStr);
			for (Object k : json.keySet()) {

				Object v = json.get(k);
				// 如果内层还是数组的话，继续解析
				if (v instanceof JSONArray) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Iterator<JSONObject> it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						JSONObject json2 = it.next();
						list.add(parseJSON2Map(json2.toString()));
					}
					map.put(k.toString(), list);
				} else {
					Map<String, Object> m = parseJSON2Map(v.toString());
					if (m == null)
						map.put(k.toString(), v);
					else
						map.put(k.toString(), m);
				}
			}
			return map;
		} else {
			return null;
		}
	}

}
