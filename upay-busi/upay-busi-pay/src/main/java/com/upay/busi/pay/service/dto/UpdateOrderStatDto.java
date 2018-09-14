package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月5日 上午8:21:02
 */
public class UpdateOrderStatDto extends BaseDto {
    private String respCode;//核心支付响应码
    private String orderNo;
    private String bkSerialNo;//核心流水号
    private String transSubSeq;//流水号
    
    
    
    
    
    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

    public String getBkSerialNo() {
        return bkSerialNo;
    }

    public void setBkSerialNo(String bkSerialNo) {
        this.bkSerialNo = bkSerialNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    
    
}
