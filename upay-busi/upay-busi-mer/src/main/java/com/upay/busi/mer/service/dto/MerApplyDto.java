package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 特约商户申请\二级商户维护
 * 
 * @author yanzixiong
 * 
 */
public class MerApplyDto extends BaseDto {
    /*申请id 修改时用*/
	 /**
     * 业务员姓名       db_column: PROMOTER_NAME 
     */ 	
	private java.lang.String promoterName;
    /**
     * 业务员手机       db_column: PROMOTER_IPHONE 
     */ 	
	private java.lang.String promoterIphone;
	
	
	private String merNo;

	/** 商户申请编号 */
	private String merApplyNo;

	/** 商户名称 */
	private String merName;

	/** 商户授权免密标志 */
	private String merWithoutPwdSign;

	/** 支付功能开通标志 */
	private String payopenflag;

	/** 商户业务类型 */
	private String merBusiType;

	/** 联系人姓名 */
	private String contact;

	/** 联系人电话 */
	private String contactTel;

	/** 联系人手机 */
	private String contactMobile;

	/** 联系人邮件 */
	private String contactEmail;

	/** 商户电话 */
	private String merTel;

	/** 商户传真 */
	private String merFax;

	/** 商户地址 */
	private String merAddr;

	/** 商户邮编 */
	private String merPostalCode;

	/** 网站备案号 */
	private String websiteCode;

	/** 网站名称 */
	private String websiteName;

	/** 网站域名 */
	private String websiteDomain;

	/** 网站经营范围 */
	private String websiteScop;

	/** 公司名称 */
	private String companyName;

	/** 企业法人代表姓名 */
	private String egalPersonName;

	/** 企业法人代表身份证件类型 */
	private String egalPersonIdType;

	/** 企业法人代表身份证件号码 */
	private String egalPersonIdNo;

	/** 企业证件类型 */
	private String companyIdType;

	/** 企业证件号 */
	private String companyIdNo;

	/** 组织机构代码证号 */
	private String orgDeptNo;

	/** 营业执照号 */
	private String busiLicenseId;

	/** 上级商户号 */
	private String parentMerNo;
	
	/** 二级商户申请邮箱 */
	private String email;
	
	/** 工作流状态 */
	private String wfStatus;
	/** 商户简称 */
	private String aliasName;


	/**
	 * 状态   00 代表新增   11代表修改
	 */
	private java.lang.String state;
	//columns END


	/**
	 * 用户名
	 */
	private java.lang.String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getState() {
		return state;
	}

	public java.lang.String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(java.lang.String promoterName) {
		this.promoterName = promoterName;
	}

	public java.lang.String getPromoterIphone() {
		return promoterIphone;
	}

	public void setPromoterIphone(java.lang.String promoterIphone) {
		this.promoterIphone = promoterIphone;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String id) {
		this.merNo = id;
	}

	public String getMerApplyNo() {
		return merApplyNo;
	}

	public void setMerApplyNo(String merApplyNo) {
		this.merApplyNo = merApplyNo;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerWithoutPwdSign() {
		return merWithoutPwdSign;
	}

	public void setMerWithoutPwdSign(String merWithoutPwdSign) {
		this.merWithoutPwdSign = merWithoutPwdSign;
	}

	public String getPayopenflag() {
		return payopenflag;
	}

	public void setPayopenflag(String payopenflag) {
		this.payopenflag = payopenflag;
	}

	public String getMerBusiType() {
		return merBusiType;
	}

	public void setMerBusiType(String merBusiType) {
		this.merBusiType = merBusiType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getMerTel() {
		return merTel;
	}

	public void setMerTel(String merTel) {
		this.merTel = merTel;
	}

	public String getMerFax() {
		return merFax;
	}

	public void setMerFax(String merFax) {
		this.merFax = merFax;
	}

	public String getMerAddr() {
		return merAddr;
	}

	public void setMerAddr(String merAddr) {
		this.merAddr = merAddr;
	}

	public String getMerPostalCode() {
		return merPostalCode;
	}

	public void setMerPostalCode(String merPostalCode) {
		this.merPostalCode = merPostalCode;
	}

	public String getWebsiteCode() {
		return websiteCode;
	}

	public void setWebsiteCode(String websiteCode) {
		this.websiteCode = websiteCode;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getWebsiteDomain() {
		return websiteDomain;
	}

	public void setWebsiteDomain(String websiteDomain) {
		this.websiteDomain = websiteDomain;
	}

	public String getWebsiteScop() {
		return websiteScop;
	}

	public void setWebsiteScop(String websiteScop) {
		this.websiteScop = websiteScop;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEgalPersonName() {
		return egalPersonName;
	}

	public void setEgalPersonName(String egalPersonName) {
		this.egalPersonName = egalPersonName;
	}

	public String getEgalPersonIdType() {
		return egalPersonIdType;
	}

	public void setEgalPersonIdType(String egalPersonIdType) {
		this.egalPersonIdType = egalPersonIdType;
	}

	public String getEgalPersonIdNo() {
		return egalPersonIdNo;
	}

	public void setEgalPersonIdNo(String egalPersonIdNo) {
		this.egalPersonIdNo = egalPersonIdNo;
	}

	public String getCompanyIdType() {
		return companyIdType;
	}

	public void setCompanyIdType(String companyIdType) {
		this.companyIdType = companyIdType;
	}

	public String getCompanyIdNo() {
		return companyIdNo;
	}

	public void setCompanyIdNo(String companyIdNo) {
		this.companyIdNo = companyIdNo;
	}

	public String getOrgDeptNo() {
		return orgDeptNo;
	}

	public void setOrgDeptNo(String orgDeptNo) {
		this.orgDeptNo = orgDeptNo;
	}

	public String getBusiLicenseId() {
		return busiLicenseId;
	}

	public void setBusiLicenseId(String busiLicenseId) {
		this.busiLicenseId = busiLicenseId;
	}

	public String getParentMerNo() {
		return parentMerNo;
	}

	public void setParentMerNo(String parentMerNo) {
		this.parentMerNo = parentMerNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	
}
