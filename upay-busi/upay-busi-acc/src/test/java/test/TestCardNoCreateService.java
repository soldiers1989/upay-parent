package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.busi.acc.service.dto.CardNoCreateDto;
import com.upay.busi.acc.service.dto.UniqueBindAccCheckDto;
import com.upay.busi.acc.service.impl.AccOpenStatusService;
import com.upay.busi.acc.service.impl.CardNoCreateService;
import com.upay.busi.acc.service.impl.CheckUserAccountStatService;
import com.upay.busi.acc.service.impl.UniqueBindAccCheckService;
import com.upay.dao.po.usr.UsrBaseInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestCardNoCreateService {

	@Resource
	CardNoCreateService cardNoCreateService;
    
    @Test
    public void execute() throws Exception{
    	CardNoCreateDto dto=new CardNoCreateDto();
		 dto = cardNoCreateService.execute(dto,null);
		 System.out.println(dto.geteCardNo());
    }
}
