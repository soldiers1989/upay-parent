package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.MerStatChkDto;
import com.upay.busi.mer.service.impl.MerStatChkService;


/**
 * 商户状态检查
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MerQueryStatTest {
	 @Resource
	    private MerStatChkService merQueryStatService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	        MerStatChkDto merQueryStatDto = new MerStatChkDto();
	        merQueryStatDto.setUserId("12332112332111");
	        merQueryStatDto.setMerNo("5");
	        merQueryStatService.execute(merQueryStatDto, null);
	    }

}
