package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

public class UserInfoCheckDto extends BaseDto{
	private String eBindAcctNo;
	private String certName;
	private String certNo;
	private String reserveMobile;
	public String geteBindAcctNo() {
		return eBindAcctNo;
	}
	public void seteBindAcctNo(String eBindAcctNo) {
		this.eBindAcctNo = eBindAcctNo;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getReserveMobile() {
		return reserveMobile;
	}
	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
}
