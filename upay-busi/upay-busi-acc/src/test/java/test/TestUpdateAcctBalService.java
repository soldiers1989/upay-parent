package test;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;




import com.upay.busi.acc.service.dto.UpdateAcctBalServiceDto;
import com.upay.busi.acc.service.impl.UpdateAcctBalService;
import com.upay.commons.util.DateUtil;


/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestUpdateAcctBalService {

	@Resource
	UpdateAcctBalService check;
    
    @Test
    public void execute() throws Exception{
    	UpdateAcctBalServiceDto dto=new UpdateAcctBalServiceDto();
    	dto.setTransCode("SI_ACC8003");
    	dto.setTransAmt(new BigDecimal("1"));
    	dto.setvAcctNo("66140040100000000613");
    	dto.setSysDate(DateUtil.parse("2016-09-01", "yyyy-MM-dd"));
    	check.execute(dto,null);
    }
}
