package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.UserPayPwdFlagSearchDto;
import com.upay.busi.usr.service.impl.UserPayPwdFlagSearchService;


/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午2:21:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUserPayPwdFlagSearchService {

    @Resource
    UserPayPwdFlagSearchService userPayPwdFlagSearchService;
    
    @Test
    public void execute() throws Exception{
    	UserPayPwdFlagSearchDto dto=new UserPayPwdFlagSearchDto();
        dto.setUserId("UR000001733323");
        dto.setOrderNo("UPAY201801120009453675");
        dto.setChnlId("02");
        dto.setTransCode("SI_USR0002");
        userPayPwdFlagSearchService.execute(dto, null);
    }
}
