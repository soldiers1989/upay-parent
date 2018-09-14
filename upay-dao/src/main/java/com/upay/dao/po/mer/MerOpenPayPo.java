
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerOpenPayPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "商户支付开通表";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号  平台生成唯一标识";
	public static final String ALIAS_MER_NAME = "商户名称";
	public static final String ALIAS_WECHAT_OPEN = "开通微信支付";
	public static final String ALIAS_WECHAT_BIND_APPID = "微信绑定APPID";
	public static final String ALIAS_WECHAT_AUTH_DIR = "微信授权目录配置";
	public static final String ALIAS_WECHAT_ATTENTION = "微信推荐关注配置";
	public static final String ALIAS_ALIPAY_OPEN = "开通支付宝支付";
	public static final String ALIAS_UNION_OPEN = "开通银联二维码支付";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后更新时间";
	public static final String ALIAS_REMARK1 = "备用一";
	public static final String ALIAS_REMARK2 = "备用二";
	public static final String ALIAS_REMARK3 = "备用三";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 商户号  平台生成唯一标识       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 商户名称       db_column: MER_NAME 
     */ 	
	private java.lang.String merName;
    /**
     * 开通微信支付       db_column: WECHAT_OPEN 
     */ 	
	private java.lang.String wechatOpen;
    /**
     * 微信绑定APPID       db_column: WECHAT_BIND_APPID 
     */ 	
	private java.lang.String wechatBindAppid;
    /**
     * 微信授权目录配置       db_column: WECHAT_AUTH_DIR 
     */ 	
	private java.lang.String wechatAuthDir;
    /**
     * 微信推荐关注配置       db_column: WECHAT_ATTENTION 
     */ 	
	private java.lang.String wechatAttention;
    /**
     * 开通支付宝支付       db_column: ALIPAY_OPEN 
     */ 	
	private java.lang.String alipayOpen;
    /**
     * 开通银联二维码支付       db_column: UNION_OPEN 
     */ 	
	private java.lang.String unionOpen;
    /**
     * 最后更新时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用一       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用二       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用三       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getMerName() {
		return this.merName;
	}
	
	public void setMerName(java.lang.String value) {
		this.merName = value;
	}
	
	
	public java.lang.String getWechatOpen() {
		return this.wechatOpen;
	}
	
	public void setWechatOpen(java.lang.String value) {
		this.wechatOpen = value;
	}
	
	
	public java.lang.String getWechatBindAppid() {
		return this.wechatBindAppid;
	}
	
	public void setWechatBindAppid(java.lang.String value) {
		this.wechatBindAppid = value;
	}
	
	
	public java.lang.String getWechatAuthDir() {
		return this.wechatAuthDir;
	}
	
	public void setWechatAuthDir(java.lang.String value) {
		this.wechatAuthDir = value;
	}
	
	
	public java.lang.String getWechatAttention() {
		return this.wechatAttention;
	}
	
	public void setWechatAttention(java.lang.String value) {
		this.wechatAttention = value;
	}
	
	
	public java.lang.String getAlipayOpen() {
		return this.alipayOpen;
	}
	
	public void setAlipayOpen(java.lang.String value) {
		this.alipayOpen = value;
	}
	
	
	public java.lang.String getUnionOpen() {
		return this.unionOpen;
	}
	
	public void setUnionOpen(java.lang.String value) {
		this.unionOpen = value;
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
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

