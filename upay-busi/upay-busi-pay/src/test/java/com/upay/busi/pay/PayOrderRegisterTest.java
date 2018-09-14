package com.upay.busi.pay;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.PayOrderRegisterDto;
import com.upay.busi.pay.service.impl.PayOrderRegisterService;
import com.upay.commons.util.DateUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PayOrderRegisterTest {
    private Logger logger = LoggerFactory.getLogger(PayOrderRegisterTest.class);
    @Resource
    private PayOrderRegisterService payOrderRegisterService;


    @Test
    public void execute() throws Exception {
        PayOrderRegisterDto payOrderRegisterDto = new PayOrderRegisterDto();
        // payOrderRegisterDto.setOrderNo("123456");
        payOrderRegisterDto.setChnlId("01");
        payOrderRegisterDto.setPayServicType("0001");
        // payOrderRegisterDto.setMerDate(DateUtil.format(new Date(),
        // "yyyyMMddHHmmss"));
        payOrderRegisterDto.setOuterOrderStartDate(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        payOrderRegisterDto.setOuterOrderEndDate(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        payOrderRegisterDto.setOrderType("01");
        payOrderRegisterDto.setMerSeq("12");
        payOrderRegisterDto.setMerNo("12");
        payOrderRegisterDto.setSecMerNo("13");
        payOrderRegisterDto.setTransCode("14");
        payOrderRegisterDto.setOrderDate(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        payOrderRegisterDto.setOrderLmtTime(0);
        payOrderRegisterDto.setCurr("1");
        payOrderRegisterDto.setTransAmt(new BigDecimal(10000));
        payOrderRegisterDto.setOrderStat("0");
        payOrderRegisterDto.setOrderTime(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        payOrderRegisterDto.setSpbillCreateIp("1111");
        payOrderRegisterService.execute(payOrderRegisterDto, null);

    }
}
