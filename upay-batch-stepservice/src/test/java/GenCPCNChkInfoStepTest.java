
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.cpcn.GenCPCNChkInfoStep;
import com.upay.batch.stepservice.chk.cpcn.GetCPCNChkFileStep;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class GenCPCNChkInfoStepTest {
	 private Logger logger = LoggerFactory.getLogger(GenCPCNChkInfoStepTest.class);

	    @Resource
	    private GenCPCNChkInfoStep genCPCNChkInfoStep;

	    private BatchParams batchParams;


	    @Test
	    public void testExecute() throws Exception {
	        batchParams = new BatchParams();
	        batchParams.setBatchNo("2354654654564512");
	        batchParams.setTranDate(DateUtil.parse("2017-07-21", "yyyy-MM-dd"));
	        batchParams.setPreDate(DateUtil.parse("2017-07-21", "yyyy-MM-dd"));
	        genCPCNChkInfoStep.execute(batchParams, 0, null	, null);
	    }
}
