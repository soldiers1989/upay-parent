/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 微信初始化字段
 * @author zhanggr
 *
 */
public class WebChatChangeInitDto extends BaseDto {
    private String transSubSeq;
    private String body;
    public String getTransSubSeq() {
        return transSubSeq;
    }
    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    

}
