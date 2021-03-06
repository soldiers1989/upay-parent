package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.busi.acc.service.impl.AccOpenStatusService;
import com.upay.dao.po.usr.UsrBaseInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestAccOpenStatusService {

//    @Resource
//    AccOpenStatusService acc;
    @Resource(name="iDaoService")
    IDaoService dao;
    
    @Test
    public void execute() throws Exception{
//        AccOpenStatusDto dto=new AccOpenStatusDto();
//        dto.setUserId("UR000000000003");
//        AccOpenStatusDto dd=acc.execute(dto, null);
//        
//        System.out.println(dd);
        System.out.println(dao.selectList(new UsrBaseInfoPo()).size());
    }
}
