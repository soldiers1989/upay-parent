/**
 * 
 */
package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.CheckUsrOptControlDto;
import com.upay.busi.usr.service.impl.CheckUsrOptControlService;

/**
 * @author shang
 * 2016年11月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestCheckUsrOptControl {
    
    @Resource
    CheckUsrOptControlService c;
    
    @Test
    public void execute() throws Exception{
        CheckUsrOptControlDto dto =new CheckUsrOptControlDto();
        dto.setUserId("UR000000000172");
        dto.setChnlId("02");
        dto.setPayType("12");
        dto.setTransCode("SI_PAY0009");
        
        dto=c.execute(dto, null);
        System.out.println(dto.getRouteCode());
    }
}
