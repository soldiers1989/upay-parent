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
import com.upay.batch.stepservice.schedule.RelieveFrzBatch;
import com.upay.dao.po.acc.AccFrzBookPo;


/**
 * 解冻
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class RelieveFrzBatchTest {

    private Logger logger = LoggerFactory.getLogger(RelieveFrzBatchTest.class);

    @Resource
    private RelieveFrzBatch relieveFrzBatch;

    private BatchParams batchParams;


    @Test
    public void testExecute() throws Exception {
        List<AccFrzBookPo> list = new ArrayList<AccFrzBookPo>();
        batchParams = new BatchParams();
        batchParams.setBatchNo("235465465456");
        batchParams.setPreDate(new Date());
        // int i = userUnlockBatch.getTotalResult(batchParams, "");
        list = relieveFrzBatch.getDataList(batchParams, 0, 10, "");
        if (list != null && list.size() != 0) {
            for (AccFrzBookPo accFrzBookPo : list) {
                relieveFrzBatch.execute(batchParams, 0, accFrzBookPo, new Object());
            }
        }
    }
}