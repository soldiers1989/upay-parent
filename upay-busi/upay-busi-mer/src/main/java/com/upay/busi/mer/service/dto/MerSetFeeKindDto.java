package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;
/**
 * 一级商户维护二级商户手续费计算方法
 * 
 * @author yanzixiong
 * 
 */
public class MerSetFeeKindDto extends BaseDto {
	/** 操作方式 01:新增  03:修改*/
	private String operation;
	
	/** 收费代码 */
	private String feeCode;
	
	/** 收费名称 */
	private String feeName;
	
	/** 手续费收取方式 */
	private String feeMode;
	
	/** 单笔固定金额 */
    private String fixFee;

    /** 按交易金额比例,该值要除以100展示，如15，表示15% */
    private String rationFee;

    /** 手续费上限 */
    private String highFee;

    /** 手续费下限 */
    private String lowFee;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getFeeMode() {
		return feeMode;
	}

	public void setFeeMode(String feeMode) {
		this.feeMode = feeMode;
	}

	public String getFixFee() {
		return fixFee;
	}

	public void setFixFee(String fixFee) {
		this.fixFee = fixFee;
	}

	public String getRationFee() {
		return rationFee;
	}

	public void setRationFee(String rationFee) {
		this.rationFee = rationFee;
	}

	public String getHighFee() {
		return highFee;
	}

	public void setHighFee(String highFee) {
		this.highFee = highFee;
	}

	public String getLowFee() {
		return lowFee;
	}

	public void setLowFee(String lowFee) {
		this.lowFee = lowFee;
	}

	
    
    
}
