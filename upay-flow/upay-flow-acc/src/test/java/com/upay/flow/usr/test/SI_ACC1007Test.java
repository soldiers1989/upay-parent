package com.upay.flow.usr.test;

import java.math.BigDecimal;
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
public class SI_ACC1007Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC1007Test.class);

    @Resource(name = "SI_ACC1007")
    private IDipperHandler<Message> SI_ACC1007;// 注册


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("chnlId", "01");
        bodys.put("sysTime", new Date());
        bodys.put("transCode", "SI_ACC1007");
        bodys.put("sysSeq", "2145436547567538");
        bodys.put("payeeAccNo", "6229807711500009929");
//        bodys.put("merNo", "MER2016000117");
        bodys.put("operatorType", "1");
        
        bodys.put("transAmt", new BigDecimal(100));

        Message rspMsg = (Message) SI_ACC1007.handle(getMessage(bodys, "SI_ACC1007"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
