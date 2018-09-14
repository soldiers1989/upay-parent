package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午9:36:19
 */
public class AccOpenStatusDto extends BaseDto {
    private String openAccFlag;//开户标志
    private int countOfBindCard;//绑定的卡数
    private String userId;//用户id
    public String getOpenAccFlag() {
        return openAccFlag;
    }
    public int getCountOfBindCard() {
        return countOfBindCard;
    }
    public String getUserId() {
        return userId;
    }
    public void setOpenAccFlag(String openAccFlag) {
        this.openAccFlag = openAccFlag;
    }
    public void setCountOfBindCard(int countOfBindCard) {
        this.countOfBindCard = countOfBindCard;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "AccOpenStatusDto [openAccFlag=" + openAccFlag + ", countOfBindCard=" + countOfBindCard
                + ", userId=" + userId + "]";
    }
    
    
    
}
