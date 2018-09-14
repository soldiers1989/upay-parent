import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.schedule.CheckPayFlowStat;
import com.upay.batch.stepservice.schedule.ContinueOrderService;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 
 */

/**
 * 补单
 * 
 * @author shang 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CheckPayFlowStatTest2 {
	private Logger logger = LoggerFactory
			.getLogger(CheckPayFlowStatTest2.class);
	@Resource(name = "CheckPayFlowStat")
	CheckPayFlowStat check;
	@Resource(name = "ContinueOrderService")
	ContinueOrderService con;

	@Test
	public void testExecute() throws Exception {
		int pageSize = 10;
		BatchParams batchParams = new BatchParams();
		// String
		// orderNo="UPAY201701190000004802, UPAY201701140000004745, UPAY201701190000004801, UPAY201701190000004806, UPAY201701190000004807, UPAY201701190000004824, UPAY201701140000004708, UPAY201701190000004804, UPAY201701160000004769, UPAY201701190000004823, UPAY201701140000004706, UPAY201701130000004688, UPAY201701190000004808, UPAY201701190000004782, UPAY201701160000004770, UPAY201701160000004772, UPAY201701190000004799, UPAY201701120000004621, UPAY201701140000004714, UPAY201701190000004816, UPAY201701190000004818, UPAY201701190000004814, UPAY201701190000004791, UPAY201701160000004755, UPAY201701190000004792, UPAY201701160000004754, UPAY201701190000004787, UPAY201701140000004697, UPAY201701140000004696";
		// String[] orderNoList=orderNo.split(",");
		// Set<String> set=new HashSet<String>();
		// for(String str:orderNoList){
		// set.add(str);
		// }
		// batchParams.getParameter().put("orderNo", set);
		Object object = new Object(); 
		// int num=con.getTotalResult(batchParams, object);
		// for(int j=0;j<num;j+=pageSize){
		List<PayOrderListPo> payList = con.getDataList(batchParams, 0,
				pageSize, object);
		for (int i = 0; i < payList.size(); i++) {
			con.execute(batchParams, i, payList.get(i), object);
		}
	}
	// }
}
