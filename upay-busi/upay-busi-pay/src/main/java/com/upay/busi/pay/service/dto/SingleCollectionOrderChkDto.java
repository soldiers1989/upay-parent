package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 单笔代收交易参数
 * @author zhangjianfeng
 * @since 2016/11/24 08:43
 */
public class SingleCollectionOrderChkDto extends BaseDto {
	private String orderName;
	private String payeeAccountNo;//收款人账号
	private String payeeAccountType;//收款人账号类型
	private String payeeAccountName;//收款人账号名称
	private String payeeCertNo;//收款人身份证号
	private String payeeMobile;//收款人手机号码
	
	private BigDecimal transAmt;
    /** 商户号 */
    private String merNo;

    /** 商户订单号 */
    private String outerOrderNo;

    /** 商户时间 */
    private String merDate;

    /** 银行标识 */
    private String bankNo;
    /** 银行编号 */
    private String bankId;
    /** 银行编号 */
    private String logoId;

    /** 代扣银行卡号 */
    private String acctNo;

    /** 代扣银行卡号账户名称 */
    private String acctName;

    /** 代扣账户类型，此账户类型为: 11-个人账户；12-企业账户；与平台账户类弄不同； */
    private String accountType;

    /** 账户类型，与平台账户类型一样 */
    private String acctType;

    /** 用户IP */
    private String spbillCreateIp;

    /** 证件类型 */
    private String certType;

    /** 证件号码 */
    private String certNo;


    /** 手机号 */
    private String mobile;

    /** 代收类型 1-内部账交易；2-非内部账交易 */
    private String collectionType;

    /** 内部账交易 收款方账号 */
    private String receiveAcctNo;

    /** 内部账交易 收款方户名 */
    private String receiveAcctName;

    /** 交易备注 */
    private String transComments;

    /** 短信验证码 */
    private String smsCode;

    /** 银行卡有效期，格式：yyMM */
    private String valiDate;
 
    /** CVN */
    private String cvn2;

    /** 接收支付成功通知的URL */
    private String notifyUrl;

    /** 同步通知路径 处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径 */
    private String returnUrl;

    /** 本他行标志 */
    private String bankBinFlag;

    /** 交易类型 */
    private String transType;

    /** 支付服务类型 */
    private String payServicType;

    /** 支付类型 */
    private String payType;

    /** 交易币种 订单用 */
    private String curr;

    /** 平台支付订单号 */
    private String orderNo;
    
    /** 平台支付订单状态 */
    private String orderStat;

    /** 订单类型 */
    private String orderType;

    /** 代收标志：01-收银台发起的代收交易； 02-商户发起的代收交易；*/
    private String singleCollectionFlag;

    /** 支付账号 */
    private String payAcctNo;

    /** 来往账标志 */
    private String srFlag;

    private String isEsbCore;

    public String getIsEsbCore() {
        return isEsbCore;
    }
    public void setIsEsbCore(String isEsbCore) {
        this.isEsbCore = isEsbCore;
    }

    /**
     * 卡类型 1、借记卡 2、贷记卡       db_column: CARD_BIN_TYPE 
     */ 	
	private java.lang.String cardBinType;
	
	 private String routeCode;

	 
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getPayeeAccountType() {
		return payeeAccountType;
	}

	public void setPayeeAccountType(String payeeAccountType) {
		this.payeeAccountType = payeeAccountType;
	}

	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}

	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public String getPayeeCertNo() {
		return payeeCertNo;
	}

	public void setPayeeCertNo(String payeeCertNo) {
		this.payeeCertNo = payeeCertNo;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getLogoId() {
		return logoId;
	}

	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public java.lang.String getCardBinType() {
		return cardBinType;
	}

	public void setCardBinType(java.lang.String cardBinType) {
		this.cardBinType = cardBinType;
	}
    

    public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getOuterOrderNo() {
        return outerOrderNo;
    }

    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }

    public String getMerDate() {
        return merDate;
    }

    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getReceiveAcctNo() {
        return receiveAcctNo;
    }

    public void setReceiveAcctNo(String receiveAcctNo) {
        this.receiveAcctNo = receiveAcctNo;
    }

    public String getReceiveAcctName() {
        return receiveAcctName;
    }

    public void setReceiveAcctName(String receiveAcctName) {
        this.receiveAcctName = receiveAcctName;
    }

    public String getTransComments() {
        return transComments;
    }

    public void setTransComments(String transComments) {
        this.transComments = transComments;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

   
    public String getValiDate() {
		return valiDate;
	}

	public void setValiDate(String valiDate) {
		this.valiDate = valiDate;
	}

	public String getCvn2() {
        return cvn2;
    }

    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSingleCollectionFlag() {
        return singleCollectionFlag;
    }

    public void setSingleCollectionFlag(String singleCollectionFlag) {
        this.singleCollectionFlag = singleCollectionFlag;
    }

    public String getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(String payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public String getBankBinFlag() {
        return bankBinFlag;
    }

    public void setBankBinFlag(String bankBinFlag) {
        this.bankBinFlag = bankBinFlag;
    }

    public String getSrFlag() {
        return srFlag;
    }

    public void setSrFlag(String srFlag) {
        this.srFlag = srFlag;
    }

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}
    
}
