package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 
 * liubing
 * 检查 绑定信用卡 有效期和安全码
 */
public class CheckBindCardServiceDto extends BaseDto {

	private String bindAcctType;//绑定卡类型
    private String validDate;//有效期
    private String cvn2;//安全码
	public String getBindAcctType() {
		return bindAcctType;
	}
	public void setBindAcctType(String bindAcctType) {
		this.bindAcctType = bindAcctType;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getCvn2() {
		return cvn2;
	}
	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

}
