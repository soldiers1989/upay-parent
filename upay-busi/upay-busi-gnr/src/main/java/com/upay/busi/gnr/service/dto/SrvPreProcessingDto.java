package com.upay.busi.gnr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 接口服务前处理数据传输结构
 * @author freeplato
 *
 */
public class SrvPreProcessingDto extends BaseDto {

    private String merNo;//商户号
    private String platform;//登陆平台
    
    private String platformUserNo;

    
    
    public String getPlatformUserNo() {
        return platformUserNo;
    }

    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    
    

}
