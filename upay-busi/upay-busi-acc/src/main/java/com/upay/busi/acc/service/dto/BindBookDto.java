/**
 * 
 */
package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;


/**
 * 电子账户绑卡
 * 
 * @author: liubing
 * @CreateDate:2015年4月8日
 * 
 */
public class BindBookDto extends BaseDto {
    private String txSNBinding;// 绑定中金渠道卡时生成的 流水号
    private String cardBinType;//卡BiN类型
    private String validDate;//YYMM格式
    private String cvn2;//信用卡后面三位安全码
    // 输入项

    private String vAcctNo;// 虚拟账户卡号
    private String bindAcctType;// 绑定账户类型
    private String bindChnlId;// 绑定渠道
    private String eBindBankFlag;// 绑定账户银行类别
    private String eBindOpenCode;// 绑定账户开户行名
    private String eBindBankCode;// 绑定账户行号
    private String eBindBankName;// 绑定账户行名
    private String eBindAcctNo;// 绑定账户账号
    private String eBindFlag;// 绑卡方式
    private String thirdAuthChnl;// 第三方鉴权渠道
    private BigDecimal transferVerifyAmt;// 打款验证金额
    private String reserveMobile;//预留手机号
 // 银行行号
    private String cnapsBankNo;

    private String cardBin;
    
 
    public String getValidDate() {
		return validDate;
	}


	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}


	public String getCvn2() {
		return cvn2;
	}


	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}


	public String getCardBinType() {
		return cardBinType;
	}


	public void setCardBinType(String cardBinType) {
		this.cardBinType = cardBinType;
	}


	
	public String getReserveMobile() {
		return reserveMobile;
	}


	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}


	public String getTxSNBinding() {
		return txSNBinding;
	}


	public void setTxSNBinding(String txSNBinding) {
		this.txSNBinding = txSNBinding;
	}


	public String getCardBin() {
        return cardBin;
    }


    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }


    public String getCnapsBankNo() {
        return cnapsBankNo;
    }


    public void setCnapsBankNo(String cnapsBankNo) {
        this.cnapsBankNo = cnapsBankNo;
    }


    public String getvAcctNo() {
        return vAcctNo;
    }


    public void setvAcctNo(String vAcctNo) {
        this.vAcctNo = vAcctNo;
    }


    public String geteBindBankFlag() {
        return eBindBankFlag;
    }


    public void seteBindBankFlag(String eBindBankFlag) {
        this.eBindBankFlag = eBindBankFlag;
    }


    public String geteBindOpenCode() {
        return eBindOpenCode;
    }


    public void seteBindOpenCode(String eBindOpenCode) {
        this.eBindOpenCode = eBindOpenCode;
    }


    public String getBindChnlId() {
        return bindChnlId;
    }


    public void setBindChnlId(String bindChnlId) {
        this.bindChnlId = bindChnlId;
    }


    public String geteBindBankName() {
        return eBindBankName;
    }


    public void seteBindBankName(String eBindBankName) {
        this.eBindBankName = eBindBankName;
    }


    public String getBindAcctType() {
        return bindAcctType;
    }


    public void setBindAcctType(String bindAcctType) {
        this.bindAcctType = bindAcctType;
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


    public BigDecimal getTransferVerifyAmt() {
        return transferVerifyAmt;
    }


    public void setTransferVerifyAmt(BigDecimal transferVerifyAmt) {
        this.transferVerifyAmt = transferVerifyAmt;
    }

}
