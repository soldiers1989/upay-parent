/**
 * 
 */
package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;


/**
 * 激活电子账户（打款验证）
 * 
 * @author:liubing
 * @CreateDate:2015年4月13日
 * 
 */
public class TransferActiveDto extends BaseDto {

    private String eAcctStat;// 账户状态
    private String eBindBankCode;// 待绑定卡行号
    private String eBindAcctNo;// 待绑定卡号
    private BigDecimal transAmt;// 打款金额
    private String eBindFlag;// 绑卡方式
    private String thirdAuthChnl;// 第三方鉴权渠道
    private String eBindOpenCode;// 绑定账户开户行名
    private String eBindBankName;// 绑定账户行名
    private String eOpenFlag;// 开户原因
    private String eBindBankFlag;// 银行卡标志
    
    private String cnapsBankNo;// 银行行号
    
	private String cardBinType;//银行卡类型

    public java.lang.String getCardBinType() {
		return this.cardBinType;
	}
	
	public void setCardBinType(java.lang.String value) {
		this.cardBinType = value;
	}

    public String getCnapsBankNo() {
        return cnapsBankNo;
    }


    public void setCnapsBankNo(String cnapsBankNo) {
        this.cnapsBankNo = cnapsBankNo;
    }


    public String geteAcctStat() {
        return eAcctStat;
    }


    public void seteAcctStat(String eAcctStat) {
        this.eAcctStat = eAcctStat;
    }


    public String geteBindBankCode() {
        return eBindBankCode;
    }


    public void seteBindBankCode(String eBindBankCode) {
        this.eBindBankCode = eBindBankCode;
    }


    public String geteBindAcctNo() {
        return eBindAcctNo;
    }


    public void seteBindAcctNo(String eBindAcctNo) {
        this.eBindAcctNo = eBindAcctNo;
    }


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public String geteBindFlag() {
        return eBindFlag;
    }


    public void seteBindFlag(String eBindFlag) {
        this.eBindFlag = eBindFlag;
    }


    public String getThirdAuthChnl() {
        return thirdAuthChnl;
    }


    public void setThirdAuthChnl(String thirdAuthChnl) {
        this.thirdAuthChnl = thirdAuthChnl;
    }


    public String geteBindOpenCode() {
        return eBindOpenCode;
    }


    public void seteBindOpenCode(String eBindOpenCode) {
        this.eBindOpenCode = eBindOpenCode;
    }


    public String geteBindBankName() {
        return eBindBankName;
    }


    public void seteBindBankName(String eBindBankName) {
        this.eBindBankName = eBindBankName;
    }


    public String geteOpenFlag() {
        return eOpenFlag;
    }


    public void seteOpenFlag(String eOpenFlag) {
        this.eOpenFlag = eOpenFlag;
    }


    public String geteBindBankFlag() {
        return eBindBankFlag;
    }


    public void seteBindBankFlag(String eBindBankFlag) {
        this.eBindBankFlag = eBindBankFlag;
    }

}
