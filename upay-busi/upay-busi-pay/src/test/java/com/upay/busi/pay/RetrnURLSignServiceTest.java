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
import com.upay.busi.pay.service.dto.RetrnURLSignDto;
import com.upay.busi.pay.service.impl.OrderStatusManageService;
import com.upay.busi.pay.service.impl.RetrnURLSignService;
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
public class RetrnURLSignServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(RetrnURLSignServiceTest.class);

    @Resource
    private RetrnURLSignService retrnURLSignServiceTest;


    @Test
    public void execute() throws Exception {

    	RetrnURLSignDto orderStatusManageDto = new RetrnURLSignDto();

        orderStatusManageDto.setOrderNo("UPAY201711070009452358");

        retrnURLSignServiceTest.execute(orderStatusManageDto, null);

    }
}
