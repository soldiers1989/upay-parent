

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.smoke.CheckTransferAmount;
import com.upay.commons.util.DateUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CheckTransferAmountTest {
	 private Logger logger = LoggerFactory.getLogger(CheckTransferAmountTest.class);

	    @Resource
	    private CheckTransferAmount checkTransferAmount;

	    private BatchParams batchParams;


	    @Test
	    public void testExecute() throws Exception {
	        batchParams = new BatchParams();
	        batchParams.setBatchNo("235465465456434373");
	        batchParams.setTranDate(DateUtil.parse("2017-07-20", "yyyy-MM-dd"));
	        checkTransferAmount.execute(batchParams, 0, new Object(), new Object());
	          
	    }
}
