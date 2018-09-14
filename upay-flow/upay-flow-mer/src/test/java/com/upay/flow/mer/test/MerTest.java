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
public class MerTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(UsrTest.class);

    @Resource(name = "SI_USR0006")
    private IDipperHandler<Message> SI_USR0006;// 登录
    
    @Resource(name = "SI_MER0001")
    private IDipperHandler<Message> SI_MER0001;// 特约商户申请

    @Test
    public void testMer() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("mobile", "18721755540");
        bodys.put("loginPwd", "123321");
        bodys.put("chnlId", "02");
        bodys.put("transCode", "SI_USR0006");
        Message rspMsg = (Message) SI_USR0006.handle(getMessage(bodys, "SI_USR0006"));
        Map<String, Object> respBody = (Map<String, Object>) rspMsg.getTarget().getBodys();
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userId",respBody.get("userId"));
        body.put("chnlId", respBody.get("chnlId"));
        body.put("transCode", "SI_MER0001");
        body.put("merName", "集中测试");
        body.put("merWithoutPwdSign", "1");
        body.put("payopenflag", "1");
        body.put("merBusiType", "02");
        body.put("contact", "集中测试");
        body.put("contactTel", "83216545");
        body.put("contactMobile", "13444444444");
        body.put("contactEmail", "111111@123.com");
        body.put("merTel", "87654321");
        body.put("merFax", "999999");
        body.put("merAddr", "地址信息123");
        body.put("merPostalCode", "999999");
        body.put("websiteCode", "999999");
        body.put("websiteName", "网");
        body.put("websiteDomain", "www.999999.com");
        body.put("websiteScop", "999999");
        body.put("companyName", "九九");
        body.put("egalPersonName", "九九九");
        body.put("egalPersonIdType", "09");
        body.put("egalPersonIdNo", "999999999999");
        body.put("companyIdType", "09");
        body.put("companyIdNo", "09090909");
        body.put("orgDeptNo", "09");
        body.put("busiLicenseId", "09");
        Message rspMsg1 = (Message) SI_MER0001.handle(getMessage(body, "SI_MER0001"));

        // if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
        logger.debug("{}", rspMsg);

    }
}
