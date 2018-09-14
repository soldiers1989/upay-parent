package com.upay.busi.gnr.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;

public class ApcNoticeReqDto extends BaseDto {
	private String outRouteSeq;

	private String respMsg;

	private String respCode;
	
	private String isSuccess;
	
	private String txnAmt;
	
	private String merId;
	private String payerInfo;
	private String payerAcctNo;
	private String settleKey;
	

	/** 是否通知核心系统 */
    private String isNotifyCoreSys;

	/**
	 * 返回码
	 */
	private String returnCode;
	/**
	 * 交易状态
	 */
	private String tradeState;
	/**
	 * 返回的信息
	 */
	private String returnMsg;
	/**
	 * 本系统订单状态
	 */
	private String orderStat;

	/**
	 * 业务结果
	 */
	private String resultCode;
	/**
	 * 商户号
	 */
	private String mchId;
	/**
	 * 设备号
	 */
	private String deviceInfo;
	/**
	 * 随机字符串
	 */
	private String nonceStr;
	/**
	 * 错误代码
	 */
	private String errCode;
	/**
	 * 错误信息
	 */
	private String errCodedes;
	/**
	 * 是否关注公众账号
	 */
	private String isSubscribe;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 付款银行
	 */
	private String bankType;
	/**
	 * 总金额
	 */
	private String totalFee;
	/**
	 * 现金券金额
	 */
	private String couponFee;
	/**
	 * 货币种类
	 */
	private String feeType;
	/**
	 * 渠道支付订单号(通道交易流水号) transactionId
	 */
	private String routeSeq;
	/**
	 * 支付订单号
	 */
	private String orderNo;

	private String transSubSeq;
	/**
	 * 商户数据包
	 */
	private String attach;
	/**
	 * 支付完成时间
	 */
	private String timeEnd;
	/**
	 * 商户签名
	 */
	private String sign;
	/**
	 * 回调地址
	 */
	private String notifyUrl;
	/**
	 * 本系统交易状态
	 */
	private String transStat;

	/**
	 * 交易类型
	 */
	private String payType;

	private BigDecimal transAmt;

	private String chnlOrderTime;

	private String orderDes;

	/** 付款银行 */
	private String payerBankName;

	/** 通道方交易状态 */
	private String routeTransStat;
	/** 支付完成时间 */
	private Date payTime;

	public String getPayerBankName() {
		return payerBankName;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public String getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSeq(String routeSeq) {
		this.routeSeq = routeSeq;
	}

	public String getTransSubSeq() {
		return transSubSeq;
	}

	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getChnlOrderTime() {
		return chnlOrderTime;
	}

	public void setChnlOrderTime(String chnlOrderTime) {
		this.chnlOrderTime = chnlOrderTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public String getTransStat() {
		return transStat;
	}

	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodedes() {
		return errCodedes;
	}

	public void setErrCodedes(String errCodedes) {
		this.errCodedes = errCodedes;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getRouteTransStat() {
		return routeTransStat;
	}

	public void setRouteTransStat(String routeTransStat) {
		this.routeTransStat = routeTransStat;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getPayerInfo() {
		return payerInfo;
	}

	public void setPayerInfo(String payerInfo) {
		this.payerInfo = payerInfo;
	}
	

	public String getPayerAcctNo() {
		return payerAcctNo;
	}

	public void setPayerAcctNo(String payerAcctNo) {
		this.payerAcctNo = payerAcctNo;
	}

	public String getIsNotifyCoreSys() {
		return isNotifyCoreSys;
	}

	public void setIsNotifyCoreSys(String isNotifyCoreSys) {
		this.isNotifyCoreSys = isNotifyCoreSys;
	}
    public String getSettleKey() {
		return settleKey;
	}

	public void setSettleKey(String settleKey) {
		this.settleKey = settleKey;
	}
	public String getOutRouteSeq() {
		return outRouteSeq;
	}

	public void setOutRouteSeq(String outRouteSeq) {
		this.outRouteSeq = outRouteSeq;
	}
}
