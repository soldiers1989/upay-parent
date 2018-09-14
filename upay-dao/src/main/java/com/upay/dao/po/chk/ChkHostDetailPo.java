
package com.upay.dao.po.chk;

import com.pactera.dipper.po.BasePo;


public class ChkHostDetailPo extends BasePo {
    private static final long serialVersionUID = 1L;
    // alias
    public static final String TABLE_ALIAS = "ChkHostDetail";
    public static final String ALIAS_ID = "PK";
    public static final String ALIAS_ORG_CODE = "单位代码";
    public static final String ALIAS_ORG_TYPE = "单位类型";
    public static final String ALIAS_CHK_ACT_NO = "对账批次号";
    public static final String ALIAS_CLEAR_DATE = "对帐日期";
    public static final String ALIAS_CHNL_ID = "渠道号";
    public static final String ALIAS_CHNL_DATE = "渠道日期";
    public static final String ALIAS_CHNL_SEQ = "渠道流水号";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_TELLER_SEQ = "柜员流水号";
    public static final String ALIAS_TRAN_STAT = "交易状态";
    public static final String ALIAS_PLAT_DATE = "平台日期";
    public static final String ALIAS_PLAT_SEQ = "平台流水号";
    public static final String ALIAS_TRANS_CODE = "交易代码";
    public static final String ALIAS_BRCH_NO = "交易机构";
    public static final String ALIAS_TELLER_NO = "交易柜员";
    public static final String ALIAS_CURR_NO = "币种";
    public static final String ALIAS_ACCT_NO = "账户";
    public static final String ALIAS_OTH_ACCT_NO = "对方账号";
    public static final String ALIAS_TRANS_AMT = "交易金额";
    public static final String ALIAS_CHK_FLAG = "核心对账标志 0-未对账 1-对账成功 2-对方多 3-本系统多 4-金额不符 默认：0";
    public static final String ALIAS_CHK_BATCH_NO = "核心对账批次号";
    public static final String ALIAS_CHK_DATE = "核心对账日期";
    public static final String ALIAS_RESERVE1 = "备用字段1";
    public static final String ALIAS_RESERVE2 = "备用字段2";
    public static final String ALIAS_BANK_FLAG = "本他行标志 0-本行 1-他行";
    public static final String ALIAS_INOUT_FLAG = "来往账标志 1-来账 2-往账";
    public static final String ALIAS_ACCT_NAME = "账户名称";
    public static final String ALIAS_REL_ACCT_NAME = "对方账户名称";

    public static final String ALIAS_FEE_AMT = "手续费";

    // columns START
    /**
     * 单位代码 db_column: ORG_CODE
     */
    private java.lang.String orgCode;
    /**
     * 单位类型 db_column: ORG_TYPE
     */
    private java.lang.String orgType;
    /**
     * 对账批次号 db_column: CHK_ACT_NO
     */
    private java.lang.String chkActNo;
    /**
     * 对帐日期 db_column: CLEAR_DATE
     */
    private java.util.Date clearDate;
    /**
     * 渠道号 db_column: CHNL_ID
     */
    private java.lang.String chnlId;
    /**
     * 渠道日期 db_column: CHNL_DATE
     */
    private java.util.Date chnlDate;
    /**
     * 渠道流水号 db_column: CHNL_SEQ
     */
    private java.lang.String chnlSeq;
    /**
     * 交易日期 db_column: TRAN_DATE
     */
    private java.util.Date tranDate;
    /**
     * 柜员流水号 db_column: TELLER_SEQ
     */
    private java.lang.String tellerSeq;
    /**
     * 交易状态 db_column: TRAN_STAT
     */
    private java.lang.String tranStat;
    /**
     * 平台日期 db_column: PLAT_DATE
     */
    private java.util.Date platDate;
    /**
     * 平台流水号 db_column: PLAT_SEQ
     */
    private java.lang.String platSeq;
    /**
     * 交易代码 db_column: TRANS_CODE
     */
    private java.lang.String transCode;
    /**
     * 交易机构 db_column: BRCH_NO
     */
    private java.lang.String brchNo;
    /**
     * 交易柜员 db_column: TELLER_NO
     */
    private java.lang.String tellerNo;
    /**
     * 币种 db_column: CURR_NO
     */
    private java.lang.String currNo;
    /**
     * 账户 db_column: ACCT_NO
     */
    private java.lang.String acctNo;
    /**
     * 对方账号 db_column: OTH_ACCT_NO
     */
    private java.lang.String othAcctNo;
    /**
     * 交易金额 db_column: TRANS_AMT
     */
    private java.math.BigDecimal transAmt;
    /**
     * 核心对账标志 0-未对账 1-对账成功 2-对方多 3-本系统多 4-金额不符 默认：0 db_column: CHK_FLAG
     */
    private java.lang.String chkFlag;
    /**
     * 核心对账批次号 db_column: CHK_BATCH_NO
     */
    private java.lang.String chkBatchNo;
    /**
     * 核心对账日期 db_column: CHK_DATE
     */
    private java.util.Date chkDate;
    /**
     * 备用字段1 db_column: RESERVE1
     */
    private java.lang.String reserve1;
    /**
     * 备用字段2 db_column: RESERVE2
     */
    private java.lang.String reserve2;
    /**
     * 本他行标志 0-本行 1-他行 db_column: BANK_FLAG
     */
    private java.lang.String bankFlag;
    /**
     * 来往账标志 1-来账 2-往账 db_column: INOUT_FLAG
     */
    private java.lang.String inoutFlag;
    /**
     * 账户名称 db_column: ACCT_NAME
     */
    private java.lang.String acctName;
    /**
     * 对方账户名称 db_column: REL_ACCT_NAME
     */
    private java.lang.String relAcctName;
    /**
     * 对方账户名称 db_column: FEE_AMT
     */
    private java.math.BigDecimal feeAmt;

    // columns END


    public java.lang.String getOrgCode() {
        return this.orgCode;
    }


    public void setOrgCode(java.lang.String value) {
        this.orgCode = value;
    }


    public java.lang.String getOrgType() {
        return this.orgType;
    }


    public void setOrgType(java.lang.String value) {
        this.orgType = value;
    }


    public java.lang.String getChkActNo() {
        return this.chkActNo;
    }


    public void setChkActNo(java.lang.String value) {
        this.chkActNo = value;
    }


    public java.util.Date getClearDate() {
        return this.clearDate;
    }


    public void setClearDate(java.util.Date value) {
        this.clearDate = value;
    }


    public java.lang.String getChnlId() {
        return this.chnlId;
    }


    public void setChnlId(java.lang.String value) {
        this.chnlId = value;
    }


    public java.util.Date getChnlDate() {
        return this.chnlDate;
    }


    public void setChnlDate(java.util.Date value) {
        this.chnlDate = value;
    }


    public java.lang.String getChnlSeq() {
        return this.chnlSeq;
    }


    public void setChnlSeq(java.lang.String value) {
        this.chnlSeq = value;
    }


    public java.util.Date getTranDate() {
        return this.tranDate;
    }


    public void setTranDate(java.util.Date value) {
        this.tranDate = value;
    }


    public java.lang.String getTellerSeq() {
        return this.tellerSeq;
    }


    public void setTellerSeq(java.lang.String value) {
        this.tellerSeq = value;
    }


    public java.lang.String getTranStat() {
        return this.tranStat;
    }


    public void setTranStat(java.lang.String value) {
        this.tranStat = value;
    }


    public java.util.Date getPlatDate() {
        return this.platDate;
    }


    public void setPlatDate(java.util.Date value) {
        this.platDate = value;
    }


    public java.lang.String getPlatSeq() {
        return this.platSeq;
    }


    public void setPlatSeq(java.lang.String value) {
        this.platSeq = value;
    }


    public java.lang.String getTransCode() {
        return this.transCode;
    }


    public void setTransCode(java.lang.String value) {
        this.transCode = value;
    }


    public java.lang.String getBrchNo() {
        return this.brchNo;
    }


    public void setBrchNo(java.lang.String value) {
        this.brchNo = value;
    }


    public java.lang.String getTellerNo() {
        return this.tellerNo;
    }


    public void setTellerNo(java.lang.String value) {
        this.tellerNo = value;
    }


    public java.lang.String getCurrNo() {
        return this.currNo;
    }


    public void setCurrNo(java.lang.String value) {
        this.currNo = value;
    }


    public java.lang.String getAcctNo() {
        return this.acctNo;
    }


    public void setAcctNo(java.lang.String value) {
        this.acctNo = value;
    }


    public java.lang.String getOthAcctNo() {
        return this.othAcctNo;
    }


    public void setOthAcctNo(java.lang.String value) {
        this.othAcctNo = value;
    }


    public java.math.BigDecimal getTransAmt() {
        return this.transAmt;
    }


    public void setTransAmt(java.math.BigDecimal value) {
        this.transAmt = value;
    }


    public java.lang.String getChkFlag() {
        return this.chkFlag;
    }


    public void setChkFlag(java.lang.String value) {
        this.chkFlag = value;
    }


    public java.lang.String getChkBatchNo() {
        return this.chkBatchNo;
    }


    public void setChkBatchNo(java.lang.String value) {
        this.chkBatchNo = value;
    }


    public java.util.Date getChkDate() {
        return this.chkDate;
    }


    public void setChkDate(java.util.Date value) {
        this.chkDate = value;
    }


    public java.lang.String getReserve1() {
        return this.reserve1;
    }


    public void setReserve1(java.lang.String value) {
        this.reserve1 = value;
    }


    public java.lang.String getReserve2() {
        return this.reserve2;
    }


    public void setReserve2(java.lang.String value) {
        this.reserve2 = value;
    }


    public java.lang.String getBankFlag() {
        return this.bankFlag;
    }


    public void setBankFlag(java.lang.String value) {
        this.bankFlag = value;
    }


    public java.lang.String getInoutFlag() {
        return this.inoutFlag;
    }


    public void setInoutFlag(java.lang.String value) {
        this.inoutFlag = value;
    }


    public java.lang.String getAcctName() {
        return this.acctName;
    }


    public void setAcctName(java.lang.String value) {
        this.acctName = value;
    }


    public java.lang.String getRelAcctName() {
        return this.relAcctName;
    }


    public void setRelAcctName(java.lang.String value) {
        this.relAcctName = value;
    }


    public java.math.BigDecimal getFeeAmt() {
        return feeAmt;
    }


    public void setFeeAmt(java.math.BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

}
