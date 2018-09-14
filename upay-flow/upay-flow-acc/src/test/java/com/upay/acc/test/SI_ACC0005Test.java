package com.upay.acc.test;

import java.math.BigDecimal;
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
public class SI_ACC0005Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC0005Test.class);

    @Resource(name = "SI_ACC0005")
    private IDipperHandler<Message> SI_ACC0005;//


    @org.junit.Test
    public void testSI_USR0005() throws Exception {    
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("userId", "UR000000000088");
        bodys.put("acctType", "01");
        bodys.put("transCode", "SI_ACC0005");
        bodys.put("chnlId", "01");
        bodys.put("amtFlag", "1");
        bodys.put("dcFlag", "1");
        bodys.put("transAmt", BigDecimal.valueOf(10));
        Message message = SI_ACC0005.handle(getMessage(bodys, "SI_ACC0005"));
        logger.debug("{}", message);

    }
}
