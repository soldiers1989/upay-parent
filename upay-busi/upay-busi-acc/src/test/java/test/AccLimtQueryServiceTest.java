package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccInfoQueryDto;
import com.upay.busi.acc.service.dto.AccLimitQueryDto;
import com.upay.busi.acc.service.impl.AccInfoQueryService;
import com.upay.busi.acc.service.impl.AccLimitQueryService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccLimtQueryServiceTest {

    @Resource
    private AccLimitQueryService accInfoQueryService;


    @Test
    public void execute() throws Exception {
    	AccLimitQueryDto dto = new AccLimitQueryDto();
    	dto.setTransCode("SI_PAY0009");
    	dto.setChnlId("02");
    	dto.setUserCertLevel("03");
    	
        AccLimitQueryDto execute = accInfoQueryService.execute(dto, null);
        System.out.println(execute);
    }
}
