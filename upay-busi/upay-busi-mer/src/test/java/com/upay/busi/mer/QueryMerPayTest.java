package com.upay.busi.mer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.QueryMerPayDto;
import com.upay.busi.mer.service.impl.QueryMerPayService;


/**
 * 查询二级商户支付流水表里所有的支付交易信息
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class QueryMerPayTest {
	 @Resource
	    private QueryMerPayService queryMerPayService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
	    	String start = "2016-7-19";
	    	String end = "2016-7-20";
	    	Date startDate = sim.parse(start);
	    	Date endDate = sim.parse(end);
	    	
	    	QueryMerPayDto queryMerPayDto = new QueryMerPayDto();
	    	queryMerPayDto.setUserId("UR000000000015");
	    	//queryMerPayDto.setPageIndex(2);
	    	//queryMerPayDto.setCurrentNum(1);
	    	queryMerPayDto.setSecMerNo("4");
	    	queryMerPayDto.setStartDate(startDate);
	    	//queryMerPayDto.setEndDate(endDate);
	    	
	    	queryMerPayService.execute(queryMerPayDto, null);
	    }

}
