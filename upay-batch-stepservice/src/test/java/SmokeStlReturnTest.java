import java.util.ArrayList;
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
import com.upay.batch.stepservice.chk.smoke.SmokeStlReturn;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.SmokeStlPo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SmokeStlReturnTest {
	 private Logger logger = LoggerFactory.getLogger(SmokeStlReturnTest.class);

	    @Resource
	    private SmokeStlReturn smokeStlReturn;

	    private BatchParams batchParams;


	    @Test
	    public void testExecute() throws Exception {
	        batchParams = new BatchParams();
	        batchParams.setBatchNo("235465465456");
	        batchParams.setTranDate(DateUtil.parse("2017-02-23", "yyyy-MM-dd"));
	        smokeStlReturn.execute(batchParams, 0, new Object(), new Object());
	    }
}
