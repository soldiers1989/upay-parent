
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class ComBrcRelPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ComBrcRel";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BRAN_CODE = "机构代码";
	public static final String ALIAS_RELATION = "关系类型，“01”--清算关系";
	public static final String ALIAS_BLG_BRAN_CODE = "归属机构，本机构的上级机构";
	public static final String ALIAS_BRC_BLG_ACCT_IDX = "归属机构账户索引，清算关系需要使用 如：40701*2601(头寸) （本机构的上级机构）";
	public static final String ALIAS_BRC_ACCT_IDX = "本机构账号索引，清算关系需要使用 如：40702*1(头寸)";
	public static final String ALIAS_REL_LEVEL = "关系级别，清算关系使用    1最大；9最小";
	public static final String ALIAS_REMARK1 = "备用字段1";
	public static final String ALIAS_REMARK2 = "备用字段2";
	

	//columns START
    /**
     * 机构代码       db_column: BRAN_CODE 
     */ 	
	private java.lang.String branCode;
    /**
     * 关系类型，“01”--清算关系       db_column: RELATION 
     */ 	
	private java.lang.String relation;
    /**
     * 归属机构，本机构的上级机构       db_column: BLG_BRAN_CODE 
     */ 	
	private java.lang.String blgBranCode;
    /**
     * 归属机构账户索引，清算关系需要使用 如：40701*2601(头寸) （本机构的上级机构）       db_column: BRC_BLG_ACCT_IDX 
     */ 	
	private java.lang.String brcBlgAcctIdx;
    /**
     * 本机构账号索引，清算关系需要使用 如：40702*1(头寸)       db_column: BRC_ACCT_IDX 
     */ 	
	private java.lang.String brcAcctIdx;
    /**
     * 关系级别，清算关系使用    1最大；9最小       db_column: REL_LEVEL 
     */ 	
	private java.lang.String relLevel;
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
	
	
	public java.lang.String getRelation() {
		return this.relation;
	}
	
	public void setRelation(java.lang.String value) {
		this.relation = value;
	}
	
	
	public java.lang.String getBlgBranCode() {
		return this.blgBranCode;
	}
	
	public void setBlgBranCode(java.lang.String value) {
		this.blgBranCode = value;
	}
	
	
	public java.lang.String getBrcBlgAcctIdx() {
		return this.brcBlgAcctIdx;
	}
	
	public void setBrcBlgAcctIdx(java.lang.String value) {
		this.brcBlgAcctIdx = value;
	}
	
	
	public java.lang.String getBrcAcctIdx() {
		return this.brcAcctIdx;
	}
	
	public void setBrcAcctIdx(java.lang.String value) {
		this.brcAcctIdx = value;
	}
	
	
	public java.lang.String getRelLevel() {
		return this.relLevel;
	}
	
	public void setRelLevel(java.lang.String value) {
		this.relLevel = value;
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

