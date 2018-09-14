package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;


/**
 * 电子账户开户
 * 
 * @author: liubing
 * @CreateDate:2015年4月8日
 * 
 */
public class AccVBookOpenDto extends BaseDto {
    private String certName;// 实名认证姓名
    private String eAcctCertLevel;// 用户实名认证等级
    private String bindAcctType;// 绑定账户类型
    private String eBindOpenCode;// 绑定账户开户行名
    private String eBindBankCode;// 绑定卡行号
    private String eBindBankName;// 绑定账户行名
    private String eBindAcctNo;// 绑定卡账户账号
    private String eOpenFlag;// 开户原因
    private String eBindFlag;// 绑卡方式
    private String eBindBankFlag;// 电子账户绑定账户银行类别
    private String thirdAuthChnl;// 第三方鉴权渠道
    private BigDecimal otherVerifyAmt;// 打款验证金额
    private String eCardNo;// 电子账户卡号

    // 输出项
    private String vAcctNo;// 虚拟账户账号
    private String ccy;// 币种


    public String getCertName() {
        return certName;
    }


    public void setCertName(String certName) {
        this.certName = certName;
    }


    public String getvAcctNo() {
        return vAcctNo;
    }


    public void setvAcctNo(String vAcctNo) {
        this.vAcctNo = vAcctNo;
    }


    public String geteBindOpenCode() {
        return eBindOpenCode;
    }


    public String geteBindBankName() {
        return eBindBankName;
    }


    public void seteBindBankName(String eBindBankName) {
        this.eBindBankName = eBindBankName;
    }


    public String geteBindBankFlag() {
        return eBindBankFlag;
    }


    public void seteBindBankFlag(String eBindBankFlag) {
        this.eBindBankFlag = eBindBankFlag;
    }


    public void seteBindOpenCode(String eBindOpenCode) {
        this.eBindOpenCode = eBindOpenCode;
    }


    public String geteAcctCertLevel() {
        return eAcctCertLevel;
    }


    public void seteAcctCertLevel(String eAcctCertLevel) {
        this.eAcctCertLevel = eAcctCertLevel;
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


    public String geteOpenFlag() {
        return eOpenFlag;
    }


    public void seteOpenFlag(String eOpenFlag) {
        this.eOpenFlag = eOpenFlag;
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


    public BigDecimal getOtherVerifyAmt() {
        return otherVerifyAmt;
    }


    public void setOtherVerifyAmt(BigDecimal otherVerifyAmt) {
        this.otherVerifyAmt = otherVerifyAmt;
    }


    public String geteCardNo() {
        return eCardNo;
    }


    public void seteCardNo(String eCardNo) {
        this.eCardNo = eCardNo;
    }


    public String getCcy() {
        return ccy;
    }


    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

}
