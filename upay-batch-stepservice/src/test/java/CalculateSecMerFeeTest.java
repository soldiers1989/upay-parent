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
import com.upay.batch.stepservice.clearing.calculateSecMerFee.CalculateSecMerFee;
import com.upay.batch.stepservice.schedule.CheckPayFlowStat;
import com.upay.batch.stepservice.schedule.ContinueOrderService;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CalculateSecMerFeeTest {
    private Logger logger = LoggerFactory.getLogger(CalculateSecMerFeeTest.class);
    @Resource(name="CalculateSecMerFee")
    CalculateSecMerFee calculateSecMerFee;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setPreDate(DateUtil.parse("2017-08-07", "yyyy-MM-dd"));
    	batchParams.setTranDate(DateUtil.parse("2017-08-08", "yyyy-MM-dd"));
    	calculateSecMerFee.getTotalResult(batchParams, null);
		List<PayOrderListPo> dataList = calculateSecMerFee.getDataList(batchParams, 0, 100, null);
		if(null!=dataList){
			for(PayOrderListPo map:dataList){
				calculateSecMerFee.execute(batchParams, 0, map, null);
			}
		}
    	
    }
}
