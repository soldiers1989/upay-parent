
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeKindPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeKind";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_FEE_CODE = "收费代码";
	public static final String ALIAS_FEE_NAME = "收费名称";
	public static final String ALIAS_FEE_MODE = "手续费收取方式,1：单笔固定； 2：单笔固定+按交易金额 3：按交易金额比例收取；";
	public static final String ALIAS_FIX_FEE = "单笔固定金额";
	public static final String ALIAS_RATION_FEE = "按交易金额比例,该值要除以100展示，如15，表示15%";
	public static final String ALIAS_HIGH_FEE = "手续费上限";
	public static final String ALIAS_LOW_FEE = "手续费下限";
	public static final String ALIAS_MEMO = "备用";
	

	//columns START
    /**
     * 收费代码       db_column: FEE_CODE 
     */ 	
	private java.lang.String feeCode;
    /**
     * 收费名称       db_column: FEE_NAME 
     */ 	
	private java.lang.String feeName;
    /**
     * 手续费收取方式,1：单笔固定； 2：单笔固定+按交易金额 3：按交易金额比例收取；       db_column: FEE_MODE 
     */ 	
	private java.lang.String feeMode;
    /**
     * 单笔固定金额       db_column: FIX_FEE 
     */ 	
	private java.math.BigDecimal fixFee;
    /**
     * 按交易金额比例,该值要除以100展示，如15，表示15%       db_column: RATION_FEE 
     */ 	
	private java.math.BigDecimal rationFee;
    /**
     * 手续费上限       db_column: HIGH_FEE 
     */ 	
	private java.math.BigDecimal highFee;
    /**
     * 手续费下限       db_column: LOW_FEE 
     */ 	
	private java.math.BigDecimal lowFee;
    /**
     * 备用       db_column: MEMO 
     */ 	
	private java.lang.String memo;
	//columns END


	
	
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	
	public void setFeeCode(java.lang.String value) {
		this.feeCode = value;
	}
	
	
	public java.lang.String getFeeName() {
		return this.feeName;
	}
	
	public void setFeeName(java.lang.String value) {
		this.feeName = value;
	}
	
	
	public java.lang.String getFeeMode() {
		return this.feeMode;
	}
	
	public void setFeeMode(java.lang.String value) {
		this.feeMode = value;
	}
	
	
	public java.math.BigDecimal getFixFee() {
		return this.fixFee;
	}
	
	public void setFixFee(java.math.BigDecimal value) {
		this.fixFee = value;
	}
	
	
	public java.math.BigDecimal getRationFee() {
		return this.rationFee;
	}
	
	public void setRationFee(java.math.BigDecimal value) {
		this.rationFee = value;
	}
	
	
	public java.math.BigDecimal getHighFee() {
		return this.highFee;
	}
	
	public void setHighFee(java.math.BigDecimal value) {
		this.highFee = value;
	}
	
	
	public java.math.BigDecimal getLowFee() {
		return this.lowFee;
	}
	
	public void setLowFee(java.math.BigDecimal value) {
		this.lowFee = value;
	}
	
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	

	

}

