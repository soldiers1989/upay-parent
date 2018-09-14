package com.upay.busi.mer.service.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;
/**
 * 查询手续费分润方法
 * 
 * @author yanzixiong
 * 
 */
public class QueryFeeAssDto extends BaseDto {
	/** 分润代码 */
	private String assCode;
	
	/** 分润名称 */
	private String assName;
	
	/** 分润方 */
	private String assId;
	
	/** 分润类型 */
	private String assKind;
	
	/** 分润方式 */
	private String assType;
	
	/** 分润比率 */
	private BigDecimal assRate;
	
	/** 固定金额 */
	private BigDecimal fixAmt;
	
	private List<Map<String, Object>> feeAssList;

	public String getAssCode() {
		return assCode;
	}

	public void setAssCode(String assCode) {
		this.assCode = assCode;
	}

	public String getAssName() {
		return assName;
	}

	public void setAssName(String assName) {
		this.assName = assName;
	}

	public String getAssId() {
		return assId;
	}

	public void setAssId(String assId) {
		this.assId = assId;
	}

	public String getAssKind() {
		return assKind;
	}

	public void setAssKind(String assKind) {
		this.assKind = assKind;
	}

	public String getAssType() {
		return assType;
	}

	public void setAssType(String assType) {
		this.assType = assType;
	}

	public BigDecimal getAssRate() {
		return assRate;
	}

	public void setAssRate(BigDecimal assRate) {
		this.assRate = assRate;
	}

	public BigDecimal getFixAmt() {
		return fixAmt;
	}

	public void setFixAmt(BigDecimal fixAmt) {
		this.fixAmt = fixAmt;
	}

	public List<Map<String, Object>> getFeeAssList() {
		return feeAssList;
	}

	public void setFeeAssList(List<Map<String, Object>> feeAssList) {
		this.feeAssList = feeAssList;
	}
	
	
}
