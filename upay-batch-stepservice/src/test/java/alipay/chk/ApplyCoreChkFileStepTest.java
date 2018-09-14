package alipay.chk;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.alipay.ApplyAlipayChkFileStep;
import com.upay.batch.stepservice.chk.core.ApplyCoreChkFileStep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ApplyCoreChkFileStepTest {
	
	  private Logger logger = LoggerFactory.getLogger(ApplyCoreChkFileStepTest.class);
	  
	    @Resource(name="ApplyAlipayChkFileStep")
	    ApplyAlipayChkFileStep applyAlipay;
	    
	    @Test
	    public void testExecute() throws Exception {
	        int pageSize=10;
	        BatchParams batchParams=new BatchParams();
//	        batchParams.setExecutionType(ExecutionType.CONTINUE);
	        batchParams.setBatchNo("2018010900006");
	        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
	        Date preDate = sim.parse("20180108");
	        Date tranDate = sim.parse("20180109");
	        batchParams.setPreDate(preDate);
	        batchParams.setTranDate(tranDate);
	        Object object=new Object();
	        applyAlipay.execute(batchParams, 0, object, object);
	    }
}
