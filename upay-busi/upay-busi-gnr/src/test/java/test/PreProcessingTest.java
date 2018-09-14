package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.context.DipperSysInfo;
import com.pactera.dipper.redis.client.DipperRedisSentinelConfiguration;
import com.upay.busi.gnr.service.dto.SrvPreProcessingDto;
import com.upay.busi.gnr.service.impl.SrvPreProcessingServiceImpl;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月23日 上午11:16:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PreProcessingTest {

    @Autowired
    SrvPreProcessingServiceImpl pre;
    
    @Test
    public void execute() throws Exception{
        SrvPreProcessingDto dto=new SrvPreProcessingDto();
        dto.setUserId("UR000000000002");
        dto.setChnlId("02");
        dto.setTransCode("SI_USR0001");
        SrvPreProcessingDto dd= pre.execute(dto, null);
        DipperRedisSentinelConfiguration s=null;
        DipperSysInfo ds=null;
        System.out.println(dd);
    }
    
    
}
