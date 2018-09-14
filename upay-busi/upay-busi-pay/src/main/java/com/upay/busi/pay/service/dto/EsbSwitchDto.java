/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author liudan
 * 2016年10月14日
 */
public class EsbSwitchDto extends BaseDto implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //临时记录 流水号
    private String tempTransSubSeq;
    private String transSubSeq;



    //二类户  所需参数
    private String agntFlg;
    //收款方卡号-代收     付款方卡号-代付
    private String cntrprtAcctNo;
    //付款方姓名-代付  收款方姓名-代收
    private String oppCstNm;




    //付款方  账号类型    核心
    private String acctNoDataSrcFlg;


    //收款方  账号类型    核心
    private String AcctNoSrc;

    //代扣    收款方和卡号类型    姓名
    private String payeeAccountNo;
    private String payeeAccountType;
    private String payeeAccountName;


    //代付    付款方和卡号类型    姓名
    private String payerAccountNo;
    private String payerAccountType;
    private String payerAccountName;




    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public String getPayeeAccountName() {
        return payeeAccountName;
    }

    public void setPayeeAccountName(String payeeAccountName) {
        this.payeeAccountName = payeeAccountName;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayerAccountType() {
        return payerAccountType;
    }

    public void setPayerAccountType(String payerAccountType) {
        this.payerAccountType = payerAccountType;
    }

    public String getPayerAccountName() {
        return payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName;
    }

    //付款方  账号类型 ---代扣    收款方-代付
    private String accountType;
    private String acctName;
    private String  acctNo;;









    public String getAgntFlg() {
        return agntFlg;
    }

    public void setAgntFlg(String agntFlg) {
        this.agntFlg = agntFlg;
    }

    public String getCntrprtAcctNo() {
        return cntrprtAcctNo;
    }

    public void setCntrprtAcctNo(String cntrprtAcctNo) {
        this.cntrprtAcctNo = cntrprtAcctNo;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getOppCstNm() {
        return oppCstNm;
    }

    public void setOppCstNm(String oppCstNm) {
        this.oppCstNm = oppCstNm;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAcctNoDataSrcFlg() {
        return acctNoDataSrcFlg;
    }

    public void setAcctNoDataSrcFlg(String acctNoDataSrcFlg) {
        this.acctNoDataSrcFlg = acctNoDataSrcFlg;
    }


    public String getAcctNoSrc() {
        return AcctNoSrc;
    }


    public void setAcctNoSrc(String acctNoSrc) {
        AcctNoSrc = acctNoSrc;
    }

    public String getPayeeAccountType() {
        return payeeAccountType;
    }

    public void setPayeeAccountType(String payeeAccountType) {
        this.payeeAccountType = payeeAccountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getTempTransSubSeq() {
        return tempTransSubSeq;
    }

    public void setTempTransSubSeq(String tempTransSubSeq) {
        this.tempTransSubSeq = tempTransSubSeq;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }
}
