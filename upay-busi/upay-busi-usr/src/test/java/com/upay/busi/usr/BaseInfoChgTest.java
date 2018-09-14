package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.usr.service.dto.BaseInfoChgDto;
import com.upay.busi.usr.service.impl.BaseInfoChgService;


/**
 * 个人信息查询
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class BaseInfoChgTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private BaseInfoChgService baseInfoChgService;
    @Resource
    IDipperCached idipperCached;


    @Test
    public void execute() throws Exception {
        BaseInfoChgDto baseInfoChgDto = new BaseInfoChgDto();
        baseInfoChgDto.setUserId("123476");
        baseInfoChgService.execute(baseInfoChgDto, null);
    }

}
