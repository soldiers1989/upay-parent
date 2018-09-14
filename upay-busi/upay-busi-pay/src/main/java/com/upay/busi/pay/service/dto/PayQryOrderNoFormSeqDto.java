/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author zhanggr
 *
 */
public class PayQryOrderNoFormSeqDto extends BaseDto {
	private String merName;//商户名+商户地址
	private String merNameResult;//商户名
	private String subMchId;//微信商户识别码
	
    private String transSubSeq;//流水号
    private String timeEnd;//交易时间
    private String outTradeNo;//交易单号
    private BigDecimal transAmt;//交易金额
    
    
    public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getMerNameResult() {
		return merNameResult;
	}
	public void setMerNameResult(String merNameResult) {
		this.merNameResult = merNameResult;
	}
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	public String getTransSubSeq() {
        return transSubSeq;
    }
    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }
   
    
    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
    
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public BigDecimal getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
    
}
