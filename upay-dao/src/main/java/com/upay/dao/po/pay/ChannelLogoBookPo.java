
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class ChannelLogoBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChannelLogoBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_LOGO_ID = "logo";
	public static final String ALIAS_LOGO_NAME = "图标名称";
	public static final String ALIAS_LOGO_CLASS = "图标路径";
	public static final String ALIAS_REMARK = "备用字段";
	public static final String ALIAS_CHANNEL_ID = "渠道ID";
	public static final String ALIAS_CHANNEL_TRANSLMT = "渠道限额";
	

	//columns START
    /**
     * logo       db_column: LOGO_ID 
     */ 	
	private java.lang.String logoId;
    /**
     * 图标名称       db_column: LOGO_NAME 
     */ 	
	private java.lang.String logoName;
    /**
     * 图标路径       db_column: LOGO_CLASS 
     */ 	
	private java.lang.String logoClass;
    /**
     * 备用字段       db_column: REMARK 
     */ 	
	private java.lang.String remark;
    /**
     * 渠道ID       db_column: CHANNEL_ID 
     */ 	
	private java.lang.String channelId;
    /**
     * 渠道限额       db_column: CHANNEL_TRANSLMT 
     */ 	
	private java.math.BigDecimal channelTranslmt;
	//columns END


	
	
	public java.lang.String getLogoId() {
		return this.logoId;
	}
	
	public void setLogoId(java.lang.String value) {
		this.logoId = value;
	}
	
	
	public java.lang.String getLogoName() {
		return this.logoName;
	}
	
	public void setLogoName(java.lang.String value) {
		this.logoName = value;
	}
	
	
	public java.lang.String getLogoClass() {
		return this.logoClass;
	}
	
	public void setLogoClass(java.lang.String value) {
		this.logoClass = value;
	}
	
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	
	public java.lang.String getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(java.lang.String value) {
		this.channelId = value;
	}
	
	
	public java.math.BigDecimal getChannelTranslmt() {
		return this.channelTranslmt;
	}
	
	public void setChannelTranslmt(java.math.BigDecimal value) {
		this.channelTranslmt = value;
	}
	

	

}

