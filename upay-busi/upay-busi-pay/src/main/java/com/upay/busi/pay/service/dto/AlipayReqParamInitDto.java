package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class AlipayReqParamInitDto  extends BaseDto{
	private String transSubSeq;
    private String aliasName;
    private String subject;
    public String getTransSubSeq() {
        return transSubSeq;
    }
    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
    
}
