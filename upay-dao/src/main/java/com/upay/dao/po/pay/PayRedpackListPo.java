package com.upay.dao.po.pay;

import com.pactera.dipper.po.BasePo;

public class PayRedpackListPo extends BasePo {
	private static final long serialVersionUID = 1L;
	// alias
	public static final String TABLE_ALIAS = "微信红包记录";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_OUTER_ORDER_NO = "商户订单号";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_MSGAPPID = "触达用户appid";
	public static final String ALIAS_SEND_NAME = "商户名称";
	public static final String ALIAS_RE_OPENID = "用户openid";
	public static final String ALIAS_TOTAL_AMOUNT = "付款金额";
	public static final String ALIAS_TOTAL_NUM = "红包发放总人数";
	public static final String ALIAS_AMT_TYPE = "红包金额设置方式";
	public static final String ALIAS_WISHING = "红包祝福语";
	public static final String ALIAS_CLIENT_IP = "Ip地址";
	public static final String ALIAS_ACT_NAME = "活动名称";
	public static final String ALIAS_SCENE_ID = "场景id";
	public static final String ALIAS_RISK_INFO = "活动信息";
	public static final String ALIAS_CONSUME_MCH_ID = "扣钱方mchID";
	public static final String ALIAS_SEND_LISTID = "微信单号";
	public static final String ALIAS_ORDER_NO = "订单号";
	public static final String ALIAS_ORDER_STATE = "订单状态";
	public static final String ALIAS_REDPACK_TYPE = "红包类型 单个：single 普通红包 多个：group裂变红包";
	public static final String ALIAS_TRANS_DATE = "交易日期";
	public static final String ALIAS_TRANS_TIME = "交易时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_REMARK1 = "备注1";
	public static final String ALIAS_REMARK2 = "备注2";

	// columns START
	/**
	 * 商户订单号 db_column: OUTER_ORDER_NO
	 */
	private java.lang.String outerOrderNo;
	/**
	 * 商户号 db_column: MER_NO
	 */
	private java.lang.String merNo;
	/**
	 * 触达用户appid db_column: MSGAPPID
	 */
	private java.lang.String msgappid;
	/**
	 * 商户名称 db_column: SEND_NAME
	 */
	private java.lang.String sendName;
	/**
	 * 用户openid db_column: RE_OPENID
	 */
	private java.lang.String reOpenid;
	/**
	 * 付款金额 db_column: TOTAL_AMOUNT
	 */
	private java.math.BigDecimal totalAmount;
	/**
	 * 红包发放总人数 db_column: TOTAL_NUM
	 */
	private Integer totalNum;
	/**
	 * 红包金额设置方式 db_column: AMT_TYPE
	 */
	private java.lang.String amtType;
	/**
	 * 红包祝福语 db_column: WISHING
	 */
	private java.lang.String wishing;
	/**
	 * Ip地址 db_column: CLIENT_IP
	 */
	private java.lang.String clientIp;
	/**
	 * 活动名称 db_column: ACT_NAME
	 */
	private java.lang.String actName;
	/**
	 * 场景id db_column: SCENE_ID
	 */
	private java.lang.String sceneId;
	/**
	 * 活动信息 db_column: RISK_INFO
	 */
	private java.lang.String riskInfo;
	/**
	 * 扣钱方mchID db_column: CONSUME_MCH_ID
	 */
	private java.lang.String consumeMchId;
	/**
	 * 微信单号 db_column: SEND_LISTID
	 */
	private java.lang.String sendListid;
	/**
	 * 订单号 db_column: ORDER_NO
	 */
	private java.lang.String orderNo;
	/**
	 * 订单状态 db_column: ORDER_STATE
	 */
	private java.lang.String orderState;
	/**
	 * 红包类型 单个：single 普通红包 多个：group裂变红包 db_column: REDPACK_TYPE
	 */
	private java.lang.String redpackType;
	/**
	 * 交易日期 db_column: TRANS_DATE
	 */
	private java.util.Date transDate;
	/**
	 * 交易时间 db_column: TRANS_TIME
	 */
	private java.util.Date transTime;
	/**
	 * 备注 db_column: REMARK
	 */
	private java.lang.String remark;
	/**
	 * 备注1 db_column: REMARK1
	 */
	private java.lang.String remark1;
	/**
	 * 备注2 db_column: REMARK2
	 */
	private java.lang.String remark2;

	// columns END

	public java.lang.String getOuterOrderNo() {
		return this.outerOrderNo;
	}

	public void setOuterOrderNo(java.lang.String value) {
		this.outerOrderNo = value;
	}

	public java.lang.String getMerNo() {
		return this.merNo;
	}

	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}

	public java.lang.String getMsgappid() {
		return this.msgappid;
	}

	public void setMsgappid(java.lang.String value) {
		this.msgappid = value;
	}

	public java.lang.String getSendName() {
		return this.sendName;
	}

	public void setSendName(java.lang.String value) {
		this.sendName = value;
	}

	public java.lang.String getReOpenid() {
		return this.reOpenid;
	}

	public void setReOpenid(java.lang.String value) {
		this.reOpenid = value;
	}

	public java.math.BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(java.math.BigDecimal value) {
		this.totalAmount = value;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public java.lang.String getAmtType() {
		return this.amtType;
	}

	public void setAmtType(java.lang.String value) {
		this.amtType = value;
	}

	public java.lang.String getWishing() {
		return this.wishing;
	}

	public void setWishing(java.lang.String value) {
		this.wishing = value;
	}

	public java.lang.String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(java.lang.String value) {
		this.clientIp = value;
	}

	public java.lang.String getActName() {
		return this.actName;
	}

	public void setActName(java.lang.String value) {
		this.actName = value;
	}

	public java.lang.String getSceneId() {
		return this.sceneId;
	}

	public void setSceneId(java.lang.String value) {
		this.sceneId = value;
	}

	public java.lang.String getRiskInfo() {
		return this.riskInfo;
	}

	public void setRiskInfo(java.lang.String value) {
		this.riskInfo = value;
	}

	public java.lang.String getConsumeMchId() {
		return this.consumeMchId;
	}

	public void setConsumeMchId(java.lang.String value) {
		this.consumeMchId = value;
	}

	public java.lang.String getSendListid() {
		return this.sendListid;
	}

	public void setSendListid(java.lang.String value) {
		this.sendListid = value;
	}

	public java.lang.String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}

	public java.lang.String getOrderState() {
		return this.orderState;
	}

	public void setOrderState(java.lang.String value) {
		this.orderState = value;
	}

	public java.lang.String getRedpackType() {
		return this.redpackType;
	}

	public void setRedpackType(java.lang.String value) {
		this.redpackType = value;
	}

	public java.util.Date getTransDate() {
		return this.transDate;
	}

	public void setTransDate(java.util.Date value) {
		this.transDate = value;
	}

	public java.util.Date getTransTime() {
		return this.transTime;
	}

	public void setTransTime(java.util.Date value) {
		this.transTime = value;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String value) {
		this.remark = value;
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
