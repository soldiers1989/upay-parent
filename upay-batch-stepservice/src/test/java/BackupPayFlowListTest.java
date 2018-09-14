import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.migration.BackupPayFlowList;
import com.upay.batch.stepservice.migration.BackupPayOrderList;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowListHisPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListHisPo;
import com.upay.dao.po.pay.PayOrderListPo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class BackupPayFlowListTest {
	
	  private Logger logger = LoggerFactory.getLogger(BackupPayFlowListTest.class);
	  
	    @Resource(name="BackupPayFlowList")
		BackupPayFlowList backupPayFlowList;
	    @Test
	    public void testExecute() throws Exception {
		        BatchParams batchParams=new BatchParams();
//		        batchParams.setExecutionType(ExecutionType.CONTINUE);
//		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		        Date startDate = simpleDateFormat.parse("2017/3/14");
//		        Date endDate = simpleDateFormat.parse("2017/3/20");
//		        batchParams.getParameter().put("startDate",startDate);
//		        batchParams.getParameter().put("endDate",endDate);
		       batchParams.setBatchNo("180124000000969379");
		       batchParams.setTranDate(DateUtil.parse("2018-03-08", DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
//				List<PayOrderListHisPo> objectList = backupPayOrderList.getObjectList(batchParams);
				backupPayFlowList.execute(batchParams,0,null,null);
	    }
}
