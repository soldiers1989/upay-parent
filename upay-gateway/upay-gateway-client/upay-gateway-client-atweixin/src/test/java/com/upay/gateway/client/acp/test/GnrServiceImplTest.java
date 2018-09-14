package com.upay.gateway.client.acp.test;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.commons.util.DateUtil;
import com.upay.gateway.client.atweixin.util.SDKConfig;
import com.upay.gateway.client.atweixin.util.WXPayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 公共模块交易
 * 
 * @author freeplato
 * 
 */
public class GnrServiceImplTest extends BaseTest {
	private static final Logger LOG = LoggerFactory
			.getLogger(GnrServiceImplTest.class);
	private IDipperHandler<Message> acpService;
	public static String encoding = "UTF-8";

	/**
	 * 5.0.0
	 */
	public static String version = "5.0.0";

	public static String backUrl = "http://yhy19950611.vicp.io:13698/service/acp/post";// 受理方和发卡方自选填写的域[O]--后台通知地址

	@SuppressWarnings("unchecked")
	@Before
	public void before() {

		logger.debug("===================test start!!");
		acpService = (IDipperHandler) applicationContext
				.getBean("unionPayOrderRefundQueryClientDipperHandlr");

	}

	@After
	public void after() {
		logger.debug("===================test end!!");
	}

	private static String out_trade_no = "121775250120140703"
			+ System.currentTimeMillis();

//	 /**
//	 * 刷卡支付
//	 *
//	 * @throws Exception
//	 */
//	 @Test
//	 public void testReceiveByAcp() throws Exception {
//	 HashMap<String, Object> data = new HashMap<String, Object>();
//	 /**
//	 * 组装请求报文
//	 */
//	 data.put("tranCode", "MICROPAY");
//	 data.put("authCode", "134554493593905652");
//	 //扫码支付授权码，设备读取用户微信中的条码或者二维码信息（注：用户刷卡条形码规则：18位纯数字，以10、11、12、13、14、15开头）
//	 data.put("feeType", "CNY"); //符合ISO 4217标准的三位字母代码，默认人民币：CNY
//	 data.put("totalFee", "2"); //订单总金额，单位为分，只能为整数
////	 data.put("device_info", "013467007045764"); //终端设备号(商户自定义，如门店编号)
//	 data.put("body", "腾讯充值中心-QQ会员充值"); //商品或支付单简要描述，格式要求：门店品牌名-城市分店名-实际商品名称
////	 List<Object> goodsDetails = new LinkedList<Object>();
////	 Map<String, Object> goodsDetail = new LinkedHashMap<String, Object>();
////	 goodsDetail.put("goods_id", "商品编码"); //商品编码 由半角的大小写字母、数字、中划线、下划线中的一种或几种组成
////	 goodsDetail.put("wxpay_goods_id", "1001"); //微信支付定义的统一商品编号（没有可不传）
////	 goodsDetail.put("goods_name", "iPhone6s 16G"); //商品的实际名称
////	 goodsDetail.put("quantity", 1); //用户购买的数量
////	 goodsDetail.put("price", 528800);
////	 //单位为：分。如果商户有优惠，需传输商户优惠后的单价(例如：用户对一笔100元的订单使用了商场发的优惠券100-50，则活动商品的单价应为原单价-50)
////	 goodsDetails.add(goodsDetail);
////	 Map<String, Object> detail = new LinkedHashMap<String, Object>();
////	 /**
////	 * 订单原价 cost_price
////	 * 1.商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。
////	 * 2.当订单原价与支付金额不相等，则不享受优惠。
////	 * 3.该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数。
////	 */
////	 detail.put("cost_price", 608800);
////	 detail.put("receipt_id", "wx123"); //商家小票ID
////	 detail.put("goods_detail", goodsDetails); //单品信息，使用Json数组格式提交
////	 data.put("detail", JSON.toJSONString(detail)); //单品优惠活动信息
////	 data.put("attach", "说明"); //商家数据包，原样返回
//	 data.put("spbillCreateIp", "8.8.8.8");
//	 //APP和网页支付提交用户端Ip，Native支付填调用微信支付API的机器IP。
////	 data.put("goods_tag", "WXG"); //订单优惠标记，代金券或立减优惠功能的参数
////	 data.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");
//	 //接收银联异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
//	 data.put("tradeType", "JSAPI"); // JSAPI 公众号支付 NATIVE 扫码支付 APP APP支付
////	 data.put("limit_pay", "no_credit"); //no_credit--指定不能使用信用卡支付
////	 data.put("openid", "oUpF8uOYunOKJ-g7EjhjXayypnIs"); //用户在商户appid 下的唯一标识
////	 data.put("sub_openid", "oUpF8uOYunOKJ-g7EjhjXayypnIs");
//	 //子商户appid下用户唯一标识，如需返回则请求时需要传sub_appid
////	 Map<String, Object> identity = new LinkedHashMap<String, Object>();
////	 identity.put("type", "IDCARD"); //证件类型
////	 identity.put("number", "330000000000000000"); //证件号，如身份证号
////	 identity.put("name", "张三"); //证件姓名
////	 data.put("identity", JSON.toJSONString(identity)); //实名支付信息
////	 //场景信息
////	 data.put("scene_info",
////	 "{\"store_info\" : {\"id\": \"SZTX001\",\"name\": \"腾大餐厅\",\"area_code\": \"440305\",\"address\": \"科技园中一路腾讯大厦\"}}");
//////	 data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号
//////	 微信支付分配给收单服务商的 ID
////	 // 商户订单号 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//	 data.put("out_trade_no", out_trade_no);
////	 data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
//	
//	 acpService.handle(getMessage(data, null));
//	 }
//
//	/**
//	 * 查询订单
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//	    data.put("tranCode", "ORDERQUERY");
////		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
//		data.put("outTradeNo","1217752501201407031528449440042"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		acpService.handle(getMessage(data, null));
//	}
	
//	/**
//	 * 撤销订单 
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//	    data.put("transCode", "ORDERREVERSE");
////		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
//		data.put("out_trade_no","1217752501201407031528186671782"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		acpService.handle(getMessage(data, null));
//	}
//	/**
//	 * 申请退款
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//	    data.put("tranCode", "ORDERREFUND");
////		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
//		data.put("outTradeNo","1217752501201407031528453257080"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		data.put("refundFee", "1"); //退款总金额，单位为分，只能为整数，可部分退款
//		data.put("totalFee", "1"); //订单总金额，单位为分，只能为整数
////		data.put("refund_fee_type", "CNY"); //货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
//		/**
//		 * refund_account  退款资金来源
//		 * 仅针对老资金流商户使用
//		 * REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
//		 * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
//		 */
////		data.put("refund_account", "REFUND_SOURCE_UNSETTLED_FUNDS"); 
////		data.put("refund_desc", "商品售完"); // 退款原因   若商户传入，会在
////		data.put("notify_url", "http://www.baidu.com/notify/"); 
//		acpService.handle(getMessage(data, null));
//	}
	
	
//	/**
//	 * 查询单笔退款
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//		data.put("tranCode", "ORDERQRYSINGLE");
//		data.put("outRefundNo", "1217752501201407031528453313737"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		//		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
////		data.put("out_trade_no","1217752501201407031528186510305"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		acpService.handle(getMessage(data, null));
//	}
//	
	
	
//	/**
//	 * 关闭订单
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//	    data.put("transCode", "ORDERCLOSE");
////		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
//		data.put("out_trade_no","1217752501201407031528186510305"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		acpService.handle(getMessage(data, null));
//	}
//	  
	
	
//	/**
//	 * 查询全部退款
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//		data.put("transCode", "ORDERQRYMULTIPLE");
//		data.put("out_trade_no", "1217752501201407031528195439285"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		data.put("out_refund_no", out_trade_no); // 商户退款单号  商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母，同一退款单号多次请求只退一笔
//		data.put("offset", "0"); // 记录起始位置 
//	    data.put("count", "10"); // 每页笔数   每页返回记录数
//		//		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
////		data.put("out_trade_no","1217752501201407031528186510305"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		acpService.handle(getMessage(data, null));
//	}
	
	/**
	 * 下属商户录入
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReceiveByAcp() throws Exception {
		 HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("transCode", "SUBMCHMANAGEADD");
		data.put("merchant_name","101");// 商户名称  该名称是公司主体全称，绑定公众号时会对主体一致性校验
		data.put("merchant_shortname", "开发测试啊"); // 商户简称  该名称是显示给消费者看的商户名称
		data.put("service_phone", "13888013467"); // 客服电话  方便银联在必要时能联系上商家，会在支付详情展示给消费者
//		data.put("contact", "老何"); // 联系人
//		data.put("contact_phone", "13888013467"); // 联系电话
//		data.put("contact_email", "jimhe001@test.com"); // 联系邮箱
		data.put("business", "165"); // 经营类目   行业类目，请填写对应的ID 
		data.put("contact_wechatid_type", "type_wechatid"); // 联系人微信账号类型
		data.put("contact_wechatid", "LD14787001245"); // 联系人微信帐号  微信号：打开微信，在"个人信息"中查看到的"微信号"
		data.put("merchant_remark", "1019"); // 商户备注    同一个受理机构，特约商户“商户备注”唯一。商户备注重复时，生成商户识别码失败，并返回提示信息“商户备注已存在，请修改后重新提交”
		acpService.handle(getMessage(data, null));
	}
//	
	
//	/**
//	 * 下属商户查询
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//		data.put("transCode", "SUBMCHQRY");
//		data.put("merchant_remark", "1009");//商户备注  同一个受理机构，特约商户“商户备注”唯一
//		//		data.put("channel_id", SDKConfig.getConfig().getChannelId()); //渠道商商户号   微信支付分配给收单服务商的 ID
////		data.put("nonce_str", WXPayUtil.generateUUID()); //随机字符串，不长于32位。推荐随机数生成算法
////		data.put("sub_appid", SDKConfig.getConfig().getSubAppid()); // 子商户公众账号ID  微信分配的子商户公众账号ID
////		data.put("out_trade_no","1217752501201407031528186510305"); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一
//		acpService.handle(getMessage(data, null));
//	}
	
	
	
//	/**
//	 * 统一下单
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp() throws Exception {
//		 HashMap<String, Object> data = new HashMap<String, Object>();
//		data.put("transCode", "ORDERPREPAY");
//		data.put("orderNo",out_trade_no); //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母，且在同一个商户号下唯一。
//		data.put("feeType", "CNY"); //符合ISO 4217标准的三位字母代码，默认人民币：CNY
//		data.put("totalFee", "1"); //订单总金额，只能为整数
//		data.put("tradeType", "NATIVE"); //交易类型   JSAPI 公众号支付        NATIVE 扫码支付       APP APP支付
//		data.put("body", "UP测一下-下单测试"); //商品或支付单简要描述，格式要求：门店品牌名-城市分店名-实际商品名称
//		data.put("spbillCreateIp", "172.21.0.4"); //终端IP  APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
//		data.put("attach", "说明一下，这个是测试的数据，不要以此为准"); //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
//
//		acpService.handle(getMessage(data, null));
//	}
	

}
