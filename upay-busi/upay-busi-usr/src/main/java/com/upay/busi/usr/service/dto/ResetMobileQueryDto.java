package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class ResetMobileQueryDto extends BaseDto {

    private String applyTime; // 申请时间
    private String approveStat; // 审批状态
    private String authApplyNo; // 事件申请编号
    private String approveTime; // 审批时间
    private String rejectReason; // 拒绝原因


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


    public String getApproveTime() {
        return approveTime;
    }


    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }


    public String getRejectReason() {
        return rejectReason;
    }


    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }


    public String getAuthApplyNo() {
        return authApplyNo;
    }


    public void setAuthApplyNo(String authApplyNo) {
        this.authApplyNo = authApplyNo;
    }
}
