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
public class MER0013Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0013Test.class);

    @Resource(name = "SI_MER0013")
    private IDipperHandler<Message> SI_USR0006;// 登录
    
    @Resource(name = "SI_MER0013")
    private IDipperHandler<Message> SI_MER0013;// 商户资金变动查询

    @Test
    public void testSI_MER0011() throws Exception {
    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
    	String start = "2016-7-16";
    	String end = "2016-9-2";
    	Date startDate = sim.parse(start);
    	Date endDate = sim.parse(end);
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("merchantName", "沃尔玛百货公司");
        body.put("merchantShortname", "沃尔玛");
        body.put("servicePhone", "039499999");
        body.put("contact", "张国荣");
        body.put("business", "203");
        body.put("contactPhone", "039499999");
        body.put("contactEmail", "");
        body.put("merchantRemark", "沃尔玛东风路店");
        body.put("merNo", "777888");
        
        Message rspMsg = (Message) SI_MER0013.handle(getMessage(body, "SI_MER0013"));

        logger.debug("{}", rspMsg);

    }
}
