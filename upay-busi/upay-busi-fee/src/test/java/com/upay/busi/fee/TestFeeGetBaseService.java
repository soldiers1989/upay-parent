package com.upay.busi.fee;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.fee.service.dto.FeeGetBaseDto;
import com.upay.busi.fee.service.impl.FeeGetBaseService;
import com.upay.dao.ISequenceService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月10日 下午4:59:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestFeeGetBaseService {

    @Resource
    FeeGetBaseService fee;
    @Resource
    ISequenceService seq;
    
    @Test
    public void execute() throws Exception{
        FeeGetBaseDto dto=new FeeGetBaseDto();
        dto.setMerNo("MER2017000159");
        dto.setSecMerNo("MER2017000159000001");
        dto.setTransCode("SI_PAY0009");
        dto.setOrderNo("UPAY201709150009451683");
        dto.setChnlId("02");
        dto.setAccType("01");
        dto.setRouteCode("0004");
        dto.setTransType("01");
        dto.setTransAmt(new BigDecimal(100));
        fee.execute(dto, null);
    }
}
