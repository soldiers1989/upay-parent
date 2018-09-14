package com.upay.busi.usr;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.DemoDto;
import com.upay.busi.usr.service.dto.UserRegDto;
import com.upay.busi.usr.service.impl.UserRegInfoService;
import com.upay.dao.SysInfoContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestUserReg {

    private Logger logger = LoggerFactory.getLogger(TestUserReg.class);

    @Resource
    UserRegInfoService reg;
    
    @Test
    public void execute() throws Exception {
        UserRegDto dto=new UserRegDto();
//        dto.setRegType("01");
//        dto.setUserName("shang");
//        dto.setName("尚");
//        dto.setCertType("01");
//        dto.setCertNo("33333333333333");
//        dto.setSex("1");
        dto.setMobile("16600000003");
        dto.setMerNo("MER2016000117");
        dto.setPlatformType("01");
        dto.setChnlId("01");
        dto.setPlatformUserNo("shanghuceshiyonghu4");
        UserRegDto dt=reg.execute(dto, null);
        System.out.println(dt);
    }
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        
//        System.out.println(SysInfoContext.getSysDate());
        
        
        Pattern pat=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 
        Matcher mat=pat.matcher("");
        System.out.println(mat.matches());
    }

}
