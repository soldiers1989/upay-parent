package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 用户电子账户开户标识查询
 * 
 * @author liubing
 * 
 */
public class VacctOpenFlagQueryDto extends BaseDto {

    /**
     * 开户标志
     */
    private String openAcctFlag;


    public String getOpenAcctFlag() {
        return openAcctFlag;
    }


    public void setOpenAcctFlag(String openAcctFlag) {
        this.openAcctFlag = openAcctFlag;
    }

}
