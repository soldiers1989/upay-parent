package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.usr.service.dto.UnlockTradePwdDto;
import com.upay.busi.usr.service.impl.UnlockTradePwdService;


/**
 * 交易密码解锁
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class UnlockTradePwdTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UnlockTradePwdService unlockTradePwdService;
    @Resource
    IDipperCached idipperCached;


    @Test
    public void execute() throws Exception {
        UnlockTradePwdDto unlockTradePwdDto = new UnlockTradePwdDto();
        unlockTradePwdDto.setUserId("UR000000000080");
        unlockTradePwdService.execute(unlockTradePwdDto, null);
    }
}
