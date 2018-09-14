
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerPlatFormPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerPlatForm";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_PLAT_NO = "商户平台号   关联商户基本信息表中的商户号";
	public static final String ALIAS_PLAT_STATUS = "平台状态   0：启用  1：锁定  2：停用";
	public static final String ALIAS_MER_PLAT_DESC = "商户平台说明";
	public static final String ALIAS_LAST_UPDATE_DATE = "最后更新时间";
	public static final String ALIAS_MER_CERT_TYPE = "商户证书类型  ";
	public static final String ALIAS_MER_CERT = "商户证书    平台给商户分配的公钥证书";
	

	//columns START
    /**
     * 商户平台号   关联商户基本信息表中的商户号       db_column: MER_PLAT_NO 
     */ 	
	private java.lang.String merPlatNo;
    /**
     * 平台状态   0：启用  1：锁定  2：停用       db_column: PLAT_STATUS 
     */ 	
	private java.lang.String platStatus;
    /**
     * 商户平台说明       db_column: MER_PLAT_DESC 
     */ 	
	private java.lang.String merPlatDesc;
    /**
     * 最后更新时间       db_column: LAST_UPDATE_DATE 
     */ 	
	private java.util.Date lastUpdateDate;
    /**
     * 商户证书类型         db_column: MER_CERT_TYPE 
     */ 	
	private java.lang.String merCertType;
    /**
     * 商户证书    平台给商户分配的公钥证书       db_column: MER_CERT 
     */ 	
	private java.lang.String merCert;
	//columns END


	
	
	public java.lang.String getMerPlatNo() {
		return this.merPlatNo;
	}
	
	public void setMerPlatNo(java.lang.String value) {
		this.merPlatNo = value;
	}
	
	
	public java.lang.String getPlatStatus() {
		return this.platStatus;
	}
	
	public void setPlatStatus(java.lang.String value) {
		this.platStatus = value;
	}
	
	
	public java.lang.String getMerPlatDesc() {
		return this.merPlatDesc;
	}
	
	public void setMerPlatDesc(java.lang.String value) {
		this.merPlatDesc = value;
	}
	
	
	public java.util.Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}
	
	public void setLastUpdateDate(java.util.Date value) {
		this.lastUpdateDate = value;
	}
	
	
	public java.lang.String getMerCertType() {
		return this.merCertType;
	}
	
	public void setMerCertType(java.lang.String value) {
		this.merCertType = value;
	}
	
	
	public java.lang.String getMerCert() {
		return this.merCert;
	}
	
	public void setMerCert(java.lang.String value) {
		this.merCert = value;
	}
	

	

}

