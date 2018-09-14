package com.upay.busi.usr.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 验证签名，有效期
 * 
 * @author liyulong
 * 
 */
public class CheckSignDto extends BaseDto {

    /** 企业邮箱 */
    private String mailTo;

    /** 有效期 */
    private Date valiedDate;

    /** 验签内容 */
    private String sid;

    /** 接口号 */
    private String trans;


    public String getMailTo() {
        return mailTo;
    }


    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }


    public Date getValiedDate() {
        return valiedDate;
    }


    public void setValiedDate(Date valiedDate) {
        this.valiedDate = valiedDate;
    }


    public String getSid() {
        return sid;
    }


    public void setSid(String sid) {
        this.sid = sid;
    }


    public String getTrans() {
        return trans;
    }


    public void setTrans(String trans) {
        this.trans = trans;
    }

}
