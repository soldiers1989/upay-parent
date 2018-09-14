package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 邮箱重置登录密码，校验是否本人
 * 
 * @author liyulong
 * 
 */
public class CheckUsrDto extends BaseDto {

    private String mobile;// 手机号

    private String mailTo;// 邮箱

    private String userName;//用户名


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getMailTo() {
        return mailTo;
    }


    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

}
