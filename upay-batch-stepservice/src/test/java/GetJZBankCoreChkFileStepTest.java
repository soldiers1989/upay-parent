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
import com.upay.batch.stepservice.chk.core.GetJZBankCoreChkFileStep;
import com.upay.dao.po.chk.ChkHostFilePo;
import com.upay.dao.po.chk.ChkInfoPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class GetJZBankCoreChkFileStepTest {

	  private Logger logger = LoggerFactory.getLogger(GetJZBankCoreChkFileStepTest.class);
	  
	    @Resource(name="GetJZBankCoreChkFileStep")
	    GetJZBankCoreChkFileStep getFile;
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        batchParams.setExecutionType(ExecutionType.CONTINUE);
	        batchParams.setBatchNo("2017031300005");
	        Calendar ca = Calendar.getInstance();
	        ca.setTime(new Date());
	        ca.add(Calendar.HOUR, -24);
	        batchParams.setPreDate(ca.getTime());
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        String format = simpleDateFormat.format(new Date());
	        Date parse = simpleDateFormat.parse(format);
	        batchParams.setTranDate(parse);
	        ChkInfoPo object=new ChkInfoPo();
	        List<ChkInfoPo> objectList = getFile.getObjectList(batchParams);
	        for (ChkInfoPo chkInfoPo : objectList) {
	        	int num=getFile.getTotalResult(batchParams, chkInfoPo);
	        	List<ChkHostFilePo> payList= getFile.getDataList(batchParams, 0, num, chkInfoPo);
	        	for(int i=0;i<payList.size();i++){
	        		getFile.execute(batchParams, i, payList.get(i), chkInfoPo);
	        	}
	        	getFile.updateObject(batchParams, object);
			}
	    }
}
