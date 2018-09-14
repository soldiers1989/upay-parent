package com.upay.busi.pay;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.CheckOrderListStatDto;
import com.upay.busi.pay.service.dto.LimitAmtCheckDto;
import com.upay.busi.pay.service.impl.CheckOrderListStatService;
import com.upay.busi.pay.service.impl.LimitAmtCheckService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月10日 上午10:31:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestCheckOrderStat {
    
    @Resource
    CheckOrderListStatService check;
    
    @Test
    public void execute() throws Exception{
        CheckOrderListStatDto dto=new CheckOrderListStatDto();
        dto.setOrderNo("UPAY201612160000003012");
        dto.setIfUpdateOrd("N");
        dto=check.execute(dto, null);
    }

}
