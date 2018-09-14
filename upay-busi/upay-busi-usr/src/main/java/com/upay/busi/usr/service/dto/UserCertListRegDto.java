package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 身份证联网核查登记记录
 * 
 * @author liubing
 * 
 */
public class UserCertListRegDto extends BaseDto {

    /**
     * 证件类型
     */
    private String certType;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 核查渠道
     */
    private String certCheckChnl;

    /**
     * 是否联网核查 1:是
     */
    private String isOnlineCertCheck;


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


    public String getCertCheckChnl() {
        return certCheckChnl;
    }


    public void setCertCheckChnl(String certCheckChnl) {
        this.certCheckChnl = certCheckChnl;
    }


    public String getIsOnlineCertCheck() {
        return isOnlineCertCheck;
    }


    public void setIsOnlineCertCheck(String isOnlineCertCheck) {
        this.isOnlineCertCheck = isOnlineCertCheck;
    }

}
