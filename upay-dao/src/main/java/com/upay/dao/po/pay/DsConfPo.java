
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class DsConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "DsConf";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_CALLER_CHNL_ID = "调用方渠道ID";
	public static final String ALIAS_PAY_ROUTE = "资金通道";
	

	//columns START
   
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 调用方渠道ID       db_column: CALLER_CHNL_ID 
     */ 	
	private java.lang.String callerChnlId;
    /**
     * 资金通道       db_column: PAY_ROUTE 
     */ 	
	private java.lang.String payRoute;
	//columns END


	
	
	
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getCallerChnlId() {
		return this.callerChnlId;
	}
	
	public void setCallerChnlId(java.lang.String value) {
		this.callerChnlId = value;
	}
	
	
	public java.lang.String getPayRoute() {
		return this.payRoute;
	}
	
	public void setPayRoute(java.lang.String value) {
		this.payRoute = value;
	}
	

	

}

