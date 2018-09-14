
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrLogListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrLogList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_CHNL_ID = "登录渠道";
	public static final String ALIAS_LOGIN_MODE = "登录方式 1：登录密码登录 2：手势密码登录 3：邮箱登录 4：登录名登录 5：人脸识别登录 6：扫码登录 。。。";
	public static final String ALIAS_IS_MUTIPLUE_LOGIN = "是否多点登录 0：否 1：是";
	public static final String ALIAS_SESSION_ID = "SESSION_ID 为了保证和检查Session 的登录情况";
	public static final String ALIAS_PLATFORM = "登录平台类型 当CHNL_ID 为APP时,为对应APP系统; 当CHNL_ID为web门户时，为浏览器类型";
	public static final String ALIAS_LOGIN_TIME = "用户登录时间";
	public static final String ALIAS_LOGOUT_TIME = "用户退出时间";
	public static final String ALIAS_LOGIN_ADDR = "用户登录地 当CHNL_ID为APP时 经纬度用”,”分割";
	public static final String ALIAS_LOGIN_IP = "用户登录IP 当CHNL_ID为web门户时";
	public static final String ALIAS_ADDR_GET_FLAG = "地址转换标志 0：未获取 1：已获取 2：无法获取";
	public static final String ALIAS_ADDR_PROV = "省(直辖市) 登录地址： 省（直辖市，自治区）代码";
	public static final String ALIAS_ADDR_CITY = "地级市 登录地址： 市地区代码 对于直辖市可能没有";
	public static final String ALIAS_ADDR_COUNTY = "县级市(区，县) 登录地址： 县地区代码";
	public static final String ALIAS_ADDR_DETAIL = "详细地址 登录地址： 县级以下地区描述";
	public static final String ALIAS_LOGIN_STAT = "用户登录状态 0：失败 1：成功";
	public static final String ALIAS_FAIL_REASON = "登录失败原因";
	public static final String ALIAS_LOGIN_TIPS = "登录提示说明";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 登录渠道       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 登录方式 1：登录密码登录 2：手势密码登录 3：邮箱登录 4：登录名登录 5：人脸识别登录 6：扫码登录 。。。       db_column: LOGIN_MODE 
     */ 	
	private java.lang.String loginMode;
    /**
     * 是否多点登录 0：否 1：是       db_column: IS_MUTIPLUE_LOGIN 
     */ 	
	private java.lang.String isMutiplueLogin;
    /**
     * SESSION_ID 为了保证和检查Session 的登录情况       db_column: SESSION_ID 
     */ 	
	private java.lang.String sessionId;
    /**
     * 登录平台类型 当CHNL_ID 为APP时,为对应APP系统; 当CHNL_ID为web门户时，为浏览器类型       db_column: PLATFORM 
     */ 	
	private java.lang.String platform;
    /**
     * 用户登录时间       db_column: LOGIN_TIME 
     */ 	
	private java.util.Date loginTime;
    /**
     * 用户退出时间       db_column: LOGOUT_TIME 
     */ 	
	private java.util.Date logoutTime;
    /**
     * 用户登录地 当CHNL_ID为APP时 经纬度用”,”分割       db_column: LOGIN_ADDR 
     */ 	
	private java.lang.String loginAddr;
    /**
     * 用户登录IP 当CHNL_ID为web门户时       db_column: LOGIN_IP 
     */ 	
	private java.lang.String loginIp;
    /**
     * 地址转换标志 0：未获取 1：已获取 2：无法获取       db_column: ADDR_GET_FLAG 
     */ 	
	private java.lang.String addrGetFlag;
    /**
     * 省(直辖市) 登录地址： 省（直辖市，自治区）代码       db_column: ADDR_PROV 
     */ 	
	private java.lang.String addrProv;
    /**
     * 地级市 登录地址： 市地区代码 对于直辖市可能没有       db_column: ADDR_CITY 
     */ 	
	private java.lang.String addrCity;
    /**
     * 县级市(区，县) 登录地址： 县地区代码       db_column: ADDR_COUNTY 
     */ 	
	private java.lang.String addrCounty;
    /**
     * 详细地址 登录地址： 县级以下地区描述       db_column: ADDR_DETAIL 
     */ 	
	private java.lang.String addrDetail;
    /**
     * 用户登录状态 0：失败 1：成功       db_column: LOGIN_STAT 
     */ 	
	private java.lang.String loginStat;
    /**
     * 登录失败原因       db_column: FAIL_REASON 
     */ 	
	private java.lang.String failReason;
    /**
     * 登录提示说明       db_column: LOGIN_TIPS 
     */ 	
	private java.lang.String loginTips;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用3       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getLoginMode() {
		return this.loginMode;
	}
	
	public void setLoginMode(java.lang.String value) {
		this.loginMode = value;
	}
	
	
	public java.lang.String getIsMutiplueLogin() {
		return this.isMutiplueLogin;
	}
	
	public void setIsMutiplueLogin(java.lang.String value) {
		this.isMutiplueLogin = value;
	}
	
	
	public java.lang.String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(java.lang.String value) {
		this.sessionId = value;
	}
	
	
	public java.lang.String getPlatform() {
		return this.platform;
	}
	
	public void setPlatform(java.lang.String value) {
		this.platform = value;
	}
	
	
	public java.util.Date getLoginTime() {
		return this.loginTime;
	}
	
	public void setLoginTime(java.util.Date value) {
		this.loginTime = value;
	}
	
	
	public java.util.Date getLogoutTime() {
		return this.logoutTime;
	}
	
	public void setLogoutTime(java.util.Date value) {
		this.logoutTime = value;
	}
	
	
	public java.lang.String getLoginAddr() {
		return this.loginAddr;
	}
	
	public void setLoginAddr(java.lang.String value) {
		this.loginAddr = value;
	}
	
	
	public java.lang.String getLoginIp() {
		return this.loginIp;
	}
	
	public void setLoginIp(java.lang.String value) {
		this.loginIp = value;
	}
	
	
	public java.lang.String getAddrGetFlag() {
		return this.addrGetFlag;
	}
	
	public void setAddrGetFlag(java.lang.String value) {
		this.addrGetFlag = value;
	}
	
	
	public java.lang.String getAddrProv() {
		return this.addrProv;
	}
	
	public void setAddrProv(java.lang.String value) {
		this.addrProv = value;
	}
	
	
	public java.lang.String getAddrCity() {
		return this.addrCity;
	}
	
	public void setAddrCity(java.lang.String value) {
		this.addrCity = value;
	}
	
	
	public java.lang.String getAddrCounty() {
		return this.addrCounty;
	}
	
	public void setAddrCounty(java.lang.String value) {
		this.addrCounty = value;
	}
	
	
	public java.lang.String getAddrDetail() {
		return this.addrDetail;
	}
	
	public void setAddrDetail(java.lang.String value) {
		this.addrDetail = value;
	}
	
	
	public java.lang.String getLoginStat() {
		return this.loginStat;
	}
	
	public void setLoginStat(java.lang.String value) {
		this.loginStat = value;
	}
	
	
	public java.lang.String getFailReason() {
		return this.failReason;
	}
	
	public void setFailReason(java.lang.String value) {
		this.failReason = value;
	}
	
	
	public java.lang.String getLoginTips() {
		return this.loginTips;
	}
	
	public void setLoginTips(java.lang.String value) {
		this.loginTips = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	
	
	public java.lang.String getRemark2() {
		return this.remark2;
	}
	
	public void setRemark2(java.lang.String value) {
		this.remark2 = value;
	}
	
	
	public java.lang.String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(java.lang.String value) {
		this.remark3 = value;
	}
	

	

}

