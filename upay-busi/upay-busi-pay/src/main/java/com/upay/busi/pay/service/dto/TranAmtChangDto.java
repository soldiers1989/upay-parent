/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * 交易金额转换 元--〉 分
 * 
 * @author zhanggr
 * 
 */
public class TranAmtChangDto extends BaseDto {
	private String changFlag;//0：分到元  1:元到分     
	private String totalFee;//分
	private BigDecimal transAmt;//交易金额
	private String transAmtStr;//前端传入的交易金额字符串
	

	public String getTransAmtStr() {
		return transAmtStr;
	}

	public void setTransAmtStr(String transAmtStr) {
		this.transAmtStr = transAmtStr;
	}

	public String getChangFlag() {
		return changFlag;
	}

	public void setChangFlag(String changFlag) {
		this.changFlag = changFlag;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

}
