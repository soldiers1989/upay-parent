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
public class SI_USR0004Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_USR0004Test.class);

    @Resource(name = "SI_USR0004")
    private IDipperHandler<Message> SI_USR0004;


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("userId", "UR000000000062");
        bodys.put("chnlId", "01");
        bodys.put("transCode", "SI_USR0004");
        bodys.put("loginPwd", "123456");
        bodys.put("newLoginPwd", "1234");
        bodys.put("pwdFlag", "1");
        Message rspMsg = (Message) SI_USR0004.handle(getMessage(bodys, "SI_USR0004"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
