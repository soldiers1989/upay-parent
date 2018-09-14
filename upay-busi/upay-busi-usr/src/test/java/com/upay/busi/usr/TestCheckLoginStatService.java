/**
 * 
 */
package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.usr.service.dto.CheckLoginStatDto;
import com.upay.busi.usr.service.impl.CheckLoginStatService;

/**
 * @author shang
 * 2016年10月8日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestCheckLoginStatService {

    @Resource
    CheckLoginStatService check;
    @Resource
    IDipperCached ca;
    
    @Test
    public void execute() throws Exception{
        CheckLoginStatDto dto=new CheckLoginStatDto();
        dto.setUserId("UR000000000301");
        dto.setSessionId("5e3f470359fb40f49103267e965d9476");
        dto.setChnlId("02");
        check.execute(dto, null);
    }
    
}
