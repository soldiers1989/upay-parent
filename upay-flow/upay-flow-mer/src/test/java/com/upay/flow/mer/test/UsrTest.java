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
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class UsrTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(UsrTest.class);

    @Resource(name = "SI_USR0006")
    private IDipperHandler<Message> SI_USR0006;// 注册

    @Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("userId", "UR000000000001");
        bodys.put("mobile", "18721755540");
        bodys.put("certNo", "333222333322212323");
        bodys.put("certType", "00");
        bodys.put("unionType", "02");
        bodys.put("unionNo", "");
        bodys.put("loginMode", "0");
        bodys.put("isMutiplueLogin", "0");
        bodys.put("loginPwd", "123456789");
        // bodys.put("systime", new Date());
        // bodys.put("sysDate", DateUtil.parse(DateUtil.format(new Date(),
        // "yyMMdd"), "yyMMdd"));
        // bodys.put("pwdCheckFlag", "P");
        bodys.put("chnlId", "1");
        bodys.put("transCode", "SI_GNR0004");
        bodys.put("VerifyKey", "YZM");
        Message rspMsg = (Message) SI_USR0006.handle(getMessage(bodys, "SI_USR0006"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
