package com.upay.busi.fee.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午3:11:51
 */
public class UpdateFeeDetailOrderStatDto extends BaseDto {
    private String orderNo;//订单号
    private String txnStat;//交易状态
    public String getOrderNo() {
        return orderNo;
    }
    public String getTxnStat() {
        return txnStat;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public void setTxnStat(String txnStat) {
        this.txnStat = txnStat;
    }
    
    
    
}
