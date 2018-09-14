package com.upay.batch.stepservice.chk.unionmer.bean;

public class ChkUnionFlowBeanMer {


	private String tranCode;          	//交易代码
	private String acqinsCode;          	//代理机构标识码

	private String hairinsCode;				//发送机构标识码

	private String traceNo ;				//系统跟踪号

	private String txnTime; 				//交易传输时间

	private String payCardNo;				//账号

//	private String txnAmtno;				//交易金额

	private String txnAmt;					//交易金额

	private String merCatCode;					//商户类别

	private String termType;					//终端类型

	private String queryId;				//查询流水号

	private String payTypeOld;					//支付方式（旧）

	private String orderId;				//商户订单号

	private String payCardType;				//支付卡类型

	private String oriSysTranSeq;				//原始交易的系统跟踪号

	private String pmyTxnTime;					//原始交易时间

	private String merFee;				//商户手续费

	private String stlAmt;				//结算金额

	private String payType;					//支付方式

	private String groupMerCode;					//集团商户代码

	private String txnType;				//交易类型

	private String txnSubType;					//交易子类

	private String bizType;			//业务类型

	private String accType;				//帐号类型

	private String billType;					//账单类型

	private String billNo;					//账单号码

	private String interactMode;				//交互方式

	private String origQryId;				//原交易查询流水号

	private String merId;			//商户代码

	private String seMethod;				//分账入账方式

	private String subMerId;				//二级商户代码

	private String subMerAbbr;					//二级商户简称

	private String seAmt;				//二级商户分账入账金额

	private String stlTwoAmt;				//清算净额

	private String termId; 				//终端号

	private String 	merReserved;					//商户自定义域

	private String disAmount;            	//优惠金额

	private String billAmt;            	//发票金额

	private String byStagesFee;            	//分期付款附加手续费

	private String byStagesNum;            	//分期付款期数

	private String TranMedium;            	//交易介质

	private String oriOrderNo;            	//原始交易订单号

	private String keepUse;            	//保留使用

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

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
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

	public String getMerCatCode() {
		return merCatCode;
	}

	public void setMerCatCode(String merCatCode) {
		this.merCatCode = merCatCode;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getPayTypeOld() {
		return payTypeOld;
	}

	public void setPayTypeOld(String payTypeOld) {
		this.payTypeOld = payTypeOld;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayCardType() {
		return payCardType;
	}

	public void setPayCardType(String payCardType) {
		this.payCardType = payCardType;
	}

	public String getOriSysTranSeq() {
		return oriSysTranSeq;
	}

	public void setOriSysTranSeq(String oriSysTranSeq) {
		this.oriSysTranSeq = oriSysTranSeq;
	}

	public String getPmyTxnTime() {
		return pmyTxnTime;
	}

	public void setPmyTxnTime(String pmyTxnTime) {
		this.pmyTxnTime = pmyTxnTime;
	}

	public String getMerFee() {
		return merFee;
	}

	public void setMerFee(String merFee) {
		this.merFee = merFee;
	}

	public String getStlAmt() {
		return stlAmt;
	}

	public void setStlAmt(String stlAmt) {
		this.stlAmt = stlAmt;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getGroupMerCode() {
		return groupMerCode;
	}

	public void setGroupMerCode(String groupMerCode) {
		this.groupMerCode = groupMerCode;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getInteractMode() {
		return interactMode;
	}

	public void setInteractMode(String interactMode) {
		this.interactMode = interactMode;
	}

	public String getOrigQryId() {
		return origQryId;
	}

	public void setOrigQryId(String origQryId) {
		this.origQryId = origQryId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getSeMethod() {
		return seMethod;
	}

	public void setSeMethod(String seMethod) {
		this.seMethod = seMethod;
	}

	public String getSubMerId() {
		return subMerId;
	}

	public void setSubMerId(String subMerId) {
		this.subMerId = subMerId;
	}

	public String getSubMerAbbr() {
		return subMerAbbr;
	}

	public void setSubMerAbbr(String subMerAbbr) {
		this.subMerAbbr = subMerAbbr;
	}

	public String getSeAmt() {
		return seAmt;
	}

	public void setSeAmt(String seAmt) {
		this.seAmt = seAmt;
	}

	public String getStlTwoAmt() {
		return stlTwoAmt;
	}

	public void setStlTwoAmt(String stlTwoAmt) {
		this.stlTwoAmt = stlTwoAmt;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getMerReserved() {
		return merReserved;
	}

	public void setMerReserved(String merReserved) {
		this.merReserved = merReserved;
	}

	public String getDisAmount() {
		return disAmount;
	}

	public void setDisAmount(String disAmount) {
		this.disAmount = disAmount;
	}

	public String getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(String billAmt) {
		this.billAmt = billAmt;
	}

	public String getByStagesFee() {
		return byStagesFee;
	}

	public void setByStagesFee(String byStagesFee) {
		this.byStagesFee = byStagesFee;
	}

	public String getByStagesNum() {
		return byStagesNum;
	}

	public void setByStagesNum(String byStagesNum) {
		this.byStagesNum = byStagesNum;
	}

	public String getTranMedium() {
		return TranMedium;
	}

	public void setTranMedium(String tranMedium) {
		TranMedium = tranMedium;
	}

	public String getOriOrderNo() {
		return oriOrderNo;
	}

	public void setOriOrderNo(String oriOrderNo) {
		this.oriOrderNo = oriOrderNo;
	}

	public String getKeepUse() {
		return keepUse;
	}

	public void setKeepUse(String keepUse) {
		this.keepUse = keepUse;
	}


}
