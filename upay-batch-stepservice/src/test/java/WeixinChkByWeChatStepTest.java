import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.atwechat.ChkByWeChatStepAT;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkThirdDetailPo;
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
public class WeixinChkByWeChatStepTest {
    
//    private BatchParams batchParams;
    @Resource
    //private ChkByWeChatStep weixin;
    private ChkByWeChatStepAT weixin;

    @Test
    public void testExecute() throws Exception {
        BatchParams b=new BatchParams();
        b.setBatchNo("1342206555");
        b.setTranDate(new Date());
        b.setPreDate(DateUtil.getPreDate(new Date()));
        List<ChkThirdDetailPo> chkList=weixin.getDataList(b, 0, 1, new Object());
        for(int i=0;i<chkList.size();i++){            
            weixin.execute(b, i, chkList.get(i), new Object());
        }
    }
}
