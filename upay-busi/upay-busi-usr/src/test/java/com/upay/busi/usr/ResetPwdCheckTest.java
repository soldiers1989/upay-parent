package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.ResetPwdCheckDto;
import com.upay.busi.usr.service.impl.ResetPwdCheckService;


/**
 * 
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ResetPwdCheckTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ResetPwdCheckService resetPwdCheckService;


    @Test
    public void execute() throws Exception {
        ResetPwdCheckDto resetPwdCheckDto = new ResetPwdCheckDto();
        resetPwdCheckDto.setUserId("");
        resetPwdCheckService.execute(resetPwdCheckDto, null);
    }

}
