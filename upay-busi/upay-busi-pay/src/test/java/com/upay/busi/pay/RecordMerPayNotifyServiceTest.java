/**
 * 
 */
package com.upay.busi.pay;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.CheckOrderListStatDto;
import com.upay.busi.pay.service.dto.RecordMerPayNotifyDto;
import com.upay.busi.pay.service.impl.CheckOrderListStatService;
import com.upay.busi.pay.service.impl.RecordMerPayNotifyService;

/**
 * @author shang
 * 2016年11月21日
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classPath*:META-INF/spring/**/*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class RecordMerPayNotifyServiceTest {

    @Resource
    RecordMerPayNotifyService r;
    
    
    @Test
    public void execute() throws Exception{
        RecordMerPayNotifyDto dto=new RecordMerPayNotifyDto();
        dto.setOrderNo("UPAY201701040000004125");
        dto=r.execute(dto, null);
        System.out.println(dto);
    }
}
