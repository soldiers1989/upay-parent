import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodayRouteFee;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodaySubProfit;
import com.upay.batch.stepservice.stl.transferRouteFee.SyncProcessing;
import com.upay.batch.stepservice.stl.transferRouteFee.TransferRouteFee;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TransferRouteFeeTest {
    private Logger logger = LoggerFactory.getLogger(TransferRouteFeeTest.class);
    @Resource(name="TransferRouteFee")
    TransferRouteFee transferRouteFee;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setBatchNo("201225784522023");
    	batchParams.setTranDate(DateUtil.parse("2017-08-16", "yyyy-MM-dd"));
    	int totalResult = transferRouteFee.getTotalResult(batchParams, null);
    	List<StlRouteFeeBookPo> dataList = transferRouteFee.getDataList(batchParams, 0, 10, null);
    	for(StlRouteFeeBookPo payRoute:dataList){
    		transferRouteFee.execute(batchParams, 0, payRoute, null);
    	}
    }
}
