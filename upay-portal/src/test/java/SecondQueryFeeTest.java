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
public class SecondQueryFeeTest {
	
/*	@Resource(name = "SI_USR0006")
	IDipperHandler<Message> SI_USR0006; // 登录
*/	
    @Resource(name = "SI_MER0008")
    IDipperHandler<Message> SI_MER0008;	//一级商户查询二级商户


    @org.junit.Test
    public void testDemo() throws Exception {
       /* Map<String, String> bodyMap = new HashMap<String, String>();
        bodyMap.put("mobile", "13444444444");
        bodyMap.put("loginPwd", "123321");
        bodyMap.put("transCode", "SI_USR0006");
        bodyMap.put("chnlId", "02");
        bodyMap.put("platform", "01");
        
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("transCode", "SI_USR0006");
        headMap.put("chnlId", "02");
        headMap.put("platform", "01");
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_USR0006.handle(message);
        System.out.println("message:" + message.getTarget().getBodys());
        	
        Map<String, Object> respBody = (Map<String, Object>) message.getTarget().getBodys();*/
        
        Map<String, String> bodyMap1 = new HashMap<String, String>();
        bodyMap1.put("transCode", "SI_MER0008");
        bodyMap1.put("secMerNo", "4");
        bodyMap1.put("userId", "UR000000000015");
        bodyMap1.put("chnlId", "02");
        //bodyMap1.put("sessionId", (String) respBody.get("sessionId"));
        
        Map<String, Object> headMap1 = new HashMap<String, Object>();
        
        Message message1 =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                		headMap1, bodyMap1), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap1), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message1 = SI_MER0008.handle(message1);
        System.out.println("message1:" + message1.getTarget().getBodys());

    }

}
