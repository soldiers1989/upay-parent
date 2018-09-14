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
public class MER0021Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0021Test.class);

    
    @Resource(name = "SI_MER0026")
    private IDipperHandler<Message> SI_MER0026;// 商户后台注册

    @Test
    public void testSI_MER0011() throws Exception {
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("merNo", "1");
        body.put("merchantName", "1");
        body.put("merchantShortname", "1");
        body.put("servicePhone", "1");
        body.put("business", "1");
        body.put("merchantRemark", "1");//商户备注
        body.put("channelId", "1");//渠道号
        
        Message rspMsg = (Message) SI_MER0026.handle(getMessage(body, "SI_MER0026"));

        logger.debug("{}", rspMsg);
    }
}
