
import java.util.Date;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.schedule.GetCoreFile;
import com.upay.commons.util.DateUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by mask on 15/9/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class GetCoreFileTest {

	private Logger logger = LoggerFactory.getLogger(GetCoreFileTest.class);

	@Resource
	private GetCoreFile getCoreFile;

	private BatchParams batchParams;

	



	@Test
	public void testExecute() throws Exception {
		batchParams=new BatchParams();
		batchParams.setBatchNo("235465465456");
		batchParams.setPreDate(DateUtil.parse("2017-02-23", "yyyy-MM-dd"));
		getCoreFile.execute(batchParams, 1, batchParams, new Object());
	}
}