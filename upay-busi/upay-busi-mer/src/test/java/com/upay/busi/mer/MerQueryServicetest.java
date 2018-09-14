package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.MerApplyDto;
import com.upay.busi.mer.service.dto.MerQueryDto;
import com.upay.busi.mer.service.impl.MerApplyService;
import com.upay.busi.mer.service.impl.MerQueryService;


/**
 * 特约商户申请
 * 
 * @author yanzixiong
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MerQueryServicetest {
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private MerQueryService merQueryService;
    
    @Test
    public void execute() throws Exception {
    	MerQueryDto queryDto=new MerQueryDto();
    	queryDto.setMerNo("MER2017000159");
    	MerQueryDto execute = merQueryService.execute(queryDto, null);
    	
    }
    
}
