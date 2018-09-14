
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class ChkInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChkInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CHK_DATE = "对账日期  需要核对的账务日期";
	public static final String ALIAS_BENCHMARK_FLAG = "对账数据基准标志 01：第三方为主 02：核心为主";
	public static final String ALIAS_ORG_CODE = "单位代码对账的机构代码";
	public static final String ALIAS_CHK_TIME = "触发时间  对账发起的时间";
	public static final String ALIAS_BATCH_NO = "批次号  BATCH_JOBGRP_EXECUTION. BATCH_NO";
	public static final String ALIAS_CHK_ENTRY_CLS_CD = "对账分类编号";
	public static final String ALIAS_CHK_STAT = "对账状态 0：对账处理中 1：对账完成 2：对账失败";
	public static final String ALIAS_FILE_TYPE = "文件类型 0：下载 1：上传";
	public static final String ALIAS_FILE_DOWN_STAT = "文件处理状态 0：文件请求发送 1：文件请求登记 2：文件下载中 3：文件下载成功 9：文件下载失败";
	public static final String ALIAS_CHK_FILE = "对账文件名称";
	public static final String ALIAS_RESERVE1 = "备用字段1";
	public static final String ALIAS_RESERVE2 = "备用字段2";
	public static final String ALIAS_ORG_TYPE = "单位类型 001：统一支付平台 002：资金通道 003：银行机构 004：合作商户";
	

	//columns START
    /**
     * 对账日期  需要核对的账务日期       db_column: CHK_DATE 
     */ 	
	private java.util.Date chkDate;
    /**
     * 对账数据基准标志 01：第三方为主 02：核心为主       db_column: BENCHMARK_FLAG 
     */ 	
	private java.lang.String benchmarkFlag;
    /**
     * 单位代码对账的机构代码       db_column: ORG_CODE 
     */ 	
	private java.lang.String orgCode;
    /**
     * 触发时间  对账发起的时间       db_column: CHK_TIME 
     */ 	
	private java.util.Date chkTime;
    /**
     * 批次号  BATCH_JOBGRP_EXECUTION. BATCH_NO       db_column: BATCH_NO 
     */ 	
	private java.lang.String batchNo;
    /**
     * 对账分类编号       db_column: CHK_ENTRY_CLS_CD 
     */ 	
	private java.lang.String chkEntryClsCd;
    /**
     * 对账状态 0：对账处理中 1：对账完成 2：对账失败       db_column: CHK_STAT 
     */ 	
	private java.lang.String chkStat;
    /**
     * 文件类型 0：下载 1：上传       db_column: FILE_TYPE 
     */ 	
	private java.lang.String fileType;
    /**
     * 文件处理状态 0：文件请求发送 1：文件请求登记 2：文件下载中 3：文件下载成功 9：文件下载失败       db_column: FILE_DOWN_STAT 
     */ 	
	private java.lang.String fileDownStat;
    /**
     * 对账文件名称       db_column: CHK_FILE 
     */ 	
	private java.lang.String chkFile;
    /**
     * 备用字段1       db_column: RESERVE1 
     */ 	
	private java.lang.String reserve1;
    /**
     * 备用字段2       db_column: RESERVE2 
     */ 	
	private java.lang.String reserve2;
    /**
     * 单位类型 001：统一支付平台 002：资金通道 003：银行机构 004：合作商户       db_column: ORG_TYPE 
     */ 	
	private java.lang.String orgType;
	//columns END


	
	
	public java.util.Date getChkDate() {
		return this.chkDate;
	}
	
	public void setChkDate(java.util.Date value) {
		this.chkDate = value;
	}
	
	
	public java.lang.String getBenchmarkFlag() {
		return this.benchmarkFlag;
	}
	
	public void setBenchmarkFlag(java.lang.String value) {
		this.benchmarkFlag = value;
	}
	
	
	public java.lang.String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(java.lang.String value) {
		this.orgCode = value;
	}
	
	
	public java.util.Date getChkTime() {
		return this.chkTime;
	}
	
	public void setChkTime(java.util.Date value) {
		this.chkTime = value;
	}
	
	
	public java.lang.String getBatchNo() {
		return this.batchNo;
	}
	
	public void setBatchNo(java.lang.String value) {
		this.batchNo = value;
	}
	
	
	public java.lang.String getChkEntryClsCd() {
		return this.chkEntryClsCd;
	}
	
	public void setChkEntryClsCd(java.lang.String value) {
		this.chkEntryClsCd = value;
	}
	
	
	public java.lang.String getChkStat() {
		return this.chkStat;
	}
	
	public void setChkStat(java.lang.String value) {
		this.chkStat = value;
	}
	
	
	public java.lang.String getFileType() {
		return this.fileType;
	}
	
	public void setFileType(java.lang.String value) {
		this.fileType = value;
	}
	
	
	public java.lang.String getFileDownStat() {
		return this.fileDownStat;
	}
	
	public void setFileDownStat(java.lang.String value) {
		this.fileDownStat = value;
	}
	
	
	public java.lang.String getChkFile() {
		return this.chkFile;
	}
	
	public void setChkFile(java.lang.String value) {
		this.chkFile = value;
	}
	
	
	public java.lang.String getReserve1() {
		return this.reserve1;
	}
	
	public void setReserve1(java.lang.String value) {
		this.reserve1 = value;
	}
	
	
	public java.lang.String getReserve2() {
		return this.reserve2;
	}
	
	public void setReserve2(java.lang.String value) {
		this.reserve2 = value;
	}
	
	
	public java.lang.String getOrgType() {
		return this.orgType;
	}
	
	public void setOrgType(java.lang.String value) {
		this.orgType = value;
	}
	

	

}

