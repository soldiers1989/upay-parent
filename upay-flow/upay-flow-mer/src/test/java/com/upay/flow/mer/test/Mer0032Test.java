package com.upay.flow.mer.test;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**

 * 
 * @author liudan
 * 
 */




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class Mer0032Test extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MER0028Test.class);


    @Resource(name = "SI_MER0032")
    private IDipperHandler<Message> SI_MER0032;// 开通微信支付

    @Test
    public void testSI_MER0032() throws Exception {




     /*   MerQueryListDto merQueryListDto=new MerQueryListDto();
        merQueryListDto.setContactEmail("1016699019@qq.com");
//		merQueryListDto.setContactMobile("");
        SI_MER0031.execute(merQueryListDto, null);*/

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("contactMobile", "18601551365");
//        body.put("contactEmail", "1016699019@qq.com");
        Message rspMsg = (Message) SI_MER0032.handle(getMessage(body, "SI_MER0032"));
        logger.debug("{}", rspMsg);

    }
}
