package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 维护费率信息
 * 
 * @author yanzixiong
 * 
 */
public class SecondMerSetFeeDto extends BaseDto{
	/** 收费方法名称  */
	private String feeName;
	
	/** 二级商户号  */
	private String secMerNo;
		
	/** 收费代码  */
	private String feeCode;
	
	/** 分润代码 */
	private String assCode;
	
	/** 费用收取起始日期  */
	private String startDate;
	
	/** 费用收取结止日期  */
	private String endDate;
	
	/** 优惠折扣率 */
	private String perFee;
	
	/** 费用方法ID  */
	private String feeId;
	
	/** 操作方式 01:新增 02:删除 03:修改*/
	private String operation;

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getSecMerNo() {
		return secMerNo;
	}

	public void setSecMerNo(String secMerNo) {
		this.secMerNo = secMerNo;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getAssCode() {
		return assCode;
	}

	public void setAssCode(String assCode) {
		this.assCode = assCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPerFee() {
		return perFee;
	}

	public void setPerFee(String perFee) {
		this.perFee = perFee;
	}

	public String getFeeId() {
		return feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}
