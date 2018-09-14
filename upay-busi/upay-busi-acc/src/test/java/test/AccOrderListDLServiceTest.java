package test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccOrderListQueryDto;
import com.upay.busi.acc.service.impl.AccOrderListDownLoadService;


/**
 * 交易明细查询----测试类
 * 
 * @author xu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccOrderListDLServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccOrderListDLServiceTest.class);

    @Resource
    private AccOrderListDownLoadService accOrderListDownLoadService;
    @Test
    public void execute() throws Exception {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        AccOrderListQueryDto payStateQryDto = new AccOrderListQueryDto();
        LOG.info("=====  start  =====");
        payStateQryDto.setUserId("UR000000000181");
//        payStateQryDto.setTransType("01");
//        payStateQryDto.setOrderStat("1");
        
        payStateQryDto.setMerNo("13968111");
//        payStateQryDto.setStartDate("2016-09-27");
//        payStateQryDto.setEndDate("2016-11-27");
//        payStateQryDto.setQueryStart(sdf.parse("2016-09-27"));
//        payStateQryDto.setQueryEnd(sdf.parse("2016-11-27"));
        AccOrderListQueryDto accOrderListQueryDto = accOrderListDownLoadService.execute(payStateQryDto, null);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result = accOrderListQueryDto.getTransList();
        if (result.size()>0) {
        	for(Map<String, Object> map:result){
         	   Set<String>keys = map.keySet();
         	   Iterator<String> keeys= keys.iterator();
         	   while(keeys.hasNext()){
         		   String key = keeys.next();
         		   System.out.println("key:"+key+"\t value:"+map.get(key));
         	   }
            }
		}
       
    }

}
