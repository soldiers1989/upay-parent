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
import com.upay.batch.stepservice.schedule.UserUnlockBatch;
import com.upay.dao.po.usr.UsrPwdlockBookPo;


/**
 * 用户登录密码临时锁定解锁
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class UserUnlockBatchTest {

    private Logger logger = LoggerFactory.getLogger(UserUnlockBatchTest.class);

    @Resource
    private UserUnlockBatch userUnlockBatch;

    private BatchParams batchParams;


    @Test
    public void testExecute() throws Exception {
        List<UsrPwdlockBookPo> list = new ArrayList<UsrPwdlockBookPo>();
        batchParams = new BatchParams();
        batchParams.setBatchNo("235465465456");
        batchParams.setPreDate(new Date());
        // int i = userUnlockBatch.getTotalResult(batchParams, "");
        list = userUnlockBatch.getDataList(batchParams, 0, 10, "");
        if (list != null && list.size() != 0) {
            for (UsrPwdlockBookPo usrPwdlockBookPo : list) {
                userUnlockBatch.execute(batchParams, 0, usrPwdlockBookPo, new Object());
            }
        } else {
            logger.debug("暂时没有登录密码被锁定的用户");
        }
    }
}