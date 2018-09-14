
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrRegInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrRegInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户ID:系统自动生成";
	public static final String ALIAS_MOBILE = "手机号";
	public static final String ALIAS_USER_NAME = "用户登录名";
	public static final String ALIAS_USER_NICK_NAME = "用户昵称";
	public static final String ALIAS_HEAD_PIC = "用户头像图片地址";
	public static final String ALIAS_LOGIN_PWD = "登录密码:加密后存储数字+字符";
	public static final String ALIAS_LAST_LOGPWD_MODIFYTIME = "最后一次登录密码修改时间";
	public static final String ALIAS_TRADE_PWD = "交易密码  加密后存储 数字+字符";
	public static final String ALIAS_LAST_TRADEPWD_MODIFYTIME = "最后一次交易密码修改时间";
	public static final String ALIAS_GASTURE_PWD = "手势密码  加密后存储";
	public static final String ALIAS_LAST_GASTUREPWD_MODIFYTIME = "最后一次手势密码修改时间";
	public static final String ALIAS_REG_TIME = "注册时间";
	public static final String ALIAS_REG_CHNL_ID = "注册渠道:渠道代码";
	public static final String ALIAS_USER_CERT_LEVEL = "用户认证等级:见附录";
	public static final String ALIAS_USER_VALUE_LEVEL = "用户价值等级:待定";
	public static final String ALIAS_BRANCH_ID = "所属机构:预留";
	public static final String ALIAS_USER_STAT = "用户状态 0：正常 1：待激活 9：注销";
	public static final String ALIAS_USER_LOCK_FLAG = "锁定标志 共6位 第1位：登录锁定 第2位：动账交易锁定 0：正常 1：锁定";
	public static final String ALIAS_BLACKLIST_FLAG = "黑名单标志 0：正常 1：黑名单";
	public static final String ALIAS_BLACKLIST_TYPE = "黑名单类型代码 当BLACKLIST_FLAG为1时";
	public static final String ALIAS_SECURITY_TYPE = "安全认证方式 预留 安全认证方式 0000000000000000 0000000000000000 第01位:合作平台 第02位:手势密码 第03位:二维码 第04位:登录密码 第05位:交易密码 第06位:预留信息 第07位:邮箱 第08位:安全问题 第09位:短信验证码 第10位:文件证书 第11位:设备绑定 第12位:软令牌 第13位:硬令牌 第14位:SE证书 第15位:UK一代 第16位:UK二代 第17位:UK三代 第18位:刮刮卡 其他：预留";
	public static final String ALIAS_ACTIVE_TIME = "激活时间";
	public static final String ALIAS_ACTIVE_CHNL_ID = "激活渠道";
	public static final String ALIAS_CLOSE_TIME = "注销时间";
	public static final String ALIAS_CLOSE_CHNL_ID = "注销渠道";
	public static final String ALIAS_LAST_LOGIN_TIME = "最后一次登录时间";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_REG_TYPE = "注册会员类型：01：免密授权注册,02：普通会员注册,03：商户会员注册";
	public static final String ALIAS_COM_EMAIL = "企业注册邮箱";
	public static final String ALIAS_MER_LEVEL = "商户级别 00:新注册商户 01:一级商户 02:二级商户";
	

	//columns START
    /**
     * 用户ID:系统自动生成       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 手机号       db_column: MOBILE 
     */ 	
	private java.lang.String mobile;
    /**
     * 用户登录名       db_column: USER_NAME 
     */ 	
	private java.lang.String userName;
    /**
     * 用户昵称       db_column: USER_NICK_NAME 
     */ 	
	private java.lang.String userNickName;
    /**
     * 用户头像图片地址       db_column: HEAD_PIC 
     */ 	
	private java.lang.String headPic;
    /**
     * 登录密码:加密后存储数字+字符       db_column: LOGIN_PWD 
     */ 	
	private java.lang.String loginPwd;
    /**
     * 最后一次登录密码修改时间       db_column: LAST_LOGPWD_MODIFYTIME 
     */ 	
	private java.util.Date lastLogpwdModifytime;
    /**
     * 交易密码  加密后存储 数字+字符       db_column: TRADE_PWD 
     */ 	
	private java.lang.String tradePwd;
    /**
     * 最后一次交易密码修改时间       db_column: LAST_TRADEPWD_MODIFYTIME 
     */ 	
	private java.util.Date lastTradepwdModifytime;
    /**
     * 手势密码  加密后存储       db_column: GASTURE_PWD 
     */ 	
	private java.lang.String gasturePwd;
    /**
     * 最后一次手势密码修改时间       db_column: LAST_GASTUREPWD_MODIFYTIME 
     */ 	
	private java.util.Date lastGasturepwdModifytime;
    /**
     * 注册时间       db_column: REG_TIME 
     */ 	
	private java.util.Date regTime;
    /**
     * 注册渠道:渠道代码       db_column: REG_CHNL_ID 
     */ 	
	private java.lang.String regChnlId;
    /**
     * 用户认证等级:见附录       db_column: USER_CERT_LEVEL 
     */ 	
	private java.lang.String userCertLevel;
    /**
     * 用户价值等级:待定       db_column: USER_VALUE_LEVEL 
     */ 	
	private java.lang.String userValueLevel;
    /**
     * 所属机构:预留       db_column: BRANCH_ID 
     */ 	
	private java.lang.String branchId;
    /**
     * 用户状态 0：正常 1：待激活 9：注销       db_column: USER_STAT 
     */ 	
	private java.lang.String userStat;
    /**
     * 锁定标志 共6位 第1位：登录锁定 第2位：动账交易锁定 0：正常 1：锁定       db_column: USER_LOCK_FLAG 
     */ 	
	private java.lang.String userLockFlag;
    /**
     * 黑名单标志 0：正常 1：黑名单       db_column: BLACKLIST_FLAG 
     */ 	
	private java.lang.String blacklistFlag;
    /**
     * 黑名单类型代码 当BLACKLIST_FLAG为1时       db_column: BLACKLIST_TYPE 
     */ 	
	private java.lang.String blacklistType;
    /**
     * 安全认证方式 预留 安全认证方式 0000000000000000 0000000000000000 第01位:合作平台 第02位:手势密码 第03位:二维码 第04位:登录密码 第05位:交易密码 第06位:预留信息 第07位:邮箱 第08位:安全问题 第09位:短信验证码 第10位:文件证书 第11位:设备绑定 第12位:软令牌 第13位:硬令牌 第14位:SE证书 第15位:UK一代 第16位:UK二代 第17位:UK三代 第18位:刮刮卡 其他：预留       db_column: SECURITY_TYPE 
     */ 	
	private java.lang.String securityType;
    /**
     * 激活时间       db_column: ACTIVE_TIME 
     */ 	
	private java.util.Date activeTime;
    /**
     * 激活渠道       db_column: ACTIVE_CHNL_ID 
     */ 	
	private java.lang.String activeChnlId;
    /**
     * 注销时间       db_column: CLOSE_TIME 
     */ 	
	private java.util.Date closeTime;
    /**
     * 注销渠道       db_column: CLOSE_CHNL_ID 
     */ 	
	private java.lang.String closeChnlId;
    /**
     * 最后一次登录时间       db_column: LAST_LOGIN_TIME 
     */ 	
	private java.util.Date lastLoginTime;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
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
    /**
     * 注册会员类型：01：免密授权注册,02：普通会员注册,03：商户会员注册       db_column: REG_TYPE 
     */ 	
	private java.lang.String regType;
    /**
     * 企业注册邮箱       db_column: COM_EMAIL 
     */ 	
	private java.lang.String comEmail;
    /**
     * 商户级别 00:新注册商户 01:一级商户 02:二级商户       db_column: MER_LEVEL 
     */ 	
	private java.lang.String merLevel;
	//columns END


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	
	public java.lang.String getUserNickName() {
		return this.userNickName;
	}
	
	public void setUserNickName(java.lang.String value) {
		this.userNickName = value;
	}
	
	
	public java.lang.String getHeadPic() {
		return this.headPic;
	}
	
	public void setHeadPic(java.lang.String value) {
		this.headPic = value;
	}
	
	
	public java.lang.String getLoginPwd() {
		return this.loginPwd;
	}
	
	public void setLoginPwd(java.lang.String value) {
		this.loginPwd = value;
	}
	
	
	public java.util.Date getLastLogpwdModifytime() {
		return this.lastLogpwdModifytime;
	}
	
	public void setLastLogpwdModifytime(java.util.Date value) {
		this.lastLogpwdModifytime = value;
	}
	
	
	public java.lang.String getTradePwd() {
		return this.tradePwd;
	}
	
	public void setTradePwd(java.lang.String value) {
		this.tradePwd = value;
	}
	
	
	public java.util.Date getLastTradepwdModifytime() {
		return this.lastTradepwdModifytime;
	}
	
	public void setLastTradepwdModifytime(java.util.Date value) {
		this.lastTradepwdModifytime = value;
	}
	
	
	public java.lang.String getGasturePwd() {
		return this.gasturePwd;
	}
	
	public void setGasturePwd(java.lang.String value) {
		this.gasturePwd = value;
	}
	
	
	public java.util.Date getLastGasturepwdModifytime() {
		return this.lastGasturepwdModifytime;
	}
	
	public void setLastGasturepwdModifytime(java.util.Date value) {
		this.lastGasturepwdModifytime = value;
	}
	
	
	public java.util.Date getRegTime() {
		return this.regTime;
	}
	
	public void setRegTime(java.util.Date value) {
		this.regTime = value;
	}
	
	
	public java.lang.String getRegChnlId() {
		return this.regChnlId;
	}
	
	public void setRegChnlId(java.lang.String value) {
		this.regChnlId = value;
	}
	
	
	public java.lang.String getUserCertLevel() {
		return this.userCertLevel;
	}
	
	public void setUserCertLevel(java.lang.String value) {
		this.userCertLevel = value;
	}
	
	
	public java.lang.String getUserValueLevel() {
		return this.userValueLevel;
	}
	
	public void setUserValueLevel(java.lang.String value) {
		this.userValueLevel = value;
	}
	
	
	public java.lang.String getBranchId() {
		return this.branchId;
	}
	
	public void setBranchId(java.lang.String value) {
		this.branchId = value;
	}
	
	
	public java.lang.String getUserStat() {
		return this.userStat;
	}
	
	public void setUserStat(java.lang.String value) {
		this.userStat = value;
	}
	
	
	public java.lang.String getUserLockFlag() {
		return this.userLockFlag;
	}
	
	public void setUserLockFlag(java.lang.String value) {
		this.userLockFlag = value;
	}
	
	
	public java.lang.String getBlacklistFlag() {
		return this.blacklistFlag;
	}
	
	public void setBlacklistFlag(java.lang.String value) {
		this.blacklistFlag = value;
	}
	
	
	public java.lang.String getBlacklistType() {
		return this.blacklistType;
	}
	
	public void setBlacklistType(java.lang.String value) {
		this.blacklistType = value;
	}
	
	
	public java.lang.String getSecurityType() {
		return this.securityType;
	}
	
	public void setSecurityType(java.lang.String value) {
		this.securityType = value;
	}
	
	
	public java.util.Date getActiveTime() {
		return this.activeTime;
	}
	
	public void setActiveTime(java.util.Date value) {
		this.activeTime = value;
	}
	
	
	public java.lang.String getActiveChnlId() {
		return this.activeChnlId;
	}
	
	public void setActiveChnlId(java.lang.String value) {
		this.activeChnlId = value;
	}
	
	
	public java.util.Date getCloseTime() {
		return this.closeTime;
	}
	
	public void setCloseTime(java.util.Date value) {
		this.closeTime = value;
	}
	
	
	public java.lang.String getCloseChnlId() {
		return this.closeChnlId;
	}
	
	public void setCloseChnlId(java.lang.String value) {
		this.closeChnlId = value;
	}
	
	
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
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
	
	
	public java.lang.String getRegType() {
		return this.regType;
	}
	
	public void setRegType(java.lang.String value) {
		this.regType = value;
	}
	
	
	public java.lang.String getComEmail() {
		return this.comEmail;
	}
	
	public void setComEmail(java.lang.String value) {
		this.comEmail = value;
	}
	
	
	public java.lang.String getMerLevel() {
		return this.merLevel;
	}
	
	public void setMerLevel(java.lang.String value) {
		this.merLevel = value;
	}
	

	

}

