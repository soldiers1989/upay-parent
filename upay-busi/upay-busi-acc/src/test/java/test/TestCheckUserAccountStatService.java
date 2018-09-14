package test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.CheckUserAccountStatDto;
import com.upay.busi.acc.service.impl.CheckUserAccountStatService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月9日 上午8:33:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestCheckUserAccountStatService {
    
    @Resource
    CheckUserAccountStatService check;
    
    @Test
    public void execute () throws Exception{
        CheckUserAccountStatDto dto=new CheckUserAccountStatDto();
        dto.setAccNo("20160809000000000099");
        dto.setTransAmt(new BigDecimal("110"));
        check.execute(dto, null);
        
    }
}
