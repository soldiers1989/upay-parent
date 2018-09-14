
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerAcctInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerAcctInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_CARD_TYPE = "持卡类型   参考附录卡类型";
	public static final String ALIAS_PARENT_MER_NO = "上级商户号";
	public static final String ALIAS_IS_PARENT_MER = "是否一级商户，0-是 1-否";
	public static final String ALIAS_MER_PLAT_NO = "商户平台代码";
	public static final String ALIAS_OPEN_USER_ID = "开通用户号";
	public static final String ALIAS_MER_MODIFY_DATE = "商户修改日期";
	public static final String ALIAS_MODIFY_USER_NO = "修改操作员";
	public static final String ALIAS_MER_CLOSE_DATE = "商户销户日期";
	public static final String ALIAS_PAY_OPEN_FLAG = "支付功能开通标志  0:未开通  1：开通";
	public static final String ALIAS_CLOSE_USER_NO = "销户操作员";
	public static final String ALIAS_MER_NAME = "商户名称";
	public static final String ALIAS_MER_PEND_STL_AMT = "商户待结算金额";
	public static final String ALIAS_STL_ACCT_NO = "结算账户";
	public static final String ALIAS_STL_ACCT_TYPE = "结算账户类型";
	public static final String ALIAS_STL_ACCT_KIND = "结算账户性质";
	public static final String ALIAS_STL_ACCT_NAME = "结算账户户名";
	public static final String ALIAS_FEE_STL_PERIOD = "手续费结算周期";
	public static final String ALIAS_FEE_MODE = "手续费收取方式";
	public static final String ALIAS_FEE_ACCT_NO = "手续费结算账户";
	public static final String ALIAS_FEE_ACCT_NAME = "手续费结算账户户名";
	public static final String ALIAS_FEE_ACCT_KIND = "手续费账户性质";
	public static final String ALIAS_FEE_ACCT_TYPE = "手续费账户类型";
	public static final String ALIAS_BAIL_ACCT_NO = "保证金账户";
	public static final String ALIAS_BAIL_ACCT_NAME = "保证金账户户名";
	public static final String ALIAS_BAIL_ACCT_KIND = "保证金账户性质";
	public static final String ALIAS_BAIL_AMT = "保证金额度";
	public static final String ALIAS_BAIL_ACCT_TYPE = "保证金类型";
	public static final String ALIAS_CLEAR_MODE = "清算方式";
	public static final String ALIAS_STL_MODE = "结算方式 0-平台结算 1-委托清算";
	public static final String ALIAS_STL_PERIOD = "结算周期";
	public static final String ALIAS_AUTH_CHECK_FLAG = "退货是否授权";
	public static final String ALIAS_FEE_RETURN_FLAG = "退货是否退手续费";
	public static final String ALIAS_STL_CYCLE = "清算周期:1：日结,2：周结,3：月结,4：季结,5：半年结,6：年结";
	public static final String ALIAS_STL_DATE = "最后清算日期";
	public static final String ALIAS_STL_ACCT_DATE = "最后结算日期";
	public static final String ALIAS_STL_CYCLE_DAY = "清算日:STL_CYCCL=2（周结）n表示周几7表示周日STL_CYCCL=3（月结）n表示每月多少号0：月末1：月初STL_CYCCL=4（季结）1：季初 0：季末STL_CYCCL=5（半年结）1：半年初0：半年末STL_CYCCL=5（年结）1：年初0：年末";
	public static final String ALIAS_BANK_ID = "银行机构编号";
	public static final String ALIAS_BANK_NAME = "银行名称";
	public static final String ALIAS_IS_ENTRUST = "isEntrust";
	public static final String ALIAS_IS_ENTRUST_DATE = "isEntrustDate";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 持卡类型   参考附录卡类型       db_column: CARD_TYPE 
     */ 	
	private java.lang.String cardType;
    /**
     * 上级商户号       db_column: PARENT_MER_NO 
     */ 	
	private java.lang.String parentMerNo;
    /**
     * 是否一级商户，0-是 1-否       db_column: IS_PARENT_MER 
     */ 	
	private java.lang.String isParentMer;
    /**
     * 商户平台代码       db_column: MER_PLAT_NO 
     */ 	
	private java.lang.String merPlatNo;
    /**
     * 开通用户号       db_column: OPEN_USER_ID 
     */ 	
	private java.lang.String openUserId;
    /**
     * 商户修改日期       db_column: MER_MODIFY_DATE 
     */ 	
	private java.util.Date merModifyDate;
    /**
     * 修改操作员       db_column: MODIFY_USER_NO 
     */ 	
	private java.lang.String modifyUserNo;
    /**
     * 商户销户日期       db_column: MER_CLOSE_DATE 
     */ 	
	private java.util.Date merCloseDate;
    /**
     * 支付功能开通标志  0:未开通  1：开通       db_column: PAY_OPEN_FLAG 
     */ 	
	private java.lang.String payOpenFlag;
    /**
     * 销户操作员       db_column: CLOSE_USER_NO 
     */ 	
	private java.lang.String closeUserNo;
    /**
     * 商户名称       db_column: MER_NAME 
     */ 	
	private java.lang.String merName;
    /**
     * 商户待结算金额       db_column: MER_PEND_STL_AMT 
     */ 	
	private java.math.BigDecimal merPendStlAmt;
    /**
     * 结算账户       db_column: STL_ACCT_NO 
     */ 	
	private java.lang.String stlAcctNo;
    /**
     * 结算账户类型       db_column: STL_ACCT_TYPE 
     */ 	
	private java.lang.String stlAcctType;
    /**
     * 结算账户性质       db_column: STL_ACCT_KIND 
     */ 	
	private java.lang.String stlAcctKind;
    /**
     * 结算账户户名       db_column: STL_ACCT_NAME 
     */ 	
	private java.lang.String stlAcctName;
    /**
     * 手续费结算周期       db_column: FEE_STL_PERIOD 
     */ 	
	private java.math.BigDecimal feeStlPeriod;
    /**
     * 手续费收取方式       db_column: FEE_MODE 
     */ 	
	private java.lang.String feeMode;
    /**
     * 手续费结算账户       db_column: FEE_ACCT_NO 
     */ 	
	private java.lang.String feeAcctNo;
    /**
     * 手续费结算账户户名       db_column: FEE_ACCT_NAME 
     */ 	
	private java.lang.String feeAcctName;
    /**
     * 手续费账户性质       db_column: FEE_ACCT_KIND 
     */ 	
	private java.lang.String feeAcctKind;
    /**
     * 手续费账户类型       db_column: FEE_ACCT_TYPE 
     */ 	
	private java.lang.String feeAcctType;
    /**
     * 保证金账户       db_column: BAIL_ACCT_NO 
     */ 	
	private java.lang.String bailAcctNo;
    /**
     * 保证金账户户名       db_column: BAIL_ACCT_NAME 
     */ 	
	private java.lang.String bailAcctName;
    /**
     * 保证金账户性质       db_column: BAIL_ACCT_KIND 
     */ 	
	private java.lang.String bailAcctKind;
    /**
     * 保证金额度       db_column: BAIL_AMT 
     */ 	
	private java.math.BigDecimal bailAmt;
    /**
     * 保证金类型       db_column: BAIL_ACCT_TYPE 
     */ 	
	private java.lang.String bailAcctType;
    /**
     * 清算方式       db_column: CLEAR_MODE 
     */ 	
	private java.lang.String clearMode;
    /**
     * 结算方式 0-平台结算 1-委托清算       db_column: STL_MODE 
     */ 	
	private java.lang.String stlMode;
    /**
     * 结算周期       db_column: STL_PERIOD 
     */ 	
	private java.math.BigDecimal stlPeriod;
    /**
     * 退货是否授权       db_column: AUTH_CHECK_FLAG 
     */ 	
	private java.lang.String authCheckFlag;
    /**
     * 退货是否退手续费       db_column: FEE_RETURN_FLAG 
     */ 	
	private java.lang.String feeReturnFlag;
    /**
     * 清算周期:1：日结,2：周结,3：月结,4：季结,5：半年结,6：年结       db_column: STL_CYCLE 
     */ 	
	private java.lang.String stlCycle;
    /**
     * 最后清算日期       db_column: STL_DATE 
     */ 	
	private java.util.Date stlDate;
    /**
     * 最后结算日期       db_column: STL_ACCT_DATE 
     */ 	
	private java.util.Date stlAcctDate;
    /**
     * 清算日:STL_CYCCL=2（周结）n表示周几7表示周日STL_CYCCL=3（月结）n表示每月多少号0：月末1：月初STL_CYCCL=4（季结）1：季初 0：季末STL_CYCCL=5（半年结）1：半年初0：半年末STL_CYCCL=5（年结）1：年初0：年末       db_column: STL_CYCLE_DAY 
     */ 	
	private java.math.BigDecimal stlCycleDay;
    /**
     * 银行机构编号       db_column: BANK_ID 
     */ 	
	private java.lang.String bankId;
    /**
     * 银行名称       db_column: BANK_NAME 
     */ 	
	private java.lang.String bankName;
    /**
     * isEntrust       db_column: IS_ENTRUST 
     */ 	
	private java.lang.String isEntrust;
    /**
     * isEntrustDate       db_column: IS_ENTRUST_DATE 
     */ 	
	private java.util.Date isEntrustDate;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getCardType() {
		return this.cardType;
	}
	
	public void setCardType(java.lang.String value) {
		this.cardType = value;
	}
	
	
	public java.lang.String getParentMerNo() {
		return this.parentMerNo;
	}
	
	public void setParentMerNo(java.lang.String value) {
		this.parentMerNo = value;
	}
	
	
	public java.lang.String getIsParentMer() {
		return this.isParentMer;
	}
	
	public void setIsParentMer(java.lang.String value) {
		this.isParentMer = value;
	}
	
	
	public java.lang.String getMerPlatNo() {
		return this.merPlatNo;
	}
	
	public void setMerPlatNo(java.lang.String value) {
		this.merPlatNo = value;
	}
	
	
	public java.lang.String getOpenUserId() {
		return this.openUserId;
	}
	
	public void setOpenUserId(java.lang.String value) {
		this.openUserId = value;
	}
	
	
	public java.util.Date getMerModifyDate() {
		return this.merModifyDate;
	}
	
	public void setMerModifyDate(java.util.Date value) {
		this.merModifyDate = value;
	}
	
	
	public java.lang.String getModifyUserNo() {
		return this.modifyUserNo;
	}
	
	public void setModifyUserNo(java.lang.String value) {
		this.modifyUserNo = value;
	}
	
	
	public java.util.Date getMerCloseDate() {
		return this.merCloseDate;
	}
	
	public void setMerCloseDate(java.util.Date value) {
		this.merCloseDate = value;
	}
	
	
	public java.lang.String getPayOpenFlag() {
		return this.payOpenFlag;
	}
	
	public void setPayOpenFlag(java.lang.String value) {
		this.payOpenFlag = value;
	}
	
	
	public java.lang.String getCloseUserNo() {
		return this.closeUserNo;
	}
	
	public void setCloseUserNo(java.lang.String value) {
		this.closeUserNo = value;
	}
	
	
	public java.lang.String getMerName() {
		return this.merName;
	}
	
	public void setMerName(java.lang.String value) {
		this.merName = value;
	}
	
	
	public java.math.BigDecimal getMerPendStlAmt() {
		return this.merPendStlAmt;
	}
	
	public void setMerPendStlAmt(java.math.BigDecimal value) {
		this.merPendStlAmt = value;
	}
	
	
	public java.lang.String getStlAcctNo() {
		return this.stlAcctNo;
	}
	
	public void setStlAcctNo(java.lang.String value) {
		this.stlAcctNo = value;
	}
	
	
	public java.lang.String getStlAcctType() {
		return this.stlAcctType;
	}
	
	public void setStlAcctType(java.lang.String value) {
		this.stlAcctType = value;
	}
	
	
	public java.lang.String getStlAcctKind() {
		return this.stlAcctKind;
	}
	
	public void setStlAcctKind(java.lang.String value) {
		this.stlAcctKind = value;
	}
	
	
	public java.lang.String getStlAcctName() {
		return this.stlAcctName;
	}
	
	public void setStlAcctName(java.lang.String value) {
		this.stlAcctName = value;
	}
	
	
	public java.math.BigDecimal getFeeStlPeriod() {
		return this.feeStlPeriod;
	}
	
	public void setFeeStlPeriod(java.math.BigDecimal value) {
		this.feeStlPeriod = value;
	}
	
	
	public java.lang.String getFeeMode() {
		return this.feeMode;
	}
	
	public void setFeeMode(java.lang.String value) {
		this.feeMode = value;
	}
	
	
	public java.lang.String getFeeAcctNo() {
		return this.feeAcctNo;
	}
	
	public void setFeeAcctNo(java.lang.String value) {
		this.feeAcctNo = value;
	}
	
	
	public java.lang.String getFeeAcctName() {
		return this.feeAcctName;
	}
	
	public void setFeeAcctName(java.lang.String value) {
		this.feeAcctName = value;
	}
	
	
	public java.lang.String getFeeAcctKind() {
		return this.feeAcctKind;
	}
	
	public void setFeeAcctKind(java.lang.String value) {
		this.feeAcctKind = value;
	}
	
	
	public java.lang.String getFeeAcctType() {
		return this.feeAcctType;
	}
	
	public void setFeeAcctType(java.lang.String value) {
		this.feeAcctType = value;
	}
	
	
	public java.lang.String getBailAcctNo() {
		return this.bailAcctNo;
	}
	
	public void setBailAcctNo(java.lang.String value) {
		this.bailAcctNo = value;
	}
	
	
	public java.lang.String getBailAcctName() {
		return this.bailAcctName;
	}
	
	public void setBailAcctName(java.lang.String value) {
		this.bailAcctName = value;
	}
	
	
	public java.lang.String getBailAcctKind() {
		return this.bailAcctKind;
	}
	
	public void setBailAcctKind(java.lang.String value) {
		this.bailAcctKind = value;
	}
	
	
	public java.math.BigDecimal getBailAmt() {
		return this.bailAmt;
	}
	
	public void setBailAmt(java.math.BigDecimal value) {
		this.bailAmt = value;
	}
	
	
	public java.lang.String getBailAcctType() {
		return this.bailAcctType;
	}
	
	public void setBailAcctType(java.lang.String value) {
		this.bailAcctType = value;
	}
	
	
	public java.lang.String getClearMode() {
		return this.clearMode;
	}
	
	public void setClearMode(java.lang.String value) {
		this.clearMode = value;
	}
	
	
	public java.lang.String getStlMode() {
		return this.stlMode;
	}
	
	public void setStlMode(java.lang.String value) {
		this.stlMode = value;
	}
	
	
	public java.math.BigDecimal getStlPeriod() {
		return this.stlPeriod;
	}
	
	public void setStlPeriod(java.math.BigDecimal value) {
		this.stlPeriod = value;
	}
	
	
	public java.lang.String getAuthCheckFlag() {
		return this.authCheckFlag;
	}
	
	public void setAuthCheckFlag(java.lang.String value) {
		this.authCheckFlag = value;
	}
	
	
	public java.lang.String getFeeReturnFlag() {
		return this.feeReturnFlag;
	}
	
	public void setFeeReturnFlag(java.lang.String value) {
		this.feeReturnFlag = value;
	}
	
	
	public java.lang.String getStlCycle() {
		return this.stlCycle;
	}
	
	public void setStlCycle(java.lang.String value) {
		this.stlCycle = value;
	}
	
	
	public java.util.Date getStlDate() {
		return this.stlDate;
	}
	
	public void setStlDate(java.util.Date value) {
		this.stlDate = value;
	}
	
	
	public java.util.Date getStlAcctDate() {
		return this.stlAcctDate;
	}
	
	public void setStlAcctDate(java.util.Date value) {
		this.stlAcctDate = value;
	}
	
	
	public java.math.BigDecimal getStlCycleDay() {
		return this.stlCycleDay;
	}
	
	public void setStlCycleDay(java.math.BigDecimal value) {
		this.stlCycleDay = value;
	}
	
	
	public java.lang.String getBankId() {
		return this.bankId;
	}
	
	public void setBankId(java.lang.String value) {
		this.bankId = value;
	}
	
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	
	public java.lang.String getIsEntrust() {
		return this.isEntrust;
	}
	
	public void setIsEntrust(java.lang.String value) {
		this.isEntrust = value;
	}
	
	
	public java.util.Date getIsEntrustDate() {
		return this.isEntrustDate;
	}
	
	public void setIsEntrustDate(java.util.Date value) {
		this.isEntrustDate = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

