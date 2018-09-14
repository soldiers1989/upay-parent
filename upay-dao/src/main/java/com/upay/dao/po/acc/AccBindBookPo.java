
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccBindBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccBindBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BIND_TIME = "绑定时间";
	public static final String ALIAS_BIND_CHNL_ID = "绑定渠道 见附录";
	public static final String ALIAS_VACCT_NO = "虚拟账户账号";
	public static final String ALIAS_DEFAULT_FLAG = "默认绑卡标志 0：否 1：是 当电子账户仅允许绑定一个账户时，默认为1";
	public static final String ALIAS_BIND_ACCT_TYPE = "绑定账户类型 见附录 暂时仅支持借记卡的绑定";
	public static final String ALIAS_VBIND_BANK_FLAG = "虚拟账户绑定账户银行类别 1：本行账户 2：他行账户";
	public static final String ALIAS_VBIND_BANK_CODE = "虚拟账户绑定账户行号";
	public static final String ALIAS_VBIND_BANK_NAME = "虚拟账户绑定账户行名";
	public static final String ALIAS_VBIND_ACCT_NO = "虚拟账户绑定账户账号";
	public static final String ALIAS_VBIND_OPEN_CODE = "虚拟账户绑定账户开户机构号";
	public static final String ALIAS_BIND_STAT = "绑定标志 0：待激活 1：已绑定 2：过期失效（绑定过激活期未激活） 3：解除绑定";
	public static final String ALIAS_VBIND_FLAG = "绑卡方式 1：第三方鉴权 2：打款验证";
	public static final String ALIAS_THIRD_AUTH_CHNL = "第三方鉴权渠道见附录通道代码 (ROUTE_CODE)";
	public static final String ALIAS_TRANSFER_VERIFY_AMT = "打款验证金额 当E_OPEN_FLAG为2时";
	public static final String ALIAS_TRANSFER_VERIFY_DATE = "打款验证截至日期";
	public static final String ALIAS_ACTIVE_TIME = "激活时间 当BIND_STAT为1时";
	public static final String ALIAS_UNBIND_TIME = "解绑时间 当BIND_STAT为3时";
	public static final String ALIAS_UNBIND_CHNL_ID = "解绑渠道 见附录";
	public static final String ALIAS_UNBIND_REASON_FLAG = "解绑原因标志 当BIND_STAT为3时 1：绑定卡变更 2：电子账户销户 3：普通解绑（绑多张卡适用）";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_VBIND_OPEN_NAME = "虚拟账户绑定账户开户行名";
	public static final String ALIAS_CARD_FIST_PAY = "首次支付标志：0 首次，1非首次";
	public static final String ALIAS_CARD_BIN = "卡bin";
	public static final String ALIAS_RESERVE_MOBILE = "预留手机号";
	public static final String ALIAS_VALID_DATE = "有效期";
	public static final String ALIAS_CVN2 = "安全码";
	

	//columns START
    /**
     * 绑定时间       db_column: BIND_TIME 
     */ 	
	private java.util.Date bindTime;
    /**
     * 绑定渠道 见附录       db_column: BIND_CHNL_ID 
     */ 	
	private java.lang.String bindChnlId;
    /**
     * 虚拟账户账号       db_column: V_ACCT_NO 
     */ 	
	private java.lang.String vacctNo;
    /**
     * 默认绑卡标志 0：否 1：是 当电子账户仅允许绑定一个账户时，默认为1       db_column: DEFAULT_FLAG 
     */ 	
	private java.lang.String defaultFlag;
    /**
     * 绑定账户类型 见附录 暂时仅支持借记卡的绑定       db_column: BIND_ACCT_TYPE 
     */ 	
	private java.lang.String bindAcctType;
    /**
     * 虚拟账户绑定账户银行类别 1：本行账户 2：他行账户       db_column: V_BIND_BANK_FLAG 
     */ 	
	private java.lang.String vbindBankFlag;
    /**
     * 虚拟账户绑定账户行号       db_column: V_BIND_BANK_CODE 
     */ 	
	private java.lang.String vbindBankCode;
    /**
     * 虚拟账户绑定账户行名       db_column: V_BIND_BANK_NAME 
     */ 	
	private java.lang.String vbindBankName;
    /**
     * 虚拟账户绑定账户账号       db_column: V_BIND_ACCT_NO 
     */ 	
	private java.lang.String vbindAcctNo;
    /**
     * 虚拟账户绑定账户开户机构号       db_column: V_BIND_OPEN_CODE 
     */ 	
	private java.lang.String vbindOpenCode;
    /**
     * 绑定标志 0：待激活 1：已绑定 2：过期失效（绑定过激活期未激活） 3：解除绑定       db_column: BIND_STAT 
     */ 	
	private java.lang.String bindStat;
    /**
     * 绑卡方式 1：第三方鉴权 2：打款验证       db_column: V_BIND_FLAG 
     */ 	
	private java.lang.String vbindFlag;
    /**
     * 第三方鉴权渠道见附录通道代码 (ROUTE_CODE)       db_column: THIRD_AUTH_CHNL 
     */ 	
	private java.lang.String thirdAuthChnl;
    /**
     * 打款验证金额 当E_OPEN_FLAG为2时       db_column: TRANSFER_VERIFY_AMT 
     */ 	
	private java.math.BigDecimal transferVerifyAmt;
    /**
     * 打款验证截至日期       db_column: TRANSFER_VERIFY_DATE 
     */ 	
	private java.util.Date transferVerifyDate;
    /**
     * 激活时间 当BIND_STAT为1时       db_column: ACTIVE_TIME 
     */ 	
	private java.util.Date activeTime;
    /**
     * 解绑时间 当BIND_STAT为3时       db_column: UNBIND_TIME 
     */ 	
	private java.util.Date unbindTime;
    /**
     * 解绑渠道 见附录       db_column: UNBIND_CHNL_ID 
     */ 	
	private java.lang.String unbindChnlId;
    /**
     * 解绑原因标志 当BIND_STAT为3时 1：绑定卡变更 2：电子账户销户 3：普通解绑（绑多张卡适用）       db_column: UNBIND_REASON_FLAG 
     */ 	
	private java.lang.String unbindReasonFlag;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 虚拟账户绑定账户开户行名       db_column: V_BIND_OPEN_NAME 
     */ 	
	private java.lang.String vbindOpenName;
    /**
     * 首次支付标志：0 首次，1非首次       db_column: CARD_FIST_PAY 
     */ 	
	private java.lang.String cardFistPay;
    /**
     * 卡bin       db_column: CARD_BIN 
     */ 	
	private java.lang.String cardBin;
    /**
     * 预留手机号       db_column: RESERVE_MOBILE 
     */ 	
	private java.lang.String reserveMobile;
    /**
     * 有效期       db_column: VALID_DATE 
     */ 	
	private java.lang.String validDate;
    /**
     * 安全码       db_column: CVN2 
     */ 	
	private java.lang.String cvn2;
	//columns END


	
	
	public java.util.Date getBindTime() {
		return this.bindTime;
	}
	
	public void setBindTime(java.util.Date value) {
		this.bindTime = value;
	}
	
	
	public java.lang.String getBindChnlId() {
		return this.bindChnlId;
	}
	
	public void setBindChnlId(java.lang.String value) {
		this.bindChnlId = value;
	}
	
	
	public java.lang.String getVacctNo() {
		return this.vacctNo;
	}
	
	public void setVacctNo(java.lang.String value) {
		this.vacctNo = value;
	}
	
	
	public java.lang.String getDefaultFlag() {
		return this.defaultFlag;
	}
	
	public void setDefaultFlag(java.lang.String value) {
		this.defaultFlag = value;
	}
	
	
	public java.lang.String getBindAcctType() {
		return this.bindAcctType;
	}
	
	public void setBindAcctType(java.lang.String value) {
		this.bindAcctType = value;
	}
	
	
	public java.lang.String getVbindBankFlag() {
		return this.vbindBankFlag;
	}
	
	public void setVbindBankFlag(java.lang.String value) {
		this.vbindBankFlag = value;
	}
	
	
	public java.lang.String getVbindBankCode() {
		return this.vbindBankCode;
	}
	
	public void setVbindBankCode(java.lang.String value) {
		this.vbindBankCode = value;
	}
	
	
	public java.lang.String getVbindBankName() {
		return this.vbindBankName;
	}
	
	public void setVbindBankName(java.lang.String value) {
		this.vbindBankName = value;
	}
	
	
	public java.lang.String getVbindAcctNo() {
		return this.vbindAcctNo;
	}
	
	public void setVbindAcctNo(java.lang.String value) {
		this.vbindAcctNo = value;
	}
	
	
	public java.lang.String getVbindOpenCode() {
		return this.vbindOpenCode;
	}
	
	public void setVbindOpenCode(java.lang.String value) {
		this.vbindOpenCode = value;
	}
	
	
	public java.lang.String getBindStat() {
		return this.bindStat;
	}
	
	public void setBindStat(java.lang.String value) {
		this.bindStat = value;
	}
	
	
	public java.lang.String getVbindFlag() {
		return this.vbindFlag;
	}
	
	public void setVbindFlag(java.lang.String value) {
		this.vbindFlag = value;
	}
	
	
	public java.lang.String getThirdAuthChnl() {
		return this.thirdAuthChnl;
	}
	
	public void setThirdAuthChnl(java.lang.String value) {
		this.thirdAuthChnl = value;
	}
	
	
	public java.math.BigDecimal getTransferVerifyAmt() {
		return this.transferVerifyAmt;
	}
	
	public void setTransferVerifyAmt(java.math.BigDecimal value) {
		this.transferVerifyAmt = value;
	}
	
	
	public java.util.Date getTransferVerifyDate() {
		return this.transferVerifyDate;
	}
	
	public void setTransferVerifyDate(java.util.Date value) {
		this.transferVerifyDate = value;
	}
	
	
	public java.util.Date getActiveTime() {
		return this.activeTime;
	}
	
	public void setActiveTime(java.util.Date value) {
		this.activeTime = value;
	}
	
	
	public java.util.Date getUnbindTime() {
		return this.unbindTime;
	}
	
	public void setUnbindTime(java.util.Date value) {
		this.unbindTime = value;
	}
	
	
	public java.lang.String getUnbindChnlId() {
		return this.unbindChnlId;
	}
	
	public void setUnbindChnlId(java.lang.String value) {
		this.unbindChnlId = value;
	}
	
	
	public java.lang.String getUnbindReasonFlag() {
		return this.unbindReasonFlag;
	}
	
	public void setUnbindReasonFlag(java.lang.String value) {
		this.unbindReasonFlag = value;
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
	
	
	public java.lang.String getVbindOpenName() {
		return this.vbindOpenName;
	}
	
	public void setVbindOpenName(java.lang.String value) {
		this.vbindOpenName = value;
	}
	
	
	public java.lang.String getCardFistPay() {
		return this.cardFistPay;
	}
	
	public void setCardFistPay(java.lang.String value) {
		this.cardFistPay = value;
	}
	
	
	public java.lang.String getCardBin() {
		return this.cardBin;
	}
	
	public void setCardBin(java.lang.String value) {
		this.cardBin = value;
	}
	
	
	public java.lang.String getReserveMobile() {
		return this.reserveMobile;
	}
	
	public void setReserveMobile(java.lang.String value) {
		this.reserveMobile = value;
	}
	
	
	public java.lang.String getValidDate() {
		return this.validDate;
	}
	
	public void setValidDate(java.lang.String value) {
		this.validDate = value;
	}
	
	
	public java.lang.String getCvn2() {
		return this.cvn2;
	}
	
	public void setCvn2(java.lang.String value) {
		this.cvn2 = value;
	}
	

	

}

