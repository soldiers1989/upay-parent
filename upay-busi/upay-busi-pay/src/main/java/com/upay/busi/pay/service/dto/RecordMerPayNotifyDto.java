/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2017年1月4日
 */
public class RecordMerPayNotifyDto extends BaseDto {
    
    private String orderNo;
    private String orderStat;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderStat() {
        return orderStat;
    }
    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }
    
    
    
}
