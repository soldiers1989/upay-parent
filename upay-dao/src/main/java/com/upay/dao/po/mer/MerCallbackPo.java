
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerCallbackPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerCallback";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ORI_TRANS_SEQ = "回调流水号";
	public static final String ALIAS_MER_N0 = "商户号";
	public static final String ALIAS_ORDER_NO = "订单号";
	public static final String ALIAS_CALL_BACK_STATUS = "回调处理状态";
	

	//columns START
    /**
     * 回调流水号       db_column: ORI_TRANS_SEQ 
     */ 	
	private java.lang.String oriTransSeq;
    /**
     * 商户号       db_column: MER_N0 
     */ 	
	private java.lang.String merN0;
    /**
     * 订单号       db_column: ORDER_NO 
     */ 	
	private java.lang.String orderNo;
    /**
     * 回调处理状态       db_column: CALL_BACK_STATUS 
     */ 	
	private java.lang.String callBackStatus;
	//columns END


	
	
	public java.lang.String getOriTransSeq() {
		return this.oriTransSeq;
	}
	
	public void setOriTransSeq(java.lang.String value) {
		this.oriTransSeq = value;
	}
	
	
	public java.lang.String getMerN0() {
		return this.merN0;
	}
	
	public void setMerN0(java.lang.String value) {
		this.merN0 = value;
	}
	
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	
	public java.lang.String getCallBackStatus() {
		return this.callBackStatus;
	}
	
	public void setCallBackStatus(java.lang.String value) {
		this.callBackStatus = value;
	}
	

	

}

