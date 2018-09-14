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

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MER0008Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0008Test.class);

    @Resource(name = "SI_USR0006")
    private IDipperHandler<Message> SI_USR0006;// 登录
    
    @Resource(name = "SI_MER0008")
    private IDipperHandler<Message> SI_MER0008;// 二级商户费率信息查询

    @Test
    public void testSI_MER0008() throws Exception {
		Map<String, Object> bodys = new HashMap<String, Object>();
		bodys.put("mobile", "13444444444");
		bodys.put("loginPwd", "123321");
		//bodys.put("loginMode","1");
		bodys.put("chnlId", "02");
		bodys.put("transCode", "SI_USR0006");
		Message rspMsg = (Message) SI_USR0006.handle(getMessage(bodys,"SI_USR0006"));
		Map<String, Object> respBody = (Map<String, Object>) rspMsg.getTarget().getBodys();
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userId", respBody.get("userId"));
        body.put("chnlId", respBody.get("chnlId"));
        body.put("sessionId", respBody.get("sessionId"));
        //body.put("currentNum", 1);
        //body.put("pageIndex", 2);
        body.put("transCode", "SI_MER0008");
        Message rspMsg1 = (Message) SI_MER0008.handle(getMessage(body, "SI_MER0008"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);
        logger.debug("{}", rspMsg1);

    }
}
