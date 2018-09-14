package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.MailSignDto;
import com.upay.busi.usr.service.dto.UserRegQueryDto;
import com.upay.busi.usr.service.impl.MailSignService;
import com.upay.busi.usr.service.impl.UserRegInfoService;
import com.upay.busi.usr.service.impl.UserRegQueryService;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午2:21:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestSignMail {

    @Resource
    MailSignService reg;
    
    @Test
    public void execute() throws Exception{
    	MailSignDto dto=new MailSignDto();
        dto.setUserId("UR000000000003");
        reg.execute(dto, null);
        
//        System.out.println(dd);
    }
}
