package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * Created by Guo on 16/8/25.
 */
public class FreePwdAuthorizeCheckDto extends BaseDto {

    /**商户号*/
    private String merNo;

    /**合作平台账号,商户的方的用户ID*/
    private String platformUserNo;

    /**用户手机号*/
    private String mobile;

    /**用户IP*/
    private String spbillCreateIp;

    private String chnlId;

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    

    public String getPlatformUserNo() {
        return platformUserNo;
    }

    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    @Override
    public String getChnlId() {
        return chnlId;
    }

    @Override
    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }
}
