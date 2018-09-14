
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerNotifiyPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerNotifiy";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_TRANS_DATE = "交易日期";
	public static final String ALIAS_NOTIFY_TYPE = "通知类型，暂定为00";
	public static final String ALIAS_NOTIFY_STATUS = "通知状态";
	public static final String ALIAS_TRANS_SEQ = "支付平台流水号";
	public static final String ALIAS_PLAIN = "交易数据明文";
	public static final String ALIAS_SIGN_ATURE = "交易数据签名";
	public static final String ALIAS_MER_URL = "商户通知地址";
	public static final String ALIAS_SEND_TIMES = "发送次数";
	public static final String ALIAS_OUTER_ORDER_NO = "商户订单号";
	public static final String ALIAS_NOTIFY_ID = "通知ID，支付时，返回的通知ID";
	

	//columns START
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 交易日期       db_column: TRANS_DATE 
     */ 	
	private java.util.Date transDate;
    /**
     * 通知类型，暂定为00       db_column: NOTIFY_TYPE 
     */ 	
	private java.lang.String notifyType;
    /**
     * 通知状态       db_column: NOTIFY_STATUS 
     */ 	
	private java.lang.String notifyStatus;
    /**
     * 支付平台流水号       db_column: TRANS_SEQ 
     */ 	
	private java.lang.String transSeq;
    /**
     * 交易数据明文       db_column: PLAIN 
     */ 	
	private java.lang.String plain;
    /**
     * 交易数据签名       db_column: SIGN_ATURE 
     */ 	
	private java.lang.String signAture;
    /**
     * 商户通知地址       db_column: MER_URL 
     */ 	
	private java.lang.String merUrl;
    /**
     * 发送次数       db_column: SEND_TIMES 
     */ 	
	private java.lang.Integer sendTimes;
    /**
     * 商户订单号       db_column: OUTER_ORDER_NO 
     */ 	
	private java.lang.String outerOrderNo;
    /**
     * 通知ID，支付时，返回的通知ID       db_column: NOTIFY_ID 
     */ 	
	private java.lang.String notifyId;
	//columns END


	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.util.Date getTransDate() {
		return this.transDate;
	}
	
	public void setTransDate(java.util.Date value) {
		this.transDate = value;
	}
	
	
	public java.lang.String getNotifyType() {
		return this.notifyType;
	}
	
	public void setNotifyType(java.lang.String value) {
		this.notifyType = value;
	}
	
	
	public java.lang.String getNotifyStatus() {
		return this.notifyStatus;
	}
	
	public void setNotifyStatus(java.lang.String value) {
		this.notifyStatus = value;
	}
	
	
	public java.lang.String getTransSeq() {
		return this.transSeq;
	}
	
	public void setTransSeq(java.lang.String value) {
		this.transSeq = value;
	}
	
	
	public java.lang.String getPlain() {
		return this.plain;
	}
	
	public void setPlain(java.lang.String value) {
		this.plain = value;
	}
	
	
	public java.lang.String getSignAture() {
		return this.signAture;
	}
	
	public void setSignAture(java.lang.String value) {
		this.signAture = value;
	}
	
	
	public java.lang.String getMerUrl() {
		return this.merUrl;
	}
	
	public void setMerUrl(java.lang.String value) {
		this.merUrl = value;
	}
	
	
	public java.lang.Integer getSendTimes() {
		return this.sendTimes;
	}
	
	public void setSendTimes(java.lang.Integer value) {
		this.sendTimes = value;
	}
	
	
	public java.lang.String getOuterOrderNo() {
		return this.outerOrderNo;
	}
	
	public void setOuterOrderNo(java.lang.String value) {
		this.outerOrderNo = value;
	}
	
	
	public java.lang.String getNotifyId() {
		return this.notifyId;
	}
	
	public void setNotifyId(java.lang.String value) {
		this.notifyId = value;
	}
	

	

}

