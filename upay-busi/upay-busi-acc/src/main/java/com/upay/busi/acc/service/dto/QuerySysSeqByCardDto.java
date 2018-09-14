package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 
 */

/**
 * 绑定账户解绑
 * 
 * @author: liubing
 * @CreateDate:2015年4月10日
 * 
 */
public class QuerySysSeqByCardDto extends BaseDto {

    private String oldGateWay_zjpay_sysSeq;
    // 输入项
    // private String eCardNo;// 电子账户卡号
    // private String bindAcctType;// 绑定账户类型
    // private String eBindBankCode;// 绑定卡行号
    private String vbindAcctNo;// 绑定卡账户账号
    private String vAcctNo;


    public String getvAcctNo() {
		return vAcctNo;
	}


	public void setvAcctNo(String vAcctNo) {
		this.vAcctNo = vAcctNo;
	}


	public String getOldGateWay_zjpay_sysSeq() {
        return oldGateWay_zjpay_sysSeq;
    }


    public void setOldGateWay_zjpay_sysSeq(String oldGateWay_zjpay_sysSeq) {
        this.oldGateWay_zjpay_sysSeq = oldGateWay_zjpay_sysSeq;
    }


    public String getVbindAcctNo() {
        return vbindAcctNo;
    }


    public void setVbindAcctNo(String vbindAcctNo) {
        this.vbindAcctNo = vbindAcctNo;
    }

}
