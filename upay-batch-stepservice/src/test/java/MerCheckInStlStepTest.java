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
import com.upay.dao.po.mer.MerBaseInfoPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MerCheckInStlStepTest {
	  private Logger logger = LoggerFactory.getLogger(GenMerChkListTest.class);
	  
	    @Resource(name="MerCheckInStlStep")
	    MerCheckInStlStep merStep;
	    
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        batchParams.setBatchNo("87564231123");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		    Date format = simpleDateFormat.parse("2017/05/14");
//		    Date preDate = simpleDateFormat.parse("2017/03/15");
//		    batchParams.setPreDate(preDate);
	        batchParams.setTranDate(format);
	       	List<MerBaseInfoPo> objectList = merStep.getObjectList(batchParams);
	       	for (MerBaseInfoPo merBaseInfoPo : objectList) {
//	       		int num=merStep.getTotalResult(batchParams, merBaseInfoPo);
//	       		List<Object> payList= merStep.getDataList(batchParams, 0, 0, merBaseInfoPo);
//	       		for(int i=0;i<payList.size();i++){
	       		if("MER2017000119".equals(merBaseInfoPo.getMerNo()))
	       			merStep.execute(batchParams, 0, null, merBaseInfoPo);
//	       		}
			}
//	    	File file = new File("D:\\A.txt");
//	    	if (!file.exists()) {
//	    		throw new BatchException("核心对账文件下载失败!");
//	        }
//	    	System.out.println("111");
	    }
}
