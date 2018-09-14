package wechat.chk;


import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.chk.atwechat.ChkByDetailStepAT;
import com.upay.batch.stepservice.chk.atwechat.ChkByWeChatStepAT;
import com.upay.batch.stepservice.chk.atwechat.GetWeChatChkFileStepAT;
import com.upay.batch.stepservice.chk.atwechat.bean.ChkWechatBeanAT;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class WechatTest {

    @Resource
    private GetWeChatChkFileStepAT getWeChatChkFileStepAT;

    @Resource
    private ChkByWeChatStepAT chkByWeChatStepAT;

    @Resource
    private ChkByDetailStepAT chkByDetailStepAT;




    @Test
    public void testExecute() throws Exception {
        BatchParams b=new BatchParams();
        b.setBatchNo("1342206556");
        Date now = new Date();
        SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
        now=sim.parse(sim.format(now));
        b.setTranDate(now);
        b.setPreDate(DateUtil.getPreDate(new Date()));





        List<ChkWechatBeanAT> beanList=getWeChatChkFileStepAT.getDataList(b, 0, 150, null);
        for(int i=0;i<beanList.size();i++){
            getWeChatChkFileStepAT.execute(b, i, beanList.get(i),null);
        }





        List<ChkThirdDetailPo> chkList=chkByWeChatStepAT.getDataList(b, 0, 1, new Object());
        for(int i=0;i<chkList.size();i++){
            chkByWeChatStepAT.execute(b, i, chkList.get(i), null);
        }


        List<PayFlowListPo> flowList=chkByDetailStepAT.getDataList(b, 0, 50,null);
        for(int j=0;j<flowList.size();j++){
            chkByDetailStepAT.execute(b, j, flowList.get(j),null);
        }



    }


}
