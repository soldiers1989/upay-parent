package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款支付流水DTO
 * @author zhangjianfeng
 * @since 2016/08/19 00:24
 */
public class RefundPayFlowDto extends BaseDto {
    
    private boolean merStlFlag;
    
    
    private String txSNBinding;//中金绑定流水号
    /** 原订单用户ID */
    private String oriUserId;
    /** 是否自定义付款方      Y-是，N-不是 */
    private String isUseOriPayer;
    /** 是否自定义收款方      Y-是，N-不是 */
    private String isUseOriPayee;
    /** 重复退款标识 */
    private String refundRepeatFlag;
    /** 虚拟账号 */
    private String accNo;
    /** 通道方流水号 */
    private String routeSeq;
    
    /** 安全码 */
    private String cvv2;
    /** 有效期 */
    private String validdate;
    /** 退款金额 */
    private BigDecimal transAmt;
    /** 商户号 */
    private String merNo;
    /** 二级商户号 */
    private String secMerNo;
    /** 退着代码 */
    private String routeCode;
    /** 退款描述 */
    private String refundDes;
    /** 原订单交易币种 */
    private String oriCcy;
    /** 支付平台订单号 */
    private String oriOrderNo;
    /** 支付平台退款订单号 */
    private String OrderNo;
    /** 原订单结算标志 */
    private String oriStlFlag;
    /** 退款金额，单位：分 */
    private String refundAmt;
    /** 是否隔日退货：0-否；1-是； */
    private String isNextDayRefund;

    /** 原支付流水明细流水号 */
    private String oriTransSubSeq;
    /** 原支付流水交易字符日期 */
    private String oriFlowTransCharDate;
    /** 原交易通道字符日期 */
    private String oriRouteCharDate;
    /** 原交易通首流水 */
    private String oriRouteSeq;
    /** 付款账号类型 */
    private String payerAcctType;
    /** 支付流水明细流水号 */
    private String transSubSeq;
    /** 支付流水交易字符日期 */
    private String flowTransCharDate;
    /** 付款账号 */
    private String payerAccNo;
    /** 付款户名 */
    private String payerName;
    /** 付款人开户机构名 */
    private String payerOrgName;
    /** 付款开户行号 */
    private String payerBankNo;
    /** 付款开户行名 */
    private String payerBankName;
    /** 收款账号类型 */
    private String payeeAccType;
    /** 收款账号 */
    private String payeeAccNo;
    /** 收款户名 */
    private String payeeName;
    /** 收款人开户机构名 */
    private String payeeOrgName;
    /** 收款开户行号 */
    private String payeeBankNo;
    /** 收款开户行名 */
    private String payeeBankName;
    
    private String isEsbCore;

    public String getIsEsbCore() {
		return isEsbCore;
	}
	public void setIsEsbCore(String isEsbCore) {
		this.isEsbCore = isEsbCore;
	}

	private java.lang.String promoterDeptNo;//订单所属于银行哪个机构
    
    
	public java.lang.String getPromoterDeptNo() {
		return promoterDeptNo;
	}
	public void setPromoterDeptNo(java.lang.String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}
    
    
    
    

    public boolean isMerStlFlag() {
        return merStlFlag;
    }

    public void setMerStlFlag(boolean merStlFlag) {
        this.merStlFlag = merStlFlag;
    }

    public String getTxSNBinding() {
        return txSNBinding;
    }

    public void setTxSNBinding(String txSNBinding) {
        this.txSNBinding = txSNBinding;
    }

    public String getOriUserId() {
        return oriUserId;
    }

    public void setOriUserId(String oriUserId) {
        this.oriUserId = oriUserId;
    }

    public String getRefundRepeatFlag() {
        return refundRepeatFlag;
    }

    public void setRefundRepeatFlag(String refundRepeatFlag) {
        this.refundRepeatFlag = refundRepeatFlag;
    }

    public String getRouteSeq() {
        return routeSeq;
    }

    public void setRouteSeq(String routeSeq) {
        this.routeSeq = routeSeq;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValiddate() {
        return validdate;
    }

    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getIsUseOriPayer() {
        return isUseOriPayer;
    }

    public void setIsUseOriPayer(String isUseOriPayer) {
        this.isUseOriPayer = isUseOriPayer;
    }

    public String getIsUseOriPayee() {
        return isUseOriPayee;
    }

    public void setIsUseOriPayee(String isUseOriPayee) {
        this.isUseOriPayee = isUseOriPayee;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getRefundDes() {
        return refundDes;
    }

    public void setRefundDes(String refundDes) {
        this.refundDes = refundDes;
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

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getOriCcy() {
        return oriCcy;
    }

    public void setOriCcy(String oriCcy) {
        this.oriCcy = oriCcy;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    
    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getOriStlFlag() {
        return oriStlFlag;
    }

    public void setOriStlFlag(String oriStlFlag) {
        this.oriStlFlag = oriStlFlag;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getPayerAcctType() {
        return payerAcctType;
    }

    public void setPayerAcctType(String payerAcctType) {
        this.payerAcctType = payerAcctType;
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

    
    public String getPayerAccNo() {
        return payerAccNo;
    }

    public void setPayerAccNo(String payerAccNo) {
        this.payerAccNo = payerAccNo;
    }

    public String getPayeeAccType() {
        return payeeAccType;
    }

    public void setPayeeAccType(String payeeAccType) {
        this.payeeAccType = payeeAccType;
    }

    public String getPayeeAccNo() {
        return payeeAccNo;
    }

    public void setPayeeAccNo(String payeeAccNo) {
        this.payeeAccNo = payeeAccNo;
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

    public String getOriRouteSeq() {
        return oriRouteSeq;
    }

    public void setOriRouteSeq(String oriRouteSeq) {
        this.oriRouteSeq = oriRouteSeq;
    }

    public String getOriTransSubSeq() {
        return oriTransSubSeq;
    }

    public void setOriTransSubSeq(String oriTransSubSeq) {
        this.oriTransSubSeq = oriTransSubSeq;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

    public String getOriFlowTransCharDate() {
        return oriFlowTransCharDate;
    }

    public void setOriFlowTransCharDate(String oriFlowTransCharDate) {
        this.oriFlowTransCharDate = oriFlowTransCharDate;
    }

    public String getFlowTransCharDate() {
        return flowTransCharDate;
    }

    public void setFlowTransCharDate(String flowTransCharDate) {
        this.flowTransCharDate = flowTransCharDate;
    }

    public String getOriRouteCharDate() {
        return oriRouteCharDate;
    }

    public void setOriRouteCharDate(String oriRouteCharDate) {
        this.oriRouteCharDate = oriRouteCharDate;
    }

    public String getIsNextDayRefund() {
        return isNextDayRefund;
    }

    public void setIsNextDayRefund(String isNextDayRefund) {
        this.isNextDayRefund = isNextDayRefund;
    }
}
