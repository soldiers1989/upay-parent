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
import com.pactera.dipper.core.utils.Constants.ResponseCode;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SI_ACC0002Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC0002Test.class);

    @Resource(name = "SI_ACC0002")
    private IDipperHandler<Message> SI_ACC0002;// 注册


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("chnlId", "01");
        bodys.put("userId", "UR000000000063");
        bodys.put("transCode", "SI_ACC0002");
        Message rspMsg = (Message) SI_ACC0002.handle(getMessage(bodys, "SI_ACC0002"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
