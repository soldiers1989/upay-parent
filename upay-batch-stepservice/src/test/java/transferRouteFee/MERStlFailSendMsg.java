package transferRouteFee;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.schedule.MerStlFailSendMsg;
import com.upay.commons.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MERStlFailSendMsg {
	 private Logger logger = LoggerFactory.getLogger(SumTodayRouteFeeTest.class);
	    @Resource(name="MerStlFailSendMsg")
	    MerStlFailSendMsg merStlFailSendMsg;
	    
	    @Test
	    public void testExecute() throws Exception {
	    	BatchParams batchParams=new BatchParams();
	    	batchParams.setBatchNo("20175784521024");
	    	batchParams.setPreDate(DateUtil.parse("2018-01-05", "yyyy-MM-dd"));
	    	batchParams.setTranDate(DateUtil.parse("2018-09-06", "yyyy-MM-dd"));
	    	merStlFailSendMsg.execute(batchParams, 0, null, null);
	    }
}
