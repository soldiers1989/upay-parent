
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrTransConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrTransConf";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TRANS_CODE = "交易代码SI_XXXXXXXX";
	public static final String ALIAS_TRANS_TYPE = "交易类型，存放公共字典表";
	public static final String ALIAS_TRANS_NAME = "交易名称";
	public static final String ALIAS_TRANS_DESC = "交易描述";
	public static final String ALIAS_SUMMARY = "摘要名称";
	public static final String ALIAS_IS_PRODUCT_TRANS = "是否为产品类交易 0：否  1：是";
	public static final String ALIAS_PRODUCT_KIND_CODE = "产品类别代码";
	public static final String ALIAS_TRANS_STAT = "交易状态  0：正常    1：关闭";
	public static final String ALIAS_CHNL_ID = "渠道代码";
	public static final String ALIAS_AMT_FLAG = "是否金融交易 1：是   0：否";
	public static final String ALIAS_EVENT_NO = "事件编号，当交易有事件需要处理时";
	public static final String ALIAS_POINTS_RETURN_FLAG = "积分返还标志  0：否   1：是";
	public static final String ALIAS_DC_FLAG = "借贷标志 D：借 C：贷";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 交易代码SI_XXXXXXXX       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 交易类型，存放公共字典表       db_column: TRANS_TYPE 
     */ 	
	private java.lang.String transType;
    /**
     * 交易名称       db_column: TRANS_NAME 
     */ 	
	private java.lang.String transName;
    /**
     * 交易描述       db_column: TRANS_DESC 
     */ 	
	private java.lang.String transDesc;
    /**
     * 摘要名称       db_column: SUMMARY 
     */ 	
	private java.lang.String summary;
    /**
     * 是否为产品类交易 0：否  1：是       db_column: IS_PRODUCT_TRANS 
     */ 	
	private java.lang.String isProductTrans;
    /**
     * 产品类别代码       db_column: PRODUCT_KIND_CODE 
     */ 	
	private java.lang.String productKindCode;
    /**
     * 交易状态  0：正常    1：关闭       db_column: TRANS_STAT 
     */ 	
	private java.lang.String transStat;
    /**
     * 渠道代码       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 是否金融交易 1：是   0：否       db_column: AMT_FLAG 
     */ 	
	private java.lang.String amtFlag;
    /**
     * 事件编号，当交易有事件需要处理时       db_column: EVENT_NO 
     */ 	
	private java.lang.String eventNo;
    /**
     * 积分返还标志  0：否   1：是       db_column: POINTS_RETURN_FLAG 
     */ 	
	private java.lang.String pointsReturnFlag;
    /**
     * 借贷标志 D：借 C：贷       db_column: DC_FLAG 
     */ 	
	private java.lang.String dcFlag;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改操作员       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
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


	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getTransType() {
		return this.transType;
	}
	
	public void setTransType(java.lang.String value) {
		this.transType = value;
	}
	
	
	public java.lang.String getTransName() {
		return this.transName;
	}
	
	public void setTransName(java.lang.String value) {
		this.transName = value;
	}
	
	
	public java.lang.String getTransDesc() {
		return this.transDesc;
	}
	
	public void setTransDesc(java.lang.String value) {
		this.transDesc = value;
	}
	
	
	public java.lang.String getSummary() {
		return this.summary;
	}
	
	public void setSummary(java.lang.String value) {
		this.summary = value;
	}
	
	
	public java.lang.String getIsProductTrans() {
		return this.isProductTrans;
	}
	
	public void setIsProductTrans(java.lang.String value) {
		this.isProductTrans = value;
	}
	
	
	public java.lang.String getProductKindCode() {
		return this.productKindCode;
	}
	
	public void setProductKindCode(java.lang.String value) {
		this.productKindCode = value;
	}
	
	
	public java.lang.String getTransStat() {
		return this.transStat;
	}
	
	public void setTransStat(java.lang.String value) {
		this.transStat = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getAmtFlag() {
		return this.amtFlag;
	}
	
	public void setAmtFlag(java.lang.String value) {
		this.amtFlag = value;
	}
	
	
	public java.lang.String getEventNo() {
		return this.eventNo;
	}
	
	public void setEventNo(java.lang.String value) {
		this.eventNo = value;
	}
	
	
	public java.lang.String getPointsReturnFlag() {
		return this.pointsReturnFlag;
	}
	
	public void setPointsReturnFlag(java.lang.String value) {
		this.pointsReturnFlag = value;
	}
	
	
	public java.lang.String getDcFlag() {
		return this.dcFlag;
	}
	
	public void setDcFlag(java.lang.String value) {
		this.dcFlag = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getLastOper() {
		return this.lastOper;
	}
	
	public void setLastOper(java.lang.String value) {
		this.lastOper = value;
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

