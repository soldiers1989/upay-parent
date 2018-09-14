package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.PayOrderDetailInfoQryDto;
import com.upay.busi.pay.service.dto.ReportOrderDetailQueryDto;
import com.upay.busi.pay.service.impl.PayOrderDetailInfoQryService;
import com.upay.busi.pay.service.impl.ReportOrderDetailQueryService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ReportOrderDetailQueryServiceTest {
	 private Logger logger = LoggerFactory.getLogger(ReportOrderDetailQueryServiceTest.class);
	    @Resource
	    private ReportOrderDetailQueryService reportOrderDetailQueryService;


	    @Test
	    public void execute() throws Exception {
	    	ReportOrderDetailQueryDto reportOrderDetailQueryDto = new ReportOrderDetailQueryDto();
	    	reportOrderDetailQueryDto.setMerNo("MER2017000119");
	    	reportOrderDetailQueryDto.setBeginTime("2018-02-01");
	    	reportOrderDetailQueryDto.setEndTime("2018-02-08");
	    	ReportOrderDetailQueryDto dd = reportOrderDetailQueryService.execute(reportOrderDetailQueryDto, null);
	        System.out.println(dd);
	    }


}
