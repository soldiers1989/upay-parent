/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;

/**
 * 发红包登记
 * 
 * @author zhanggr
 * 
 */
public class RedpackRegisterDto extends BaseDto {
	private String outerOrderNo;
	private String merNo;
	private String msgappid;
	private String sendName;
	private String reOpenid;
	private String totalFee;//分
	private BigDecimal transAmt;//交易金额
	private String totalNo;
	private String amtType;
	private String wishing;
	private String clientIp;
	private String actName;
	private String sceneId;
	private String riskInfo;
	private String consumeMchId;
	private String sendListid;
	private String transSubSeq;
	private String orderState;
	private String redpackType;
	private Date transDate;
	private Date transTime;
	private String remark;
	private String remark1;
	private String remark2;
	private String orgNo;// 机构号、商户号

	public String getOuterOrderNo() {
		return outerOrderNo;
	}

	public void setOuterOrderNo(String outerOrderNo) {
		this.outerOrderNo = outerOrderNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getMsgappid() {
		return msgappid;
	}

	public void setMsgappid(String msgappid) {
		this.msgappid = msgappid;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getReOpenid() {
		return reOpenid;
	}

	public void setReOpenid(String reOpenid) {
		this.reOpenid = reOpenid;
	}

	

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}

	public String getAmtType() {
		return amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public String getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}

	public String getConsumeMchId() {
		return consumeMchId;
	}

	public void setConsumeMchId(String consumeMchId) {
		this.consumeMchId = consumeMchId;
	}

	public String getSendListid() {
		return sendListid;
	}

	public void setSendListid(String sendListid) {
		this.sendListid = sendListid;
	}

	

	public String getTransSubSeq() {
		return transSubSeq;
	}

	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getRedpackType() {
		return redpackType;
	}

	public void setRedpackType(String redpackType) {
		this.redpackType = redpackType;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	@Override
	public String toString() {
		return "RedpackRegisterDto [outerOrderNo=" + outerOrderNo + ", merNo="
				+ merNo + ", msgappid=" + msgappid + ", sendName=" + sendName
				+ ", reOpenid=" + reOpenid + ", totalAmount=" + transAmt
				+ ", totalNo=" + totalNo + ", amtType=" + amtType
				+ ", wishing=" + wishing + ", clientIp=" + clientIp
				+ ", actName=" + actName + ", sceneId=" + sceneId
				+ ", riskInfo=" + riskInfo + ", consumeMchId=" + consumeMchId
				+ ", sendListid=" + sendListid + ", orderNo=" + transSubSeq
				+ ", orderState=" + orderState + ", redpackType=" + redpackType
				+ ", transDate=" + transDate + ", transTime=" + transTime
				+ ", remark=" + remark + ", remark1=" + remark1 + ", remark2="
				+ remark2 + "]";
	}

}
