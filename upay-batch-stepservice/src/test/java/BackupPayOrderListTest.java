import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.clearing.fee.FeeStatistics;
import com.upay.batch.stepservice.migration.BackupPayOrderList;
import com.upay.commons.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class BackupPayOrderListTest {

    private Logger logger = LoggerFactory.getLogger(BackupPayOrderListTest.class);

    @Resource(name = "BackupPayOrderList")
    BackupPayOrderList backupPayOrderList;
    @Resource(name = "FeeStatistics")
    FeeStatistics feeStatistics;

    @Test
    public void testExecute() throws Exception {
        int pageSize = 50;
        BatchParams batchParams = new BatchParams();
        batchParams.setExecutionType(ExecutionType.CONTINUE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//	        Date startDate = simpleDateFormat.parse("2017/3/14");
//	        Date endDate = simpleDateFormat.parse("2017/3/20");
////	        batchParams.getParameter().put("startDate",startDate);
////	        batchParams.getParameter().put("endDate",endDate);
        batchParams.setBatchNo("180124000000969379");
        batchParams.setTranDate(DateUtil.parse("2018-08-29", "yyyy-MM-dd"));
//			List<PayOrderListHisPo> objectList = backupPayOrderList.getObjectList(batchParams);
//			backupPayOrderList.execute(batchParams,0,null,null);
//			//			for (int j = 0; j < objectList.size(); j++) {
        List<Map<String, Object>> dataList = feeStatistics.getDataList(batchParams, 0, pageSize, null);
        for (int i = 0; i < dataList.size(); i++) {
            feeStatistics.execute(batchParams, i, dataList.get(i), null);
        }
//			}


    }
}
