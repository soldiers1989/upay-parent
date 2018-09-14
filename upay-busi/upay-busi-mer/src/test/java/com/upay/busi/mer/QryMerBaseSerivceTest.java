package com.upay.busi.mer;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.mer.service.dto.QueryMerBaseDto;
import com.upay.busi.mer.service.impl.QueryMerBaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class QryMerBaseSerivceTest {
	 @Resource
	    private QueryMerBaseService queryMerBaseService;


	    @Test
	    public void execute() throws Exception {
	    	
	    	QueryMerBaseDto merBaseDto = new QueryMerBaseDto();
	    	merBaseDto.setUserId("UR000000000064");
	    	merBaseDto = queryMerBaseService.execute(merBaseDto,null);
	    	//System.out.println(merBaseDto.getMerNo());
	    }	
}
