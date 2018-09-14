package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 退款商户手续费DTO
 * @author zhangjianfeng
 * @since 2016/08/19 00:29
 */
public class RefundMerFeeDto extends BaseDto {

    /** 支付平台支付订单号 */
    private String oriOrderNo;

    /** 原订单已退款金额，当存在多次退款时存在 */
    private BigDecimal hadRefundAmt;

    /** 是否退还商户手续费：0-不退还；1-退还； */
    private String isReturnFee;

    /** 是否隔日退货：0-否；1-是； */
    private String isNextDayRefund;

    /** 费用方法ID */
    private String feeId;

    /** 手续费金额 */
    private BigDecimal feeAmt;

    /** 商户平台账户 */
    private String merVacctNo;

    /** 支付平台退款订单号 */
    private String refundOrderNo;

    /** 商户号 */
    private String merNo;

    /** 二级商户号 */
    private String secMerNo;

    /** 原订单交易币种 */
    private String oriCcy;

    /** 手续费支付流水，流水号 */
    private String refundFeeTransSubSeq;

    /** 付款账号类型 */
    private String refundFeePayerAcctType;

    /** 支付流水交易字符日期 */
    private String refundFeeFlowTransCharDate;

    /** 付款账号 */
    private String refundFeePayerAcctNo;

    /** 付款户名 */
    private String refundFeePayerName;

    /** 付款人开户机构名 */
    private String refundFeePayerOrgName;

    /** 付款开户行号 */
    private String refundFeePayerBankNo;

    /** 付款开户行名 */
    private String refundFeePayerBankName;

    /** 收款账号类型 */
    private String refundFeePayeeAcctType;

    /** 收款账号 */
    private String refundFeePayeeAcctNo;

    /** 收款户名 */
    private String refundFeePayeeName;

    /** 收款人开户机构名 */
    private String refundFeePayeeOrgName;

    /** 收款开户行号 */
    private String refundFeePayeeBankNo;

    /** 收款开户行名 */
    private String refundFeePayeeBankName;

    /** 手续费金额字符串，单位分 */
    private String feeCharAmt;


    public BigDecimal getHadRefundAmt() {
        return hadRefundAmt;
    }

    public void setHadRefundAmt(BigDecimal hadRefundAmt) {
        this.hadRefundAmt = hadRefundAmt;
    }

    public String getIsReturnFee() {
        return isReturnFee;
    }

    public void setIsReturnFee(String isReturnFee) {
        this.isReturnFee = isReturnFee;
    }

    public String getIsNextDayRefund() {
        return isNextDayRefund;
    }

    public void setIsNextDayRefund(String isNextDayRefund) {
        this.isNextDayRefund = isNextDayRefund;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getMerVacctNo() {
        return merVacctNo;
    }

    public void setMerVacctNo(String merVacctNo) {
        this.merVacctNo = merVacctNo;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getOriCcy() {
        return oriCcy;
    }

    public void setOriCcy(String oriCcy) {
        this.oriCcy = oriCcy;
    }

    public String getRefundFeeTransSubSeq() {
        return refundFeeTransSubSeq;
    }

    public void setRefundFeeTransSubSeq(String refundFeeTransSubSeq) {
        this.refundFeeTransSubSeq = refundFeeTransSubSeq;
    }

    public String getRefundFeePayerAcctType() {
        return refundFeePayerAcctType;
    }

    public void setRefundFeePayerAcctType(String refundFeePayerAcctType) {
        this.refundFeePayerAcctType = refundFeePayerAcctType;
    }

    public String getRefundFeeFlowTransCharDate() {
        return refundFeeFlowTransCharDate;
    }

    public void setRefundFeeFlowTransCharDate(String refundFeeFlowTransCharDate) {
        this.refundFeeFlowTransCharDate = refundFeeFlowTransCharDate;
    }

    public String getRefundFeePayerAcctNo() {
        return refundFeePayerAcctNo;
    }

    public void setRefundFeePayerAcctNo(String refundFeePayerAcctNo) {
        this.refundFeePayerAcctNo = refundFeePayerAcctNo;
    }

    public String getRefundFeePayerName() {
        return refundFeePayerName;
    }

    public void setRefundFeePayerName(String refundFeePayerName) {
        this.refundFeePayerName = refundFeePayerName;
    }

    public String getRefundFeePayerOrgName() {
        return refundFeePayerOrgName;
    }

    public void setRefundFeePayerOrgName(String refundFeePayerOrgName) {
        this.refundFeePayerOrgName = refundFeePayerOrgName;
    }

    public String getRefundFeePayerBankNo() {
        return refundFeePayerBankNo;
    }

    public void setRefundFeePayerBankNo(String refundFeePayerBankNo) {
        this.refundFeePayerBankNo = refundFeePayerBankNo;
    }

    public String getRefundFeePayerBankName() {
        return refundFeePayerBankName;
    }

    public void setRefundFeePayerBankName(String refundFeePayerBankName) {
        this.refundFeePayerBankName = refundFeePayerBankName;
    }

    public String getRefundFeePayeeAcctType() {
        return refundFeePayeeAcctType;
    }

    public void setRefundFeePayeeAcctType(String refundFeePayeeAcctType) {
        this.refundFeePayeeAcctType = refundFeePayeeAcctType;
    }

    public String getRefundFeePayeeAcctNo() {
        return refundFeePayeeAcctNo;
    }

    public void setRefundFeePayeeAcctNo(String refundFeePayeeAcctNo) {
        this.refundFeePayeeAcctNo = refundFeePayeeAcctNo;
    }

    public String getRefundFeePayeeName() {
        return refundFeePayeeName;
    }

    public void setRefundFeePayeeName(String refundFeePayeeName) {
        this.refundFeePayeeName = refundFeePayeeName;
    }

    public String getRefundFeePayeeOrgName() {
        return refundFeePayeeOrgName;
    }

    public void setRefundFeePayeeOrgName(String refundFeePayeeOrgName) {
        this.refundFeePayeeOrgName = refundFeePayeeOrgName;
    }

    public String getRefundFeePayeeBankNo() {
        return refundFeePayeeBankNo;
    }

    public void setRefundFeePayeeBankNo(String refundFeePayeeBankNo) {
        this.refundFeePayeeBankNo = refundFeePayeeBankNo;
    }

    public String getRefundFeePayeeBankName() {
        return refundFeePayeeBankName;
    }

    public void setRefundFeePayeeBankName(String refundFeePayeeBankName) {
        this.refundFeePayeeBankName = refundFeePayeeBankName;
    }

    public String getFeeCharAmt() {
        return feeCharAmt;
    }

    public void setFeeCharAmt(String feeCharAmt) {
        this.feeCharAmt = feeCharAmt;
    }
}
