package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 企业重置密码修改邮件状态
 * 
 * @author liyulong
 * 
 */
public class ChangeMailStatusDto extends BaseDto {

    /** 用户名 */
    private String email;


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

}
