
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayRoutePermInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayRoutePermInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ROUTE_CODE = "通道代码";
	public static final String ALIAS_ROUTE_FUNC_CODE = "功能代码 01-账户鉴权02-充值03-提现";
	public static final String ALIAS_TRANS_SYNC_FLAG = "交易同步/异步标识 1、同步2、异步";
	public static final String ALIAS_ROUTE_STAT = "通道状态 1：正常 0：停用";
	public static final String ALIAS_STAT_TIME = "开放开始时间";
	public static final String ALIAS_END_TIME = "开放截止时间";
	public static final String ALIAS_ROUTE_LMTBAL = "通道头寸";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 通道代码       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 功能代码 01-账户鉴权02-充值03-提现       db_column: ROUTE_FUNC_CODE 
     */ 	
	private java.lang.String routeFuncCode;
    /**
     * 交易同步/异步标识 1、同步2、异步       db_column: TRANS_SYNC_FLAG 
     */ 	
	private java.lang.String transSyncFlag;
    /**
     * 通道状态 1：正常 0：停用       db_column: ROUTE_STAT 
     */ 	
	private java.lang.String routeStat;
    /**
     * 开放开始时间       db_column: STAT_TIME 
     */ 	
	private java.lang.String statTime;
    /**
     * 开放截止时间       db_column: END_TIME 
     */ 	
	private java.lang.String endTime;
    /**
     * 通道头寸       db_column: ROUTE_LMTBAL 
     */ 	
	private java.math.BigDecimal routeLmtbal;
    /**
     * 最后变更时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
	//columns END


	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getRouteFuncCode() {
		return this.routeFuncCode;
	}
	
	public void setRouteFuncCode(java.lang.String value) {
		this.routeFuncCode = value;
	}
	
	
	public java.lang.String getTransSyncFlag() {
		return this.transSyncFlag;
	}
	
	public void setTransSyncFlag(java.lang.String value) {
		this.transSyncFlag = value;
	}
	
	
	public java.lang.String getRouteStat() {
		return this.routeStat;
	}
	
	public void setRouteStat(java.lang.String value) {
		this.routeStat = value;
	}
	
	
	public java.lang.String getStatTime() {
		return this.statTime;
	}
	
	public void setStatTime(java.lang.String value) {
		this.statTime = value;
	}
	
	
	public java.lang.String getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(java.lang.String value) {
		this.endTime = value;
	}
	
	
	public java.math.BigDecimal getRouteLmtbal() {
		return this.routeLmtbal;
	}
	
	public void setRouteLmtbal(java.math.BigDecimal value) {
		this.routeLmtbal = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
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
	

	

}

