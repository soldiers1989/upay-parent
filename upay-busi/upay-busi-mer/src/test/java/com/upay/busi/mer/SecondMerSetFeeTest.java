package com.upay.busi.mer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.SecondMerSetFeeDto;
import com.upay.busi.mer.service.impl.SecondMerSetFeeService;


/**
 * 修改或者新增费率信息
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SecondMerSetFeeTest {
	 @Resource
	    private SecondMerSetFeeService secondMerSetFeeService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
	    	String start = "2016-11-24";
	    	String end = "2016-11-25";
	    	
	    	SecondMerSetFeeDto secondMerSetFeeDto = new SecondMerSetFeeDto();
	    	secondMerSetFeeDto.setTransCode("SI_MER0009");
	    	secondMerSetFeeDto.setUserId("UR000000000321");
	    	secondMerSetFeeDto.setOperation("03");
	    	secondMerSetFeeDto.setFeeId("FEE00083");
	    	secondMerSetFeeDto.setFeeName("web手续费-01");
	    	secondMerSetFeeDto.setSecMerNo("4");
	    	secondMerSetFeeDto.setFeeCode("0001");
	    	secondMerSetFeeDto.setAssCode("123123");
	    	secondMerSetFeeDto.setStartDate(start);
	    	secondMerSetFeeDto.setEndDate(end);
	    	secondMerSetFeeService.execute(secondMerSetFeeDto, null);
	    }	

}
