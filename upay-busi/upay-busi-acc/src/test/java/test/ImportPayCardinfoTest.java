package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccBindQueryDto;
import com.upay.busi.acc.service.dto.AccInfoQueryDto;
import com.upay.busi.acc.service.impl.AccInfoQueryService;
import com.upay.busi.acc.service.impl.ImportPayCardInfoService;


/**
 * @author liyulong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class ImportPayCardinfoTest {

    @Resource
    private ImportPayCardInfoService importPayCardInfoService;


    @Test
    public void execute() throws Exception {
    	importPayCardInfoService.execute(new AccBindQueryDto(), null);

    }
}
