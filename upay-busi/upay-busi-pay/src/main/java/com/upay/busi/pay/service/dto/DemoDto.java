package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


public class DemoDto extends BaseDto {

    private String username;

    private String password;


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}
