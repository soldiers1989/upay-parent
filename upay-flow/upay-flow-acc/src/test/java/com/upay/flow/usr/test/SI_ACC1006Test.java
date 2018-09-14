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
public class SI_ACC1006Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(SI_ACC1006Test.class);

    @Resource(name = "SI_ACC1006")
    private IDipperHandler<Message> SI_ACC1006;// 解绑


    @org.junit.Test
    public void testSI_USR0006() throws Exception {
        Map<String, Object> bodys = new HashMap<String, Object>();

//        bodys.put("chnlId", "01");
//        bodys.put("sysTime", new Date());
//        bodys.put("transCode", "SI_ACC1006");
//        bodys.put("sysSeq", "2145436547712834");
//        bodys.put("merNo", "MER2017000144");
//        bodys.put("transAmt", new BigDecimal(1));//转账金额
//        bodys.put("payeeAccNo", "6229807711600003194");//收款账号  商户银行卡号
//        bodys.put("payeeName", "测试收款");//收款人姓名
//		bodys.put("stlAcctType", "01");//转账方式
//		bodys.put("payerAccNo", "6229807711500096801");//付款账户口     核心待清算账户   卡号     内部户
//		bodys.put("payerName", "测试付款");//付款人姓名
        
        bodys.put("chnlId", "01");
        bodys.put("sysTime", new Date());
        bodys.put("transCode", "SI_ACC1006");
        bodys.put("sysSeq", "2145436547716455");
//        bodys.put("merNo", "MER2017000144");
        bodys.put("transAmt", new BigDecimal(1));//转账金额
        bodys.put("payeeAccNo", "6229807711500381179");//收款账号  商户银行卡号
        bodys.put("payeeName", "测试收款");//收款人姓名
		bodys.put("stlAcctType", "11");//转账方式
		bodys.put("payerAccNo", "6229807711600003194");//付款账户口     核心待清算账户   卡号     内部户
		bodys.put("payerName", "测试付款");//付款人姓名

//		bodys.put("chnlId", "01");
//        bodys.put("sysTime", new Date());
//        bodys.put("transCode", "SI_ACC1006");
//        bodys.put("sysSeq", "2145436547716836");
////	    bodys.put("merNo", "MER2017000144");
//        bodys.put("transAmt", new BigDecimal("0.99"));//转账金额
//        bodys.put("payeeAccNo", "6229807711500381179");//收款账号  商户银行卡号
//        bodys.put("payeeName", "测试收款");//收款人姓名
//		bodys.put("stlAcctType", "21");//转账方式
//		bodys.put("payerAccNo", "1010001300119000002");//付款账户口     核心待清算账户   卡号     内部户
//		bodys.put("payerName", "测试付款");//付款人姓名
        Message rspMsg = (Message) SI_ACC1006.handle(getMessage(bodys, "SI_ACC1006"));

        if (!ResponseCode.SUCCESS.equals(rspMsg.getFault().getCode()))
            logger.debug("{}", rspMsg);

    }
}
