package com.upay.pay.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author  liudan
 * @version v1.0
 * @since  2017.12.01
 * @date 2017.12.01
 * 银联营销活动余额查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class Pay1008Test extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(Pay1008Test.class);
    @Resource(name = "SI_PAY1008")
    IDipperHandler<Message> SI_PAY1008;

    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("transCode", "SI_PAY1008");
        headMap.put("chnlId", "02");
        headMap.put("platform", "01");
        //headMap.put("userId", "UR000000000009");
        headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        //bodyMap.put("payPwd", "123456");
        bodyMap.put("transCode", "SI_PAY1008");
        bodyMap.put("merNo", "MER2017000119");
        bodyMap.put("secMerNo", "MER2017000119000001");
        bodyMap.put("outerOrderNo", "awefsdfdfer1234123f4");
        bodyMap.put("transAmt", new BigDecimal("1"));
        
        bodyMap.put("curr", "CNY");
        bodyMap.put("spbillCreateIp", "127.0.0.1");
        bodyMap.put("outerOrderStartDate", "20180103000000");
        bodyMap.put("outerOrderEndDate", "20180103235858");

        bodyMap.put("notifyUrl", "http://www.baidu.com");
        bodyMap.put("returnUrl", "http://www.baidu.com?");
        bodyMap.put("payServicType", "0002");
        
        bodyMap.put("platformUserNo", "13441414141");//userId
        bodyMap.put("userName", "0002");
        bodyMap.put("certName", "0002");
        bodyMap.put("certType", "01");
        bodyMap.put("certNo", "0002");
        bodyMap.put("sex", "1");
        bodyMap.put("mobile", "12425636526");
        bodyMap.put("email", "124256365236@126.com");
        bodyMap.put("chnlId", "02");
        bodyMap.put("sysTime", new Date());
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        headMap, bodyMap), MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        SI_PAY1008.handle(message);
    }
}
