import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.wechat.ApplyWeChatChkFileStep;
import com.upay.batch.stepservice.chk.wechat.GetWeChatChkFileStep;
import com.upay.batch.stepservice.chk.wechat.bean.ChkWechatBean;
import com.upay.batch.stepservice.clearing.mer.MerCheckInStlStep;
import com.upay.batch.stepservice.schedule.NotifyMerAfterPay;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.mer.MerNotifiyPo;

/**
 * 
 */

/**
 * @author shang
 * 2016年12月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class WWMerCheckInStlStepTest {
    
    @Resource
    private MerCheckInStlStep clean;
    
    @Test
    public void testExecute() throws Exception {
        SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
        BatchParams param=new BatchParams();
        param.setBatchNo("123456");
        param.setTranDate(sim.parse(sim.format(new Date())));
        List<MerBaseInfoPo> merList=clean.getObjectList(param);
        if(merList==null){
            System.out.println("空的");
            return;
        }
        Object obj=new Object();
        for(int i=0;i<merList.size();i++){
            clean.execute(param, i, obj, merList.get(i));
        }
    }
}
