import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.core.ChkByHostDetailStep;
import com.upay.batch.stepservice.chk.core.GetJZBankCoreChkFileStep;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkHostFilePo;
import com.upay.dao.po.chk.ChkInfoPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChkByHostDetailStepTest {
	  private Logger logger = LoggerFactory.getLogger(ChkByHostDetailStepTest.class);
	  
	    @Resource(name="ChkByHostDetailStep")
	    ChkByHostDetailStep ckStep;
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        batchParams.setExecutionType(ExecutionType.CONTINUE);
	        batchParams.setBatchNo("2017031300005");
	        List<Object> objectList = ckStep.getObjectList(batchParams);
	        for (Object object : objectList) {
	        	int num=ckStep.getTotalResult(batchParams, object);
	        	List<ChkHostDetailPo> payList= ckStep.getDataList(batchParams, 0, num, object);
	        	for(int i=0;i<payList.size();i++){
	        		ckStep.execute(batchParams, i, payList.get(i), object);
	        	}
			}
	    }
}
