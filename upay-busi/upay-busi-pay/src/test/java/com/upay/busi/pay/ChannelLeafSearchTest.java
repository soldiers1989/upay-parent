package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.ChannelLeafSearchDto;
import com.upay.busi.pay.service.impl.ChannelLeafSearchService;
import com.upay.commons.constants.DataBaseConstants_USR;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChannelLeafSearchTest {
    private Logger logger = LoggerFactory.getLogger(ChannelLeafSearchTest.class);
    @Resource
    private ChannelLeafSearchService channelLeafSearchService;


    @Test
    public void execute() throws Exception {
        ChannelLeafSearchDto moneyChannelSearchDto = new ChannelLeafSearchDto();
//        moneyChannelSearchDto.setChannelId("01");
        moneyChannelSearchDto.setChnlId(DataBaseConstants_USR.CHNL_ID_WEB);
        moneyChannelSearchDto.setMerNo("MER2017000119");
        channelLeafSearchService.execute(moneyChannelSearchDto, null);
    }

}
