package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.CheckBindCardStatDto;
import com.upay.busi.acc.service.impl.CheckBindCardStatService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月9日 下午2:03:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestCheckBindCardStatService {
    @Resource
    CheckBindCardStatService ch;
    
    @Test
    public void execute() throws Exception{
        CheckBindCardStatDto dto=new CheckBindCardStatDto();
        dto.setAccNo("66140040100000000515");
        dto.setBankAccNo("6231351234598741");
        ch.execute(dto, null);
    }
}
