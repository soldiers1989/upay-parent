package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.ChannelLogoSearchDto;
import com.upay.busi.pay.service.impl.ChannelLogoSearchService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChannelLogoSearchTest {
    private Logger logger = LoggerFactory.getLogger(ChannelLogoSearchTest.class);
    @Resource
    private ChannelLogoSearchService channelLogoSearchService;


    @Test
    public void execute() throws Exception {
        ChannelLogoSearchDto channelLogoSearchDto = new ChannelLogoSearchDto();
        // channelLogoSearchDto.setChannelId("02");
        channelLogoSearchService.execute(channelLogoSearchDto, null);
    }
}
