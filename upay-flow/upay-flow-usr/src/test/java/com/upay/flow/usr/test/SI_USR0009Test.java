package com.upay.flow.usr.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SI_USR0009Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_USR0009Test.class);

    @Resource(name = "SI_USR0009")
    private IDipperHandler<Message> SI_USR0009;


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("userId", "UR000000000080");
        bodys.put("chnlId", "01");
        bodys.put("transCode", "SI_USR0009");
        bodys.put("smsCode", "999999");
        bodys.put("newTradePwd", "12345");
        bodys.put("pwdFlag", "2");
        Message rspMsg = (Message) SI_USR0009.handle(getMessage(bodys, "SI_USR0009"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
