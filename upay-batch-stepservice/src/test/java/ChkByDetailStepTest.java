
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.cpcn.ChkByCPCNStep;
import com.upay.batch.stepservice.chk.cpcn.ChkByDetailStep;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChkByDetailStepTest {
	 private Logger logger = LoggerFactory.getLogger(ChkByDetailStepTest.class);

	    @Resource
	    private ChkByDetailStep chkByDetailStep;

	    private BatchParams batchParams;


	    @Test
	    public void testExecute() throws Exception {
	        batchParams = new BatchParams();
	        batchParams.setBatchNo("2354654654564521");
	        batchParams.setTranDate(DateUtil.parse("2017-07-21", "yyyy-MM-dd"));
	        batchParams.setPreDate(DateUtil.parse("2017-07-20", "yyyy-MM-dd"));
	        List<ChkInfoPo> objectList = chkByDetailStep.getObjectList(batchParams);
	        for(ChkInfoPo chkInfoPo:objectList){
	        	List<PayFlowListPo> dataList = chkByDetailStep.getDataList(batchParams, 0, 100, chkInfoPo);
		        for(PayFlowListPo po:dataList){
		        	chkByDetailStep.execute(batchParams, 0, po	, chkInfoPo);
		        }
	        }
	    }
}
