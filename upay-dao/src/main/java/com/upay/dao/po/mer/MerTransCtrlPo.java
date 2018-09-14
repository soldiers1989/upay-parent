
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerTransCtrlPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerTransCtrl";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_TRANS_CODE = "交易代码  平台交易码";
	public static final String ALIAS_PAY_TYPE = "支付方式  参见附录支付方式";
	public static final String ALIAS_PAY_CARD_TYPE = "支付账户类型   0:虚拟账户 1:借记卡 2:贷记卡";
	public static final String ALIAS_CHNL_ID = "渠道代码 参见附录渠道代码";
	public static final String ALIAS_PAY_SERVIC = "支付服务类型  详见附录支付服务类型";
	public static final String ALIAS_STATUS = "状态  0：启用 1：停用";
	public static final String ALIAS_MER_TRANS_CTRL_CODE = "商户权限控制代码  平台唯一规则";
	public static final String ALIAS_DATE_LAST_MAINT = "最后更新日期";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 交易代码  平台交易码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 支付方式  参见附录支付方式       db_column: PAY_TYPE 
     */ 	
	private java.lang.String payType;
    /**
     * 支付账户类型   0:虚拟账户 1:借记卡 2:贷记卡       db_column: PAY_CARD_TYPE 
     */ 	
	private java.lang.String payCardType;
    /**
     * 渠道代码 参见附录渠道代码       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 支付服务类型  详见附录支付服务类型       db_column: PAY_SERVIC 
     */ 	
	private java.lang.String payServic;
    /**
     * 状态  0：启用 1：停用       db_column: STATUS 
     */ 	
	private java.lang.String status;
    /**
     * 商户权限控制代码  平台唯一规则       db_column: MER_TRANS_CTRL_CODE 
     */ 	
	private java.lang.String merTransCtrlCode;
    /**
     * 最后更新日期       db_column: DATE_LAST_MAINT 
     */ 	
	private java.util.Date dateLastMaint;
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
	
	
	public java.lang.String getMerTransCtrlCode() {
		return this.merTransCtrlCode;
	}
	
	public void setMerTransCtrlCode(java.lang.String value) {
		this.merTransCtrlCode = value;
	}
	
	
	public java.util.Date getDateLastMaint() {
		return this.dateLastMaint;
	}
	
	public void setDateLastMaint(java.util.Date value) {
		this.dateLastMaint = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

