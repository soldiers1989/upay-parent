package com.upay.flow.usr.test;

import java.util.Date;
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
public class SI_ACC1002Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC1002Test.class);

    @Resource(name = "SI_ACC1002")
    private IDipperHandler<Message> SI_ACC1002;// 注册


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("chnlId", "01");
        bodys.put("sysTime", new Date());
        bodys.put("userId", "UR000000000044");
        bodys.put("transCode", "SI_ACC1002");
     
        bodys.put("thirdAuthChnl", "0001");
        bodys.put("eBindAcctNo", "6231351234567859");
        bodys.put("bindAcctType", "11");
        

        Message rspMsg = (Message) SI_ACC1002.handle(getMessage(bodys, "SI_ACC1002"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
