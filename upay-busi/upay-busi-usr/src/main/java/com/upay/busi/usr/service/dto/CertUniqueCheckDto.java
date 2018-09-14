package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 身份信息唯一性检查
 * 
 * @author liubing
 * 
 */
public class CertUniqueCheckDto extends BaseDto {
    /** 证件类型 */
    private String certType;

    /** 证件姓名 */
    private String certName;

    /** 证件号码 */
    private String certNo;
    
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


	public String getCertType() {
        return certType;
    }


    public void setCertType(String certType) {
        this.certType = certType;
    }


    public String getCertName() {
        return certName;
    }


    public void setCertName(String certName) {
        this.certName = certName;
    }


    public String getCertNo() {
        return certNo;
    }


    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

}
