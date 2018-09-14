package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class InitReqHostDto extends BaseDto{
	/**  商户号 */
	private String merNo;
	/**  内部账户 */
	private String stlAcctNo;
	/**  查询日期 */
	private String queryDate;
	/**  查询时间 */
	private String queryTime;
	/**  查询流水号*/
	private String querySeq;
	/**  交易日期*/
	private String tranDate;
	/**  结算帐户类型*/
	private String stlAcctType;
	
	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}


	public String getStlAcctNo() {
		return stlAcctNo;
	}

	public void setStlAcctNo(String stlAcctNo) {
		this.stlAcctNo = stlAcctNo;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}


	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public String getQuerySeq() {
		return querySeq;
	}

	public void setQuerySeq(String querySeq) {
		this.querySeq = querySeq;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getStlAcctType() {
		return stlAcctType;
	}

	public void setStlAcctType(String stlAcctType) {
		this.stlAcctType = stlAcctType;
	}

	
}
