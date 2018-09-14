package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author wangzhenxing
 * 2016年11月28日
 */
public class CheckParamDto extends BaseDto {
	
	/**
	 * 卡号
	 */
	private String eBindAcctNo;
	/**
	 * 账号名称
	 */
	private String acctName;
	
	
	/**
	 * 证件号码
	 */
	private String certNo;
	
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 证件类型
	 */
	private String certType;
	
	/**
	 * 账号类型
	 */
	private String bindAcctType;
	/**
	 *	姓名
	 */
	private String certName;
	
	private String mobilePhone;
	private String accountName;
	private String bankCardNo;
	/**
	 * IP
	 */
	private String spbillCreateIp;
    
    private String machineDate;//自然日期(必输)
    
    private String machineTime;//自然时间(必输)
    
    private String bizDate;//业务日期(必输)
    
    private String bizSerialNo;//业务流水号(必输)

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String geteBindAcctNo() {
		return eBindAcctNo;
	}

	public void seteBindAcctNo(String eBindAcctNo) {
		this.eBindAcctNo = eBindAcctNo;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getBindAcctType() {
		return bindAcctType;
	}

	public void setBindAcctType(String bindAcctType) {
		this.bindAcctType = bindAcctType;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getMachineDate() {
		return machineDate;
	}

	public void setMachineDate(String machineDate) {
		this.machineDate = machineDate;
	}

	public String getMachineTime() {
		return machineTime;
	}

	public void setMachineTime(String machineTime) {
		this.machineTime = machineTime;
	}

	public String getBizDate() {
		return bizDate;
	}

	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}

	public String getBizSerialNo() {
		return bizSerialNo;
	}

	public void setBizSerialNo(String bizSerialNo) {
		this.bizSerialNo = bizSerialNo;
	}
    
    
}
