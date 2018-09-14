package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.PayStateQryDto;
import com.upay.busi.pay.service.impl.PayStateQryService;


/**
 * 流水号查询订单状态（单笔）----测试类
 * 
 * @author liu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PayStateQryServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(PayStateQryServiceTest.class);

    @Resource
    private PayStateQryService payStateQryService;


    @Test
    public void execute() throws Exception {
        PayStateQryDto payStateQryDto = new PayStateQryDto();

        payStateQryDto.setSysSeq("1");

        payStateQryService.execute(payStateQryDto, null);

    }

}
