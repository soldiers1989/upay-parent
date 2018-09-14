package com.upay.gateway.client.acppay.service;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.flow.utils.Constants.FlowStatus;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.UnionUtils;
import com.upay.gateway.client.acppay.util.AcpToolUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联代收授权
 * 
 * @author hry
 * 
 */
public class UnionPayReceiveAuthorizeClientDipperHandler extends
		AbstractAcpClientDipperHandler {
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";


	@Override
	protected void setInitParam(Map<String, Object> init) {
		init.put("tranCode", "72");
		init.put("txnSubType", "11");                           //交易子类型
		init.put("bizType", "000501");                          //业务类型
		init.put("orderId", AcpToolUtil.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		init.put("txnTime", AcpToolUtil.getCurrentTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		init.put("accType", "01");                              //账号类型
		
		//姓名，证件类型+证件号码至少二选一必送，手机号可选，贷记卡的cvn2,expired可选。
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		if(init.containsKey("certifTp")){
			Object certifTp = init.get("certifTp");
			if(certifTp !=null){
				customerInfoMap.put("certifTp", certifTp.toString());
			}
		}
		if(init.containsKey("certifId")){
			Object certifId = init.get("certifId");
			if(certifId !=null){
				customerInfoMap.put("certifId", certifId.toString());
			}
		}
		if(init.containsKey("customerNm")){
			Object customerNm = init.get("customerNm");
			if(customerNm !=null){
				customerInfoMap.put("customerNm", customerNm.toString());
			}
		}
		if(init.containsKey("phoneNo")){
			Object phoneNo = init.get("phoneNo");
			if(phoneNo !=null){
				customerInfoMap.put("phoneNo", phoneNo.toString());
			}
		}
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
		//如果商户号开通了【商户对敏感信息加密】的权限那么需要对
		//accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
//
		/*String accNoEnc = AcpToolUtil.encryptData((String)init.get("accNo"), "UTF-8"); 		//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		init.put("accNo", accNoEnc);
		init.put("encryptCertId",AcpToolUtil.getEncryptCertId()); //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpToolUtil.
				getCustomerInfoWithEncrypt(customerInfoMap,null,encoding);
		init.put("customerInfo", customerInfoStr);*/


		//如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		String accNo = (String) init.get("accNo");
		init.put("accNo", accNo);            		//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		String customerInfoStr = AcpToolUtil.getCustomerInfo(customerInfoMap,accNo,"UTF-8");
		init.put("customerInfo", customerInfoStr);
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

		String backCode = fault.getCode();
		/*switch (backCode) {
		case "00":
			fault.setMsgAll("成功");
			break;
		case "01":
		case "02":
		case "10":
			fault.setCodeAll(AppCodeDict.BISPMT0016);
			fault.setMsgAll((String) source.get("respMsg"));
		case "11":
		case "12":
		case "13":
		case "14":
		case "30":
		case "31":
		case "32":
		case "37":
		case "41":
		case "60":
		case "38":
		case "39":
		case "98":
			fault.setCodeAll(AppCodeDict.BISPMT0016);
			fault.setMsgAll("接收行处理失败。");
			break;
		case "34":
			fault.setCodeAll(AppCodeDict.BISPMT0019);
			fault.setMsgAll("查无此交易");
			break;*/
		/*case "03":
		case "04":
		case "05":
		case "42":
		case "69":
			fault.setCodeAll(AppCodeDict.BISPMT0018);
			fault.setMsgAll("接收行处理超时，请稍后查询交易结果");
			break;
		case "33":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("交易金额超限");
			break;
		case "35":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("原交易状态不正确");
			break;
		case "36":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("与原交易信息不符");
			break;
		case "40":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("绑定关系检查失败");
			break;
		case "61":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("输入的卡号无效，请确认后输入");
			break;
		case "62":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("交易失败，发卡银行不支持该商户，请更换其他银行卡");
			break;
		case "63":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("卡状态不正确");
			break;
		case "64":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("卡上的余额不足");
			break;
		case "65":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("输入的密码、有效期或CVN2有误，交易失败");
			break;
		case "66":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("持卡人身份信息或手机号输入不正确，验证失败");
			break;
		case "67":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("密码输入次数超限");
			break;
		case "68":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("您的银行卡暂不支持该业务，请向您的银行或95516咨询");
			break;
		case "70":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("交易已跳转，等待持卡人输入");
			break;
		case "71":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("动态口令或短信验证码校验失败");
			break;
		case "72":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("您尚未在{}银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通");
			break;
		case "73":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("支付卡已超过有效期");
			break;
		case "74":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("扣款成功，销账未知");
			break;
		case "75":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("扣款成功，销账失败");
			break;
		case "76":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("需要验密开通");
			break;
		case "77":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("银行卡未开通认证支付");
			break;
		case "99":
			fault.setCodeAll(AppCodeDict.BISPMT0016);
			fault.setMsgAll("通用错误");
			break;
		default:
			fault.setCodeAll(AppCodeDict.BISPMT0016);
			fault.setMsgAll("通用错误");
		}*/
		target.put("errCode", fault.getCode());
		target.put("errMsg", fault.getMsg());
		UnionUtils.setRespCode(target,fault);
	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		// TODO Auto-generated method stub
		target.put("coreStat", CoreStat.TIME_OUT);
		target.put("errCode", fault.getOutCode());
		target.put("errMsg", fault.getOutMsg());
		target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
		String backCode = fault.getCode();
		/*switch (backCode) {
		case "00":
			fault.setMsgAll("成功");
			break;
		case "01":
		case "02":
		case "10":
		case "11":
		case "12":
		case "13":
		case "14":
		case "30":
		case "31":
		case "32":
		case "37":
		case "41":
		case "60":
		case "38":
		case "39":
		case "98":
			fault.setCodeAll(AppCodeDict.BISPMT0016);
			fault.setMsgAll("接收行处理失败。");
			break;
		case "34":
			fault.setCodeAll(AppCodeDict.BISPMT0019);
			fault.setMsgAll("查无此交易");
			break;
		case "03":
		case "04":
		case "05":
		case "42":
		case "69":
			fault.setCodeAll(AppCodeDict.BISPMT0018);
			fault.setMsgAll("接收行处理超时，请稍后查询交易结果");
			break;
		case "33":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("交易金额超限");
			break;
		case "35":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("原交易状态不正确");
			break;
		case "36":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("与原交易信息不符");
			break;
		case "40":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("绑定关系检查失败");
			break;
		case "61":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("输入的卡号无效，请确认后输入");
			break;
		case "62":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("交易失败，发卡银行不支持该商户，请更换其他银行卡");
			break;
		case "63":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("卡状态不正确");
			break;
		case "64":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("卡上的余额不足");
			break;
		case "65":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("输入的密码、有效期或CVN2有误，交易失败");
			break;
		case "66":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("持卡人身份信息或手机号输入不正确，验证失败");
			break;
		case "67":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("密码输入次数超限");
			break;
		case "68":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("您的银行卡暂不支持该业务，请向您的银行或95516咨询");
			break;
		case "70":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("交易已跳转，等待持卡人输入");
			break;
		case "71":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("动态口令或短信验证码校验失败");
			break;
		case "72":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("您尚未在{}银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通");
			break;
		case "73":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("支付卡已超过有效期");
			break;
		case "74":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("扣款成功，销账未知");
			break;
		case "75":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("扣款成功，销账失败");
			break;
		case "76":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("需要验密开通");
			break;
		case "77":
			fault.setCodeAll(AppCodeDict.BISPMT0017);
			fault.setMsgAll("银行卡未开通认证支付");
			break;
		case "99":
			fault.setCodeAll(AppCodeDict.BISPMT0016);
			fault.setMsgAll("通用错误");
			break;
		}*/
		UnionUtils.setRespCode(target,fault);
	}

}
