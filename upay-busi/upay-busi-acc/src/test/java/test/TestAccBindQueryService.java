package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccBindQueryDto;
import com.upay.busi.acc.service.impl.AccBindQueryService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:24:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestAccBindQueryService {
    
    @Resource
    AccBindQueryService bind;
    
    @Test
    public void execute() throws Exception{
        AccBindQueryDto dto=new AccBindQueryDto();
        dto.setUserId("UR000000000339");
        dto.setChnlId("02");
        AccBindQueryDto dt=bind.execute(dto, null);
        
        System.out.println(dt);
        
    }
}
