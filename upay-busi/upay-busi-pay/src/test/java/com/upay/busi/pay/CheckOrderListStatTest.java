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
import com.upay.busi.pay.service.impl.CheckOrderListStatService;

/**
 * @author shang
 * 2016年11月21日
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classPath*:META-INF/spring/**/*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CheckOrderListStatTest {

    @Resource
    CheckOrderListStatService checkOrderListStatService;
    
    
    @Test
    public void execute() throws Exception{
        CheckOrderListStatDto dto=new CheckOrderListStatDto();
        dto.setOrderNo("UPAY201611090000001574");
        dto=checkOrderListStatService.execute(dto, null);
        System.out.println(dto);
    }
}
