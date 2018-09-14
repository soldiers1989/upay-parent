
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerTransTemplatePo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerTransTemplate";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_TEMPLATE_ID = "模版ID";
	public static final String ALIAS_STATUS = "启用状态   0：启用  1：停用";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_REMARK1 = "remark1";
	public static final String ALIAS_MER_TRANS_CTRL_CODE = "权限控制代码";
	public static final String ALIAS_DAILY_ACMLATIVE_AMT = "日累计金额";
	public static final String ALIAS_DAILY_ACMLATIVE_LIMIT = "日累计限额";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 模版ID       db_column: TEMPLATE_ID 
     */ 	
	private java.lang.String templateId;
    /**
     * 启用状态   0：启用  1：停用       db_column: STATUS 
     */ 	
	private java.lang.String status;
    /**
     * remark       db_column: REMARK 
     */ 	
	private java.lang.String remark;
    /**
     * remark1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 权限控制代码       db_column: MER_TRANS_CTRL_CODE 
     */ 	
	private java.lang.String merTransCtrlCode;
    /**
     * 日累计金额       db_column: DAILY_ACMLATIVE_AMT 
     */ 	
	private java.math.BigDecimal dailyAcmlativeAmt;
    /**
     * 日累计限额       db_column: DAILY_ACMLATIVE_LIMIT 
     */ 	
	private java.math.BigDecimal dailyAcmlativeLimit;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getTemplateId() {
		return this.templateId;
	}
	
	public void setTemplateId(java.lang.String value) {
		this.templateId = value;
	}
	
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	
	
	public java.lang.String getMerTransCtrlCode() {
		return this.merTransCtrlCode;
	}
	
	public void setMerTransCtrlCode(java.lang.String value) {
		this.merTransCtrlCode = value;
	}
	
	
	public java.math.BigDecimal getDailyAcmlativeAmt() {
		return this.dailyAcmlativeAmt;
	}
	
	public void setDailyAcmlativeAmt(java.math.BigDecimal value) {
		this.dailyAcmlativeAmt = value;
	}
	
	
	public java.math.BigDecimal getDailyAcmlativeLimit() {
		return this.dailyAcmlativeLimit;
	}
	
	public void setDailyAcmlativeLimit(java.math.BigDecimal value) {
		this.dailyAcmlativeLimit = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

