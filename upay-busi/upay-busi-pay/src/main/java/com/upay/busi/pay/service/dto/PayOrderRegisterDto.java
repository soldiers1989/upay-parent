package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * 订单详情登记
 * 
 * @author lihe
 * 
 */
public class PayOrderRegisterDto extends BaseDto {
	private String orderNo;// 订单号
	private String chnlId; // 渠道代码
	private String payServicType;// 支付服务类型
	private String merDate;// 商户日期
	private String merSeq;// 商户流水号
	private String merNo;// 商户代码
	private String secMerNo;// 二级商户代码
	private String outerOrderNo;// 商户订单号
	private String OuterOrderStartDate;// 商户订单生成日期
	private String outerOrderEndDate;// 商户订单失效日期
	private String orderType; // 订单类型
	private String payType; // 支付方式
	private String orderName;// 支付订单名称
	private String userId;// 用户ID
	private String orderDate;// 订单创建日期
	private String orderTime;// 订单创建时间
	private Integer orderLmtTime;// 订单时效
	private String curr;// 币种
	private BigDecimal transAmt;// 交易金额
	private BigDecimal otherTranAmt;// 其他费用
	private BigDecimal productAmt;// 商品费用
	private BigDecimal merFeeAmt;// 商户手续费
	private BigDecimal feeAmt;// 客户手续费
	private String oriDate;// 原交易日期
	private String oriOrderNo;// 原支付订单号
	private BigDecimal ejectAmt;// 累计退货额
	private String orderStat;// 订单状态
	private String spbillCreateIp;// 用户IP
	private String notifyUrl;// 异步通知路径
	private String returnUrl;// 同步通知路径
	private String lastUpdateTime;// 最后变更时间
	private String transComments;//
	private String transType;//交易类型
	private String orderDes;//订单说明
	
	private String transferPayerOrderNo;//余额转账付款方订单号
	private String isRegPayerOrderNo;//0：不登记    1:登记
	private String getType;//手续费收起方式：0内扣，1外扣


/**
 * 以下几个字段  主要
 * 用于  补单时
 * 第三方流水成功      核心流水失败 。（根本核心流水没有入库 导致无法补单的情况）
 *
 */

	/**
	 * 付款账号类型见附录4.3,ACCT_TYPE       db_column: PAYER_ACCT_TYPE
	 */
	private String payerAcctType;
	/**
	 * 付款账号       db_column: PAYER_ACCT_NO
	 */
	private String payerAcctNo;
	/**
	 * 收款账号类型见附录4.3,acct_type       db_column: PAYEE_ACCT_TYPE
	 */
	private String payeeAcctType;
	/**
	 * 收款账号       db_column: PAYEE_ACCT_NO
	 */
	private String payeeAcctNo;
	
	private String promoterDeptNo;//商户所属部门
	
	
	
	public String getPromoterDeptNo() {
		return promoterDeptNo;
	}

	public void setPromoterDeptNo(String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}
	//columns END


	public String getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(String payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerAcctNo() {
		return payerAcctNo;
	}

	public void setPayerAcctNo(String payerAcctNo) {
		this.payerAcctNo = payerAcctNo;
	}

	public String getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(String payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayeeAcctNo() {
		return payeeAcctNo;
	}

	public void setPayeeAcctNo(String payeeAcctNo) {
		this.payeeAcctNo = payeeAcctNo;
	}

	public String getGetType() {
		return getType;
	}

	public void setGetType(String getType) {
		this.getType = getType;
	}

	public String getIsRegPayerOrderNo() {
		return isRegPayerOrderNo;
	}

	public void setIsRegPayerOrderNo(String isRegPayerOrderNo) {
		this.isRegPayerOrderNo = isRegPayerOrderNo;
	}

	public String getTransferPayerOrderNo() {
		return transferPayerOrderNo;
	}

	public void setTransferPayerOrderNo(String transferPayerOrderNo) {
		this.transferPayerOrderNo = transferPayerOrderNo;
	}

	public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    /** 商户名称 */
    private String primaryMerName;
	
	

    public String getPrimaryMerName() {
        return primaryMerName;
    }

    public void setPrimaryMerName(String primaryMerName) {
        this.primaryMerName = primaryMerName;
    }

    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransComments() {
		return transComments;
	}

	public void setTransComments(String transComments) {
		this.transComments = transComments;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getEjectAmt() {
		return ejectAmt;
	}

	public void setEjectAmt(BigDecimal ejectAmt) {
		this.ejectAmt = ejectAmt;
	}

	public String getChnlId() {
		return chnlId;
	}

	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}


	public String getMerSeq() {
		return merSeq;
	}

	public void setMerSeq(String merSeq) {
		this.merSeq = merSeq;
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

	public String getOuterOrderNo() {
		return outerOrderNo;
	}

	public void setOuterOrderNo(String outerOrderNo) {
		this.outerOrderNo = outerOrderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	

	public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOrderLmtTime() {
		return orderLmtTime;
	}

	public void setOrderLmtTime(Integer orderLmtTime) {
		this.orderLmtTime = orderLmtTime;
	}

	public String getCurr() {
		if (null == curr)
			curr = "CNY";
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public BigDecimal getOtherTranAmt() {
		return otherTranAmt;
	}

	public void setOtherTranAmt(BigDecimal otherTranAmt) {
		this.otherTranAmt = otherTranAmt;
	}

	public BigDecimal getProductAmt() {
		return productAmt;
	}

	public void setProductAmt(BigDecimal productAmt) {
		this.productAmt = productAmt;
	}

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

	public String getOriOrderNo() {
		return oriOrderNo;
	}

	public void setOriOrderNo(String oriOrderNo) {
		this.oriOrderNo = oriOrderNo;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public String getOuterOrderStartDate() {
		return OuterOrderStartDate;
	}

	public void setOuterOrderStartDate(String outerOrderStartDate) {
		OuterOrderStartDate = outerOrderStartDate;
	}

	public String getOuterOrderEndDate() {
		return outerOrderEndDate;
	}

	public void setOuterOrderEndDate(String outerOrderEndDate) {
		this.outerOrderEndDate = outerOrderEndDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOriDate() {
		return oriDate;
	}

	public void setOriDate(String oriDate) {
		this.oriDate = oriDate;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
