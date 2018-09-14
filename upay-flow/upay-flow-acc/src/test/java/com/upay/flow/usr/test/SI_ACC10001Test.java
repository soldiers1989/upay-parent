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
public class SI_ACC10001Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC10001Test.class);

    @Resource(name = "SI_ACC1001")
    private IDipperHandler<Message> SI_ACC1001;// 注册


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("chnlId", "02");
        bodys.put("certWeakWay", "02");
        bodys.put("userCertLevel", "02");
        bodys.put("userId", "UR000000000139");
        bodys.put("reserveMobile", "15112345679");
        bodys.put("certType", "01");
        bodys.put("certNo", "420881199406067987");
        bodys.put("certName", "陌陌");
//        bodys.put("smsCode", "999999");
        bodys.put("thirdAuthChnl", "0004");
        bodys.put("isFirst", "2");
        
//        bodys.put("cvn2", "055");
//        bodys.put("validDate", "202902");
        
        bodys.put("bindAcctType", "11");// 绑定账户类型为信用卡
        bodys.put("eBindAcctNo", "6214831235653110"); 
        bodys.put("transCode", "SI_ACC1001");
        bodys.put("sysTime", new Date());

        Message rspMsg = (Message) SI_ACC1001.handle(getMessage(bodys, "SI_ACC1001"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
