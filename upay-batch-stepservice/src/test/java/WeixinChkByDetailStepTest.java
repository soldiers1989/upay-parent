import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.atwechat.ChkByDetailStepAT;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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
public class WeixinChkByDetailStepTest {
    
//    private BatchParams batchParams;
    @Resource
   // private ChkByDetailStep weixin;
    private ChkByDetailStepAT weixin;

    @Test
    public void testExecute() throws Exception {
        BatchParams b=new BatchParams();
        b.setBatchNo("1342206556");
        b.setTranDate(new Date());
        b.setPreDate(DateUtil.getPreDate(new Date()));
        //List<ChkInfoPo> chkList=weixin.getObjectList(b);
       //for(int i=0;i<chkList.size();i++){
            List<PayFlowListPo> flowList=weixin.getDataList(b, 0, 50,null);
            for(int j=0;j<flowList.size();j++){
                weixin.execute(b, j, flowList.get(j),null);
            }
       // }
    }
}
