package com.upay.flow.usr.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
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
public class SI_USR0027Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_USR0027Test.class);

    @Resource(name = "SI_USR0027")
    private IDipperHandler<Message> SI_USR0027;


    @org.junit.Test
    public void testSI_USR0016() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("userId", "UR000001733337");
        bodys.put("userId", "UR000001733337");
        Message rspMsg = (Message) SI_USR0027.handle(getMessage(bodys, "SI_USR0024"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
