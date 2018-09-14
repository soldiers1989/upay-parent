package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 登录成功后处理Session
 * 
 * @author liubing
 * 
 */
public class LogSuccessSessionDealDto extends BaseDto {

    private String sessionId;


    public String getSessionId() {
        return sessionId;
    }


    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
