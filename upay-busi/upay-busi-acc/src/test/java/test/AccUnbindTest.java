package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccUnbindDto;
import com.upay.busi.acc.service.impl.AccUnbindService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccUnbindTest {

    @Resource
    private AccUnbindService accUnbindService;


    @Test
    public void execute() throws Exception {
        AccUnbindDto accUnbindDto = new AccUnbindDto();
        accUnbindDto.setvAcctNo("20160809000000000099");
        accUnbindDto.setBindAcctType("01");
        accUnbindService.execute(accUnbindDto, null);

    }
}
