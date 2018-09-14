package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class UserStatCheckDto extends BaseDto {
    // 获取用户ID
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
