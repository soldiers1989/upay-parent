
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayCardbinInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayCardbinInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CARD_BIN = "卡Bin";
	public static final String ALIAS_CARD_BIN_NAME = "卡bin名称";
	public static final String ALIAS_CARD_BIN_TYPE = "卡类型 1、借记卡 2、贷记卡";
	public static final String ALIAS_BANK_BIN_FLAG = "行内行外标志 0：行内 1：行外";
	public static final String ALIAS_CUP_BANK_NO = "银联机构行号";
	public static final String ALIAS_CUP_BANK_NAME = "银联机构名称";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_LOGO_ID = "logo";
	public static final String ALIAS_CARD_NO_LEN = "卡长度";
	

	//columns START
    /**
     * 卡Bin       db_column: CARD_BIN 
     */ 	
	private java.lang.String cardBin;
    /**
     * 卡bin名称       db_column: CARD_BIN_NAME 
     */ 	
	private java.lang.String cardBinName;
    /**
     * 卡类型 1、借记卡 2、贷记卡       db_column: CARD_BIN_TYPE 
     */ 	
	private java.lang.String cardBinType;
    /**
     * 行内行外标志 0：行内 1：行外       db_column: BANK_BIN_FLAG 
     */ 	
	private java.lang.String bankBinFlag;
    /**
     * 银联机构行号       db_column: CUP_BANK_NO 
     */ 	
	private java.lang.String cupBankNo;
    /**
     * 银联机构名称       db_column: CUP_BANK_NAME 
     */ 	
	private java.lang.String cupBankName;
    /**
     * 最后变更时间       db_column: LAST_UPDATE_TIME 
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
     * logo       db_column: LOGO_ID 
     */ 	
	private java.lang.String logoId;
    /**
     * 卡长度       db_column: CARD_NO_LEN 
     */ 	
	private java.lang.Integer cardNoLen;
	//columns END


	
	
	public java.lang.String getCardBin() {
		return this.cardBin;
	}
	
	public void setCardBin(java.lang.String value) {
		this.cardBin = value;
	}
	
	
	public java.lang.String getCardBinName() {
		return this.cardBinName;
	}
	
	public void setCardBinName(java.lang.String value) {
		this.cardBinName = value;
	}
	
	
	public java.lang.String getCardBinType() {
		return this.cardBinType;
	}
	
	public void setCardBinType(java.lang.String value) {
		this.cardBinType = value;
	}
	
	
	public java.lang.String getBankBinFlag() {
		return this.bankBinFlag;
	}
	
	public void setBankBinFlag(java.lang.String value) {
		this.bankBinFlag = value;
	}
	
	
	public java.lang.String getCupBankNo() {
		return this.cupBankNo;
	}
	
	public void setCupBankNo(java.lang.String value) {
		this.cupBankNo = value;
	}
	
	
	public java.lang.String getCupBankName() {
		return this.cupBankName;
	}
	
	public void setCupBankName(java.lang.String value) {
		this.cupBankName = value;
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
	
	
	public java.lang.String getLogoId() {
		return this.logoId;
	}
	
	public void setLogoId(java.lang.String value) {
		this.logoId = value;
	}
	
	
	public java.lang.Integer getCardNoLen() {
		return this.cardNoLen;
	}
	
	public void setCardNoLen(java.lang.Integer value) {
		this.cardNoLen = value;
	}
	

	

}

