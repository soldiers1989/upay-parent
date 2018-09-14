package com.upay.busi.mer.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;

/**
 * 一级商户查询二级商户
 * 
 * @author yanzixiong
 * 
 */
public class MerQuerySecondDto extends BaseDto {
	/** 二级商户号 */
	private String secMerNo;
	
	/** 二级商户名 */
	private String secMerName;
	
	/** 商户号 */
	private String merNo;
	
	/** 商户名 */
	private String merName;
	
	/** 商户授权免密标志 */
	private String merWithoutPwdSign;
	
	/** 支付功能开通标志 */
	private String payOpenFlag;
	
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
	
	/** 商户开通日期 */
	private String openDate;
	
	/** 商户状态 */
	private String merState;
	
	/** 商户识别码 */
	private String subMchId;
	
	private List<Map<String, Object>> merBaseInfoList;

	public String getSecMerNo() {
		return secMerNo;
	}

	public void setSecMerNo(String secMerNo) {
		this.secMerNo = secMerNo;
	}

	public String getSecMerName() {
		return secMerName;
	}

	public void setSecMerName(String secMerName) {
		this.secMerName = secMerName;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
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

	public String getPayOpenFlag() {
		return payOpenFlag;
	}

	public void setPayOpenFlag(String payOpenFlag) {
		this.payOpenFlag = payOpenFlag;
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

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getMerState() {
		return merState;
	}

	public void setMerState(String merState) {
		this.merState = merState;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public List<Map<String, Object>> getMerBaseInfoList() {
		return merBaseInfoList;
	}

	public void setMerBaseInfoList(List<Map<String, Object>> merBaseInfoList) {
		this.merBaseInfoList = merBaseInfoList;
	}

}
