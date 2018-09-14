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
import com.upay.batch.stepservice.schedule.MerStlException;
import com.upay.batch.stepservice.stl.mer.MerStlStep;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class MertlExceptionTest {

	private Logger logger = LoggerFactory.getLogger(MertlExceptionTest.class);
	  
	    @Resource(name="MerStlException")
	    MerStlException merStlException;
	    
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		    Date format = simpleDateFormat.parse("2017/6/19");
//		    Date preDate = simpleDateFormat.parse("2017/03/15");
//		    batchParams.setPreDate(preDate);
	        batchParams.setTranDate(format);
	        StlBookPo object = new StlBookPo();
	        int num=merStlException.getTotalResult(batchParams, object);
	        List<StlBookPo> payList= merStlException.getDataList(batchParams, 0, num, object);
	        for(int i=0;i<payList.size();i++){
	        	merStlException.execute(batchParams, i, payList.get(i), object);
	        }
	    }
}
