package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.DemoDto;
import com.upay.busi.pay.service.impl.DemoAtomHandler;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class DemoAtomTest {
    private Logger logger = LoggerFactory.getLogger(DemoAtomTest.class);
    @Resource
    private DemoAtomHandler demoAtomHandler;


    @Test
    public void execute() throws Exception {
        DemoDto demoDto = new DemoDto();
        // moneyChannelSearchDto.setChannelId("01");
        demoAtomHandler.execute(demoDto, null);
    }

}
