import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.schedule.NotifyMerAfterPay;
import com.upay.dao.po.mer.MerNotifiyPo;

/**
 * 
 */

/**
 * @author shang
 * 2016年12月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class NotifyTest {
    
    private BatchParams batchParams;
    @Resource
    private NotifyMerAfterPay notify;
    
    @Test
    public void testExecute() throws Exception {
        Object obj=new Object();
        batchParams=new BatchParams();
        batchParams.setBatchNo("235465465456");
        batchParams.setPreDate(new Date());
        List<MerNotifiyPo> data=notify.getDataList(batchParams, 0, 10, obj);
        
        for(int i=0;i<data.size();i++){            
            notify.execute(batchParams, i, data.get(i), obj);
        }
    }
}
