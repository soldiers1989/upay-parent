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
import com.upay.batch.stepservice.schedule.RelieveStpBatch;
import com.upay.dao.po.acc.AccStpBookPo;


/**
 * 解付
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class RelieveStpBatchTest {

    private Logger logger = LoggerFactory.getLogger(RelieveStpBatchTest.class);

    @Resource
    private RelieveStpBatch relieveStpBatch;

    private BatchParams batchParams;


    @Test
    public void testExecute() throws Exception {
        List<AccStpBookPo> list = new ArrayList<AccStpBookPo>();
        batchParams = new BatchParams();
        batchParams.setBatchNo("235465465456");
        batchParams.setPreDate(new Date());
        // int i = userUnlockBatch.getTotalResult(batchParams, "");
        list = relieveStpBatch.getDataList(batchParams, 0, 10, "");
        if (list != null && list.size() != 0) {
            for (AccStpBookPo accStpBookPo : list) {
                relieveStpBatch.execute(batchParams, 0, accStpBookPo, new Object());
            }
        } else {
            logger.debug("暂时没有需要解付的账户");
        }
    }
}