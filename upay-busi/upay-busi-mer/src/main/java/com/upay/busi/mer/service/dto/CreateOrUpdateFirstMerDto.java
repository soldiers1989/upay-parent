package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 检查二级商户是否所属一级商户
 * 
 * @author yanzixiong
 * 
 */
public class CreateOrUpdateFirstMerDto extends BaseDto {
	private String operateFlag;//A为创建新增  U为更新
	
	// 商户信息
	/**
     * 申请编号       db_column: PROMOTER_NAME 
     */ 	
	private java.lang.String merApplyNo;
    /**
     * 业务员姓名       db_column: PROMOTER_NAME 
     */ 	
	private java.lang.String promoterName;
    /**
     * 业务员手机       db_column: PROMOTER_IPHONE 
     */ 	
	private java.lang.String promoterIphone;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
//	private java.lang.String promoterDeptNo;
	
	private String userNickName;// 用户昵称
	/**
	 * 结算账户 db_column: STL_ACCT_NO
	 */
	private java.lang.String stlAcctNo;
	/**
	 * 结算账户类型 db_column: STL_ACCT_TYPE
	 */
	private java.lang.String stlAcctType;
	/**
	 * 结算账户户名 db_column: STL_ACCT_NAME
	 */
	private java.lang.String stlAcctName;
    /**
     * 银行机构编号       db_column: BANK_ID 
     */ 	
	private java.lang.String bankId;
    /**
     * 银行名称       db_column: BANK_NAME 
     */ 	
	private java.lang.String bankName;

	/** 商户名称 */
	private String merName;

	/** 商户简称 */
	private String aliasName;
	/** 商户授权免密标志 */
	private String merWithoutPwdSign;

	/** 支付功能开通标志 */
	private String payopenflag;

	/** 商户业务类型 */
	private String merBusiType;

	/** 联系人姓名 */
	private String contact;
	
	private String sex;// 性别

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
	
	private String extensionParty;

	
	

	public java.lang.String getMerApplyNo() {
		return merApplyNo;
	}

	public void setMerApplyNo(java.lang.String merApplyNo) {
		this.merApplyNo = merApplyNo;
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

//	public java.lang.String getPromoterDeptNo() {
//		return promoterDeptNo;
//	}
//
//	public void setPromoterDeptNo(java.lang.String promoterDeptNo) {
//		this.promoterDeptNo = promoterDeptNo;
//	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	// 返回商户信息
	private String merNo;
	
	
	private String privateKey;

	
	

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public String getExtensionParty() {
		return extensionParty;
	}

	public void setExtensionParty(String extensionParty) {
		this.extensionParty = extensionParty;
	}
	
	public java.lang.String getBankId() {
		return bankId;
	}

	public void setBankId(java.lang.String bankId) {
		this.bankId = bankId;
	}

	public java.lang.String getBankName() {
		return bankName;
	}

	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}

	public java.lang.String getStlAcctNo() {
		return stlAcctNo;
	}

	public void setStlAcctNo(java.lang.String stlAcctNo) {
		this.stlAcctNo = stlAcctNo;
	}

	public java.lang.String getStlAcctType() {
		return stlAcctType;
	}

	public void setStlAcctType(java.lang.String stlAcctType) {
		this.stlAcctType = stlAcctType;
	}

	public java.lang.String getStlAcctName() {
		return stlAcctName;
	}

	public void setStlAcctName(java.lang.String stlAcctName) {
		this.stlAcctName = stlAcctName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
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

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
