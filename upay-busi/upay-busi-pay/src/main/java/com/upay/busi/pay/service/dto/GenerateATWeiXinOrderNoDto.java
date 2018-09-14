package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class GenerateATWeiXinOrderNoDto extends BaseDto{
	private String transSubSeq;

	public String getTransSubSeq() {
		return transSubSeq;
	}

	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}


}
