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
import com.upay.batch.stepservice.chk.smoke.SmokeMerWithdraw;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.SmokeStlPo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SmokeMerWithdrawTest {
	private Logger logger = LoggerFactory.getLogger(SmokeMerWithdrawTest.class);

	@Resource
	private SmokeMerWithdraw smokeTransferAccount;

	private BatchParams batchParams;

	@Test
	public void testExecute() throws Exception {
		List<SmokeStlPo> list = new ArrayList<SmokeStlPo>();
		batchParams = new BatchParams();
		batchParams.setBatchNo("235465465456");
		batchParams.setTranDate(DateUtil.parse("2017-03-01", "yyyy-MM-dd"));
		int totalResult = smokeTransferAccount
				.getTotalResult(batchParams, null);
		smokeTransferAccount.execute(batchParams, 0, null, new Object());

	}
}
