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
import com.upay.batch.stepservice.schedule.NotifyMerAfterPay;
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
public class WeixinDownTest {
    
    private BatchParams batchParams;
    @Resource
    private ApplyWeChatChkFileStep weixin;
    
    @Test
    public void testExecute() throws Exception {
        SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
        Calendar a=Calendar.getInstance();
        Date now=new Date();
        a.setTime(now);
        //a.add(Calendar.DAY_OF_YEAR, -4);
        now=a.getTime();
        now=sim.parse(sim.format(now));
        Calendar ca=Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.DAY_OF_YEAR,-1);
        Date preDate=ca.getTime();
        Object obj=new Object();
        batchParams=new BatchParams();
        batchParams.setPreDate(preDate);
        batchParams.setTranDate(now);
        batchParams.setBatchNo("1342206556");
        weixin.execute(batchParams, 1, obj, obj);
    }



}
