package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.PayOrderDetailInfoQryDto;
import com.upay.busi.pay.service.impl.PayOrderDetailInfoQryService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PayOrderDetailInfoQryTest {
    private Logger logger = LoggerFactory.getLogger(PayOrderDetailInfoQryTest.class);
    @Resource
    private PayOrderDetailInfoQryService payOrderDetailInfoQryService;


    @Test
    public void execute() throws Exception {
        PayOrderDetailInfoQryDto payOrderDetailInfoQryDto = new PayOrderDetailInfoQryDto();
        payOrderDetailInfoQryDto.setOrderNo("UPAY201611090000001574");
        PayOrderDetailInfoQryDto dd = payOrderDetailInfoQryService.execute(payOrderDetailInfoQryDto, null);
        System.out.println(dd.getTransAmt());
    }

}
