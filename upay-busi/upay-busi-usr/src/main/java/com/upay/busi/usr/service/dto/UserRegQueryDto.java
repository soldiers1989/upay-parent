package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午11:01:36
 */
public class UserRegQueryDto extends BaseDto {
    private String loginMode;// 用户登录方式

    private String certType;
    private String certNo;
    private String merNo;
    private String email;

    private java.lang.String mobile;
    private java.lang.String comEmail;
    private java.lang.String userName;
    private java.lang.String userNickName;
    private java.lang.String headPic;
    private java.lang.String loginPwd;
    private java.lang.String lastLogpwdModifytime;
    private java.lang.String tradePwd;
    private java.lang.String lastTradepwdModifytime;
    private java.lang.String gasturePwd;
    private java.lang.String lastGasturepwdModifytime;
    private java.lang.String regTime;
    private java.lang.String regChnlId;
    private java.lang.String userCertLevel;
    private java.lang.String userValueLevel;
    private java.lang.String branchId;
    private java.lang.String userStat;
    private java.lang.String userLockFlag;
    private java.lang.String blacklistFlag;
    private java.lang.String blacklistType;
    private java.lang.String securityType;
    private java.lang.String activeTime;
    private java.lang.String activeChnlId;
    private java.lang.String closeTime;
    private java.lang.String closeChnlId;
    private java.lang.String lastLoginTime;
    private java.lang.String lastUpdateTime;
    private java.lang.String unionPlatType;
    private java.lang.String unionPlatNo;
    private java.lang.String regType;
    private String payPwdFlag;
    private String merLevel;
    /**
     * 认证类型 01：免密授权 02：普通会员 03：商户会员 db_column: APPROVE_TYPE
     */
    private java.lang.String approveType;


    public java.lang.String getApproveType() {
        return approveType;
    }


    public void setApproveType(java.lang.String approveType) {
        this.approveType = approveType;
    }


    public String getPayPwdFlag() {
        return payPwdFlag;
    }


    public void setPayPwdFlag(String payPwdFlag) {
        this.payPwdFlag = payPwdFlag;
    }


    public java.lang.String getComEmail() {
        return comEmail;
    }


    public void setComEmail(java.lang.String comEmail) {
        this.comEmail = comEmail;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getLoginMode() {
        return loginMode;
    }


    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
    }


    public String getCertType() {
        return certType;
    }


    public String getCertNo() {
        return certNo;
    }


    public java.lang.String getMobile() {
        return mobile;
    }


    public java.lang.String getUserName() {
        return userName;
    }


    public java.lang.String getUserNickName() {
        return userNickName;
    }


    public java.lang.String getHeadPic() {
        return headPic;
    }


    public java.lang.String getLoginPwd() {
        return loginPwd;
    }


    public java.lang.String getLastLogpwdModifytime() {
        return lastLogpwdModifytime;
    }


    public java.lang.String getTradePwd() {
        return tradePwd;
    }


    public java.lang.String getLastTradepwdModifytime() {
        return lastTradepwdModifytime;
    }


    public java.lang.String getGasturePwd() {
        return gasturePwd;
    }


    public java.lang.String getLastGasturepwdModifytime() {
        return lastGasturepwdModifytime;
    }


    public java.lang.String getRegTime() {
        return regTime;
    }


    public java.lang.String getRegChnlId() {
        return regChnlId;
    }


    public java.lang.String getUserCertLevel() {
        return userCertLevel;
    }


    public java.lang.String getUserValueLevel() {
        return userValueLevel;
    }


    public java.lang.String getBranchId() {
        return branchId;
    }


    public java.lang.String getUserStat() {
        return userStat;
    }


    public java.lang.String getUserLockFlag() {
        return userLockFlag;
    }


    public java.lang.String getBlacklistFlag() {
        return blacklistFlag;
    }


    public java.lang.String getBlacklistType() {
        return blacklistType;
    }


    public java.lang.String getSecurityType() {
        return securityType;
    }


    public java.lang.String getActiveTime() {
        return activeTime;
    }


    public java.lang.String getActiveChnlId() {
        return activeChnlId;
    }


    public java.lang.String getCloseTime() {
        return closeTime;
    }


    public java.lang.String getCloseChnlId() {
        return closeChnlId;
    }


    public java.lang.String getLastLoginTime() {
        return lastLoginTime;
    }


    public java.lang.String getLastUpdateTime() {
        return lastUpdateTime;
    }


    public java.lang.String getUnionPlatType() {
        return unionPlatType;
    }


    public java.lang.String getUnionPlatNo() {
        return unionPlatNo;
    }


    public java.lang.String getRegType() {
        return regType;
    }


    public void setCertType(String certType) {
        this.certType = certType;
    }


    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }


    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    public void setUserNickName(java.lang.String userNickName) {
        this.userNickName = userNickName;
    }


    public void setHeadPic(java.lang.String headPic) {
        this.headPic = headPic;
    }


    public void setLoginPwd(java.lang.String loginPwd) {
        this.loginPwd = loginPwd;
    }


    public void setLastLogpwdModifytime(java.lang.String lastLogpwdModifytime) {
        this.lastLogpwdModifytime = lastLogpwdModifytime;
    }


    public void setTradePwd(java.lang.String tradePwd) {
        this.tradePwd = tradePwd;
    }


    public void setLastTradepwdModifytime(java.lang.String lastTradepwdModifytime) {
        this.lastTradepwdModifytime = lastTradepwdModifytime;
    }


    public void setGasturePwd(java.lang.String gasturePwd) {
        this.gasturePwd = gasturePwd;
    }


    public void setLastGasturepwdModifytime(java.lang.String lastGasturepwdModifytime) {
        this.lastGasturepwdModifytime = lastGasturepwdModifytime;
    }


    public void setRegTime(java.lang.String regTime) {
        this.regTime = regTime;
    }


    public void setRegChnlId(java.lang.String regChnlId) {
        this.regChnlId = regChnlId;
    }


    public void setUserCertLevel(java.lang.String userCertLevel) {
        this.userCertLevel = userCertLevel;
    }


    public void setUserValueLevel(java.lang.String userValueLevel) {
        this.userValueLevel = userValueLevel;
    }


    public void setBranchId(java.lang.String branchId) {
        this.branchId = branchId;
    }


    public void setUserStat(java.lang.String userStat) {
        this.userStat = userStat;
    }


    public void setUserLockFlag(java.lang.String userLockFlag) {
        this.userLockFlag = userLockFlag;
    }


    public void setBlacklistFlag(java.lang.String blacklistFlag) {
        this.blacklistFlag = blacklistFlag;
    }


    public void setBlacklistType(java.lang.String blacklistType) {
        this.blacklistType = blacklistType;
    }


    public void setSecurityType(java.lang.String securityType) {
        this.securityType = securityType;
    }


    public void setActiveTime(java.lang.String activeTime) {
        this.activeTime = activeTime;
    }


    public void setActiveChnlId(java.lang.String activeChnlId) {
        this.activeChnlId = activeChnlId;
    }


    public void setCloseTime(java.lang.String closeTime) {
        this.closeTime = closeTime;
    }


    public void setCloseChnlId(java.lang.String closeChnlId) {
        this.closeChnlId = closeChnlId;
    }


    public void setLastLoginTime(java.lang.String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public void setLastUpdateTime(java.lang.String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public void setUnionPlatType(java.lang.String unionPlatType) {
        this.unionPlatType = unionPlatType;
    }


    public void setUnionPlatNo(java.lang.String unionPlatNo) {
        this.unionPlatNo = unionPlatNo;
    }


    public void setRegType(java.lang.String regType) {
        this.regType = regType;
    }


    public String getMerLevel() {
        return merLevel;
    }


    public void setMerLevel(String merLevel) {
        this.merLevel = merLevel;
    }


    @Override
    public String toString() {
        return "UserRegQueryDto [certType=" + certType + ", certNo=" + certNo + ", mobile=" + mobile
                + ", userName=" + userName + ", userNickName=" + userNickName + ", headPic=" + headPic
                + ", loginPwd=" + loginPwd + ", lastLogpwdModifytime=" + lastLogpwdModifytime + ", tradePwd="
                + tradePwd + ", lastTradepwdModifytime=" + lastTradepwdModifytime + ", gasturePwd="
                + gasturePwd + ", lastGasturepwdModifytime=" + lastGasturepwdModifytime + ", regTime="
                + regTime + ", regChnlId=" + regChnlId + ", userCertLevel=" + userCertLevel
                + ", userValueLevel=" + userValueLevel + ", branchId=" + branchId + ", userStat=" + userStat
                + ", userLockFlag=" + userLockFlag + ", blacklistFlag=" + blacklistFlag + ", blacklistType="
                + blacklistType + ", securityType=" + securityType + ", activeTime=" + activeTime
                + ", activeChnlId=" + activeChnlId + ", closeTime=" + closeTime + ", closeChnlId="
                + closeChnlId + ", lastLoginTime=" + lastLoginTime + ", lastUpdateTime=" + lastUpdateTime
                + ", unionPlatType=" + unionPlatType + ", unionPlatNo=" + unionPlatNo + ", regType="
                + regType + "]";
    }

}
