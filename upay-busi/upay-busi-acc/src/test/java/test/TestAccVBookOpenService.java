package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.acc.service.dto.AccVBookOpenDto;
import com.upay.busi.acc.service.impl.AccVBookOpenService;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestAccVBookOpenService {

	@Resource
	AccVBookOpenService accVBookOpenService;
    
    @Test
    public void execute() throws Exception{
    	
		 AccVBookOpenDto dto=new AccVBookOpenDto();
		 dto.setUserId("13265465");// 用户号
		 dto.seteAcctCertLevel("1");// 用户实名认证等级
		 //绑定账户类型，软需中写的没有用到
		 dto.seteBindBankName("农业银行");// 绑定账户行名----------第三方渠道传值
		 dto.seteOpenFlag("1");// 开户原因
		 dto.seteBindFlag("1");// 绑卡方式----------第三方渠道传值1
		 dto.setThirdAuthChnl("0003");// 第三方鉴权渠道
		 
		 
		 dto = accVBookOpenService.execute(dto,null);
		 System.out.println(dto.geteCardNo());
    }
}
