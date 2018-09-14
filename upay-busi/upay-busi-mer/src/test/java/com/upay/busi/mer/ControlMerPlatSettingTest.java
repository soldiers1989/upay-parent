package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.ControlMerPlatSettingDto;
import com.upay.busi.mer.service.impl.ControlMerPlatSettingService;


/**
 * 特约商户申请
 * 
 * @author yanzixiong
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ControlMerPlatSettingTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ControlMerPlatSettingService controlMerPlatSettingService;
    @Resource
    IDipperCached idipperCached;


    @Test
    public void execute() throws Exception {
        ControlMerPlatSettingDto ControlMerPlatSettingDto = new ControlMerPlatSettingDto();
        ControlMerPlatSettingDto.setMerPlatNo("MER2016000117");
        ControlMerPlatSettingDto.setContralFlag("1");
        controlMerPlatSettingService.execute(ControlMerPlatSettingDto, null);
    }

}
