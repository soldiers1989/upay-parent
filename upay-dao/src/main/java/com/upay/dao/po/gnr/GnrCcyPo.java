
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrCcyPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrCcy";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CCY = "币种  与核心保持一致";
	public static final String ALIAS_CCY_NAME = "货币名称";
	public static final String ALIAS_CCY_UNIT = "基本单位  计算金额的最小单位    人民币:0.01   日元:1";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	public static final String ALIAS_REMARK1 = "备注一";
	public static final String ALIAS_REMARK2 = "备注二";
	public static final String ALIAS_REMARK3 = "备注三";
	

	//columns START
    /**
     * 币种  与核心保持一致       db_column: CCY 
     */ 	
	private java.lang.String ccy;
    /**
     * 货币名称       db_column: CCY_NAME 
     */ 	
	private java.lang.String ccyName;
    /**
     * 基本单位  计算金额的最小单位    人民币:0.01   日元:1       db_column: CCY_UNIT 
     */ 	
	private java.math.BigDecimal ccyUnit;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改操作员       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
    /**
     * 备注一       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备注二       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备注三       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getCcy() {
		return this.ccy;
	}
	
	public void setCcy(java.lang.String value) {
		this.ccy = value;
	}
	
	
	public java.lang.String getCcyName() {
		return this.ccyName;
	}
	
	public void setCcyName(java.lang.String value) {
		this.ccyName = value;
	}
	
	
	public java.math.BigDecimal getCcyUnit() {
		return this.ccyUnit;
	}
	
	public void setCcyUnit(java.math.BigDecimal value) {
		this.ccyUnit = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getLastOper() {
		return this.lastOper;
	}
	
	public void setLastOper(java.lang.String value) {
		this.lastOper = value;
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

