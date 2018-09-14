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
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 用户登录密码临时锁定解锁
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ConfirmReceiptTest {

    private Logger logger = LoggerFactory.getLogger(ConfirmReceiptTest.class);

    @Resource
    private ConfirmReceipt confirmReceipt;

    private BatchParams batchParams;


    @Test
    public void testExecute() throws Exception {
        List<PayOrderListPo> list = new ArrayList<PayOrderListPo>();
        batchParams = new BatchParams();
        batchParams.setBatchNo("235465465456");
        batchParams.setPreDate(new Date());
        int i = confirmReceipt.getTotalResult(batchParams, "");
        list = confirmReceipt.getDataList(batchParams, 0, 10, "");
        if (list != null && list.size() != 0) {
            for (PayOrderListPo payOrderListPo : list) {
                confirmReceipt.execute(batchParams, 0, payOrderListPo, new Object());
            }
        } else {
            logger.debug("暂时没有需要自动确认收货的订单");
        }
    }
}