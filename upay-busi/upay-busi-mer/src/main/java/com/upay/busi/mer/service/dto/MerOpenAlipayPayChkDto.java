package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class MerOpenAlipayPayChkDto extends BaseDto{
	private String merNo;//商户号
	private String merName;//商户名称
	private String aliasName;//商户简称
	private String servicePhone;//商户客服电话
	private String categoryId;//经营类目
	private String source;//受理商户来源标识
	private String externalId;//受理商户编号，由受理机构定义，需要保证在受理机构下唯一
	private String alipaySource;
	private String reqType;//请求类型
	private String subMerchantId;
	
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
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getAlipaySource() {
		return alipaySource;
	}
	public void setAlipaySource(String alipaySource) {
		this.alipaySource = alipaySource;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	
	
	
	
	
}
