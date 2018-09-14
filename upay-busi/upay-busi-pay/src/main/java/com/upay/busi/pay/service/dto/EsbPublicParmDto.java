package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class EsbPublicParmDto extends BaseDto {
	private String tranRetSt;
	private String retCd;
	private String retMsg;
	private String prvdSysId;
    private String prvdSysSeqNo;
    private String bankId;
    private String cardType;
    
    
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPrvdSysSeqNo() {
		return prvdSysSeqNo;
	}

	public void setPrvdSysSeqNo(String prvdSysSeqNo) {
		this.prvdSysSeqNo = prvdSysSeqNo;
	}

	public String getPrvdSysId() {
		return prvdSysId;
	}

	public void setPrvdSysId(String prvdSysId) {
		this.prvdSysId = prvdSysId;
	}

	public String getTranRetSt() {
		return tranRetSt;
	}

	public void setTranRetSt(String tranRetSt) {
		this.tranRetSt = tranRetSt;
	}

	public String getRetCd() {
		return retCd;
	}

	public void setRetCd(String retCd) {
		this.retCd = retCd;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
}
