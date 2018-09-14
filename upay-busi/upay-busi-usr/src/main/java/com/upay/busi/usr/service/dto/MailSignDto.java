package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 邮箱加签，内容拼接
 * 
 * @author liyulong
 * 
 */
public class MailSignDto extends BaseDto {

    /** 邮件发送目标 */
    private String mailTo;

    /** 邮件内容 */
    private String mailContent;

    /** 邮件标题 */
    private String mailSubject;

    /** 邮件发送人 */
    private String mailFrom;

    /** 功能类型 */
    private String funcType;

    /*接受邮件的用户名*/
    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMailTo() {
        return mailTo;
    }


    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }


    public String getMailContent() {
        return mailContent;
    }


    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }


    public String getMailSubject() {
        return mailSubject;
    }


    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }


    public String getMailFrom() {
        return mailFrom;
    }


    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }


    public String getFuncType() {
        return funcType;
    }


    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }
    
}
