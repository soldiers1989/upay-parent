package com.upay.gateway.client.acpmer.service;

import java.util.HashMap;
import java.util.Map;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.UnionUtils;
import com.upay.gateway.client.acpmer.util.AcpToolUtil;


/**
 * 实名认证
 * 
 * @author SunOlny
 * 
 */
public class RealNameAuthHandler extends AbstractAcpClientDipperHandler {
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";

	@Override
	protected void setInitParam(Map<String, Object> init) {
		init.put("signMethod", "01");                //签名方法 目前只支持01-RSA方式证书加密
		// 交易类型
		init.put("tranCode", "72");
		init.put("txnSubType", "12");// M

		init.put("orderId", AcpToolUtil.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		init.put("txnTime", AcpToolUtil.getCurrentTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		init.put("channelType", "07");
		init.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		if(init.containsKey("certifTp")){
			Object certifTp = init.get("certifTp");
			if(certifTp !=null){
				customerInfoMap.put("certifTp", certifTp.toString());
			}else{
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "证件类型");
			}
		}
		if(init.containsKey("certifId")){
			Object certifId = init.get("certifId");
			if(certifId !=null){
				customerInfoMap.put("certifId", certifId.toString());
			}else{
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "证件号");
			}
		}
		if(init.containsKey("customerNm")){
			Object customerNm = init.get("customerNm");
			if(customerNm !=null){
				customerInfoMap.put("customerNm", customerNm.toString());
			}else{
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "姓名");
			}
		}
		if(init.containsKey("phoneNo")){
			Object phoneNo = init.get("phoneNo");
			if(phoneNo !=null){
				customerInfoMap.put("phoneNo", phoneNo.toString());
			}else{
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "手机号");
			}
		}
		String accNo="";
		if(init.containsKey("accNo")){
			Object accNoObj = init.get("accNo");
			if(accNoObj !=null){
				accNo=accNoObj.toString();
			}else{
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "卡号");
			}
		}
		
		String accNoEnc = AcpToolUtil.encryptData(accNo, "UTF-8"); 		//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		init.put("accNo", accNoEnc);
		init.put("productId", "B156");// M
		init.put("encryptCertId",AcpToolUtil.getEncryptCertId()); //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpToolUtil.
				getCustomerInfoWithEncrypt(customerInfoMap,null,encoding);
		init.put("customerInfo", customerInfoStr);
		
	}
	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		target.putAll(target);
		target.put("coreStat", CoreStat.TIME_OUT);
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.putAll(source);
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.putAll(source);
		target.put("coreStat", CoreStat.FAULT);
		target.put("errCode", fault.getOutCode());
		target.put("errMsg", fault.getOutMsg());
		UnionUtils.setRespCode(target,fault);
	}

	@Override
	public boolean isErrorThrow() {
		return true;// 超时抛异常
	}

	@Override
	public boolean isFailureThrow() {
		// return false;
		return true;// 失败抛异常
	}


}