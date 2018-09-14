/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.io.Serializable;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年10月14日
 */
public class AccLmtCountDto extends BaseDto implements Serializable {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 交易日期 YYYYMMDD       db_column: TRANS_DATE 
     */     
    private java.util.Date transDate;
    /**
     * 账户       db_column: USER_ID 
     */     
    private java.lang.String userId;
    /**
     * 累计限额类型 1：账户系统限额       db_column: LMT_ACCOUNT_FLAG 
     */     
    private java.lang.String lmtAccountFlag;
    /**
     * 系统限额序号 见附录 当LMT_ACCOUNT_FLAG为1时       db_column: SYS_LMT_ID 
     */     
    private java.lang.String sysLmtId;
    /**
     * 系统交易限额序号 见附录 当LMT_ACCOUNT_FLAG为2时       db_column: SYS_TRANSLMT_ID 
     */     
    private java.lang.String sysTranslmtId;
    /**
     * 已累计限额（日）       db_column: DAY_SUM_AMT_LIMIT 
     */     
    private java.math.BigDecimal daySumAmtLimit;
    /**
     * 已累计笔数（日）       db_column: DAY_SUM_COUNT_LIMIT 
     */     
    private java.lang.Integer daySumCountLimit;
    /**
     * 已累计限额（月）       db_column: MON_SUM_AMT_LIMIT 
     */     
    private java.math.BigDecimal monSumAmtLimit;
    /**
     * 已累计笔数（月）       db_column: MON_SUM_COUNT_LIMIT 
     */     
    private java.lang.Integer monSumCountLimit;
    /**
     * 备注       db_column: REMARK1 
     */     
    private java.lang.String remark1;
    /**
     * 已累计限额（年）       db_column: YEAR_SUM_AMT_LIMIT 
     */     
    private java.math.BigDecimal yearSumAmtLimit;
    /**
     * 已累计笔数（年）       db_column: YEAR_SUM_COUNT_LIMIT 
     */     
    private java.lang.Integer yearSumCountLimit;
    public java.util.Date getTransDate() {
        return transDate;
    }
    public void setTransDate(java.util.Date transDate) {
        this.transDate = transDate;
    }
    public java.lang.String getUserId() {
        return userId;
    }
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    public java.lang.String getLmtAccountFlag() {
        return lmtAccountFlag;
    }
    public void setLmtAccountFlag(java.lang.String lmtAccountFlag) {
        this.lmtAccountFlag = lmtAccountFlag;
    }
    public java.lang.String getSysLmtId() {
        return sysLmtId;
    }
    public void setSysLmtId(java.lang.String sysLmtId) {
        this.sysLmtId = sysLmtId;
    }
    public java.lang.String getSysTranslmtId() {
        return sysTranslmtId;
    }
    public void setSysTranslmtId(java.lang.String sysTranslmtId) {
        this.sysTranslmtId = sysTranslmtId;
    }
    public java.math.BigDecimal getDaySumAmtLimit() {
        return daySumAmtLimit;
    }
    public void setDaySumAmtLimit(java.math.BigDecimal daySumAmtLimit) {
        this.daySumAmtLimit = daySumAmtLimit;
    }
    public java.lang.Integer getDaySumCountLimit() {
        return daySumCountLimit;
    }
    public void setDaySumCountLimit(java.lang.Integer daySumCountLimit) {
        this.daySumCountLimit = daySumCountLimit;
    }
    public java.math.BigDecimal getMonSumAmtLimit() {
        return monSumAmtLimit;
    }
    public void setMonSumAmtLimit(java.math.BigDecimal monSumAmtLimit) {
        this.monSumAmtLimit = monSumAmtLimit;
    }
    public java.lang.Integer getMonSumCountLimit() {
        return monSumCountLimit;
    }
    public void setMonSumCountLimit(java.lang.Integer monSumCountLimit) {
        this.monSumCountLimit = monSumCountLimit;
    }
    public java.lang.String getRemark1() {
        return remark1;
    }
    public void setRemark1(java.lang.String remark1) {
        this.remark1 = remark1;
    }
    public java.math.BigDecimal getYearSumAmtLimit() {
        return yearSumAmtLimit;
    }
    public void setYearSumAmtLimit(java.math.BigDecimal yearSumAmtLimit) {
        this.yearSumAmtLimit = yearSumAmtLimit;
    }
    public java.lang.Integer getYearSumCountLimit() {
        return yearSumCountLimit;
    }
    public void setYearSumCountLimit(java.lang.Integer yearSumCountLimit) {
        this.yearSumCountLimit = yearSumCountLimit;
    }
    
    
    
}
