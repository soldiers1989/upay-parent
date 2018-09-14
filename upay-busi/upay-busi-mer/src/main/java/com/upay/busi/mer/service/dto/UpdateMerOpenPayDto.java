package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class UpdateMerOpenPayDto extends BaseDto{
	private String updateFlag;//1:微信商户  2：微信回调地址配置  3：绑定APPID 4:配置appid   5:支付宝    6：银联
	private String merNo;
	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	
	
}
