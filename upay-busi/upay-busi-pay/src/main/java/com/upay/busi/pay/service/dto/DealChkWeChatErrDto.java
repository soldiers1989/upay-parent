package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;

public class DealChkWeChatErrDto extends BaseDto{
	/** 差错处理方式 */
	private String dealType;
	/** 差错平台流水号 */
	private String errFlowSeq;
	private Date tranDate;
	private String payErAcctNo;
	private String payEeAcctNo;
	private String tranAmt;
	private String tranType;
	private String coreTranCode;
	private String coreSubSeq;
	private String isReqCore;
	//是否补手续费  Y-补 N-冲
	private String isAddFee;
	private String routeCode;
	private String machineTime;
	private String machineDate;
	private String bizDate;
	private String errRouteCode;
	
	private String amount;
	private String bankCardNo;
	private String setAccount;
	
	private String orgBizSerialNo;//核心贷记卡接口 原 交易流水
	private String orgBizDate;//核心贷记卡接口 原 交易日期
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
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	
	public String getIsAddFee() {
		return isAddFee;
	}
	public void setIsAddFee(String isAddFee) {
		this.isAddFee = isAddFee;
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
	
	
}
