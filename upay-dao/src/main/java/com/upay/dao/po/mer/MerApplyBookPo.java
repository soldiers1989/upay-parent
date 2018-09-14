
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerApplyBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerApplyBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_APPLY_NO = "商户申请编号";
	public static final String ALIAS_MER_NAME = "商户名称";
	public static final String ALIAS_MER_WITHOUT_PWD_SIGN = "商户授权免密标志  0:非授权免密  1：授权免密";
	public static final String ALIAS_PAY_OPEN_FLAG = "支付功能开通标志  0：开通  1：不开通";
	public static final String ALIAS_MER_BUSI_TYPE = "商户业务类型 01:电商平台 02:线下门店 03:金融机构";
	public static final String ALIAS_CONTACT = "联系人姓名";
	public static final String ALIAS_CONTACT_TEL = "联系人电话";
	public static final String ALIAS_CONTACT_MOBILE = "联系人手机";
	public static final String ALIAS_CONTACT_EMAIL = "联系人邮件";
	public static final String ALIAS_MER_TEL = "商户电话";
	public static final String ALIAS_MER_FAX = "商户传真";
	public static final String ALIAS_MER_ADDR = "商户地址";
	public static final String ALIAS_MER_POSTAL_CODE = "商户邮编";
	public static final String ALIAS_WEBSITE_CODE = "网站备案号";
	public static final String ALIAS_WEBSITE_NAME = "网站名称";
	public static final String ALIAS_WEBSITE_DOMAIN = "网站域名";
	public static final String ALIAS_WEBSITE_SCOP = "网站经营范围";
	public static final String ALIAS_COMPANY_NAME = "公司名称";
	public static final String ALIAS_EGAL_PERSON_NAME = "企业法人代表姓名";
	public static final String ALIAS_EGAL_PERSON_ID_TYPE = "企业法人代表身份证件类型";
	public static final String ALIAS_EGAL_PERSON_ID_NO = "企业法人代表身份证件号码";
	public static final String ALIAS_COMPANY_ID_TYPE = "企业证件类型";
	public static final String ALIAS_COMPANY_ID_NO = "企业证件号";
	public static final String ALIAS_ORG_DEPT_NO = "组织机构代码证号";
	public static final String ALIAS_BUSI_LICENSE_ID = "营业执照号地址";
	public static final String ALIAS_APPLY_DATE = "商户申请日期";
	public static final String ALIAS_APPLY_STATE = "申请状态   0：待审核   1：审核中  2：审核通过  3：审核未通过";
	public static final String ALIAS_ANSWER_APPLY = "审核反馈  审核反馈意见";
	public static final String ALIAS_PARENT_MER_NO = "上级商户号";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_WF_ID = "工作流实例ID";
	public static final String ALIAS_WF_STATUS = "工作流状态，如：待审核、已审核、已退回，一般为两位数字编码";
	public static final String ALIAS_SUBMITTER = "提交人";
	public static final String ALIAS_SUBMITTER_NAME = "提交人姓名";
	public static final String ALIAS_SUBMIT_DATE = "提交日期";
	public static final String ALIAS_REVIEWER = "复核人";
	public static final String ALIAS_REVIEWER_NAME = "复核人";
	public static final String ALIAS_REVIEW_DATE = "复核日期";
	public static final String ALIAS_APPROVER = "审批人";
	public static final String ALIAS_APPROVER_NAME = "审批人姓名";
	public static final String ALIAS_APPROVE_DATE = "审批日期";
	public static final String ALIAS_AUDITOR = "审核人";
	public static final String ALIAS_AUDITOR_NAME = "审核人姓名";
	public static final String ALIAS_AUDIT_DATE = "审核日期";
	public static final String ALIAS_HEAD_AUDITOR = "领导审核人";
	public static final String ALIAS_HEAD_AUDITOR_NAME = "领导审核人";
	public static final String ALIAS_HEAD_AUDIT_DATE = "领导审核日期";
	public static final String ALIAS_SUB_MCH_ID = "微信商户识别码";
	public static final String ALIAS_EXTENSION_PARTY = "推广方";
	public static final String ALIAS_STATE = "状态   00：新增   11：修改";
	public static final String ALIAS_MER_STATE = "商户状态 0：启用，1：停用";
	public static final String ALIAS_ALIAS_NAME = "商户简称";
	public static final String ALIAS_PROMOTER_NAME = "业务员姓名";
	public static final String ALIAS_PROMOTER_IPHONE = "业务员手机";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	public static final String ALIAS_PROMOTER_DEPT_NAME = "业务员组织机构名称";
	public static final String ALIAS_QRCODE_USR_WECHAT = "微信二维码支付使用渠道    DIRECT:直连   UNIONPAY:银联   NETUNION:网联";
	public static final String ALIAS_QRCODE_USR_ALIPAY = "支付宝二维码支付使用渠道    DIRECT:直连   UNIONPAY:银联   NETUNION:网联";
	public static final String ALIAS_STL_ACCT_NO = "结算账户";
	public static final String ALIAS_STL_ACCT_TYPE = "结算账户类型";
	public static final String ALIAS_STL_ACCT_NAME = "结算账户户名";
	public static final String ALIAS_BANK_ID = "银行机构编号";
	public static final String ALIAS_BANK_NAME = "银行名称";
	public static final String ALIAS_KEY3DES = "密钥3DES";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_QR_MER = "银联商户号";
	public static final String ALIAS_QR_CODE = "银联商户二维码";
	public static final String ALIAS_QR_LIMIT_COUNT = "商户二维码支付次数";
	public static final String ALIAS_QR_VALID_TIME = "商户二维码有效期";
	public static final String ALIAS_QR_ORDER_NO = "商户二维码订单号";
	public static final String ALIAS_ALIPAY_MERCHANT_ID = "支付宝商户号";
	

	//columns START
    /**
     * 商户申请编号       db_column: MER_APPLY_NO 
     */ 	
	private java.lang.String merApplyNo;
    /**
     * 商户名称       db_column: MER_NAME 
     */ 	
	private java.lang.String merName;
    /**
     * 商户授权免密标志  0:非授权免密  1：授权免密       db_column: MER_WITHOUT_PWD_SIGN 
     */ 	
	private java.lang.String merWithoutPwdSign;
    /**
     * 支付功能开通标志  0：开通  1：不开通       db_column: PAY_OPEN_FLAG 
     */ 	
	private java.lang.String payOpenFlag;
    /**
     * 商户业务类型 01:电商平台 02:线下门店 03:金融机构       db_column: MER_BUSI_TYPE 
     */ 	
	private java.lang.String merBusiType;
    /**
     * 联系人姓名       db_column: CONTACT 
     */ 	
	private java.lang.String contact;
    /**
     * 联系人电话       db_column: CONTACT_TEL 
     */ 	
	private java.lang.String contactTel;
    /**
     * 联系人手机       db_column: CONTACT_MOBILE 
     */ 	
	private java.lang.String contactMobile;
    /**
     * 联系人邮件       db_column: CONTACT_EMAIL 
     */ 	
	private java.lang.String contactEmail;
    /**
     * 商户电话       db_column: MER_TEL 
     */ 	
	private java.lang.String merTel;
    /**
     * 商户传真       db_column: MER_FAX 
     */ 	
	private java.lang.String merFax;
    /**
     * 商户地址       db_column: MER_ADDR 
     */ 	
	private java.lang.String merAddr;
    /**
     * 商户邮编       db_column: MER_POSTAL_CODE 
     */ 	
	private java.lang.String merPostalCode;
    /**
     * 网站备案号       db_column: WEBSITE_CODE 
     */ 	
	private java.lang.String websiteCode;
    /**
     * 网站名称       db_column: WEBSITE_NAME 
     */ 	
	private java.lang.String websiteName;
    /**
     * 网站域名       db_column: WEBSITE_DOMAIN 
     */ 	
	private java.lang.String websiteDomain;
    /**
     * 网站经营范围       db_column: WEBSITE_SCOP 
     */ 	
	private java.lang.String websiteScop;
    /**
     * 公司名称       db_column: COMPANY_NAME 
     */ 	
	private java.lang.String companyName;
    /**
     * 企业法人代表姓名       db_column: EGAL_PERSON_NAME 
     */ 	
	private java.lang.String egalPersonName;
    /**
     * 企业法人代表身份证件类型       db_column: EGAL_PERSON_ID_TYPE 
     */ 	
	private java.lang.String egalPersonIdType;
    /**
     * 企业法人代表身份证件号码       db_column: EGAL_PERSON_ID_NO 
     */ 	
	private java.lang.String egalPersonIdNo;
    /**
     * 企业证件类型       db_column: COMPANY_ID_TYPE 
     */ 	
	private java.lang.String companyIdType;
    /**
     * 企业证件号       db_column: COMPANY_ID_NO 
     */ 	
	private java.lang.String companyIdNo;
    /**
     * 组织机构代码证号       db_column: ORG_DEPT_NO 
     */ 	
	private java.lang.String orgDeptNo;
    /**
     * 营业执照号地址       db_column: BUSI_LICENSE_ID 
     */ 	
	private java.lang.String busiLicenseId;
    /**
     * 商户申请日期       db_column: APPLY_DATE 
     */ 	
	private java.util.Date applyDate;
    /**
     * 申请状态   0：待审核   1：审核中  2：审核通过  3：审核未通过       db_column: APPLY_STATE 
     */ 	
	private java.lang.String applyState;
    /**
     * 审核反馈  审核反馈意见       db_column: ANSWER_APPLY 
     */ 	
	private java.lang.String answerApply;
    /**
     * 上级商户号       db_column: PARENT_MER_NO 
     */ 	
	private java.lang.String parentMerNo;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 工作流实例ID       db_column: WF_ID 
     */ 	
	private java.lang.String wfId;
    /**
     * 工作流状态，如：待审核、已审核、已退回，一般为两位数字编码       db_column: WF_STATUS 
     */ 	
	private java.lang.String wfStatus;
    /**
     * 提交人       db_column: SUBMITTER 
     */ 	
	private java.lang.String submitter;
    /**
     * 提交人姓名       db_column: SUBMITTER_NAME 
     */ 	
	private java.lang.String submitterName;
    /**
     * 提交日期       db_column: SUBMIT_DATE 
     */ 	
	private java.util.Date submitDate;
    /**
     * 复核人       db_column: REVIEWER 
     */ 	
	private java.lang.String reviewer;
    /**
     * 复核人       db_column: REVIEWER_NAME 
     */ 	
	private java.lang.String reviewerName;
    /**
     * 复核日期       db_column: REVIEW_DATE 
     */ 	
	private java.util.Date reviewDate;
    /**
     * 审批人       db_column: APPROVER 
     */ 	
	private java.lang.String approver;
    /**
     * 审批人姓名       db_column: APPROVER_NAME 
     */ 	
	private java.lang.String approverName;
    /**
     * 审批日期       db_column: APPROVE_DATE 
     */ 	
	private java.util.Date approveDate;
    /**
     * 审核人       db_column: AUDITOR 
     */ 	
	private java.lang.String auditor;
    /**
     * 审核人姓名       db_column: AUDITOR_NAME 
     */ 	
	private java.lang.String auditorName;
    /**
     * 审核日期       db_column: AUDIT_DATE 
     */ 	
	private java.util.Date auditDate;
    /**
     * 领导审核人       db_column: HEAD_AUDITOR 
     */ 	
	private java.lang.String headAuditor;
    /**
     * 领导审核人       db_column: HEAD_AUDITOR_NAME 
     */ 	
	private java.lang.String headAuditorName;
    /**
     * 领导审核日期       db_column: HEAD_AUDIT_DATE 
     */ 	
	private java.util.Date headAuditDate;
    /**
     * 微信商户识别码       db_column: SUB_MCH_ID 
     */ 	
	private java.lang.String subMchId;
    /**
     * 推广方       db_column: EXTENSION_PARTY 
     */ 	
	private java.lang.String extensionParty;
    /**
     * 状态   00：新增   11：修改       db_column: STATE 
     */ 	
	private java.lang.String state;
    /**
     * 商户状态 0：启用，1：停用       db_column: MER_STATE 
     */ 	
	private java.lang.String merState;
    /**
     * 商户简称       db_column: ALIAS_NAME 
     */ 	
	private java.lang.String aliasName;
    /**
     * 业务员姓名       db_column: PROMOTER_NAME 
     */ 	
	private java.lang.String promoterName;
    /**
     * 业务员手机       db_column: PROMOTER_IPHONE 
     */ 	
	private java.lang.String promoterIphone;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
    /**
     * 业务员组织机构名称       db_column: PROMOTER_DEPT_NAME 
     */ 	
	private java.lang.String promoterDeptName;
    /**
     * 微信二维码支付使用渠道    DIRECT:直连   UNIONPAY:银联   NETUNION:网联       db_column: QRCODE_USR_WECHAT 
     */ 	
	private java.lang.String qrcodeUsrWechat;
    /**
     * 支付宝二维码支付使用渠道    DIRECT:直连   UNIONPAY:银联   NETUNION:网联       db_column: QRCODE_USR_ALIPAY 
     */ 	
	private java.lang.String qrcodeUsrAlipay;
    /**
     * 结算账户       db_column: STL_ACCT_NO 
     */ 	
	private java.lang.String stlAcctNo;
    /**
     * 结算账户类型       db_column: STL_ACCT_TYPE 
     */ 	
	private java.lang.String stlAcctType;
    /**
     * 结算账户户名       db_column: STL_ACCT_NAME 
     */ 	
	private java.lang.String stlAcctName;
    /**
     * 银行机构编号       db_column: BANK_ID 
     */ 	
	private java.lang.String bankId;
    /**
     * 银行名称       db_column: BANK_NAME 
     */ 	
	private java.lang.String bankName;
    /**
     * 密钥3DES       db_column: KEY_3DES 
     */ 	
	private java.lang.String key3des;
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 银联商户号       db_column: QR_MER 
     */ 	
	private java.lang.String qrMer;
    /**
     * 银联商户二维码       db_column: QR_CODE 
     */ 	
	private java.lang.String qrCode;
    /**
     * 商户二维码支付次数       db_column: QR_LIMIT_COUNT 
     */ 	
	private java.lang.String qrLimitCount;
    /**
     * 商户二维码有效期       db_column: QR_VALID_TIME 
     */ 	
	private java.util.Date qrValidTime;
    /**
     * 商户二维码订单号       db_column: QR_ORDER_NO 
     */ 	
	private java.lang.String qrOrderNo;
    /**
     * 支付宝商户号       db_column: ALIPAY_MERCHANT_ID 
     */ 	
	private java.lang.String alipayMerchantId;
	//columns END


	
	
	public java.lang.String getMerApplyNo() {
		return this.merApplyNo;
	}
	
	public void setMerApplyNo(java.lang.String value) {
		this.merApplyNo = value;
	}
	
	
	public java.lang.String getMerName() {
		return this.merName;
	}
	
	public void setMerName(java.lang.String value) {
		this.merName = value;
	}
	
	
	public java.lang.String getMerWithoutPwdSign() {
		return this.merWithoutPwdSign;
	}
	
	public void setMerWithoutPwdSign(java.lang.String value) {
		this.merWithoutPwdSign = value;
	}
	
	
	public java.lang.String getPayOpenFlag() {
		return this.payOpenFlag;
	}
	
	public void setPayOpenFlag(java.lang.String value) {
		this.payOpenFlag = value;
	}
	
	
	public java.lang.String getMerBusiType() {
		return this.merBusiType;
	}
	
	public void setMerBusiType(java.lang.String value) {
		this.merBusiType = value;
	}
	
	
	public java.lang.String getContact() {
		return this.contact;
	}
	
	public void setContact(java.lang.String value) {
		this.contact = value;
	}
	
	
	public java.lang.String getContactTel() {
		return this.contactTel;
	}
	
	public void setContactTel(java.lang.String value) {
		this.contactTel = value;
	}
	
	
	public java.lang.String getContactMobile() {
		return this.contactMobile;
	}
	
	public void setContactMobile(java.lang.String value) {
		this.contactMobile = value;
	}
	
	
	public java.lang.String getContactEmail() {
		return this.contactEmail;
	}
	
	public void setContactEmail(java.lang.String value) {
		this.contactEmail = value;
	}
	
	
	public java.lang.String getMerTel() {
		return this.merTel;
	}
	
	public void setMerTel(java.lang.String value) {
		this.merTel = value;
	}
	
	
	public java.lang.String getMerFax() {
		return this.merFax;
	}
	
	public void setMerFax(java.lang.String value) {
		this.merFax = value;
	}
	
	
	public java.lang.String getMerAddr() {
		return this.merAddr;
	}
	
	public void setMerAddr(java.lang.String value) {
		this.merAddr = value;
	}
	
	
	public java.lang.String getMerPostalCode() {
		return this.merPostalCode;
	}
	
	public void setMerPostalCode(java.lang.String value) {
		this.merPostalCode = value;
	}
	
	
	public java.lang.String getWebsiteCode() {
		return this.websiteCode;
	}
	
	public void setWebsiteCode(java.lang.String value) {
		this.websiteCode = value;
	}
	
	
	public java.lang.String getWebsiteName() {
		return this.websiteName;
	}
	
	public void setWebsiteName(java.lang.String value) {
		this.websiteName = value;
	}
	
	
	public java.lang.String getWebsiteDomain() {
		return this.websiteDomain;
	}
	
	public void setWebsiteDomain(java.lang.String value) {
		this.websiteDomain = value;
	}
	
	
	public java.lang.String getWebsiteScop() {
		return this.websiteScop;
	}
	
	public void setWebsiteScop(java.lang.String value) {
		this.websiteScop = value;
	}
	
	
	public java.lang.String getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(java.lang.String value) {
		this.companyName = value;
	}
	
	
	public java.lang.String getEgalPersonName() {
		return this.egalPersonName;
	}
	
	public void setEgalPersonName(java.lang.String value) {
		this.egalPersonName = value;
	}
	
	
	public java.lang.String getEgalPersonIdType() {
		return this.egalPersonIdType;
	}
	
	public void setEgalPersonIdType(java.lang.String value) {
		this.egalPersonIdType = value;
	}
	
	
	public java.lang.String getEgalPersonIdNo() {
		return this.egalPersonIdNo;
	}
	
	public void setEgalPersonIdNo(java.lang.String value) {
		this.egalPersonIdNo = value;
	}
	
	
	public java.lang.String getCompanyIdType() {
		return this.companyIdType;
	}
	
	public void setCompanyIdType(java.lang.String value) {
		this.companyIdType = value;
	}
	
	
	public java.lang.String getCompanyIdNo() {
		return this.companyIdNo;
	}
	
	public void setCompanyIdNo(java.lang.String value) {
		this.companyIdNo = value;
	}
	
	
	public java.lang.String getOrgDeptNo() {
		return this.orgDeptNo;
	}
	
	public void setOrgDeptNo(java.lang.String value) {
		this.orgDeptNo = value;
	}
	
	
	public java.lang.String getBusiLicenseId() {
		return this.busiLicenseId;
	}
	
	public void setBusiLicenseId(java.lang.String value) {
		this.busiLicenseId = value;
	}
	
	
	public java.util.Date getApplyDate() {
		return this.applyDate;
	}
	
	public void setApplyDate(java.util.Date value) {
		this.applyDate = value;
	}
	
	
	public java.lang.String getApplyState() {
		return this.applyState;
	}
	
	public void setApplyState(java.lang.String value) {
		this.applyState = value;
	}
	
	
	public java.lang.String getAnswerApply() {
		return this.answerApply;
	}
	
	public void setAnswerApply(java.lang.String value) {
		this.answerApply = value;
	}
	
	
	public java.lang.String getParentMerNo() {
		return this.parentMerNo;
	}
	
	public void setParentMerNo(java.lang.String value) {
		this.parentMerNo = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getWfId() {
		return this.wfId;
	}
	
	public void setWfId(java.lang.String value) {
		this.wfId = value;
	}
	
	
	public java.lang.String getWfStatus() {
		return this.wfStatus;
	}
	
	public void setWfStatus(java.lang.String value) {
		this.wfStatus = value;
	}
	
	
	public java.lang.String getSubmitter() {
		return this.submitter;
	}
	
	public void setSubmitter(java.lang.String value) {
		this.submitter = value;
	}
	
	
	public java.lang.String getSubmitterName() {
		return this.submitterName;
	}
	
	public void setSubmitterName(java.lang.String value) {
		this.submitterName = value;
	}
	
	
	public java.util.Date getSubmitDate() {
		return this.submitDate;
	}
	
	public void setSubmitDate(java.util.Date value) {
		this.submitDate = value;
	}
	
	
	public java.lang.String getReviewer() {
		return this.reviewer;
	}
	
	public void setReviewer(java.lang.String value) {
		this.reviewer = value;
	}
	
	
	public java.lang.String getReviewerName() {
		return this.reviewerName;
	}
	
	public void setReviewerName(java.lang.String value) {
		this.reviewerName = value;
	}
	
	
	public java.util.Date getReviewDate() {
		return this.reviewDate;
	}
	
	public void setReviewDate(java.util.Date value) {
		this.reviewDate = value;
	}
	
	
	public java.lang.String getApprover() {
		return this.approver;
	}
	
	public void setApprover(java.lang.String value) {
		this.approver = value;
	}
	
	
	public java.lang.String getApproverName() {
		return this.approverName;
	}
	
	public void setApproverName(java.lang.String value) {
		this.approverName = value;
	}
	
	
	public java.util.Date getApproveDate() {
		return this.approveDate;
	}
	
	public void setApproveDate(java.util.Date value) {
		this.approveDate = value;
	}
	
	
	public java.lang.String getAuditor() {
		return this.auditor;
	}
	
	public void setAuditor(java.lang.String value) {
		this.auditor = value;
	}
	
	
	public java.lang.String getAuditorName() {
		return this.auditorName;
	}
	
	public void setAuditorName(java.lang.String value) {
		this.auditorName = value;
	}
	
	
	public java.util.Date getAuditDate() {
		return this.auditDate;
	}
	
	public void setAuditDate(java.util.Date value) {
		this.auditDate = value;
	}
	
	
	public java.lang.String getHeadAuditor() {
		return this.headAuditor;
	}
	
	public void setHeadAuditor(java.lang.String value) {
		this.headAuditor = value;
	}
	
	
	public java.lang.String getHeadAuditorName() {
		return this.headAuditorName;
	}
	
	public void setHeadAuditorName(java.lang.String value) {
		this.headAuditorName = value;
	}
	
	
	public java.util.Date getHeadAuditDate() {
		return this.headAuditDate;
	}
	
	public void setHeadAuditDate(java.util.Date value) {
		this.headAuditDate = value;
	}
	
	
	public java.lang.String getSubMchId() {
		return this.subMchId;
	}
	
	public void setSubMchId(java.lang.String value) {
		this.subMchId = value;
	}
	
	
	public java.lang.String getExtensionParty() {
		return this.extensionParty;
	}
	
	public void setExtensionParty(java.lang.String value) {
		this.extensionParty = value;
	}
	
	
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	
	
	public java.lang.String getMerState() {
		return this.merState;
	}
	
	public void setMerState(java.lang.String value) {
		this.merState = value;
	}
	
	
	public java.lang.String getAliasName() {
		return this.aliasName;
	}
	
	public void setAliasName(java.lang.String value) {
		this.aliasName = value;
	}
	
	
	public java.lang.String getPromoterName() {
		return this.promoterName;
	}
	
	public void setPromoterName(java.lang.String value) {
		this.promoterName = value;
	}
	
	
	public java.lang.String getPromoterIphone() {
		return this.promoterIphone;
	}
	
	public void setPromoterIphone(java.lang.String value) {
		this.promoterIphone = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	
	
	public java.lang.String getPromoterDeptName() {
		return this.promoterDeptName;
	}
	
	public void setPromoterDeptName(java.lang.String value) {
		this.promoterDeptName = value;
	}
	
	
	public java.lang.String getQrcodeUsrWechat() {
		return this.qrcodeUsrWechat;
	}
	
	public void setQrcodeUsrWechat(java.lang.String value) {
		this.qrcodeUsrWechat = value;
	}
	
	
	public java.lang.String getQrcodeUsrAlipay() {
		return this.qrcodeUsrAlipay;
	}
	
	public void setQrcodeUsrAlipay(java.lang.String value) {
		this.qrcodeUsrAlipay = value;
	}
	
	
	public java.lang.String getStlAcctNo() {
		return this.stlAcctNo;
	}
	
	public void setStlAcctNo(java.lang.String value) {
		this.stlAcctNo = value;
	}
	
	
	public java.lang.String getStlAcctType() {
		return this.stlAcctType;
	}
	
	public void setStlAcctType(java.lang.String value) {
		this.stlAcctType = value;
	}
	
	
	public java.lang.String getStlAcctName() {
		return this.stlAcctName;
	}
	
	public void setStlAcctName(java.lang.String value) {
		this.stlAcctName = value;
	}
	
	
	public java.lang.String getBankId() {
		return this.bankId;
	}
	
	public void setBankId(java.lang.String value) {
		this.bankId = value;
	}
	
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	
	public java.lang.String getKey3des() {
		return this.key3des;
	}
	
	public void setKey3des(java.lang.String value) {
		this.key3des = value;
	}
	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getQrMer() {
		return this.qrMer;
	}
	
	public void setQrMer(java.lang.String value) {
		this.qrMer = value;
	}
	
	
	public java.lang.String getQrCode() {
		return this.qrCode;
	}
	
	public void setQrCode(java.lang.String value) {
		this.qrCode = value;
	}
	
	
	public java.lang.String getQrLimitCount() {
		return this.qrLimitCount;
	}
	
	public void setQrLimitCount(java.lang.String value) {
		this.qrLimitCount = value;
	}
	
	
	public java.util.Date getQrValidTime() {
		return this.qrValidTime;
	}
	
	public void setQrValidTime(java.util.Date value) {
		this.qrValidTime = value;
	}
	
	
	public java.lang.String getQrOrderNo() {
		return this.qrOrderNo;
	}
	
	public void setQrOrderNo(java.lang.String value) {
		this.qrOrderNo = value;
	}
	
	
	public java.lang.String getAlipayMerchantId() {
		return this.alipayMerchantId;
	}
	
	public void setAlipayMerchantId(java.lang.String value) {
		this.alipayMerchantId = value;
	}
	

	

}

