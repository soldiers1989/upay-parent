package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 单笔代付交易参数
 * @author zhangjianfeng
 * @since 2016/11/28 00:30
 */
public class SinglePaymentOrderChkDto extends BaseDto {
	private String orderName;
	private String payerAccountNo;//收款人账号
	private String payerAccountType;//收款人账号类型
	private String payerAccountName;//收款人账号名称
	private String payerCertNo;//收款人身份证号
	private String payerMobile;//收款人手机号码
	 /** 平台明细流水,提供给外部系统的订单号 */
    private String transSubSeq;
    private java.lang.String promoterDeptNo;//订单所属于银行哪个机构
    



    private String payRouteMethod;//具体支付渠道:  代付：0001:银联代付0002:中金代付    --0003:银联代收0004:银联无跳转支付0005:中金代收
    public String getPayRouteMethod() {
        return payRouteMethod;
    }
    public void setPayRouteMethod(String payRouteMethod) {
        this.payRouteMethod = payRouteMethod;
    }
    
    
    public String getTransSubSeq() {
		return transSubSeq;
	}
	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}


	/** 商户号 */
    private String merNo;

    /** 商户订单号 */
    private String outerOrderNo;

    /** 商户时间 */
    private String merDate;

    /** 代付类型 */
    private String singlePayType;

    /** 付款方账号 */
    private String payAcctNo;

    /** 付款方户名 */
    private String payAcctName;

    /** 交易备注 */
    private String transComments;

    /** 银行标识 */
    private String bankNo;

    /** 代扣银行卡号 */
    private String acctNo;

    /** 代扣银行卡号账户名称 */
    private String acctName;

    /** 代扣账户类型，此账户类型为: 11-个人账户；12-企业账户；与平台账户类弄不同； */
    private String accountType;

    /** 账户类型，与平台账户类型一样 */
    private String acctType;

    /** 交易金额，单位：元 */
    private BigDecimal transAmt;

    /** 手机号 */
    private String mobile;

    /** 字符串交易金额，单位分 */
    private String charTransAmt;

    /** 字符串交易金额，单位元 */
    private String charTransAmtYuan;

    /** 本他行标志 */
    private String bankBinFlag;

    /** 接收支付成功通知的URL */
    private String notifyUrl;

    /** 交易类型 */
    private String transType;

    /** 支付服务类型 */
    private String payServicType;

    /** 同步通知路径 处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径 */
    private String returnUrl;

    /** 支付类型 */
    private String payType;

    /** 币种 订单用 */
    private String curr;

    /** 平台支付订单号 */
    private String orderNo;

    //平台支付订单状态
    private String orderStat;

    /** 用户IP */
    private String spbillCreateIp;

    /** 订单类型 */
    private String orderType;

    /** 代付标志：01-收银台发起的代付交易； 02-商户发起的代付交易；*/
    private String singlePaymentFlag;

    /** 来往账标志 */
    private String srFlag;
    
    private String isEsbCore;

    public String getIsEsbCore() {
		return isEsbCore;
	}
	public void setIsEsbCore(String isEsbCore) {
		this.isEsbCore = isEsbCore;
	}
	public String getIsAt() {
		return isAt;
	}
	public void setIsAt(String isAt) {
		this.isAt = isAt;
	}
	private String routeCode;
    
    private String isAt;

    /**
     * 卡类型 1、借记卡 2、贷记卡       db_column: CARD_BIN_TYPE
     */
	private java.lang.String cardBinType;
	/**
     * 是否是结算 如果是结算就是需要检查商户订单号  Y:是
     */
	private String stlFlag;

	public java.lang.String getPromoterDeptNo() {
		return promoterDeptNo;
	}
	public void setPromoterDeptNo(java.lang.String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}
	 public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getPayerAccountNo() {
			return payerAccountNo;
		}

		public void setPayerAccountNo(String payerAccountNo) {
			this.payerAccountNo = payerAccountNo;
		}

		public String getPayerAccountType() {
			return payerAccountType;
		}

		public void setPayerAccountType(String payerAccountType) {
			this.payerAccountType = payerAccountType;
		}

		public String getPayerAccountName() {
			return payerAccountName;
		}

		public void setPayerAccountName(String payerAccountName) {
			this.payerAccountName = payerAccountName;
		}

		public String getPayerCertNo() {
			return payerCertNo;
		}

		public void setPayerCertNo(String payerCertNo) {
			this.payerCertNo = payerCertNo;
		}

		public String getPayerMobile() {
			return payerMobile;
		}

		public void setPayerMobile(String payerMobile) {
			this.payerMobile = payerMobile;
		}


    public String getStlFlag() {
		return stlFlag;
	}

	public void setStlFlag(String stlFlag) {
		this.stlFlag = stlFlag;
	}

	public java.lang.String getCardBinType() {
		return cardBinType;
	}

	public void setCardBinType(java.lang.String cardBinType) {
		this.cardBinType = cardBinType;
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

    public String getSinglePayType() {
        return singlePayType;
    }

    public void setSinglePayType(String singlePayType) {
        this.singlePayType = singlePayType;
    }

    public String getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(String payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public String getPayAcctName() {
        return payAcctName;
    }

    public void setPayAcctName(String payAcctName) {
        this.payAcctName = payAcctName;
    }

    public String getTransComments() {
        return transComments;
    }

    public void setTransComments(String transComments) {
        this.transComments = transComments;
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

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCharTransAmt() {
        return charTransAmt;
    }

    public void setCharTransAmt(String charTransAmt) {
        this.charTransAmt = charTransAmt;
    }

    public String getBankBinFlag() {
        return bankBinFlag;
    }

    public void setBankBinFlag(String bankBinFlag) {
        this.bankBinFlag = bankBinFlag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
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

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
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

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSinglePaymentFlag() {
        return singlePaymentFlag;
    }

    public void setSinglePaymentFlag(String singlePaymentFlag) {
        this.singlePaymentFlag = singlePaymentFlag;
    }

    public String getCharTransAmtYuan() {
        return charTransAmtYuan;
    }

    public void setCharTransAmtYuan(String charTransAmtYuan) {
        this.charTransAmtYuan = charTransAmtYuan;
    }

    public String getSrFlag() {
        return srFlag;
    }

    public void setSrFlag(String srFlag) {
        this.srFlag = srFlag;
    }
}
