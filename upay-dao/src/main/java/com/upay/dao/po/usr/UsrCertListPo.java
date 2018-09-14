
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrCertListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrCertList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CERT_APPLY_TIME = "实名认证申请时间";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_USER_CERT_LEVEL = "实名认证申请等级";
	public static final String ALIAS_CERT_CHNL_ID = "实名认证申请渠道";
	public static final String ALIAS_CERT_TYPE = "证件类型，仅支持身份证";
	public static final String ALIAS_CERT_NO = "证件号码";
	public static final String ALIAS_CERT_NAME = "用户姓名";
	public static final String ALIAS_CERT_FRONT = "身份证正面照文件名";
	public static final String ALIAS_CERT_BACK = "身份证反面照文件名";
	public static final String ALIAS_CERT_PERSON = "身份证本人手持照文件名";
	public static final String ALIAS_CERT_WEAK_WAY = "弱实名认证方式，当实名认证申请等级为弱实名时 01：临柜 02：核查身份证信息 03：上传身份证";
	public static final String ALIAS_CERT_STRONG_WAY = "强实名认证方式，当实名认证申请等级为强实名时 待确定";
	public static final String ALIAS_CERT_STAT = "实名认证申请状态， 0：已通过 1：待审核 2：已拒绝";
	public static final String ALIAS_CERT_ERR_REASON = "实名认证申请拒绝原因，当CERT_STAT为2时";
	public static final String ALIAS_CERT_TIME = "实名认证审核通过时间";
	public static final String ALIAS_APPROVE_OPER = "实名认证审核人";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_APPLY_REASON = "实名认证申请原因0:登陆密码重置 1：支付密码重置";
	public static final String ALIAS_WF_ID = "工作流实例ID";
	public static final String ALIAS_WF_STATUS = "工作流状态，如：待审核、已审核、已退回，一般为两位数字编码";
	public static final String ALIAS_SUBMITTER = "提交人";
	public static final String ALIAS_SUBMITTER_NAME = "提交人姓名";
	public static final String ALIAS_SUBMIT_DATE = "提交日期";
	public static final String ALIAS_REVIEWER = "复核人";
	public static final String ALIAS_REVIEWER_NAME = "复核人姓名";
	public static final String ALIAS_REVIEW_DATE = "复核日期";
	public static final String ALIAS_APPROVER = "审批人";
	public static final String ALIAS_APPROVER_NAME = "审批人姓名";
	public static final String ALIAS_APPROVE_DATE = "审批日期";
	public static final String ALIAS_AUDITOR = "审核人";
	public static final String ALIAS_AUDITOR_NAME = "审核人姓名";
	public static final String ALIAS_AUDIT_DATE = "审核日期";
	public static final String ALIAS_HEAD_AUDITOR = "领导审核人";
	public static final String ALIAS_HEAD_AUDITOR_NAME = "领导审核人姓名";
	public static final String ALIAS_HEAD_AUDIT_DATE = "领导审核日期";
	public static final String ALIAS_APPROVE_TYPE = "认证类型 01：免密授权 02：普通会员 03：商户会员";
	

	//columns START
    /**
     * 实名认证申请时间       db_column: CERT_APPLY_TIME 
     */ 	
	private java.util.Date certApplyTime;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 实名认证申请等级       db_column: USER_CERT_LEVEL 
     */ 	
	private java.lang.String userCertLevel;
    /**
     * 实名认证申请渠道       db_column: CERT_CHNL_ID 
     */ 	
	private java.lang.String certChnlId;
    /**
     * 证件类型，仅支持身份证       db_column: CERT_TYPE 
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
     * 身份证正面照文件名       db_column: CERT_FRONT 
     */ 	
	private java.lang.String certFront;
    /**
     * 身份证反面照文件名       db_column: CERT_BACK 
     */ 	
	private java.lang.String certBack;
    /**
     * 身份证本人手持照文件名       db_column: CERT_PERSON 
     */ 	
	private java.lang.String certPerson;
    /**
     * 弱实名认证方式，当实名认证申请等级为弱实名时 01：临柜 02：核查身份证信息 03：上传身份证       db_column: CERT_WEAK_WAY 
     */ 	
	private java.lang.String certWeakWay;
    /**
     * 强实名认证方式，当实名认证申请等级为强实名时 待确定       db_column: CERT_STRONG_WAY 
     */ 	
	private java.lang.String certStrongWay;
    /**
     * 实名认证申请状态， 0：已通过 1：待审核 2：已拒绝       db_column: CERT_STAT 
     */ 	
	private java.lang.String certStat;
    /**
     * 实名认证申请拒绝原因，当CERT_STAT为2时       db_column: CERT_ERR_REASON 
     */ 	
	private java.lang.String certErrReason;
    /**
     * 实名认证审核通过时间       db_column: CERT_TIME 
     */ 	
	private java.util.Date certTime;
    /**
     * 实名认证审核人       db_column: APPROVE_OPER 
     */ 	
	private java.lang.String approveOper;
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
    /**
     * 实名认证申请原因0:登陆密码重置 1：支付密码重置       db_column: APPLY_REASON 
     */ 	
	private java.lang.String applyReason;
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
     * 复核人姓名       db_column: REVIEWER_NAME 
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
     * 领导审核人姓名       db_column: HEAD_AUDITOR_NAME 
     */ 	
	private java.lang.String headAuditorName;
    /**
     * 领导审核日期       db_column: HEAD_AUDIT_DATE 
     */ 	
	private java.util.Date headAuditDate;
    /**
     * 认证类型 01：免密授权 02：普通会员 03：商户会员       db_column: APPROVE_TYPE 
     */ 	
	private java.lang.String approveType;
	//columns END


	
	
	public java.util.Date getCertApplyTime() {
		return this.certApplyTime;
	}
	
	public void setCertApplyTime(java.util.Date value) {
		this.certApplyTime = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getUserCertLevel() {
		return this.userCertLevel;
	}
	
	public void setUserCertLevel(java.lang.String value) {
		this.userCertLevel = value;
	}
	
	
	public java.lang.String getCertChnlId() {
		return this.certChnlId;
	}
	
	public void setCertChnlId(java.lang.String value) {
		this.certChnlId = value;
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
	
	
	public java.lang.String getCertFront() {
		return this.certFront;
	}
	
	public void setCertFront(java.lang.String value) {
		this.certFront = value;
	}
	
	
	public java.lang.String getCertBack() {
		return this.certBack;
	}
	
	public void setCertBack(java.lang.String value) {
		this.certBack = value;
	}
	
	
	public java.lang.String getCertPerson() {
		return this.certPerson;
	}
	
	public void setCertPerson(java.lang.String value) {
		this.certPerson = value;
	}
	
	
	public java.lang.String getCertWeakWay() {
		return this.certWeakWay;
	}
	
	public void setCertWeakWay(java.lang.String value) {
		this.certWeakWay = value;
	}
	
	
	public java.lang.String getCertStrongWay() {
		return this.certStrongWay;
	}
	
	public void setCertStrongWay(java.lang.String value) {
		this.certStrongWay = value;
	}
	
	
	public java.lang.String getCertStat() {
		return this.certStat;
	}
	
	public void setCertStat(java.lang.String value) {
		this.certStat = value;
	}
	
	
	public java.lang.String getCertErrReason() {
		return this.certErrReason;
	}
	
	public void setCertErrReason(java.lang.String value) {
		this.certErrReason = value;
	}
	
	
	public java.util.Date getCertTime() {
		return this.certTime;
	}
	
	public void setCertTime(java.util.Date value) {
		this.certTime = value;
	}
	
	
	public java.lang.String getApproveOper() {
		return this.approveOper;
	}
	
	public void setApproveOper(java.lang.String value) {
		this.approveOper = value;
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
	
	
	public java.lang.String getApplyReason() {
		return this.applyReason;
	}
	
	public void setApplyReason(java.lang.String value) {
		this.applyReason = value;
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
	
	
	public java.lang.String getApproveType() {
		return this.approveType;
	}
	
	public void setApproveType(java.lang.String value) {
		this.approveType = value;
	}
	

	

}

