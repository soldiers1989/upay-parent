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
import com.upay.batch.stepservice.stl.secMer.SecMerStlStep;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SecMerStlStepTest {
	  private Logger logger = LoggerFactory.getLogger(SecMerStlStepTest.class);
	  
	    @Resource(name="SecMerStlStep")
	    SecMerStlStep secMerStlStep;
	    
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        batchParams.setBatchNo("201708011054");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		    Date format = simpleDateFormat.parse("2017/09/06");
	        batchParams.setTranDate(format);
	       	List<StlBookPo> objectList = secMerStlStep.getObjectList(batchParams);
	       	for (StlBookPo stlBookPo : objectList) {
//	       		if("MER2017000119".equals(stlBookPo.getMerNo())){
	       			secMerStlStep.execute(batchParams, 0, null, stlBookPo);
//	       		}
			}
	    }
}
