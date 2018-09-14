package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.UpdateLastLoginTimeDto;
import com.upay.busi.usr.service.impl.UpdateLastLoginTimeService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午9:00:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUpdateLastLoginTime {
    
    @Resource
    UpdateLastLoginTimeService update;
    
    @Test
    public void execute() throws Exception{
        UpdateLastLoginTimeDto dto=new UpdateLastLoginTimeDto();
        dto.setUserId("UR000000000003");
        UpdateLastLoginTimeDto dd=update.execute(dto, null);
        
        System.out.println(dd);
        
    }

}
