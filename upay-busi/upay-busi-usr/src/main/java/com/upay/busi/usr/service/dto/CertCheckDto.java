package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 用户身份验证
 * 
 * @author liu
 * 
 */
public class CertCheckDto extends BaseDto {

    /** 身份认证方式 */
    private String certFlag;
    /** 用户姓名 */
    private String certName;
    /** 证件类型 */
    private String certType;
    /** 证件号码 */
    private String certNo;
    /** 身份证正面照文件名 */
    private String certFront;
    /** 身份证反面照文件名 */
    private String certBack;
    /** 身份证本人手持照文件名 */
    private String certPerson;
    /** 是否需要联网核查 0:否 1：是 */
    private String isOnlineCertCheck;
    /** 用户认证等级 */
    private String userCertLevel;

    /**
     * 认证类型 01：免密授权 02：普通会员 03：商户会员       db_column: APPROVE_TYPE 
     */ 	
	private java.lang.String approveType;
	
	
    public java.lang.String getApproveType() {
		return approveType;
	}


	public void setApproveType(java.lang.String approveType) {
		this.approveType = approveType;
	}


	public String getUserCertLevel() {
        return userCertLevel;
    }


    public void setUserCertLevel(String userCertLevel) {
        this.userCertLevel = userCertLevel;
    }


    public String getCertFlag() {
        return certFlag;
    }


    public void setCertFlag(String certFlag) {
        this.certFlag = certFlag;
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


    public String getIsOnlineCertCheck() {
        return isOnlineCertCheck;
    }


    public void setIsOnlineCertCheck(String isOnlineCertCheck) {
        this.isOnlineCertCheck = isOnlineCertCheck;
    }


    @Override
    public String toString() {
        return "CertCheckDto [certFlag=" + certFlag + ", certName=" + certName + ", certType=" + certType
                + ", certNo=" + certNo + ", certFront=" + certFront + ", certBack=" + certBack
                + ", certPerson=" + certPerson + ", isOnlineCertCheck=" + isOnlineCertCheck
                + ", userCertLevel=" + userCertLevel + "]";
    }

}
