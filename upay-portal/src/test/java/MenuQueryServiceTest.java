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
public class MenuQueryServiceTest {

    @Resource(name = "SI_GNR0008")
    IDipperHandler<Message> SI_GNR0008; // 菜单查询


    @org.junit.Test
    public void testDemo() throws Exception {
        Map<String, Object> bodyMap1 = new HashMap<String, Object>();
        bodyMap1.put("transCode", "SI_GNR0008");
        bodyMap1.put("chnlId", "02");
        bodyMap1.put("userCertLevel", "03");

        Map<String, Object> headMap1 = new HashMap<String, Object>();

        Message message1 =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap1, bodyMap1), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap1), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message1 = SI_GNR0008.handle(message1);
        System.out.println("message1:" + message1.getTarget().getBodys());

    }

}
