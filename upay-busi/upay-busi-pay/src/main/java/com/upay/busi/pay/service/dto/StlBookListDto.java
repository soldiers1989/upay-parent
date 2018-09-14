package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseQueryDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 	资金结算登记薄 (T_STL_BOOK)
 * 
 * @author liudan
 * 
 */
public class StlBookListDto extends BaseQueryDto {

    /**
     * 结算清算日期       db_column: STL_DATE
     */
    private String stlDate;

    /**
     * 结算批次号  YYMMDD + 12位系统交易流水号       db_column: STL_BATCH_NO
     */
    private java.lang.String stlBatchNo;
    /**
     * 商户代码  结算主体       db_column: MER_NO
     */
    private java.lang.String merNo;
    /**
     * 二级商户代码  STL_MER_FLAG=2的情况下，发卡机构对应的特约商户       db_column: SEC_MER_NO
     */
    private java.lang.String secMerNo;
    /**
     * 总金额       db_column: TXN_TOT_AMT
     */

    /**
     * 应付本金       db_column: PAY_AMT
     */
    private java.math.BigDecimal payAmt;
    /**
     * 应付手续费       db_column: PAY_FEE
     */
    private java.math.BigDecimal payFee;
    /**
     * 应收本金       db_column: REV_AMT
     */
    private java.math.BigDecimal revAmt;
    /**
     * 应收手续费       db_column: REV_FEE
     */
    private java.math.BigDecimal revFee;
    /**
     * 汇总扎差       db_column: BAR_AMT
     */
    private java.math.BigDecimal barAmt;

    /**
     * 状态 0：登记 1：完成（入账）       db_column: STAT
     */
    private java.lang.String stat;

    public String getStlDate() {
        return stlDate;
    }

    public void setStlDate(String stlDate) {
        this.stlDate = stlDate;
    }

    public String getStlBatchNo() {
        return stlBatchNo;
    }

    public void setStlBatchNo(String stlBatchNo) {
        this.stlBatchNo = stlBatchNo;
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

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    public BigDecimal getRevAmt() {
        return revAmt;
    }

    public void setRevAmt(BigDecimal revAmt) {
        this.revAmt = revAmt;
    }

    public BigDecimal getRevFee() {
        return revFee;
    }

    public void setRevFee(BigDecimal revFee) {
        this.revFee = revFee;
    }

    public BigDecimal getBarAmt() {
        return barAmt;
    }

    public void setBarAmt(BigDecimal barAmt) {
        this.barAmt = barAmt;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
