/**
 * 
 */
package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 绑定账户解绑
 * 
 * @author: liyulong
 * 
 */
public class AccUnbindQueryDto extends BaseDto {

	 private String oldGateWay_zjpay_sysSeq;
	 
    private String vAcctNo;// 虚拟账户账号
    private String bindAcctType;// 绑定账户类型
    private String vBindBankCode;// 绑定卡行号
    private String vBindAcctNo;// 绑定卡账户账号

    /**
     * 电子账户绑定账户银行类别(1：本行账户 2：他行账户)
     */
    private String vbindBankFlag;
    
    
   


	public String getVbindBankFlag() {
		return vbindBankFlag;
	}


	public void setVbindBankFlag(String vbindBankFlag) {
		this.vbindBankFlag = vbindBankFlag;
	}


	public String getOldGateWay_zjpay_sysSeq() {
		return oldGateWay_zjpay_sysSeq;
	}


	public void setOldGateWay_zjpay_sysSeq(String oldGateWay_zjpay_sysSeq) {
		this.oldGateWay_zjpay_sysSeq = oldGateWay_zjpay_sysSeq;
	}


	public String getvAcctNo() {
        return vAcctNo;
    }


    public void setvAcctNo(String vAcctNo) {
        this.vAcctNo = vAcctNo;
    }


    public String getBindAcctType() {
        return bindAcctType;
    }


    public void setBindAcctType(String bindAcctType) {
        this.bindAcctType = bindAcctType;
    }


    public String getvBindBankCode() {
        return vBindBankCode;
    }


    public void setvBindBankCode(String vBindBankCode) {
        this.vBindBankCode = vBindBankCode;
    }


    public String getvBindAcctNo() {
        return vBindAcctNo;
    }


    public void setvBindAcctNo(String vBindAcctNo) {
        this.vBindAcctNo = vBindAcctNo;
    }

}
