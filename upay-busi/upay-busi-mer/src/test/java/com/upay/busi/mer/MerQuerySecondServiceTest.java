package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.MerQuerySecondDto;
import com.upay.busi.mer.service.dto.SecondQueryFeeDto;
import com.upay.busi.mer.service.impl.MerQuerySecondService;
import com.upay.busi.mer.service.impl.SecondQueryFeeService;


/**
 * 检查一级商户状态
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MerQuerySecondServiceTest {
	 @Resource
	    private MerQuerySecondService merQuerySecondService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	    	MerQuerySecondDto secondQueryFeeDto = new MerQuerySecondDto();
	    	secondQueryFeeDto.setUserId("UR000001733197");
	    	secondQueryFeeDto.setPageIndex(1);
	    	secondQueryFeeDto.setCurrentNum(5);
	    	merQuerySecondService.execute(secondQueryFeeDto, null);
	    }

}
