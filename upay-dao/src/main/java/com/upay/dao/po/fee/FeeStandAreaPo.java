
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeStandAreaPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeStandArea";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_FEE_CODE = "费用代码";
	public static final String ALIAS_FEE_MODEL_CODE = "费用模型代码 01-元/笔 02-定额+百分比 03-百分比";
	public static final String ALIAS_AREA_COUNT = "区间计数   区间第几段";
	public static final String ALIAS_AREA_LOW = "区间下限  ";
	public static final String ALIAS_AREA_UP = "区间上限";
	public static final String ALIAS_BEG_DATE = "生效日期  格式：YYYYMMdd";
	public static final String ALIAS_END_DATE = "失效日期  格式：YYYYMMdd";
	public static final String ALIAS_STATUS = "状态   0-失效 1-有效默认1";
	public static final String ALIAS_OPER = "操作员   内管用户ID";
	public static final String ALIAS_LAST_UPDATE_TIME = "操作时间   yyyy-mm-dd hh24:mi:ss";
	public static final String ALIAS_REMARK = "备用  ";
	public static final String ALIAS_FIX_FEE = "单笔固定金额";
	public static final String ALIAS_RATION_FEE = "按交易金额比例";
	public static final String ALIAS_AREA_VALUE = "区间值";
	

	//columns START
    /**
     * 费用代码       db_column: FEE_CODE 
     */ 	
	private java.lang.String feeCode;
    /**
     * 费用模型代码 01-元/笔 02-定额+百分比 03-百分比       db_column: FEE_MODEL_CODE 
     */ 	
	private java.lang.String feeModelCode;
    /**
     * 区间计数   区间第几段       db_column: AREA_COUNT 
     */ 	
	private java.lang.Integer areaCount;
    /**
     * 区间下限         db_column: AREA_LOW 
     */ 	
	private java.math.BigDecimal areaLow;
    /**
     * 区间上限       db_column: AREA_UP 
     */ 	
	private java.math.BigDecimal areaUp;
    /**
     * 生效日期  格式：YYYYMMdd       db_column: BEG_DATE 
     */ 	
	private java.util.Date begDate;
    /**
     * 失效日期  格式：YYYYMMdd       db_column: END_DATE 
     */ 	
	private java.util.Date endDate;
    /**
     * 状态   0-失效 1-有效默认1       db_column: STATUS 
     */ 	
	private java.lang.String status;
    /**
     * 操作员   内管用户ID       db_column: OPER 
     */ 	
	private java.lang.String oper;
    /**
     * 操作时间   yyyy-mm-dd hh24:mi:ss       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用         db_column: REMARK 
     */ 	
	private java.lang.String remark;
    /**
     * 单笔固定金额       db_column: FIX_FEE 
     */ 	
	private java.math.BigDecimal fixFee;
    /**
     * 按交易金额比例       db_column: RATION_FEE 
     */ 	
	private java.math.BigDecimal rationFee;
    /**
     * 区间值       db_column: AREA_VALUE 
     */ 	
	private java.math.BigDecimal areaValue;
	//columns END


	
	
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	
	public void setFeeCode(java.lang.String value) {
		this.feeCode = value;
	}
	
	
	public java.lang.String getFeeModelCode() {
		return this.feeModelCode;
	}
	
	public void setFeeModelCode(java.lang.String value) {
		this.feeModelCode = value;
	}
	
	
	public java.lang.Integer getAreaCount() {
		return this.areaCount;
	}
	
	public void setAreaCount(java.lang.Integer value) {
		this.areaCount = value;
	}
	
	
	public java.math.BigDecimal getAreaLow() {
		return this.areaLow;
	}
	
	public void setAreaLow(java.math.BigDecimal value) {
		this.areaLow = value;
	}
	
	
	public java.math.BigDecimal getAreaUp() {
		return this.areaUp;
	}
	
	public void setAreaUp(java.math.BigDecimal value) {
		this.areaUp = value;
	}
	
	
	public java.util.Date getBegDate() {
		return this.begDate;
	}
	
	public void setBegDate(java.util.Date value) {
		this.begDate = value;
	}
	
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	
	public java.lang.String getOper() {
		return this.oper;
	}
	
	public void setOper(java.lang.String value) {
		this.oper = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	
	public java.math.BigDecimal getFixFee() {
		return this.fixFee;
	}
	
	public void setFixFee(java.math.BigDecimal value) {
		this.fixFee = value;
	}
	
	
	public java.math.BigDecimal getRationFee() {
		return this.rationFee;
	}
	
	public void setRationFee(java.math.BigDecimal value) {
		this.rationFee = value;
	}
	
	
	public java.math.BigDecimal getAreaValue() {
		return this.areaValue;
	}
	
	public void setAreaValue(java.math.BigDecimal value) {
		this.areaValue = value;
	}
	

	

}

