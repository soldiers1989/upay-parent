package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 下午9:08:32
 */
public class LoginFailDto extends BaseDto {

	private String loginStat;// 用户登录状态
	private String loginMode;// 登录方式
	private String isMutiplueLogin;// 是否多点登录
	private String sessionId;// session ID
	private String loginAddr;// 用户登录地(经纬度)
	private String loginIp;// 登录ip
	private String addrGetFlag;// 地址转换标志
	private String addrProv;// 登录省份
	private String addrCity;// 登录城市
	private String addrCounty;// 登录县
	private String addrDetail;// 登录详细地址

	private String verifyKey;

	private String verifyCodeFlag;

	public String getVerifyCodeFlag() {
		return verifyCodeFlag;
	}

	public void setVerifyCodeFlag(String verifyCodeFlag) {
		this.verifyCodeFlag = verifyCodeFlag;
	}

	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	public String getLoginStat() {
		return loginStat;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public String getIsMutiplueLogin() {
		return isMutiplueLogin;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getLoginAddr() {
		return loginAddr;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public String getAddrGetFlag() {
		return addrGetFlag;
	}

	public String getAddrProv() {
		return addrProv;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public String getAddrCounty() {
		return addrCounty;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setLoginStat(String loginStat) {
		this.loginStat = loginStat;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public void setIsMutiplueLogin(String isMutiplueLogin) {
		this.isMutiplueLogin = isMutiplueLogin;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setLoginAddr(String loginAddr) {
		this.loginAddr = loginAddr;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public void setAddrGetFlag(String addrGetFlag) {
		this.addrGetFlag = addrGetFlag;
	}

	public void setAddrProv(String addrProv) {
		this.addrProv = addrProv;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public void setAddrCounty(String addrCounty) {
		this.addrCounty = addrCounty;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

}
