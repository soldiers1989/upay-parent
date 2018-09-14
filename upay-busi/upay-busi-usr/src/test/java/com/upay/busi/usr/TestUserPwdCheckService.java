package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.UserPwdCheckDto;
import com.upay.busi.usr.service.dto.UserRegQueryDto;
import com.upay.busi.usr.service.impl.UserPwdCheckServiceImpl;
import com.upay.busi.usr.service.impl.UserRegInfoService;
import com.upay.busi.usr.service.impl.UserRegQueryService;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午2:21:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUserPwdCheckService {

    @Resource
    UserPwdCheckServiceImpl userPwdCheckServiceImpl;
    
    @Test
    public void execute() throws Exception{
    	UserPwdCheckDto dto=new UserPwdCheckDto();
        dto.setUserId("UR000000000314");
        dto.setRepeatCheckFlag("01");
        dto.setLoginPwd("safdf");
        dto.setNewLoginPwd("safdf");
        UserPwdCheckDto dd= userPwdCheckServiceImpl.execute(dto, null);
        
        System.out.println(dd);
    }
}
