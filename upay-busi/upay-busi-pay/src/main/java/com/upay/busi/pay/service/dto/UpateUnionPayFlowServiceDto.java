package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class UpateUnionPayFlowServiceDto extends BaseDto{
	
	private String transSubSeq;
	
	private String settleKey;
	
	private String oriRouteSeq;

	public String getOriRouteSeq() {
		return oriRouteSeq;
	}

	public void setOriRouteSeq(String oriRouteSeq) {
		this.oriRouteSeq = oriRouteSeq;
	}

	public String getSettleKey() {
		return settleKey;
	}

	public void setSettleKey(String settleKey) {
		this.settleKey = settleKey;
	}

	public String getTransSubSeq() {
		return transSubSeq;
	}

	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}
	
	
}
