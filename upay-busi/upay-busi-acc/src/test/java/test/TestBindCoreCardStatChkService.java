package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.busi.acc.service.dto.BindCoreCardStatChkDto;
import com.upay.busi.acc.service.impl.AccOpenStatusService;
import com.upay.busi.acc.service.impl.BindCoreCardStatChkService;
import com.upay.dao.po.usr.UsrBaseInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestBindCoreCardStatChkService {

    @Resource
    BindCoreCardStatChkService acc;
    
    @Test
    public void execute() throws Exception{
    	BindCoreCardStatChkDto dto=new BindCoreCardStatChkDto();
    	dto.setCardStat("00000000000000000000");
    	dto.setBindAcctType("11");
    	BindCoreCardStatChkDto dd=acc.execute(dto, null);
//        
        System.out.println(dd);
//        System.out.println(dao.selectList(new UsrBaseInfoPo()).size());
    }
}
