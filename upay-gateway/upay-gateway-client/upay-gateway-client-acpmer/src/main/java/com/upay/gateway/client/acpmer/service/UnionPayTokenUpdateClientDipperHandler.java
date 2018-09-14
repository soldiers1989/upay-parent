package com.upay.gateway.client.acpmer.service;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.UnionUtils;
import com.upay.gateway.client.acpmer.util.AcpToolUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联无跳转支付授权更新
 * 
 * @author hry
 * 
 */
public class UnionPayTokenUpdateClientDipperHandler  extends AbstractAcpClientDipperHandler {
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";

		@Override
		protected void setInitParam(Map<String, Object> init) {
			init.put("tranCode", "79");
			init.put("txnSubType", "03"); // 交易子类型 00-默认开通
			init.put("bizType", "000902"); // 业务类型 token支付
			init.put("channelType", "07"); // 渠道类型07-PC
			init.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			init.put("orderId", AcpToolUtil.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			init.put("txnTime", AcpToolUtil.getCurrentTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			init.put("tokenPayData", "{token="+init.get("token")+"&trId=62000000001&tokenType=01}");
			
			//只支持贷记卡 必送：卡号、手机号、CVN2、有效期；验证码看业务配置（默认不要短信验证码,本测试商户777290058110097配置了需要）。
			Map<String,String> customerInfoMap = new HashMap<String,String>();
			if(init.get("certifTp")!=null){
				Object certifTp = init.get("certifTp");
				if(certifTp !=null){
					customerInfoMap.put("certifTp", certifTp.toString());
				}
			}
			if(init.get("certifId")!=null){
				Object certifId = init.get("certifId");
				if(certifId !=null){
					customerInfoMap.put("certifId", certifId.toString());
				}
			}
			if(init.get("customerNm")!=null){
				Object customerNm = init.get("customerNm");
				if(customerNm !=null){
					customerInfoMap.put("customerNm", customerNm.toString());
				}
			}
			customerInfoMap.put("phoneNo", init.get("phone").toString()); // 手机号
			// customerInfoMap.put("pin", "123456"); //密码【这里如果送密码 商户号必须配置
			// ”商户允许采集密码“】
			
			if(init.containsKey("cvn2")){
				Object cvn2 = init.get("cvn2");
				if(cvn2 !=null){
					customerInfoMap.put("cvn2", cvn2.toString());
				}
			}
			if(init.containsKey("expired")){
				Object expired = init.get("expired");
				if(expired !=null){
					customerInfoMap.put("expired", expired.toString());
				}
			}
			if(init.containsKey("smsCode")){
				Object smsCode = init.get("smsCode");
				if(smsCode !=null){
					customerInfoMap.put("smsCode", smsCode.toString());
				}
			}
			init.put("encryptCertId",AcpToolUtil.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
			String customerInfoStr = AcpToolUtil.getCustomerInfoWithEncrypt(customerInfoMap,null,encoding);
			
			init.put("customerInfo", customerInfoStr);

		}

		@Override
		protected void doErrorHandle(Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.TIME_OUT);
			target.putAll(target);
			// 超时

			/*
			 * target.put("orderStat", "2"); target.put("transStat", "8");
			 * target.put("routTransStat", "8"); target.put("errCode",
			 * fault.getOutCode()); target.put("errMsg", fault.getOutMsg());
			 * target.put("transSyncFlag", "2");
			 */// 如果出现超时的问题，则把同异步标识设置为异步（2），终止流程，在finally中更新订单，流水状态
            ExInfo.throwDipperEx(fault);
		}

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
