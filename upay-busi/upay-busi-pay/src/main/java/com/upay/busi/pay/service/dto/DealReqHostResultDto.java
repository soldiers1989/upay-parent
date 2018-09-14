package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class DealReqHostResultDto extends BaseDto{
	private String bkDate;//核心日期
	private String bkSerialNo;//核心流水号
	private String respCode;//核心状态码
	private String dbalance;//核心返回可用余额 08007
	private String bal;//核心返回可用余额 08008
	private String queryDate;
	
	private String acctBalance;
	public String getBkDate() {
		return bkDate;
	}
	public void setBkDate(String bkDate) {
		this.bkDate = bkDate;
	}
	public String getBkSerialNo() {
		return bkSerialNo;
	}
	public void setBkSerialNo(String bkSerialNo) {
		this.bkSerialNo = bkSerialNo;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getDbalance() {
		return dbalance;
	}
	public void setDbalance(String dbalance) {
		this.dbalance = dbalance;
	}
	
	
	public String getAcctBalance() {
		return acctBalance;
	}
	public void setAcctBalance(String acctBalance) {
		this.acctBalance = acctBalance;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	
	
}
