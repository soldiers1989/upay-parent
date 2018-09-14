package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月6日 下午2:14:11
 */
public class RecordPayFlowListDto extends BaseDto {
    private String orderNo;//订单号
    private String merNo;//一级商户号
    private String secMerNo;//二级商户号
    private String routeCode;//渠道号
    private String accType;//账户类型
    private String accNo;//账号
    private String bankAccNo;//卡号
    private BigDecimal transAmt;//支付金额
    private BigDecimal feeAmt;//手续费
    private String orderDes;//订单描述
    private String times;//第几次记录支付流水
    private String payType;//支付类型
    private String isFeeAmt;//是否是手续费流水
    private String getType;//手续费收起方式：0内扣，1外扣
    private String isPayee;//转账收款方
    private String srFlag;//往来标识
    
    
    private String bindAccType;//绑定账户类型
    private String bindBankFlag;//绑定账户银行类别
    private String bindBankCode;//绑定账户行号
    private String bindBankName;//绑定账户行名
    private String bindAccNo;//绑定账户账号
    private String bindOpenCode;//绑定账户开户机构号
    private String bindOpenName;//绑定账户开户行名
    
    private String transSubSeq;//交易流水
    private String flowRouteCode;//资金通道
    
    private String payerAccNo;//付款账号
    private String payerName;//付款户名
    private String payerOrgName;//付款账户机构名
    private String payerBankNo;//付款账户行号
    private String payerBankName;//付款账户行名
    private String payerUserId;//付款用户id
    private String payerAccType;//付款账户类型 
    
    private String payeeAccNo;//收款账号
    private String payeeAccType;//收款账户类型
    private String payeeName;//收款账户名
    private String payeeOrgName;//收款账户机构名
    private String payeeBankNo;//收款账户行号
    private String payeeBankName;//收款账户行号
    private String routeSeq;//通道方流水号
    private String isRegPayerOrderNo;//0：不登记    1:登记
    private java.lang.String promoterDeptNo;//订单所属于银行哪个机构
    
    
	public java.lang.String getPromoterDeptNo() {
		return promoterDeptNo;
	}
	public void setPromoterDeptNo(java.lang.String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}
	
	
	public String getIsPayee() {
		return isPayee;
	}

	public void setIsPayee(String isPayee) {
		this.isPayee = isPayee;
	}

	public String getGetType() {
		return getType;
	}

	public void setGetType(String getType) {
		this.getType = getType;
	}

	public String getIsFeeAmt() {
		return isFeeAmt;
	}

	public void setIsFeeAmt(String isFeeAmt) {
		this.isFeeAmt = isFeeAmt;
	}

	public String getIsRegPayerOrderNo() {
		return isRegPayerOrderNo;
	}

	public void setIsRegPayerOrderNo(String isRegPayerOrderNo) {
		this.isRegPayerOrderNo = isRegPayerOrderNo;
	}
    public String getRouteSeq() {
        return routeSeq;
    }

    public void setRouteSeq(String routeSeq) {
        this.routeSeq = routeSeq;
    }

    public String getPayerAccType() {
        return payerAccType;
    }

    public void setPayerAccType(String payerAccType) {
        this.payerAccType = payerAccType;
    }

    

    public String getPayeeAccType() {
        return payeeAccType;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public String getPayeeOrgName() {
        return payeeOrgName;
    }

    public String getPayeeBankNo() {
        return payeeBankNo;
    }

    public String getPayeeBankName() {
        return payeeBankName;
    }

    public void setPayeeAccType(String payeeAccType) {
        this.payeeAccType = payeeAccType;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public void setPayeeOrgName(String payeeOrgName) {
        this.payeeOrgName = payeeOrgName;
    }

    public void setPayeeBankNo(String payeeBankNo) {
        this.payeeBankNo = payeeBankNo;
    }

    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }

    public String getPayerUserId() {
        return payerUserId;
    }

    public void setPayerUserId(String payerUserId) {
        this.payerUserId = payerUserId;
    }

    public String getPayerOrgName() {
        return payerOrgName;
    }

    public String getPayerBankNo() {
        return payerBankNo;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerOrgName(String payerOrgName) {
        this.payerOrgName = payerOrgName;
    }

    public void setPayerBankNo(String payerBankNo) {
        this.payerBankNo = payerBankNo;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getFlowRouteCode() {
        return flowRouteCode;
    }

    public void setFlowRouteCode(String flowRouteCode) {
        this.flowRouteCode = flowRouteCode;
    }

    public String getPayerAccNo() {
        return payerAccNo;
    }

    public String getPayeeAccNo() {
        return payeeAccNo;
    }

    public void setPayerAccNo(String payerAccNo) {
        this.payerAccNo = payerAccNo;
    }

    public void setPayeeAccNo(String payeeAccNo) {
        this.payeeAccNo = payeeAccNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

    public String getBindAccType() {
        return bindAccType;
    }

    public String getBindBankFlag() {
        return bindBankFlag;
    }

    public String getBindBankCode() {
        return bindBankCode;
    }

    public String getBindBankName() {
        return bindBankName;
    }

    public String getBindAccNo() {
        return bindAccNo;
    }

    public String getBindOpenCode() {
        return bindOpenCode;
    }

    public String getBindOpenName() {
        return bindOpenName;
    }

    public void setBindAccType(String bindAccType) {
        this.bindAccType = bindAccType;
    }

    public void setBindBankFlag(String bindBankFlag) {
        this.bindBankFlag = bindBankFlag;
    }

    public void setBindBankCode(String bindBankCode) {
        this.bindBankCode = bindBankCode;
    }

    public void setBindBankName(String bindBankName) {
        this.bindBankName = bindBankName;
    }

    public void setBindAccNo(String bindAccNo) {
        this.bindAccNo = bindAccNo;
    }

    public void setBindOpenCode(String bindOpenCode) {
        this.bindOpenCode = bindOpenCode;
    }

    public void setBindOpenName(String bindOpenName) {
        this.bindOpenName = bindOpenName;
    }

    public String getSrFlag() {
        return srFlag;
    }

    public void setSrFlag(String srFlag) {
        this.srFlag = srFlag;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getAccType() {
        return accType;
    }

    public String getAccNo() {
        return accNo;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    
    

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
