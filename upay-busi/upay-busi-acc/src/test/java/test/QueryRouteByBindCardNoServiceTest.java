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

import com.upay.busi.acc.service.dto.QueryRouteByBindCardNoDto;
import com.upay.busi.acc.service.impl.QueryRouteByBindCardNoService;

/**
 * @author lb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class QueryRouteByBindCardNoServiceTest {
	@Resource
	QueryRouteByBindCardNoService bind;
    
    @Test
    public void execute() throws Exception{
    	QueryRouteByBindCardNoDto dto=new QueryRouteByBindCardNoDto();
    	dto.setVbindAcctNo("6231351234512322");
    	dto.setCardBin("623135");
    	dto.setSysTime(new Date());
    	dto.setSysDate(new Date());
    	dto.setCertName("刘兵");
//    	dto.setTransAmtStr("1.03");
    	dto.setTransCode("SI_ACC8001");
    	dto=bind.execute(dto, null);
        
        System.out.println(dto);
        
    }
}
