package com.upay.gateway.client.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

public class TestSubMchAddMer {
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

		IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx
				.getBean("AT_AlipaySubMchAddMerHandler");

		Message m = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create(Constants.ResponseCode.SUCCESS, ""));
		Map<String, Object> map = (Map<String, Object>) m.getTarget()
				.getBodys();
		map.put("_TRAN_CODE", "subMchAdd");
		map.put("externalId", "MER2017000211");//商户编号，由机构定义  2088921560866102
		map.put("merName", "小青年");//商户名称
		map.put("aliasName", "小飞饺子"); //商户简称 
		map.put("servicePhone", "13233258318"); //商户客服电话
		map.put("categoryId", "2015050700000000"); //商户经营类目
		map.put("alipaySource", "2088501624560335"); //商户经营类目
		map.put("orgPid", "2088721382101609"); //商户经营类目
		
		map.put("mcc", "1731"); //商户经营类目
		//2088821147231702
/*		map.put("businessLicense", ""); //商户证件编号
		map.put("businessLicenseType", ""); //商户证件类型NATIONAL_LEGAL:营业执照；NATIONAL_LEGAL_MERGE:营业执照;INST_RGST_CTF:事业单位法人证书
		//商户联系人信息
		Map<String, Object> cantactInfo = new HashMap<String, Object>();
		cantactInfo.put("userName", "");// 联系人名字
		cantactInfo.put("phone", "");// 电话
		cantactInfo.put("mobile", "");// 手机号
		cantactInfo.put("email", "");// 电子邮件
		cantactInfo.put("contactType", "");//联系人类型 LEGAL_PERSON:法人；CONTROLLER:实际控制人；AGENT 代理人； OTHER 其他；
		cantactInfo.put("idCardNo", "");// 身份证号
		List<Object> list = new ArrayList<>();
		list.add(cantactInfo);
		map.put("cantactInfo", list);
		//商户地址信息
		Map<String, Object> addressInfo = new HashMap<String, Object>();
		addressInfo.put("provinceCode", "");//商户所在省份编码
		addressInfo.put("cityCode", "");// 商户所在城市编码
		addressInfo.put("districtCode", "");// 商户所在区县编码
		addressInfo.put("address", "");// 商户详细经营地址
		addressInfo.put("longitude", "");//经度
		addressInfo.put("latitude", "");// 纬度
		addressInfo.put("addressType", "");// 地址类型：BUSINESS_ADDRESS 经营地址 默认
		
		List<Object> addressInfoList = new ArrayList<>();
		list.add(addressInfo);
		map.put("addressInfo", addressInfoList);
		//商户对应银行所开立的结算卡信息
		Map<String, Object> bankcardInfo = new HashMap<String, Object>();
		bankcardInfo.put("cardNo", "");//银行卡号
		bankcardInfo.put("cardName", "");// 持卡人姓名
		List<Object> bankcardInfoList = new ArrayList<>();
		list.add(bankcardInfo);
		map.put("bankcardInfo", bankcardInfoList);
		*/
		Map<String, Object> payCodeInfo = new HashMap<String, Object>();
		payCodeInfo.put("payCodeInfoUrl", "https://www.web.com/cashier");
		
		List<Object> payCodeInfoList = new ArrayList<>();
		payCodeInfoList.add(payCodeInfo);
		map.put("payCodeInfo", payCodeInfoList);
		
		Map<String, Object> logonId = new HashMap<String, Object>();
		logonId.put("alipayLogonId", "https://www.web.com/cashier");
		
		List<Object> logonIdList = new ArrayList<>();
		logonIdList.add(logonId);
		map.put("logonId", logonIdList);
//		map.put("memo", ""); //商户备注信息

		m = handler.handle(m);
		System.out.println(m.getTarget().getBodys().toString());

	}

}
