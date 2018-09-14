package transferRouteFee;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodayRouteFee;
import com.upay.commons.util.DateUtil;

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SumTodayRouteFeeTest {
    private Logger logger = LoggerFactory.getLogger(SumTodayRouteFeeTest.class);
    @Resource(name="SumTodayRouteFee")
    SumTodayRouteFee sumTodayRouteFee;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setBatchNo("20175784521024");
    	batchParams.setPreDate(DateUtil.parse("2017-09-05", "yyyy-MM-dd"));
    	batchParams.setTranDate(DateUtil.parse("2017-09-06", "yyyy-MM-dd"));
    	sumTodayRouteFee.execute(batchParams, 0, null, null);
    }
}
