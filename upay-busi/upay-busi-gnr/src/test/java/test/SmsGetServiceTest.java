package test;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.impl.SimpleMessage;
import com.pactera.dipper.core.utils.Constants;
import com.upay.busi.gnr.service.dto.SmsGetDto;
import com.upay.busi.gnr.service.dto.SmsVerifyDto;
import com.upay.busi.gnr.service.dto.SrvPostProcessingDto;
import com.upay.busi.gnr.service.impl.SmsGetServiceImpl;
import com.upay.busi.gnr.service.impl.SmsVerifyServiceImpl;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月23日 下午5:04:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SmsGetServiceTest {
    
    @Resource
    SmsGetServiceImpl sms;
    
    @Test
    public void execute() throws Exception{
    	SmsGetDto dto=new SmsGetDto();
        dto.setMobile("13551324852");
        dto.setTransCode("SI_GNR0003");
       // dto.setSmsMsg("$你的手机号是$");
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("mobile", "13551324852");
        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory
                    .createSimpleMessageInstance(), MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), map), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, ""));
		sms.execute(dto, m);
    }
}
