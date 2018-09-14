import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodayRouteFee;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodaySubProfit;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SumTodaySubProfitTest {
    private Logger logger = LoggerFactory.getLogger(SumTodaySubProfitTest.class);
    @Resource(name="SumTodaySubProfit")
    SumTodaySubProfit sumTodaySubProfit;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setBatchNo("201225784521023");
    	batchParams.setPreDate(DateUtil.parse("2017-08-15", "yyyy-MM-dd"));
    	batchParams.setTranDate(DateUtil.parse("2017-08-16", "yyyy-MM-dd"));
//    	PayRouteInfoPo payRoute=new PayRouteInfoPo();
//    	payRoute.setRouteCode("0003");
//			    	List<PayRouteInfoPo> dataList = sumTodaySubProfit.getDataList(batchParams, 0, 0, null);
//    	for(PayRouteInfoPo payRoute:dataList){
    		sumTodaySubProfit.execute(batchParams, 0, null, null);
//    	}
    }
}
