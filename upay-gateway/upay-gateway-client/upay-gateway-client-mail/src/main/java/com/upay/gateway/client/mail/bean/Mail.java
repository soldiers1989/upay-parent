package com.upay.gateway.client.mail.bean;

/**
 * Created by Guo on 2016/9/30.
 */
public class Mail {

    /**邮件发送目标*/
    private String mailTo;

    /**邮件内容*/
    private String mailContent;

    /**邮件标题*/
    private String mailSubject;

    /**邮件发送人*/
    private String mailFrom;

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
}
