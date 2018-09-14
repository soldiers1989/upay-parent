package com.upay.busi.pay;

import java.util.Date;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.OrderSyncStatusManageDto;
import com.upay.busi.pay.service.impl.OrderSyncStatusManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ThreadRun extends Thread{
	private Logger logger = LoggerFactory
			.getLogger(ThreadRun.class);
	private static OrderSyncStatusManageService bean ;
	
	static{
		ApplicationContext ac = new FileSystemXmlApplicationContext(
				"classpath:META-INF/spring/bean/service-bean.xml");
		bean = ac
				.getBean(OrderSyncStatusManageService.class);
	}
	
	@Override
	public void run() {
		OrderSyncStatusManageDto dto = new OrderSyncStatusManageDto();
		dto.setOrderNo("11");
		dto.setOrderStat("1");
		dto.setPayType("02");
		dto.setUserId("11");
		dto.setPayTime(new Date());
		try {
			bean.execute(dto, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {
			Thread test = new ThreadRun();
			test.setName("NO"+i);
			test.start();
		}
		
	}
}
