package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 密码重置登记
 * 
 * @author liyulong
 * 
 */
public class ResetPwdRegiserDto extends BaseDto {
    /** 证件号码 */
    private String certNo;

    /** 证件类型 */
    private String certType;

    /** 证件姓名 */
    private String certName;

    /** 身份证正面照文件名 */
    private String certFront;

    /** 身份证反面照文件名 */
    private String certBack;

    /** 身份证本人手持照文件名 */
    private String certPerson;

    /** 新交易密码 */
    private String tradePwd;
    
    private String miType;//密码控件类型

    public String getMiType() {
		return miType;
	}
	public void setMiType(String miType) {
		this.miType = miType;
	}
	


    public String getCertNo() {
        return certNo;
    }


    public void setCertNo(String certNo) {
        this.certNo = certNo;
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


    public String getTradePwd() {
        return tradePwd;
    }


    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }
}
