
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccFrzListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccFrzList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_FRZ_NO = "冻结编号";
	public static final String ALIAS_FRZ_ORDER_NUM = "冻结序号 根据FRZ_NO从1开始";
	public static final String ALIAS_FRZ_TYPE = "冻结类型 0：冻结 1：续冻 2：解冻";
	public static final String ALIAS_FRZ_STAT = "冻结状态 当FRZ_TYPE不为2时 见附录";
	public static final String ALIAS_FRZ_AMT = "冻结金额 当FRZ_STAT为4时";
	public static final String ALIAS_UNFRZ_AMT = "解冻金额 当FRZ_TYPE为2时";
	public static final String ALIAS_FRZ_ACTIVE_DATE = "冻结生效日 当FRZ_TYPE不为2时";
	public static final String ALIAS_FRZ_END_DATE = "冻结到期日 当FRZ_TYPE不为2时 司法冻结首次冻结最长时间为6个月，续冻为3个月";
	public static final String ALIAS_FRZ_FILE_NO = "通知书编号 当FRZ_TYPE为0、1时";
	public static final String ALIAS_FRZ_AUTHORITY = "冻结机关 01：人民法院 02：税务机关 03：海关 04：人民检察院 05：公安机关 06：国家安全机关 07：军队保卫部门 08：监狱 09：走私犯罪侦查机关 10：工商行政管理机关 91：直销银行交易性冻结（T+0基金）";
	public static final String ALIAS_FRZ_AUTHORITY_NAME = "冻结机关名称";
	public static final String ALIAS_FRZ_AUTHORITY_CODE = "冻结机关代码 直销银行自动生成的冻结机关代码 见附录";
	public static final String ALIAS_FRZ_OPER = "经办人 当FRZ_TYPE为0、1时";
	public static final String ALIAS_FRZ_CERT_TYPE = "经办人证件代码 当FRZ_TYPE为0、1时";
	public static final String ALIAS_FRZ_CERT_NO = "经办人证件号码 当FRZ_TYPE为0、1时";
	public static final String ALIAS_FRZ_MODE = "冻结方式 当FRZ_TYPE为0或者1时 1：临时冻结 2：非临时冻结";
	public static final String ALIAS_PART_FRZ_TIME = "临时冻结时间（小时） 当FRZ_MODE为2时";
	public static final String ALIAS_FRZ_REASON = "经办原因";
	public static final String ALIAS_OPER = "操作员";
	public static final String ALIAS_OPER_TIME = "操作时间";
	public static final String ALIAS_REMARK1 = "备用";
	

	//columns START
    /**
     * 冻结编号       db_column: FRZ_NO 
     */ 	
	private java.lang.String frzNo;
    /**
     * 冻结序号 根据FRZ_NO从1开始       db_column: FRZ_ORDER_NUM 
     */ 	
	private java.lang.Integer frzOrderNum;
    /**
     * 冻结类型 0：冻结 1：续冻 2：解冻       db_column: FRZ_TYPE 
     */ 	
	private java.lang.String frzType;
    /**
     * 冻结状态 当FRZ_TYPE不为2时 见附录       db_column: FRZ_STAT 
     */ 	
	private java.lang.String frzStat;
    /**
     * 冻结金额 当FRZ_STAT为4时       db_column: FRZ_AMT 
     */ 	
	private java.math.BigDecimal frzAmt;
    /**
     * 解冻金额 当FRZ_TYPE为2时       db_column: UNFRZ_AMT 
     */ 	
	private java.math.BigDecimal unfrzAmt;
    /**
     * 冻结生效日 当FRZ_TYPE不为2时       db_column: FRZ_ACTIVE_DATE 
     */ 	
	private java.util.Date frzActiveDate;
    /**
     * 冻结到期日 当FRZ_TYPE不为2时 司法冻结首次冻结最长时间为6个月，续冻为3个月       db_column: FRZ_END_DATE 
     */ 	
	private java.util.Date frzEndDate;
    /**
     * 通知书编号 当FRZ_TYPE为0、1时       db_column: FRZ_FILE_NO 
     */ 	
	private java.lang.String frzFileNo;
    /**
     * 冻结机关 01：人民法院 02：税务机关 03：海关 04：人民检察院 05：公安机关 06：国家安全机关 07：军队保卫部门 08：监狱 09：走私犯罪侦查机关 10：工商行政管理机关 91：直销银行交易性冻结（T+0基金）       db_column: FRZ_AUTHORITY 
     */ 	
	private java.lang.String frzAuthority;
    /**
     * 冻结机关名称       db_column: FRZ_AUTHORITY_NAME 
     */ 	
	private java.lang.String frzAuthorityName;
    /**
     * 冻结机关代码 直销银行自动生成的冻结机关代码 见附录       db_column: FRZ_AUTHORITY_CODE 
     */ 	
	private java.lang.String frzAuthorityCode;
    /**
     * 经办人 当FRZ_TYPE为0、1时       db_column: FRZ_OPER 
     */ 	
	private java.lang.String frzOper;
    /**
     * 经办人证件代码 当FRZ_TYPE为0、1时       db_column: FRZ_CERT_TYPE 
     */ 	
	private java.lang.String frzCertType;
    /**
     * 经办人证件号码 当FRZ_TYPE为0、1时       db_column: FRZ_CERT_NO 
     */ 	
	private java.lang.String frzCertNo;
    /**
     * 冻结方式 当FRZ_TYPE为0或者1时 1：临时冻结 2：非临时冻结       db_column: FRZ_MODE 
     */ 	
	private java.lang.String frzMode;
    /**
     * 临时冻结时间（小时） 当FRZ_MODE为2时       db_column: PART_FRZ_TIME 
     */ 	
	private java.lang.Integer partFrzTime;
    /**
     * 经办原因       db_column: FRZ_REASON 
     */ 	
	private java.lang.String frzReason;
    /**
     * 操作员       db_column: OPER 
     */ 	
	private java.lang.String oper;
    /**
     * 操作时间       db_column: OPER_TIME 
     */ 	
	private java.util.Date operTime;
    /**
     * 备用       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
	//columns END


	
	
	public java.lang.String getFrzNo() {
		return this.frzNo;
	}
	
	public void setFrzNo(java.lang.String value) {
		this.frzNo = value;
	}
	
	
	public java.lang.Integer getFrzOrderNum() {
		return this.frzOrderNum;
	}
	
	public void setFrzOrderNum(java.lang.Integer value) {
		this.frzOrderNum = value;
	}
	
	
	public java.lang.String getFrzType() {
		return this.frzType;
	}
	
	public void setFrzType(java.lang.String value) {
		this.frzType = value;
	}
	
	
	public java.lang.String getFrzStat() {
		return this.frzStat;
	}
	
	public void setFrzStat(java.lang.String value) {
		this.frzStat = value;
	}
	
	
	public java.math.BigDecimal getFrzAmt() {
		return this.frzAmt;
	}
	
	public void setFrzAmt(java.math.BigDecimal value) {
		this.frzAmt = value;
	}
	
	
	public java.math.BigDecimal getUnfrzAmt() {
		return this.unfrzAmt;
	}
	
	public void setUnfrzAmt(java.math.BigDecimal value) {
		this.unfrzAmt = value;
	}
	
	
	public java.util.Date getFrzActiveDate() {
		return this.frzActiveDate;
	}
	
	public void setFrzActiveDate(java.util.Date value) {
		this.frzActiveDate = value;
	}
	
	
	public java.util.Date getFrzEndDate() {
		return this.frzEndDate;
	}
	
	public void setFrzEndDate(java.util.Date value) {
		this.frzEndDate = value;
	}
	
	
	public java.lang.String getFrzFileNo() {
		return this.frzFileNo;
	}
	
	public void setFrzFileNo(java.lang.String value) {
		this.frzFileNo = value;
	}
	
	
	public java.lang.String getFrzAuthority() {
		return this.frzAuthority;
	}
	
	public void setFrzAuthority(java.lang.String value) {
		this.frzAuthority = value;
	}
	
	
	public java.lang.String getFrzAuthorityName() {
		return this.frzAuthorityName;
	}
	
	public void setFrzAuthorityName(java.lang.String value) {
		this.frzAuthorityName = value;
	}
	
	
	public java.lang.String getFrzAuthorityCode() {
		return this.frzAuthorityCode;
	}
	
	public void setFrzAuthorityCode(java.lang.String value) {
		this.frzAuthorityCode = value;
	}
	
	
	public java.lang.String getFrzOper() {
		return this.frzOper;
	}
	
	public void setFrzOper(java.lang.String value) {
		this.frzOper = value;
	}
	
	
	public java.lang.String getFrzCertType() {
		return this.frzCertType;
	}
	
	public void setFrzCertType(java.lang.String value) {
		this.frzCertType = value;
	}
	
	
	public java.lang.String getFrzCertNo() {
		return this.frzCertNo;
	}
	
	public void setFrzCertNo(java.lang.String value) {
		this.frzCertNo = value;
	}
	
	
	public java.lang.String getFrzMode() {
		return this.frzMode;
	}
	
	public void setFrzMode(java.lang.String value) {
		this.frzMode = value;
	}
	
	
	public java.lang.Integer getPartFrzTime() {
		return this.partFrzTime;
	}
	
	public void setPartFrzTime(java.lang.Integer value) {
		this.partFrzTime = value;
	}
	
	
	public java.lang.String getFrzReason() {
		return this.frzReason;
	}
	
	public void setFrzReason(java.lang.String value) {
		this.frzReason = value;
	}
	
	
	public java.lang.String getOper() {
		return this.oper;
	}
	
	public void setOper(java.lang.String value) {
		this.oper = value;
	}
	
	
	public java.util.Date getOperTime() {
		return this.operTime;
	}
	
	public void setOperTime(java.util.Date value) {
		this.operTime = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	

	

}

