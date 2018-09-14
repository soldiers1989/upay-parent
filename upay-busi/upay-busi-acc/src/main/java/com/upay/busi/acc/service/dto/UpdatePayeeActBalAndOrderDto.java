/**
 * 
 */
package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年12月26日
 */
public class UpdatePayeeActBalAndOrderDto extends BaseDto {
    private String payeeVacctNo;//收款 人虚拟账号
    private BigDecimal transAmt;//交易金额
    private String payeeOrderNo;//收款订单号
	
	public String getPayeeVacctNo() {
		return payeeVacctNo;
	}
	public void setPayeeVacctNo(String payeeVacctNo) {
		this.payeeVacctNo = payeeVacctNo;
	}
	public BigDecimal getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}
	public String getPayeeOrderNo() {
		return payeeOrderNo;
	}
	public void setPayeeOrderNo(String payeeOrderNo) {
		this.payeeOrderNo = payeeOrderNo;
	}
    
    
}
