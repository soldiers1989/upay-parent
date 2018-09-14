package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseQueryDto;


/**
 * 获取渠道节点
 * 
 * @author xuxin
 * 
 */
public class AccOrderListQuerySubDto extends BaseQueryDto {
    private String transType;
    /*
     * 账户类型
     */
    private String orderNo;
    /*
     * 账户类型
     */
    private String orderStat;
    public String getOrderStat() {
        return orderStat;
    }
    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }
    /*
     * 订单号
     */
    private String orderName;
    public String getOrderName() {
        return orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    /*
     * 订单号
     */
    private String orderDate;
    
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    /*
     * 订单创建日期
     */
    private Date queryBegin;
    /*
     * 查询起始日期
     */
    private Date queryEnd;
    /*
     * 查询终止日期
     */
    private Date transTime;
    /*
     * 交易时间
     */
    private String transStat;
    /*
     * 交易状态
     */
    private String transSeq;
    /*
     * 交易流水号
     */
    private BigDecimal transAmt;
    /*
     * 交易金额
     */
    private String dcFlag;
    /*
     * 借贷标识
     */
    private String eCardNo;
    /*
     * 账号
     */
    private String relAcctType;
    /*
     * 对方账户类型
     */
    private String relAcct;
    /*
     * 对方账号
     */
    private BigDecimal acctBal;
    /*
     * 账户余额
     */
    private String memoMsg;
    /*
     * 摘要信息
     */
    private String memoCode;
    /*
     * 摘要码
     */


    /**
     * 商户手续费 应收商户手续费       db_column: MER_FEE_AMT
     */
    private java.math.BigDecimal merFeeAmt;
    /**
     * 客户手续费 应收客户手续费       db_column: FEE_AMT
     */
    private java.math.BigDecimal feeAmt;

    public BigDecimal getMerFeeAmt() {
        return merFeeAmt;
    }

    public void setMerFeeAmt(BigDecimal merFeeAmt) {
        this.merFeeAmt = merFeeAmt;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public Date getQueryBegin() {
        return queryBegin;
    }
    public String getTransType() {
        return transType;
    }
    public void setTransType(String transType) {
        this.transType = transType;
    }
    public void setQueryBegin(Date queryBegin) {
        this.queryBegin = queryBegin;
    }
    public Date getQueryEnd() {
        return queryEnd;
    }
    public void setQueryEnd(Date queryEnd) {
        this.queryEnd = queryEnd;
    }
    
    
    public Date getTransTime() {
        return transTime;
    }
    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }
    public String getTransStat() {
        return transStat;
    }
    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }
    public String getTransSeq() {
        return transSeq;
    }
    public void setTransSeq(String transSeq) {
        this.transSeq = transSeq;
    }
    public BigDecimal getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
    public String getDcFlag() {
        return dcFlag;
    }
    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }
    
    public String geteCardNo() {
        return eCardNo;
    }
    public void seteCardNo(String eCardNo) {
        this.eCardNo = eCardNo;
    }
    public String getRelAcctType() {
        return relAcctType;
    }
    public void setRelAcctType(String relAcctType) {
        this.relAcctType = relAcctType;
    }
    public String getRelAcct() {
        return relAcct;
    }
    public void setRelAcct(String relAcct) {
        this.relAcct = relAcct;
    }
    public BigDecimal getAcctBal() {
        return acctBal;
    }
    public void setAcctBal(BigDecimal acctBal) {
        this.acctBal = acctBal;
    }
    public String getMemoMsg() {
        return memoMsg;
    }
    public void setMemoMsg(String memoMsg) {
        this.memoMsg = memoMsg;
    }
    public String getMemoCode() {
        return memoCode;
    }
    public void setMemoCode(String memoCode) {
        this.memoCode = memoCode;
    }
    
    /*
     *商户号 
     */
    private String merNo;
    
    /*
     * 商户名称
     */
    private String merName;
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
    
    
    
}
