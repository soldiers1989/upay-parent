
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayBankMatchPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayBankMatch";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BANK_NO = "银行机构行号 银行在支付平台机构号";
	public static final String ALIAS_ROUTE_CODE = "通道代码";
	public static final String ALIAS_ROUTE_BANK_NO = "通道银行机构行号";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 银行机构行号 银行在支付平台机构号       db_column: BANK_NO 
     */ 	
	private java.lang.String bankNo;
    /**
     * 通道代码       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 通道银行机构行号       db_column: ROUTE_BANK_NO 
     */ 	
	private java.lang.String routeBankNo;
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


	
	
	public java.lang.String getBankNo() {
		return this.bankNo;
	}
	
	public void setBankNo(java.lang.String value) {
		this.bankNo = value;
	}
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getRouteBankNo() {
		return this.routeBankNo;
	}
	
	public void setRouteBankNo(java.lang.String value) {
		this.routeBankNo = value;
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

