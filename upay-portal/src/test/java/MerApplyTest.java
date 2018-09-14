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
public class MerApplyTest {
		
    @Resource(name = "SI_MER0001")
    IDipperHandler<Message> SI_MER0001;	//特约商户申请


    @org.junit.Test
    public void testDemo() throws Exception {     
        Map<String, Object> bodyMap1 = new HashMap<String, Object>();
        bodyMap1.put("userId", "UR000000000079");
        bodyMap1.put("merName", "商户portal测试");
        bodyMap1.put("merWithoutPwdSign", "1");
        bodyMap1.put("payopenflag", "0");
        bodyMap1.put("merBusiType", "03");
        bodyMap1.put("contact", "测试啊");
        bodyMap1.put("contactTel", "02102102100");
        bodyMap1.put("contactMobile", "15112345678");
        bodyMap1.put("contactEmail", "email@123.com");
        bodyMap1.put("merTel", "02800000000");
        bodyMap1.put("merFax", "123456");
        bodyMap1.put("merAddr", "portal测试地址");
        bodyMap1.put("merPostalCode", "000000");
        bodyMap1.put("websiteCode", "123321");
        bodyMap1.put("websiteName", "网站名称");
        bodyMap1.put("websiteDomain", "www.aaa.com");
        bodyMap1.put("websiteScop", "各种");
        bodyMap1.put("companyName", "公司名称");
        bodyMap1.put("egalPersonName", "测试");
        bodyMap1.put("egalPersonIdType", "02");
        bodyMap1.put("egalPersonIdNo", "123456");
        bodyMap1.put("companyIdType", "02");
        bodyMap1.put("companyIdNo", "654321");
        bodyMap1.put("orgDeptNo", "02");
        bodyMap1.put("busiLicenseId", "987654");
        bodyMap1.put("transCode", "SI_MER0001");
        bodyMap1.put("chnlId", "02");
        
        Map<String, Object> headMap1 = new HashMap<String, Object>();

        Message message1 =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                		headMap1, bodyMap1), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap1), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        message1 = SI_MER0001.handle(message1);
        System.out.println("message1:" + message1.getTarget().getBodys());

    }

}
