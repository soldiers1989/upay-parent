package com.upay.busi.usr;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.CertUniqueCheckDto;
import com.upay.busi.usr.service.dto.UserBaseInfoDto;
import com.upay.busi.usr.service.dto.WeakAuthDto;
import com.upay.busi.usr.service.impl.CertUniqueCheckService;
import com.upay.busi.usr.service.impl.UserBaseInfoService;
import com.upay.busi.usr.service.impl.WeakAuthService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午8:50:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestWeakAuthService {
    
    
    @Resource
    WeakAuthService weakAuthService;
    
    
    @Test
    public void execute() throws Exception{
       
    	WeakAuthDto dto=new WeakAuthDto();
    	dto.setUserId("");
    	dto.setChnlId("01");
    	dto.setCertName("刘兵");
		dto.setCertType("01");
    	dto.setCertNo("510623188608108436");
    	dto.setCertWeakWay("02");
        dto = weakAuthService.execute(dto, null);
        System.out.println(dto.getCertNo());
    }
}
