package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午10:52:29
 */
public class AccBindInfoDto extends BaseDto {
    /**
     * 
     */
   // private static final long serialVersionUID = -8617019852354557070L;
    private java.lang.String bindTime;
    private java.lang.String bindChnlId;
    private java.lang.String vacctNo;
    private java.lang.String defaultFlag;
    private java.lang.String bindAcctType;
    private java.lang.String vbindBankFlag;
    private java.lang.String vbindBankCode;
    private java.lang.String vbindBankName;
    private java.lang.String vbindAcctNo;
    private java.lang.String vbindOpenCode;
    private java.lang.String bindStat;
    private java.lang.String vbindFlag;
    private java.lang.String thirdAuthChnl;
    private java.lang.String transferVerifyAmt;
    private java.lang.String transferVerifyDate;
    private java.lang.String activeTime;
    private java.lang.String unbindTime;
    private java.lang.String unbindChnlId;
    private java.lang.String unbindReasonFlag;
    
    private String logoName;//图标名称
    private String logoClass;//图标路径
    private String chnnelTransLmt;//渠道限额 
    private String routeCode;//渠道限额 
    private String cardBin;//卡bin
    private String bankBinFlag;//行内行外标志
    
    
    
    
    public String getBankBinFlag() {
        return bankBinFlag;
    }
    public void setBankBinFlag(String bankBinFlag) {
        this.bankBinFlag = bankBinFlag;
    }
    public String getCardBin() {
        return cardBin;
    }
    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }
    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
    public String getLogoName() {
        return logoName;
    }
    public String getLogoClass() {
        return logoClass;
    }
    public String getChnnelTransLmt() {
        return chnnelTransLmt;
    }
    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }
    public void setLogoClass(String logoClass) {
        this.logoClass = logoClass;
    }
    public void setChnnelTransLmt(String chnnelTransLmt) {
        this.chnnelTransLmt = chnnelTransLmt;
    }
    public java.lang.String getBindTime() {
        return bindTime;
    }
    public java.lang.String getBindChnlId() {
        return bindChnlId;
    }
    public java.lang.String getVacctNo() {
        return vacctNo;
    }
    public java.lang.String getDefaultFlag() {
        return defaultFlag;
    }
    public java.lang.String getBindAcctType() {
        return bindAcctType;
    }
    public java.lang.String getVbindBankFlag() {
        return vbindBankFlag;
    }
    public java.lang.String getVbindBankCode() {
        return vbindBankCode;
    }
    public java.lang.String getVbindBankName() {
        return vbindBankName;
    }
    public java.lang.String getVbindAcctNo() {
        return vbindAcctNo;
    }
    public java.lang.String getVbindOpenCode() {
        return vbindOpenCode;
    }
    public java.lang.String getBindStat() {
        return bindStat;
    }
    public java.lang.String getVbindFlag() {
        return vbindFlag;
    }
    public java.lang.String getThirdAuthChnl() {
        return thirdAuthChnl;
    }
    public java.lang.String getTransferVerifyAmt() {
        return transferVerifyAmt;
    }
    public java.lang.String getTransferVerifyDate() {
        return transferVerifyDate;
    }
    public java.lang.String getActiveTime() {
        return activeTime;
    }
    public java.lang.String getUnbindTime() {
        return unbindTime;
    }
    public java.lang.String getUnbindChnlId() {
        return unbindChnlId;
    }
    public java.lang.String getUnbindReasonFlag() {
        return unbindReasonFlag;
    }
    public void setBindTime(java.lang.String bindTime) {
        this.bindTime = bindTime;
    }
    public void setBindChnlId(java.lang.String bindChnlId) {
        this.bindChnlId = bindChnlId;
    }
    public void setVacctNo(java.lang.String vacctNo) {
        this.vacctNo = vacctNo;
    }
    public void setDefaultFlag(java.lang.String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }
    public void setBindAcctType(java.lang.String bindAcctType) {
        this.bindAcctType = bindAcctType;
    }
    public void setVbindBankFlag(java.lang.String vbindBankFlag) {
        this.vbindBankFlag = vbindBankFlag;
    }
    public void setVbindBankCode(java.lang.String vbindBankCode) {
        this.vbindBankCode = vbindBankCode;
    }
    public void setVbindBankName(java.lang.String vbindBankName) {
        this.vbindBankName = vbindBankName;
    }
    public void setVbindAcctNo(java.lang.String vbindAcctNo) {
        this.vbindAcctNo = vbindAcctNo;
    }
    public void setVbindOpenCode(java.lang.String vbindOpenCode) {
        this.vbindOpenCode = vbindOpenCode;
    }
    public void setBindStat(java.lang.String bindStat) {
        this.bindStat = bindStat;
    }
    public void setVbindFlag(java.lang.String vbindFlag) {
        this.vbindFlag = vbindFlag;
    }
    public void setThirdAuthChnl(java.lang.String thirdAuthChnl) {
        this.thirdAuthChnl = thirdAuthChnl;
    }
    public void setTransferVerifyAmt(java.lang.String transferVerifyAmt) {
        this.transferVerifyAmt = transferVerifyAmt;
    }
    public void setTransferVerifyDate(java.lang.String transferVerifyDate) {
        this.transferVerifyDate = transferVerifyDate;
    }
    public void setActiveTime(java.lang.String activeTime) {
        this.activeTime = activeTime;
    }
    public void setUnbindTime(java.lang.String unbindTime) {
        this.unbindTime = unbindTime;
    }
    public void setUnbindChnlId(java.lang.String unbindChnlId) {
        this.unbindChnlId = unbindChnlId;
    }
    public void setUnbindReasonFlag(java.lang.String unbindReasonFlag) {
        this.unbindReasonFlag = unbindReasonFlag;
    }
    @Override
    public String toString() {
        return "AccBindInfoDto [bindTime=" + bindTime + ", bindChnlId=" + bindChnlId + ", vacctNo=" + vacctNo
                + ", defaultFlag=" + defaultFlag + ", bindAcctType=" + bindAcctType + ", vbindBankFlag="
                + vbindBankFlag + ", vbindBankCode=" + vbindBankCode + ", vbindBankName=" + vbindBankName
                + ", vbindAcctNo=" + vbindAcctNo + ", vbindOpenCode=" + vbindOpenCode + ", bindStat="
                + bindStat + ", vbindFlag=" + vbindFlag + ", thirdAuthChnl=" + thirdAuthChnl
                + ", transferVerifyAmt=" + transferVerifyAmt + ", transferVerifyDate=" + transferVerifyDate
                + ", activeTime=" + activeTime + ", unbindTime=" + unbindTime + ", unbindChnlId="
                + unbindChnlId + ", unbindReasonFlag=" + unbindReasonFlag + ", logoName=" + logoName
                + ", logoClass=" + logoClass + ", chnnelTransLmt=" + chnnelTransLmt + ",routeCode=" + routeCode + ""
                        + ",cardBin=" + cardBin + ",bankBinFlag=" + bankBinFlag + "]";
        
         
    }
    
   
    
    
    
    
    
    
    
}
