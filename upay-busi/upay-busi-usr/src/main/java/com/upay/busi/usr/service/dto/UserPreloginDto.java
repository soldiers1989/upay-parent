package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * @author shangqiankun
 * @version 创建时间：2016年7月20日 上午11:26:46
 */
public class UserPreloginDto extends BaseDto {
    private String userName;//用户登录名
//    private String userId;//用户id
    private String mobile;//手机号
    private String certNo;//证件号
    private String platformType;//合作平台类型
    private String platformUserNo;//合作平台用户id
    private String userStatus;//用户状态
    private String email;
    
    
    private String loginMode;//登录方式
    private String isMutiplueLogin;//是否多点登录
    private String sessionId;//session ID
    private String loginAddr;//用户登录地(经纬度)
    private String loginIp;//登录ip
    private String addrGetFlag;//地址转换标志
    private String addrProv;//登录省份
    private String addrCity;//登录城市
    private String addrCounty;//登录县
    private String addrDetail;//登录详细地址
    private String loginStat;//用户登录状态
    
	public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserStatus() {
        return userStatus;
    }
    public String getLoginStat() {
        return loginStat;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public void setLoginStat(String loginStat) {
        this.loginStat = loginStat;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPlatformType() {
        return platformType;
    }
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }
    public String getLoginMode() {
        return loginMode;
    }
    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
    }
    public String getIsMutiplueLogin() {
        return isMutiplueLogin;
    }
    public void setIsMutiplueLogin(String isMutiplueLogin) {
        this.isMutiplueLogin = isMutiplueLogin;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getLoginAddr() {
        return loginAddr;
    }
    public void setLoginAddr(String loginAddr) {
        this.loginAddr = loginAddr;
    }
    public String getLoginIp() {
        return loginIp;
    }
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    public String getAddrGetFlag() {
        return addrGetFlag;
    }
    public void setAddrGetFlag(String addrGetFlag) {
        this.addrGetFlag = addrGetFlag;
    }
    public String getAddrProv() {
        return addrProv;
    }
    public void setAddrProv(String addrProv) {
        this.addrProv = addrProv;
    }
    public String getAddrCity() {
        return addrCity;
    }
    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }
    public String getAddrCounty() {
        return addrCounty;
    }
    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }
    public String getAddrDetail() {
        return addrDetail;
    }
    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getCertNo() {
        return certNo;
    }
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    public String getPlatformUserNo() {
        return platformUserNo;
    }
    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }
    @Override
    public String toString() {
        return "UserPreloginDto [userName=" + userName + ", mobile=" + mobile + ", certNo=" + certNo
                + ", platformType=" + platformType + ", platformUserNo=" + platformUserNo + ", userStatus="
                + userStatus + ", loginMode=" + loginMode + ", isMutiplueLogin=" + isMutiplueLogin
                + ", sessionId=" + sessionId + ", loginAddr=" + loginAddr + ", loginIp=" + loginIp
                + ", addrGetFlag=" + addrGetFlag + ", addrProv=" + addrProv + ", addrCity=" + addrCity
                + ", addrCounty=" + addrCounty + ", addrDetail=" + addrDetail + ", loginStat=" + loginStat
                + "]";
    }
    
    
    
}
