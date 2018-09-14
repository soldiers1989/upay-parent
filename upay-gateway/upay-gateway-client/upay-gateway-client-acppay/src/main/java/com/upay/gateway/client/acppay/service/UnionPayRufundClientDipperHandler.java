package com.upay.gateway.client.acppay.service;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.flow.utils.Constants.FlowStatus;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.UnionUtils;
import com.upay.gateway.client.acppay.util.AcpToolUtil;

import java.util.Map;

/**
 * 银联交易退货
 * 
 * @author hry
 * 
 */
public class UnionPayRufundClientDipperHandler extends
		AbstractAcpClientDipperHandler {
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";


	@Override
	protected void setInitParam(Map<String, Object> init) {
		init.put("tranCode", "04");
		init.put("txnSubType", "00");                          //交易子类型  默认00
		init.put("bizType", init.get("bizType"));                         //业务类型 代收
		init.put("orderId", AcpToolUtil.getOrderId());          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
		init.put("txnTime", AcpToolUtil.getCurrentTime());      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效		
		init.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）		
		init.put("txnAmt", init.get("txnAmt"));                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额		
		init.put("reqReserved", "透传信息");                    //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
		
		/***要调通交易以下字段必须修改***/
		init.put("origQryId", init.get("oriRouteSeq"));      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
	
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
	}

}
