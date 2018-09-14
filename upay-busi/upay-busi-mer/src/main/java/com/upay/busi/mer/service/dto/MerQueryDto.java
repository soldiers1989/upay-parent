package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class MerQueryDto extends BaseDto{
	
	/**
     * 业务员姓名       db_column: PROMOTER_NAME 
     */ 	
	private java.lang.String promoterName;
    /**
     * 业务员手机       db_column: PROMOTER_IPHONE 
     */ 	
	private java.lang.String promoterIphone;
	
	private String merNo;

	/**
     * 商户名称       db_column: MER_NAME 
     */ 	
	private java.lang.String merName;
    /**
     * 商户授权免密标志  0:非授权免密  1：授权免密       db_column: MER_WITHOUT_PWD_SIGN 
     */ 	
	private java.lang.String merWithoutPwdSign;
    /**
     * 支付功能开通标志    0：开通   1：不开通       db_column: PAY_OPEN_FLAG 
     */ 	
	private java.lang.String payOpenFlag;
    /**
     * 商户业务类型   01:电商平台  02:线下门店   03:金融机构       db_column: MER_BUSI_TYPE 
     */ 	
	private java.lang.String merBusiType;
    /**
     * 联系人姓名       db_column: CONTACT 
     */ 	
	private java.lang.String contact;
    /**
     * 联系人电话       db_column: CONTACT_TEL 
     */ 	
	private java.lang.String contactTel;
    /**
     * 联系人手机       db_column: CONTACT_MOBILE 
     */ 	
	private java.lang.String contactMobile;
    /**
     * 联系人邮件       db_column: CONTACT_EMAIL 
     */ 	
	private java.lang.String contactEmail;
    /**
     * 商户电话       db_column: MER_TEL 
     */ 	
	private java.lang.String merTel;
    /**
     * 商户传真       db_column: MER_FAX 
     */ 	
	private java.lang.String merFax;
    /**
     * 商户地址       db_column: MER_ADDR 
     */ 	
	private java.lang.String merAddr;
    /**
     * 商户邮编       db_column: MER_POSTAL_CODE 
     */ 	
	private java.lang.String merPostalCode;
    /**
     * 网站备案号       db_column: WEBSITE_CODE 
     */ 	
	private java.lang.String websiteCode;
    /**
     * 网站名称       db_column: WEBSITE_NAME 
     */ 	
	private java.lang.String websiteName;
    /**
     * 网站域名       db_column: WEBSITE_DOMAIN 
     */ 	
	private java.lang.String websiteDomain;
    /**
     * 网站经营范围       db_column: WEBSITE_SCOP 
     */ 	
	private java.lang.String websiteScop;
	 /**
     * 网站经营范围 说明       db_column: WEBSITE_SCOP_DESC 
     */ 	
	private java.lang.String websiteScopDesc;
    /**
     * 公司名称       db_column: COMPANY_NAME 
     */ 	
	private java.lang.String companyName;
    /**
     * 企业法人代表姓名       db_column: EGAL_PERSON_NAME 
     */ 	
	private java.lang.String egalPersonName;
    /**
     * 企业法人代表身份证件类型       db_column: EGAL_PERSON_ID_TYPE 
     */ 	
	private java.lang.String egalPersonIdType;
    /**
     * 企业法人代表身份证件号码       db_column: EGAL_PERSON_ID_NO 
     */ 	
	private java.lang.String egalPersonIdNo;
    /**
     * 企业证件类型       db_column: COMPANY_ID_TYPE 
     */ 	
	private java.lang.String companyIdType;
    /**
     * 企业证件号       db_column: COMPANY_ID_NO 
     */ 	
	private java.lang.String companyIdNo;
    /**
     * 组织机构代码证号       db_column: ORG_DEPT_NO 
     */ 	
	private java.lang.String orgDeptNo;
    /**
     * 营业执照号       db_column: BUSI_LICENSE_ID 
     */ 	
	private java.lang.String busiLicenseId;
    /**
     * 商户开通日期       db_column: OPEN_DATE 
     */ 	
	private java.util.Date openDate;
    /**
     * 商户状态   0：正常    1：停用       db_column: MER_STATE 
     */ 	
	private java.lang.String merState;
    /**
     * 上级商户号       db_column: PARENT_MER_NO 
     */ 	
	private java.lang.String parentMerNo;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 微信商户识别码       db_column: SUB_MCH_ID 
     */ 	
	private java.lang.String subMchId;
    /**
     * isEntrust       db_column: IS_ENTRUST 
     */ 	
	private java.lang.String isEntrust;
    /**
     * 推广方       db_column: EXTENSION_PARTY 
     */ 	
	private java.lang.String extensionParty;
	
    /**
     * 商户二维码      db_column: qr_code 
     */ 	
	private java.lang.String qrCode;
    /**
     * 商户二维码支付次数      db_column: qr_limit_count 
     */ 	
	private java.lang.String qrLimitCount;
	
    /**
     * 商户二维码有效期      db_column: qr_valid_time 
     */ 	
	private java.util.Date qrValidTime;
    /**
     * 商户二维码唯一标识      db_column: qr_order_no
     */ 	
	private java.lang.String qrOrderNo;
	
	
    /**
     * 支付宝商户号       db_column: ALIPAY_MERCHANT_ID 
     */ 	
	private java.lang.String alipayMerchantId;
    /**
     * 支付宝商户更新参数       db_column: ALIPAY_MERUPDATE_PARAM 
     */ 	
//	private byte[] alipayMerupdateParam;
	/**
     * 商户简称       db_column: ALIAS_NAME 
     */ 	
	private java.lang.String aliasName;
	
	private java.lang.String promoterDeptName;
	
	private java.lang.String promoterDeptNo;
	
	
	
	public java.lang.String getPromoterDeptNo() {
		return promoterDeptNo;
	}

	public void setPromoterDeptNo(java.lang.String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}

	public java.lang.String getPromoterDeptName() {
		return promoterDeptName;
	}

	public void setPromoterDeptName(java.lang.String promoterDeptName) {
		this.promoterDeptName = promoterDeptName;
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

	public java.lang.String getWebsiteScopDesc() {
		return websiteScopDesc;
	}

	public void setWebsiteScopDesc(java.lang.String websiteScopDesc) {
		this.websiteScopDesc = websiteScopDesc;
	}

	private String parentMerName;//上级商户名称
	
	
	public java.lang.String getMerName() {
		return merName;
	}

	public void setMerName(java.lang.String merName) {
		this.merName = merName;
	}

	public java.lang.String getMerWithoutPwdSign() {
		return merWithoutPwdSign;
	}

	public void setMerWithoutPwdSign(java.lang.String merWithoutPwdSign) {
		this.merWithoutPwdSign = merWithoutPwdSign;
	}

	public java.lang.String getPayOpenFlag() {
		return payOpenFlag;
	}

	public void setPayOpenFlag(java.lang.String payOpenFlag) {
		this.payOpenFlag = payOpenFlag;
	}

	public java.lang.String getMerBusiType() {
		return merBusiType;
	}

	public void setMerBusiType(java.lang.String merBusiType) {
		this.merBusiType = merBusiType;
	}

	public java.lang.String getContact() {
		return contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	public java.lang.String getContactTel() {
		return contactTel;
	}

	public void setContactTel(java.lang.String contactTel) {
		this.contactTel = contactTel;
	}

	public java.lang.String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(java.lang.String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public java.lang.String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(java.lang.String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public java.lang.String getMerTel() {
		return merTel;
	}

	public void setMerTel(java.lang.String merTel) {
		this.merTel = merTel;
	}

	public java.lang.String getMerFax() {
		return merFax;
	}

	public void setMerFax(java.lang.String merFax) {
		this.merFax = merFax;
	}

	public java.lang.String getMerAddr() {
		return merAddr;
	}

	public void setMerAddr(java.lang.String merAddr) {
		this.merAddr = merAddr;
	}

	public java.lang.String getMerPostalCode() {
		return merPostalCode;
	}

	public void setMerPostalCode(java.lang.String merPostalCode) {
		this.merPostalCode = merPostalCode;
	}

	public java.lang.String getWebsiteCode() {
		return websiteCode;
	}

	public void setWebsiteCode(java.lang.String websiteCode) {
		this.websiteCode = websiteCode;
	}

	public java.lang.String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(java.lang.String websiteName) {
		this.websiteName = websiteName;
	}

	public java.lang.String getWebsiteDomain() {
		return websiteDomain;
	}

	public void setWebsiteDomain(java.lang.String websiteDomain) {
		this.websiteDomain = websiteDomain;
	}

	public java.lang.String getWebsiteScop() {
		return websiteScop;
	}

	public void setWebsiteScop(java.lang.String websiteScop) {
		this.websiteScop = websiteScop;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.String getEgalPersonName() {
		return egalPersonName;
	}

	public void setEgalPersonName(java.lang.String egalPersonName) {
		this.egalPersonName = egalPersonName;
	}

	public java.lang.String getEgalPersonIdType() {
		return egalPersonIdType;
	}

	public void setEgalPersonIdType(java.lang.String egalPersonIdType) {
		this.egalPersonIdType = egalPersonIdType;
	}

	public java.lang.String getEgalPersonIdNo() {
		return egalPersonIdNo;
	}

	public void setEgalPersonIdNo(java.lang.String egalPersonIdNo) {
		this.egalPersonIdNo = egalPersonIdNo;
	}

	public java.lang.String getCompanyIdType() {
		return companyIdType;
	}

	public void setCompanyIdType(java.lang.String companyIdType) {
		this.companyIdType = companyIdType;
	}

	public java.lang.String getCompanyIdNo() {
		return companyIdNo;
	}

	public void setCompanyIdNo(java.lang.String companyIdNo) {
		this.companyIdNo = companyIdNo;
	}

	public java.lang.String getOrgDeptNo() {
		return orgDeptNo;
	}

	public void setOrgDeptNo(java.lang.String orgDeptNo) {
		this.orgDeptNo = orgDeptNo;
	}

	public java.lang.String getBusiLicenseId() {
		return busiLicenseId;
	}

	public void setBusiLicenseId(java.lang.String busiLicenseId) {
		this.busiLicenseId = busiLicenseId;
	}

	public java.util.Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(java.util.Date openDate) {
		this.openDate = openDate;
	}

	public java.lang.String getMerState() {
		return merState;
	}

	public void setMerState(java.lang.String merState) {
		this.merState = merState;
	}

	public java.lang.String getParentMerNo() {
		return parentMerNo;
	}

	public void setParentMerNo(java.lang.String parentMerNo) {
		this.parentMerNo = parentMerNo;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(java.lang.String subMchId) {
		this.subMchId = subMchId;
	}

	public java.lang.String getIsEntrust() {
		return isEntrust;
	}

	public void setIsEntrust(java.lang.String isEntrust) {
		this.isEntrust = isEntrust;
	}

	public java.lang.String getExtensionParty() {
		return extensionParty;
	}

	public void setExtensionParty(java.lang.String extensionParty) {
		this.extensionParty = extensionParty;
	}

	public java.lang.String getQrCode() {
		return qrCode;
	}

	public void setQrCode(java.lang.String qrCode) {
		this.qrCode = qrCode;
	}

	public java.lang.String getQrLimitCount() {
		return qrLimitCount;
	}

	public void setQrLimitCount(java.lang.String qrLimitCount) {
		this.qrLimitCount = qrLimitCount;
	}

	public java.util.Date getQrValidTime() {
		return qrValidTime;
	}

	public void setQrValidTime(java.util.Date qrValidTime) {
		this.qrValidTime = qrValidTime;
	}

	public java.lang.String getQrOrderNo() {
		return qrOrderNo;
	}

	public void setQrOrderNo(java.lang.String qrOrderNo) {
		this.qrOrderNo = qrOrderNo;
	}

	public java.lang.String getAlipayMerchantId() {
		return alipayMerchantId;
	}

	public void setAlipayMerchantId(java.lang.String alipayMerchantId) {
		this.alipayMerchantId = alipayMerchantId;
	}

//	public byte[] getAlipayMerupdateParam() {
//		return alipayMerupdateParam;
//	}
//
//	public void setAlipayMerupdateParam(byte[] alipayMerupdateParam) {
//		this.alipayMerupdateParam = alipayMerupdateParam;
//	}

	public java.lang.String getAliasName() {
		return aliasName;
	}

	public void setAliasName(java.lang.String aliasName) {
		this.aliasName = aliasName;
	}

	public String getParentMerName() {
		return parentMerName;
	}

	public void setParentMerName(String parentMerName) {
		this.parentMerName = parentMerName;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	
	
}
