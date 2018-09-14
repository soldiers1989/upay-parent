
package com.upay.dao.po.menu;
import com.pactera.dipper.po.BasePo;

public class UserMenuBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UserMenuBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MENU_ID = "菜单编号";
	public static final String ALIAS_USER_CERT_LEVEL = "用户认证级别";
	public static final String ALIAS_APPLY_CHNL_ID = "适用渠道 01-门户，02-APP，03-微信； 说明：如果适用渠道为门户和APP，则数据库保存为{01,02}";
	public static final String ALIAS_UPDATE_TIME = "关联时间";
	public static final String ALIAS_UPDATE_USER_ID = "关联用户";
	public static final String ALIAS_REG_TYPE = "注册类型 01：免密授权注册 02：普通会员注册 03：商户会员注册";
	public static final String ALIAS_MER_LEVEL = "商户级别 01：一级商户 02：二级商户";
	

	//columns START
    /**
     * 菜单编号       db_column: MENU_ID 
     */ 	
	private java.lang.String menuId;
    /**
     * 用户认证级别       db_column: USER_CERT_LEVEL 
     */ 	
	private java.lang.String userCertLevel;
    /**
     * 适用渠道 01-门户，02-APP，03-微信； 说明：如果适用渠道为门户和APP，则数据库保存为{01,02}       db_column: APPLY_CHNL_ID 
     */ 	
	private java.lang.String applyChnlId;
    /**
     * 关联时间       db_column: UPDATE_TIME 
     */ 	
	private java.util.Date updateTime;
    /**
     * 关联用户       db_column: UPDATE_USER_ID 
     */ 	
	private java.lang.String updateUserId;
    /**
     * 注册类型 01：免密授权注册 02：普通会员注册 03：商户会员注册       db_column: REG_TYPE 
     */ 	
	private java.lang.String regType;
    /**
     * 商户级别 01：一级商户 02：二级商户       db_column: MER_LEVEL 
     */ 	
	private java.lang.String merLevel;
	//columns END


	
	
	public java.lang.String getMenuId() {
		return this.menuId;
	}
	
	public void setMenuId(java.lang.String value) {
		this.menuId = value;
	}
	
	
	public java.lang.String getUserCertLevel() {
		return this.userCertLevel;
	}
	
	public void setUserCertLevel(java.lang.String value) {
		this.userCertLevel = value;
	}
	
	
	public java.lang.String getApplyChnlId() {
		return this.applyChnlId;
	}
	
	public void setApplyChnlId(java.lang.String value) {
		this.applyChnlId = value;
	}
	
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	
	public java.lang.String getUpdateUserId() {
		return this.updateUserId;
	}
	
	public void setUpdateUserId(java.lang.String value) {
		this.updateUserId = value;
	}
	
	
	public java.lang.String getRegType() {
		return this.regType;
	}
	
	public void setRegType(java.lang.String value) {
		this.regType = value;
	}
	
	
	public java.lang.String getMerLevel() {
		return this.merLevel;
	}
	
	public void setMerLevel(java.lang.String value) {
		this.merLevel = value;
	}
	

	

}

