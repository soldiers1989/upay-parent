package com.upay.pay.test;

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
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class DemTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(DemTest.class);

    @Resource(name = "SI_PAY0004")
    private IDipperHandler<Message> SI_PAY0004;// 注册


    @org.junit.Test
    public void testSI_USR0001() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("orderNo", "123456");
        Message rspMsg = (Message) SI_PAY0004.handle(getMessage(bodys, "SI_PAY0004"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
