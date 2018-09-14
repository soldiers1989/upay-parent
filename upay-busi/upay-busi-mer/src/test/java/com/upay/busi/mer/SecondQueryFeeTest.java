package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.SecondQueryFeeDto;
import com.upay.busi.mer.service.impl.SecondQueryFeeService;


/**
 * 检查一级商户状态
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SecondQueryFeeTest {
	 @Resource
	    private SecondQueryFeeService secondQueryFeeService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	    	SecondQueryFeeDto secondQueryFeeDto = new SecondQueryFeeDto();
	    	secondQueryFeeDto.setUserId("12332112332111");
	    	secondQueryFeeDto.setPageIndex(2);
	    	secondQueryFeeDto.setCurrentNum(1);
	    	//secondQueryFeeDto.setSecMerNo("3");
	        secondQueryFeeService.execute(secondQueryFeeDto, null);
	    }

}
