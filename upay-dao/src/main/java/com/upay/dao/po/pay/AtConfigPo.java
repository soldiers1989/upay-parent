
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class AtConfigPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AtConfig";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TRANS_CODE = "交易代码SI_XXXXXXXX";
	public static final String ALIAS_USE_AT = "是否使用:Y:使用N:不使用";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 交易代码SI_XXXXXXXX       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 是否使用:Y:使用N:不使用       db_column: USE_AT 
     */ 	
	private java.lang.String useAt;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
	//columns END


	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getUseAt() {
		return this.useAt;
	}
	
	public void setUseAt(java.lang.String value) {
		this.useAt = value;
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

