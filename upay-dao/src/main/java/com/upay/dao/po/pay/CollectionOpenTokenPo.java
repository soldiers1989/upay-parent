
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class CollectionOpenTokenPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "代收权限开通表";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_PHONE = "手机号";
	public static final String ALIAS_CARD_BIN_TYPE = "卡类型  1、借记卡 2、贷记卡";
	public static final String ALIAS_TOKEN = "TOKEN号";
	public static final String ALIAS_TRID = "标记请求者";
	public static final String ALIAS_TOKENLEVEL = "标记担保级别";
	public static final String ALIAS_TOKENBEGIN = "标记生效时间";
	public static final String ALIAS_TOKENEND = "标记失效时间";
	public static final String ALIAS_TOKENTYPE = "标记类型";
	public static final String ALIAS_STATUS = "状态 01已开通 02未开通";
	public static final String ALIAS_BINDACCTNO = "卡号";
	public static final String ALIAS_CREATE_DATE = "创建时间";
	public static final String ALIAS_CVN2 = "CVN2";
	public static final String ALIAS_EXPIRED = "卡有效期";
	public static final String ALIAS_CERTIF_TP = "证件类型";
	public static final String ALIAS_CERTIF_ID = "证件号";
	public static final String ALIAS_CUSTOMER_NM = "姓名";
	public static final String ALIAS_PIN = "密码【这里如果送密码 商户号必须配置 ”商户允许采集密码“】";
	public static final String ALIAS_REMARK1 = "备用一";
	public static final String ALIAS_REMARK2 = "备用二";
	public static final String ALIAS_REMARK3 = "备用三";
	

	//columns START
    /**
     * 手机号       db_column: PHONE 
     */ 	
	private java.lang.String phone;
    /**
     * 卡类型  1、借记卡 2、贷记卡       db_column: CARD_BIN_TYPE 
     */ 	
	private java.lang.String cardBinType;
    /**
     * TOKEN号       db_column: TOKEN 
     */ 	
	private java.lang.String token;
    /**
     * 标记请求者       db_column: TRID 
     */ 	
	private java.lang.String trid;
    /**
     * 标记担保级别       db_column: TOKENLEVEL 
     */ 	
	private java.lang.String tokenlevel;
    /**
     * 标记生效时间       db_column: TOKENBEGIN 
     */ 	
	private java.lang.String tokenbegin;
    /**
     * 标记失效时间       db_column: TOKENEND 
     */ 	
	private java.lang.String tokenend;
    /**
     * 标记类型       db_column: TOKENTYPE 
     */ 	
	private java.lang.String tokentype;
    /**
     * 状态 01已开通 02未开通       db_column: STATUS 
     */ 	
	private java.lang.String status;
    /**
     * 卡号       db_column: BINDACCTNO 
     */ 	
	private java.lang.String bindacctno;
    /**
     * 创建时间       db_column: CREATE_DATE 
     */ 	
	private java.util.Date createDate;
    /**
     * CVN2       db_column: CVN2 
     */ 	
	private java.lang.String cvn2;
    /**
     * 卡有效期       db_column: EXPIRED 
     */ 	
	private java.lang.String expired;
    /**
     * 证件类型       db_column: CERTIF_TP 
     */ 	
	private java.lang.String certifTp;
    /**
     * 证件号       db_column: CERTIF_ID 
     */ 	
	private java.lang.String certifId;
    /**
     * 姓名       db_column: CUSTOMER_NM 
     */ 	
	private java.lang.String customerNm;
    /**
     * 密码【这里如果送密码 商户号必须配置 ”商户允许采集密码“】       db_column: PIN 
     */ 	
	private java.lang.String pin;
    /**
     * 备用一       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用二       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用三       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	
	public java.lang.String getCardBinType() {
		return this.cardBinType;
	}
	
	public void setCardBinType(java.lang.String value) {
		this.cardBinType = value;
	}
	
	
	public java.lang.String getToken() {
		return this.token;
	}
	
	public void setToken(java.lang.String value) {
		this.token = value;
	}
	
	
	public java.lang.String getTrid() {
		return this.trid;
	}
	
	public void setTrid(java.lang.String value) {
		this.trid = value;
	}
	
	
	public java.lang.String getTokenlevel() {
		return this.tokenlevel;
	}
	
	public void setTokenlevel(java.lang.String value) {
		this.tokenlevel = value;
	}
	
	
	public java.lang.String getTokenbegin() {
		return this.tokenbegin;
	}
	
	public void setTokenbegin(java.lang.String value) {
		this.tokenbegin = value;
	}
	
	
	public java.lang.String getTokenend() {
		return this.tokenend;
	}
	
	public void setTokenend(java.lang.String value) {
		this.tokenend = value;
	}
	
	
	public java.lang.String getTokentype() {
		return this.tokentype;
	}
	
	public void setTokentype(java.lang.String value) {
		this.tokentype = value;
	}
	
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	
	public java.lang.String getBindacctno() {
		return this.bindacctno;
	}
	
	public void setBindacctno(java.lang.String value) {
		this.bindacctno = value;
	}
	
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	
	public java.lang.String getCvn2() {
		return this.cvn2;
	}
	
	public void setCvn2(java.lang.String value) {
		this.cvn2 = value;
	}
	
	
	public java.lang.String getExpired() {
		return this.expired;
	}
	
	public void setExpired(java.lang.String value) {
		this.expired = value;
	}
	
	
	public java.lang.String getCertifTp() {
		return this.certifTp;
	}
	
	public void setCertifTp(java.lang.String value) {
		this.certifTp = value;
	}
	
	
	public java.lang.String getCertifId() {
		return this.certifId;
	}
	
	public void setCertifId(java.lang.String value) {
		this.certifId = value;
	}
	
	
	public java.lang.String getCustomerNm() {
		return this.customerNm;
	}
	
	public void setCustomerNm(java.lang.String value) {
		this.customerNm = value;
	}
	
	
	public java.lang.String getPin() {
		return this.pin;
	}
	
	public void setPin(java.lang.String value) {
		this.pin = value;
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

