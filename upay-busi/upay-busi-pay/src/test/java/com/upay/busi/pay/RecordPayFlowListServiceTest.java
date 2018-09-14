package com.upay.busi.pay;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.RecordPayFlowListDto;
import com.upay.busi.pay.service.dto.SeqStatusManageDto;
import com.upay.busi.pay.service.impl.RecordPayFlowListService;
import com.upay.busi.pay.service.impl.SeqStatusManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class RecordPayFlowListServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(SeqStatusManageServiceTest.class);

    @Resource
    private RecordPayFlowListService secordPayFlowListService;


    @Test
    public void execute() throws Exception {

    	RecordPayFlowListDto seqStatusManageDto = new RecordPayFlowListDto();

        seqStatusManageDto.setTransAmt(new BigDecimal("22"));
        seqStatusManageDto.setSrFlag("1");
        seqStatusManageDto.setOrderNo("UPAY201703120009447503");
        seqStatusManageDto.setRouteCode("0003");

        secordPayFlowListService.execute(seqStatusManageDto, null);

    }
}
