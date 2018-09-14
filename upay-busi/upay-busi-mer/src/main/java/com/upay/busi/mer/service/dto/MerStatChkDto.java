package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 商户状态检查
 * 
 * @author yanzixiong
 * 
 */
public class MerStatChkDto extends BaseDto {

	/**  一级商户号 */
	private String merNo;

	/** 二级商户号 */
	private String secMerNo;

	/** 商户在平台的用户ID */
	private String merUserId;
	
	
	/** 商户名称 */
	private String primaryMerName;
	
	private String promoterDeptNo;//商户所属部门
	
	
	
	public String getPromoterDeptNo() {
		return promoterDeptNo;
	}

	public void setPromoterDeptNo(String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}

	public String getQrMerId() {
		return qrMerId;
	}

	public void setQrMerId(String qrMerId) {
		this.qrMerId = qrMerId;
	}

	/** 银联商户名称 */
	private String qrMerId;
	
	
	
    

    public String getPrimaryMerName() {
        return primaryMerName;
    }

    public void setPrimaryMerName(String primaryMerName) {
        this.primaryMerName = primaryMerName;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getMerUserId() {
		return merUserId;
	}

	public void setMerUserId(String merUserId) {
		this.merUserId = merUserId;
	}
}
