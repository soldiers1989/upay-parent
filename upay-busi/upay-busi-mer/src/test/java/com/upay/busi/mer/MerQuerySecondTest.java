package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.MerQuerySecondDto;
import com.upay.busi.mer.service.impl.MerQuerySecondService;


/**
 * 一级商户查询二级商户
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MerQuerySecondTest {
	 @Resource
	    private MerQuerySecondService merQuerySecondService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	        MerQuerySecondDto merQuerySecondDto = new MerQuerySecondDto();
	        merQuerySecondDto.setUserId("UR000000000015");
	        //merQuerySecondDto.setSecMerName("集中");
	        //merQuerySecondDto.setSecMerNo("4");
	        merQuerySecondDto.setPageIndex(3);
	        merQuerySecondDto.setCurrentNum(2);
	        merQuerySecondService.execute(merQuerySecondDto, null);
	    }

}
