
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayRouteInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayRouteInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ROUTE_CODE = "通道代码 见附录4.3,ROUTE_CODE";
	public static final String ALIAS_ROUTE_NAME = "通道名称";
	public static final String ALIAS_ORG_NO = "商户或机构代码 登记银联的商户号或关联系统行号";
	public static final String ALIAS_CLR_TYPE = "清算方式 0、无清算 1、实时清算 2、实时代理清算 3、日终清算 4、日终代理清算";
	public static final String ALIAS_TRANS_ACCT_NO = "往来账户";
	public static final String ALIAS_CLR_ACCT_NO = "清算账户";
	public static final String ALIAS_DEF_USER_ID = "默认用户代码";
	public static final String ALIAS_CHK_STAT = "对账状态 1-收到对账文件 2-核对中 3-核对处理成功 4-核对处理失败 5-自动调账中 6-自动调账失败 7-对账完成";
	public static final String ALIAS_CHK_DATE = "上次对账日期";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_TERMINAL_ID = "终端号";
	public static final String ALIAS_MERCH_LOGIN_KEY = "商户登录密码";
	public static final String ALIAS_PUBLIC_KEY_PATH = "公钥文件路径";
	public static final String ALIAS_CERT_FILE_PATH = "私钥证书路径";
	public static final String ALIAS_CERT_PWD = "私钥密码";
	public static final String ALIAS_CALLBACK_RUL = "异步回调接收服务地址";
	public static final String ALIAS_SERVICE_VERSION = "版本号";
	public static final String ALIAS_APP_ID = "APP_ID";
	public static final String ALIAS_APP_SECRET = "应用密钥，微信使用";
	public static final String ALIAS_FEE_INCOME_ACCT_NO = "feeIncomeAcctNo";
	public static final String ALIAS_FEE_EXPENSE_ACCT_NO = "feeExpenseAcctNo";
	public static final String ALIAS_UNION_ACCT_NO = "unionAcctNo";
	public static final String ALIAS_UNION_AT_CALLBACK_RUL = "银联AT通道回调的URL";
	

	//columns START
    /**
     * 通道代码 见附录4.3,ROUTE_CODE       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 通道名称       db_column: ROUTE_NAME 
     */ 	
	private java.lang.String routeName;
    /**
     * 商户或机构代码 登记银联的商户号或关联系统行号       db_column: ORG_NO 
     */ 	
	private java.lang.String orgNo;
    /**
     * 清算方式 0、无清算 1、实时清算 2、实时代理清算 3、日终清算 4、日终代理清算       db_column: CLR_TYPE 
     */ 	
	private java.lang.String clrType;
    /**
     * 往来账户       db_column: TRANS_ACCT_NO 
     */ 	
	private java.lang.String transAcctNo;
    /**
     * 清算账户       db_column: CLR_ACCT_NO 
     */ 	
	private java.lang.String clrAcctNo;
    /**
     * 默认用户代码       db_column: DEF_USER_ID 
     */ 	
	private java.lang.String defUserId;
    /**
     * 对账状态 1-收到对账文件 2-核对中 3-核对处理成功 4-核对处理失败 5-自动调账中 6-自动调账失败 7-对账完成       db_column: CHK_STAT 
     */ 	
	private java.lang.String chkStat;
    /**
     * 上次对账日期       db_column: CHK_DATE 
     */ 	
	private java.util.Date chkDate;
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
    /**
     * 终端号       db_column: TERMINAL_ID 
     */ 	
	private java.lang.String terminalId;
    /**
     * 商户登录密码       db_column: MERCH_LOGIN_KEY 
     */ 	
	private java.lang.String merchLoginKey;
    /**
     * 公钥文件路径       db_column: PUBLIC_KEY_PATH 
     */ 	
	private java.lang.String publicKeyPath;
    /**
     * 私钥证书路径       db_column: CERT_FILE_PATH 
     */ 	
	private java.lang.String certFilePath;
    /**
     * 私钥密码       db_column: CERT_PWD 
     */ 	
	private java.lang.String certPwd;
    /**
     * 异步回调接收服务地址       db_column: CALLBACK_RUL 
     */ 	
	private java.lang.String callbackRul;
    /**
     * 版本号       db_column: SERVICE_VERSION 
     */ 	
	private java.lang.String serviceVersion;
    /**
     * APP_ID       db_column: APP_ID 
     */ 	
	private java.lang.String appId;
    /**
     * 应用密钥，微信使用       db_column: APP_SECRET 
     */ 	
	private java.lang.String appSecret;
    /**
     * feeIncomeAcctNo       db_column: FEE_INCOME_ACCT_NO 
     */ 	
	private java.lang.String feeIncomeAcctNo;
    /**
     * feeExpenseAcctNo       db_column: FEE_EXPENSE_ACCT_NO 
     */ 	
	private java.lang.String feeExpenseAcctNo;
    /**
     * unionAcctNo       db_column: UNION_ACCT_NO 
     */ 	
	private java.lang.String unionAcctNo;
    /**
     * 银联AT通道回调的URL       db_column: UNION_AT_CALLBACK_RUL 
     */ 	
	private java.lang.String unionAtCallbackRul;
	//columns END


	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getRouteName() {
		return this.routeName;
	}
	
	public void setRouteName(java.lang.String value) {
		this.routeName = value;
	}
	
	
	public java.lang.String getOrgNo() {
		return this.orgNo;
	}
	
	public void setOrgNo(java.lang.String value) {
		this.orgNo = value;
	}
	
	
	public java.lang.String getClrType() {
		return this.clrType;
	}
	
	public void setClrType(java.lang.String value) {
		this.clrType = value;
	}
	
	
	public java.lang.String getTransAcctNo() {
		return this.transAcctNo;
	}
	
	public void setTransAcctNo(java.lang.String value) {
		this.transAcctNo = value;
	}
	
	
	public java.lang.String getClrAcctNo() {
		return this.clrAcctNo;
	}
	
	public void setClrAcctNo(java.lang.String value) {
		this.clrAcctNo = value;
	}
	
	
	public java.lang.String getDefUserId() {
		return this.defUserId;
	}
	
	public void setDefUserId(java.lang.String value) {
		this.defUserId = value;
	}
	
	
	public java.lang.String getChkStat() {
		return this.chkStat;
	}
	
	public void setChkStat(java.lang.String value) {
		this.chkStat = value;
	}
	
	
	public java.util.Date getChkDate() {
		return this.chkDate;
	}
	
	public void setChkDate(java.util.Date value) {
		this.chkDate = value;
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
	
	
	public java.lang.String getTerminalId() {
		return this.terminalId;
	}
	
	public void setTerminalId(java.lang.String value) {
		this.terminalId = value;
	}
	
	
	public java.lang.String getMerchLoginKey() {
		return this.merchLoginKey;
	}
	
	public void setMerchLoginKey(java.lang.String value) {
		this.merchLoginKey = value;
	}
	
	
	public java.lang.String getPublicKeyPath() {
		return this.publicKeyPath;
	}
	
	public void setPublicKeyPath(java.lang.String value) {
		this.publicKeyPath = value;
	}
	
	
	public java.lang.String getCertFilePath() {
		return this.certFilePath;
	}
	
	public void setCertFilePath(java.lang.String value) {
		this.certFilePath = value;
	}
	
	
	public java.lang.String getCertPwd() {
		return this.certPwd;
	}
	
	public void setCertPwd(java.lang.String value) {
		this.certPwd = value;
	}
	
	
	public java.lang.String getCallbackRul() {
		return this.callbackRul;
	}
	
	public void setCallbackRul(java.lang.String value) {
		this.callbackRul = value;
	}
	
	
	public java.lang.String getServiceVersion() {
		return this.serviceVersion;
	}
	
	public void setServiceVersion(java.lang.String value) {
		this.serviceVersion = value;
	}
	
	
	public java.lang.String getAppId() {
		return this.appId;
	}
	
	public void setAppId(java.lang.String value) {
		this.appId = value;
	}
	
	
	public java.lang.String getAppSecret() {
		return this.appSecret;
	}
	
	public void setAppSecret(java.lang.String value) {
		this.appSecret = value;
	}
	
	
	public java.lang.String getFeeIncomeAcctNo() {
		return this.feeIncomeAcctNo;
	}
	
	public void setFeeIncomeAcctNo(java.lang.String value) {
		this.feeIncomeAcctNo = value;
	}
	
	
	public java.lang.String getFeeExpenseAcctNo() {
		return this.feeExpenseAcctNo;
	}
	
	public void setFeeExpenseAcctNo(java.lang.String value) {
		this.feeExpenseAcctNo = value;
	}
	
	
	public java.lang.String getUnionAcctNo() {
		return this.unionAcctNo;
	}
	
	public void setUnionAcctNo(java.lang.String value) {
		this.unionAcctNo = value;
	}
	
	
	public java.lang.String getUnionAtCallbackRul() {
		return this.unionAtCallbackRul;
	}
	
	public void setUnionAtCallbackRul(java.lang.String value) {
		this.unionAtCallbackRul = value;
	}
	

	

}

