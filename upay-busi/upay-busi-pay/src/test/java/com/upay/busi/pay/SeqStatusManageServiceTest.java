package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.SeqStatusManageDto;
import com.upay.busi.pay.service.impl.SeqStatusManageService;


/**
 * 流水号管理交易状态
 * 
 * @author liu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SeqStatusManageServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(SeqStatusManageServiceTest.class);

    @Resource
    private SeqStatusManageService seqStatusManageService;


    @Test
    public void execute() throws Exception {

        SeqStatusManageDto seqStatusManageDto = new SeqStatusManageDto();

        seqStatusManageDto.setSysSeq("1");
        seqStatusManageDto.setTransStat("2");

        seqStatusManageService.execute(seqStatusManageDto, null);

    }

}
