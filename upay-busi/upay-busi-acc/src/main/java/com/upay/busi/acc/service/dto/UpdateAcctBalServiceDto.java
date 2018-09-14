package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

public class UpdateAcctBalServiceDto extends BaseDto {

	private String vAcctNo;// 虚拟账号
	
	private BigDecimal transAmt;// 交易金额
	
	private String operFlag;// 操作标志 1 余额加 2 余额减

	private String routeType;//资金通道
	
	private String respCode;//核心状态
	
	private String status;//中金状态
	
	private BigDecimal feeAmt;//手续费
	
	
	
	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getvAcctNo() {
		return vAcctNo;
	}

	public void setvAcctNo(String vAcctNo) {
		this.vAcctNo = vAcctNo;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

}
