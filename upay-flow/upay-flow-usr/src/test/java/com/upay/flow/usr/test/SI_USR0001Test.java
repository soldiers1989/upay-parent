package com.upay.flow.usr.test;

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
import com.upay.busi.usr.service.dto.WeakAuthDto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SI_USR0001Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_USR0001Test.class);

    @Resource(name = "SI_USR0001")
    private IDipperHandler<Message> SI_USR0001;// 注册


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
    	
//        bodys.put("userName", "13888888888");
        bodys.put("mobile", "17700000007");
//        bodys.put("loginPwd", "123456");
        bodys.put("email", "");
        bodys.put("merNo", "13968111");
//        bodys.put("regType", "02");
        bodys.put("transCode", "SI_USR0001");
        bodys.put("chnlId", "02");
        
        Message rspMsg = (Message) SI_USR0001.handle(getMessage(bodys, "SI_USR0001"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
