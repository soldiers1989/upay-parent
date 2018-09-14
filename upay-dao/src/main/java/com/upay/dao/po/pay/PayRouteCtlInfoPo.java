
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayRouteCtlInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayRouteCtlInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CARD_BIN = "卡Bin";
	public static final String ALIAS_ROUTE_CODE = "通道代码";
	public static final String ALIAS_ROUTE_FUNC_CODE = "功能代码 01-账户鉴权 02-充值 03-提现";
	public static final String ALIAS_PAY_PRITY = "通道优先级 值：1，2，3，4，5 优先级依次递减";
	public static final String ALIAS_ROUTE_STAT = "通道状态 1：正常 0：停用";
	public static final String ALIAS_ROUTE_LMTBAL = "通道头寸";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 卡Bin       db_column: CARD_BIN 
     */ 	
	private java.lang.String cardBin;
    /**
     * 通道代码       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 功能代码 01-账户鉴权 02-充值 03-提现       db_column: ROUTE_FUNC_CODE 
     */ 	
	private java.lang.String routeFuncCode;
    /**
     * 通道优先级 值：1，2，3，4，5 优先级依次递减       db_column: PAY_PRITY 
     */ 	
	private java.lang.String payPrity;
    /**
     * 通道状态 1：正常 0：停用       db_column: ROUTE_STAT 
     */ 	
	private java.lang.String routeStat;
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


	
	
	public java.lang.String getCardBin() {
		return this.cardBin;
	}
	
	public void setCardBin(java.lang.String value) {
		this.cardBin = value;
	}
	
	
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
	
	
	public java.lang.String getPayPrity() {
		return this.payPrity;
	}
	
	public void setPayPrity(java.lang.String value) {
		this.payPrity = value;
	}
	
	
	public java.lang.String getRouteStat() {
		return this.routeStat;
	}
	
	public void setRouteStat(java.lang.String value) {
		this.routeStat = value;
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

