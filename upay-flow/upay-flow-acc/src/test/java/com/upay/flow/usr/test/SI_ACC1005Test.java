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
public class SI_ACC1005Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC1005Test.class);

    @Resource(name = "SI_ACC1005")
    private IDipperHandler<Message> SI_ACC1005;// 解绑


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

        bodys.put("chnlId", "01");
        bodys.put("sysTime", new Date());
        bodys.put("transCode", "SI_ACC1005");
        bodys.put("sysSeq", "214543654756758");
        bodys.put("eBindAcctNo", "6214831234567810");
//        bodys.put("eBindAcctNo", "6229807711500096801");
        bodys.put("certName", "刘兵");
        bodys.put("certNo", "510623198608108436");
        bodys.put("reserveMobile", "13551324852");

        bodys.put("vBindAcctNo", "1040001234567810");

        Message rspMsg = (Message) SI_ACC1005.handle(getMessage(bodys, "SI_ACC1005"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
