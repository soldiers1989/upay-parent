package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class InitParamRefundDto extends BaseDto{
	private String merNo;
	private String merStlAccNo;
	private String merStlAccType;//结算账户类型
	private String merAddOrSub;//增减标志
	private String accountType;//账户类型（中金）
	private String certName;
	private String bankId;//机构编号
	private String paymentAccountName;//账户名称
	private String paymentAccountNumber;//账户号
	private String settlementFlag;//结算标志
	private String refundAmt;;//退款金额
	private String reqzjFlag;
	private String zjVbindAcctNo;
	private String zjRouteCode;
	private String zjPayErAcctType;
	private String zjPayEeAcctType;
	private String zjTransAccount;
	
	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getMerStlAccNo() {
		return merStlAccNo;
	}

	public void setMerStlAccNo(String merStlAccNo) {
		this.merStlAccNo = merStlAccNo;
	}

	public String getMerStlAccType() {
		return merStlAccType;
	}

	public void setMerStlAccType(String merStlAccType) {
		this.merStlAccType = merStlAccType;
	}

	public String getMerAddOrSub() {
		return merAddOrSub;
	}

	public void setMerAddOrSub(String merAddOrSub) {
		this.merAddOrSub = merAddOrSub;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getPaymentAccountName() {
		return paymentAccountName;
	}

	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}

	public String getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	public void setPaymentAccountNumber(String paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}

	public String getSettlementFlag() {
		return settlementFlag;
	}

	public void setSettlementFlag(String settlementFlag) {
		this.settlementFlag = settlementFlag;
	}

	public String getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getReqzjFlag() {
		return reqzjFlag;
	}

	public void setReqzjFlag(String reqzjFlag) {
		this.reqzjFlag = reqzjFlag;
	}

	public String getZjVbindAcctNo() {
		return zjVbindAcctNo;
	}

	public void setZjVbindAcctNo(String zjVbindAcctNo) {
		this.zjVbindAcctNo = zjVbindAcctNo;
	}

	public String getZjRouteCode() {
		return zjRouteCode;
	}

	public void setZjRouteCode(String zjRouteCode) {
		this.zjRouteCode = zjRouteCode;
	}

	public String getZjPayErAcctType() {
		return zjPayErAcctType;
	}

	public void setZjPayErAcctType(String zjPayErAcctType) {
		this.zjPayErAcctType = zjPayErAcctType;
	}

	public String getZjPayEeAcctType() {
		return zjPayEeAcctType;
	}

	public void setZjPayEeAcctType(String zjPayEeAcctType) {
		this.zjPayEeAcctType = zjPayEeAcctType;
	}

	public String getZjTransAccount() {
		return zjTransAccount;
	}

	public void setZjTransAccount(String zjTransAccount) {
		this.zjTransAccount = zjTransAccount;
	}
	
	
}
