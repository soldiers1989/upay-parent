package com.upay.batch.stepservice.chk.union.bean;

public class ChkUnionFlowBean {
	
	private String tranCode;          	//交易代码
	
	private String acqinsCode;          	//代理机构标识码
		
	private String hairinsCode;				//发送机构标识码 
	
	private String traceNo ;				//系统跟踪号
	
	private String traceTime; 				//交易传输时间
	
	private String payCardNo;				//主账号
	
    private String txnAmt;				//交易金额
	
	private String partCollectionAmt;				//部分代收时的承兑金额

	private String cardholderTransactionFee;				//持卡人交易手续费
	
	private String messageType;				//报文类型
	
	private String tranCodeType;				//交易码类型
	
	private String merCatCode;				//商户类型
	
	private String terminalCode;				//受卡机终端标识码
	
	private String recipientIDCode;				//受卡方标识码
	
	private String seachReferenceCode;				//检索参考号码
	
	private String serviceConditionCode;				//服务点条件码
	
	private String authorizedReqCode;				//授权应答码
	
	private String receiveCode;				//接收机构标识码
	
	private String oriTraceNo;				//原始交易的系统跟踪号
	
	private String reqCode;				//交易返回码
	
	private String serviceInputType;				//服务点输入方式
	
	private String acqinsPayerExchangeFee;				//受理方应收交换费
	
	private String acqinsPayExchangeFee;				//受理方应付交换费
	
	private String transferFee;				//转接清算费
	
	private String singleDoubleFlag;				//单双转换标志
	
	private String cardSerialNumber;				//卡片序列号
	
	private String terminalTeadingAbility;				//终端读取能力
	
	private String icCardConditionCode;				//ic卡条件代码
	
	private String oriTxnTime;				//原始交易日期时间
	
	private String issuerIDNumber;				//发卡机构标识码
	
	private String tradeMark;				//交易地域标志
	
	private String terminalType;				//终端类型
	
	private String eciFlag;				//ECI标志
	
	private String paymentInstalments;				//分期付款附加手续费
	
	private String otherInformation;				//其他信息
	private String turnCardNumber;				//转入卡卡号
	private String instalmentPeriod;				//分期付款期数
	private String orderNo;				//订单号
	private String payType;				//支付方式

	private String reserve1;				//保留使用
	private String reserve2;				//保留使用
	private String reserve3;				//保留使用
	private String reserve4;				//保留使用
	private String reserve5;				//保留使用
	private String reserve6;				//保留使用
	private String reserve7;				//保留使用
	private String accountLevel;				//账户等级
	private String cabinet;				//是否柜面核身
	private String reserve8;				//保留使用


	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}

	public String getReserve6() {
		return reserve6;
	}

	public void setReserve6(String reserve6) {
		this.reserve6 = reserve6;
	}

	public String getReserve7() {
		return reserve7;
	}

	public void setReserve7(String reserve7) {
		this.reserve7 = reserve7;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getCabinet() {
		return cabinet;
	}

	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}

	public String getReserve8() {
		return reserve8;
	}

	public void setReserve8(String reserve8) {
		this.reserve8 = reserve8;
	}

	public String getTurnCardNumber() {
		return turnCardNumber;
	}

	public void setTurnCardNumber(String turnCardNumber) {
		this.turnCardNumber = turnCardNumber;
	}

	public String getInstalmentPeriod() {
		return instalmentPeriod;
	}

	public void setInstalmentPeriod(String instalmentPeriod) {
		this.instalmentPeriod = instalmentPeriod;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getAcqinsCode() {
		return acqinsCode;
	}

	public void setAcqinsCode(String acqinsCode) {
		this.acqinsCode = acqinsCode;
	}

	public String getHairinsCode() {
		return hairinsCode;
	}

	public void setHairinsCode(String hairinsCode) {
		this.hairinsCode = hairinsCode;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}



	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getPayCardNo() {
		return payCardNo;
	}

	public void setPayCardNo(String payCardNo) {
		this.payCardNo = payCardNo;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getPartCollectionAmt() {
		return partCollectionAmt;
	}

	public void setPartCollectionAmt(String partCollectionAmt) {
		this.partCollectionAmt = partCollectionAmt;
	}

	public String getCardholderTransactionFee() {
		return cardholderTransactionFee;
	}

	public void setCardholderTransactionFee(String cardholderTransactionFee) {
		this.cardholderTransactionFee = cardholderTransactionFee;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTranCodeType() {
		return tranCodeType;
	}

	public void setTranCodeType(String tranCodeType) {
		this.tranCodeType = tranCodeType;
	}

	public String getMerCatCode() {
		return merCatCode;
	}

	public void setMerCatCode(String merCatCode) {
		this.merCatCode = merCatCode;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}


	public String getRecipientIDCode() {
		return recipientIDCode;
	}

	public void setRecipientIDCode(String recipientIDCode) {
		this.recipientIDCode = recipientIDCode;
	}

	public String getSeachReferenceCode() {
		return seachReferenceCode;
	}

	public void setSeachReferenceCode(String seachReferenceCode) {
		this.seachReferenceCode = seachReferenceCode;
	}

	public String getServiceConditionCode() {
		return serviceConditionCode;
	}

	public void setServiceConditionCode(String serviceConditionCode) {
		this.serviceConditionCode = serviceConditionCode;
	}

	public String getAuthorizedReqCode() {
		return authorizedReqCode;
	}

	public void setAuthorizedReqCode(String authorizedReqCode) {
		this.authorizedReqCode = authorizedReqCode;
	}


	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}

	public String getOriTraceNo() {
		return oriTraceNo;
	}

	public void setOriTraceNo(String oriTraceNo) {
		this.oriTraceNo = oriTraceNo;
	}

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public String getServiceInputType() {
		return serviceInputType;
	}

	public void setServiceInputType(String serviceInputType) {
		this.serviceInputType = serviceInputType;
	}

	public String getAcqinsPayerExchangeFee() {
		return acqinsPayerExchangeFee;
	}

	public void setAcqinsPayerExchangeFee(String acqinsPayerExchangeFee) {
		this.acqinsPayerExchangeFee = acqinsPayerExchangeFee;
	}

	public String getAcqinsPayExchangeFee() {
		return acqinsPayExchangeFee;
	}

	public void setAcqinsPayExchangeFee(String acqinsPayExchangeFee) {
		this.acqinsPayExchangeFee = acqinsPayExchangeFee;
	}

	public String getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}

	public String getSingleDoubleFlag() {
		return singleDoubleFlag;
	}

	public void setSingleDoubleFlag(String singleDoubleFlag) {
		this.singleDoubleFlag = singleDoubleFlag;
	}

	public String getCardSerialNumber() {
		return cardSerialNumber;
	}

	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}

	public String getTerminalTeadingAbility() {
		return terminalTeadingAbility;
	}

	public void setTerminalTeadingAbility(String terminalTeadingAbility) {
		this.terminalTeadingAbility = terminalTeadingAbility;
	}

	public String getIcCardConditionCode() {
		return icCardConditionCode;
	}

	public void setIcCardConditionCode(String icCardConditionCode) {
		this.icCardConditionCode = icCardConditionCode;
	}

	public String getOriTxnTime() {
		return oriTxnTime;
	}

	public void setOriTxnTime(String oriTxnTime) {
		this.oriTxnTime = oriTxnTime;
	}

	public String getIssuerIDNumber() {
		return issuerIDNumber;
	}

	public void setIssuerIDNumber(String issuerIDNumber) {
		this.issuerIDNumber = issuerIDNumber;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getEciFlag() {
		return eciFlag;
	}

	public void setEciFlag(String eciFlag) {
		this.eciFlag = eciFlag;
	}

	public String getPaymentInstalments() {
		return paymentInstalments;
	}

	public void setPaymentInstalments(String paymentInstalments) {
		this.paymentInstalments = paymentInstalments;
	}

	public String getOtherInformation() {
		return otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}

	
}
