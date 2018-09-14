import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.unionmer.ChkByDetailUnionStepToMer;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
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
public class ChkByDetailUnionStepTest {

    private Logger logger = LoggerFactory.getLogger(ChkByDetailUnionStepTest.class);

    /*@Resource(name = "ChkByDetailUnionStep")
    ChkByDetailUnionStep downFile;*/
    @Resource(name = "ChkByDetailUnionStepToMer")
    ChkByDetailUnionStepToMer downFile;

    @Test
    public void testExecute() throws Exception {
        int pageSize = 50;
        BatchParams batchParams = new BatchParams();
        batchParams.setExecutionType(ExecutionType.CONTINUE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date parse = simpleDateFormat.parse("2018/01/24");
        batchParams.setPreDate(parse);
        batchParams.setBatchNo("171128000000969326");
        List<ChkInfoPo> objectList = downFile.getObjectList(batchParams);
        for (int j = 0; j < objectList.size(); j++) {
            List<PayFlowListPo> payList = downFile.getDataList(batchParams, 0, pageSize, objectList.get(j));
            for (int i = 0; i < payList.size(); i++) {
                downFile.execute(batchParams, i, payList.get(i), objectList.get(j));
            }
        }
    }
}
