package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款订单检查DTO
 * @author zhangjianfeng
 * @since 2016/8/16
 */
public class RefundOrderDto extends BaseDto {

    //=========请求交易参数============
    private BigDecimal transAmt;
    private String merDcFlag;
    private String merAddOrSub;
    private BigDecimal refundMerStlAmt;
    private String refundMerStlAmtFlag;
    private String refundMerStlAmtF;
    private String coreTransAccNo;
    private String coreStlAccNo;
    
    private String merStlAccNo;
    private String merStlAccType;
    
    /** 退款平台订单号 */
    private String platOrderNo;
    /** 原订单的订单名称 */
    private String oriOrderName;
    /** 累计退款金额 */
    private BigDecimal ejectAmt;
    /** 退款额度标识  */
    private String refundAmtFlag;
    /** 重复退款标识 */
    private String refundRepeatFlag;
    /** 微信商户识别码 */
    private String subMchId;
    /** 退款金额：分 */
    private String totalFeeRefund;
    /** 原订单金额：分 */
    private String oriTotalFee;
    /** 商户的虚拟账户号 */
    private String merAccNo;
    /** 商户号 */
    private String merNo;
    /** 二级商户号 */
    private String secMerNo;
    /** 商户退款流水号 */
    private String merSeq;
    /** 商户支付订单号 */
    private String outerOrderNo;
    /** 商户退款订单号 */
    private String outerRefundNo;
    /** 支付平台支付订单号 */
    private String orderNo;
    /** 退款金额，*/
    private String refundAmt;
    /** 操作员密码 */
    private String opUserPwd;
    /** 接收人账号 */
    private String recvUserAcctNo;
    /** 接收退款的姓名(需与接收退款的平台帐号绑定的姓名一致) */
    private String recvUsername;
    /** 是否退还商户手续费 Y-退；N-不退 */
    private String isRefundFee;
    /** 异步通知路径 */
    private String notifyUrl;
    /** 商户平台发起退款交易的日期 格式：yyyyMMdd */
    private String merDate;
    /** 商户平台发起退款交易的时间 格式：HHmmss */
    private String merTime;
    /** 退款机器IP */
    private String spbillCreateIp;
    /** 退款描述 */
    private String refundDes;
    /** 商户平台用户ID */
    private String merUserId;

    //=========原订单交易参数============
    /** 原订单的手续费 */
    private BigDecimal oriFeeAmt;
    /** 原订单支付方式 */
    private String oriPayType;
    /** 原订单清算标志 */
    private String oriStlFlag;
    /** 原订单交易金额 */
    private BigDecimal oriOrderTransAmt;
    /** 原订单已退款金额，当存在多次退款时存在 */
    private BigDecimal hadRefundAmt;
    /** 原订单交易成功日期 */
    private Date oriTransDate;
    /** 原订单交易币种 */
    private String oriCcy;
    /** 支付平台退款订单号 */
    private String oriOrderNo;
    /** 原订单用户ID */
    private String oriUserId;
    /** 退款渠道，响应报文中返回 */
    private String refundChnl;
    /** 是否首次退款 0-非首次退款；1-首次退款； */
    private String isFirstRefund;
    /** 是否重新提交退款 0-非重新提交；1-重新提交； */
    private String isResubmitRefund;
    /** 交易类型 */
    private String transType;
    /** 交易金额，退款金额的BigDecimal类型，单位：元 */
    private BigDecimal userTransAmt;
    /** 交易金额，退款金额的BigDecimal类型，单位：元 */
    private BigDecimal oriTransAmt;
    /** 是否隔日退货：N-否；Y-是； */
    private String isNextDayRefund;

    /** 原交易资金通道 */
    private String routeCode;
    /** 原交易资金通道 */
    private String oriRouteCode;
    
    private String clrTypeZJ;
	private String clrTypeHost;
	private String clrTypeWechat;
	private String clrTypeUnion;
	private String clrTypeAlipay;

	private String unionOrderTime;
	
	private String reqType;
	
	private String orgRouteCode;
	
	private String promoterDeptNo;
	
	
	
	
	public String getPromoterDeptNo() {
		return promoterDeptNo;
	}

	public void setPromoterDeptNo(String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}

	public String getOrgRouteCode() {
		return orgRouteCode;
	}

	public void setOrgRouteCode(String orgRouteCode) {
		this.orgRouteCode = orgRouteCode;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getUnionOrderTime() {
		return unionOrderTime;
	}

	public void setUnionOrderTime(String unionOrderTime) {
		this.unionOrderTime = unionOrderTime;
	}

    public String getOriRouteCode() {
        return oriRouteCode;
    }

    public void setOriRouteCode(String oriRouteCode) {
        this.oriRouteCode = oriRouteCode;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getMerAddOrSub() {
        return merAddOrSub;
    }

    public void setMerAddOrSub(String merAddOrSub) {
        this.merAddOrSub = merAddOrSub;
    }

    public String getRefundMerStlAmtFlag() {
        return refundMerStlAmtFlag;
    }

    public void setRefundMerStlAmtFlag(String refundMerStlAmtFlag) {
        this.refundMerStlAmtFlag = refundMerStlAmtFlag;
    }

    public BigDecimal getRefundMerStlAmt() {
        return refundMerStlAmt;
    }

    public void setRefundMerStlAmt(BigDecimal refundMerStlAmt) {
        this.refundMerStlAmt = refundMerStlAmt;
    }

    public String getRefundMerStlAmtF() {
        return refundMerStlAmtF;
    }

    public void setRefundMerStlAmtF(String refundMerStlAmtF) {
        this.refundMerStlAmtF = refundMerStlAmtF;
    }

    public String getCoreTransAccNo() {
        return coreTransAccNo;
    }

    public void setCoreTransAccNo(String coreTransAccNo) {
        this.coreTransAccNo = coreTransAccNo;
    }

    public String getCoreStlAccNo() {
        return coreStlAccNo;
    }

    public void setCoreStlAccNo(String coreStlAccNo) {
        this.coreStlAccNo = coreStlAccNo;
    }

    public String getMerStlAccNo() {
        return merStlAccNo;
    }

    public void setMerStlAccNo(String merStlAccNo) {
        this.merStlAccNo = merStlAccNo;
    }

    public String getMerStlAccType() {
        return merStlAccType;
    }

    public void setMerStlAccType(String merStlAccType) {
        this.merStlAccType = merStlAccType;
    }

    public BigDecimal getOriTransAmt() {
        return oriTransAmt;
    }

    public void setOriTransAmt(BigDecimal oriTransAmt) {
        this.oriTransAmt = oriTransAmt;
    }

    public String getPlatOrderNo() {
        return platOrderNo;
    }

    public void setPlatOrderNo(String platOrderNo) {
        this.platOrderNo = platOrderNo;
    }

    public String getOriOrderName() {
        return oriOrderName;
    }

    public void setOriOrderName(String oriOrderName) {
        this.oriOrderName = oriOrderName;
    }

    public BigDecimal getEjectAmt() {
        return ejectAmt;
    }

    public void setEjectAmt(BigDecimal ejectAmt) {
        this.ejectAmt = ejectAmt;
    }

    public String getRefundAmtFlag() {
        return refundAmtFlag;
    }

    public void setRefundAmtFlag(String refundAmtFlag) {
        this.refundAmtFlag = refundAmtFlag;
    }

    public String getRefundRepeatFlag() {
        return refundRepeatFlag;
    }

    public void setRefundRepeatFlag(String refundRepeatFlag) {
        this.refundRepeatFlag = refundRepeatFlag;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getOriTotalFee() {
        return oriTotalFee;
    }

    public void setOriTotalFee(String oriTotalFee) {
        this.oriTotalFee = oriTotalFee;
    }

    
    public String getTotalFeeRefund() {
        return totalFeeRefund;
    }

    public void setTotalFeeRefund(String totalFeeRefund) {
        this.totalFeeRefund = totalFeeRefund;
    }

    public BigDecimal getOriFeeAmt() {
        return oriFeeAmt;
    }

    public void setOriFeeAmt(BigDecimal oriFeeAmt) {
        this.oriFeeAmt = oriFeeAmt;
    }

    public String getMerAccNo() {
        return merAccNo;
    }

    public void setMerAccNo(String merAccNo) {
        this.merAccNo = merAccNo;
    }

    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
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

    public String getMerSeq() {
        return merSeq;
    }

    public void setMerSeq(String merSeq) {
        this.merSeq = merSeq;
    }
    

    public String getOuterOrderNo() {
        return outerOrderNo;
    }

    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }

    public String getOuterRefundNo() {
        return outerRefundNo;
    }

    public void setOuterRefundNo(String outerRefundNo) {
        this.outerRefundNo = outerRefundNo;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }



    public String getOpUserPwd() {
        return opUserPwd;
    }

    public void setOpUserPwd(String opUserPwd) {
        this.opUserPwd = opUserPwd;
    }

    public String getRecvUserAcctNo() {
        return recvUserAcctNo;
    }

    public void setRecvUserAcctNo(String recvUserAcctNo) {
        this.recvUserAcctNo = recvUserAcctNo;
    }

    public String getRecvUsername() {
        return recvUsername;
    }

    public void setRecvUsername(String recvUsername) {
        this.recvUsername = recvUsername;
    }

    public String getIsRefundFee() {
        return isRefundFee;
    }

    public void setIsRefundFee(String isRefundFee) {
        this.isRefundFee = isRefundFee;
    }

    
    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOriPayType() {
        return oriPayType;
    }

    public void setOriPayType(String oriPayType) {
        this.oriPayType = oriPayType;
    }

    public String getMerDate() {
        return merDate;
    }

    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }

    public String getMerTime() {
        return merTime;
    }

    public void setMerTime(String merTime) {
        this.merTime = merTime;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getRefundDes() {
        return refundDes;
    }

    public void setRefundDes(String refundDes) {
        this.refundDes = refundDes;
    }

    public BigDecimal getOriOrderTransAmt() {
        return oriOrderTransAmt;
    }

    public void setOriOrderTransAmt(BigDecimal oriOrderTransAmt) {
        this.oriOrderTransAmt = oriOrderTransAmt;
    }

    public BigDecimal getHadRefundAmt() {
        return hadRefundAmt;
    }

    public void setHadRefundAmt(BigDecimal hadRefundAmt) {
        this.hadRefundAmt = hadRefundAmt;
    }

    public Date getOriTransDate() {
        return oriTransDate;
    }

    public void setOriTransDate(Date oriTransDate) {
        this.oriTransDate = oriTransDate;
    }

    public String getOriCcy() {
        return oriCcy;
    }

    public void setOriCcy(String oriCcy) {
        this.oriCcy = oriCcy;
    }

    public String getOriStlFlag() {
        return oriStlFlag;
    }

    public void setOriStlFlag(String oriStlFlag) {
        this.oriStlFlag = oriStlFlag;
    }


    public String getOriUserId() {
        return oriUserId;
    }

    public void setOriUserId(String oriUserId) {
        this.oriUserId = oriUserId;
    }

    public String getRefundChnl() {
        return refundChnl;
    }

    public void setRefundChnl(String refundChnl) {
        this.refundChnl = refundChnl;
    }

    public String getIsFirstRefund() {
        return isFirstRefund;
    }

    public void setIsFirstRefund(String isFirstRefund) {
        this.isFirstRefund = isFirstRefund;
    }

    public String getIsResubmitRefund() {
        return isResubmitRefund;
    }

    public void setIsResubmitRefund(String isResubmitRefund) {
        this.isResubmitRefund = isResubmitRefund;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public BigDecimal getUserTransAmt() {
        return userTransAmt;
    }

    public void setUserTransAmt(BigDecimal userTransAmt) {
        this.userTransAmt = userTransAmt;
    }

    public String getMerUserId() {
        return merUserId;
    }

    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId;
    }

    public String getIsNextDayRefund() {
        return isNextDayRefund;
    }

    public void setIsNextDayRefund(String isNextDayRefund) {
        this.isNextDayRefund = isNextDayRefund;
    }

    public String getMerDcFlag() {
        return merDcFlag;
    }

    public void setMerDcFlag(String merDcFlag) {
        this.merDcFlag = merDcFlag;
    }

	public String getClrTypeZJ() {
		return clrTypeZJ;
	}

	public void setClrTypeZJ(String clrTypeZJ) {
		this.clrTypeZJ = clrTypeZJ;
	}

	public String getClrTypeHost() {
		return clrTypeHost;
	}

	public void setClrTypeHost(String clrTypeHost) {
		this.clrTypeHost = clrTypeHost;
	}

	public String getClrTypeWechat() {
		return clrTypeWechat;
	}

	public void setClrTypeWechat(String clrTypeWechat) {
		this.clrTypeWechat = clrTypeWechat;
	}

	public String getClrTypeUnion() {
		return clrTypeUnion;
	}

	public void setClrTypeUnion(String clrTypeUnion) {
		this.clrTypeUnion = clrTypeUnion;
	}

	public String getClrTypeAlipay() {
		return clrTypeAlipay;
	}

	public void setClrTypeAlipay(String clrTypeAlipay) {
		this.clrTypeAlipay = clrTypeAlipay;
	}
    
    
}
