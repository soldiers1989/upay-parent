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
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.core.ChkByDetailStep;
import com.upay.dao.po.pay.PayFlowListPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChkByDetailStepTestForHost {
	private Logger logger = LoggerFactory.getLogger(ChkByDetailStepTestForHost.class);
	  
    @Resource(name="ChkByDetailStep")
    ChkByDetailStep ckStep;
    @Test
    public void testExecute() throws Exception {
	    BatchParams batchParams=new BatchParams();
	    batchParams.setExecutionType(ExecutionType.CONTINUE);
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date format = simpleDateFormat.parse("2017/03/15");
	    batchParams.setPreDate(format);
	    String format1 = simpleDateFormat.format(new Date());
	    Date parse = simpleDateFormat.parse(format1);
	    batchParams.setTranDate(parse);
        batchParams.setBatchNo("2017031300005");
        List<Object> objectList = ckStep.getObjectList(batchParams);
        for (int j = 0; j < objectList.size(); j++) {
        	int num=ckStep.getTotalResult(batchParams, objectList.get(j));
        	List<PayFlowListPo> payList= ckStep.getDataList(batchParams, 0, num,objectList.get(j));
        	for(int i=0;i<payList.size();i++){
        		ckStep.execute(batchParams, i, payList.get(i), objectList.get(j));
        	}
        	ckStep.updateObject(batchParams, objectList.get(j));
		}
    }
}
