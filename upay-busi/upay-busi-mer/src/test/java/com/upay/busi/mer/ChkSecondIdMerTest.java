package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.ChkSecondIsMerDto;
import com.upay.busi.mer.service.impl.ChkSecondIsMerService;


/**
 * 检查二级商户是否所属一级商户
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChkSecondIdMerTest {
	 @Resource
	    private ChkSecondIsMerService chkSecondIsMerService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	    	ChkSecondIsMerDto chkSecondIsMerDto = new ChkSecondIsMerDto();
	    	chkSecondIsMerDto.setUserId("UR000000000015");
	    	chkSecondIsMerDto.setSecMerNo("3");
	        chkSecondIsMerService.execute(chkSecondIsMerDto, null);
	    }

}
