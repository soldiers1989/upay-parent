package com.upay.busi.pay.service.dto;

import java.io.Serializable;

import com.upay.commons.dto.BaseDto;


/**
 * 类或接口的功能说明 类或接口的使用说明:通过卡bin查询银行信息
 * 
 * @author: liubing
 * @CreateDate:2015年4月13日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2015年4月13日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class PayQueryCardBinOfBankDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cardBinType;//卡bin类型

    /**
     * 绑定卡号 db_column: E_BIND_ACCT_NO
     */
    private java.lang.String eBindAcctNo;

    /**
	 * 
	 */

    /**
     * 卡名称 db_column: CARD_BIN_NAME
     */
    private String cardBinName;

    /**
     * 银行标识 db_column: BANK_BIN_FLAG
     */
    private String bankBinFlag;

    /**
     * 银联机构行号 db_column: CUP_BANK_NO
     */
    private String cupBankNo;

    /**
     * 银联机构名称 db_column: CUP_BANK_NAME
     */
    private String cupBankName;

    // 银行行号
    private String cnapsBankNo;

    /** 卡BIN */
    private String cardBin;
    
    private String logoId;

    
    public String getLogoId() {
		return logoId;
	}


	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}


	public String getCardBinType() {
		return cardBinType;
	}


	public void setCardBinType(String cardBinType) {
		this.cardBinType = cardBinType;
	}


	public String getCnapsBankNo() {
        return cnapsBankNo;
    }


    public void setCnapsBankNo(String cnapsBankNo) {
        this.cnapsBankNo = cnapsBankNo;
    }


    public String getCardBinName() {
        return cardBinName;
    }


    public void setCardBinName(String cardBinName) {
        this.cardBinName = cardBinName;
    }


    public String getBankBinFlag() {
        return bankBinFlag;
    }


    public void setBankBinFlag(String bankBinFlag) {
        this.bankBinFlag = bankBinFlag;
    }


    public String getCupBankNo() {
        return cupBankNo;
    }


    public void setCupBankNo(String cupBankNo) {
        this.cupBankNo = cupBankNo;
    }


    public String getCupBankName() {
        return cupBankName;
    }


    public void setCupBankName(String cupBankName) {
        this.cupBankName = cupBankName;
    }


    public java.lang.String geteBindAcctNo() {
        return eBindAcctNo;
    }


    public void seteBindAcctNo(java.lang.String eBindAcctNo) {
        this.eBindAcctNo = eBindAcctNo;
    }


    public String getCardBin() {
        return cardBin;
    }


    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }
}
