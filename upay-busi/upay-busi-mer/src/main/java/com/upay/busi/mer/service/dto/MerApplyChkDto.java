package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 商户申请检查
 * 
 * @author yanzixiong
 * 
 */ 
public class MerApplyChkDto extends BaseDto {
	/** 商户状态 */
	private String merStat;
	/** 商户申请日期 */
	private String applyDate;
	/** 审核返回 */
	private String answerApply;
	/** 商户申请编号 */
	private String merApplyNo;
	/** 商户号 */
	private String merNo;
	
	private String parentMerNo;
	/** 商户名 */
	private String merName;
	
	private String contact;

	/**
	 * 状态   00 代表新增   11代表修改
	 */
	private java.lang.String state;
	//columns END


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

	public String getParentMerNo() {
		return parentMerNo;
	}

	public void setParentMerNo(String parentMerNo) {
		this.parentMerNo = parentMerNo;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMerStat() {
		return merStat;
	}

	public void setMerStat(String merStat) {
		this.merStat = merStat;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getAnswerApply() {
		return answerApply;
	}

	public void setAnswerApply(String answerApply) {
		this.answerApply = answerApply;
	}

	public String getMerApplyNo() {
		return merApplyNo;
	}

	public void setMerApplyNo(String merApplyNo) {
		this.merApplyNo = merApplyNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}
}
