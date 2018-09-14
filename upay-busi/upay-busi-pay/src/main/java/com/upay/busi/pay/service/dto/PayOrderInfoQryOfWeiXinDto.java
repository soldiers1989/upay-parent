/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 外部微信公众号订单信息查询
 * @author zhanggr
 *
 */
public class PayOrderInfoQryOfWeiXinDto extends BaseDto {
    private String orderNo;// 统一支付订单号
    private String outerOrderNo;// 商户订单号
    private String bankType;//微信支付时的付款银行
    
    
    public String getBankType() {
		return bankType;
	}


	public void setBankType(String bankType) {
		this.bankType = bankType;
	}


	/**
     * 支付服务类型 0001担保支付 0002即时到账 db_column: PAY_SERVIC_TYPE
     */
    private java.lang.String payServicType;
    /**
     * 商户日期 外部订单必输 db_column: MER_DATE
     */
    private java.util.Date merDate;

    /**
     * 商户代码 外部订单为外部商户号 db_column: MER_NO
     */
    private java.lang.String merNo;
    /**
     * 二级商户代码 db_column: SEC_MER_NO
     */
    private java.lang.String secMerNo;

    /**
     * 商户订单生成日期 订单生成时间，格式为yyyyMMdd hh：mm：ss。该时间取自商户服务器 db_column:
     * OUTER_ORDER_START_DATE
     */
    private String outerOrderStartDate;
    /**
     * 商户订单失效日期 订单生成时间，格式为yyyyMMdd hh：mm：ss。该时间取自商户服务器 db_column:
     * OUTER_ORDER_END_DATE
     */
    private String outerOrderEndDate;

    /**
     * 订单名称 db_column: ORDER_NAME
     */
    private java.lang.String orderName;

    /**
     * 订单创建日期 创建支付订单平台日期 db_column: ORDER_DATE
     */
    private java.util.Date orderDate;
    /**
     * 订单创建时间 db_column: ORDER_TIME
     */
    private String orderTime;
    /**
     * 币种 CNY：人民币 db_column: CURR
     */
    private java.lang.String curr;
    /**
     * 交易金额 总交易金额=其他费用+商品费用 db_column: TRANS_AMT
     */
    private java.math.BigDecimal transAmt;
    /**
     * 其他费用 其他费用说明 db_column: OTHER_TRAN_AMT
     */
    private java.math.BigDecimal otherTranAmt;
    /**
     * 商品费用 db_column: PRODUCT_AMT
     */
    private java.math.BigDecimal productAmt;

    /**
     * 订单状态 见附录4.3,order_stat db_column: ORDER_STAT
     */
    private java.lang.String orderStat;
    /**
     * 用户IP db_column: SPBILL_CREATE_IP
     */
    private java.lang.String spbillCreateIp;
    /**
     * 异步通知路径 服务器主动通知商户网站里指定的页面http 路径 db_column: NOTIFY_URL
     */
    private java.lang.String notifyUrl;
    /**
     * 同步通知路径 处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。 db_column: RETURN_URL
     */
    private java.lang.String returnUrl;
    /**
     * 附言 db_column: TRANS_COMMENTS
     */
    private java.lang.String transComments;
    /**
     * 订单描述 db_column: ORDER_DES
     */
    private java.lang.String orderDes;
    /**
     * 用户ID，微信公众号支付用户在公众号中的唯一标识 db_column: OPEN_ID
     */
    private java.lang.String openId;
    /**
     * 支付完成时间 db_column: PAY_TIME
     */
    private java.util.Date payTime;
    /**
     * 商户手续费 应收商户手续费       db_column: MER_FEE_AMT 
     */ 	
	private java.math.BigDecimal merFeeAmt;
	
	

    public java.math.BigDecimal getMerFeeAmt() {
		return merFeeAmt;
	}


	public void setMerFeeAmt(java.math.BigDecimal merFeeAmt) {
		this.merFeeAmt = merFeeAmt;
	}


	public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getOuterOrderNo() {
        return outerOrderNo;
    }


    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }


    public java.lang.String getPayServicType() {
        return payServicType;
    }


    public void setPayServicType(java.lang.String payServicType) {
        this.payServicType = payServicType;
    }


    public java.util.Date getMerDate() {
        return merDate;
    }


    public void setMerDate(java.util.Date merDate) {
        this.merDate = merDate;
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


    


    public String getOuterOrderStartDate() {
        return outerOrderStartDate;
    }


    public void setOuterOrderStartDate(String outerOrderStartDate) {
        this.outerOrderStartDate = outerOrderStartDate;
    }


    public String getOuterOrderEndDate() {
        return outerOrderEndDate;
    }


    public void setOuterOrderEndDate(String outerOrderEndDate) {
        this.outerOrderEndDate = outerOrderEndDate;
    }


    public java.lang.String getOrderName() {
        return orderName;
    }


    public void setOrderName(java.lang.String orderName) {
        this.orderName = orderName;
    }


    public java.util.Date getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }


    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }


    public java.lang.String getCurr() {
        return curr;
    }


    public void setCurr(java.lang.String curr) {
        this.curr = curr;
    }


    public java.math.BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(java.math.BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public java.math.BigDecimal getOtherTranAmt() {
        return otherTranAmt;
    }


    public void setOtherTranAmt(java.math.BigDecimal otherTranAmt) {
        this.otherTranAmt = otherTranAmt;
    }


    public java.math.BigDecimal getProductAmt() {
        return productAmt;
    }


    public void setProductAmt(java.math.BigDecimal productAmt) {
        this.productAmt = productAmt;
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


    public java.util.Date getPayTime() {
        return payTime;
    }


    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

}
