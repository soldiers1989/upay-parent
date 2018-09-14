package com.upay.pay.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author  liudan
 * @version v1.0
 * @since  2017.12.01
 * @date 2017.12.01
 * 银联营销活动余额查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class Pay1010Test extends Thread {

	private Logger logger = LoggerFactory.getLogger(Pay1010Test.class);
	
	private static IDipperHandler<Message> SI_PAY1010;
	

	static{
		ApplicationContext ac = new FileSystemXmlApplicationContext(
				"classpath:spring/test-service.xml");
		SI_PAY1010 = (IDipperHandler<Message>) ac
				.getBean("SI_PAY1010");
	}

	@Override
	public void run() {
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("transCode", "SI_PAY1010");
        headMap.put("chnlId", "02");
        headMap.put("platform", "01");
        //headMap.put("userId", "UR000000000009");
//        headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        //bodyMap.put("payPwd", "123456");
        bodyMap.put("transCode", "SI_PAY1010");
        bodyMap.put("merNo", "MER2017000211123123123");
        bodyMap.put("beginTime", "2018-02-01");
        bodyMap.put("endTime", "2018-02-08");
        
        bodyMap.put("endTime", "2018-02-08");
//        bodyMap.put("platformUserNo", "13441414141");//userId
//        bodyMap.put("userName", "0002");
//        bodyMap.put("certName", "0002");
//        bodyMap.put("certType", "01");
//        bodyMap.put("certNo", "0002");
//        bodyMap.put("sex", "1");
//        bodyMap.put("mobile", "12425636526");
//        bodyMap.put("email", "124256365236@126.com");
        bodyMap.put("tradeOrg", "1054646761664449416");
        bodyMap.put("tradeDate", "2018-04-03");
        bodyMap.put("tradeTime", "09:28:26");
        bodyMap.put("tradeCode", "3063001801");
        bodyMap.put("tradeName", "烟草结算回单打印(卷烟销售资金)");
        bodyMap.put("tradeNo", "3063001801");
        bodyMap.put("tradeCode", "56544649");
        
        bodyMap.put("accNumber", "6225888720356236");
        bodyMap.put("accName", "杨辉勇");
        bodyMap.put("accOpeningBank", "1646167461");
        bodyMap.put("shroffAccNumber", "62646497979161");
        bodyMap.put("shroffAccName", "杨白劳");
        
        bodyMap.put("payNumber", "6225888720356237");
        bodyMap.put("payName", "老李狗");
        bodyMap.put("payOpeningBank", "1746167461");
        bodyMap.put("shroffPayNumber", "62646497979162");
        bodyMap.put("shroffPayName", "黄狗狗");
        
        bodyMap.put("tradeChannel", "批量代扣/网上结算");
        
        
//        bodyMap.put("sysTime", new Date());
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        headMap, bodyMap), MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        try {
        	SI_PAY1010.handle(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
			Thread test = new Pay1010Test();
			test.start();
	}
}
