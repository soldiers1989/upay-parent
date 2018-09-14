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
public class SI_ACC0002Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC0002Test.class);

    @Resource(name = "SI_ACC0002")
    private IDipperHandler<Message> SI_ACC0002;//


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("userId", "UR000000000009");
        bodys.put("vAcctNo", "20160809000000000099");
        bodys.put("chnlId", "1");
        bodys.put("transCode", "SI_ACC0002");
        // bodys.put("VerifyKey", "YZM");
        Message rspMsg = (Message) SI_ACC0002.handle(getMessage(bodys, "SI_ACC0002"));

        logger.debug("{}", rspMsg);

    }
}
