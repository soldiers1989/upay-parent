
package com.upay.dao.po.chk;

import com.pactera.dipper.po.BasePo;


public class StlCountPo extends BasePo {

    /**
     * 
     */
    private static final long serialVersionUID = -5452319802514203799L;
    public static final String ALIAS_TXN_TOT_AMT = "总金额";
    public static final String ALIAS_PAY_AMT = "应付本金";
    public static final String ALIAS_PAY_FEE = "应付手续费";
    public static final String ALIAS_REV_AMT = "应收本金";
    public static final String ALIAS_REV_FEE = "应收手续费";
    public static final String ALIAS_BAR_AMT = "汇总扎差";
    public static final String ALIAS_CARD_REV_FEE = "发卡机构应收手续费";
    public static final String ALIAS_CARD_PAY_FEE = "发卡机构应付手续费";
    public static final String ALIAS_PLATE_REV_FEE = "平台应收手续费";
    public static final String ALIAS_PLATE_PAY_FEE = "平台应付手续费";
    public static final String ALIAS_COUNT = "总记录数";

    // columns START

    /**
     * 总金额 db_column: TXN_TOT_AMT
     */
    private java.math.BigDecimal txnTotAmt;
    /**
     * 应付本金 db_column: PAY_AMT
     */
    private java.math.BigDecimal payAmt;
    /**
     * 应付手续费 db_column: PAY_FEE
     */
    private java.math.BigDecimal payFee;
    /**
     * 应收本金 db_column: REV_AMT
     */
    private java.math.BigDecimal revAmt;
    /**
     * 应收手续费 db_column: REV_FEE
     */
    private java.math.BigDecimal revFee;
    /**
     * 汇总扎差 db_column: BAR_AMT
     */
    private java.math.BigDecimal barAmt;
    /**
     * 发卡机构应收手续费 db_column: CARD_REV_FEE
     */
    private java.math.BigDecimal cardRevFee;
    /**
     * 发卡机构应付手续费 db_column: CARD_PAY_FEE
     */
    private java.math.BigDecimal cardPayFee;
    /**
     * 平台应收手续费 db_column: PLATE_REV_FEE
     */
    private java.math.BigDecimal plateRevFee;
    /**
     * 平台应付手续费 db_column: PLATE_PAY_FEE
     */
    private java.math.BigDecimal platePayFee;
    /**
     * 总记录数db_column: count
     */
    private java.math.BigDecimal count;
    // columns END


    public java.math.BigDecimal getTxnTotAmt() {
        return this.txnTotAmt;
    }


    public void setTxnTotAmt(java.math.BigDecimal value) {
        this.txnTotAmt = value;
    }


    public java.math.BigDecimal getPayAmt() {
        return this.payAmt;
    }


    public void setPayAmt(java.math.BigDecimal value) {
        this.payAmt = value;
    }


    public java.math.BigDecimal getPayFee() {
        return this.payFee;
    }


    public void setPayFee(java.math.BigDecimal value) {
        this.payFee = value;
    }


    public java.math.BigDecimal getRevAmt() {
        return this.revAmt;
    }


    public void setRevAmt(java.math.BigDecimal value) {
        this.revAmt = value;
    }


    public java.math.BigDecimal getRevFee() {
        return this.revFee;
    }


    public void setRevFee(java.math.BigDecimal value) {
        this.revFee = value;
    }


    public java.math.BigDecimal getBarAmt() {
        return this.barAmt;
    }


    public void setBarAmt(java.math.BigDecimal value) {
        this.barAmt = value;
    }


    public java.math.BigDecimal getCardRevFee() {
        return this.cardRevFee;
    }


    public void setCardRevFee(java.math.BigDecimal value) {
        this.cardRevFee = value;
    }


    public java.math.BigDecimal getCardPayFee() {
        return this.cardPayFee;
    }


    public void setCardPayFee(java.math.BigDecimal value) {
        this.cardPayFee = value;
    }


    public java.math.BigDecimal getPlateRevFee() {
        return this.plateRevFee;
    }


    public void setPlateRevFee(java.math.BigDecimal value) {
        this.plateRevFee = value;
    }


    public java.math.BigDecimal getPlatePayFee() {
        return this.platePayFee;
    }


    public void setPlatePayFee(java.math.BigDecimal value) {
        this.platePayFee = value;
    }


    public java.math.BigDecimal getCount() {
        return count;
    }


    public void setCount(java.math.BigDecimal count) {
        this.count = count;
    }

}
