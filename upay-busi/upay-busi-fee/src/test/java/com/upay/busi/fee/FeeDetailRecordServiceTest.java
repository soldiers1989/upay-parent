package com.upay.busi.fee;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.fee.service.dto.FeeDetailRecordDto;
import com.upay.busi.fee.service.impl.FeeDetailRecordService;


/**
 * 费用明细记录----测试类
 * 
 * @author liu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class FeeDetailRecordServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(FeeDetailRecordServiceTest.class);

    @Resource
    private FeeDetailRecordService feeDetailRecordService;


    @Test
    public void execute() throws Exception {
        FeeDetailRecordDto feeDetailRecordDto = new FeeDetailRecordDto();
        Date date = new Date();
        feeDetailRecordDto.setTxnDate(date);
        feeDetailRecordDto.setTxnTime(date);
        feeDetailRecordDto.setOrderNo("12345678");
        feeDetailRecordDto.setAcctNo("2222222222222");
        feeDetailRecordDto.setAcctType("0000");
        feeDetailRecordDto.setChnlId("01");
        feeDetailRecordDto.setTransCode("00000001");
        feeDetailRecordDto.setFeeCode("1234");
        feeDetailRecordDto.setAssCode("2345");
        feeDetailRecordDto.setTxnStat("1");
        feeDetailRecordDto.setGetType("1");
        feeDetailRecordDto.setTxnAmt(BigDecimal.valueOf(1111111D));
        feeDetailRecordDto.setFeeAmt(BigDecimal.valueOf(11D));
        feeDetailRecordDto.setFreeFlag("1");
        feeDetailRecordDto.setFreeCycle("1");

        feeDetailRecordService.execute(feeDetailRecordDto, null);

    }
}
