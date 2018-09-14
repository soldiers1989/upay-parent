import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.core.DownloadJzBankCoreChkFileStep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class DownloadJzBankCoreChkFileStepTest {
	
	  private Logger logger = LoggerFactory.getLogger(DownloadJzBankCoreChkFileStepTest.class);
	  
	    @Resource(name="DownloadJzBankCoreChkFileStep")
	    DownloadJzBankCoreChkFileStep downFile;
	    @Test
	    public void testExecute() throws Exception {
	        int pageSize=10;
	        BatchParams batchParams=new BatchParams();
	        batchParams.setExecutionType(ExecutionType.CONTINUE);
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        Date parse = simpleDateFormat.parse("2017/04/16");
	        
	        batchParams.setPreDate(parse);
	        batchParams.setBatchNo("170417000001102892");
	        Map<String, String> map = new HashMap<String, String>();
	        List<Map<String, String>> objectList = downFile.getObjectList(batchParams);
	        for (int j = 0; j < objectList.size(); j++) {
	        	int num=downFile.getTotalResult(batchParams, objectList.get(j));
	        	List<Object> payList= downFile.getDataList(batchParams, 0, pageSize,objectList.get(j));
	        	for(int i=0;i<payList.size();i++){
	        		downFile.execute(batchParams, i, payList.get(i), objectList.get(j));
	        	}
			}
	    }
}
