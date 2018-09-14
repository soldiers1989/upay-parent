package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.ComRegDto;
import com.upay.busi.usr.service.impl.ComRegService;


/**
 * 个人信息查询
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ComRegTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ComRegService comRegService;


    @Test
    public void execute() throws Exception {
        ComRegDto comRegDto = new ComRegDto();
        comRegDto.setMailTo("123456@qq.com");
        comRegService.execute(comRegDto, null);

    }

}
