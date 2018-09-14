package alipay.chk;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.alipay.bean.ChkAlipayBean;
import com.upay.batch.stepservice.chk.atalipay.GetAlipayChkFileStepAT;
import com.upay.batch.stepservice.chk.atalipay.bean.ChkAlipayBeanAT;
import com.upay.commons.util.DateUtil;
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
public class GetAlipayChkFileStepTest {
	 private Logger logger = LoggerFactory.getLogger(GetAlipayChkFileStepTest.class);

	    @Resource
	   // private GetAlipayChkFileStep getAlipayChkFileStep;
	    private GetAlipayChkFileStepAT getAlipayChkFileStep;

	    private BatchParams batchParams;


	    @Test
	    public void testExecute() throws Exception {
	    	    batchParams = new BatchParams();
		        batchParams.setBatchNo("2018010900006");
		        batchParams.setTranDate(DateUtil.parse("2018-07-17", "yyyy-MM-dd"));
		        batchParams.setPreDate(DateUtil.parse("2018-07-16", "yyyy-MM-dd"));
		      //  getAlipayChkFileStep.getTotalResult(batchParams,new ChkInfoPo());
		     //   List<ChkInfoPo> chkInfoPo=getAlipayChkFileStep.getObjectList(batchParams);
		      //  if(chkInfoPo.size()>0){
		        	//for(ChkInfoPo po:chkInfoPo){
		        		List<ChkAlipayBeanAT> dataList = getAlipayChkFileStep.getDataList(batchParams, 0, 60, null);
		        		if(dataList!=null&&dataList.size()>0){
		        			for(ChkAlipayBeanAT data:dataList){
		        				getAlipayChkFileStep.execute(batchParams, 0, data, null);
		        			}
		        		}
		        	//}
		      // }
	    }
}
