package com.upay.busi.mer;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upay.busi.mer.service.dto.CreateOrUpdateFirstMerDto;
import com.upay.busi.mer.service.impl.CreateOrUpdateFirstMerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class CreateOrUpdateFirstMerTest {
	@Resource
    private CreateOrUpdateFirstMerService createOrUpdateFirstMerService;
	
	@Test
    public void execute() throws Exception {
		CreateOrUpdateFirstMerDto dto=new CreateOrUpdateFirstMerDto();
		dto.setSysTime(new Date());
		dto.setSysDate(new Date());
		dto.setChnlId("02");
		dto.setUserNickName("静静");// 用户昵称
		dto.setStlAcctNo("6229807711600013706");//结算账户
		dto.setStlAcctType("11");//结算账户类型
		/** 账户类型,本行借记卡-11 */
	    /** 账户类型,贷记卡-12 */
	    /** 账户类型,个人结算户-13 */
	    /** 账户类型,第三方平台账户-14 */
	    /** 账户类型,单位结算户-15 */
	    /** 账户类型,内部账户-21 */
	    /** 账户类型,他行借记卡-22 */
	    /** 账户类型,本行对公账户-23 */
	    /** 账户类型,他行对公账户-24 */
		dto.setStlAcctName("王静");//结算账户户名
		dto.setBankId("308");//银行机构编号
		dto.setBankName("云南红塔银行");//银行名称
		dto.setMerName("小静宠物店");//商户名称
		dto.setMerWithoutPwdSign("1");//商户授权免密标志  0:非授权免密；1：授权免密；
		dto.setPayopenflag("0");//支付功能开通标志 0：开通； 1：不开通
		dto.setMerBusiType("01");//商户业务类型 01:电商平台 02:线下门店 03:金融机构
		dto.setContact("杨辉勇");//联系人姓名
		dto.setSex("1");// 性别   1	男性       2	女性
		dto.setContactTel("15202356326");//联系人电话
		dto.setContactMobile("15202356326");//联系人手机
		dto.setContactEmail("15202356326@139.com");//联系人邮件
		dto.setMerTel("15202356325");//商户电话
		dto.setMerFax("");//商户传真
		dto.setMerAddr("河北挖煤村");//商户地址
		dto.setMerPostalCode("652348");//商户邮编
		dto.setWebsiteCode("");//网站备案号
		dto.setWebsiteName("");//网站名称
		dto.setWebsiteDomain("");//网站域名
		dto.setWebsiteScop("庞物护理");//网站经营范围
		dto.setCompanyName("小静庞物");//公司名称
		dto.setEgalPersonName("王静");//企业法人代表姓名
		dto.setEgalPersonIdType("01");//企业法人代表身份证件类型
		dto.setEgalPersonIdNo("510843685265320259");//企业法人代表身份证件号码
		dto.setCompanyIdType("0");//企业证件类型
		dto.setCompanyIdNo("asdfasdfds");//企业证件号
		dto.setOrgDeptNo("asdfasdfdsf");//组织机构代码证号
		dto.setBusiLicenseId("/adsf");//营业执照号
		dto.setPromoterIphone("13800138000");
		dto.setPromoterName("兵兵业务");
		createOrUpdateFirstMerService.execute(dto, null);
	}
}
