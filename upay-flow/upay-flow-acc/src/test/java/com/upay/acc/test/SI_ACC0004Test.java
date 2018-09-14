package com.upay.acc.test;

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
public class SI_ACC0004Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC0004Test.class);

    @Resource(name = "SI_ACC0004")
    private IDipperHandler<Message> SI_ACC0004;//


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("userId", "UR000000000062");
        bodys.put("chnlId", "01");
        bodys.put("transCode", "SI_ACC0004");
        bodys.put("pendTransCode", "SI_ACC0004");
        bodys.put("acctType", "02");
        Message rspMsg = (Message) SI_ACC0004.handle(getMessage(bodys, "SI_ACC0004"));

        logger.debug("{}", rspMsg);

    }
}
