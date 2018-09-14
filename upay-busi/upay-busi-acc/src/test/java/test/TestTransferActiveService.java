package test;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.busi.acc.service.dto.TransferActiveDto;
import com.upay.busi.acc.service.dto.UniqueBindAccCheckDto;
import com.upay.busi.acc.service.impl.AccOpenStatusService;
import com.upay.busi.acc.service.impl.CheckUserAccountStatService;
import com.upay.busi.acc.service.impl.TransferActiveService;
import com.upay.busi.acc.service.impl.UniqueBindAccCheckService;
import com.upay.dao.po.usr.UsrBaseInfoPo;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 下午4:32:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/spring/**/*.xml")
public class TestTransferActiveService {

	@Resource
	TransferActiveService transferActiveService;
    
    @Test
    public void execute() throws Exception{
    	TransferActiveDto transferActiveDto=new TransferActiveDto();
    	transferActiveDto.setUserId("UR000000000088");// 获取userId
    	transferActiveDto.seteBindBankCode("1004");// 待绑定卡行号（相当于机构）
    	transferActiveDto.seteBindAcctNo("6214830284711011");// 待绑定卡号
    	transferActiveDto.setTransCode("SI_ACC10001");// 绑定账户行名
    	transferActiveDto.setSysTime(new Date());
    	transferActiveDto.setCnapsBankNo("1002");// 绑定卡的行号
    	
    	
    	transferActiveDto.seteBindFlag("1");// 绑卡方式----------第三方渠道传值1
    	transferActiveDto.setThirdAuthChnl("003");// 第三方鉴权渠道
    	transferActiveDto.setTransAmt(BigDecimal.ZERO);// 打款验证金额
    	//transferActiveDto.seteOpenFlag("");// 开户原因
    	transferActiveDto.seteBindBankFlag("03");// 本行他行标志
		
    	transferActiveDto = transferActiveService.execute(transferActiveDto, null);
    }
}
