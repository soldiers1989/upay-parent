package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.usr.service.dto.CheckSignDto;
import com.upay.busi.usr.service.impl.CheckSignService;


/**
 * 个人信息查询
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CheckSignTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CheckSignService checkSignService;


    @Test
    public void execute() throws Exception {
        CheckSignDto checkSignDto = new CheckSignDto();
        checkSignDto.setSid("6BA8163B30FF3B9F18D3AAB2207E50AC");
        checkSignService.execute(checkSignDto, null);
    }

}
