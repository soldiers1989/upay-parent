package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月5日 下午2:24:30
 */
public class CheckUserAccountStatDto extends BaseDto {
    private String accNo;//账号
    private BigDecimal transAmt;//支付金额
    private String bankAccNo;//卡号
    private String dcFlag;//借贷标识，需要在组装flow的时候初始化
    private String merNo;//商户号
    
    private String accType;//账户类型
    private String payType;//支付方式
    private String routeCode;
    private String transType;//
    private String oriOrderNo;//原订单号
    private String orderNo;//订单号
    private String oriTransType;//原订单交易类型
    private String oriPayType;//原支付类型
    private String merAccNo; //商户平台账户
    private String oriUserId;//原订单用户id
    
    private String merUserId;//商户的用户号
    private String isNextDayRefund;//是否隔日退款
    

    
    
    public String getIsNextDayRefund() {
        return isNextDayRefund;
    }

    public void setIsNextDayRefund(String isNextDayRefund) {
        this.isNextDayRefund = isNextDayRefund;
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId;
    }

    public String getOriUserId() {
        return oriUserId;
    }

    public void setOriUserId(String oriUserId) {
        this.oriUserId = oriUserId;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getOriTransType() {
        return oriTransType;
    }

    public void setOriTransType(String oriTransType) {
        this.oriTransType = oriTransType;
    }

    public String getOriPayType() {
        return oriPayType;
    }

    public void setOriPayType(String oriPayType) {
        this.oriPayType = oriPayType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    
    public String getDcFlag() {
        return dcFlag;
    }

    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    
    

    public String getMerAccNo() {
        return merAccNo;
    }

    public void setMerAccNo(String merAccNo) {
        this.merAccNo = merAccNo;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    
}
