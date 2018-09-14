
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerPlatSettingPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerPlatSetting";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_PLAT_NO = "商户平台代码   关联商户基本信息表中的商户号";
	public static final String ALIAS_FILE_URL = "文件URL地址";
	public static final String ALIAS_REFER_URL = "来源url";
	public static final String ALIAS_SIGN_TYPE = "签名类型   0:MD5";
	public static final String ALIAS_FILE_ENCRY_TYPE = "文件加密类型     0:3DES";
	public static final String ALIAS_KEY3DES = "密钥3DES";
	public static final String ALIAS_CHECK_FILE_FORM_TYPE = "对账文件格式类型";
	public static final String ALIAS_SIGN_CHECK_FLAG = "签约对账标志";
	public static final String ALIAS_CLEAR_CHECK_FLAG = "清算对账标志";
	public static final String ALIAS_FILE_TRANSFER_MODE = "文件传输模式";
	public static final String ALIAS_FTP_IP = "FTPIP地址";
	public static final String ALIAS_FTP_PORT = "FTP端口";
	public static final String ALIAS_FTP_USER_NAME = "FTP用户名";
	public static final String ALIAS_FTP_PWD = "FTP密码";
	public static final String ALIAS_FILE_UPLOAD_PATH = "文件上传路径";
	public static final String ALIAS_MODIFY_USER = "修改人";
	public static final String ALIAS_DATE_LAST_MAINT = "最后更新日期";
	public static final String ALIAS_MER_REP_URL_B = "通知商户平台地址(后台)";
	public static final String ALIAS_MER_REP_URL_F = "通知商户平台地址(前台)";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 商户平台代码   关联商户基本信息表中的商户号       db_column: MER_PLAT_NO 
     */ 	
	private java.lang.String merPlatNo;
    /**
     * 文件URL地址       db_column: FILE_URL 
     */ 	
	private java.lang.String fileUrl;
    /**
     * 来源url       db_column: REFER_URL 
     */ 	
	private java.lang.String referUrl;
    /**
     * 签名类型   0:MD5       db_column: SIGN_TYPE 
     */ 	
	private java.lang.String signType;
    /**
     * 文件加密类型     0:3DES       db_column: FILE_ENCRY_TYPE 
     */ 	
	private java.lang.String fileEncryType;
    /**
     * 密钥3DES       db_column: KEY_3DES 
     */ 	
	private java.lang.String key3des;
    /**
     * 对账文件格式类型       db_column: CHECK_FILE_FORM_TYPE 
     */ 	
	private java.lang.String checkFileFormType;
    /**
     * 签约对账标志       db_column: SIGN_CHECK_FLAG 
     */ 	
	private java.lang.String signCheckFlag;
    /**
     * 清算对账标志       db_column: CLEAR_CHECK_FLAG 
     */ 	
	private java.lang.String clearCheckFlag;
    /**
     * 文件传输模式       db_column: FILE_TRANSFER_MODE 
     */ 	
	private java.lang.String fileTransferMode;
    /**
     * FTPIP地址       db_column: FTP_IP 
     */ 	
	private java.lang.String ftpIp;
    /**
     * FTP端口       db_column: FTP_PORT 
     */ 	
	private java.lang.String ftpPort;
    /**
     * FTP用户名       db_column: FTP_USER_NAME 
     */ 	
	private java.lang.String ftpUserName;
    /**
     * FTP密码       db_column: FTP_PWD 
     */ 	
	private java.lang.String ftpPwd;
    /**
     * 文件上传路径       db_column: FILE_UPLOAD_PATH 
     */ 	
	private java.lang.String fileUploadPath;
    /**
     * 修改人       db_column: MODIFY_USER 
     */ 	
	private java.lang.String modifyUser;
    /**
     * 最后更新日期       db_column: DATE_LAST_MAINT 
     */ 	
	private java.util.Date dateLastMaint;
    /**
     * 通知商户平台地址(后台)       db_column: MER_REP_URL_B 
     */ 	
	private java.lang.String merRepUrlB;
    /**
     * 通知商户平台地址(前台)       db_column: MER_REP_URL_F 
     */ 	
	private java.lang.String merRepUrlF;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.lang.String getMerPlatNo() {
		return this.merPlatNo;
	}
	
	public void setMerPlatNo(java.lang.String value) {
		this.merPlatNo = value;
	}
	
	
	public java.lang.String getFileUrl() {
		return this.fileUrl;
	}
	
	public void setFileUrl(java.lang.String value) {
		this.fileUrl = value;
	}
	
	
	public java.lang.String getReferUrl() {
		return this.referUrl;
	}
	
	public void setReferUrl(java.lang.String value) {
		this.referUrl = value;
	}
	
	
	public java.lang.String getSignType() {
		return this.signType;
	}
	
	public void setSignType(java.lang.String value) {
		this.signType = value;
	}
	
	
	public java.lang.String getFileEncryType() {
		return this.fileEncryType;
	}
	
	public void setFileEncryType(java.lang.String value) {
		this.fileEncryType = value;
	}
	
	
	public java.lang.String getKey3des() {
		return this.key3des;
	}
	
	public void setKey3des(java.lang.String value) {
		this.key3des = value;
	}
	
	
	public java.lang.String getCheckFileFormType() {
		return this.checkFileFormType;
	}
	
	public void setCheckFileFormType(java.lang.String value) {
		this.checkFileFormType = value;
	}
	
	
	public java.lang.String getSignCheckFlag() {
		return this.signCheckFlag;
	}
	
	public void setSignCheckFlag(java.lang.String value) {
		this.signCheckFlag = value;
	}
	
	
	public java.lang.String getClearCheckFlag() {
		return this.clearCheckFlag;
	}
	
	public void setClearCheckFlag(java.lang.String value) {
		this.clearCheckFlag = value;
	}
	
	
	public java.lang.String getFileTransferMode() {
		return this.fileTransferMode;
	}
	
	public void setFileTransferMode(java.lang.String value) {
		this.fileTransferMode = value;
	}
	
	
	public java.lang.String getFtpIp() {
		return this.ftpIp;
	}
	
	public void setFtpIp(java.lang.String value) {
		this.ftpIp = value;
	}
	
	
	public java.lang.String getFtpPort() {
		return this.ftpPort;
	}
	
	public void setFtpPort(java.lang.String value) {
		this.ftpPort = value;
	}
	
	
	public java.lang.String getFtpUserName() {
		return this.ftpUserName;
	}
	
	public void setFtpUserName(java.lang.String value) {
		this.ftpUserName = value;
	}
	
	
	public java.lang.String getFtpPwd() {
		return this.ftpPwd;
	}
	
	public void setFtpPwd(java.lang.String value) {
		this.ftpPwd = value;
	}
	
	
	public java.lang.String getFileUploadPath() {
		return this.fileUploadPath;
	}
	
	public void setFileUploadPath(java.lang.String value) {
		this.fileUploadPath = value;
	}
	
	
	public java.lang.String getModifyUser() {
		return this.modifyUser;
	}
	
	public void setModifyUser(java.lang.String value) {
		this.modifyUser = value;
	}
	
	
	public java.util.Date getDateLastMaint() {
		return this.dateLastMaint;
	}
	
	public void setDateLastMaint(java.util.Date value) {
		this.dateLastMaint = value;
	}
	
	
	public java.lang.String getMerRepUrlB() {
		return this.merRepUrlB;
	}
	
	public void setMerRepUrlB(java.lang.String value) {
		this.merRepUrlB = value;
	}
	
	
	public java.lang.String getMerRepUrlF() {
		return this.merRepUrlF;
	}
	
	public void setMerRepUrlF(java.lang.String value) {
		this.merRepUrlF = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

