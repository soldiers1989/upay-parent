package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.UserBaseInfoDto;
import com.upay.busi.usr.service.impl.UserBaseInfoService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午8:50:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUserBaseInfo {
    
    
    @Resource
    UserBaseInfoService base;
    
    
    @Test
    public void execute() throws Exception{
        UserBaseInfoDto dto=new UserBaseInfoDto();
        dto.setUserId("UR000000000003");
        UserBaseInfoDto dd=base.execute(dto, null);
        System.out.println(dd);
    }
}
