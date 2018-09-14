package com.upay.busi.usr.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 企业注册
 * 
 * @author liyulong
 * 
 */
public class ComRegDto extends BaseDto {

    /** 企业邮箱 */
    private String mailTo;

    /** 是否已生成用户0-没有1-已生成 */
    private String generateFlag;

    /** 发送次数 */
    private int sendNum;

    /** 有效期 */
    private Date valiedDate;

    /** 最后发送时间 */
    private Date lastSendTime;


    public String getMailTo() {
        return mailTo;
    }


    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }


    public String getGenerateFlag() {
        return generateFlag;
    }


    public void setGenerateFlag(String generateFlag) {
        this.generateFlag = generateFlag;
    }


    public int getSendNum() {
        return sendNum;
    }


    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }


    public Date getValiedDate() {
        return valiedDate;
    }


    public void setValiedDate(Date valiedDate) {
        this.valiedDate = valiedDate;
    }


    public Date getLastSendTime() {
        return lastSendTime;
    }


    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }
}
