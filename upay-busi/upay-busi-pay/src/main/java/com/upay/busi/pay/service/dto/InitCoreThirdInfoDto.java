package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class InitCoreThirdInfoDto extends BaseDto {
	private String chnlTp;// esb 渠道字段
	private String payerAcctNo;// 付款方
	private String thirdFlag;
	private String thirdAccount;

	public String getPayerAcctNo() {
		return payerAcctNo;
	}

	public void setPayerAcctNo(String payerAcctNo) {
		this.payerAcctNo = payerAcctNo;
	}

	public String getChnlTp() {
		return chnlTp;
	}

	public void setChnlTp(String chnlTp) {
		this.chnlTp = chnlTp;
	}

	public String getThirdFlag() {
		return thirdFlag;
	}

	public void setThirdFlag(String thirdFlag) {
		this.thirdFlag = thirdFlag;
	}

	public String getThirdAccount() {
		return thirdAccount;
	}

	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}

}
