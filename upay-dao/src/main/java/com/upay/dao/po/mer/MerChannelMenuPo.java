
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerChannelMenuPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "商户支付渠道配置";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_CHANNEL_MENU_BOOK_ID = "支付渠道编号";
	public static final String ALIAS_CHANNEL_ID = "平台渠道";
	public static final String ALIAS_MER_NAME = "商户名称";
	public static final String ALIAS_CHANNEL_NAME = "支付渠道名称";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 支付渠道编号       db_column: CHANNEL_MENU_BOOK_ID 
     */ 	
	private java.lang.String channelMenuBookId;
    /**
     * 平台渠道       db_column: CHANNEL_ID 
     */ 	
	private java.lang.String channelId;
    /**
     * 商户名称       db_column: MER_NAME 
     */ 	
	private java.lang.String merName;
    /**
     * 支付渠道名称       db_column: CHANNEL_NAME 
     */ 	
	private java.lang.String channelName;
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
	
	
	public java.lang.String getChannelMenuBookId() {
		return this.channelMenuBookId;
	}
	
	public void setChannelMenuBookId(java.lang.String value) {
		this.channelMenuBookId = value;
	}
	
	
	public java.lang.String getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(java.lang.String value) {
		this.channelId = value;
	}
	
	
	public java.lang.String getMerName() {
		return this.merName;
	}
	
	public void setMerName(java.lang.String value) {
		this.merName = value;
	}
	
	
	public java.lang.String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(java.lang.String value) {
		this.channelName = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

