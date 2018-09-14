package test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccOrderListQueryDto;
import com.upay.busi.acc.service.impl.AccOrderListQueryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccOrderListQueryTest {
    @Resource
    private AccOrderListQueryService accOrderListQueryService;

    @Test
    public void execute() throws Exception {
        AccOrderListQueryDto accOrderListQueryDto = new AccOrderListQueryDto();
        accOrderListQueryDto.setUserId("UR000000000110");
//        accOrderListQueryDto.setStartDate("2016-10-01");
//        accOrderListQueryDto.setEndDate("2016-11-07");
//		accOrderListQueryDto.setQueryScope("");
//		accOrderListQueryDto.setTransCode("01");
//        accOrderListQueryDto.setTransType("01");
//        accOrderListQueryDto.setOrderStat("1");
//        accOrderListQueryDto.setSysDate(new Date());
//        accOrderListQueryDto.setQueryScope("03");
        accOrderListQueryDto.setSecMerNo("MER2017000119000001");
        accOrderListQueryDto.setMerNo("MER2017000119");
        accOrderListQueryDto.setPageIndex(1);
        accOrderListQueryDto.setCurrentNum(10);
        accOrderListQueryDto.setQueryStart(null);
        accOrderListQueryDto = accOrderListQueryService.execute(
                accOrderListQueryDto, null);
        List<Map<String, Object>> resultList = accOrderListQueryDto.getTransList();
        for (Map<String, Object> map : resultList) {
            map = resultList.get(0);
            System.out.println(map.size());
            Set<String> keys = map.keySet();
            Iterator<String> keeys = keys.iterator();
            while (keeys.hasNext()) {
                String key = keeys.next();
                System.out.println("key:" + key + "\t value:" + map.get(key));
            }
        }

    }
}
