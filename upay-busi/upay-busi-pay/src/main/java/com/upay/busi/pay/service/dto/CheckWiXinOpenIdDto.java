/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**校验微信下单的openid 即授权
 * @author zhanggr
 *
 */
public class CheckWiXinOpenIdDto extends BaseDto {
    private String openId;
    private String subOpenid;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSubOpenid() {
        return subOpenid;
    }

    public void setSubOpenid(String subOpenid) {
        this.subOpenid = subOpenid;
    }
    
    
    

}
