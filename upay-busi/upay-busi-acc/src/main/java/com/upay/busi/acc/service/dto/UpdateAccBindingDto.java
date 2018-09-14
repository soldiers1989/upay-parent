/**
 * 
 */
package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年12月26日
 */
public class UpdateAccBindingDto extends BaseDto {
    private String accNo;
    private String bankAccNo;
    private String txSNBinding;
    private boolean txSNBindingNew;//
    
    
    public boolean isTxSNBindingNew() {
        return txSNBindingNew;
    }
    public void setTxSNBindingNew(boolean txSNBindingNew) {
        this.txSNBindingNew = txSNBindingNew;
    }
    public String getTxSNBinding() {
        return txSNBinding;
    }
    public void setTxSNBinding(String txSNBinding) {
        this.txSNBinding = txSNBinding;
    }
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    public String getBankAccNo() {
        return bankAccNo;
    }
    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }
    
    
    
}
