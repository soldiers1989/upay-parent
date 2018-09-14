package com.upay.busi.pay;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.LimitAmtCheckDto;
import com.upay.busi.pay.service.dto.TranAmtChangDto;
import com.upay.busi.pay.service.impl.LimitAmtCheckService;
import com.upay.busi.pay.service.impl.TranAmtChangService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月10日 上午10:31:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TranAmtChangServiceTest {
    
    @Resource
    TranAmtChangService limit;
    
    @Test
    public void execute() throws Exception{
    	TranAmtChangDto dto=new TranAmtChangDto();
        dto.setTransAmtStr("0.02");
        dto.setChangFlag("1");
        TranAmtChangDto execute = limit.execute(dto, null);
        System.out.println(execute.getTotalFee());
        System.out.println(execute.getTransAmt());
    }

}
