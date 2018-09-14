package com.upay.pay.test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

/**
 * @author hry
 * @version v1.0
 * @date 2017.12.01
 * 申请二维码（主扫）
 * @since 2017.12.01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class Pay3008Test extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(Pay3008Test.class);
    @Resource(name = "SI_PAY3008")
    IDipperHandler<Message> SI_PAY3008;

    @org.junit.Test
    public void test() throws Exception {
       /* Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("transCode", "SI_PAY3006");
        headMap.put("chnlId", "02");
        headMap.put("platform", "01");*/
        //  headMap.put("userId", "UR000000000009");
      //  headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
        Map<String, Object> bodyMap = new HashMap<String, Object>();

       /* bodyMap.put("transCode", "SI_PAY3006");
        bodyMap.put("chnlId", "02");*/
       // bodyMap.put("platform", "01");


       // bodyMap.put("payPwd", "123456");
        bodyMap.put("transCode", "SI_PAY3008");
        //bodyMap.put("accNo", "20160809000000000099");
        //bodyMap.put("bankAccNo", "6229807711600003194");
        bodyMap.put("merNo", "MER2017000211");
//        bodyMap.put("name", "商户名称");
       
        bodyMap.put("limitCount","0");
        bodyMap.put("qrValidTime", BigInteger.valueOf(10*365*24*60*60).toString());

        bodyMap.put("chnlId", "02");
        
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        null, bodyMap), MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        SI_PAY3008.handle(message);
    }
}
