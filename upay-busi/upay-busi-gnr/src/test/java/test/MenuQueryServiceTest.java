package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.gnr.service.dto.MenuQueryDto;
import com.upay.busi.gnr.service.impl.MenuQueryServiceImpl;


/**
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MenuQueryServiceTest {
    @Resource
    private MenuQueryServiceImpl menuQueryServiceImpl;


    @Test
    public void execute() throws Exception {
        MenuQueryDto dto = new MenuQueryDto();
        dto.setUserCertLevel("03");
        dto.setUserId("UR000000000084");
        menuQueryServiceImpl.execute(dto, null);
    }
}
