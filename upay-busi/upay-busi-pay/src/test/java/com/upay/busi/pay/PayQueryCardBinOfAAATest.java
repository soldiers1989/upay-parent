package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.PayQueryCardBinOfAAADto;
import com.upay.busi.pay.service.impl.PayQueryCardBinOfAAAService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PayQueryCardBinOfAAATest {
    private Logger logger = LoggerFactory.getLogger(PayQueryCardBinOfAAATest.class);
    @Resource
    private PayQueryCardBinOfAAAService payQueryCardBinOfAAAService;


    @Test
    public void execute() throws Exception {
        PayQueryCardBinOfAAADto payQueryCardBinOfAAADto = new PayQueryCardBinOfAAADto();
        payQueryCardBinOfAAADto.seteBindAcctNo("6232711235648545123");
        payQueryCardBinOfAAAService.execute(payQueryCardBinOfAAADto, null);
    }
}
