
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccCardConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccCardConf";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CARD_BIN = "卡BIN";
	public static final String ALIAS_SECTION_ONE = "卡号段1";
	public static final String ALIAS_SECTION_TWO = "卡号段2";
	public static final String ALIAS_SECTION_THREE = "卡号段3";
	public static final String ALIAS_CARD_SEQ = "卡序号";
	public static final String ALIAS_CARD_LENGTH = "卡号长度";
	public static final String ALIAS_CARD_STAT = "卡号状态 0：在用 1：未用";
	public static final String ALIAS_CARD_TYPE = "卡号类型 预留 默认：00";
	public static final String ALIAS_LAST_UPDATE_OPER = "最后更新操作员";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后更新时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 卡BIN       db_column: CARD_BIN 
     */ 	
	private java.lang.String cardBin;
    /**
     * 卡号段1       db_column: SECTION_ONE 
     */ 	
	private java.lang.String sectionOne;
    /**
     * 卡号段2       db_column: SECTION_TWO 
     */ 	
	private java.lang.String sectionTwo;
    /**
     * 卡号段3       db_column: SECTION_THREE 
     */ 	
	private java.lang.String sectionThree;
    /**
     * 卡序号       db_column: CARD_SEQ 
     */ 	
	private java.lang.Integer cardSeq;
    /**
     * 卡号长度       db_column: CARD_LENGTH 
     */ 	
	private Integer cardLength;
    /**
     * 卡号状态 0：在用 1：未用       db_column: CARD_STAT 
     */ 	
	private java.lang.String cardStat;
    /**
     * 卡号类型 预留 默认：00       db_column: CARD_TYPE 
     */ 	
	private java.lang.String cardType;
    /**
     * 最后更新操作员       db_column: LAST_UPDATE_OPER 
     */ 	
	private java.lang.String lastUpdateOper;
    /**
     * 最后更新时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
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


	
	
	public java.lang.String getCardBin() {
		return this.cardBin;
	}
	
	public void setCardBin(java.lang.String value) {
		this.cardBin = value;
	}
	
	
	public java.lang.String getSectionOne() {
		return this.sectionOne;
	}
	
	public void setSectionOne(java.lang.String value) {
		this.sectionOne = value;
	}
	
	
	public java.lang.String getSectionTwo() {
		return this.sectionTwo;
	}
	
	public void setSectionTwo(java.lang.String value) {
		this.sectionTwo = value;
	}
	
	
	public java.lang.String getSectionThree() {
		return this.sectionThree;
	}
	
	public void setSectionThree(java.lang.String value) {
		this.sectionThree = value;
	}
	
	
	public java.lang.Integer getCardSeq() {
		return this.cardSeq;
	}
	
	public void setCardSeq(java.lang.Integer value) {
		this.cardSeq = value;
	}
	
	
	public Integer getCardLength() {
		return this.cardLength;
	}
	
	public void setCardLength(Integer value) {
		this.cardLength = value;
	}
	
	
	public java.lang.String getCardStat() {
		return this.cardStat;
	}
	
	public void setCardStat(java.lang.String value) {
		this.cardStat = value;
	}
	
	
	public java.lang.String getCardType() {
		return this.cardType;
	}
	
	public void setCardType(java.lang.String value) {
		this.cardType = value;
	}
	
	
	public java.lang.String getLastUpdateOper() {
		return this.lastUpdateOper;
	}
	
	public void setLastUpdateOper(java.lang.String value) {
		this.lastUpdateOper = value;
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
	

	

}

