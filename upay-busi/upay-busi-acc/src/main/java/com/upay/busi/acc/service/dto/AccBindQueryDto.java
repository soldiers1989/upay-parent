package com.upay.busi.acc.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午10:09:34
 */
public class AccBindQueryDto extends BaseDto {
    private String userId;//用户id
    private List<Map<String,Object>> bindCardList;//绑定的卡集合
   // private List<AccBindInfoDto> bindCardList;//绑定的卡集合
    private int cardNum;//绑定卡数量
    private String routeCode;//资金通道
    private String vAcctNo;//账号
    private String queryFlag;//查询标志0：全部1：借记卡
   
    public String getQueryFlag() {
        return queryFlag;
    }
    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }
    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
    public String getvAcctNo() {
        return vAcctNo;
    }
    public void setvAcctNo(String vAcctNo) {
        this.vAcctNo = vAcctNo;
    }
    public String getUserId() {
        return userId;
    }
//    public List<AccBindInfoDto> getBindCardList() {
//        return bindCardList;
//    }
    public int getCardNum() {
        return cardNum;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
//    public void setBindCardList(List<AccBindInfoDto> bindCardList) {
//        this.bindCardList = bindCardList;
//    }
    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }
    public List<Map<String, Object>> getBindCardList() {
        return bindCardList;
    }
    public void setBindCardList(List<Map<String, Object>> bindCardList) {
        this.bindCardList = bindCardList;
    }
    @Override
    public String toString() {
        return "AccBindQueryDto [userId=" + userId + ", bindCardList=" + bindCardList + ", cardNum="
                + cardNum + ",vAcctNo=" + vAcctNo + ",routeCode=" + routeCode + "]";
    }
    
    
    
    
    
}
