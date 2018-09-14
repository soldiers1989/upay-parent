package com.upay.busi.pay.service.dto;


import java.util.Date;

import com.upay.commons.dto.BaseDto;

public class DealChkHostErrServiceDto extends BaseDto{
	
	private String thirdFlag;//第三标志
	private String thirdAccount;//第三方账号
	private String accountName;//账户名
	private String memoCode;//摘要
	
	private String dealType;//差错处理方式
	private String errFlowSeq;//差错平台流水号
	private Date tranDate;
	private String payErAcctNo;
	private String payEeAcctNo;
	private String tranAmt;
	private String tranType;
	private String coreTranCode;
	private String coreSubSeq;
	private String isReqCore;//是否去请求核心
	private String machineTime;
	private String machineDate;
	private String bizDate;
	private String debitTrantype;//信用卡交易类型1-支付；2-退款
	private String cvv2;//信用卡码
	private String vaildDate;//信用卡有效期
	private String isAddFee;//是否补手续费  Y-补 N-冲
	private String orgBizSerialNo;//核心贷记卡接口 原 交易流水
	private String orgBizDate;//核心贷记卡接口 原 交易日期
	private String routeCode;
	private String errRouteCode;
	//冲正接口
	private String amount;
	private String bankCardNo;
	private String setAccount;
	
	
	
	public String getThirdFlag() {
		return thirdFlag;
	}
	public void setThirdFlag(String thirdFlag) {
		this.thirdFlag = thirdFlag;
	}
	public String getThirdAccount() {
		return thirdAccount;
	}
	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getMemoCode() {
		return memoCode;
	}
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}
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
	public String getPayErAcctNo() {
		return payErAcctNo;
	}
	public void setPayErAcctNo(String payErAcctNo) {
		this.payErAcctNo = payErAcctNo;
	}
	public String getPayEeAcctNo() {
		return payEeAcctNo;
	}
	public void setPayEeAcctNo(String payEeAcctNo) {
		this.payEeAcctNo = payEeAcctNo;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getCoreTranCode() {
		return coreTranCode;
	}
	public void setCoreTranCode(String coreTranCode) {
		this.coreTranCode = coreTranCode;
	}
	public String getCoreSubSeq() {
		return coreSubSeq;
	}
	public void setCoreSubSeq(String coreSubSeq) {
		this.coreSubSeq = coreSubSeq;
	}
	public String getIsReqCore() {
		return isReqCore;
	}
	public void setIsReqCore(String isReqCore) {
		this.isReqCore = isReqCore;
	}
	public String getMachineTime() {
		return machineTime;
	}
	public void setMachineTime(String machineTime) {
		this.machineTime = machineTime;
	}
	public String getMachineDate() {
		return machineDate;
	}
	public void setMachineDate(String machineDate) {
		this.machineDate = machineDate;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	public String getDebitTrantype() {
		return debitTrantype;
	}
	public void setDebitTrantype(String debitTrantype) {
		this.debitTrantype = debitTrantype;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getVaildDate() {
		return vaildDate;
	}
	public void setVaildDate(String vaildDate) {
		this.vaildDate = vaildDate;
	}
	public String getIsAddFee() {
		return isAddFee;
	}
	public void setIsAddFee(String isAddFee) {
		this.isAddFee = isAddFee;
	}
	
	public String getOrgBizSerialNo() {
		return orgBizSerialNo;
	}
	public void setOrgBizSerialNo(String orgBizSerialNo) {
		this.orgBizSerialNo = orgBizSerialNo;
	}
	public String getOrgBizDate() {
		return orgBizDate;
	}
	public void setOrgBizDate(String orgBizDate) {
		this.orgBizDate = orgBizDate;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getErrRouteCode() {
		return errRouteCode;
	}
	public void setErrRouteCode(String errRouteCode) {
		this.errRouteCode = errRouteCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getSetAccount() {
		return setAccount;
	}
	public void setSetAccount(String setAccount) {
		this.setAccount = setAccount;
	}
	
	
}
