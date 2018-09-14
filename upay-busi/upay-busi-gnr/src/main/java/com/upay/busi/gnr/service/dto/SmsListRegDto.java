package com.upay.busi.gnr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 短信发送成功登记记录
 * 
 * @author freeplato
 * 
 */
public class SmsListRegDto extends BaseDto {

    /**
     * 短信编号
     */
    private String smsNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 要发送的短信
     */
    private String sendMessage;


    /**
     * 短信发送数据
     */
    // private Map<String, List<String>> smsSendMsgs;

    public String getSmsNo() {
        return smsNo;
    }


    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }


    public String getMobile() {
        return mobile;
    }


    public String getSendMessage() {
        return sendMessage;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    // public Map<String, List<String>> getSmsSendMsgs() {
    // return smsSendMsgs;
    // }
    //
    // public void setSmsSendMsgs(Map<String, List<String>> smsSendMsgs) {
    // this.smsSendMsgs = smsSendMsgs;
    // }
    //
}
