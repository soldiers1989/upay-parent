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
public class USR0023Test {
    @Resource(name = "SI_USR0023")
    IDipperHandler<Message> SI_USR0023;


    @org.junit.Test
    public void testDemo() throws Exception {
        // SI_DEMO001
        Map<String, String> bodyMap = new HashMap<String, String>();

        bodyMap.put("mailTo", "569598269@qq.com");

        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("chnlId", "01");
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(),
                    MessageFactory.createSimpleMessage(headMap, bodyMap),
                    com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(headMap, bodyMap),
                    FaultFactory.create(Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_USR0023.handle(message);
        System.out.println("message:" + message.getTarget().getBodys());

    }

}
