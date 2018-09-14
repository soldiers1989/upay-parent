
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrChannelConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrChannelConf";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CHNL_ID = "渠道代码";
	public static final String ALIAS_CHNL_NAME = "渠道名称";
	public static final String ALIAS_CHNL_DESC = "渠道描述";
	public static final String ALIAS_CHNL_STAT = "渠道状态 1：正常  0：关闭";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 渠道代码       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 渠道名称       db_column: CHNL_NAME 
     */ 	
	private java.lang.String chnlName;
    /**
     * 渠道描述       db_column: CHNL_DESC 
     */ 	
	private java.lang.String chnlDesc;
    /**
     * 渠道状态 1：正常  0：关闭       db_column: CHNL_STAT 
     */ 	
	private java.lang.String chnlStat;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改操作员       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
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


	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getChnlName() {
		return this.chnlName;
	}
	
	public void setChnlName(java.lang.String value) {
		this.chnlName = value;
	}
	
	
	public java.lang.String getChnlDesc() {
		return this.chnlDesc;
	}
	
	public void setChnlDesc(java.lang.String value) {
		this.chnlDesc = value;
	}
	
	
	public java.lang.String getChnlStat() {
		return this.chnlStat;
	}
	
	public void setChnlStat(java.lang.String value) {
		this.chnlStat = value;
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

