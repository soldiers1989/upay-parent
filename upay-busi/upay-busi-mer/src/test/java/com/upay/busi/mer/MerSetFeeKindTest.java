package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.MerSetFeeKindDto;
import com.upay.busi.mer.service.impl.MerSetFeeKindService;


/**
 * 一级商户维护二级商户手续费计算方法
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MerSetFeeKindTest {
	 @Resource
	    private MerSetFeeKindService merSetFeeKindService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	    	
	    	MerSetFeeKindDto merSetFeeKindDto = new MerSetFeeKindDto();
	    	merSetFeeKindDto.setOperation("02");
	    	merSetFeeKindDto.setFeeName("测试用03");
	    	merSetFeeKindDto.setFeeCode("000188");
	    	merSetFeeKindDto.setFeeMode("2");
	    	merSetFeeKindDto.setFixFee("2");
	    	merSetFeeKindDto.setRationFee("11");
	    	merSetFeeKindService.execute(merSetFeeKindDto, null);
	    }	

}
