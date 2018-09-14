package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Guo on 16/8/25.
 */
public class ReconciliationDocDownloadDto extends BaseDto {

    /**一级商户*/
    private String merNo;

    /**二级商户*/
    private String secMerNo;

    /**
     * 下载对账单的日期，格式： yyyyMMdd
     */
    private String chkDate;
    
    private String orderDate;
    private String extensionParty;
    /**
     * 下载对账单类型
     0001：返回当日所有订单
     0002：返回当日成功支付的订单
     0003：返回当日退款的订单
     默认：0001；
     */
    private String orderType;

    /**每页记录数*/
    private Integer pageSize;

    /**起始记录*/
    private Integer offset;

    /**返回结果记录数*/
    private Integer count;

    /**订单日期*/
    private Date txnDate;

    /**汇总金额*/
    private BigDecimal totTransAmt;

    /**订单总金额*/
    private BigDecimal txnAmt;

    /**平台交易流水号,支付订单号*/
    private String orderNo;

    /**原支付订单号*/
    private String oriOrderNo;

    /**商户订单号*/
    private String merOrder;

    /**币种*/
    private String curr;

    /**订单状态*/
    private String orderStat;

    /**订单类型*/
    private String transType;

    /**一级商户手续费*/
    private BigDecimal merFeeAmt;

    /**二级商户手续费*/
    private BigDecimal secMerFeeAmt;

    /**列表*/
    private List<Map<String, Object>> reconciliationDocDownloadDtoList;

    
    
    public String getExtensionParty() {
		return extensionParty;
	}

	public void setExtensionParty(String extensionParty) {
		this.extensionParty = extensionParty;
	}

	public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<Map<String, Object>> getReconciliationDocDownloadDtoList() {
        return reconciliationDocDownloadDtoList;
    }

    public void setReconciliationDocDownloadDtoList(List<Map<String, Object>> reconciliationDocDownloadDtoList) {
        this.reconciliationDocDownloadDtoList = reconciliationDocDownloadDtoList;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public BigDecimal getTotTransAmt() {
        return totTransAmt;
    }

    public void setTotTransAmt(BigDecimal totTransAmt) {
        this.totTransAmt = totTransAmt;
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    public String getMerOrder() {
        return merOrder;
    }

    public void setMerOrder(String merOrder) {
        this.merOrder = merOrder;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public BigDecimal getMerFeeAmt() {
        return merFeeAmt;
    }

    public void setMerFeeAmt(BigDecimal merFeeAmt) {
        this.merFeeAmt = merFeeAmt;
    }

    public BigDecimal getSecMerFeeAmt() {
        return secMerFeeAmt;
    }

    public void setSecMerFeeAmt(BigDecimal secMerFeeAmt) {
        this.secMerFeeAmt = secMerFeeAmt;
    }
}
