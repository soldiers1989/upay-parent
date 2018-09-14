package com.upay.busi.pay;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.pay.service.dto.LimitAmtCheckDto;
import com.upay.busi.pay.service.impl.LimitAmtCheckService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月10日 上午10:31:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestLimitAmtCheckService {
    
    @Resource
    LimitAmtCheckService limit;
    
    @Test
    public void execute() throws Exception{
        LimitAmtCheckDto dto=new LimitAmtCheckDto();
        dto.setTransAmt(new BigDecimal("1000"));
        dto.setMerNo("MER2017000120");
        dto.setCheckUserLmt("Y");
        dto.setPayType("11");
        dto.setUserId("UR000000000521");
        dto.setAccType("14");
        dto.setChnlId("03");
        dto.setDcFlag("D");
        dto.setPayServicType("0002");
        dto.setTransCode("SI_PAY2001");
        dto.setLmtAccountFlag("1");
        dto.setSysLmtType("01");
        dto.setTransType("01");
        
        limit.execute(dto, null);
    }

}
