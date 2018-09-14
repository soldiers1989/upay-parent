/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**微信返回字段判断
 * @author zhanggr
 *
 */
public class WebChatBackFlagChargeDto extends BaseDto{
    
    private String returnCode;
    private String resultCode;
    private String updateFlag;
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getUpdateFlag() {
        return updateFlag;
    }
    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }
    
    
    
    

}
