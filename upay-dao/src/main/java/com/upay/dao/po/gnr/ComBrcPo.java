
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class ComBrcPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ComBrc";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BRAN_CODE = "机构代码";
	public static final String ALIAS_BRAN_NAME = "机构名称";
	public static final String ALIAS_BRAN_STATUS = "机构状态，预留       0-撤销    1-生效";
	public static final String ALIAS_BRAN_TYPE = "机构类别 预留 第一位，功能 0-核算主体机构      1-营业网点     2-管理机构    3-虚拟机构 第二位，业务范围 0-综合     1-对公    2-对私 第三位，币种 0-本外币     1-本币     2-外币 其余位，预留";
	public static final String ALIAS_BRAN_LEVEL = "机构级别 预留 0-总行     1-支行     2-营业网点";
	public static final String ALIAS_AREA_CODE = "区域代码，预留";
	public static final String ALIAS_ADDR = "地址，预留";
	public static final String ALIAS_ZIP_CODE = "邮编，预留";
	public static final String ALIAS_REMARK1 = "备用字段1";
	public static final String ALIAS_REMARK2 = "备用字段2";
	

	//columns START
    /**
     * 机构代码       db_column: BRAN_CODE 
     */ 	
	private java.lang.String branCode;
    /**
     * 机构名称       db_column: BRAN_NAME 
     */ 	
	private java.lang.String branName;
    /**
     * 机构状态，预留       0-撤销    1-生效       db_column: BRAN_STATUS 
     */ 	
	private java.lang.String branStatus;
    /**
     * 机构类别 预留 第一位，功能 0-核算主体机构      1-营业网点     2-管理机构    3-虚拟机构 第二位，业务范围 0-综合     1-对公    2-对私 第三位，币种 0-本外币     1-本币     2-外币 其余位，预留       db_column: BRAN_TYPE 
     */ 	
	private java.lang.String branType;
    /**
     * 机构级别 预留 0-总行     1-支行     2-营业网点       db_column: BRAN_LEVEL 
     */ 	
	private java.lang.String branLevel;
    /**
     * 区域代码，预留       db_column: AREA_CODE 
     */ 	
	private java.lang.String areaCode;
    /**
     * 地址，预留       db_column: ADDR 
     */ 	
	private java.lang.String addr;
    /**
     * 邮编，预留       db_column: ZIP_CODE 
     */ 	
	private java.lang.String zipCode;
    /**
     * 备用字段1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用字段2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
	//columns END


	
	
	public java.lang.String getBranCode() {
		return this.branCode;
	}
	
	public void setBranCode(java.lang.String value) {
		this.branCode = value;
	}
	
	
	public java.lang.String getBranName() {
		return this.branName;
	}
	
	public void setBranName(java.lang.String value) {
		this.branName = value;
	}
	
	
	public java.lang.String getBranStatus() {
		return this.branStatus;
	}
	
	public void setBranStatus(java.lang.String value) {
		this.branStatus = value;
	}
	
	
	public java.lang.String getBranType() {
		return this.branType;
	}
	
	public void setBranType(java.lang.String value) {
		this.branType = value;
	}
	
	
	public java.lang.String getBranLevel() {
		return this.branLevel;
	}
	
	public void setBranLevel(java.lang.String value) {
		this.branLevel = value;
	}
	
	
	public java.lang.String getAreaCode() {
		return this.areaCode;
	}
	
	public void setAreaCode(java.lang.String value) {
		this.areaCode = value;
	}
	
	
	public java.lang.String getAddr() {
		return this.addr;
	}
	
	public void setAddr(java.lang.String value) {
		this.addr = value;
	}
	
	
	public java.lang.String getZipCode() {
		return this.zipCode;
	}
	
	public void setZipCode(java.lang.String value) {
		this.zipCode = value;
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
	

	

}

