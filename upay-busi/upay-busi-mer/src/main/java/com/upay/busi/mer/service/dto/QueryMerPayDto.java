package com.upay.busi.mer.service.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;


/**
 * 查询二级商户支付流水表里所有的支付交易信息
 * 
 * @author yanzixiong
 * 
 */
public class QueryMerPayDto extends BaseDto {
    /** 二级商户代码 */
    private String secMerNo;

    /** 查询起始日期 */
    private Date startDate;

    /** 查询结止日期 */
    private Date endDate;

    /** 订单号 商户订单 */
    private String orderNo;

    /** 订单描述 商户 */
    private String orderDes;

    /** 商户号 */
    private String merNo;

    /** 往来标识 */
    private String srFlag;

    /** 交易日期 平台日期 */
    private Date sysDate;

    /** 明细流水号 */
    private String transSubSeq;

    /** 通道代码 */
    private String routeCode;

    /** 支付用户ID */
    private String payUserId;

    /** 付款账号类型 */
    private String payerAcctType;

    /** 付款账号 */
    private String payerAcctNo;

    /** 付款户名 */
    private String payerName;

    /** 付款人开户机构名 */
    private String payerOrgName;

    /** 付款开户行号 */
    private String payerBankNo;

    /** 付款开户行名 */
    private String payerBankName;

    /** 收款账号类型 */
    private String payeeAcctType;

    /** 收款账号 */
    private String payeeAcctNo;

    /** 收款户名 */
    private String payeeName;

    /** 收款人开户机构名 */
    private String payeeOrgName;

    /** 收款开户行号 */
    private String payeeBankNo;

    /** 收款开户行名 */
    private String payeeBankName;

    /** 币种 */
    private String ccy;

    /** 支付金额 */
    private BigDecimal transAmt;

    /** 支付手续费 */
    private BigDecimal feeAmt;

    /** 内部交易状态 */
    private String transStat;

    /** 通道方交易状态 */
    private String routeTransStat;

    /** 支付时间 */
    private Date transTime;

    /** 通道交易日期 */
    private Date routeDate;

    /** 通道交易流水号 */
    private String routeSeq;

    /** 最后变更时间 */
    private Date lastUpdateTime;

    /** 对账标志 */
    private String chkFlag;

    /** 对账批次号 */
    private String chkBatchNo;

    /** 对账日期 */
    private Date chkDate;

    /** 用户ID */
    private String openId;

    /** 支付流水List */
    private List<Map<String, Object>> payFlowList;


    public String getSecMerNo() {
        return secMerNo;
    }


    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }


    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getOrderDes() {
        return orderDes;
    }


    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getSrFlag() {
        return srFlag;
    }


    public void setSrFlag(String srFlag) {
        this.srFlag = srFlag;
    }


    public Date getSysDate() {
        return sysDate;
    }


    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }


    public String getTransSubSeq() {
        return transSubSeq;
    }


    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }


    public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public String getPayUserId() {
        return payUserId;
    }


    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }


    public String getPayerAcctType() {
        return payerAcctType;
    }


    public void setPayerAcctType(String payerAcctType) {
        this.payerAcctType = payerAcctType;
    }


    public String getPayerAcctNo() {
        return payerAcctNo;
    }


    public void setPayerAcctNo(String payerAcctNo) {
        this.payerAcctNo = payerAcctNo;
    }


    public String getPayerName() {
        return payerName;
    }


    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }


    public String getPayerOrgName() {
        return payerOrgName;
    }


    public void setPayerOrgName(String payerOrgName) {
        this.payerOrgName = payerOrgName;
    }


    public String getPayerBankNo() {
        return payerBankNo;
    }


    public void setPayerBankNo(String payerBankNo) {
        this.payerBankNo = payerBankNo;
    }


    public String getPayerBankName() {
        return payerBankName;
    }


    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }


    public String getPayeeAcctType() {
        return payeeAcctType;
    }


    public void setPayeeAcctType(String payeeAcctType) {
        this.payeeAcctType = payeeAcctType;
    }


    public String getPayeeAcctNo() {
        return payeeAcctNo;
    }


    public void setPayeeAcctNo(String payeeAcctNo) {
        this.payeeAcctNo = payeeAcctNo;
    }


    public String getPayeeName() {
        return payeeName;
    }


    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }


    public String getPayeeOrgName() {
        return payeeOrgName;
    }


    public void setPayeeOrgName(String payeeOrgName) {
        this.payeeOrgName = payeeOrgName;
    }


    public String getPayeeBankNo() {
        return payeeBankNo;
    }


    public void setPayeeBankNo(String payeeBankNo) {
        this.payeeBankNo = payeeBankNo;
    }


    public String getPayeeBankName() {
        return payeeBankName;
    }


    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }


    public String getCcy() {
        return ccy;
    }


    public void setCcy(String ccy) {
        this.ccy = ccy;
    }


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public BigDecimal getFeeAmt() {
        return feeAmt;
    }


    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }


    public String getTransStat() {
        return transStat;
    }


    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }


    public String getRouteTransStat() {
        return routeTransStat;
    }


    public void setRouteTransStat(String routeTransStat) {
        this.routeTransStat = routeTransStat;
    }


    public Date getTransTime() {
        return transTime;
    }


    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }


    public Date getRouteDate() {
        return routeDate;
    }


    public void setRouteDate(Date routeDate) {
        this.routeDate = routeDate;
    }


    public String getRouteSeq() {
        return routeSeq;
    }


    public void setRouteSeq(String routeSeq) {
        this.routeSeq = routeSeq;
    }


    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }


    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public String getChkFlag() {
        return chkFlag;
    }


    public void setChkFlag(String chkFlag) {
        this.chkFlag = chkFlag;
    }


    public String getChkBatchNo() {
        return chkBatchNo;
    }


    public void setChkBatchNo(String chkBatchNo) {
        this.chkBatchNo = chkBatchNo;
    }


    public Date getChkDate() {
        return chkDate;
    }


    public void setChkDate(Date chkDate) {
        this.chkDate = chkDate;
    }


    public String getOpenId() {
        return openId;
    }


    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public List<Map<String, Object>> getPayFlowList() {
        return payFlowList;
    }


    public void setPayFlowList(List<Map<String, Object>> payFlowList) {
        this.payFlowList = payFlowList;
    }

}
