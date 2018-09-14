import java.io.File;
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
import com.pactera.dipper.batch.exception.BatchException;
import com.upay.batch.stepservice.clearing.mer.MerCheckInStlStep;
import com.upay.batch.stepservice.stl.mer.MerStlStep;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MertlStepTest {

	private Logger logger = LoggerFactory.getLogger(MertlStepTest.class);
	  
	    @Resource(name="MerStlStep")
	    MerStlStep merStep;
	    @Resource
		private ISequenceService sequenceService;
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		    Date format = simpleDateFormat.parse("2018/4/21");
//		    Date preDate = simpleDateFormat.parse("2017/03/15");
//		    batchParams.setPreDate(preDate);
	        batchParams.setTranDate(format);
	       	List<StlBookPo> objectList = merStep.getObjectList(batchParams);
	       	Object object = new Object();
	       	for(int i=0;i<objectList.size();i++){
	       		merStep.execute(batchParams, i, object, objectList.get(i));
	       	}
	    }
}
