package com.upay.busi.usr;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.SafeLogoffDto;
import com.upay.busi.usr.service.impl.SafeLogoffService;


/**
 * 安全退出
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SafeLogoffTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private SafeLogoffService safeLogoffService;


    @Test
    public void execute() throws Exception {
        SafeLogoffDto dto = new SafeLogoffDto();
        dto.setSessionId("225ea8f75b5049438ba8957838e8ab3e");
        dto.setSysTime(new Date());
        logger.debug(dto.getSysDate() + "");
        safeLogoffService.execute(dto, null);
    }
}
