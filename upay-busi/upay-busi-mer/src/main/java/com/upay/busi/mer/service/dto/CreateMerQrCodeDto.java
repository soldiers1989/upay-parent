/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**生成微信公众号商户二维码
 * @author zhanggr
 *
 */
public class CreateMerQrCodeDto extends BaseDto {
    
    private String filePath;//存储路径
    private String merNo;//商户号
    private String content;//内容
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
   
    
    
    

}
