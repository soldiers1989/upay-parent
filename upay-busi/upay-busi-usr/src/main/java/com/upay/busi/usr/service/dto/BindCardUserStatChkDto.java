package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 用户绑卡开户状态检查DTO
 * @author zhangjianfeng
 * @since 2016/8/9
 */
public class BindCardUserStatChkDto extends BaseDto {
	
	/** 证件号码 */
	private String certId;
	
	/** 证件用户名 */
	private String certName;
	
	/** 证件类型 */
	private String certType;
	
	/** 用户认证等级 */
	private String userCertLevel;

	/** 绑定账户类型 */
	private String bindAcctType;

	/** 虚拟账户绑定账户行号 */
	private String eBindBankCode;

	/** 虚拟账户绑定账户账号 */
	private String eBindAcctNo;

	/** 虚拟账户绑定账户开户机构 */
	private String eBindOpenCode;

	/** 银行预留手机号 */
	private String eBindMobile;

	/** 第三方鉴权渠道 */
	private String thirdAuthChnl;

	/** 是否校验手机号，取值：Y-校验，N-不校验 */
	private String isCheckmobilePhone;

	/** 是否校验户名，取值：Y-校验，N-不校验 */
	private String isCheckAccountName;

	/** 是否校验证件号，取值：Y-校验，N-不校验 */
	private String isCheckCertificateNo;

	/** 核心证件类型 */
	private String coreCertType;


	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getUserCertLevel() {
		return userCertLevel;
	}

	public void setUserCertLevel(String userCertLevel) {
		this.userCertLevel = userCertLevel;
	}

	public String getBindAcctType() {
		return bindAcctType;
	}

	public void setBindAcctType(String bindAcctType) {
		this.bindAcctType = bindAcctType;
	}

	public String geteBindBankCode() {
		return eBindBankCode;
	}

	public void seteBindBankCode(String eBindBankCode) {
		this.eBindBankCode = eBindBankCode;
	}

	public String geteBindAcctNo() {
		return eBindAcctNo;
	}

	public void seteBindAcctNo(String eBindAcctNo) {
		this.eBindAcctNo = eBindAcctNo;
	}

	public String geteBindOpenCode() {
		return eBindOpenCode;
	}

	public void seteBindOpenCode(String eBindOpenCode) {
		this.eBindOpenCode = eBindOpenCode;
	}

	public String geteBindMobile() {
		return eBindMobile;
	}

	public void seteBindMobile(String eBindMobile) {
		this.eBindMobile = eBindMobile;
	}

	public String getThirdAuthChnl() {
		return thirdAuthChnl;
	}

	public void setThirdAuthChnl(String thirdAuthChnl) {
		this.thirdAuthChnl = thirdAuthChnl;
	}

	public String getIsCheckmobilePhone() {
		return isCheckmobilePhone;
	}

	public void setIsCheckmobilePhone(String isCheckmobilePhone) {
		this.isCheckmobilePhone = isCheckmobilePhone;
	}

	public String getIsCheckAccountName() {
		return isCheckAccountName;
	}

	public void setIsCheckAccountName(String isCheckAccountName) {
		this.isCheckAccountName = isCheckAccountName;
	}

	public String getIsCheckCertificateNo() {
		return isCheckCertificateNo;
	}

	public void setIsCheckCertificateNo(String isCheckCertificateNo) {
		this.isCheckCertificateNo = isCheckCertificateNo;
	}

	public String getCoreCertType() {
		return coreCertType;
	}

	public void setCoreCertType(String coreCertType) {
		this.coreCertType = coreCertType;
	}
}
