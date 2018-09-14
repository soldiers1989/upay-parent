import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.unionmer.GetUnionPaySinglePayChkFileStepToMer;
import com.upay.batch.stepservice.chk.unionmer.bean.ChkUnionFlowBeanMer;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class GetUnionPaySinglePayChkFileStepTest {
	
	  private Logger logger = LoggerFactory.getLogger(GetUnionPaySinglePayChkFileStepTest.class);
	  
	 /*   @Resource(name="GetUnionPaySinglePayChkFileStep")
		GetUnionPaySinglePayChkFileStep downFile;*/

	@Resource(name="GetUnionPaySinglePayChkFileStepToMer")
	GetUnionPaySinglePayChkFileStepToMer downFile;
	    @Test
	    public void testExecute() throws Exception {
	        int pageSize=50;
	        BatchParams batchParams=new BatchParams();
	      //  batchParams.setExecutionType(ExecutionType.CONTINUE);
	       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	        //Date parse = simpleDateFormat.parse("2018/01/24");
	      //  batchParams.setPreDate(parse);
	      ///  batchParams.setTranDate(parse);
	      // batchParams.setBatchNo("180124000000969375");
			//batchParams.setExecutionType(ExecutionType.CONTINUE);
			batchParams.setBatchNo("171128000000969326");
			batchParams.setPreDate(DateUtil.getPreDate(new Date()));
			batchParams.setTranDate(new Date());
			batchParams.getParameter().put("outPutZipDirectory","D:\\DEV_ENV\\config-file\\file\\20180620");
	        //对账文件地址
	        //batchParams.getParameter().put("chkFileName","F:\\DEV_ENV\\config-file\\certs\\INN17011988ZM_898111472980125");
			Object object = new Object();
			int num=downFile.getTotalResult(batchParams, null);
			List<ChkInfoPo>objectList = downFile.getObjectList(batchParams);
	        for (int j = 0; j < objectList.size(); j++) {
				List<ChkUnionFlowBeanMer> payList= downFile.getDataList(batchParams, j, pageSize,objectList.get(j));
	        	for(int i=0;i<payList.size();i++){
	        		downFile.execute(batchParams, i, payList.get(i), objectList.get(j));
	        	}
			}
	    }
}
