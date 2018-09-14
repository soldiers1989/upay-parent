package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 电子账户变更绑定卡前检查
 * 
 * @author liyulong
 * 
 */
public class BindChgCheckDto extends BaseDto {
    /*
     * 虚拟账户账号
     */
    private String vAcctNo;


    public String getvAcctNo() {
        return vAcctNo;
    }


    public void setvAcctNo(String vAcctNo) {
        this.vAcctNo = vAcctNo;
    }

}
