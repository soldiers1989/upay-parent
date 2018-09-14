package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class WeakAuthDto extends BaseDto {
	private String userCertLevel;//用户认证等级
	// 用户姓名
	private String certName;
	// 证件类型
	private String certType;
	// 证件号码
	private String certNo;
	// 弱实名认证方式
	private String certWeakWay;
	// 身份证正面照文件名
	private String certFront;
	// 身份证反面照文件名
	private String certBack;
	// 身份证本人手持照文件名
	private String certPerson;
	/**
     * 认证类型 01：免密授权 02：普通会员 03：商户会员       db_column: APPROVE_TYPE 
     */ 	
	private java.lang.String approveType;

	/**
	 * ecif客户号
	 */
	private String ecifCustNo;
	
	

	public String getUserCertLevel() {
		return userCertLevel;
	}

	public void setUserCertLevel(String userCertLevel) {
		this.userCertLevel = userCertLevel;
	}

	public java.lang.String getApproveType() {
		return approveType;
	}

	public void setApproveType(java.lang.String approveType) {
		this.approveType = approveType;
	}

	public String getEcifCustNo() {
		return ecifCustNo;
	}

	public void setEcifCustNo(String ecifCustNo) {
		this.ecifCustNo = ecifCustNo;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertWeakWay() {
		return certWeakWay;
	}

	public void setCertWeakWay(String certWeakWay) {
		this.certWeakWay = certWeakWay;
	}

	public String getCertFront() {
		return certFront;
	}

	public void setCertFront(String certFront) {
		this.certFront = certFront;
	}

	public String getCertBack() {
		return certBack;
	}

	public void setCertBack(String certBack) {
		this.certBack = certBack;
	}

	public String getCertPerson() {
		return certPerson;
	}

	public void setCertPerson(String certPerson) {
		this.certPerson = certPerson;
	}

}
