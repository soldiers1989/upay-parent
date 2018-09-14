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
import com.upay.batch.stepservice.chk.smoke.QueryProcessingTransfer;
import com.upay.batch.stepservice.chk.smoke.SmokeTransferAccount;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.SmokeStlDetailPo;
import com.upay.dao.po.pay.SmokeStlPo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class QueryProcessingTransferTest {
	 private Logger logger = LoggerFactory.getLogger(QueryProcessingTransferTest.class);

	    @Resource
	    private QueryProcessingTransfer queryProcessingTransfer;

	    private BatchParams batchParams;


	    @Test
	    public void testExecute() throws Exception {
	        List<SmokeStlDetailPo> list = new ArrayList<SmokeStlDetailPo>();
	        batchParams = new BatchParams();
	        batchParams.setBatchNo("23546546545623222");
	        batchParams.setTranDate(DateUtil.parse("2017-07-24", "yyyy-MM-dd"));
	        int totalResult = queryProcessingTransfer.getTotalResult(batchParams, null);
	        list = queryProcessingTransfer.getDataList(batchParams, 0, 10, "");
	        System.out.println(list);
	        if (list != null && list.size() != 0) {
	            for (SmokeStlDetailPo accStpBookPo : list) {
	            	queryProcessingTransfer.execute(batchParams, 0, accStpBookPo, new Object());
	            }
	        }
	        else {
	            logger.debug("暂时没有需要解付的账户");
	        }
	    }
}
