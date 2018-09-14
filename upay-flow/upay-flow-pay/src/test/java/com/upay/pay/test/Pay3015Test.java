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
public class Pay3015Test extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(Pay3015Test.class);
    @Resource(name = "SI_PAY3015")
    IDipperHandler<Message> SI_PAY3015;

    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("transCode", "SI_PAY3015");
        headMap.put("chnlId", "02");
        headMap.put("platform", "01");
        //headMap.put("userId", "UR000000000009");
        headMap.put("sessionId", "5e3f470359fb40f49103267e965d9475");
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        //bodyMap.put("payPwd", "123456");
        bodyMap.put("transCode", "SI_PAY3015");
        //bodyMap.put("accNo", "20160809000000000099");
        //bodyMap.put("bankAccNo", "6229807711600003194");
        bodyMap.put("merNo", "MER2017000119");
        // bodyMap.put("orderNo", "UPAY201610270000000573");
        bodyMap.put("userId", "UR000000000068");
        bodyMap.put("chnlId", "02");
        bodyMap.put("productId", "02");

        bodyMap.put("spbillCreateIp", "127.0.0.1");
        bodyMap.put("authCode", "130359885851970954");


        // --商户订单号
        String orderId ="";
        // TODO:营销活动余额查询  待测试
        bodyMap.put("orderId", orderId);
        bodyMap.put("discountId","xxxxxxxxx");    //营销活动编号,请根据实际上送为准
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        headMap, bodyMap), MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        SI_PAY3015.handle(message);
    }
}
