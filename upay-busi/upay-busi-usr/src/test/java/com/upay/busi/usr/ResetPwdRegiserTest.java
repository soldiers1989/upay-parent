package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.ResetPwdRegiserDto;
import com.upay.busi.usr.service.impl.ResetPwdRegiserService;


/**
 * 
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ResetPwdRegiserTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ResetPwdRegiserService resetPwdRegiserService;


    @Test
    public void execute() throws Exception {
        ResetPwdRegiserDto resetPwdRegiserDto = new ResetPwdRegiserDto();
        resetPwdRegiserDto.setUserId("");
        resetPwdRegiserService.execute(resetPwdRegiserDto, null);
    }

}
