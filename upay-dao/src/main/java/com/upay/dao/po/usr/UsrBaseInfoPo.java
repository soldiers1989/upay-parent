
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrBaseInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrBaseInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户ID 系统自动生成";
	public static final String ALIAS_CERT_TYPE = "证件类型 仅支持身份证";
	public static final String ALIAS_CERT_NO = "证件号码";
	public static final String ALIAS_CERT_NAME = "用户姓名";
	public static final String ALIAS_CERT_EXP_FLAG = "证件有效期类型 0：非长期 1：长期";
	public static final String ALIAS_CERT_EXP_BEGIN = "证件开始有效期";
	public static final String ALIAS_CERT_EXP_END = "证件结束有效期";
	public static final String ALIAS_SEX = "性别 见附录";
	public static final String ALIAS_BIRTHDAY = "出生日期";
	public static final String ALIAS_COUNTRY = "国籍 见附录";
	public static final String ALIAS_NATION = "民族 见附录";
	public static final String ALIAS_BACKGROUND = "政治面貌";
	public static final String ALIAS_RELIGION = "宗教信仰";
	public static final String ALIAS_MARRIAGE = "婚姻状况 1：已婚 2：未婚";
	public static final String ALIAS_EDU_BG = "教育程度";
	public static final String ALIAS_JOB = "职业";
	public static final String ALIAS_ADDRESS_CODE = "省市区地址代码";
	public static final String ALIAS_ADDRESS_REAL = "实际地址";
	public static final String ALIAS_EMAIL = "Email";
	public static final String ALIAS_QQ = "QQ号";
	public static final String ALIAS_WEIXIN = "微信号";
	public static final String ALIAS_SIGN = "签名";
	public static final String ALIAS_PRE_INFO = "预留信息";
	public static final String ALIAS_ECIF_CUST_NO = "ECIF客户号";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 用户ID 系统自动生成       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 证件类型 仅支持身份证       db_column: CERT_TYPE 
     */ 	
	private java.lang.String certType;
    /**
     * 证件号码       db_column: CERT_NO 
     */ 	
	private java.lang.String certNo;
    /**
     * 用户姓名       db_column: CERT_NAME 
     */ 	
	private java.lang.String certName;
    /**
     * 证件有效期类型 0：非长期 1：长期       db_column: CERT_EXP_FLAG 
     */ 	
	private java.lang.String certExpFlag;
    /**
     * 证件开始有效期       db_column: CERT_EXP_BEGIN 
     */ 	
	private java.util.Date certExpBegin;
    /**
     * 证件结束有效期       db_column: CERT_EXP_END 
     */ 	
	private java.util.Date certExpEnd;
    /**
     * 性别 见附录       db_column: SEX 
     */ 	
	private java.lang.String sex;
    /**
     * 出生日期       db_column: BIRTHDAY 
     */ 	
	private java.util.Date birthday;
    /**
     * 国籍 见附录       db_column: COUNTRY 
     */ 	
	private java.lang.String country;
    /**
     * 民族 见附录       db_column: NATION 
     */ 	
	private java.lang.String nation;
    /**
     * 政治面貌       db_column: BACKGROUND 
     */ 	
	private java.lang.String background;
    /**
     * 宗教信仰       db_column: RELIGION 
     */ 	
	private java.lang.String religion;
    /**
     * 婚姻状况 1：已婚 2：未婚       db_column: MARRIAGE 
     */ 	
	private java.lang.String marriage;
    /**
     * 教育程度       db_column: EDU_BG 
     */ 	
	private java.lang.String eduBg;
    /**
     * 职业       db_column: JOB 
     */ 	
	private java.lang.String job;
    /**
     * 省市区地址代码       db_column: ADDRESS_CODE 
     */ 	
	private java.lang.String addressCode;
    /**
     * 实际地址       db_column: ADDRESS_REAL 
     */ 	
	private java.lang.String addressReal;
    /**
     * Email       db_column: EMAIL 
     */ 	
	private java.lang.String email;
    /**
     * QQ号       db_column: QQ 
     */ 	
	private java.lang.String qq;
    /**
     * 微信号       db_column: WEIXIN 
     */ 	
	private java.lang.String weixin;
    /**
     * 签名       db_column: SIGN 
     */ 	
	private java.lang.String sign;
    /**
     * 预留信息       db_column: PRE_INFO 
     */ 	
	private java.lang.String preInfo;
    /**
     * ECIF客户号       db_column: ECIF_CUST_NO 
     */ 	
	private java.lang.String ecifCustNo;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
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


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getCertType() {
		return this.certType;
	}
	
	public void setCertType(java.lang.String value) {
		this.certType = value;
	}
	
	
	public java.lang.String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(java.lang.String value) {
		this.certNo = value;
	}
	
	
	public java.lang.String getCertName() {
		return this.certName;
	}
	
	public void setCertName(java.lang.String value) {
		this.certName = value;
	}
	
	
	public java.lang.String getCertExpFlag() {
		return this.certExpFlag;
	}
	
	public void setCertExpFlag(java.lang.String value) {
		this.certExpFlag = value;
	}
	
	
	public java.util.Date getCertExpBegin() {
		return this.certExpBegin;
	}
	
	public void setCertExpBegin(java.util.Date value) {
		this.certExpBegin = value;
	}
	
	
	public java.util.Date getCertExpEnd() {
		return this.certExpEnd;
	}
	
	public void setCertExpEnd(java.util.Date value) {
		this.certExpEnd = value;
	}
	
	
	public java.lang.String getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
	
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	
	
	public java.lang.String getCountry() {
		return this.country;
	}
	
	public void setCountry(java.lang.String value) {
		this.country = value;
	}
	
	
	public java.lang.String getNation() {
		return this.nation;
	}
	
	public void setNation(java.lang.String value) {
		this.nation = value;
	}
	
	
	public java.lang.String getBackground() {
		return this.background;
	}
	
	public void setBackground(java.lang.String value) {
		this.background = value;
	}
	
	
	public java.lang.String getReligion() {
		return this.religion;
	}
	
	public void setReligion(java.lang.String value) {
		this.religion = value;
	}
	
	
	public java.lang.String getMarriage() {
		return this.marriage;
	}
	
	public void setMarriage(java.lang.String value) {
		this.marriage = value;
	}
	
	
	public java.lang.String getEduBg() {
		return this.eduBg;
	}
	
	public void setEduBg(java.lang.String value) {
		this.eduBg = value;
	}
	
	
	public java.lang.String getJob() {
		return this.job;
	}
	
	public void setJob(java.lang.String value) {
		this.job = value;
	}
	
	
	public java.lang.String getAddressCode() {
		return this.addressCode;
	}
	
	public void setAddressCode(java.lang.String value) {
		this.addressCode = value;
	}
	
	
	public java.lang.String getAddressReal() {
		return this.addressReal;
	}
	
	public void setAddressReal(java.lang.String value) {
		this.addressReal = value;
	}
	
	
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	
	public java.lang.String getQq() {
		return this.qq;
	}
	
	public void setQq(java.lang.String value) {
		this.qq = value;
	}
	
	
	public java.lang.String getWeixin() {
		return this.weixin;
	}
	
	public void setWeixin(java.lang.String value) {
		this.weixin = value;
	}
	
	
	public java.lang.String getSign() {
		return this.sign;
	}
	
	public void setSign(java.lang.String value) {
		this.sign = value;
	}
	
	
	public java.lang.String getPreInfo() {
		return this.preInfo;
	}
	
	public void setPreInfo(java.lang.String value) {
		this.preInfo = value;
	}
	
	
	public java.lang.String getEcifCustNo() {
		return this.ecifCustNo;
	}
	
	public void setEcifCustNo(java.lang.String value) {
		this.ecifCustNo = value;
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

