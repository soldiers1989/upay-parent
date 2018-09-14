
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerReltypePo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerReltype";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_RELTYPE_NAME = "类目名称";
	public static final String ALIAS_RELTYPE_ID = "类目编号 每一级两位数字";
	public static final String ALIAS_ORI_RELTYPE_TYPE = "上级类目编号 根一级的编号默认为root编号";
	

	//columns START
    /**
     * 类目名称       db_column: RELTYPE_NAME 
     */ 	
	private java.lang.String reltypeName;
    /**
     * 类目编号 每一级两位数字       db_column: RELTYPE_ID 
     */ 	
	private java.lang.String reltypeId;
    /**
     * 上级类目编号 根一级的编号默认为root编号       db_column: ORI_RELTYPE_TYPE 
     */ 	
	private java.lang.String oriReltypeType;
	//columns END


	
	
	public java.lang.String getReltypeName() {
		return this.reltypeName;
	}
	
	public void setReltypeName(java.lang.String value) {
		this.reltypeName = value;
	}
	
	
	public java.lang.String getReltypeId() {
		return this.reltypeId;
	}
	
	public void setReltypeId(java.lang.String value) {
		this.reltypeId = value;
	}
	
	
	public java.lang.String getOriReltypeType() {
		return this.oriReltypeType;
	}
	
	public void setOriReltypeType(java.lang.String value) {
		this.oriReltypeType = value;
	}
	

	

}

