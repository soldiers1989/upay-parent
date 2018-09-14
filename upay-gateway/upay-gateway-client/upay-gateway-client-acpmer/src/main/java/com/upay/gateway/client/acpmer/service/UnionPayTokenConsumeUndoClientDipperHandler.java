package com.upay.gateway.client.acpmer.service;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.UnionUtils;
import com.upay.gateway.client.acpmer.util.AcpToolUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联无跳转撤销
 * 
 * @author hry
 * 
 */
public class UnionPayTokenConsumeUndoClientDipperHandler extends AbstractAcpClientDipperHandler{
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";


		@Override
		protected void setInitParam(Map<String, Object> init) {
			init.put("tranCode", "01");
			init.put("txnSubType", "01"); //交易子类型 01-消费
			init.put("bizType", "000902"); // 业务类型 token支付
			init.put("channelType", "07"); // 渠道类型07-PC
			init.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			
			init.put("orderId", AcpToolUtil.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			init.put("txnTime", AcpToolUtil.getCurrentTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效

			init.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
			init.put("txnAmt", "");							   //交易金额，单位分，不要带小数点
			init.put("accType", "01");                              //账号类型
			
			//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
			//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  代收产品接口规范 代收交易 商户通知
			//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
			//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
			//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
			init.put("backUrl", "");
			String token=(String) init.get("token");
			//消费：token号（从前台开通的后台通知中获取或者后台开通的返回报文中获取），验证码看业务配置(默认要短信验证码)。
			init.put("tokenPayData", "{token="+token+"&trId=62000000001}");
			Map<String,String> customerInfoMap = new HashMap<String,String>();
			customerInfoMap.put("smsCode", "");			    	//短信验证码
			//customerInfoMap不送pin的话 该方法可以不送 卡号
			String customerInfoStr = AcpToolUtil.getCustomerInfo(customerInfoMap,null,encoding);
			init.put("customerInfo", customerInfoStr);
			
			
		}

		@Override
		protected void doErrorHandle(Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.TIME_OUT);
			// 超时

			/*
			 * target.put("orderStat", "2"); target.put("transStat", "8");
			 * target.put("routTransStat", "8"); target.put("errCode",
			 * fault.getOutCode()); target.put("errMsg", fault.getOutMsg());
			 * target.put("transSyncFlag", "2");
			 */// 如果出现超时的问题，则把同异步标识设置为异步（2），终止流程，在finally中更新订单，流水状态
			ExInfo.throwDipperEx(fault);		}

		@Override
		protected void doSuccessHandle(Map<String, Object> source,
				Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.SUCESS);
			target.putAll(source);
		}

		@Override
		protected void doFailureHandle(Map<String, Object> source,
				Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.FAULT);
			/*
			 * target.put("transStat", "2"); target.put("routTransStat", "2");
			 */
			target.put("errCode", fault.getOutCode());
			target.put("errMsg", fault.getOutMsg());
			UnionUtils.setRespCode(target,fault);
		}

}
