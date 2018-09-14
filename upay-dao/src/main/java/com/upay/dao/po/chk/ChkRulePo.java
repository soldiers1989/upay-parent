
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class ChkRulePo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChkRule";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BENCHMARK_FLAG = "对账数据基准标志 01：第三方为主 02：核心为主";
	public static final String ALIAS_ROUTE_CODE = "资金通道代码   对账的单位代码";
	public static final String ALIAS_CHK_FLAG = "对账方式  1：第三方生成对账文件，平台对账 2：平台生成对账文件，第三方对账";
	public static final String ALIAS_CHK_CYCLE = "对账周期 00：每日对账 99：月末对账 1-31：每月对账日";
	public static final String ALIAS_CHK_DATE_FLAG = "对账日期类型   对账的时候按照那个日期来取流水 1:平台日期 2:资金通道日期";
	public static final String ALIAS_CHK_TYPE = "对账模式 1：只对明细账 2：先对总账，总账平，不对明细；总账不平，则对明细； 3：总账、明细账都需要对账； 4: 只对明细账，手续费入内部帐 ";
	public static final String ALIAS_STAT = "状态 0:失效 1：正常";
	public static final String ALIAS_RESERVE1 = "备用字段1";
	public static final String ALIAS_RESERVE2 = "备用字段2";
	

	//columns START
    /**
     * 对账数据基准标志 01：第三方为主 02：核心为主       db_column: BENCHMARK_FLAG 
     */ 	
	private java.lang.String benchmarkFlag;
    /**
     * 资金通道代码   对账的单位代码       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 对账方式  1：第三方生成对账文件，平台对账 2：平台生成对账文件，第三方对账       db_column: CHK_FLAG 
     */ 	
	private java.lang.String chkFlag;
    /**
     * 对账周期 00：每日对账 99：月末对账 1-31：每月对账日       db_column: CHK_CYCLE 
     */ 	
	private java.lang.String chkCycle;
    /**
     * 对账日期类型   对账的时候按照那个日期来取流水 1:平台日期 2:资金通道日期       db_column: CHK_DATE_FLAG 
     */ 	
	private java.lang.String chkDateFlag;
    /**
     * 对账模式 1：只对明细账 2：先对总账，总账平，不对明细；总账不平，则对明细； 3：总账、明细账都需要对账； 4: 只对明细账，手续费入内部帐        db_column: CHK_TYPE 
     */ 	
	private java.lang.String chkType;
    /**
     * 状态 0:失效 1：正常       db_column: STAT 
     */ 	
	private java.lang.String stat;
    /**
     * 备用字段1       db_column: RESERVE1 
     */ 	
	private java.lang.String reserve1;
    /**
     * 备用字段2       db_column: RESERVE2 
     */ 	
	private java.lang.String reserve2;
	//columns END


	
	
	public java.lang.String getBenchmarkFlag() {
		return this.benchmarkFlag;
	}
	
	public void setBenchmarkFlag(java.lang.String value) {
		this.benchmarkFlag = value;
	}
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getChkFlag() {
		return this.chkFlag;
	}
	
	public void setChkFlag(java.lang.String value) {
		this.chkFlag = value;
	}
	
	
	public java.lang.String getChkCycle() {
		return this.chkCycle;
	}
	
	public void setChkCycle(java.lang.String value) {
		this.chkCycle = value;
	}
	
	
	public java.lang.String getChkDateFlag() {
		return this.chkDateFlag;
	}
	
	public void setChkDateFlag(java.lang.String value) {
		this.chkDateFlag = value;
	}
	
	
	public java.lang.String getChkType() {
		return this.chkType;
	}
	
	public void setChkType(java.lang.String value) {
		this.chkType = value;
	}
	
	
	public java.lang.String getStat() {
		return this.stat;
	}
	
	public void setStat(java.lang.String value) {
		this.stat = value;
	}
	
	
	public java.lang.String getReserve1() {
		return this.reserve1;
	}
	
	public void setReserve1(java.lang.String value) {
		this.reserve1 = value;
	}
	
	
	public java.lang.String getReserve2() {
		return this.reserve2;
	}
	
	public void setReserve2(java.lang.String value) {
		this.reserve2 = value;
	}
	

	

}

