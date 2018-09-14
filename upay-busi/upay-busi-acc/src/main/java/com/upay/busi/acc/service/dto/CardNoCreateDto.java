package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 电子账户开户生成电子账户卡号
 * 
 * @author:liubing
 * @CreateDate:2015年6月16日
 * 
 */
public class CardNoCreateDto extends BaseDto {

    // 输出项
    private String eCardNo;// 电子账户账号


    public String geteCardNo() {
        return eCardNo;
    }


    public void seteCardNo(String eCardNo) {
        this.eCardNo = eCardNo;
    }

}
