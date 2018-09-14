package com.upay.dao.po.chk;

import com.pactera.dipper.po.BasePo;


public class ChkErrListPo extends BasePo {
    private static final long serialVersionUID = 1L;
    // alias
    public static final String TABLE_ALIAS = "ChkErrList";
    public static final String ALIAS_ID = "PK";
    public static final String ALIAS_TRANS_DATE = "交易日期  ";
    public static final String ALIAS_TRANS_TIME = "交易时间";
    public static final String ALIAS_TRANS_CODE = "交易代码  内部交易代码";
    public static final String ALIAS_SYS_SEQ = "平台流水号  交易流水号";
    public static final String ALIAS_CHNL_ID = "渠道代码";
    public static final String ALIAS_CHNL_SEQ = "渠道流水号  ";
    public static final String ALIAS_ORDER_NO = "渠道订单号   门户生成/商家订单号";
    public static final String ALIAS_HOST_SEQ = "核心流水号  核心交易流水号/明细流水号";
    public static final String ALIAS_TELLER_SEQ = "柜员流水号";
    public static final String ALIAS_BATCH_NO = "批次号  ";
    public static final String ALIAS_ORG_CODE = "第三方机构代码（对应支付订单中的商户代码）";
    public static final String ALIAS_HOST_CHK_BATCH_NO = "核心对账批次号";
    public static final String ALIAS_HOST_CHK_DATE = "核心对帐日期";
    public static final String ALIAS_THIRD_CHK_BATCH_NO = "第三方对账批次号";
    public static final String ALIAS_THIRD_CHK_DATE = "第三方对账日期";
    public static final String ALIAS_CURR_NO = "币种";
    public static final String ALIAS_PAY_ACCT = "付款账号";
    public static final String ALIAS_PAY_BANKNO = "付款人机构号";
    public static final String ALIAS_PAY_ACCT_TYPE = "付款账号类型";
    public static final String ALIAS_PAY_ACCT_BANK_FLAG = "付款账户银行标识 1：本行 2：他行 ";
    public static final String ALIAS_PAYEE_ACCT = "收款人账号";
    public static final String ALIAS_PAYEE_BANKNO = "收款人机构号";
    public static final String ALIAS_REV_ACCT_TYPE = "收款账号类型";
    public static final String ALIAS_REV_ACCT_BANK_FLAG = "收款账户银行标识  1：本行 2：他行";
    public static final String ALIAS_TRANS_AMT = "交易金额";
    public static final String ALIAS_FEE_AMT = "手续费";
    public static final String ALIAS_HOST_ERR_STAT = "主机差错信息 0-  主机无 1-  主机有 2-  帐不平 3-  主机有，平台无";
    public static final String ALIAS_THRID_ERR_STAT = "第三方差错信息 0-第三方有 1-第三方无 2-帐不平";
    public static final String ALIAS_PLAT_STAT = "平台状态";
    public static final String ALIAS_ERR_STAT = "差错处理状态 0—  未处理 1—  处理成功 2—  处理失败";
    public static final String ALIAS_ERR_ADVICE = "差错处理意见";
    public static final String ALIAS_AUTO_FLAG = "自动处理标志  Y-自动处理 N-手动处理";
    public static final String ALIAS_PROC_FLAG =
            "处理方式 0-  补主机账 1-  冲主机帐 2-  补能力中心帐 3-  冲能力中心帐 4-  修改主机流水 5-  修改平台流水 6-  冲能力中心、主机帐 7-  补能力中心、主机帐 8-  修改主机、平台流水 9-  手工处理";
    public static final String ALIAS_SVC_NAME = "原子服务名称     进行补账、冲正、修改状态的后续的原子服务名称";
    public static final String ALIAS_DATA_SOURCE = "数据源  数据来源表： 支付流水表PmtFlowList积分流水表 PotTranList";
    public static final String ALIAS_RESERVE1 = "备用字段1";
    public static final String ALIAS_RESERVE2 = "备用字段2";
    public static final String ALIAS_THIRD_SEQ = "第三方支付流水号";

    // columns START
    /**
     * 交易日期 db_column: TRANS_DATE
     */
    private java.util.Date transDate;
    /**
     * 交易时间 db_column: TRANS_TIME
     */
    private java.util.Date transTime;
    /**
     * 交易代码 内部交易代码 db_column: TRANS_CODE
     */
    private java.lang.String transCode;
    /**
     * 平台流水号 交易流水号 db_column: SYS_SEQ
     */
    private java.lang.String sysSeq;
    /**
     * 渠道代码 db_column: CHNL_ID
     */
    private java.lang.String chnlId;
    /**
     * 渠道流水号 db_column: CHNL_SEQ
     */
    private java.lang.String chnlSeq;
    /**
     * 渠道订单号 门户生成/商家订单号 db_column: ORDER_NO
     */
    private java.lang.String orderNo;
    /**
     * 核心流水号 核心交易流水号/明细流水号 db_column: HOST_SEQ
     */
    private java.lang.String hostSeq;
    /**
     * 柜员流水号 db_column: TELLER_SEQ
     */
    private java.lang.String tellerSeq;
    /**
     * 批次号 db_column: BATCH_NO
     */
    private java.lang.String batchNo;
    /**
     * 第三方机构代码（对应支付订单中的商户代码） db_column: ORG_CODE
     */
    private java.lang.String orgCode;
    /**
     * 核心对账批次号 db_column: HOST_CHK_BATCH_NO
     */
    private java.lang.String hostChkBatchNo;
    /**
     * 核心对帐日期 db_column: HOST_CHK_DATE
     */
    private java.util.Date hostChkDate;
    /**
     * 第三方对账批次号 db_column: THIRD_CHK_BATCH_NO
     */
    private java.lang.String thirdChkBatchNo;
    /**
     * 第三方对账日期 db_column: THIRD_CHK_DATE
     */
    private java.util.Date thirdChkDate;
    /**
     * 币种 db_column: CURR_NO
     */
    private java.lang.String currNo;
    /**
     * 付款账号 db_column: PAY_ACCT
     */
    private java.lang.String payAcct;
    /**
     * 付款人机构号 db_column: PAY_BANKNO
     */
    private java.lang.String payBankno;
    /**
     * 付款账号类型 db_column: PAY_ACCT_TYPE
     */
    private java.lang.String payAcctType;
    /**
     * 付款账户银行标识 1：本行 2：他行 db_column: PAY_ACCT_BANK_FLAG
     */
    private java.lang.String payAcctBankFlag;
    /**
     * 收款人账号 db_column: PAYEE_ACCT
     */
    private java.lang.String payeeAcct;
    /**
     * 收款人机构号 db_column: PAYEE_BANKNO
     */
    private java.lang.String payeeBankno;
    /**
     * 收款账号类型 db_column: REV_ACCT_TYPE
     */
    private java.lang.String revAcctType;
    /**
     * 收款账户银行标识 1：本行 2：他行 db_column: REV_ACCT_BANK_FLAG
     */
    private java.lang.String revAcctBankFlag;
    /**
     * 交易金额 db_column: TRANS_AMT
     */
    private java.math.BigDecimal transAmt;
    /**
     * 手续费 db_column: FEE_AMT
     */
    private java.math.BigDecimal feeAmt;
    /**
     * 主机差错信息 0- 主机无 1- 主机有 2- 帐不平 3- 主机有，平台无 db_column: HOST_ERR_STAT
     */
    private java.lang.String hostErrStat;
    /**
     * 第三方差错信息 0-第三方有 1-第三方无 2-帐不平 db_column: THRID_ERR_STAT
     */
    private java.lang.String thridErrStat;
    /**
     * 平台状态 db_column: PLAT_STAT
     */
    private java.lang.String platStat;
    /**
     * 差错处理状态 0— 未处理 1— 处理成功 2— 处理失败 db_column: ERR_STAT
     */
    private java.lang.String errStat;
    /**
     * 差错处理意见 db_column: ERR_ADVICE
     */
    private java.lang.String errAdvice;
    /**
     * 自动处理标志 Y-自动处理 N-手动处理 db_column: AUTO_FLAG
     */
    private java.lang.String autoFlag;
    /**
     * 处理方式 0- 补主机账 1- 冲主机帐 2- 补能力中心帐 3- 冲能力中心帐 4- 修改主机流水 5- 修改平台流水 6- 冲能力中心、主机帐
     * 7- 补能力中心、主机帐 8- 修改主机、平台流水 9- 手工处理 db_column: PROC_FLAG
     */
    private java.lang.String procFlag;
    /**
     * 原子服务名称 进行补账、冲正、修改状态的后续的原子服务名称 db_column: SVC_NAME
     */
    private java.lang.String svcName;
    /**
     * 数据源 数据来源表： 支付流水表"PmtFlowList" 积分流水表 "PotTranList " db_column: DATA_SOURCE
     */
    private java.lang.String dataSource;
    /**
     * 备用字段1 db_column: RESERVE1
     */
    private java.lang.String reserve1;
    /**
     * 备用字段2 db_column: RESERVE2
     */
    private java.lang.String reserve2;

    /**
     * 第三方流水号 db_column: THIRD_SEQ
     */
    private java.lang.String thirdSeq;


    // columns END

    public java.util.Date getTransDate() {
        return this.transDate;
    }


    public void setTransDate(java.util.Date value) {
        this.transDate = value;
    }


    public java.util.Date getTransTime() {
        return this.transTime;
    }


    public void setTransTime(java.util.Date value) {
        this.transTime = value;
    }


    public java.lang.String getTransCode() {
        return this.transCode;
    }


    public void setTransCode(java.lang.String value) {
        this.transCode = value;
    }


    public java.lang.String getSysSeq() {
        return this.sysSeq;
    }


    public void setSysSeq(java.lang.String value) {
        this.sysSeq = value;
    }


    public java.lang.String getChnlId() {
        return this.chnlId;
    }


    public void setChnlId(java.lang.String value) {
        this.chnlId = value;
    }


    public java.lang.String getChnlSeq() {
        return this.chnlSeq;
    }


    public void setChnlSeq(java.lang.String value) {
        this.chnlSeq = value;
    }


    public java.lang.String getOrderNo() {
        return this.orderNo;
    }


    public void setOrderNo(java.lang.String value) {
        this.orderNo = value;
    }


    public java.lang.String getHostSeq() {
        return this.hostSeq;
    }


    public void setHostSeq(java.lang.String value) {
        this.hostSeq = value;
    }


    public java.lang.String getTellerSeq() {
        return this.tellerSeq;
    }


    public void setTellerSeq(java.lang.String value) {
        this.tellerSeq = value;
    }


    public java.lang.String getBatchNo() {
        return this.batchNo;
    }


    public void setBatchNo(java.lang.String value) {
        this.batchNo = value;
    }


    public java.lang.String getOrgCode() {
        return this.orgCode;
    }


    public void setOrgCode(java.lang.String value) {
        this.orgCode = value;
    }


    public java.lang.String getHostChkBatchNo() {
        return this.hostChkBatchNo;
    }


    public void setHostChkBatchNo(java.lang.String value) {
        this.hostChkBatchNo = value;
    }


    public java.util.Date getHostChkDate() {
        return this.hostChkDate;
    }


    public void setHostChkDate(java.util.Date value) {
        this.hostChkDate = value;
    }


    public java.lang.String getThirdChkBatchNo() {
        return this.thirdChkBatchNo;
    }


    public void setThirdChkBatchNo(java.lang.String value) {
        this.thirdChkBatchNo = value;
    }


    public java.util.Date getThirdChkDate() {
        return this.thirdChkDate;
    }


    public void setThirdChkDate(java.util.Date value) {
        this.thirdChkDate = value;
    }


    public java.lang.String getCurrNo() {
        return this.currNo;
    }


    public void setCurrNo(java.lang.String value) {
        this.currNo = value;
    }


    public java.lang.String getPayAcct() {
        return this.payAcct;
    }


    public void setPayAcct(java.lang.String value) {
        this.payAcct = value;
    }


    public java.lang.String getPayBankno() {
        return this.payBankno;
    }


    public void setPayBankno(java.lang.String value) {
        this.payBankno = value;
    }


    public java.lang.String getPayAcctType() {
        return this.payAcctType;
    }


    public void setPayAcctType(java.lang.String value) {
        this.payAcctType = value;
    }


    public java.lang.String getPayAcctBankFlag() {
        return this.payAcctBankFlag;
    }


    public void setPayAcctBankFlag(java.lang.String value) {
        this.payAcctBankFlag = value;
    }


    public java.lang.String getPayeeAcct() {
        return this.payeeAcct;
    }


    public void setPayeeAcct(java.lang.String value) {
        this.payeeAcct = value;
    }


    public java.lang.String getPayeeBankno() {
        return this.payeeBankno;
    }


    public void setPayeeBankno(java.lang.String value) {
        this.payeeBankno = value;
    }


    public java.lang.String getRevAcctType() {
        return this.revAcctType;
    }


    public void setRevAcctType(java.lang.String value) {
        this.revAcctType = value;
    }


    public java.lang.String getRevAcctBankFlag() {
        return this.revAcctBankFlag;
    }


    public void setRevAcctBankFlag(java.lang.String value) {
        this.revAcctBankFlag = value;
    }


    public java.math.BigDecimal getTransAmt() {
        return this.transAmt;
    }


    public void setTransAmt(java.math.BigDecimal value) {
        this.transAmt = value;
    }


    public java.math.BigDecimal getFeeAmt() {
        return this.feeAmt;
    }


    public void setFeeAmt(java.math.BigDecimal value) {
        this.feeAmt = value;
    }


    public java.lang.String getHostErrStat() {
        return this.hostErrStat;
    }


    public void setHostErrStat(java.lang.String value) {
        this.hostErrStat = value;
    }


    public java.lang.String getThridErrStat() {
        return this.thridErrStat;
    }


    public void setThridErrStat(java.lang.String value) {
        this.thridErrStat = value;
    }


    public java.lang.String getPlatStat() {
        return this.platStat;
    }


    public void setPlatStat(java.lang.String value) {
        this.platStat = value;
    }


    public java.lang.String getErrStat() {
        return this.errStat;
    }


    public void setErrStat(java.lang.String value) {
        this.errStat = value;
    }


    public java.lang.String getErrAdvice() {
        return this.errAdvice;
    }


    public void setErrAdvice(java.lang.String value) {
        this.errAdvice = value;
    }


    public java.lang.String getAutoFlag() {
        return this.autoFlag;
    }


    public void setAutoFlag(java.lang.String value) {
        this.autoFlag = value;
    }


    public java.lang.String getProcFlag() {
        return this.procFlag;
    }


    public void setProcFlag(java.lang.String value) {
        this.procFlag = value;
    }


    public java.lang.String getSvcName() {
        return this.svcName;
    }


    public void setSvcName(java.lang.String value) {
        this.svcName = value;
    }


    public java.lang.String getDataSource() {
        return this.dataSource;
    }


    public void setDataSource(java.lang.String value) {
        this.dataSource = value;
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


    public java.lang.String getThirdSeq() {
        return thirdSeq;
    }


    public void setThirdSeq(java.lang.String thirdSeq) {
        this.thirdSeq = thirdSeq;
    }

}
