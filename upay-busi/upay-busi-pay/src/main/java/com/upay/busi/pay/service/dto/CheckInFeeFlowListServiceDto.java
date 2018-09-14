package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class CheckInFeeFlowListServiceDto extends BaseDto{
	/** 差错处理方式 */
	private String dealType;
	/** 差错平台流水号 */
	private String errFlowSeq;
	/** 手续费付款方 */
	private String feePayErAcctNo;
	/** 手续费收款方 */
	private String feePayEeAcctNo;
	
	private String feePaySeq;
	
	private String machineTime;
	private String machineDate;
	private String bizDate;
	private String tranFee;
	private String routeCode;
	
	private String isAddFee;//是否补手续费  Y-补 N-冲
	private String coreFeeCode;
	
	private String bankCardNo;
	private String setAccount;
	private String orgBizDate;
	private String orgBizSerialNo;
	private String amount;
	private String bizSerialNo;
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
	public String getFeePayErAcctNo() {
		return feePayErAcctNo;
	}
	public void setFeePayErAcctNo(String feePayErAcctNo) {
		this.feePayErAcctNo = feePayErAcctNo;
	}
	public String getFeePayEeAcctNo() {
		return feePayEeAcctNo;
	}
	public void setFeePayEeAcctNo(String feePayEeAcctNo) {
		this.feePayEeAcctNo = feePayEeAcctNo;
	}
	public String getFeePaySeq() {
		return feePaySeq;
	}
	public void setFeePaySeq(String feePaySeq) {
		this.feePaySeq = feePaySeq;
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
	
	public String getTranFee() {
		return tranFee;
	}
	public void setTranFee(String tranFee) {
		this.tranFee = tranFee;
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
	public String getCoreFeeCode() {
		return coreFeeCode;
	}
	public void setCoreFeeCode(String coreFeeCode) {
		this.coreFeeCode = coreFeeCode;
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
	public String getOrgBizDate() {
		return orgBizDate;
	}
	public void setOrgBizDate(String orgBizDate) {
		this.orgBizDate = orgBizDate;
	}
	public String getOrgBizSerialNo() {
		return orgBizSerialNo;
	}
	public void setOrgBizSerialNo(String orgBizSerialNo) {
		this.orgBizSerialNo = orgBizSerialNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBizSerialNo() {
		return bizSerialNo;
	}
	public void setBizSerialNo(String bizSerialNo) {
		this.bizSerialNo = bizSerialNo;
	}
	
}
