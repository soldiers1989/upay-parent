import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.atwechat.GetWeChatChkFileStepAT;
import com.upay.batch.stepservice.chk.atwechat.bean.ChkWechatBeanAT;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 */

/**
 * @author shang
 * 2016年12月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class WeixinGetWeChatChkFileStepTest {
    
    private BatchParams batchParams;
    @Resource
    //private GetWeChatChkFileStep weixin;
    private GetWeChatChkFileStepAT weixin;

    @Test
    public void testExecute() throws Exception {
        BatchParams param=new BatchParams();
        param.setBatchNo("1342206555");
        Date now = new Date();
        SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
        now=sim.parse(sim.format(now));
        param.setTranDate(now);
        param.setPreDate(DateUtil.getPreDate(new Date()));
        param.getParameter().put(DataBaseConstants_BATCH.WECHAT_USE_AT_ROUTE,false);
    //    List<ChkInfoPo> chkList=weixin.getObjectList(param);
      //  for(int j=0;j<chkList.size();j++){
            List<ChkWechatBeanAT> beanList=weixin.getDataList(param, 0, 150, null);
            for(int i=0;i<beanList.size();i++){
                weixin.execute(param, i, beanList.get(i),null);
            }
      //  }
    }
}
