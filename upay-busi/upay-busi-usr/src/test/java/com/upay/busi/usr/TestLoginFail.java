package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.LoginFailDto;
import com.upay.busi.usr.service.impl.LoginFailService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午9:15:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestLoginFail {
    
    @Resource
    LoginFailService fail;
    
    @Test
    public void execute() throws Exception{
        LoginFailDto dto=new LoginFailDto();
        dto.setUserId("UR000000000003");
        dto.setChnlId("01");
        dto.setSessionId("3c310b50ada");
        dto.setLoginMode("1");
        dto.setIsMutiplueLogin("1");
        dto.setAddrGetFlag("1");
        LoginFailDto dd=fail.execute(dto, null);
        
        System.out.println(dd);
        
        
    }

}
