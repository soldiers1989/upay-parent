
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeAssPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeAss";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ASS_CODE = "分润代码";
	public static final String ALIAS_ASS_NAME = "分润名称";
	public static final String ALIAS_ASS_ID = "分润方  001：支付平台    002：资金通道    003：合作商户";
	public static final String ALIAS_ASS_KIND = "分润类型,0：应收    1：应付";
	public static final String ALIAS_ASS_TYPE = "分润方式 ,1:按比率    2：固定金额（优先）";
	public static final String ALIAS_ASS_RATE = "分润比率 ,该值要除以100展示，如15，表示15%";
	public static final String ALIAS_FIX_AMT = "固定金额";
	public static final String ALIAS_MEMO_AMT = "备用金额";
	public static final String ALIAS_MEMO_COUNT = "备用数字";
	public static final String ALIAS_LAST_UPD_USER_ID = "最后修改操作员";
	public static final String ALIAS_LAST_UPD_TXN_ID = "最后修改交易码";
	public static final String ALIAS_LAST_UPD_TS = "最后修改时间";
	public static final String ALIAS_MEMO = "备用";
	

	//columns START
    /**
     * 分润代码       db_column: ASS_CODE 
     */ 	
	private java.lang.String assCode;
    /**
     * 分润名称       db_column: ASS_NAME 
     */ 	
	private java.lang.String assName;
    /**
     * 分润方  001：支付平台    002：资金通道    003：合作商户       db_column: ASS_ID 
     */ 	
	private java.lang.String assId;
    /**
     * 分润类型,0：应收    1：应付       db_column: ASS_KIND 
     */ 	
	private java.lang.String assKind;
    /**
     * 分润方式 ,1:按比率    2：固定金额（优先）       db_column: ASS_TYPE 
     */ 	
	private java.lang.String assType;
    /**
     * 分润比率 ,该值要除以100展示，如15，表示15%       db_column: ASS_RATE 
     */ 	
	private java.math.BigDecimal assRate;
    /**
     * 固定金额       db_column: FIX_AMT 
     */ 	
	private java.math.BigDecimal fixAmt;
    /**
     * 备用金额       db_column: MEMO_AMT 
     */ 	
	private java.math.BigDecimal memoAmt;
    /**
     * 备用数字       db_column: MEMO_COUNT 
     */ 	
	private java.lang.Integer memoCount;
    /**
     * 最后修改操作员       db_column: LAST_UPD_USER_ID 
     */ 	
	private java.lang.String lastUpdUserId;
    /**
     * 最后修改交易码       db_column: LAST_UPD_TXN_ID 
     */ 	
	private java.lang.String lastUpdTxnId;
    /**
     * 最后修改时间       db_column: LAST_UPD_TS 
     */ 	
	private java.util.Date lastUpdTs;
    /**
     * 备用       db_column: MEMO 
     */ 	
	private java.lang.String memo;
	//columns END


	
	
	public java.lang.String getAssCode() {
		return this.assCode;
	}
	
	public void setAssCode(java.lang.String value) {
		this.assCode = value;
	}
	
	
	public java.lang.String getAssName() {
		return this.assName;
	}
	
	public void setAssName(java.lang.String value) {
		this.assName = value;
	}
	
	
	public java.lang.String getAssId() {
		return this.assId;
	}
	
	public void setAssId(java.lang.String value) {
		this.assId = value;
	}
	
	
	public java.lang.String getAssKind() {
		return this.assKind;
	}
	
	public void setAssKind(java.lang.String value) {
		this.assKind = value;
	}
	
	
	public java.lang.String getAssType() {
		return this.assType;
	}
	
	public void setAssType(java.lang.String value) {
		this.assType = value;
	}
	
	
	public java.math.BigDecimal getAssRate() {
		return this.assRate;
	}
	
	public void setAssRate(java.math.BigDecimal value) {
		this.assRate = value;
	}
	
	
	public java.math.BigDecimal getFixAmt() {
		return this.fixAmt;
	}
	
	public void setFixAmt(java.math.BigDecimal value) {
		this.fixAmt = value;
	}
	
	
	public java.math.BigDecimal getMemoAmt() {
		return this.memoAmt;
	}
	
	public void setMemoAmt(java.math.BigDecimal value) {
		this.memoAmt = value;
	}
	
	
	public java.lang.Integer getMemoCount() {
		return this.memoCount;
	}
	
	public void setMemoCount(java.lang.Integer value) {
		this.memoCount = value;
	}
	
	
	public java.lang.String getLastUpdUserId() {
		return this.lastUpdUserId;
	}
	
	public void setLastUpdUserId(java.lang.String value) {
		this.lastUpdUserId = value;
	}
	
	
	public java.lang.String getLastUpdTxnId() {
		return this.lastUpdTxnId;
	}
	
	public void setLastUpdTxnId(java.lang.String value) {
		this.lastUpdTxnId = value;
	}
	
	
	public java.util.Date getLastUpdTs() {
		return this.lastUpdTs;
	}
	
	public void setLastUpdTs(java.util.Date value) {
		this.lastUpdTs = value;
	}
	
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	

	

}

