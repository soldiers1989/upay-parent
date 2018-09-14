package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.QueryFeeKindDto;
import com.upay.busi.mer.service.impl.QueryFeeKindService;


/**
 * 查询一级商户维护的费率规则信息
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class QueryFeeKindTest {
	 @Resource
	    private QueryFeeKindService queryFeeKindService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {	    	
	    	QueryFeeKindDto queryFeeKindDto = new QueryFeeKindDto();
	    	queryFeeKindDto.setPageIndex(2);
	    	queryFeeKindDto.setCurrentNum(1);
	    	queryFeeKindDto.setFeeName("1");
	    	
	    	queryFeeKindService.execute(queryFeeKindDto, null);
	    }

}
