package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.MailSignDto;
import com.upay.busi.usr.service.impl.MailSignService;


/**
 * 个人信息查询
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MailSignTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private MailSignService mailSignService;


    @Test
    public void execute() throws Exception {
        MailSignDto mailSignDto = new MailSignDto();
        mailSignDto.setMailTo("123456@qq.com");
        mailSignService.execute(mailSignDto, null);
        logger.debug(mailSignDto.getMailTo());
        logger.debug(mailSignDto.getMailFrom());
        logger.debug(mailSignDto.getMailSubject());
        logger.debug(mailSignDto.getMailContent());
    }

}
