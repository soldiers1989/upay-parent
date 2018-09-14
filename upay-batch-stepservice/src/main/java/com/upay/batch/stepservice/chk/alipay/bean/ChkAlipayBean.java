package com.upay.batch.stepservice.chk.alipay.bean;

import java.util.Date;

public class ChkAlipayBean {
	private String tradeNo;		  //支付宝交易号
	private String outTradeNo;    //商户订单号
	private String subType;       //业务类型
	private String goodsName;     //商品名称
	private Date createTime;    //创建时间
	private Date endTime;       //完成时间
	private String storeId;       //门店编号
	private String storeName;     //门店名称
	private String operatorId;    //操作员
	private String terminalId;    //终端号
	private String payErNo;       //对方账户
	private String orderAmt;      //订单金额（元）
	private String txnAmt;        //商家实收（元）
	private String redPgAmt;      //支付宝红包（元）
	private String ctAmt;         //集分宝（元）
	private String aliPreferAmt;  //支付宝优惠（元）
	private String merPreferAmt;  //商家优惠（元）
	private String ticketAmt;     //券核销金额（元）
	private String ticketName;    //券名称
	private String merRedPgAmt;   //商家红包消费金额（元）
	private String cardConsureAmt;//卡消费金额（元）
	private String refundNo;      //退款批次号/请求号
	private String serviceCharge; //服务费（元）
	private String receiptAmt;        //实收净额（元）
	private String merchantId;        //商户识别号
	private String payType;        //交易方式
	
	private String remark;        //备注
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getPayErNo() {
		return payErNo;
	}
	public void setPayErNo(String payErNo) {
		this.payErNo = payErNo;
	}

	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getRedPgAmt() {
		return redPgAmt;
	}
	public void setRedPgAmt(String redPgAmt) {
		this.redPgAmt = redPgAmt;
	}
	public String getCtAmt() {
		return ctAmt;
	}
	public void setCtAmt(String ctAmt) {
		this.ctAmt = ctAmt;
	}
	public String getAliPreferAmt() {
		return aliPreferAmt;
	}
	public void setAliPreferAmt(String aliPreferAmt) {
		this.aliPreferAmt = aliPreferAmt;
	}
	public String getMerPreferAmt() {
		return merPreferAmt;
	}
	public void setMerPreferAmt(String merPreferAmt) {
		this.merPreferAmt = merPreferAmt;
	}
	public String getTicketAmt() {
		return ticketAmt;
	}
	public void setTicketAmt(String ticketAmt) {
		this.ticketAmt = ticketAmt;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public String getMerRedPgAmt() {
		return merRedPgAmt;
	}
	public void setMerRedPgAmt(String merRedPgAmt) {
		this.merRedPgAmt = merRedPgAmt;
	}
	public String getCardConsureAmt() {
		return cardConsureAmt;
	}
	public void setCardConsureAmt(String cardConsureAmt) {
		this.cardConsureAmt = cardConsureAmt;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	public String getReceiptAmt() {
		return receiptAmt;
	}
	public void setReceiptAmt(String receiptAmt) {
		this.receiptAmt = receiptAmt;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
