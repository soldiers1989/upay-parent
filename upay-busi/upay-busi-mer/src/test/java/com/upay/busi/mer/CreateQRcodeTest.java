package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.CreateMerQrCodeDto;
import com.upay.busi.mer.service.impl.CreateMerQrCodeService;


/**
 * 生成二维码
 * 
 * @author yanzixiong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CreateQRcodeTest {
	 @Resource
	    private CreateMerQrCodeService createMerQrCodeService;
	    @Resource
	    IDipperCached idipperCached;


	    @Test
	    public void execute() throws Exception {
	        CreateMerQrCodeDto createMerQrCodeDto = new CreateMerQrCodeDto();
	        createMerQrCodeDto.setMerNo("15234488");
	        createMerQrCodeDto.setFilePath("C:/home/appsvr/logs");
	    	createMerQrCodeService.execute(createMerQrCodeDto, null);
	    }

}
