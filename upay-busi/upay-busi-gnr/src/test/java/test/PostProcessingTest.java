package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.impl.SimpleMessage;
import com.upay.busi.gnr.service.dto.SrvPostProcessingDto;
import com.upay.busi.gnr.service.impl.SrvPostProcessingServiceImpl;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月23日 下午5:04:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PostProcessingTest {
    
    @Resource
    SrvPostProcessingServiceImpl post;
    
    @Test
    public void execute() throws Exception{
        SrvPostProcessingDto dto=new SrvPostProcessingDto();
        dto.setSysSeq("YM1607230000000008");
        dto.setUserId("UR000000000002");
        dto.setChnlId("02");
        dto.setSessionId("3c310b50adad480ca2859c1f1ad5f021");
        SrvPostProcessingDto dd=post.execute(dto, null);
        
        System.out.println(dd);
    }
}
