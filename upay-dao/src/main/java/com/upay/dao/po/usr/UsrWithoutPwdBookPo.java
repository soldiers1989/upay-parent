
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrWithoutPwdBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrWithoutPwdBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BIND_TIME = "绑定时间";
	public static final String ALIAS_BIND_CHNL_ID = "绑定渠道 见附录";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_MER_NO = "授权商户号 授权免密登陆的商户号";
	public static final String ALIAS_MOBILE = "手机号";
	public static final String ALIAS_BIND_STAT = "绑定标志 0：待激活 1：已绑定 2：过期失效（绑定过激活期未激活） 3：解除绑定";
	public static final String ALIAS_UNION_PLAT_TYPE = "合作平台类型 01:电商平台 02:第三方支付平台 03:独立商户 04:微信 05:微博 06:QQ";
	public static final String ALIAS_UNION_PLAT_NO = "合作平台账号";
	public static final String ALIAS_ACTIVE_TIME = "激活时间 当BIND_STAT为1时";
	public static final String ALIAS_UNBIND_TIME = "解绑时间 当BIND_STAT为3时";
	public static final String ALIAS_UNBIND_CHNL_ID = "解绑渠道 见附录";
	public static final String ALIAS_UNBIND_REASON_FLAG = "解绑原因标志 当BIND_STAT为3时 1：绑定卡变更 2：电子账户销户 3：普通解绑（绑多张卡适用）";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

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
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 授权商户号 授权免密登陆的商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 手机号       db_column: MOBILE 
     */ 	
	private java.lang.String mobile;
    /**
     * 绑定标志 0：待激活 1：已绑定 2：过期失效（绑定过激活期未激活） 3：解除绑定       db_column: BIND_STAT 
     */ 	
	private java.lang.String bindStat;
    /**
     * 合作平台类型 01:电商平台 02:第三方支付平台 03:独立商户 04:微信 05:微博 06:QQ       db_column: UNION_PLAT_TYPE 
     */ 	
	private java.lang.String unionPlatType;
    /**
     * 合作平台账号       db_column: UNION_PLAT_NO 
     */ 	
	private java.lang.String unionPlatNo;
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
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	
	
	public java.lang.String getBindStat() {
		return this.bindStat;
	}
	
	public void setBindStat(java.lang.String value) {
		this.bindStat = value;
	}
	
	
	public java.lang.String getUnionPlatType() {
		return this.unionPlatType;
	}
	
	public void setUnionPlatType(java.lang.String value) {
		this.unionPlatType = value;
	}
	
	
	public java.lang.String getUnionPlatNo() {
		return this.unionPlatNo;
	}
	
	public void setUnionPlatNo(java.lang.String value) {
		this.unionPlatNo = value;
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
	

	

}

