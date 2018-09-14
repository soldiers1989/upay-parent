package com.upay.busi.fee.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午4:21:44
 */
public class UpdateFeeAssDetailOrderStatDto extends BaseDto {
    private String orderNo;
    private String txnStat;
    
    public String getTxnStat() {
        return txnStat;
    }

    public void setTxnStat(String txnStat) {
        this.txnStat = txnStat;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
   
   
    
    
}
