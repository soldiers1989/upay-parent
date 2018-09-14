import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.schedule.ConfirmReceipt;
import com.upay.batch.stepservice.schedule.ContinueOrderService;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 用户登录密码临时锁定解锁
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ContinueOrderServiceTest {

    private Logger logger = LoggerFactory.getLogger(ContinueOrderServiceTest.class);

    @Resource
    private ContinueOrderService continueOrderService;

    private BatchParams batchParams;


    @Test
    public void testExecute() throws Exception {
        List<PayOrderListPo> list = new ArrayList<PayOrderListPo>();
        batchParams = new BatchParams();
        batchParams.setBatchNo("2354654654561");
        batchParams.setPreDate(DateUtil.parse("2017-07-20", "yyyy-MM-dd"));
        List<PayOrderListPo> dataList = continueOrderService.getDataList(batchParams, 0, 0, null);
        if(null!=dataList){
        	for(PayOrderListPo orderList:dataList){
        		System.out.println(orderList.getOrderNo());
        		if(orderList.getOrderNo().equals("UPAY201808210009514100")){
        			continueOrderService.execute(batchParams, 0, orderList, null);
        		}
        		
        		
        	}
        }
    }
}