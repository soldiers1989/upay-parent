package com.upay.busi.fee.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 费用明细记录
 * 
 * @author liu
 * 
 */

public class FeeDetailRecordDto extends BaseDto {

    private Date txnDate; // 交易日期
    private Date txnTime; // 账号交易时间
    private String orderNo; // 交易订单号
    private String acctNo; // 支付账号
    private String acctType; // 账户类型
    private String chnlId; // 渠道编号
    // private String transCode; // 交易代码
    private String feeCode;// 收费代码
    private String assCode;// 分润代码
    private BigDecimal txnAmt; // 交易金额
    private BigDecimal feeAmt; // 手续费金额
    private String remarkCode; // 摘要代码
    private String txnStat; // 交易状态
    private String freeFlag; // 免收标志
    private String freeCycle; // 免收周期
    private String getType; // 手续费收起方式
    private String memo; // 备注


    public Date getTxnDate() {
        return txnDate;
    }


    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }


    public Date getTxnTime() {
        return txnTime;
    }


    public void setTxnTime(Date txnTime) {
        this.txnTime = txnTime;
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


    public String getChnlId() {
        return chnlId;
    }


    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }


    public String getFeeCode() {
        return feeCode;
    }


    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }


    public String getAssCode() {
        return assCode;
    }


    public void setAssCode(String assCode) {
        this.assCode = assCode;
    }


    public BigDecimal getTxnAmt() {
        return txnAmt;
    }


    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }


    public BigDecimal getFeeAmt() {
        return feeAmt;
    }


    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }


    public String getRemarkCode() {
        return remarkCode;
    }


    public void setRemarkCode(String remarkCode) {
        this.remarkCode = remarkCode;
    }


    public String getTxnStat() {
        return txnStat;
    }


    public void setTxnStat(String txnStat) {
        this.txnStat = txnStat;
    }


    public String getFreeFlag() {
        return freeFlag;
    }


    public void setFreeFlag(String freeFlag) {
        this.freeFlag = freeFlag;
    }


    public String getFreeCycle() {
        return freeCycle;
    }


    public void setFreeCycle(String freeCycle) {
        this.freeCycle = freeCycle;
    }


    public String getGetType() {
        return getType;
    }


    public void setGetType(String getType) {
        this.getType = getType;
    }


    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }


    @Override
    public String toString() {
        return "FeeDetailRecordDto [txnDate=" + txnDate + ", txnTime=" + txnTime + ", orderNo=" + orderNo
                + ", acctNo=" + acctNo + ", acctType=" + acctType + ", chnlId=" + chnlId + ", feeCode="
                + feeCode + ", assCode=" + assCode + ", txnAmt=" + txnAmt + ", feeAmt=" + feeAmt
                + ", remarkCode=" + remarkCode + ", txnStat=" + txnStat + ", freeFlag=" + freeFlag
                + ", freeCycle=" + freeCycle + ", getType=" + getType + ", memo=" + memo + "]";
    }

}
