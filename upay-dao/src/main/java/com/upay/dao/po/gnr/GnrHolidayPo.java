
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrHolidayPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrHoliday";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_HOLIDAY_TYPE = "假日类型";
	public static final String ALIAS_PROD_CODE = "产品代码";
	public static final String ALIAS_YEAR = "假日年份";
	public static final String ALIAS_HOLIDAY_DATE = "假日日期";
	public static final String ALIAS_REMARK = "备注";
	

	//columns START
    /**
     * 假日类型       db_column: HOLIDAY_TYPE 
     */ 	
	private String holidayType;
    /**
     * 产品代码       db_column: PROD_CODE 
     */ 	
	private String prodCode;
    /**
     * 假日年份       db_column: YEAR 
     */ 	
	private String year;
    /**
     * 假日日期       db_column: HOLIDAY_DATE 
     */ 	
	private java.util.Date holidayDate;
    /**
     * 备注       db_column: REMARK 
     */ 	
	private String remark;
	//columns END


	
	
	public String getHolidayType() {
		return this.holidayType;
	}
	
	public void setHolidayType(String value) {
		this.holidayType = value;
	}
	
	
	public String getProdCode() {
		return this.prodCode;
	}
	
	public void setProdCode(String value) {
		this.prodCode = value;
	}
	
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String value) {
		this.year = value;
	}
	
	
	public java.util.Date getHolidayDate() {
		return this.holidayDate;
	}
	
	public void setHolidayDate(java.util.Date value) {
		this.holidayDate = value;
	}
	
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	

	

}

