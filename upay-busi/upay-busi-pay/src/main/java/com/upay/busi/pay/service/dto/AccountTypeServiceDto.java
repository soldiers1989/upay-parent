/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.io.Serializable;

/**
 * @author shang
 * 2016年10月14日
 */
public class AccountTypeServiceDto extends BaseDto implements Serializable {


   private  String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
