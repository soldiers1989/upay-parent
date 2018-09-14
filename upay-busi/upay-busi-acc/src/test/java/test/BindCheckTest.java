package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.BindCheckDto;
import com.upay.busi.acc.service.impl.BindCheckService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class BindCheckTest {

    @Resource
    private BindCheckService bindCheckService;


    @Test
    public void execute() throws Exception {
        BindCheckDto bindCheckDto = new BindCheckDto();
        bindCheckDto.setMobile("13821313362");
        bindCheckDto.setvBindAcctNo("6231351234512343");

        bindCheckService.execute(bindCheckDto, null);

    }
}
