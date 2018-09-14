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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class MER0028Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0028Test.class);

    
    @Resource(name = "SI_MER0028")
    private IDipperHandler<Message> SI_MER0028;// 开通微信支付

    @Test
    public void testSI_MER0011() throws Exception {
    	
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("merNo", "MER2017000123");
        body.put("categoryId", "2015062600004525");
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> bankInfo = new HashMap<Object, Object>();
		bankInfo.put("cardNo", "6226200106341259");
		bankInfo.put("cardName", "杨辉勇");
		list.add(bankInfo);
		body.put("bankcardInfo", list);
        
        Message rspMsg = (Message) SI_MER0028.handle(getMessage(body, "SI_MER0028"));
        logger.debug("{}", rspMsg);

    }
}
