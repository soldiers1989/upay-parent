
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrOperateListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrOperateList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户号";
	public static final String ALIAS_OPER_TIME = "操作时间";
	public static final String ALIAS_CHNL_ID = "操作渠道";
	public static final String ALIAS_BUSI_TRACK_NO = "业务跟踪号，渠道生成";
	public static final String ALIAS_OPER_SEQ = "操作流水号，系统自动生成";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_TRANS_NAME = "交易名称";
	public static final String ALIAS_OPER_STAT = "流水状态，01：交易成功  02：交易失败";
	public static final String ALIAS_RSP_CODE = "响应码";
	public static final String ALIAS_RSP_MSG = "响应信息";
	public static final String ALIAS_LOG_ID = "日志编号";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 用户号       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 操作时间       db_column: OPER_TIME 
     */ 	
	private java.util.Date operTime;
    /**
     * 操作渠道       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 业务跟踪号，渠道生成       db_column: BUSI_TRACK_NO 
     */ 	
	private java.lang.String busiTrackNo;
    /**
     * 操作流水号，系统自动生成       db_column: OPER_SEQ 
     */ 	
	private java.lang.String operSeq;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 交易名称       db_column: TRANS_NAME 
     */ 	
	private java.lang.String transName;
    /**
     * 流水状态，01：交易成功  02：交易失败       db_column: OPER_STAT 
     */ 	
	private java.lang.String operStat;
    /**
     * 响应码       db_column: RSP_CODE 
     */ 	
	private java.lang.String rspCode;
    /**
     * 响应信息       db_column: RSP_MSG 
     */ 	
	private java.lang.String rspMsg;
    /**
     * 日志编号       db_column: LOG_ID 
     */ 	
	private java.lang.String logId;
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
	
	
	public java.util.Date getOperTime() {
		return this.operTime;
	}
	
	public void setOperTime(java.util.Date value) {
		this.operTime = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getBusiTrackNo() {
		return this.busiTrackNo;
	}
	
	public void setBusiTrackNo(java.lang.String value) {
		this.busiTrackNo = value;
	}
	
	
	public java.lang.String getOperSeq() {
		return this.operSeq;
	}
	
	public void setOperSeq(java.lang.String value) {
		this.operSeq = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getTransName() {
		return this.transName;
	}
	
	public void setTransName(java.lang.String value) {
		this.transName = value;
	}
	
	
	public java.lang.String getOperStat() {
		return this.operStat;
	}
	
	public void setOperStat(java.lang.String value) {
		this.operStat = value;
	}
	
	
	public java.lang.String getRspCode() {
		return this.rspCode;
	}
	
	public void setRspCode(java.lang.String value) {
		this.rspCode = value;
	}
	
	
	public java.lang.String getRspMsg() {
		return this.rspMsg;
	}
	
	public void setRspMsg(java.lang.String value) {
		this.rspMsg = value;
	}
	
	
	public java.lang.String getLogId() {
		return this.logId;
	}
	
	public void setLogId(java.lang.String value) {
		this.logId = value;
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

