package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 交易密码重置申请结果查询
 * 
 * @author liyulong
 * 
 */
public class ResetPwdQueryDto extends BaseDto {
    /** 申请时间 */
    private String applyTime;

    /** 审批状态 */
    private String approveStat;

    /** 审批未通过的原因 */
    private String rejectReason;


    public String getApplyTime() {
        return applyTime;
    }


    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }


    public String getApproveStat() {
        return approveStat;
    }


    public void setApproveStat(String approveStat) {
        this.approveStat = approveStat;
    }


    public String getRejectReason() {
        return rejectReason;
    }


    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

}
