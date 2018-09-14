package com.upay.busi.fee.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 手续费计算DTO
 * @author zhangjianfeng
 * @since 2016/11/24 21:24
 */
public class CalculateFeeDto extends BaseDto {

    /** 一级商户号 */
    private String merNo;

    /** 交易金额 */
    private BigDecimal transAmt;

    /** 交易订单号 */
    private String orderNo;

    /** 账号 */
    private String acctNo;

    /** 账户类型 */
    private String acctType;

    /** 订单状态 */
    private String orderStat;

    /** 交易类型 */
    private String transType;

    /** 手续费 单位：元 */
    private BigDecimal feeAmt;

    /** 手续费字符串 单位：分  */
    private String feeAmtChar;

    /** 手续费收取方式 */
    private String feeGetType;


    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getFeeAmtChar() {
        return feeAmtChar;
    }

    public void setFeeAmtChar(String feeAmtChar) {
        this.feeAmtChar = feeAmtChar;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getFeeGetType() {
        return feeGetType;
    }

    public void setFeeGetType(String feeGetType) {
        this.feeGetType = feeGetType;
    }
}
