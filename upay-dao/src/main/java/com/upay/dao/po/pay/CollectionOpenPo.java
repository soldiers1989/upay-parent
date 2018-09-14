
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class CollectionOpenPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "CollectionOpen";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PHONE = "phone";
	public static final String ALIAS_CARD_BIN_TYPE = "cardBinType";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_BINDACCTNO = "bindacctno";
	public static final String ALIAS_CREATE_DATE = "createDate";
	public static final String ALIAS_CVN2 = "cvn2";
	public static final String ALIAS_EXPIRED = "expired";
	public static final String ALIAS_CERTIF_TP = "certifTp";
	public static final String ALIAS_CERTIF_ID = "certifId";
	public static final String ALIAS_CUSTOMER_NM = "customerNm";
	public static final String ALIAS_REMARK1 = "remark1";
	public static final String ALIAS_REMARK2 = "remark2";
	public static final String ALIAS_REMARK3 = "remark3";
	

	//columns START
    /**
     * phone       db_column: PHONE 
     */ 	
	private String phone;
    /**
     * cardBinType       db_column: CARD_BIN_TYPE 
     */ 	
	private String cardBinType;
    /**
     * status       db_column: STATUS 
     */ 	
	private String status;
    /**
     * bindacctno       db_column: BINDACCTNO 
     */ 	
	private String bindacctno;
    /**
     * createDate       db_column: CREATE_DATE 
     */ 	
	private java.util.Date createDate;
    /**
     * cvn2       db_column: CVN2 
     */ 	
	private String cvn2;
    /**
     * expired       db_column: EXPIRED 
     */ 	
	private String expired;
    /**
     * certifTp       db_column: CERTIF_TP 
     */ 	
	private String certifTp;
    /**
     * certifId       db_column: CERTIF_ID 
     */ 	
	private String certifId;
    /**
     * customerNm       db_column: CUSTOMER_NM 
     */ 	
	private String customerNm;
    /**
     * remark1       db_column: REMARK1 
     */ 	
	private String remark1;
    /**
     * remark2       db_column: REMARK2 
     */ 	
	private String remark2;
    /**
     * remark3       db_column: REMARK3 
     */ 	
	private String remark3;
	//columns END


	
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String value) {
		this.phone = value;
	}
	
	
	public String getCardBinType() {
		return this.cardBinType;
	}
	
	public void setCardBinType(String value) {
		this.cardBinType = value;
	}
	
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String value) {
		this.status = value;
	}
	
	
	public String getBindacctno() {
		return this.bindacctno;
	}
	
	public void setBindacctno(String value) {
		this.bindacctno = value;
	}
	
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	
	public String getCvn2() {
		return this.cvn2;
	}
	
	public void setCvn2(String value) {
		this.cvn2 = value;
	}
	
	
	public String getExpired() {
		return this.expired;
	}
	
	public void setExpired(String value) {
		this.expired = value;
	}
	
	
	public String getCertifTp() {
		return this.certifTp;
	}
	
	public void setCertifTp(String value) {
		this.certifTp = value;
	}
	
	
	public String getCertifId() {
		return this.certifId;
	}
	
	public void setCertifId(String value) {
		this.certifId = value;
	}
	
	
	public String getCustomerNm() {
		return this.customerNm;
	}
	
	public void setCustomerNm(String value) {
		this.customerNm = value;
	}
	
	
	public String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(String value) {
		this.remark1 = value;
	}
	
	
	public String getRemark2() {
		return this.remark2;
	}
	
	public void setRemark2(String value) {
		this.remark2 = value;
	}
	
	
	public String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(String value) {
		this.remark3 = value;
	}
	

	

}

