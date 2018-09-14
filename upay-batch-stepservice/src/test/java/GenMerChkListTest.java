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
import com.upay.batch.stepservice.chk.mer.GenMerChkList;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class GenMerChkListTest {
	  private Logger logger = LoggerFactory.getLogger(GenMerChkListTest.class);
	  
	    @Resource(name="GenMerChkList")
	    GenMerChkList merStep;
	    
	    @Test
	    public void testExecute() throws Exception {
	        BatchParams batchParams=new BatchParams();
	        batchParams.setBatchNo("2017031300005123456");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		    Date format = simpleDateFormat.parse("2018/05/08");
		    Date preDate = simpleDateFormat.parse("2018/05/07");
	        batchParams.setTranDate(format);
	        batchParams.setPreDate(preDate);
	       	Object object = new Object();
	        int num=merStep.getTotalResult(batchParams, object);
	        List<PayOrderListPo> payList= merStep.getDataList(batchParams, 0, num, object);
	        for(int i=0;i<payList.size();i++){
	        	if(payList.get(i).getOrderNo().equals("UPAY201801170009453845")){
	        		merStep.execute(batchParams, i, payList.get(i), object);
		        	merStep.updateObject(batchParams, object);
	        	}
	        }
	    }
}
