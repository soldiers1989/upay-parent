package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 
 * 
 * @author:liubing
 * @CreateDate:2015年4月8日
 * 
 */
public class ZjpaySysSeqDto extends BaseDto {

    private String txSNBinding;// 流水号
    
    private String validDate;//信用卡有效期
    
    

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getTxSNBinding() {
		return txSNBinding;
	}

	public void setTxSNBinding(String txSNBinding) {
		this.txSNBinding = txSNBinding;
	}

	



}
