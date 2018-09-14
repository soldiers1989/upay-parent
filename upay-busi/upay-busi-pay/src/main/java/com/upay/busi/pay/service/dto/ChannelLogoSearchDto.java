package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;


/**
 * 获取渠道logo
 * 
 * @author liyulong
 * 
 */

public class ChannelLogoSearchDto extends BaseDto {
    private String channelId;// 渠道ID
    private String logoId;// 图标ID
    private String logoName;// 图标名
    private String logoClass;// 图标路径
    private BigDecimal channelTranslmt;// 渠道限额
    private List<Map<String, Object>> channelLogoSearchDtoList;


    public String getChannelId() {
        return channelId;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public String getLogoId() {
        return logoId;
    }


    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }


    public String getLogoName() {
        return logoName;
    }


    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }


    public String getLogoClass() {
        return logoClass;
    }


    public void setLogoClass(String logoClass) {
        this.logoClass = logoClass;
    }


    public BigDecimal getChannelTranslmt() {
        return channelTranslmt;
    }


    public void setChannelTranslmt(BigDecimal channelTranslmt) {
        this.channelTranslmt = channelTranslmt;
    }


    public List<Map<String, Object>> getChannelLogoSearchDtoList() {
        return channelLogoSearchDtoList;
    }


    public void setChannelLogoSearchDtoList(List<Map<String, Object>> channelLogoSearchDtoList) {
        this.channelLogoSearchDtoList = channelLogoSearchDtoList;
    }

}
