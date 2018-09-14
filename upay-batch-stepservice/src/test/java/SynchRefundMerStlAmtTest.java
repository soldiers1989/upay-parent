import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.extension.SPI;
import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.schedule.SynchRefundMerStlAmt;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 
 */

/**
 * @author shang
 * 2017年4月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class SynchRefundMerStlAmtTest {

    @Resource
    SynchRefundMerStlAmt sy;
    private BatchParams batchParams;
    private Object object=new Object();
    @Test
    public void execute(){
        int total=sy.getTotalResult(batchParams, object);
        List<PayOrderListPo> orderList=sy.getDataList(batchParams, 0, 100, object);
        if(orderList!=null){            
            for(int i=0;i<orderList.size();i++){
                sy.execute(batchParams, i, orderList.get(i), object);
            }
        }
    }
    
}
