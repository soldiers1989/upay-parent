package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.UserRegQueryDto;
import com.upay.busi.usr.service.impl.UserRegInfoService;
import com.upay.busi.usr.service.impl.UserRegQueryService;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午2:21:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUserRegQuery {

    @Resource
    UserRegQueryService reg;
    
    @Test
    public void execute() throws Exception{
        UserRegQueryDto dto=new UserRegQueryDto();
        dto.setUserId("UR000000000003");
        UserRegQueryDto dd= reg.execute(dto, null);
        
        System.out.println(dd);
    }
}
