
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerTransLimitPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerTransLimit";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_TRANS_CTRL_CODE = "权限控制代码";
	public static final String ALIAS_TRANS_LIMIT = "每笔限额";
	public static final String ALIAS_STATUS = "控制状态  0:启用   1:关闭";
	public static final String ALIAS_OPEN_DATE = "启用日期";
	public static final String ALIAS_OPEN_USER_ID = "启用用户号";
	public static final String ALIAS_CLOSE_DATE = "关闭日期";
	public static final String ALIAS_CLOSE_USER_ID = "关闭用户号";
	public static final String ALIAS_MODIFY_USER_ID = "最后修改用户";
	public static final String ALIAS_DATE_LAST_MAINT = "最后更新日期";
	public static final String ALIAS_USER_TYPE = "0普通用户，1商户用户，2不限制";
	public static final String ALIAS_IS_DEFAULT = "是否默认";
	

	//columns START
    /**
     * 权限控制代码       db_column: MER_TRANS_CTRL_CODE 
     */ 	
	private java.lang.String merTransCtrlCode;
    /**
     * 每笔限额       db_column: TRANS_LIMIT 
     */ 	
	private java.math.BigDecimal transLimit;
    /**
     * 控制状态  0:启用   1:关闭       db_column: STATUS 
     */ 	
	private java.lang.String status;
    /**
     * 启用日期       db_column: OPEN_DATE 
     */ 	
	private java.util.Date openDate;
    /**
     * 启用用户号       db_column: OPEN_USER_ID 
     */ 	
	private java.lang.String openUserId;
    /**
     * 关闭日期       db_column: CLOSE_DATE 
     */ 	
	private java.util.Date closeDate;
    /**
     * 关闭用户号       db_column: CLOSE_USER_ID 
     */ 	
	private java.lang.String closeUserId;
    /**
     * 最后修改用户       db_column: MODIFY_USER_ID 
     */ 	
	private java.lang.String modifyUserId;
    /**
     * 最后更新日期       db_column: DATE_LAST_MAINT 
     */ 	
	private java.util.Date dateLastMaint;
    /**
     * 0普通用户，1商户用户，2不限制       db_column: USER_TYPE 
     */ 	
	private java.lang.String userType;
    /**
     * 是否默认       db_column: IS_DEFAULT 
     */ 	
	private java.lang.String isDefault;
	//columns END


	
	
	public java.lang.String getMerTransCtrlCode() {
		return this.merTransCtrlCode;
	}
	
	public void setMerTransCtrlCode(java.lang.String value) {
		this.merTransCtrlCode = value;
	}
	
	
	public java.math.BigDecimal getTransLimit() {
		return this.transLimit;
	}
	
	public void setTransLimit(java.math.BigDecimal value) {
		this.transLimit = value;
	}
	
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	
	public java.util.Date getOpenDate() {
		return this.openDate;
	}
	
	public void setOpenDate(java.util.Date value) {
		this.openDate = value;
	}
	
	
	public java.lang.String getOpenUserId() {
		return this.openUserId;
	}
	
	public void setOpenUserId(java.lang.String value) {
		this.openUserId = value;
	}
	
	
	public java.util.Date getCloseDate() {
		return this.closeDate;
	}
	
	public void setCloseDate(java.util.Date value) {
		this.closeDate = value;
	}
	
	
	public java.lang.String getCloseUserId() {
		return this.closeUserId;
	}
	
	public void setCloseUserId(java.lang.String value) {
		this.closeUserId = value;
	}
	
	
	public java.lang.String getModifyUserId() {
		return this.modifyUserId;
	}
	
	public void setModifyUserId(java.lang.String value) {
		this.modifyUserId = value;
	}
	
	
	public java.util.Date getDateLastMaint() {
		return this.dateLastMaint;
	}
	
	public void setDateLastMaint(java.util.Date value) {
		this.dateLastMaint = value;
	}
	
	
	public java.lang.String getUserType() {
		return this.userType;
	}
	
	public void setUserType(java.lang.String value) {
		this.userType = value;
	}
	
	
	public java.lang.String getIsDefault() {
		return this.isDefault;
	}
	
	public void setIsDefault(java.lang.String value) {
		this.isDefault = value;
	}
	

	

}

