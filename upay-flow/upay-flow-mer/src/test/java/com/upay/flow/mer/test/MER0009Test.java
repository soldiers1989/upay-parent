package com.upay.flow.mer.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MER0009Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0009Test.class);

    @Resource(name = "SI_USR0006")
    private IDipperHandler<Message> SI_USR0006;// 登录
    
    @Resource(name = "SI_MER0009")
    private IDipperHandler<Message> SI_MER0009;// 二级商户费率信息维护

    @Test
    public void testSI_MER0009() throws Exception {
    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
    	String start = "2016-9-1";
    	String end = "2016-9-2";
    	Date startDate = sim.parse(start);
    	Date endDate = sim.parse(end);
    	
		Map<String, Object> bodys = new HashMap<String, Object>();
		bodys.put("mobile", "13444444444");
		bodys.put("loginPwd", "123321");
		bodys.put("chnlId", "02");
		bodys.put("transCode", "SI_USR0006");
		Message rspMsg = (Message) SI_USR0006.handle(getMessage(bodys,"SI_USR0006"));
		Map<String, Object> respBody = (Map<String, Object>) rspMsg.getTarget().getBodys();
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userId", respBody.get("userId"));
        body.put("chnlId", respBody.get("chnlId"));
        body.put("sessionId", respBody.get("sessionId"));
        body.put("feeName", "接口修改测试");
        body.put("acctType", "01");
        body.put("terminalType", "00");
        body.put("secMerNo", "3");
        body.put("txnCode", "SI_MER0009");
        body.put("feeCode", "0001");
        body.put("freeCycle", "0");
        body.put("startDate", startDate);
        body.put("endDate", endDate);
        body.put("transCode", "SI_MER0009");
        Message rspMsg1 = (Message) SI_MER0009.handle(getMessage(body, "SI_MER0009"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);
        logger.debug("{}", rspMsg1);

    }
}
