import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.upay.batch.stepservice.chk.unionmer.DownloadUnionPayChkFileToMer;
import com.upay.commons.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 下载银联对账文件
 *
 * @author NULL
 * @version 1.0
 * @date 2017-12-08
 * @since 2017-12-08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class DownloadUnionPayChkFileTest {

    private Logger logger = LoggerFactory.getLogger(DownloadUnionPayChkFileTest.class);

    /*机构*/
        /*@Resource(name="DownloadUnionPayChkFile")
        DownloadUnionPayChkFile downFile;*/
	    /*商户*/
    @Resource(name = "DownloadUnionPayChkFileMer")
    DownloadUnionPayChkFileToMer downFile;

    @Test
    public void testExecute() throws Exception {
        int pageSize = 10;
        BatchParams batchParams = new BatchParams();
        batchParams.setExecutionType(ExecutionType.CONTINUE);
        batchParams.setBatchNo("171128000000969326");
        batchParams.setPreDate(DateUtil.getPreDate(new Date()));
        batchParams.setTranDate(new Date());
        List<Map<String, String>> objectList = downFile.getObjectList(batchParams);
        for (int j = 0; j < objectList.size(); j++) {
            List<Object> payList = downFile.getDataList(batchParams, 0, pageSize, objectList.get(j));
            for (int i = 0; i < payList.size(); i++) {
                downFile.execute(batchParams, i, payList.get(i), objectList.get(j));
            }
        }
    }
}
