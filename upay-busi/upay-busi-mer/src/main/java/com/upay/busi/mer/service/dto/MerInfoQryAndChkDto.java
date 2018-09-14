/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 商户基本信息查询和状态检查
 * 
 * @author zhanggr
 * 
 */
public class MerInfoQryAndChkDto extends BaseDto {
	private String merNo;//商户号
	private String secMerNo;//二级商户号
	private String merName;//商户名+商户地址
	private String merNameResult;//商户名
	private String subMchId;//微信商户识别码
	private String promoterDeptNo;//商户所属部门
	
	
	
	public String getPromoterDeptNo() {
		return promoterDeptNo;
	}

	public void setPromoterDeptNo(String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

    public String getMerNameResult() {
        return merNameResult;
    }

    public void setMerNameResult(String merNameResult) {
        this.merNameResult = merNameResult;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

}
