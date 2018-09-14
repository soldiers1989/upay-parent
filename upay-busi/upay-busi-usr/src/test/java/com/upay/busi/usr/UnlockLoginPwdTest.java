package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.usr.service.dto.UnlockLogPwdDto;
import com.upay.busi.usr.service.impl.UnlockLogPwdService;


/**
 * 交易密码解锁
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class UnlockLoginPwdTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UnlockLogPwdService unlockLogPwdService;
    @Resource
    IDipperCached idipperCached;


    @Test
    public void execute() throws Exception {
        UnlockLogPwdDto unlockLogPwdDto = new UnlockLogPwdDto();
        unlockLogPwdDto.setUserId("UR000000000084");
        unlockLogPwdService.execute(unlockLogPwdDto, null);
    }
}
