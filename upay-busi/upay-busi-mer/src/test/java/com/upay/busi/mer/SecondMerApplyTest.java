package com.upay.busi.mer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.core.IDipperCached;
import com.upay.busi.mer.service.dto.MerApplyDto;
import com.upay.busi.mer.service.impl.MerApplyService;
import com.upay.busi.mer.service.impl.SecondMerApplyService;

/**
 * 二级商户维护
 * 
 * @author yanzixiong
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class SecondMerApplyTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private SecondMerApplyService secondmerapplyservice;
    @Resource
    IDipperCached idipperCached;
    
    @Test
    public void execute() throws Exception {
    	MerApplyDto merapplydto = new MerApplyDto();
//    	merapplydto.setMerApplyNo("123476");
    	merapplydto.setMerName("测试1");
    	merapplydto.setContact("test2");
    	merapplydto.setContactTel("02888888888");
    	merapplydto.setContactMobile("13000000000");
    	merapplydto.setContactEmail("123456@qq.com");
    	merapplydto.setMerTel("02188888888");
    	merapplydto.setMerFax("123456");
    	merapplydto.setMerAddr("地址");
    	merapplydto.setMerPostalCode("000000");
    	merapplydto.setWebsiteCode("123456");
    	merapplydto.setWebsiteName("网站名称");
    	merapplydto.setWebsiteDomain("www.123.com");
    	merapplydto.setWebsiteScop("aaaa");
    	merapplydto.setCompanyName("公司名称");
    	merapplydto.setEgalPersonName("小王");
    	merapplydto.setEgalPersonIdType("01");
    	merapplydto.setEgalPersonIdNo("123456789987");
    	merapplydto.setCompanyIdType("02");
    	merapplydto.setCompanyIdNo("7894654");
    	merapplydto.setOrgDeptNo("SI392343424");
    	merapplydto.setBusiLicenseId("asdf2323423");
    	merapplydto.setUserId("UR000000000015");
    	merapplydto.setEmail("774643638@qq.com");
    	secondmerapplyservice.execute(merapplydto, null);
    }
}
