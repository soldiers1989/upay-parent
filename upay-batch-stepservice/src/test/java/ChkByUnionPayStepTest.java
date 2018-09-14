import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.unionmer.ChkByUnionPayStepToMer;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ChkByUnionPayStepTest {

    private Logger logger = LoggerFactory.getLogger(ChkByUnionPayStepTest.class);

//    @Resource(name = "ChkByUnionPayStep")
//    ChkByUnionPayStep downFile;
    @Resource(name = "ChkByUnionPayStepToMer")
 ChkByUnionPayStepToMer downFile;

    @Test
    public void testExecute() throws Exception {
        int pageSize = 50;
        BatchParams batchParams = new BatchParams();
        batchParams.setExecutionType(ExecutionType.CONTINUE);
      /*  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date parse = simpleDateFormat.parse("2017/01/19");*/

      /*  batchParams.setPreDate(parse);
        batchParams.setTranDate(parse);*/
        batchParams.setBatchNo("171128000000969326");
        List<Object> objectList = downFile.getObjectList(batchParams);
        for (int j = 0; j < objectList.size(); j++) {
            List<ChkThirdDetailPo> payList = downFile.getDataList(batchParams, 0, pageSize, objectList.get(j));
            for (int i = 0; i < payList.size(); i++) {
                downFile.execute(batchParams, i, payList.get(i), objectList.get(j));
            }
        }
    }
}
