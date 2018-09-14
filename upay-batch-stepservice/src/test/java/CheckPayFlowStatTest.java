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

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CheckPayFlowStatTest {
    private Logger logger = LoggerFactory.getLogger(CheckPayFlowStatTest.class);
    @Resource(name="CheckPayFlowStat")
    CheckPayFlowStat check;
    @Resource(name="ContinueOrderService")
    ContinueOrderService con;
    
    @Test
    public void testExecute() throws Exception {
        int pageSize=1000;
        BatchParams batchParams=new BatchParams();
        Object object=new Object();
        int num=check.getTotalResult(batchParams, object);
        List<PayFlowListPo> payList= check.getDataList(batchParams, 0, pageSize, object);
        Set<String> orderList=null;
        if(batchParams.getParameter().get("orderNo")!=null){
            orderList=(Set<String>) batchParams.getParameter().get("orderNo");
        }
        
        for(int i=0;i<payList.size();i++){
        	if(payList.get(i).getTransSubSeq().equals("20180914000011703254")){
        		check.execute(batchParams, i, payList.get(i), object);
        	}
        }
    }
}
