package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccInfoQueryDto;
import com.upay.busi.acc.service.impl.AccInfoQueryService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccInfoQueryTest {

    @Resource
    private AccInfoQueryService accInfoQueryService;


    @Test
    public void execute() throws Exception {
        AccInfoQueryDto accInfoQueryDto = new AccInfoQueryDto();
        accInfoQueryDto.setvAcctNo("66140040100000000436");
        accInfoQueryDto.setUserId("UR000000000079");
        accInfoQueryService.execute(accInfoQueryDto, null);

    }
}
