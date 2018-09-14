package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccOrderListQueryDto;
import com.upay.busi.acc.service.impl.AccOrderListDownLoadService;
import com.upay.busi.acc.service.impl.AccOrderListQueryService;


/**
 * 交易明细查询----测试类
 * 
 * @author xu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccOrderListQryServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccOrderListQryServiceTest.class);

    @Resource
    private AccOrderListQueryService payOrderListQueryService;


    @Test
    public void execute() throws Exception {
        AccOrderListQueryDto payStateQryDto = new AccOrderListQueryDto();
        LOG.info("=====  start  =====");
        String start = "2016-07-19";
        String end = "2016-12-27";

        // payStateQryDto.setQueryScope("03");
        payStateQryDto.setUserId("UR000001733357");
        payStateQryDto.setTransCode("SI_ACC0006");
        payStateQryDto.setCurrentNum(10);
        payStateQryDto.setPageIndex(1);
//        payStateQryDto.setOrderStat("1");
//        payStateQryDto.setTransType("01");
       // payStateQryDto.setRouteCode("0002");
        payStateQryDto.setChnlId("02");
        payStateQryDto.setMerNo("MER2017000211");
        payStateQryDto.setSecMerNo("MER2017000211000002");
        // payStateQryDto.setQueryStart(SysInfoContext.getSysDate());
        // payStateQryDto.setQueryEnd(SysInfoContext.getNextDate());
       // payStateQryDto.setStartDate(start);
       // payStateQryDto.setEndDate(end);
        payOrderListQueryService.execute(payStateQryDto, null);

    }

}
