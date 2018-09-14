package com.upay.busi.pay.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;


/**
 * 获取渠道节点
 * 
 * @author liyulong
 * 
 */
public class ChannelLeafSearchDto extends BaseDto {
    private String channelId;// 支付渠道ID
    private String channelName;// 支付渠道
    private String channelLeaf;// 支付渠道菜单节点
    private String channelTopLeaf;// 节点的上层节点
    private String channelUrl;// 支付渠道前端模板路径
    private String channelFlag;// 渠道启用标志 01_启用 02_停用
    private String appChannelUrl;// app支付渠道前端模板路径
    private String channelLogo;// app渠道图标
    private List<Map<String, Object>> channelLeafSearchDtoList;
    private String merNo;
    
    

    public String getMerNo() {
		return merNo;
	}


	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}


	public String getChannelId() {
        return channelId;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public String getChannelName() {
        return channelName;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public String getChannelLeaf() {
        return channelLeaf;
    }


    public void setChannelLeaf(String channelLeaf) {
        this.channelLeaf = channelLeaf;
    }


    public String getChannelTopLeaf() {
        return channelTopLeaf;
    }


    public void setChannelTopLeaf(String channelTopLeaf) {
        this.channelTopLeaf = channelTopLeaf;
    }


    public String getChannelUrl() {
        return channelUrl;
    }


    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }


    public String getChannelFlag() {
        return channelFlag;
    }


    public void setChannelFlag(String channelFlag) {
        this.channelFlag = channelFlag;
    }


    public List<Map<String, Object>> getChannelLeafSearchDtoList() {
        return channelLeafSearchDtoList;
    }


    public void setChannelLeafSearchDtoList(List<Map<String, Object>> channelLeafSearchDtoList) {
        this.channelLeafSearchDtoList = channelLeafSearchDtoList;
    }


    public String getAppChannelUrl() {
        return appChannelUrl;
    }


    public void setAppChannelUrl(String appChannelUrl) {
        this.appChannelUrl = appChannelUrl;
    }


    public String getChannelLogo() {
        return channelLogo;
    }


    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }

}
