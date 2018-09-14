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

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.clearing.calculateRouteFee.CalculateRouteFee;
import com.upay.batch.stepservice.schedule.CheckPayFlowStat;
import com.upay.batch.stepservice.schedule.ContinueOrderService;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowListPo;

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CalculateRouteFeeTest {
    private Logger logger = LoggerFactory.getLogger(CalculateRouteFeeTest.class);
    @Resource(name="CalculateRouteFee")
    CalculateRouteFee calculateRouteFee;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setPreDate(DateUtil.parse("2017-08-04", "yyyy-MM-dd"));
    	batchParams.setTranDate(DateUtil.parse("2017-08-05", "yyyy-MM-dd"));
		calculateRouteFee.getTotalResult(batchParams, null);
		List<PayFlowListPo> dataList = calculateRouteFee.getDataList(batchParams, 0, 100, null);
		if(null!=dataList){
			for(PayFlowListPo map:dataList){
				calculateRouteFee.execute(batchParams, 0, map, null);
			}
		}
    	
    }
}
