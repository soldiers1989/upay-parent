package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 绑定账户唯一性检查
 * 
 * @author liubing
 * 
 */
public class UniqueBindAccCheckDto extends BaseDto {
    /*
     * 绑定卡行号
     */
    private String eBindBankCode;
    /*
     * 绑定卡卡号
     */
    private String eBindAcctNo;


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

}
