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
import com.upay.batch.stepservice.chk.core.ApplyCoreChkFileStep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ApplyCoreChkFileStepTest {
	
	  private Logger logger = LoggerFactory.getLogger(ApplyCoreChkFileStepTest.class);
	  
	    @Resource(name="ApplyCoreChkFileStep")
	    ApplyCoreChkFileStep applyCore;
	    
	    @Test
	    public void testExecute() throws Exception {
	        int pageSize=10;
	        BatchParams batchParams=new BatchParams();
	        batchParams.setExecutionType(ExecutionType.CONTINUE);
	        batchParams.setBatchNo("2017031300005");
	        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
	        Date parse2 = sim.parse("20170315");
	        batchParams.setPreDate(parse2);
	        
	        
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        String format = simpleDateFormat.format(new Date());
	        Date parse = simpleDateFormat.parse(format);
	        batchParams.setTranDate(parse);
	        Object object=new Object();
	        int num=applyCore.getTotalResult(batchParams, object);
	        List<Object> payList= applyCore.getDataList(batchParams, 0, pageSize, object);
	        for(int i=0;i<payList.size();i++){
	        	applyCore.execute(batchParams, i, payList.get(i), object);
	        }
	    }
}
