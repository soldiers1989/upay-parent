package test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.PermissionCheckDto;
import com.upay.busi.acc.service.impl.PermissionCheckService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class TestPermissionCheckService {

    @Resource
    private PermissionCheckService permissionCheckService;


    @Test
    public void execute() throws Exception {
        PermissionCheckDto permissionCheckDto = new PermissionCheckDto();
        permissionCheckDto.setUserId("UR000000000368");
        permissionCheckDto.setAcctType("01");
        permissionCheckDto.setTransCode("SI_ACC8003");
        permissionCheckDto.setChnlId("02");
        permissionCheckDto.setAmtFlag("1");
        permissionCheckDto.setDcFlag("1");
        permissionCheckDto.setTransAmt(BigDecimal.valueOf(10));

        permissionCheckService.execute(permissionCheckDto, null);

    }
}
