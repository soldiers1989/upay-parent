
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrCancelBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrCancelBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户ID 系统流水号序列";
	public static final String ALIAS_CANCEL_TIME = "注销时间";
	public static final String ALIAS_CANCEL_CHNL_ID = "注销渠道 只允许柜面注销";
	public static final String ALIAS_CANCEL_MODE = "注销方式 1：用户主动注销";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 用户ID 系统流水号序列       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 注销时间       db_column: CANCEL_TIME 
     */ 	
	private java.util.Date cancelTime;
    /**
     * 注销渠道 只允许柜面注销       db_column: CANCEL_CHNL_ID 
     */ 	
	private java.lang.String cancelChnlId;
    /**
     * 注销方式 1：用户主动注销       db_column: CANCEL_MODE 
     */ 	
	private java.lang.String cancelMode;
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


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.util.Date getCancelTime() {
		return this.cancelTime;
	}
	
	public void setCancelTime(java.util.Date value) {
		this.cancelTime = value;
	}
	
	
	public java.lang.String getCancelChnlId() {
		return this.cancelChnlId;
	}
	
	public void setCancelChnlId(java.lang.String value) {
		this.cancelChnlId = value;
	}
	
	
	public java.lang.String getCancelMode() {
		return this.cancelMode;
	}
	
	public void setCancelMode(java.lang.String value) {
		this.cancelMode = value;
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

