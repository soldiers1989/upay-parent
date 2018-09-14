import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

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
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class WeiXinOrderTest {
    @Resource(name = "SI_PAY2006")
    IDipperHandler<Message> SI_PAY2006;


    @org.junit.Test
    public void testDemo() throws Exception {
        // SI_DEMO001
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("transCode", "SI_PAY3002");
        
        bodyMap.put("merNo", "13968111");
        bodyMap.put("openId", "o4GgauI1BkJE7EGQSADJL3hJ8jq8");
        bodyMap.put("orderName", "红塔银行1999");
        bodyMap.put("orderDes", "红塔山玉溪烟11111");
        bodyMap.put("transAmt", new BigDecimal(1));
        bodyMap.put("body", "红塔山玉溪烟11111");
        bodyMap.put("chnlId", "03");
        bodyMap.put("spbillCreateIp", "192.168.1.99");
        //bodyMap.put("transComments", "微信公众号订单");
        bodyMap.put("orderLmtTime", 0);
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("sessionId", "123456789");
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                    headMap, bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message = SI_PAY2006.handle(message);
        System.out.println("message:" + message.getTarget().getBodys());

    }
    
   

}
