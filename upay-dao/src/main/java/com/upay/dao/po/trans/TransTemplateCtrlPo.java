
package com.upay.dao.po.trans;
import com.pactera.dipper.po.BasePo;

public class TransTemplateCtrlPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "商户模板交易权限控制";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TEMPLATE_ID = "模板ID";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_PAY_TYPE = "支付方式";
	public static final String ALIAS_PAY_CARD_TYPE = "支付账户类型";
	public static final String ALIAS_CHNL_ID = "渠道代码";
	public static final String ALIAS_PAY_SERVIC = "支付服务类型";
	public static final String ALIAS_STATUS = "状态  0：启用 1：停用";
	public static final String ALIAS_DATE_LAST_MAINT = "最后更新日期";
	

	//columns START
    /**
     * 模板ID       db_column: TEMPLATE_ID 
     */ 	
	private java.lang.String templateId;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 支付方式       db_column: PAY_TYPE 
     */ 	
	private java.lang.String payType;
    /**
     * 支付账户类型       db_column: PAY_CARD_TYPE 
     */ 	
	private java.lang.String payCardType;
    /**
     * 渠道代码       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 支付服务类型       db_column: PAY_SERVIC 
     */ 	
	private java.lang.String payServic;
    /**
     * 状态  0：启用 1：停用       db_column: STATUS 
     */ 	
	private java.lang.String status;
    /**
     * 最后更新日期       db_column: DATE_LAST_MAINT 
     */ 	
	private java.util.Date dateLastMaint;
	//columns END


	
	
	public java.lang.String getTemplateId() {
		return this.templateId;
	}
	
	public void setTemplateId(java.lang.String value) {
		this.templateId = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getPayType() {
		return this.payType;
	}
	
	public void setPayType(java.lang.String value) {
		this.payType = value;
	}
	
	
	public java.lang.String getPayCardType() {
		return this.payCardType;
	}
	
	public void setPayCardType(java.lang.String value) {
		this.payCardType = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getPayServic() {
		return this.payServic;
	}
	
	public void setPayServic(java.lang.String value) {
		this.payServic = value;
	}
	
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	
	public java.util.Date getDateLastMaint() {
		return this.dateLastMaint;
	}
	
	public void setDateLastMaint(java.util.Date value) {
		this.dateLastMaint = value;
	}
	

	

}

