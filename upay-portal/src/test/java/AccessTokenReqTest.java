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
public class AccessTokenReqTest {
    @Resource(name = "SI_PAY0006")
    IDipperHandler<Message> SI_PAY0006;


    @org.junit.Test
    public void testDemo() throws Exception {
        // SI_DEMO001
         Map<String, String> bodyMap = new HashMap<String, String>();
         bodyMap.put("appId", "wx3950cc559a1244f4");
         bodyMap.put("appSecret", "c3c0b9011961fa81b669f7e9fa4114a5");
        // bodyMap.put("code", "001kstbf0df6jF1RKMaf0Opsbf0kstba");
        // bodyMap.put("grantType", "authorization_code");
        // bodyMap.put("searchFlag", "1");
      /*  Map<String, String> bodyMap = new HashMap<String, String>();
        bodyMap.put("name", "李四");
        bodyMap.put("password", "121212121");*/
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("sessionId", "123456789");
        headMap.put("trans_code", "SI_PAY0006");
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_PAY0006.handle(message);
        System.out.println("message:" + message.getTarget().getBodys());

    }

}
