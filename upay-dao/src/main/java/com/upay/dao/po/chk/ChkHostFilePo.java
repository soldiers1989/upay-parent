
package com.upay.dao.po.chk;

import com.pactera.dipper.po.BasePo;


public class ChkHostFilePo extends BasePo {
    private static final long serialVersionUID = 1L;
    // alias
    public static final String TABLE_ALIAS = "核心对账文件登记临时表";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_orgCode = "单位代码";
    public static final String ALIAS_transType = "业务类型";
    public static final String ALIAS_transDate = "业务日期";
    public static final String ALIAS_transSeq = "业务流水号";
    public static final String ALIAS_hostTransDate = "核心业务日期";
    public static final String ALIAS_hostTransSeq = "核心业务流水号";
    public static final String ALIAS_payerAcctNo = "付款人账号（转出账号）";
    public static final String ALIAS_payeeAcctNo = "收款人账号（转入账号）";
    public static final String ALIAS_amount = "交易金额";
    public static final String ALIAS_feeAmt = "手续费";
    public static final String ALIAS_thirdDate = "第三方交易日期";
    public static final String ALIAS_remark1 = "备注1";
    public static final String ALIAS_remark2 = "备注2";
    public static final String ALIAS_remark3 = "备注3";
    public static final String ALIAS_remark4 = "备注4";
    public static final String ALIAS_remark5 = "备注5";

    // columns START
    /**
     * 单位代码 db_column: orgCode
     */
    private java.lang.String orgCode;
    /**
     * 业务类型 db_column: transType ;
     */
    private java.lang.String transType;
    /**
     * 业务日期:YYYYMMDD db_column: transDate
     */
    private java.util.Date transDate;
    /**
     * 业务流水号 db_column: transSeq
     */
    private java.lang.String transSeq;
    /**
     * 核心业务日期:YYYYMMDD db_column: hostTransDate
     */
    private java.util.Date hostTransDate;
    /**
     * 核心业务流水号 db_column: hostTransSeq
     */
    private java.lang.String hostTransSeq;
    /**
     * 付款人账号（转出账号） db_column: payerAcctNo
     */
    private java.lang.String payerAcctNo;
    /**
     * 收款人账号（转入账号） db_column: payeeAcctNo
     */
    private java.lang.String payeeAcctNo;
    /**
     * 交易金额 db_column: amount
     */
    private java.math.BigDecimal amount;
    /**
     * 交易手续费 db_column: feeAmt
     */
    private java.lang.String feeAmt;
    /**
     * 第三方交易日期:YYYYMMDD db_column: thirdDate
     */
    private java.lang.String thirdDate;
    /**
     * 备注1 db_column: transDate
     */
    private java.lang.String remark1;
    /**
     * 备注2 db_column: remark2
     */
    private java.lang.String remark2;
    /**
     * 备注3 db_column: remark3
     */
    private java.lang.String remark3;
    /**
     * 备注4 db_column: remark4
     */
    private java.lang.String remark4;
    /**
     * 备注5 db_column: remark5
     */
    private java.lang.String remark5;


    public java.lang.String getOrgCode() {
        return orgCode;
    }


    public void setOrgCode(java.lang.String orgCode) {
        this.orgCode = orgCode;
    }


    public java.lang.String getTransType() {
        return transType;
    }


    public void setTransType(java.lang.String transType) {
        this.transType = transType;
    }


    public java.util.Date getTransDate() {
        return transDate;
    }


    public void setTransDate(java.util.Date transDate) {
        this.transDate = transDate;
    }


    public java.lang.String getTransSeq() {
        return transSeq;
    }


    public void setTransSeq(java.lang.String transSeq) {
        this.transSeq = transSeq;
    }


    public java.util.Date getHostTransDate() {
        return hostTransDate;
    }


    public void setHostTransDate(java.util.Date hostTransDate) {
        this.hostTransDate = hostTransDate;
    }


    public java.lang.String getHostTransSeq() {
        return hostTransSeq;
    }


    public void setHostTransSeq(java.lang.String hostTransSeq) {
        this.hostTransSeq = hostTransSeq;
    }


    public java.lang.String getPayerAcctNo() {
        return payerAcctNo;
    }


    public void setPayerAcctNo(java.lang.String payerAcctNo) {
        this.payerAcctNo = payerAcctNo;
    }


    public java.lang.String getPayeeAcctNo() {
        return payeeAcctNo;
    }


    public void setPayeeAcctNo(java.lang.String payeeAcctNo) {
        this.payeeAcctNo = payeeAcctNo;
    }


    public java.math.BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    public java.lang.String getFeeAmt() {
        return feeAmt;
    }


    public void setFeeAmt(java.lang.String feeAmt) {
        this.feeAmt = feeAmt;
    }


    public java.lang.String getThirdDate() {
        return thirdDate;
    }


    public void setThirdDate(java.lang.String thirdDate) {
        this.thirdDate = thirdDate;
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


    public java.lang.String getRemark4() {
        return remark4;
    }


    public void setRemark4(java.lang.String remark4) {
        this.remark4 = remark4;
    }


    public java.lang.String getRemark5() {
        return remark5;
    }


    public void setRemark5(java.lang.String remark5) {
        this.remark5 = remark5;
    }

}
