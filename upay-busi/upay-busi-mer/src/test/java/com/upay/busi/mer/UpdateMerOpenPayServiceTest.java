package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.mer.service.dto.MerQueryDto;
import com.upay.busi.mer.service.dto.UpdateMerOpenPayDto;
import com.upay.busi.mer.service.impl.MerQueryService;
import com.upay.busi.mer.service.impl.UpdateMerOpenPayService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class UpdateMerOpenPayServiceTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UpdateMerOpenPayService updateMerOpenPayService;
    
    @Test
    public void execute() throws Exception {
    	UpdateMerOpenPayDto queryDto=new UpdateMerOpenPayDto();
    	queryDto.setMerNo("MER2017000159");
    	updateMerOpenPayService.execute(queryDto, null);
    	
    }
}
