/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author zhanggr
 *
 */
public class WeiXinMerNoToMerInfoResultDto extends BaseDto {
    private String resultCode;
    private String resultMsg;
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    
    
    

}
