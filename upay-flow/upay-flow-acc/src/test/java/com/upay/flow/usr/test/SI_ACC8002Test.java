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
public class SI_ACC8002Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC8002Test.class);

    @Resource(name = "SI_ACC8002")
    private IDipperHandler<Message> SI_ACC8002;// 注册


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("userId", "UR000000000522");
        bodys.put("chnlId", "02");
        bodys.put("transCode", "SI_ACC8002");
        bodys.put("transAmtStr", "100");
        bodys.put("tradePwd", "14758");
//        bodys.put("payeeComMail", );
        bodys.put("payeeMobile", "13733333335");
        
        
        
        Message rspMsg = (Message) SI_ACC8002.handle(getMessage(bodys, "SI_ACC8002"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
