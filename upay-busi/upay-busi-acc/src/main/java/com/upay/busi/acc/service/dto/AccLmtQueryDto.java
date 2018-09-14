package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 账户限额查询
 * 
 * @author liyulong
 *
 */
public class AccLmtQueryDto extends BaseDto {

	/**
	 * 待查询的交易代码
	 */
	private String pendTransCode;

	/**
	 * 账户类型
	 */
	private String acctType;

	/**
	 * 虚拟账户
	 */
	private String vAcctNo;

	/**
	 * 用户认证等级
	 */
	private String userCertLevel;

	/**
	 * 限额类型
	 */
	private String lmtType;

	/**
	 * 单笔限额
	 */
	private BigDecimal singleAmtLimit;

	/**
	 * 日累计金额
	 */
	private BigDecimal daySumAmtLimit;

	/**
	 * 月累计金额
	 */
	private BigDecimal monSumAmtLimit;
	/**
	 * 年累计金额
	 */
	private BigDecimal yearSumAmtLimit;

	/**
	 * 日累计限额次数
	 */
	private int daySumTimesLimit;

	/**
	 * 月累计限额次数
	 */
	private int monSumTimesLimit;
	/**
	 * 年累计限额次数
	 */
	private int yearSumTimesLimit;

	/**
	 * 日累计用户剩余限额
	 */
	private BigDecimal daySumAmtRemainLmt;

	/**
	 * 月累计用户剩余限额
	 */
	private BigDecimal monSumAmtRemainLmt;

	/**
	 * 年累计用户剩余限额
	 */
	private BigDecimal yearSumAmtRemainLmt;

	/**
	 * 日累计用户剩余限额次数
	 */
	private int daySumTimesRemainLmt;

	/**
	 * 月累计用户剩余限额次数
	 */
	private int monSumTimesRemainLmt;
	/**
	 * 年累计用户剩余限额次数
	 */
	private int yearSumTimesRemainLmt;

	/**
	 * 交易时间，便于查找最新的交易
	 */
	private Date transDate;

	/**
	 * 可用余额
	 */
	private BigDecimal avlBal;

	public String getPendTransCode() {
		return pendTransCode;
	}

	public void setPendTransCode(String pendTransCode) {
		this.pendTransCode = pendTransCode;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}


	public String getvAcctNo() {
		return vAcctNo;
	}

	public void setvAcctNo(String vAcctNo) {
		this.vAcctNo = vAcctNo;
	}

	public String getUserCertLevel() {
		return userCertLevel;
	}

	public void setUserCertLevel(String userCertLevel) {
		this.userCertLevel = userCertLevel;
	}

	public String getLmtType() {
		return lmtType;
	}

	public void setLmtType(String lmtType) {
		this.lmtType = lmtType;
	}

	public BigDecimal getSingleAmtLimit() {
		return singleAmtLimit;
	}

	public void setSingleAmtLimit(BigDecimal singleAmtLimit) {
		this.singleAmtLimit = singleAmtLimit;
	}

	public BigDecimal getDaySumAmtLimit() {
		return daySumAmtLimit;
	}

	public void setDaySumAmtLimit(BigDecimal daySumAmtLimit) {
		this.daySumAmtLimit = daySumAmtLimit;
	}

	public BigDecimal getMonSumAmtLimit() {
		return monSumAmtLimit;
	}

	public void setMonSumAmtLimit(BigDecimal monSumAmtLimit) {
		this.monSumAmtLimit = monSumAmtLimit;
	}

	public int getDaySumTimesLimit() {
		return daySumTimesLimit;
	}

	public void setDaySumTimesLimit(int daySumTimesLimit) {
		this.daySumTimesLimit = daySumTimesLimit;
	}

	public int getMonSumTimesLimit() {
		return monSumTimesLimit;
	}

	public void setMonSumTimesLimit(int monSumTimesLimit) {
		this.monSumTimesLimit = monSumTimesLimit;
	}

	public BigDecimal getDaySumAmtRemainLmt() {
		return daySumAmtRemainLmt;
	}

	public void setDaySumAmtRemainLmt(BigDecimal daySumAmtRemainLmt) {
		this.daySumAmtRemainLmt = daySumAmtRemainLmt;
	}

	public BigDecimal getMonSumAmtRemainLmt() {
		return monSumAmtRemainLmt;
	}

	public void setMonSumAmtRemainLmt(BigDecimal monSumAmtRemainLmt) {
		this.monSumAmtRemainLmt = monSumAmtRemainLmt;
	}

	public int getDaySumTimesRemainLmt() {
		return daySumTimesRemainLmt;
	}

	public void setDaySumTimesRemainLmt(int daySumTimesRemainLmt) {
		this.daySumTimesRemainLmt = daySumTimesRemainLmt;
	}

	public int getMonSumTimesRemainLmt() {
		return monSumTimesRemainLmt;
	}

	public void setMonSumTimesRemainLmt(int monSumTimesRemainLmt) {
		this.monSumTimesRemainLmt = monSumTimesRemainLmt;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public BigDecimal getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(BigDecimal avlBal) {
		this.avlBal = avlBal;
	}

	public BigDecimal getYearSumAmtLimit() {
		return yearSumAmtLimit;
	}

	public void setYearSumAmtLimit(BigDecimal yearSumAmtLimit) {
		this.yearSumAmtLimit = yearSumAmtLimit;
	}

	public int getYearSumTimesLimit() {
		return yearSumTimesLimit;
	}

	public void setYearSumTimesLimit(int yearSumTimesLimit) {
		this.yearSumTimesLimit = yearSumTimesLimit;
	}

	public BigDecimal getYearSumAmtRemainLmt() {
		return yearSumAmtRemainLmt;
	}

	public void setYearSumAmtRemainLmt(BigDecimal yearSumAmtRemainLmt) {
		this.yearSumAmtRemainLmt = yearSumAmtRemainLmt;
	}

	public int getYearSumTimesRemainLmt() {
		return yearSumTimesRemainLmt;
	}

	public void setYearSumTimesRemainLmt(int yearSumTimesRemainLmt) {
		this.yearSumTimesRemainLmt = yearSumTimesRemainLmt;
	}
	

}
