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
 * @author hry
 * @version v1.0
 * @date 2017.12.01
 * 申请二维码（主扫）
 * @since 2017.12.01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")
public class Pay3006Test extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(Pay3006Test.class);
    @Resource(name = "SI_PAY3006")
    IDipperHandler<Message> SI_PAY3006;

    @org.junit.Test
    public void test() throws Exception {
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("transCode", "SI_PAY3006");
        bodyMap.put("merNo", "MER2017000119");
        bodyMap.put("id", "777290058135880");
        bodyMap.put("primaryMerName", "商户名称");
        bodyMap.put("chnlId", "02");
        bodyMap.put("transAmtStr", "1000");
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        null, bodyMap), MessageFactory.createSimpleMessage(
                        new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                        Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        SI_PAY3006.handle(message);
    }
}
