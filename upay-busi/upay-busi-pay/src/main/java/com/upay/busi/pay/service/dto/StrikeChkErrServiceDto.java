package com.upay.busi.pay.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;

public class StrikeChkErrServiceDto extends BaseDto{
	//差错处理方式 
	private String dealType;
	//平台流水号 
	private String errFlowSeq;
	//第三方流水号
	private String thiredSeq;
	private Date tranDate;
	private String tranAmt;
	private String totalFee;
	private String coreSubSeq;
	private String subMchId;
	//中金接口
	private String zjReqInfa;
	private String bankId;
	private String accountName;
	private String accountNumber;
	private String accountType;
	private String zjRouteSeq;
	
	private String vbindAcctNo;
	private String paymentAccountName;
	private String paymentAccountNumber;
	private String certName;
	
	private String routeCode;
	private String transType;
	
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getErrFlowSeq() {
		return errFlowSeq;
	}
	public void setErrFlowSeq(String errFlowSeq) {
		this.errFlowSeq = errFlowSeq;
	}
	public Date getTranDate() {
		return tranDate;
	}
	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getThiredSeq() {
		return thiredSeq;
	}
	public void setThiredSeq(String thiredSeq) {
		this.thiredSeq = thiredSeq;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	public String getZjReqInfa() {
		return zjReqInfa;
	}
	public void setZjReqInfa(String zjReqInfa) {
		this.zjReqInfa = zjReqInfa;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getZjRouteSeq() {
		return zjRouteSeq;
	}
	public void setZjRouteSeq(String zjRouteSeq) {
		this.zjRouteSeq = zjRouteSeq;
	}
	public String getVbindAcctNo() {
		return vbindAcctNo;
	}
	public void setVbindAcctNo(String vbindAcctNo) {
		this.vbindAcctNo = vbindAcctNo;
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
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getCoreSubSeq() {
		return coreSubSeq;
	}
	public void setCoreSubSeq(String coreSubSeq) {
		this.coreSubSeq = coreSubSeq;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	
	
}
