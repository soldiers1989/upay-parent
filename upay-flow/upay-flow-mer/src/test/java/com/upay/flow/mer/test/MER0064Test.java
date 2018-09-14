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
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class MER0064Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0064Test.class);

    
    @Resource(name = "SI_MER0064")
    private IDipperHandler<Message> SI_MER0064;// 开通微信支付

    @Test
    public void testSI_MER0011() throws Exception {
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("merNo", "MER2017000162");
        body.put("channelId", "10100000");
        body.put("merchantRemark", "MER2017000124");
        body.put("business", "165");
        Message rspMsg = (Message) SI_MER0064.handle(getMessage(body, "SI_MER0064"));
        logger.debug("{}", rspMsg);

    }
}
