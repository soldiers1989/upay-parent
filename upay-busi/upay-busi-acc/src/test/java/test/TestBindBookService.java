package test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.busi.acc.service.dto.BindBookDto;
import com.upay.busi.acc.service.dto.UniqueBindAccCheckDto;
import com.upay.busi.acc.service.impl.AccOpenStatusService;
import com.upay.busi.acc.service.impl.BindBookService;
import com.upay.busi.acc.service.impl.CheckUserAccountStatService;
import com.upay.busi.acc.service.impl.UniqueBindAccCheckService;
import com.upay.dao.po.usr.UsrBaseInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestBindBookService {

	@Resource
	BindBookService bindBookService;
    
    @Test
    public void execute() throws Exception{
    	BindBookDto bindBookDto=new BindBookDto();
    	
    	bindBookDto.setvAcctNo("60809000000000099");// 虚拟账户账号
    	bindBookDto.setBindAcctType("11");// 绑定账户类型   11借记卡 22
    	bindBookDto.seteBindBankFlag("1");//
    	bindBookDto.setChnlId("01");// 绑定渠道
    	bindBookDto.seteBindOpenCode("刘兵");// 绑定账户开户行名
    	bindBookDto.seteBindBankCode("1002");// 绑定账户行号
    	bindBookDto.seteBindBankName("招商银行");// 绑定账户开户行名
    	bindBookDto.seteBindAcctNo("6214830284711011");// 电子账户绑定账户账号
    	bindBookDto.setTransCode("SI_ACC00001");// 电子账户绑定账户账号
    	bindBookDto.setCnapsBankNo("1001");
		// 绑定账户银行类别---------第三方渠道传值
    	bindBookDto.seteBindFlag("1");// 绑卡方式---------第三方渠道传值
    	bindBookDto.setThirdAuthChnl("1");// 第三方鉴权渠道
    	bindBookDto.setTransferVerifyAmt(BigDecimal.ONE);// 打款验证金额
		
    	bindBookDto=bindBookService.execute(bindBookDto, null);
    }
}
