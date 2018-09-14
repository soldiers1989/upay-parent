package com.upay.batch.stepservice.chk.atwechat.bean;

public class ChkWechatBeanAT {
    private String txnTime; // 交易时间

    private String appid; // 公众账号ID

    private String wcMerNo;// 微信商户号

    private String wcSecMerNo;// 微信子商户号

    private String traceNo; // 设备号

    private String wcOrderNo; // 微信订单号

    private String orderNo; // 平台订单号商户订单号

    private String userFlag;// 用户标识

    private String txnType; // 交易类型

    private String txnStat; // 交易状态

    private String payBankNo; // 付款银行

    private String ccy; // 币种

    private String txnAmt; // 交易总金额

    private String voucherAmt; // 企业红包金额

    private String wcRefundOrderNo;//微信退款订单号
    
    private String refundOrderNo; // 商户退款单号

    private String refundAmt;// 退款金额

    private String refundVoucherAmt; // 企业红包退款金额

    private String refundType;// 退款类型

    private String refundStat;// 退款状态

    private String commodityName; // 商品名称

    private String commodityByte; // 商品数据包

    private String txnFee; // 交易手续费
    private String txnFeeRate; // 手续费费率
    private String orderAmount; // 订单金额
    private String refundApplicationAmount; // 申请退款金额
    private String txnFeeRateRemark; // 费率备注

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getRefundApplicationAmount() {
        return refundApplicationAmount;
    }

    public void setRefundApplicationAmount(String refundApplicationAmount) {
        this.refundApplicationAmount = refundApplicationAmount;
    }

    public String getTxnFeeRateRemark() {
        return txnFeeRateRemark;
    }

    public void setTxnFeeRateRemark(String txnFeeRateRemark) {
        this.txnFeeRateRemark = txnFeeRateRemark;
    }

    public String getAppid() {
        return appid;
    }


    public void setAppid(String appid) {
        this.appid = appid;
    }


    public String getWcMerNo() {
        return wcMerNo;
    }


    public void setWcMerNo(String wcMerNo) {
        this.wcMerNo = wcMerNo;
    }


    public String getWcSecMerNo() {
        return wcSecMerNo;
    }


    public void setWcSecMerNo(String wcSecMerNo) {
        this.wcSecMerNo = wcSecMerNo;
    }


    public String getTraceNo() {
        return traceNo;
    }


    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }


    public String getWcOrderNo() {
        return wcOrderNo;
    }


    public void setWcOrderNo(String wcOrderNo) {
        this.wcOrderNo = wcOrderNo;
    }


    public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getUserFlag() {
        return userFlag;
    }


    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }


    public String getTxnType() {
        return txnType;
    }


    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }


    public String getTxnStat() {
        return txnStat;
    }


    public void setTxnStat(String txnStat) {
        this.txnStat = txnStat;
    }


    public String getPayBankNo() {
        return payBankNo;
    }


    public void setPayBankNo(String payBankNo) {
        this.payBankNo = payBankNo;
    }


    public String getCcy() {
        return ccy;
    }


    public void setCcy(String ccy) {
        this.ccy = ccy;
    }


    public String getRefundOrderNo() {
        return refundOrderNo;
    }


    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }


    public String getRefundType() {
        return refundType;
    }


    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }


    public String getRefundStat() {
        return refundStat;
    }


    public void setRefundStat(String refundStat) {
        this.refundStat = refundStat;
    }


    public String getCommodityName() {
        return commodityName;
    }


    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }


    public String getCommodityByte() {
        return commodityByte;
    }


    public void setCommodityByte(String commodityByte) {
        this.commodityByte = commodityByte;
    }


    public String getTxnTime() {
        return txnTime;
    }


    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }


    public String getTxnAmt() {
        return txnAmt;
    }


    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }


    public String getVoucherAmt() {
        return voucherAmt;
    }


    public void setVoucherAmt(String voucherAmt) {
        this.voucherAmt = voucherAmt;
    }

    public String getWcRefundOrderNo() {
        return wcRefundOrderNo;
    }


    public void setWcRefundOrderNo(String wcRefundOrderNo) {
        this.wcRefundOrderNo = wcRefundOrderNo;
    }


    public String getRefundAmt() {
        return refundAmt;
    }


    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }


    public String getRefundVoucherAmt() {
        return refundVoucherAmt;
    }


    public void setRefundVoucherAmt(String refundVoucherAmt) {
        this.refundVoucherAmt = refundVoucherAmt;
    }


    public String getTxnFee() {
        return txnFee;
    }


    public void setTxnFee(String txnFee) {
        this.txnFee = txnFee;
    }


    public String getTxnFeeRate() {
        return txnFeeRate;
    }


    public void setTxnFeeRate(String txnFeeRate) {
        this.txnFeeRate = txnFeeRate;
    }

}
