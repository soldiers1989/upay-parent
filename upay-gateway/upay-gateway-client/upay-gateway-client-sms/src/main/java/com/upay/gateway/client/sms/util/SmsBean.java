package com.upay.gateway.client.sms.util;

/**
 * SmsBean
 * 
 * @author 车静
 * 
 */
public class SmsBean {

    private String mobilePhone;

    private String userId;

    private String content;


    public String getMobilePhone() {
        return mobilePhone;
    }


    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

}
