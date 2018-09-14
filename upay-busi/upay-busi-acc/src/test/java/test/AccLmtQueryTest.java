package test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccLmtQueryDto;
import com.upay.busi.acc.service.impl.AccLmtQueryService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class AccLmtQueryTest {

    @Resource
    private AccLmtQueryService accLmtQueryService;


    @Test
    public void execute() throws Exception {
    	AccLmtQueryDto accLmtQueryDto = new AccLmtQueryDto();
    	accLmtQueryDto.setSysDate(new Date());
    	accLmtQueryDto.setPendTransCode("SI_ACC1001");
    	accLmtQueryDto.setUserCertLevel("02");
    	accLmtQueryDto.setChnlId("02");
    	accLmtQueryDto.setvAcctNo("20160809000000000099");
    	accLmtQueryDto.setUserId("UR000000000009");
    	accLmtQueryDto.setDcFlag("N");
    	accLmtQueryDto.setLmtType("01");
    	accLmtQueryDto.setAcctType("02");
    	accLmtQueryService.execute(accLmtQueryDto, null);

    }
}
