package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.BindChgCheckDto;
import com.upay.busi.acc.service.impl.BindChgCheckService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class BindChgCheckTest {

    @Resource
    private BindChgCheckService bindChgCheckService;


    @Test
    public void execute() throws Exception {
        BindChgCheckDto bindChgCheckDto = new BindChgCheckDto();
        bindChgCheckDto.setvAcctNo("66140040100000000178");
        bindChgCheckService.execute(bindChgCheckDto, null);

    }
}
