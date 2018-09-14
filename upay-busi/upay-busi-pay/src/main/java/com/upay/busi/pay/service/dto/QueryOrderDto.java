/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年11月7日
 */
public class QueryOrderDto extends BaseDto {
    
    private String resCode;
    private String resMsg;

    private String refundStat;
    
    private java.lang.String chnlId;
    private java.lang.String payServicType;
    private java.lang.String merDate;
    private java.lang.String merSeq;
    private java.lang.String merNo;
    private java.lang.String secMerNo;
    private java.lang.String outerOrderNo;
    private java.lang.String outerOrderStartDate;
    private java.lang.String outerOrderEndDate;
    private java.lang.String orderType;
    private java.lang.String payType;
    private java.lang.String orderNo;
    private java.lang.String orderName;
    private java.lang.String userId;
    private java.lang.String orderDate;
    private java.lang.String orderTime;
    private java.lang.Integer orderLmtTime;
    private java.lang.String curr;
    private String transAmt;
    private String otherTranAmt;
    private String productAmt;
    private String merFeeAmt;
    private String feeAmt;
    private java.lang.String oriDate;
    private java.lang.String oriOrderNo;
    private String ejectAmt;
    private java.lang.String orderStat;
    private java.lang.String spbillCreateIp;
    private java.lang.String notifyUrl;
    private java.lang.String returnUrl;
    private java.lang.String transComments;
    private java.lang.String lastUpdateTime;
    private java.lang.String remark1;
    private java.lang.String remark2;
    private java.lang.String remark3;
    private java.lang.String chkFlag;
    private java.lang.String chkBatchNo;
    private java.lang.String stlFlag;
    private java.lang.String chkDate;
    private java.lang.String stlBatchNo;
    private java.lang.String stlDate;
    private java.lang.String orderDes;
    private java.lang.String openId;
    private java.lang.String payTime;
    private java.lang.String payAcctNo;
    private java.lang.String transType;
    
    
    
    
    public String getRefundStat() {
        return refundStat;
    }
    public void setRefundStat(String refundStat) {
        this.refundStat = refundStat;
    }
    public String getResCode() {
        return resCode;
    }
    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
    public String getResMsg() {
        return resMsg;
    }
    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
    public java.lang.String getChnlId() {
        return chnlId;
    }
    public void setChnlId(java.lang.String chnlId) {
        this.chnlId = chnlId;
    }
    public java.lang.String getPayServicType() {
        return payServicType;
    }
    public void setPayServicType(java.lang.String payServicType) {
        this.payServicType = payServicType;
    }
    public java.lang.String getMerDate() {
        return merDate;
    }
    public void setMerDate(java.lang.String merDate) {
        this.merDate = merDate;
    }
    public java.lang.String getMerSeq() {
        return merSeq;
    }
    public void setMerSeq(java.lang.String merSeq) {
        this.merSeq = merSeq;
    }
    public java.lang.String getMerNo() {
        return merNo;
    }
    public void setMerNo(java.lang.String merNo) {
        this.merNo = merNo;
    }
    public java.lang.String getSecMerNo() {
        return secMerNo;
    }
    public void setSecMerNo(java.lang.String secMerNo) {
        this.secMerNo = secMerNo;
    }
    public java.lang.String getOuterOrderNo() {
        return outerOrderNo;
    }
    public void setOuterOrderNo(java.lang.String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }
    public java.lang.String getOuterOrderStartDate() {
        return outerOrderStartDate;
    }
    public void setOuterOrderStartDate(java.lang.String outerOrderStartDate) {
        this.outerOrderStartDate = outerOrderStartDate;
    }
    public java.lang.String getOuterOrderEndDate() {
        return outerOrderEndDate;
    }
    public void setOuterOrderEndDate(java.lang.String outerOrderEndDate) {
        this.outerOrderEndDate = outerOrderEndDate;
    }
    public java.lang.String getOrderType() {
        return orderType;
    }
    public void setOrderType(java.lang.String orderType) {
        this.orderType = orderType;
    }
    public java.lang.String getPayType() {
        return payType;
    }
    public void setPayType(java.lang.String payType) {
        this.payType = payType;
    }
    public java.lang.String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(java.lang.String orderNo) {
        this.orderNo = orderNo;
    }
    public java.lang.String getOrderName() {
        return orderName;
    }
    public void setOrderName(java.lang.String orderName) {
        this.orderName = orderName;
    }
    public java.lang.String getUserId() {
        return userId;
    }
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    public java.lang.String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(java.lang.String orderDate) {
        this.orderDate = orderDate;
    }
    public java.lang.String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(java.lang.String orderTime) {
        this.orderTime = orderTime;
    }
    public java.lang.Integer getOrderLmtTime() {
        return orderLmtTime;
    }
    public void setOrderLmtTime(java.lang.Integer orderLmtTime) {
        this.orderLmtTime = orderLmtTime;
    }
    public java.lang.String getCurr() {
        return curr;
    }
    public void setCurr(java.lang.String curr) {
        this.curr = curr;
    }
    
    public java.lang.String getOriDate() {
        return oriDate;
    }
    public void setOriDate(java.lang.String oriDate) {
        this.oriDate = oriDate;
    }
    public java.lang.String getOriOrderNo() {
        return oriOrderNo;
    }
    public void setOriOrderNo(java.lang.String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }
    public java.lang.String getOrderStat() {
        return orderStat;
    }
    public void setOrderStat(java.lang.String orderStat) {
        this.orderStat = orderStat;
    }
    public java.lang.String getSpbillCreateIp() {
        return spbillCreateIp;
    }
    public void setSpbillCreateIp(java.lang.String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
    public java.lang.String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(java.lang.String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public java.lang.String getReturnUrl() {
        return returnUrl;
    }
    public void setReturnUrl(java.lang.String returnUrl) {
        this.returnUrl = returnUrl;
    }
    public java.lang.String getTransComments() {
        return transComments;
    }
    public void setTransComments(java.lang.String transComments) {
        this.transComments = transComments;
    }
    public java.lang.String getLastUpdateTime() {
        return lastUpdateTime;
    }
    public void setLastUpdateTime(java.lang.String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    public java.lang.String getRemark1() {
        return remark1;
    }
    public void setRemark1(java.lang.String remark1) {
        this.remark1 = remark1;
    }
    public java.lang.String getRemark2() {
        return remark2;
    }
    public void setRemark2(java.lang.String remark2) {
        this.remark2 = remark2;
    }
    public java.lang.String getRemark3() {
        return remark3;
    }
    public void setRemark3(java.lang.String remark3) {
        this.remark3 = remark3;
    }
    public java.lang.String getChkFlag() {
        return chkFlag;
    }
    public void setChkFlag(java.lang.String chkFlag) {
        this.chkFlag = chkFlag;
    }
    public java.lang.String getChkBatchNo() {
        return chkBatchNo;
    }
    public void setChkBatchNo(java.lang.String chkBatchNo) {
        this.chkBatchNo = chkBatchNo;
    }
    public java.lang.String getStlFlag() {
        return stlFlag;
    }
    public void setStlFlag(java.lang.String stlFlag) {
        this.stlFlag = stlFlag;
    }
    public java.lang.String getChkDate() {
        return chkDate;
    }
    public void setChkDate(java.lang.String chkDate) {
        this.chkDate = chkDate;
    }
    public java.lang.String getStlBatchNo() {
        return stlBatchNo;
    }
    public void setStlBatchNo(java.lang.String stlBatchNo) {
        this.stlBatchNo = stlBatchNo;
    }
    public java.lang.String getStlDate() {
        return stlDate;
    }
    public void setStlDate(java.lang.String stlDate) {
        this.stlDate = stlDate;
    }
    public java.lang.String getOrderDes() {
        return orderDes;
    }
    public void setOrderDes(java.lang.String orderDes) {
        this.orderDes = orderDes;
    }
    public java.lang.String getOpenId() {
        return openId;
    }
    public void setOpenId(java.lang.String openId) {
        this.openId = openId;
    }
    public java.lang.String getPayTime() {
        return payTime;
    }
    public void setPayTime(java.lang.String payTime) {
        this.payTime = payTime;
    }
    public java.lang.String getPayAcctNo() {
        return payAcctNo;
    }
    public void setPayAcctNo(java.lang.String payAcctNo) {
        this.payAcctNo = payAcctNo;
    }
    public java.lang.String getTransType() {
        return transType;
    }
    public void setTransType(java.lang.String transType) {
        this.transType = transType;
    }
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getOtherTranAmt() {
		return otherTranAmt;
	}
	public void setOtherTranAmt(String otherTranAmt) {
		this.otherTranAmt = otherTranAmt;
	}
	public String getProductAmt() {
		return productAmt;
	}
	public void setProductAmt(String productAmt) {
		this.productAmt = productAmt;
	}
	public String getMerFeeAmt() {
		return merFeeAmt;
	}
	public void setMerFeeAmt(String merFeeAmt) {
		this.merFeeAmt = merFeeAmt;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getEjectAmt() {
		return ejectAmt;
	}
	public void setEjectAmt(String ejectAmt) {
		this.ejectAmt = ejectAmt;
	}
    
    
}
