package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.usr.service.dto.PwdChgDto;
import com.upay.busi.usr.service.impl.PwdChgService;
import com.upay.commons.constants.DataBaseConstants_USR;


/**
 * 查询用户是否设置支付密码
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PwdChgTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private PwdChgService pwdChgService;
    @Resource
    IDipperCached idipperCached;


    @Test
    public void execute() throws Exception {
        PwdChgDto pwdChgDto = new PwdChgDto();
        pwdChgDto.setUserId("UR000000000001");
        pwdChgDto.setLoginPwd("1234567");
        pwdChgDto.setNewLoginPwd("1234567");
        pwdChgDto.setPwdFlag(DataBaseConstants_USR.PWD_FLAG_LOGIN_PWD);
        pwdChgDto.setChnlId("01");
        pwdChgService.execute(pwdChgDto, null);
        logger.debug("--------------test end");

    }
}
