
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayOrderListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayOrderList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CHNL_ID = "渠道代码 见附录4.3,CHNL_ID";
	public static final String ALIAS_PAY_SERVIC_TYPE = "支付服务类型 0001担保支付 0002即时到账";
	public static final String ALIAS_MER_DATE = "商户日期 外部订单必输";
	public static final String ALIAS_MER_SEQ = "商户流水号 外部订单可填";
	public static final String ALIAS_MER_NO = "商户代码 外部订单为外部商户号";
	public static final String ALIAS_SEC_MER_NO = "二级商户代码";
	public static final String ALIAS_OUTER_ORDER_NO = "商户订单号 外部订单必输";
	public static final String ALIAS_OUTER_ORDER_START_DATE = "商户订单生成日期 订单生成时间，格式为yyyyMMdd  hh：mm：ss。该时间取自商户服务器";
	public static final String ALIAS_OUTER_ORDER_END_DATE = "商户订单失效日期 订单生成时间，格式为yyyyMMdd  hh：mm：ss。该时间取自商户服务器";
	public static final String ALIAS_ORDER_TYPE = "订单类型 00：非订单管理 01：平台订单 02：外部预先生成订单 03：外部联机生成订单 ";
	public static final String ALIAS_PAY_TYPE = "支付方式 支付交易时必填，参见附录支付方式";
	public static final String ALIAS_ORDER_NO = "支付订单号 平台生成唯一支付订单号";
	public static final String ALIAS_ORDER_NAME = "订单名称";
	public static final String ALIAS_TRANS_CODE = "交易代码 平台交易码";
	public static final String ALIAS_USER_ID = "用户ID 只有支付方式为11公众号支付时可为空，其他支付方式必输";
	public static final String ALIAS_ORDER_DATE = "订单创建日期 创建支付订单平台日期";
	public static final String ALIAS_ORDER_TIME = "订单创建时间";
	public static final String ALIAS_ORDER_LMT_TIME = "订单时效 订单等待支付时间 0：即时支付 >0：允许延时支付（分）";
	public static final String ALIAS_CURR = "币种 CNY：人民币";
	public static final String ALIAS_TRANS_AMT = "交易金额 总交易金额=其他费用+商品费用";
	public static final String ALIAS_OTHER_TRAN_AMT = "其他费用 其他费用说明";
	public static final String ALIAS_PRODUCT_AMT = "商品费用";
	public static final String ALIAS_MER_FEE_AMT = "商户手续费 应收商户手续费";
	public static final String ALIAS_FEE_AMT = "客户手续费 应收客户手续费";
	public static final String ALIAS_ORI_DATE = "原交易日期 对冲正和撤销交易； 原支付交易的日期";
	public static final String ALIAS_ORI_ORDER_NO = "原支付订单号 对冲正和撤销交易； 原支付交易的流水号";
	public static final String ALIAS_EJECT_AMT = "累计退货额 退货的金额";
	public static final String ALIAS_ORDER_STAT = "订单状态 见附录4.3,order_stat";
	public static final String ALIAS_SPBILL_CREATE_IP = "用户IP";
	public static final String ALIAS_NOTIFY_URL = "异步通知路径 服务器主动通知商户网站里指定的页面http 路径";
	public static final String ALIAS_RETURN_URL = "同步通知路径 处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。";
	public static final String ALIAS_TRANS_COMMENTS = "附言";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_CHK_FLAG = "对账标志 0-未对账 1-对账成功 2-对账不平 默认：0";
	public static final String ALIAS_CHK_BATCH_NO = "对账批次号";
	public static final String ALIAS_STL_FLAG = "结算标志 默认：0 0：未结算 1：结算中 2：已结算";
	public static final String ALIAS_CHK_DATE = "对账日期";
	public static final String ALIAS_STL_BATCH_NO = "结算批次号";
	public static final String ALIAS_STL_DATE = "结算日期";
	public static final String ALIAS_ORDER_DES = "订单描述";
	public static final String ALIAS_OPEN_ID = "用户ID，微信公众号支付用户在公众号中的唯一标识";
	public static final String ALIAS_PAY_TIME = "支付完成时间";
	public static final String ALIAS_PAY_ACCT_NO = "支付账号";
	public static final String ALIAS_TRANS_TYPE = "交易类型 01支付 02充值 03提现 04转账 09退款";
	public static final String ALIAS_CLEAR_FLAG = "清算标志默认：0 0：未清算  1：一级清算中  2：一级清算失败   3：一级清算完成  4：二级清算中 5：二级清算失败 6：二级清算完成 7：清算成功  8：待清算";
	public static final String ALIAS_CLEAR_BATCH_NO = "清算批次号";
	public static final String ALIAS_CLEAR_DATE = "清算日期";
	public static final String ALIAS_SEC_MER_FEE_AMT = "secMerFeeAmt";
	public static final String ALIAS_EXTENSION_PARTY = "推广方";
	public static final String ALIAS_ROUTE_CODE = "支付渠道";
	public static final String ALIAS_PAYER_ACCT_TYPE = "(付款方账户类型)付款账号类型见附录4.3,ACCT_TYPE";
	public static final String ALIAS_PAYER_ACCT_NO = "付款账号";
	public static final String ALIAS_PAYEE_ACCT_TYPE = "收款账号类型见附录4.3,acct_type";
	public static final String ALIAS_PAYEE_ACCT_NO = "收款账号";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 渠道代码 见附录4.3,CHNL_ID       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 支付服务类型 0001担保支付 0002即时到账       db_column: PAY_SERVIC_TYPE 
     */ 	
	private java.lang.String payServicType;
    /**
     * 商户日期 外部订单必输       db_column: MER_DATE 
     */ 	
	private java.util.Date merDate;
    /**
     * 商户流水号 外部订单可填       db_column: MER_SEQ 
     */ 	
	private java.lang.String merSeq;
    /**
     * 商户代码 外部订单为外部商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 二级商户代码       db_column: SEC_MER_NO 
     */ 	
	private java.lang.String secMerNo;
    /**
     * 商户订单号 外部订单必输       db_column: OUTER_ORDER_NO 
     */ 	
	private java.lang.String outerOrderNo;
    /**
     * 商户订单生成日期 订单生成时间，格式为yyyyMMdd  hh：mm：ss。该时间取自商户服务器       db_column: OUTER_ORDER_START_DATE 
     */ 	
	private java.util.Date outerOrderStartDate;
    /**
     * 商户订单失效日期 订单生成时间，格式为yyyyMMdd  hh：mm：ss。该时间取自商户服务器       db_column: OUTER_ORDER_END_DATE 
     */ 	
	private java.util.Date outerOrderEndDate;
    /**
     * 订单类型 00：非订单管理 01：平台订单 02：外部预先生成订单 03：外部联机生成订单        db_column: ORDER_TYPE 
     */ 	
	private java.lang.String orderType;
    /**
     * 支付方式 支付交易时必填，参见附录支付方式       db_column: PAY_TYPE 
     */ 	
	private java.lang.String payType;
    /**
     * 支付订单号 平台生成唯一支付订单号       db_column: ORDER_NO 
     */ 	
	private java.lang.String orderNo;
    /**
     * 订单名称       db_column: ORDER_NAME 
     */ 	
	private java.lang.String orderName;
    /**
     * 交易代码 平台交易码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 用户ID 只有支付方式为11公众号支付时可为空，其他支付方式必输       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 订单创建日期 创建支付订单平台日期       db_column: ORDER_DATE 
     */ 	
	private java.util.Date orderDate;
    /**
     * 订单创建时间       db_column: ORDER_TIME 
     */ 	
	private java.util.Date orderTime;
    /**
     * 订单时效 订单等待支付时间 0：即时支付 >0：允许延时支付（分）       db_column: ORDER_LMT_TIME 
     */ 	
	private java.lang.Integer orderLmtTime;
    /**
     * 币种 CNY：人民币       db_column: CURR 
     */ 	
	private java.lang.String curr;
    /**
     * 交易金额 总交易金额=其他费用+商品费用       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 其他费用 其他费用说明       db_column: OTHER_TRAN_AMT 
     */ 	
	private java.math.BigDecimal otherTranAmt;
    /**
     * 商品费用       db_column: PRODUCT_AMT 
     */ 	
	private java.math.BigDecimal productAmt;
    /**
     * 商户手续费 应收商户手续费       db_column: MER_FEE_AMT 
     */ 	
	private java.math.BigDecimal merFeeAmt;
    /**
     * 客户手续费 应收客户手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 原交易日期 对冲正和撤销交易； 原支付交易的日期       db_column: ORI_DATE 
     */ 	
	private java.util.Date oriDate;
    /**
     * 原支付订单号 对冲正和撤销交易； 原支付交易的流水号       db_column: ORI_ORDER_NO 
     */ 	
	private java.lang.String oriOrderNo;
    /**
     * 累计退货额 退货的金额       db_column: EJECT_AMT 
     */ 	
	private java.math.BigDecimal ejectAmt;
    /**
     * 订单状态 见附录4.3,order_stat       db_column: ORDER_STAT 
     */ 	
	private java.lang.String orderStat;
    /**
     * 用户IP       db_column: SPBILL_CREATE_IP 
     */ 	
	private java.lang.String spbillCreateIp;
    /**
     * 异步通知路径 服务器主动通知商户网站里指定的页面http 路径       db_column: NOTIFY_URL 
     */ 	
	private java.lang.String notifyUrl;
    /**
     * 同步通知路径 处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。       db_column: RETURN_URL 
     */ 	
	private java.lang.String returnUrl;
    /**
     * 附言       db_column: TRANS_COMMENTS 
     */ 	
	private java.lang.String transComments;
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
     * 备用3       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
    /**
     * 对账标志 0-未对账 1-对账成功 2-对账不平 默认：0       db_column: CHK_FLAG 
     */ 	
	private java.lang.String chkFlag;
    /**
     * 对账批次号       db_column: CHK_BATCH_NO 
     */ 	
	private java.lang.String chkBatchNo;
    /**
     * 结算标志 默认：0 0：未结算 1：结算中 2：已结算       db_column: STL_FLAG 
     */ 	
	private java.lang.String stlFlag;
    /**
     * 对账日期       db_column: CHK_DATE 
     */ 	
	private java.util.Date chkDate;
    /**
     * 结算批次号       db_column: STL_BATCH_NO 
     */ 	
	private java.lang.String stlBatchNo;
    /**
     * 结算日期       db_column: STL_DATE 
     */ 	
	private java.util.Date stlDate;
    /**
     * 订单描述       db_column: ORDER_DES 
     */ 	
	private java.lang.String orderDes;
    /**
     * 用户ID，微信公众号支付用户在公众号中的唯一标识       db_column: OPEN_ID 
     */ 	
	private java.lang.String openId;
    /**
     * 支付完成时间       db_column: PAY_TIME 
     */ 	
	private java.util.Date payTime;
    /**
     * 支付账号       db_column: PAY_ACCT_NO 
     */ 	
	private java.lang.String payAcctNo;
    /**
     * 交易类型 01支付 02充值 03提现 04转账 09退款       db_column: TRANS_TYPE 
     */ 	
	private java.lang.String transType;
    /**
     * 清算标志默认：0 0：未清算  1：一级清算中  2：一级清算失败   3：一级清算完成  4：二级清算中 5：二级清算失败 6：二级清算完成 7：清算成功  8：待清算       db_column: CLEAR_FLAG 
     */ 	
	private java.lang.String clearFlag;
    /**
     * 清算批次号       db_column: CLEAR_BATCH_NO 
     */ 	
	private java.lang.String clearBatchNo;
    /**
     * 清算日期       db_column: CLEAR_DATE 
     */ 	
	private java.util.Date clearDate;
    /**
     * secMerFeeAmt       db_column: SEC_MER_FEE_AMT 
     */ 	
	private java.math.BigDecimal secMerFeeAmt;
    /**
     * 推广方       db_column: EXTENSION_PARTY 
     */ 	
	private java.lang.String extensionParty;
    /**
     * 支付渠道       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * (付款方账户类型)付款账号类型见附录4.3,ACCT_TYPE       db_column: PAYER_ACCT_TYPE 
     */ 	
	private java.lang.String payerAcctType;
    /**
     * 付款账号       db_column: PAYER_ACCT_NO 
     */ 	
	private java.lang.String payerAcctNo;
    /**
     * 收款账号类型见附录4.3,acct_type       db_column: PAYEE_ACCT_TYPE 
     */ 	
	private java.lang.String payeeAcctType;
    /**
     * 收款账号       db_column: PAYEE_ACCT_NO 
     */ 	
	private java.lang.String payeeAcctNo;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getPayServicType() {
		return this.payServicType;
	}
	
	public void setPayServicType(java.lang.String value) {
		this.payServicType = value;
	}
	
	
	public java.util.Date getMerDate() {
		return this.merDate;
	}
	
	public void setMerDate(java.util.Date value) {
		this.merDate = value;
	}
	
	
	public java.lang.String getMerSeq() {
		return this.merSeq;
	}
	
	public void setMerSeq(java.lang.String value) {
		this.merSeq = value;
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
	
	
	public java.lang.String getOuterOrderNo() {
		return this.outerOrderNo;
	}
	
	public void setOuterOrderNo(java.lang.String value) {
		this.outerOrderNo = value;
	}
	
	
	public java.util.Date getOuterOrderStartDate() {
		return this.outerOrderStartDate;
	}
	
	public void setOuterOrderStartDate(java.util.Date value) {
		this.outerOrderStartDate = value;
	}
	
	
	public java.util.Date getOuterOrderEndDate() {
		return this.outerOrderEndDate;
	}
	
	public void setOuterOrderEndDate(java.util.Date value) {
		this.outerOrderEndDate = value;
	}
	
	
	public java.lang.String getOrderType() {
		return this.orderType;
	}
	
	public void setOrderType(java.lang.String value) {
		this.orderType = value;
	}
	
	
	public java.lang.String getPayType() {
		return this.payType;
	}
	
	public void setPayType(java.lang.String value) {
		this.payType = value;
	}
	
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	
	public java.lang.String getOrderName() {
		return this.orderName;
	}
	
	public void setOrderName(java.lang.String value) {
		this.orderName = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.util.Date getOrderDate() {
		return this.orderDate;
	}
	
	public void setOrderDate(java.util.Date value) {
		this.orderDate = value;
	}
	
	
	public java.util.Date getOrderTime() {
		return this.orderTime;
	}
	
	public void setOrderTime(java.util.Date value) {
		this.orderTime = value;
	}
	
	
	public java.lang.Integer getOrderLmtTime() {
		return this.orderLmtTime;
	}
	
	public void setOrderLmtTime(java.lang.Integer value) {
		this.orderLmtTime = value;
	}
	
	
	public java.lang.String getCurr() {
		return this.curr;
	}
	
	public void setCurr(java.lang.String value) {
		this.curr = value;
	}
	
	
	public java.math.BigDecimal getTransAmt() {
		return this.transAmt;
	}
	
	public void setTransAmt(java.math.BigDecimal value) {
		this.transAmt = value;
	}
	
	
	public java.math.BigDecimal getOtherTranAmt() {
		return this.otherTranAmt;
	}
	
	public void setOtherTranAmt(java.math.BigDecimal value) {
		this.otherTranAmt = value;
	}
	
	
	public java.math.BigDecimal getProductAmt() {
		return this.productAmt;
	}
	
	public void setProductAmt(java.math.BigDecimal value) {
		this.productAmt = value;
	}
	
	
	public java.math.BigDecimal getMerFeeAmt() {
		return this.merFeeAmt;
	}
	
	public void setMerFeeAmt(java.math.BigDecimal value) {
		this.merFeeAmt = value;
	}
	
	
	public java.math.BigDecimal getFeeAmt() {
		return this.feeAmt;
	}
	
	public void setFeeAmt(java.math.BigDecimal value) {
		this.feeAmt = value;
	}
	
	
	public java.util.Date getOriDate() {
		return this.oriDate;
	}
	
	public void setOriDate(java.util.Date value) {
		this.oriDate = value;
	}
	
	
	public java.lang.String getOriOrderNo() {
		return this.oriOrderNo;
	}
	
	public void setOriOrderNo(java.lang.String value) {
		this.oriOrderNo = value;
	}
	
	
	public java.math.BigDecimal getEjectAmt() {
		return this.ejectAmt;
	}
	
	public void setEjectAmt(java.math.BigDecimal value) {
		this.ejectAmt = value;
	}
	
	
	public java.lang.String getOrderStat() {
		return this.orderStat;
	}
	
	public void setOrderStat(java.lang.String value) {
		this.orderStat = value;
	}
	
	
	public java.lang.String getSpbillCreateIp() {
		return this.spbillCreateIp;
	}
	
	public void setSpbillCreateIp(java.lang.String value) {
		this.spbillCreateIp = value;
	}
	
	
	public java.lang.String getNotifyUrl() {
		return this.notifyUrl;
	}
	
	public void setNotifyUrl(java.lang.String value) {
		this.notifyUrl = value;
	}
	
	
	public java.lang.String getReturnUrl() {
		return this.returnUrl;
	}
	
	public void setReturnUrl(java.lang.String value) {
		this.returnUrl = value;
	}
	
	
	public java.lang.String getTransComments() {
		return this.transComments;
	}
	
	public void setTransComments(java.lang.String value) {
		this.transComments = value;
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
	
	
	public java.lang.String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(java.lang.String value) {
		this.remark3 = value;
	}
	
	
	public java.lang.String getChkFlag() {
		return this.chkFlag;
	}
	
	public void setChkFlag(java.lang.String value) {
		this.chkFlag = value;
	}
	
	
	public java.lang.String getChkBatchNo() {
		return this.chkBatchNo;
	}
	
	public void setChkBatchNo(java.lang.String value) {
		this.chkBatchNo = value;
	}
	
	
	public java.lang.String getStlFlag() {
		return this.stlFlag;
	}
	
	public void setStlFlag(java.lang.String value) {
		this.stlFlag = value;
	}
	
	
	public java.util.Date getChkDate() {
		return this.chkDate;
	}
	
	public void setChkDate(java.util.Date value) {
		this.chkDate = value;
	}
	
	
	public java.lang.String getStlBatchNo() {
		return this.stlBatchNo;
	}
	
	public void setStlBatchNo(java.lang.String value) {
		this.stlBatchNo = value;
	}
	
	
	public java.util.Date getStlDate() {
		return this.stlDate;
	}
	
	public void setStlDate(java.util.Date value) {
		this.stlDate = value;
	}
	
	
	public java.lang.String getOrderDes() {
		return this.orderDes;
	}
	
	public void setOrderDes(java.lang.String value) {
		this.orderDes = value;
	}
	
	
	public java.lang.String getOpenId() {
		return this.openId;
	}
	
	public void setOpenId(java.lang.String value) {
		this.openId = value;
	}
	
	
	public java.util.Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}
	
	
	public java.lang.String getPayAcctNo() {
		return this.payAcctNo;
	}
	
	public void setPayAcctNo(java.lang.String value) {
		this.payAcctNo = value;
	}
	
	
	public java.lang.String getTransType() {
		return this.transType;
	}
	
	public void setTransType(java.lang.String value) {
		this.transType = value;
	}
	
	
	public java.lang.String getClearFlag() {
		return this.clearFlag;
	}
	
	public void setClearFlag(java.lang.String value) {
		this.clearFlag = value;
	}
	
	
	public java.lang.String getClearBatchNo() {
		return this.clearBatchNo;
	}
	
	public void setClearBatchNo(java.lang.String value) {
		this.clearBatchNo = value;
	}
	
	
	public java.util.Date getClearDate() {
		return this.clearDate;
	}
	
	public void setClearDate(java.util.Date value) {
		this.clearDate = value;
	}
	
	
	public java.math.BigDecimal getSecMerFeeAmt() {
		return this.secMerFeeAmt;
	}
	
	public void setSecMerFeeAmt(java.math.BigDecimal value) {
		this.secMerFeeAmt = value;
	}
	
	
	public java.lang.String getExtensionParty() {
		return this.extensionParty;
	}
	
	public void setExtensionParty(java.lang.String value) {
		this.extensionParty = value;
	}
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getPayerAcctType() {
		return this.payerAcctType;
	}
	
	public void setPayerAcctType(java.lang.String value) {
		this.payerAcctType = value;
	}
	
	
	public java.lang.String getPayerAcctNo() {
		return this.payerAcctNo;
	}
	
	public void setPayerAcctNo(java.lang.String value) {
		this.payerAcctNo = value;
	}
	
	
	public java.lang.String getPayeeAcctType() {
		return this.payeeAcctType;
	}
	
	public void setPayeeAcctType(java.lang.String value) {
		this.payeeAcctType = value;
	}
	
	
	public java.lang.String getPayeeAcctNo() {
		return this.payeeAcctNo;
	}
	
	public void setPayeeAcctNo(java.lang.String value) {
		this.payeeAcctNo = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

