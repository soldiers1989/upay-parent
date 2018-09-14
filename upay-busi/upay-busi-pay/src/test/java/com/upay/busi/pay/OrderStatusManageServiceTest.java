package com.upay.busi.pay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.busi.pay.service.dto.OrderStatusManageDto;
import com.upay.busi.pay.service.impl.OrderStatusManageService;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;


/**
 * 订单号管理订单状态
 * 
 * @author liu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class OrderStatusManageServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusManageServiceTest.class);

    @Resource
    private OrderStatusManageService orderStatusManageService;


    @Test
    public void execute() throws Exception {

        OrderStatusManageDto orderStatusManageDto = new OrderStatusManageDto();

        orderStatusManageDto.setOrderNo("1");
        orderStatusManageDto.setOrderStat("2");

        orderStatusManageService.execute(orderStatusManageDto, null);

    }
    public static void main(String [] args) throws ParseException{
    	
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		Date now =new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        
        String startDate = dateFormat.format(now);
        String endDate=dateFormat.format(cal.getTime());
        ;
		System.out.println(startDate);
		System.out.println(endDate);
		if(dateFormat.parse("20171031104118").compareTo(dateFormat.parse("20171101104118"))>=0
		        ||dateFormat.parse(endDate).compareTo(now)<0){
		    ExInfo.throwDipperEx("时间段不对");
		}
	}
}
