package test;

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


/**
 * Created by dongweizhao on 16/7/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class SmsTest {
    @Resource(name = "SI_GNR0003")
    IDipperHandler<Message> SI_DEMO001;


    @org.junit.Test
    public void testDemo() throws Exception {
        // SI_DEMO001
        Map<String, String> bodyMap = new HashMap<String, String>();
        bodyMap.put("mobile", "18721755540");
        bodyMap.put("transCode", "SI_GNR0003");
        bodyMap.put("chnlId", "02");
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("sessionId", "123456789");
        headMap.put("chnlId", "02");
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_DEMO001.handle(message);
        System.out.println("message:" + message.getTarget().getBodys());
        System.out.println("XXX" + message.getFault().getCode());
        System.out.println("XXX2" + message.getFault().getMsg());

    }

}
