package com.dubhe.common.json;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dubhe.common.annotation.NotNull;

/**
 * 报文系统头
 * @author freeplato
 *
 */
public class SysHead{

	/** 交易代码 */
	@NotNull
    @JSONField(name = "TRANS_CODE")
	private String transCode;
	
	/** 交易代码渠道代码 */
	@NotNull
    @JSONField(name = "CHNL_ID")
	private String chnlId;
	
	/** 平台类型 */
    @JSONField(name = "PLATFORM")
	private String platform;
	
    /** 渠道IP地址 */
    @JSONField(name = "ADDRESS")
	private String address;
	
    /** 渠道APP版本号 */
    @JSONField(name = "VERSION")
	private String version;
    
    /** 微信OPEN_ID */
    @JSONField(name = "OPEN_ID")
	private String openId;
	
    /** SESSION会话 */
    @JSONField(name = "SESSION_ID")
	private String sessionId;

	/** 用户号，登录后必输 */
    @JSONField(name = "USER_ID")
	private String userId;

    /** 密码AES加密参数 */
    @JSONField(name = "AES_KEY")
	private String aesKey;
    
    /** 交易时间 */
    @JSONField(name = "TRANS_TIME")
    private String transTime;
    
	/** 返回错误码 */
    @JSONField(name = "RSP_CODE")
	private String rspCode;
	
	/** 返回错误信息 */
    @JSONField(name = "RSP_MSG")
	private String rspMsg;
    
    private Date sysDate;
    
    private Date sysTime;
	
    private String httpSessionId;
    
    public SysHead() {
        super();
    }

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getChnlId() {
		return chnlId;
	}

	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	public void setHttpSessionId(String httpSessionId) {
		this.httpSessionId = httpSessionId;
	}

	public Date getSysDate() {
		return sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

	public Date getSysTime() {
		return sysTime;
	}

	public void setSysTime(Date sysTime) {
		this.sysTime = sysTime;
	}

}
