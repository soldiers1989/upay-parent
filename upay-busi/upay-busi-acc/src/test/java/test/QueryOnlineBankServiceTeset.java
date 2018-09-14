/**
 * 
 */
package test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.QueryOnlineBankDto;
import com.upay.busi.acc.service.dto.QueryRouteByBindCardNoDto;
import com.upay.busi.acc.service.impl.QueryOnlineBankService;
import com.upay.busi.acc.service.impl.QueryRouteByBindCardNoService;

/**
 * @author lb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class QueryOnlineBankServiceTeset {
	@Resource
	QueryOnlineBankService bind;
    
    @Test
    public void execute() throws Exception{
    	QueryOnlineBankDto dto=new QueryOnlineBankDto();
    	dto.setQueryFlag("1");
    	dto=bind.execute(dto, null);
        
        System.out.println(dto);
        
    }
}
