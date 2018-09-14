/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**删除微信特约商户，校验商户信息
 * @author zhanggr
 *
 */
public class ChkWeiXinMerDelInfoDto extends BaseDto {
    
    private String subMchId;//商户识别码
    private String merNo;//商户号
    
    

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }
   
    

}
