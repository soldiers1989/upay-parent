package com.upay.commons.constants;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午9:50:14
 */
public interface CommonBaseConstans_PAY {
    

    /** 中金卡类型       10个人借记卡 ，20个人贷记卡*/
    public static final String ZJ_PAY_CARD_TYPE_10="10";
    /** 中金卡类型       10个人借记卡 ，20个人贷记卡*/
    public static final String ZJ_PAY_CARD_TYPE_20="20";
    
    
    /** 中金证件类型      0身份证 */
    public static final String ZJ_PAPER_TYPE_0="0";
    
    /** 中金成功退款状态 */
    public static final String ZJ_REFUND_SUCCESS="2000";
    
    /** 微信退款状态 退款成功*/
    public static final String WEIXIN_REFUND_SUCCESS="SUCCESS";
    
    /**银联退款状态 退款成功*/
    public static final String UNION_REFUND_SUCCESS="00";



    /**银联代付状态 支付成功*/
    public static final String UNION_PAYMENT_SUCCESS="00";

    /** 微信退款状态 退款失败*/
    public static final String WEIXIN_REFUND_FAIL="FAIL";
    /** 微信退款状态：退款处理中 */
    public static final String WEIXIN_REFUND_PROCESSIONG="PROCESSING";
    /** 微信退款状态：转入代发 */
    public static final String WEIXIN_REFUND_CHANGE="CHANGE";
    
    
    /** 微信交易状态      SUCCESS—支付成功 ,REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,
     * REVOKED—已撤销(刷卡支付),USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败) */
    public static final String WEIXIN_TRANS_STAT_SUCCESS="SUCCESS";
    public static final String WEIXIN_TRANS_STAT_REFUND="REFUND";
    public static final String WEIXIN_TRANS_STAT_NOTPAY="NOTPAY";
    public static final String WEIXIN_TRANS_STAT_CLOSED="CLOSED";
    public static final String WEIXIN_TRANS_STAT_REVOKED="REVOKED";
    public static final String WEIXIN_TRANS_STAT_USERPAYING="USERPAYING";
    public static final String WEIXIN_TRANS_STAT_PAYERROR="PAYERROR";
    
    
	/** 核心交易类型：1 支付 */
	public static final String CORE_BANK_TRAN_TYPE_PAY = "1";
	/** 核心交易类型：2 退货 */
	public static final String CORE_BANK_TRAN_TYPE_RETURN = "2";
	/** 核心交易类型：3 提现 */
	public static final String CORE_BANK_TRAN_TYPE_WITHDRAWALS = "3";
	/** 核心交易类型：4 内转内 */
	public static final String CORE_BANK_TRAN_TYPE_INSIDE_TO_INSIDE = "4";

	/** 状态设置标识statFlag：0：支付成功 2:支付中 1：支付失败 */
	public static final String STAT_SET_FLAG_SUCC = "0";
	/** 状态设置标识：0：支付成功 2:支付中 1：支付失败 statFlag */
	public static final String STAT_SET_FLAG_PAYMENT = "2";
	/** 状态设置标识：0：支付成功 2:支付中 1：支付失败 statFlag */
	public static final String STAT_SET_FLAG_FAIL = "1";
	/** 流水登记标识  0：已登记  1：未登记*/
    public static final String REGIST_NOT = "1";
    /** 流水登记标识  0：已登记  1：未登记*/
    public static final String REGIST_YES = "0";
	
    /** 余额是否回退     Y-回退 */
    public static final String ACCAMT_REFUND_Y="Y";
    /** 余额是否回退     N-不回退 */
    public static final String ACCAMT_REFUND_N="N";

    
    /** 是否隔日退款      Y-隔日，N-当日 */
    public static final String REFUND_IS_TODAY="N";
    /** 是否隔日退款      Y-隔日，N-当日 */
    public static final String REFUND_NOT_TODAY="Y";
    
    
    /** 余额修改        0-增加，1-减去 */
    public static final String ACC_AMT_ADD="0";
    /** 余额修改        0-增加，1-减去 */
    public static final String ACC_AMT_SUB="1";
    
    /** 是否重复退款      0不是，1是 */
    public static final String REFUND_REPEAT_YES="1";
    public static final String REFUND_REPEAT_NO="0";
    
    
    /** 退款金额标识      0全额退款，1部分退款 */
    public static final String REFUND_AMT_ALL="0";
    /** 退款金额标识      0全额退款，1部分退款 */
    public static final String REFUND_AMT_POR="1";
    
    /** 外部接口返回退款状态      0退款成功，1退款失败，2退款 处理中 */
    public static final String OUTER_REFUND_STAT_SUCCESS="0";
    /** 外部接口返回退款状态      0退款成功，1退款失败，2退款 处理中 */
    public static final String OUTER_REFUND_STAT_FAIL="1";
    /** 外部接口返回退款状态      0退款成功，1退款失败，2退款 处理中 */
    public static final String OUTER_REFUND_STAT_DOING="2";
    
    /** 支付结果通知：交易状态：0成功，1失败，2处理中 */
    public static final String MER_PAY_NOTIFY_STAT_SUC="0";
    /** 支付结果通知：交易状态：0成功，1失败，2处理中 */
    public static final String MER_PAY_NOTIFY_STAT_FAIL="1";
    /** 支付结果通知：交易状态：0成功，1失败，2处理中 */
    public static final String MER_PAY_NOTIFY_STAT_DOING="2";




    //银联代付
    public static final String  UNION_PAY_PRIORITY_PAY="0001";
    //中金代付
    public static final String ZJ_PAY_PRIORITY_PAY="0002";
    //银联代收
    public static final String UNION_PAY_PRIORITY_COLLECTION="0003";
    //银联无跳转支付
    public static final String UNION_PAY_NON_JUMP_PAY="0004";
    //中金代收
    public static final String  ZJ_PAY_PRIORITY_COLLECTION="0005";





    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
