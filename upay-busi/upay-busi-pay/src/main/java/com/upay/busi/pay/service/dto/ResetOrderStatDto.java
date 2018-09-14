package com.upay.busi.pay.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午4:01:42
 */
public class ResetOrderStatDto extends BaseDto {
    private String orderNo;
    private String orderStat;
    private Date payTime;
    private String payServicType;//支付服务类型
    private String transType;//交易类型
    
    private String clearFlag;//清算 标志
    private String refundStat;
    
    
    
    
    
    
    public String getRefundStat() {
        return refundStat;
    }

    public void setRefundStat(String refundStat) {
        this.refundStat = refundStat;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    
}
