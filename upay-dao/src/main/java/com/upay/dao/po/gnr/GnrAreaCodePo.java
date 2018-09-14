
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrAreaCodePo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrAreaCode";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_AREA_CODE = "地区代码";
	public static final String ALIAS_AREA_NAME = "地区名称";
	public static final String ALIAS_UP_AREA_CODE = "上级地区代码，如果为上级地区代码为000000，表示顶级";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 地区代码       db_column: AREA_CODE 
     */ 	
	private java.lang.String areaCode;
    /**
     * 地区名称       db_column: AREA_NAME 
     */ 	
	private java.lang.String areaName;
    /**
     * 上级地区代码，如果为上级地区代码为000000，表示顶级       db_column: UP_AREA_CODE 
     */ 	
	private java.lang.String upAreaCode;
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


	
	
	public java.lang.String getAreaCode() {
		return this.areaCode;
	}
	
	public void setAreaCode(java.lang.String value) {
		this.areaCode = value;
	}
	
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	
	public void setAreaName(java.lang.String value) {
		this.areaName = value;
	}
	
	
	public java.lang.String getUpAreaCode() {
		return this.upAreaCode;
	}
	
	public void setUpAreaCode(java.lang.String value) {
		this.upAreaCode = value;
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

