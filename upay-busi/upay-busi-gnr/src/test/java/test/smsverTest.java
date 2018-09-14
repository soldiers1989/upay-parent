package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.impl.SimpleMessage;
import com.upay.busi.gnr.service.dto.SmsVerifyDto;
import com.upay.busi.gnr.service.dto.SrvPostProcessingDto;
import com.upay.busi.gnr.service.impl.SmsVerifyServiceImpl;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月23日 下午5:04:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class smsverTest {
    
    @Resource
    SmsVerifyServiceImpl sms;
    
    @Test
    public void execute() throws Exception{
        SmsVerifyDto dto=new SmsVerifyDto();
        dto.setMobile("13551324852");
        dto.setSmsCode("999999");
        sms.execute(dto, null);
    }
}
