import java.text.SimpleDateFormat;
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
import com.upay.batch.stepservice.stl.secMer.SecMerCheckInStlStep;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SecMerCheckInStlStepTest {
	  private Logger logger = LoggerFactory.getLogger(SecMerCheckInStlStepTest.class);
	  
	    @Resource(name="SecMerCheckInStlStep")
	    SecMerCheckInStlStep secMerStep;
	    
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        batchParams.setBatchNo("2017080110517");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		    Date format = simpleDateFormat.parse("2017/09/05");
	        batchParams.setTranDate(format);
	       	List<MerAcctInfoPo> objectList = secMerStep.getObjectList(batchParams);
	       	for (MerAcctInfoPo merBaseInfoPo : objectList) {
	       		if("MER2017000119".equals(merBaseInfoPo.getParentMerNo())){
	       			secMerStep.execute(batchParams, 0, null, merBaseInfoPo);
	       		}
			}
	    }
}
