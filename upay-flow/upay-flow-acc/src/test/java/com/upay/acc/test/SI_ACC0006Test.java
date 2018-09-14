package com.upay.acc.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SI_ACC0006Test  extends BaseTest {

    @Resource(name = "SI_ACC0006")
    private IDipperHandler<Message> SI_ACC0006;//

    @org.junit.Test
    public void testDemo() throws Exception {
        // SI_DEMO001
        Map<String, String> bodyMap = new HashMap<String, String>();
        bodyMap.put("userId", "UR000000000079");

       //bodyMap.put("queryFlag", "1");
       bodyMap.put("chnlId", "01");
        //bodyMap.put("transCode", "SI_PAY0012");
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("sessionId", "123456789");
        headMap.put("transCode", "01");
//      headMap.put("currentNum", 10);
//       headMap.put("pageIndex", 1);
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_ACC0006.handle(message);
        System.out.println("message:" + message.getTarget().getBodys());
        System.out.println("XXX" + message.getFault().getCode());
        System.out.println("XXX2" + message.getFault().getMsg());

    }
    
}
