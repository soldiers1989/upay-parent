/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**微信支付开通之后更新微信特约商户识别码到商户信息表和商户审批表
 * @author zhanggr
 *
 */
public class UpdateWeiXinMerNoToMerInfoDto extends BaseDto {
    private String merNo;//商户号
    private String subMchId;//商户识别码
   
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
