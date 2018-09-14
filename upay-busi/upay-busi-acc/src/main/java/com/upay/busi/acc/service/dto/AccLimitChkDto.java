/**
 * 
 */
package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * 微信单笔限额检查
 * 
 * @author zhanggr
 * 
 */
public class AccLimitChkDto extends BaseDto {

	private BigDecimal transAmt;

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

}
