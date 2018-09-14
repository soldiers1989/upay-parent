package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class ResetMobileRegisterDto extends BaseDto {

    // 申请方式
    private String applyType;
    // 用户姓名
    private String certName;
    // 证件类型
    private String certType;
    // 证件号码
    private String certNo;
    // 身份证正面照文件名
    private String certFront;
    // 身份证反面照文件名
    private String certBack;
    // 身份证本人手持照文件名
    private String certPerson;
    // 原手机号
    private String oldMobile;
    // 新手机号
    private String newMobile;


    public String getApplyType() {
        return applyType;
    }


    public void setApplyType(String applyType) {
        this.applyType = applyType;
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


    public String getOldMobile() {
        return oldMobile;
    }


    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }


    public String getNewMobile() {
        return newMobile;
    }


    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

}
