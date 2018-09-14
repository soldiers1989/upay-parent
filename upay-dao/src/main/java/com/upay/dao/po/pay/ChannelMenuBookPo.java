
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class ChannelMenuBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChannelMenuBook";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CHANNEL_ID = "支付渠道ID";
	public static final String ALIAS_CHANNEL_NAME = "支付渠道 推荐支付 网银支付 平台支付 扫码支付 支付宝 借记卡 扫码";
	public static final String ALIAS_CHANNEL_LEAF = "支付渠道菜单节点 如：01_推荐支付 02_网银支付 03_平台支付";
	public static final String ALIAS_CHANNEL_CHILDLEAF = "支付渠道菜单子节点 如： 0101_本行卡快捷支付 0301_支付宝";
	public static final String ALIAS_CHANNEL_URL = "支付渠道前端模板路径";
	public static final String ALIAS_CHANNEL_FLAG = "渠道启用标志 01_启用 02_停用";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改人";
	public static final String ALIAS_CHANNEL_TOPLEAF = "节点的父节点";
	public static final String ALIAS_PAY_TYPE = "支付方式 00 本行快捷支付  01 他行快捷支付  02 平台账户支付  10 微信支付（扫码支付）  11 微信支付（公众号支付） 20 银联网关支付  60 网银支付(个人) 61 网银支付(企业) 77 退款";
	public static final String ALIAS_APP_CHANNEL_URL = "app支付渠道前端模板路径";
	public static final String ALIAS_CHANNEL_LOGO = "app渠道图标";
	public static final String ALIAS_PRIORITY = "priority";
	

	//columns START
    /**
     * 支付渠道ID       db_column: CHANNEL_ID 
     */ 	
	private java.lang.String channelId;
    /**
     * 支付渠道 推荐支付 网银支付 平台支付 扫码支付 支付宝 借记卡 扫码       db_column: CHANNEL_NAME 
     */ 	
	private java.lang.String channelName;
    /**
     * 支付渠道菜单节点 如：01_推荐支付 02_网银支付 03_平台支付       db_column: CHANNEL_LEAF 
     */ 	
	private java.lang.String channelLeaf;
    /**
     * 支付渠道菜单子节点 如： 0101_本行卡快捷支付 0301_支付宝       db_column: CHANNEL_CHILDLEAF 
     */ 	
	private java.lang.String channelChildleaf;
    /**
     * 支付渠道前端模板路径       db_column: CHANNEL_URL 
     */ 	
	private java.lang.String channelUrl;
    /**
     * 渠道启用标志 01_启用 02_停用       db_column: CHANNEL_FLAG 
     */ 	
	private java.lang.String channelFlag;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改人       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
    /**
     * 节点的父节点       db_column: CHANNEL_TOPLEAF 
     */ 	
	private java.lang.String channelTopleaf;
    /**
     * 支付方式 00 本行快捷支付  01 他行快捷支付  02 平台账户支付  10 微信支付（扫码支付）  11 微信支付（公众号支付） 20 银联网关支付  60 网银支付(个人) 61 网银支付(企业) 77 退款       db_column: PAY_TYPE 
     */ 	
	private java.lang.String payType;
    /**
     * app支付渠道前端模板路径       db_column: APP_CHANNEL_URL 
     */ 	
	private java.lang.String appChannelUrl;
    /**
     * app渠道图标       db_column: CHANNEL_LOGO 
     */ 	
	private java.lang.String channelLogo;
    /**
     * priority       db_column: PRIORITY 
     */ 	
	private java.lang.String priority;
	//columns END


	
	
	public java.lang.String getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(java.lang.String value) {
		this.channelId = value;
	}
	
	
	public java.lang.String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(java.lang.String value) {
		this.channelName = value;
	}
	
	
	public java.lang.String getChannelLeaf() {
		return this.channelLeaf;
	}
	
	public void setChannelLeaf(java.lang.String value) {
		this.channelLeaf = value;
	}
	
	
	public java.lang.String getChannelChildleaf() {
		return this.channelChildleaf;
	}
	
	public void setChannelChildleaf(java.lang.String value) {
		this.channelChildleaf = value;
	}
	
	
	public java.lang.String getChannelUrl() {
		return this.channelUrl;
	}
	
	public void setChannelUrl(java.lang.String value) {
		this.channelUrl = value;
	}
	
	
	public java.lang.String getChannelFlag() {
		return this.channelFlag;
	}
	
	public void setChannelFlag(java.lang.String value) {
		this.channelFlag = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getLastOper() {
		return this.lastOper;
	}
	
	public void setLastOper(java.lang.String value) {
		this.lastOper = value;
	}
	
	
	public java.lang.String getChannelTopleaf() {
		return this.channelTopleaf;
	}
	
	public void setChannelTopleaf(java.lang.String value) {
		this.channelTopleaf = value;
	}
	
	
	public java.lang.String getPayType() {
		return this.payType;
	}
	
	public void setPayType(java.lang.String value) {
		this.payType = value;
	}
	
	
	public java.lang.String getAppChannelUrl() {
		return this.appChannelUrl;
	}
	
	public void setAppChannelUrl(java.lang.String value) {
		this.appChannelUrl = value;
	}
	
	
	public java.lang.String getChannelLogo() {
		return this.channelLogo;
	}
	
	public void setChannelLogo(java.lang.String value) {
		this.channelLogo = value;
	}
	
	
	public java.lang.String getPriority() {
		return this.priority;
	}
	
	public void setPriority(java.lang.String value) {
		this.priority = value;
	}
	

	

}

