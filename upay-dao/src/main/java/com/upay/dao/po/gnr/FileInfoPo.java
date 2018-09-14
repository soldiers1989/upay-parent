
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class FileInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FileInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_REAL_TRANS_DATE = "实际交易日期";
	public static final String ALIAS_BATCH_NO = "批次号 BATCH_JOBGRP_EXECUTION.BATCH_NO";
	public static final String ALIAS_TASK_CODE = "任务代码见附录";
	public static final String ALIAS_FILE_NAME = "文件名称";
	public static final String ALIAS_FILE_TYPE = "文件类型   0：上传1：下载 ";
	public static final String ALIAS_FILE_SERINO = "文件生成顺序";
	public static final String ALIAS_FILE_STAT = "文件状态  0：初始化 1：文件创建成功 2：文件创建失败 3：文件上传成功 4：文件上传失败 5：文件下载成功 6：文件下载失败";
	public static final String ALIAS_CREATE_TIME = "创建时间  ";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 实际交易日期       db_column: REAL_TRANS_DATE 
     */ 	
	private java.util.Date realTransDate;
    /**
     * 批次号 BATCH_JOBGRP_EXECUTION.BATCH_NO       db_column: BATCH_NO 
     */ 	
	private java.lang.String batchNo;
    /**
     * 任务代码见附录       db_column: TASK_CODE 
     */ 	
	private java.lang.String taskCode;
    /**
     * 文件名称       db_column: FILE_NAME 
     */ 	
	private java.lang.String fileName;
    /**
     * 文件类型   0：上传1：下载        db_column: FILE_TYPE 
     */ 	
	private java.lang.String fileType;
    /**
     * 文件生成顺序       db_column: FILE_SERINO 
     */ 	
	private java.lang.String fileSerino;
    /**
     * 文件状态  0：初始化 1：文件创建成功 2：文件创建失败 3：文件上传成功 4：文件上传失败 5：文件下载成功 6：文件下载失败       db_column: FILE_STAT 
     */ 	
	private java.lang.String fileStat;
    /**
     * 创建时间         db_column: CREATE_TIME 
     */ 	
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: UPDATE_TIME 
     */ 	
	private java.util.Date updateTime;
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


	
	
	public java.util.Date getRealTransDate() {
		return this.realTransDate;
	}
	
	public void setRealTransDate(java.util.Date value) {
		this.realTransDate = value;
	}
	
	
	public java.lang.String getBatchNo() {
		return this.batchNo;
	}
	
	public void setBatchNo(java.lang.String value) {
		this.batchNo = value;
	}
	
	
	public java.lang.String getTaskCode() {
		return this.taskCode;
	}
	
	public void setTaskCode(java.lang.String value) {
		this.taskCode = value;
	}
	
	
	public java.lang.String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(java.lang.String value) {
		this.fileName = value;
	}
	
	
	public java.lang.String getFileType() {
		return this.fileType;
	}
	
	public void setFileType(java.lang.String value) {
		this.fileType = value;
	}
	
	
	public java.lang.String getFileSerino() {
		return this.fileSerino;
	}
	
	public void setFileSerino(java.lang.String value) {
		this.fileSerino = value;
	}
	
	
	public java.lang.String getFileStat() {
		return this.fileStat;
	}
	
	public void setFileStat(java.lang.String value) {
		this.fileStat = value;
	}
	
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
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

