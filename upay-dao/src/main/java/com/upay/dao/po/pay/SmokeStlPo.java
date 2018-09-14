package com.upay.dao.po.pay;

import com.pactera.dipper.po.BasePo;

public class SmokeStlPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "烟草转账商户提现表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MER_NO = "提现商户";
	public static final String ALIAS_TRANS_DATE = "提现日期";
	public static final String ALIAS_TRANS_AMT = "提现金额";
	public static final String ALIAS_RESULT = "0 成功 1 失败";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_SUCC_AMT = "转账成功金额";
	

	//columns START
    /**
     * 提现商户       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 提现日期       db_column: TRANS_DATE 
     */ 	
	private java.util.Date transDate;
    /**
     * 提现金额       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 0 成功 1 失败       db_column: RESULT 
     */ 	
	private java.lang.String result;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 转账成功金额       db_column: SUCC_AMT 
     */ 	
	private java.math.BigDecimal succAmt;
	//columns END


	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.util.Date getTransDate() {
		return this.transDate;
	}
	
	public void setTransDate(java.util.Date value) {
		this.transDate = value;
	}
	
	
	public java.math.BigDecimal getTransAmt() {
		return this.transAmt;
	}
	
	public void setTransAmt(java.math.BigDecimal value) {
		this.transAmt = value;
	}
	
	
	public java.lang.String getResult() {
		return this.result;
	}
	
	public void setResult(java.lang.String value) {
		this.result = value;
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
	
	
	public java.math.BigDecimal getSuccAmt() {
		return this.succAmt;
	}
	
	public void setSuccAmt(java.math.BigDecimal value) {
		this.succAmt = value;
	}
	

	

}

