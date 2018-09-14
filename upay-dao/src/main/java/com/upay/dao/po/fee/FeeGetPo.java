
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeGetPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeGet";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_FEE_NAME = "收费方法名称";
	public static final String ALIAS_ACCT_TYPE = "账户类型 ,00：不限制账户类型";
	public static final String ALIAS_CHNL_ID = "渠道代码  00：不限制渠道";
	public static final String ALIAS_MER_NO = "商户代码";
	public static final String ALIAS_SEC_MER_NO = "二级商户代码";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_FEE_CODE = "收费代码";
	public static final String ALIAS_ASS_CODE = "分润代码 ,默认0000:表示不分润";
	public static final String ALIAS_START_DATE = "费用收取起始日期";
	public static final String ALIAS_END_DATE = "费用收取终止日期";
	public static final String ALIAS_FREE_CYCLE = "免收周期,0:不免 1：日 2：月 3：年 9：全免";
	public static final String ALIAS_FREE_COUNT = "免收次数";
	public static final String ALIAS_PER_FEE = "优惠折扣率 1.00表示不优惠 0.8表示8折  暂不用";
	public static final String ALIAS_GET_TYPE = "手续费收起方式  0：内扣 1：外扣";
	public static final String ALIAS_LAST_UPD_USER_ID = "最后修改操作员";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_MEMO = "备用";
	public static final String ALIAS_ROUTE_CODE = "通道代码";
	public static final String ALIAS_FEE_ID = "费用方法ID";
	public static final String ALIAS_USER_TYPE = "用户类型（0：普通用户，1：商户用户）";
	public static final String ALIAS_ROUTE_FEE_FLAG = "1：资金通道手续费其他：不为资金道手续费";

	//columns START
    /**
     * 收费方法名称       db_column: FEE_NAME 
     */ 	
	private java.lang.String feeName;
    /**
     * 账户类型 ,00：不限制账户类型       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 渠道代码  00：不限制渠道       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 商户代码       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 二级商户代码       db_column: SEC_MER_NO 
     */ 	
	private java.lang.String secMerNo;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 收费代码       db_column: FEE_CODE 
     */ 	
	private java.lang.String feeCode;
    /**
     * 分润代码 ,默认0000:表示不分润       db_column: ASS_CODE 
     */ 	
	private java.lang.String assCode;
    /**
     * 费用收取起始日期       db_column: START_DATE 
     */ 	
	private java.util.Date startDate;
    /**
     * 费用收取终止日期       db_column: END_DATE 
     */ 	
	private java.util.Date endDate;
    /**
     * 免收周期,0:不免 1：日 2：月 3：年 9：全免       db_column: FREE_CYCLE 
     */ 	
	private java.lang.String freeCycle;
    /**
     * 免收次数       db_column: FREE_COUNT 
     */ 	
	private java.lang.Integer freeCount;
    /**
     * 优惠折扣率 1.00表示不优惠 0.8表示8折  暂不用       db_column: PER_FEE 
     */ 	
	private java.lang.Integer perFee;
    /**
     * 手续费收起方式  0：内扣 1：外扣       db_column: GET_TYPE 
     */ 	
	private java.lang.String getType;
    /**
     * 最后修改操作员       db_column: LAST_UPD_USER_ID 
     */ 	
	private java.lang.String lastUpdUserId;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用       db_column: MEMO 
     */ 	
	private java.lang.String memo;
    /**
     * 通道代码       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 费用方法ID       db_column: FEE_ID 
     */ 	
	private java.lang.String feeId;
    /**
     * 用户类型（0：普通用户，1：商户用户）       db_column: USER_TYPE 
     */ 	
	private java.lang.String userType;
	
	/**
     * 资金通道手续费标志 1：资金通道手续费     其他：不为资金道手续费 
     */ 	
	private java.lang.String routeFeeFlag;
	//columns END


	
	
	public java.lang.String getFeeName() {
		return this.feeName;
	}
	
	public java.lang.String getRouteFeeFlag() {
		return routeFeeFlag;
	}

	public void setRouteFeeFlag(java.lang.String routeFeeFlag) {
		this.routeFeeFlag = routeFeeFlag;
	}

	public void setFeeName(java.lang.String value) {
		this.feeName = value;
	}
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getSecMerNo() {
		return this.secMerNo;
	}
	
	public void setSecMerNo(java.lang.String value) {
		this.secMerNo = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	
	public void setFeeCode(java.lang.String value) {
		this.feeCode = value;
	}
	
	
	public java.lang.String getAssCode() {
		return this.assCode;
	}
	
	public void setAssCode(java.lang.String value) {
		this.assCode = value;
	}
	
	
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	
	public java.lang.String getFreeCycle() {
		return this.freeCycle;
	}
	
	public void setFreeCycle(java.lang.String value) {
		this.freeCycle = value;
	}
	
	
	public java.lang.Integer getFreeCount() {
		return this.freeCount;
	}
	
	public void setFreeCount(java.lang.Integer value) {
		this.freeCount = value;
	}
	
	
	public java.lang.Integer getPerFee() {
		return this.perFee;
	}
	
	public void setPerFee(java.lang.Integer value) {
		this.perFee = value;
	}
	
	
	public java.lang.String getGetType() {
		return this.getType;
	}
	
	public void setGetType(java.lang.String value) {
		this.getType = value;
	}
	
	
	public java.lang.String getLastUpdUserId() {
		return this.lastUpdUserId;
	}
	
	public void setLastUpdUserId(java.lang.String value) {
		this.lastUpdUserId = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getFeeId() {
		return this.feeId;
	}
	
	public void setFeeId(java.lang.String value) {
		this.feeId = value;
	}
	
	
	public java.lang.String getUserType() {
		return this.userType;
	}
	
	public void setUserType(java.lang.String value) {
		this.userType = value;
	}
	

	

}

