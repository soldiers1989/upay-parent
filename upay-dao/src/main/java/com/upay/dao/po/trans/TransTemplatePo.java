
package com.upay.dao.po.trans;
import com.pactera.dipper.po.BasePo;

public class TransTemplatePo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "TransTemplate";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TEMPLATE_NAME = "模版名称";
	public static final String ALIAS_TEMPLATE_DESC = "模版描述";
	public static final String ALIAS_TEMPLATE_ID = "模版ID";
	public static final String ALIAS_IS_DEFAULT = "是否是默认模版  0:不是  1：是";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_REMARK1 = "remark1";
	

	//columns START
    /**
     * 模版名称       db_column: TEMPLATE_NAME 
     */ 	
	private java.lang.String templateName;
    /**
     * 模版描述       db_column: TEMPLATE_DESC 
     */ 	
	private java.lang.String templateDesc;
    /**
     * 模版ID       db_column: TEMPLATE_ID 
     */ 	
	private java.lang.String templateId;
    /**
     * 是否是默认模版  0:不是  1：是       db_column: IS_DEFAULT 
     */ 	
	private java.lang.String isDefault;
    /**
     * remark       db_column: REMARK 
     */ 	
	private java.lang.String remark;
    /**
     * remark1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
	//columns END


	
	
	public java.lang.String getTemplateName() {
		return this.templateName;
	}
	
	public void setTemplateName(java.lang.String value) {
		this.templateName = value;
	}
	
	
	public java.lang.String getTemplateDesc() {
		return this.templateDesc;
	}
	
	public void setTemplateDesc(java.lang.String value) {
		this.templateDesc = value;
	}
	
	
	public java.lang.String getTemplateId() {
		return this.templateId;
	}
	
	public void setTemplateId(java.lang.String value) {
		this.templateId = value;
	}
	
	
	public java.lang.String getIsDefault() {
		return this.isDefault;
	}
	
	public void setIsDefault(java.lang.String value) {
		this.isDefault = value;
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
	

	

}

