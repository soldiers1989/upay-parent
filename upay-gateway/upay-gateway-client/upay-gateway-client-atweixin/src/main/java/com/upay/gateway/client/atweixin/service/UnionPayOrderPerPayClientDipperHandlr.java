package com.upay.gateway.client.atweixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.DateUtil;

/**
 * 银联at 统一下单
 * 
 * 
 * @author hry
 */
public class UnionPayOrderPerPayClientDipperHandlr extends
		AbstractAtWeiXinClientDipperHandler {
	@Override
	protected void setInitParam(Map<String, Object> paramMap) {
		paramMap.put("transCode", paramMap.get("tranCode"));
		paramMap.put("out_trade_no", paramMap.get("transSubSeq"));
		paramMap.put("fee_type", paramMap.get("feeType"));
		paramMap.put("total_fee", paramMap.get("totalFee"));
		paramMap.put("trade_type", paramMap.get("tradeType"));
		paramMap.put("spbill_create_ip", paramMap.get("spbillCreateIp"));
		paramMap.put("sign_type", "RSA");
		paramMap.remove("transSubSeq");
		paramMap.remove("tranCode");
		paramMap.remove("feeType");
		paramMap.remove("totalFee");
		paramMap.remove("tradeType");
		paramMap.remove("spbillCreateIp");

	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.put("coreStat", CoreStat.SUCESS);
		String weixintimeEnd = (String) source.get("timeEnd");
		String bankType = (String) source.get("bankType");
		if (StringUtils.isNotBlank(weixintimeEnd)) {
			Date timeEnd = DateUtil.parse(weixintimeEnd, "yyyyMMddhhmmss");
			weixintimeEnd = DateUtil.format(timeEnd, "yyyy-MM-dd HH:mm:ss");
			target.put("timeEnd", weixintimeEnd);
		}
		if (StringUtils.isNotBlank(bankType)) {
			switch (bankType) {
			case "CFT":
				bankType = "零钱";
				break;
			case "ICBC_DEBIT":
				bankType = "工商银行(借记卡)";
				break;
			case "ICBC_CREDIT":
				bankType = "工商银行(信用卡)";
				break;
			case "ABC_DEBIT":
				bankType = "农业银行(借记卡)";
				break;
			case "ABC_CREDIT":
				bankType = "农业银行(信用卡)";
				break;
			case "PSBC_DEBIT":
				bankType = "邮政储蓄银行(借记卡)";
				break;
			case "PSBC_CREDIT":
				bankType = "邮政储蓄银行(信用卡)";
				break;
			case "CCB_DEBIT":
				bankType = "建设银行(借记卡)";
				break;
			case "CCB_CREDIT":
				bankType = "建设银行(信用卡)";
				break;
			case "CMB_DEBIT":
				bankType = "招商银行(借记卡)";
				break;
			case "CMB_CREDIT":
				bankType = "招商银行(信用卡)";
				break;
			case "BOC_DEBIT":
				bankType = "中国银行(借记卡)";
				break;
			case "BOC_CREDIT":
				bankType = "中国银行(信用卡)";
				break;
			case "COMM_DEBIT":
				bankType = "交通银行(借记卡)";
				break;
			case "SPDB_DEBIT":
				bankType = "浦发银行(借记卡)";
				break;
			case "SPDB_CREDIT":
				bankType = "浦发银行(信用卡)";
				break;
			case "GDB_DEBIT":
				bankType = "广发银行(借记卡)";
				break;
			case "GDB_CREDIT":
				bankType = "广发银行(信用卡";
				break;
			case "CMBC_DEBIT":
				bankType = "民生银行(借记卡)";
				break;
			case "CMBC_CREDIT":
				bankType = "民生银行(信用卡)";
				break;
			case "PAB_DEBIT":
				bankType = "平安银行(借记卡)";
				break;
			case "PAB_CREDIT":
				bankType = "平安银行(信用卡)";
				break;
			case "CEB_DEBIT":
				bankType = "光大银行(借记卡)";
				break;
			case "CEB_CREDIT":
				bankType = "光大银行(信用卡)";
				break;
			case "CIB_DEBIT":
				bankType = "兴业银行(借记卡)";
				break;
			case "CIB_CREDIT":
				bankType = "兴业银行(信用卡)";
				break;
			case "CITIC_DEBIT":
				bankType = "中信银行(借记卡)";
				break;
			case "CITIC_CREDIT":
				bankType = "中信银行(信用卡)";
				break;
			case "BOSH_DEBIT":
				bankType = "上海银行(借记卡)";
				break;
			case "BOSH_CREDIT":
				bankType = "上海银行(信用卡)";
				break;
			case "CRB_DEBIT":
				bankType = "华润银行(借记卡)";
				break;
			case "HZB_DEBIT":
				bankType = "杭州银行(借记卡)";
				break;
			case "HZB_CREDIT":
				bankType = "杭州银行(信用卡";
			case "BSB_DEBIT":
				bankType = "包商银行(借记卡)";
				break;
			case "BSB_CREDIT":
				bankType = "包商银行(信用卡)";
				break;
			case "CQB_DEBIT":
				bankType = "重庆银行(借记卡)";
				break;
			case "SDEB_DEBIT":
				bankType = "顺德农商行(借记卡)";
				break;
			case "SZRCB_DEBIT":
				bankType = "深圳农商银行(借记卡)";
				break;
			case "HRBB_DEBIT":
				bankType = "哈尔滨银行(借记卡)";
				break;
			case "BOCD_DEBIT":
				bankType = " 成都银行(借记卡)";
				break;
			case "GDNYB_DEBIT":
				bankType = "南粤银行(借记卡)";
				break;
			case "GDNYB_CREDIT":
				bankType = "南粤银行(信用卡)";
				break;
			case "GZCB_DEBIT":
				bankType = "广州银行(借记卡)";
				break;
			case "GZCB_CREDIT":
				bankType = "广州银行(信用卡)";
				break;
			case "JSB_DEBIT":
				bankType = "江苏银行(借记卡)";
				break;
			case "JSB_CREDIT":
				bankType = "江苏银行(信用卡)";
				break;
			case "NBCB_DEBIT":
				bankType = "宁波银行(借记卡)";
				break;
			case "NBCB_CREDIT":
				bankType = "宁波银行(信用卡)";
				break;
			case "NJCB_DEBIT":
				bankType = "南京银行(借记卡)";
				break;
			case "JZB_DEBIT":
				bankType = "晋中银行(借记卡)";
				break;
			case "KRCB_DEBIT":
				bankType = "昆山农商(借记卡)";
				break;
			case "LJB_DEBIT":
				bankType = "龙江银行(借记卡)";
				break;
			case "LNNX_DEBIT":
				bankType = "辽宁农信(借记卡)";
				break;
			case "LZB_DEBIT":
				bankType = "兰州银行(借记卡)";
				break;
			case "WRCB_DEBIT":
				bankType = "无锡农商(借记卡)";
				break;
			case "ZYB_DEBIT":
				bankType = "中原银行(借记卡)";
				break;
			case "ZJRCUB_DEBIT":
				bankType = "浙江农信(借记卡)";
				break;
			case "WZB_DEBIT":
				bankType = "西安银行(借记卡)";
				break;
			case "XAB_DEBIT":
				bankType = "西安银行(借记卡)";
				break;
			case "JXNXB_DEBIT":
				bankType = "江西农信(借记卡)";
				break;
			case "NCB_DEBIT":
				bankType = "宁波通商银行(借记卡)";
				break;
			case "NYCCB_DEBIT":
				bankType = "南阳村镇银行(借记卡)";
				break;
			case "NMGNX_DEBIT":
				bankType = "内蒙古农信(借记卡)";
				break;
			case "SXXH_DEBIT":
				bankType = "陕西信合(借记卡)";
				break;
			case "SRCB_CREDIT":
				bankType = "上海农商银行(信用卡)";
				break;
			case "SJB_DEBIT":
				bankType = "盛京银行(借记卡)";
				break;
			case "SDRCU_DEBIT":
				bankType = "山东农信(借记卡)";
				break;
			case "SRCB_DEBIT":
				bankType = "上海农商银行(借记卡)";
				break;
			case "SCNX_DEBIT":
				bankType = "四川农信(借记卡)";
				break;
			case "QLB_DEBIT":
				bankType = "齐鲁银行(借记卡)";
				break;
			case "QDCCB_DEBIT":
				bankType = "青岛银行(借记卡)";
				break;
			case "PZHCCB_DEBIT":
				bankType = "攀枝花银行(借记卡)";
				break;
			case "ZJTLCB_DEBIT":
				bankType = "江泰隆银行(借记卡)";
				break;
			case "TJBHB_DEBIT":
				bankType = "天津滨海农商行(借记卡)";
				break;
			case "WEB_DEBIT":
				bankType = "微众银行(借记卡)";
				break;
			case "YNRCCB_DEBIT":
				bankType = "云南农信(借记卡)";
				break;
			case "WFB_DEBIT":
				bankType = "潍坊银行(借记卡)";
				break;
			case "WHRC_DEBIT":
				bankType = "武汉农商行(借记卡";
				break;
			case "ORDOSB_DEBIT":
				bankType = "鄂尔多斯银行(借记卡)";
				break;
			case "XJRCCB_DEBIT":
				bankType = "新疆农信银行(借记卡)";
				break;
			case "ORDOSB_CREDIT":
				bankType = "鄂尔多斯银行(信用卡)";
				break;
			case "CSRCB_DEBIT":
				bankType = "常熟农商银行(借记卡)";
				break;
			case "JSNX_DEBIT":
				bankType = "江苏农商行(借记卡)";
				break;
			case "GRCB_CREDIT":
				bankType = "广州农商银行(信用卡)";
				break;
			case "GLB_DEBIT":
				bankType = "桂林银行(借记卡)";
				break;
			case "GDRCU_DEBIT":
				bankType = "广东农信银行(借记卡)";
				break;
			case "GDHX_DEBIT":
				bankType = "广东华兴银行(借记卡)";
				break;
			case "FJNX_DEBIT":
				bankType = "福建农信银行(借记卡)";
				break;
			case "DYCCB_DEBIT":
				bankType = "德阳银行(借记卡)";
				break;
			case "DRCB_DEBIT":
				bankType = "东莞农商行(借记卡)";
				break;
			case "CZCB_DEBIT":
				bankType = "稠州银行(借记卡)";
				break;
			case "CZB_DEBIT":
				bankType = "浙商银行(借记卡)";
				break;
			case "CZB_CREDIT":
				bankType = "浙商银行(信用卡)";
				break;
			case "GRCB_DEBIT":
				bankType = "广州农商银行(借记卡)";
				break;
			case "CSCB_DEBIT":
				bankType = "长沙银行(借记卡)";
				break;
			case "CQRCB_DEBIT":
				bankType = "重庆农商银行(借记卡)";
				break;
			case "CBHB_DEBIT":
				bankType = "渤海银行(借记卡)";
				break;
			case "BOIMCB_DEBIT":
				bankType = "内蒙古银行(借记卡)";
				break;
			case "BOD_DEBIT":
				bankType = "东莞银行(借记卡)";
				break;
			case "BOD_CREDIT":
				bankType = "东莞银行(信用卡)";
				break;
			case "BOB_DEBIT":
				bankType = "北京银行(借记卡)";
				break;
			case "BNC_DEBIT":
				bankType = "江西银行(借记卡)";
				break;
			case "BJRCB_DEBIT":
				bankType = "北京农商行(借记卡)";
				break;
			case "AE_CREDIT":
				bankType = "AE(信用卡)";
				break;
			case "GYCB_CREDIT":
				bankType = "贵阳银行(信用卡)";
				break;
			case "JSHB_DEBIT":
				bankType = "晋商银行(借记卡)";
				break;
			case "JRCB_DEBIT":
				bankType = "江阴农商行(借记卡)";
				break;
			case "JNRCB_DEBIT":
				bankType = "江南农商(借记卡)";
				break;
			case "JLNX_DEBIT":
				bankType = "吉林农信(借记卡)";
				break;
			case "JLB_DEBIT":
				bankType = "吉林银行(借记卡)";
				break;
			case "JJCCB_DEBIT":
				bankType = "九江银行(借记卡)";
				break;
			case "HXB_DEBIT":
				bankType = " 华夏银行(借记卡)";
				break;
			case "HXB_CREDIT":
				bankType = "华夏银行(信用卡)";
				break;
			case "HUNNX_DEBIT":
				bankType = "湖南农信(借记卡)";
				break;
			case "HSB_DEBIT":
				bankType = "徽商银行(借记卡)";
				break;
			case "HSBC_DEBIT":
				bankType = "恒生银行(借记卡)";
				break;
			case "HRXJB_DEBIT":
				bankType = "华融湘江银行(借记卡)";
				break;
			case "HNNX_DEBIT":
				bankType = "河南农信(借记卡)";
				break;
			case "HKBEA_DEBIT":
				bankType = "东亚银行(借记卡)";
				break;
			case "HEBNX_DEBIT":
				bankType = "河北农信(借记卡)";
				break;
			case "HBNX_DEBIT":
				bankType = "湖北农信(借记卡)";
				break;
			case "HBNX_CREDIT":
				bankType = "湖北农信(信用卡)";
				break;
			case "GYCB_DEBIT":
				bankType = "贵阳银行(借记卡)";
				break;
			case "GSNX_DEBIT":
				bankType = "甘肃农信(借记卡)";
				break;
			case "JCB_CREDIT":
				bankType = "JCB(信用卡)";
				break;
			case "MASTERCARD_CREDIT":
				bankType = "MASTERCARD(信用卡)";
				break;
			case "VISA_CREDIT":
				bankType = "VISA(信用卡)";
				break;

			default:
				bankType = "微信支付";

			}
			target.put("bankType", bankType);
		}
		if(source.containsKey("wcPayData")){
		JSONObject jsonObject = JSON.parseObject((String) source
				.get("wcPayData"));
		source.put("repAppid", jsonObject.getString("appId"));
		source.put("nonceStr", jsonObject.getString("nonceStr"));
		source.put("paySign", jsonObject.getString("paySign"));
		source.put("prepayId", jsonObject.getString("package").split("=")[1]);
		source.put("signType", jsonObject.getString("signType"));
		source.put("timeStamp", jsonObject.getString("timeStamp"));
		source.remove("wcPayData");
		}
		target.putAll(source);
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		LOG.info("================远程访问失败，原因： 错误码：" + source.get("errCode")
				+ ",,,,错误信息：" + source.get("errMsg"));
		target.putAll(source);
	}

	@Override
	protected void doErrorHandle(Map<String, Object> arg0, Fault fault) {
		// TODO Auto-generated method stub
		LOG.info("================远程访问错误，原因： 错误码：" + fault.getOutCode()
				+ ",,,,错误信息：" + fault.getOutMsg());

		arg0.put("errCode", fault.getOutCode());
		arg0.put("errMsg", fault.getOutMsg());

	}
}
