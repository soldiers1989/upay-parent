package com.upay.busi.pay.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;

public class CollectionOpenTokenQueryDto extends BaseDto {
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCardBinType() {
		return cardBinType;
	}
	public void setCardBinType(String cardBinType) {
		this.cardBinType = cardBinType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTrid() {
		return trid;
	}
	public void setTrid(String trid) {
		this.trid = trid;
	}
	public String getTokenlevel() {
		return tokenlevel;
	}
	public void setTokenlevel(String tokenlevel) {
		this.tokenlevel = tokenlevel;
	}
	public String getTokenbegin() {
		return tokenbegin;
	}
	public void setTokenbegin(String tokenbegin) {
		this.tokenbegin = tokenbegin;
	}
	public String getTokenend() {
		return tokenend;
	}
	public void setTokenend(String tokenend) {
		this.tokenend = tokenend;
	}
	public String getTokentype() {
		return tokentype;
	}
	public void setTokentype(String tokentype) {
		this.tokentype = tokentype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBindacctno() {
		return bindacctno;
	}
	public void setBindacctno(String bindacctno) {
		this.bindacctno = bindacctno;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public String getCvn2() {
		return cvn2;
	}
	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getCertifTp() {
		return certifTp;
	}
	public void setCertifTp(String certifTp) {
		this.certifTp = certifTp;
	}
	public String getCertifId() {
		return certifId;
	}
	public void setCertifId(String certifId) {
		this.certifId = certifId;
	}
	public String getCustomerNm() {
		return customerNm;
	}
	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}

	private String phone;
 	
	private String cardBinType;
	
	private String token;
	
	private String trid;
	
	private String tokenlevel;
	
	private String tokenbegin;
	
	private String tokenend;
	
	private String tokentype;

	private String status;

	private String bindacctno;
	
	private Date createDate;
	
	private String cvn2;
 	
	private String expired;
	
	private String certifTp;
	
	private String certifId;
	
	private String customerNm;

	private String pin;
	
	private String respCode;
	
	private String flay;
	
	private String activateStatus;
	
	private String payCardType;
	
	private String accNo;
		private String applyFlag;

	public String getApplyFlag() {
		return applyFlag;
	}

	public void setApplyFlag(String applyFlag) {
		this.applyFlag = applyFlag;
	}

	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getPayCardType() {
		return payCardType;
	}
	public void setPayCardType(String payCardType) {
		this.payCardType = payCardType;
	}
	public String getActivateStatus() {
		return activateStatus;
	}
	public void setActivateStatus(String activateStatus) {
		this.activateStatus = activateStatus;
	}
	public String getFlay() {
		return flay;
	}
	public void setFlay(String flay) {
		this.flay = flay;
	}

	private String tokenPayDataQuery;
	
	public String getTokenPayDataQuery() {
		return tokenPayDataQuery;
	}
	public void setTokenPayDataQuery(String tokenPayDataQuery) {
		this.tokenPayDataQuery = tokenPayDataQuery;
	}
	public String getExistRecord() {
		return existRecord;
	}
	public void setExistRecord(String existRecord) {
		this.existRecord = existRecord;
	}

	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	private String existRecord;
	
    private String phoneNo;

	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
