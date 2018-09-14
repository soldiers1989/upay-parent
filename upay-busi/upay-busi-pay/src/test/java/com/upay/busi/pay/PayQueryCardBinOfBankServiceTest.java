package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.PayQueryCardBinOfBankDto;
import com.upay.busi.pay.service.dto.PayRouteStateChkDto;
import com.upay.busi.pay.service.impl.PayQueryCardBinOfBankService;
import com.upay.busi.pay.service.impl.PayRouteStateChkService;


/**
 * 资金通道状态检查
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class PayQueryCardBinOfBankServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private PayQueryCardBinOfBankService payQueryCardBinOfBankService;


    @Test
    public void execute() throws Exception {
    	PayQueryCardBinOfBankDto payQueryCardBinOfBankDto = new PayQueryCardBinOfBankDto();
    	payQueryCardBinOfBankDto.seteBindAcctNo("4392268313549702");
        PayQueryCardBinOfBankDto rtnDto = payQueryCardBinOfBankService.execute(payQueryCardBinOfBankDto, null);
        System.out.println(rtnDto.getCupBankName());
    }

}
