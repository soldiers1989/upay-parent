/**
 * 
 */
package com.upay.busi.gnr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 获取短信验证码DTO
 * 
 * @author zacks
 * 
 */
public class SmsGetDto extends BaseDto {
	
    private String name;

    /**
     * 短信编号
     */
    private String smsNo;
    /**
     * 手机号
     */
    private String mobile;
    private String phoneNo;

    private java.lang.String sendMsg;
    
    private String isEsbCore;
    private String transSubSeq;
    
    /**
     * 要发送的短信
     */
    // private String sendMessage;


	public String getIsEsbCore() {
		return isEsbCore;
	}


	public void setIsEsbCore(String isEsbCore) {
		this.isEsbCore = isEsbCore;
	}


	public String getTransSubSeq() {
		return transSubSeq;
	}


	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}


	/**
     * 手机验证码
     */
    private String smsCode;

    /**
     * 手机验证码重发时间（秒）
     */
    private String smsTime;

    /**
     * 手机验证码失效时间(秒)
     */
    private String smsTimeout;


    /**
     * 短信验证码发送内容
     */
    // private Map<String, List<String>> smsMap;

    /**
     * 发送内容
     * 
     * @return
     */
    // private ArrayList<String> smsSendMsgs;

    public String getName() {
        return name;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getPhoneNo() {
        return phoneNo;
    }


    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public java.lang.String getSendMsg() {
        return sendMsg;
    }


    public void setSendMsg(java.lang.String sendMsg) {
        this.sendMsg = sendMsg;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSmsNo() {
        return smsNo;
    }


    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }


    // public String getSendMessage() {
    // return sendMessage;
    // }
    //
    //
    // public void setSendMessage(String sendMessage) {
    // this.sendMessage = sendMessage;
    // }

    // public ArrayList<String> getSmsSendMsgs() {
    // return smsSendMsgs;
    // }
    //
    //
    // public void setSmsSendMsgs(ArrayList<String> smsSendMsgs) {
    // this.smsSendMsgs = smsSendMsgs;
    // }

    // public String getMobile() {
    // return mobile;
    // }
    //
    //
    // public void setMobile(String mobile) {
    // this.mobile = mobile;
    // }

    public String getSmsCode() {
        return smsCode;
    }


    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }


    public String getSmsTime() {
        return smsTime;
    }


    public void setSmsTime(String smsTime) {
        this.smsTime = smsTime;
    }


    // public Map<String, List<String>> getSmsMap() {
    // return smsMap;
    // }
    //
    //
    // public void setSmsMap(Map<String, List<String>> smsMap) {
    // this.smsMap = smsMap;
    // }

    public String getSmsTimeout() {
        return smsTimeout;
    }


    public void setSmsTimeout(String smsTimeout) {
        this.smsTimeout = smsTimeout;
    }

}
