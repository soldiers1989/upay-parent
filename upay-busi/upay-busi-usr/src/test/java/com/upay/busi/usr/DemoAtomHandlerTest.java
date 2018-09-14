package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.DemoDto;
import com.upay.busi.usr.service.impl.DemoAtomHandler;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class DemoAtomHandlerTest {

	private Logger logger = LoggerFactory.getLogger(DemoAtomHandlerTest.class);

	@Resource
	IDaoService dao;
	@Resource
	private DemoAtomHandler demoAtomHandler;
//	private StudentService stu;
//	private UsrBaseInfoService usr;
	
	
	@Test
	public void execute() throws Exception {
//		dao.insert(arg0)
		DemoDto dto = new DemoDto();
////		UsrBaseInfoDto dto=new UsrBaseInfoDto();
//		dto.setCertName("shang");
//		dto.setSex("1");
//		dto.setCountry("1");
//		dto.setNation("1");
////		dto.setName("shang");
		demoAtomHandler.execute(dto, null);
	}

}
