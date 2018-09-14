package com.upay.busi.usr;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.usr.service.dto.UserPayPwdFlagSearchDto;
import com.upay.busi.usr.service.impl.UserPayPwdFlagSearchService;


/**
 * 查询用户是否设置支付密码
 * 
 * @author liyulong
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class UserPayPwdFlagSearchTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
   // @Resource
   // private UserPayPwdFlagSearchService userPayPwdFlagSearchService;
    @Resource
    IDipperCached c;


    @Test
    public void execute() throws Exception {
    	System.out.println(c.keyExists("dsdfe"));
        //UserPayPwdFlagSearchDto userPayPwdFlagSearchDto = new UserPayPwdFlagSearchDto();
        //userPayPwdFlagSearchDto.setOrderNo("1");
        //userPayPwdFlagSearchDto.setChnlId("01");
        //userPayPwdFlagSearchDto.setUserId("1");
       // logger.debug("-------------------------");
        //userPayPwdFlagSearchService.execute(userPayPwdFlagSearchDto, null);
    }
}
