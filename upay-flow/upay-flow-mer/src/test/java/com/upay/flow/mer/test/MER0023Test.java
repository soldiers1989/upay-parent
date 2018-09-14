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
public class MER0023Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0023Test.class);

    //微信特约商户配置 对外接口
    @Resource(name = "SI_MER0023")
    private IDipperHandler<Message> SI_MER0023;// 商户后台注册

    @Test
    public void testSI_MER0011() throws Exception {
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("merNo", "MER2017000124");
//        body.put("jsapiPath", "http://www.jd.com/cn/");
        body.put("subAppid", "MER2017000124");
        body.put("subscribeAppid", "300");
        body.put("operateFlag", "ATTENTIONCONF");
        
        Message rspMsg = (Message) SI_MER0023.handle(getMessage(body, "SI_MER0023"));

        logger.debug("{}", rspMsg);

    }
}
