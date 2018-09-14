package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseQueryDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 	报表交易明细、汇总查询
 * 
 * @author hry
 * 
 */
public class ReportOrderDetailQueryDto extends BaseQueryDto {
    
	 /*汇总笔数*/
	private String sumTrade;
	   /*汇总金额*/
    private BigDecimal sumAmount;
	   /*交易机构*/
    private String BranchId;
	   /*交易日期*/
    private String tranDt;
	   /*交易时间*/
    private String tranTm;
	   /*交易名称*/
    private String tradeName;
	   /*交易流水*/
    private String cnsmSysSeqNo;
	   /*收款账号*/
    private String accNumber;   
	   /*收款户名*/
    private String accName;    
	   /*收款开户行*/
    private String accOpeningBank;    
	   /*收款子账号*/
    private String shroffAccNumber;    
	   /*收款子户名*/
    private String shroffAccName;    
	       /*付款账号*/
	 private String payNumber;   
		   /*付款户名*/
	 private String payName;    
		   /*付款开户行*/
	 private String payOpeningBank;    
		   /*付款子账号*/
	 private String shroffPayNumber;    
		   /*收款子户名*/
	 private String shroffPayName;  
	 
	   /*交易渠道*/
     private String chnlTp;  
	   /*事后监督*/
     private String postSupervision;  
	   /*授权员*/
     private String authorizedOfficer;  
	   /*复核员*/
     private String checker; 
	   /*操作员*/
     private String operator; 
	   /*文件路径*/
     private String filePath;
	   /*交易代码*/ 
     private String tradeNo;
     
     private String fileFlg;
    
     private String tlrNo;
    public String getTlrNo() {
		return tlrNo;
	}

	public void setTlrNo(String tlrNo) {
		this.tlrNo = tlrNo;
	}

	public String getFileFlg() {
		return fileFlg;
	}

	public void setFileFlg(String fileFlg) {
		this.fileFlg = fileFlg;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}




	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccOpeningBank() {
		return accOpeningBank;
	}

	public void setAccOpeningBank(String accOpeningBank) {
		this.accOpeningBank = accOpeningBank;
	}

	public String getShroffAccNumber() {
		return shroffAccNumber;
	}

	public void setShroffAccNumber(String shroffAccNumber) {
		this.shroffAccNumber = shroffAccNumber;
	}

	public String getShroffAccName() {
		return shroffAccName;
	}

	public void setShroffAccName(String shroffAccName) {
		this.shroffAccName = shroffAccName;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayOpeningBank() {
		return payOpeningBank;
	}

	public void setPayOpeningBank(String payOpeningBank) {
		this.payOpeningBank = payOpeningBank;
	}

	public String getShroffPayNumber() {
		return shroffPayNumber;
	}

	public void setShroffPayNumber(String shroffPayNumber) {
		this.shroffPayNumber = shroffPayNumber;
	}

	public String getShroffPayName() {
		return shroffPayName;
	}

	public void setShroffPayName(String shroffPayName) {
		this.shroffPayName = shroffPayName;
	}

	public String getBranchId() {
		return BranchId;
	}

	public void setBranchId(String branchId) {
		BranchId = branchId;
	}

	public String getTranDt() {
		return tranDt;
	}

	public void setTranDt(String tranDt) {
		this.tranDt = tranDt;
	}

	public String getTranTm() {
		return tranTm;
	}

	public void setTranTm(String tranTm) {
		this.tranTm = tranTm;
	}

	public String getCnsmSysSeqNo() {
		return cnsmSysSeqNo;
	}

	public void setCnsmSysSeqNo(String cnsmSysSeqNo) {
		this.cnsmSysSeqNo = cnsmSysSeqNo;
	}

	public String getChnlTp() {
		return chnlTp;
	}

	public void setChnlTp(String chnlTp) {
		this.chnlTp = chnlTp;
	}

	public String getPostSupervision() {
		return postSupervision;
	}

	public void setPostSupervision(String postSupervision) {
		this.postSupervision = postSupervision;
	}

	public String getAuthorizedOfficer() {
		return authorizedOfficer;
	}

	public void setAuthorizedOfficer(String authorizedOfficer) {
		this.authorizedOfficer = authorizedOfficer;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String merNo;
    
    private String beginTime;
    
    private String endTime;
    private String trades;
    
    public String getTrades() {
		return trades;
	}

	public void setTrades(String trades) {
		this.trades = trades;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	private String amount;
    
	private List<Map<String,String>> orderDetailList;
	
	private List<Map<String,String>> orderCountList;

	public String getSumTrade() {
		return sumTrade;
	}

	public List<Map<String, String>> getOrderCountList() {
		return orderCountList;
	}

	public void setOrderCountList(List<Map<String, String>> orderCountList) {
		this.orderCountList = orderCountList;
	}

	public void setSumTrade(String sumTrade) {
		this.sumTrade = sumTrade;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public List<Map<String, String>> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<Map<String, String>> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

   
   

}
