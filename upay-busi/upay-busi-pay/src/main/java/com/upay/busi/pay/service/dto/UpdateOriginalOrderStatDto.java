/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年12月12日
 */
public class UpdateOriginalOrderStatDto extends BaseDto {
    /** 支付平台退款订单号 */
    private String oriOrderNo;
    /** 退款订单状态 */
    private String orderStat;
    /** 退款额度标识  */
    private String refundAmtFlag;
    /** 累计退款金额 */
    private BigDecimal ejectAmt;
    
    
    

    public BigDecimal getEjectAmt() {
        return ejectAmt;
    }

    public void setEjectAmt(BigDecimal ejectAmt) {
        this.ejectAmt = ejectAmt;
    }

    public String getRefundAmtFlag() {
        return refundAmtFlag;
    }

    public void setRefundAmtFlag(String refundAmtFlag) {
        this.refundAmtFlag = refundAmtFlag;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }
    
    
    
}
