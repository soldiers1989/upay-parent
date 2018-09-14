package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


public class NotifyIdQueryDto extends BaseDto {

    private String merNo;
    private String notifyId;

    private String resCode;
    private String resMsg;


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getNotifyId() {
        return notifyId;
    }


    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }


    public String getResCode() {
        return resCode;
    }


    public void setResCode(String resCode) {
        this.resCode = resCode;
    }


    public String getResMsg() {
        return resMsg;
    }


    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

}
