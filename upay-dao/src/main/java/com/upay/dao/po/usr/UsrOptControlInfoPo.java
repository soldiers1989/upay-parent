
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrOptControlInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrOptControlInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_CERT_LEVEL = "用户认证等级";
	public static final String ALIAS_CHNL_ID = "渠道";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_OP_PERMISSION = "操作权限：0不可操作，1可操作";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 用户认证等级       db_column: USER_CERT_LEVEL 
     */ 	
	private java.lang.String userCertLevel;
    /**
     * 渠道       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 操作权限：0不可操作，1可操作       db_column: OP_PERMISSION 
     */ 	
	private java.lang.String opPermission;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用3       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getUserCertLevel() {
		return this.userCertLevel;
	}
	
	public void setUserCertLevel(java.lang.String value) {
		this.userCertLevel = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getOpPermission() {
		return this.opPermission;
	}
	
	public void setOpPermission(java.lang.String value) {
		this.opPermission = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	
	
	public java.lang.String getRemark2() {
		return this.remark2;
	}
	
	public void setRemark2(java.lang.String value) {
		this.remark2 = value;
	}
	
	
	public java.lang.String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(java.lang.String value) {
		this.remark3 = value;
	}
	

	

}

