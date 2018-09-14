package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.CheckOrderListStatDto;
import com.upay.busi.pay.service.dto.SinglePaymentOrderChkDto;
import com.upay.busi.pay.service.impl.CheckOrderListStatService;
import com.upay.busi.pay.service.impl.GeneralSinglePaymentParamChkService;

@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
	public class GeneralSinglePaymentParamChkServiceTest {

	    @Resource
	    GeneralSinglePaymentParamChkService generalSinglePaymentParamChkService;
	    
	    
	    @Test
	    public void execute() throws Exception{
	    	SinglePaymentOrderChkDto dto=new SinglePaymentOrderChkDto();
	        dto.setPayRouteMethod("ALIPAY_USE_AT_ROUTE");
	        dto.setMerNo("UAT2018000230");
	        dto=generalSinglePaymentParamChkService.execute(dto, null);
	        System.out.println(dto);
	    }
	}
