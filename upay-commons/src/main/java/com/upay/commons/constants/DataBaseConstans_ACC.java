package com.upay.commons.constants;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午9:58:16
 */
public interface DataBaseConstans_ACC {
	public static final String DATE_FORMAT_YYYYMMDDHHmmSS="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDDHHmmSS_1="yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YYYYMMDD="yyyyMMdd";
	public static final String DATE_FORMAT_YYYY_MM_DD="yyyy-MM-dd";
	public static final String DATE_FORMAT_HHMMSS="HHmmss";
	
	public static final String IS_PAYEE_ACCT="Y";// Y：不加手续费扣减账户余额   转账时收款方余额扣减不需要加手续费
	public static final String ADD_FEE_AMT_YES="Y";// Y：账户余额加减时需要扣手续费
	public static final String ADD_FEE_AMT_NO="N";// N：账户余额加减时不需要加续费
	
	/**中金返回绑定状态**/
	public static final String ACCT_NO_BINDED="30";
	
	/**资金通道  核心**/
	public static final String ROUTE_CODE_CORE="0001";
	
	/**资金通道  中金**/
	public static final String ROUTE_CODE_ZJ="0004";
	
	/**中金备用金账户**/
	public static final String ZJ_ACCT_NO="ZJ_ACCT_NO";
	
	/**网银标识**/
	public static final String ONLINE_BANK_FLAG="1";
	
	
	/**虚拟账户类型**/
	public static final String ACC_V_BOOK_TYPE="VACC";
	
	public static final String ACC_V_BOOK_SET_FLAG_DEFAULT="0";
    /** 绑止首次 0 首次 */
    public static final String ACC_V_BOOK_BIND_FIRST = "0";
    /** 账户状态:1非首次 */
    public static final String ACC_V_BOOK_BIND_NOFIRST = "1";

    /** 账户状态:0正常 */
    public static final String ACC_V_BOOK_VSTAT_NOMAL = "0";
    /** 账户状态:1待激活 */
    public static final String ACC_V_BOOK_VSTAT_UNACTIVEL = "1";
    /** 账户状态:2睡眠 */
    public static final String ACC_V_BOOK_VSTAT_SLEEP = "2";
    /** 账户状态:9已注销 */
    public static final String ACC_V_BOOK_VSTAT_CANCEL = "9";

    /** 账户冻结状态:0正常 */
    public static final String ACC_V_BOOK_FRZSTAT_NORMAL = "0";
    /** 账户冻结状态:1双向冻结（不收不付） */
    public static final String ACC_V_BOOK_FRZSTAT_FROZEN = "1";
    /** 账户冻结状态:2借方冻结（只收不付） */
    public static final String ACC_V_BOOK_FRZSTAT_BORROW = "2";
    /** 账户冻结状态:3贷方冻结（只付不收） */
    public static final String ACC_V_BOOK_FRZSTAT_LOAN = "3";
    /** 账户冻结状态:金额冻结 */
    public static final String ACC_V_BOOK_FRZSTAT_AMT = "4";

    /** 账户止付状态:0正常 */
    public static final String ACC_V_BOOK_STOPSTAT_NORMAL = "0";
    /** 账户止付状态:1不收不付 */
    public static final String ACC_V_BOOK_STOPSTAT_NO = "1";
    /** 账户止付状态:2只收不付 */
    public static final String ACC_V_BOOK_STOPSTAT_ONLYGET = "2";
    /** 账户止付状态:3部分止付 */
    public static final String ACC_V_BOOK_STOPSTAT_PART = "3";

    /** 账户绑卡状态:0待激活 */
    public static final String ACC_BIND_BOOK_BIND_STAT_UNACTIVE = "0";
    /** 账户绑卡状态:1已绑定 */
    public static final String ACC_BIND_BOOK_BIND_STAT_BIND = "1";
    /** 账户绑卡状态:2过期失效 */
    public static final String ACC_BIND_BOOK_BIND_STAT_LOSE = "2";
    /** 账户绑卡状态:3解除绑定 */
    public static final String ACC_BIND_BOOK_BIND_STAT_UNBIND = "3";

    /** 账户系统限额状态 ： 1 启用 */
    public static final String T_ACC_SYS_LMT_BOOK_LMT_STAT_OPEN = "1";
    /** 账户系统限额状态 ： 2 关闭 */
    public static final String T_ACC_SYS_LMT_BOOK_LMT_STAT_CLOSE = "2";

    /** 限额交易累计表，累计限额类型 1：账户系统限额 */
    public static final String T_ACC_LMT_COUNT_ACCOUNT_FLAG_ACCSYS = "1";

    /** 账户系统限额配置表，限额类型 1：普通限额 */
    public static final String T_ACC_SYS_LMT_BOOK_LMT_TYPE_COMMON = "1";
    public static final String ACC_SYS_LMT_BOOK_LMT_TYPE_COMMON = "01";

    /** 开户标志 0：正常 */
    public static final String OPEN_ACCT_FLAG_NO = "0";
    /** 开户标志 1：已开户 */
    public static final String OPEN_ACCT_FLAG_YES = "1";
    /** 开户标志 2：已开户待激活 */
    public static final String OPEN_ACCT_FLAG_NOACTIVE = "2";
    /** 开户标志 3：已注销 */
    public static final String OPEN_ACCT_FLAG_CANCEL = "3";

    /** 账户状态,正常-0 */
    public static final String ACCT_STAT_NORMAL = "0";
    /** 账户状态,待激活-1 */
    public static final String ACCT_STAT_NOACTIVE = "1";
    /** 账户状态,睡眠-3 */
    public static final String ACCT_STAT_SLEEP = "2";
    /** 账户状态,注销-9 */
    public static final String ACCT_STAT_CANCEL = "9";

    /** 绑定状态,绑定待激活-0 */
    public static final String BIND_STAT_BINDED_TOACTIVATED = "0";
    /** 绑定状态,绑定已激活-1 */
    public static final String BIND_STAT_BINDED_ACTIVATED = "1";

    /** 卡号状态 0:在用 */
    public static final String CARD_STAT_USED = "0";

    /** 交易币种 默认：CNY */
    public static final String TRANS_CCY = "CNY";

    /** 第三方鉴权渠道:1：银联在线2：快钱等 */
    public static final String BIND_FLAG_CHNL = "1";
    /** 打款验证 */
    public static final String BIND_FLAG_VERIFYAMT = "2";

    /** 开户方式：1：普通开户 */
    public static final String OPEN_FLAG_NORMAL = "1";

    /** 虚拟账户信息表 止付类型 0-正常 */
    public static final String STP_TYPE_NOMAL = "0";
    /** 虚拟账户信息表 止付类型 1-不收不付 */
    public static final String STP_TYPE_ALL_FROZEN = "1";
    /** 虚拟账户信息表 止付类型 2-只收不付 */
    public static final String STP_TYPE_DEBIT_FROZEN = "2";
    /** 虚拟账户信息表 止付类型 3-部分止付 */
    public static final String STP_TYPE_PART_FROZEN = "3";

    /** 冻结类型,正常-0 */
    public static final String FRZ_TYPE_FROZEN_NORMAL = "0";
    /** 冻结类型,双向冻结-1 */
    public static final String FRZ_TYPE_ALL_FROZEN = "1";
    /** 冻结类型,借方冻结-2 */
    public static final String FRZ_TYPE_DEBIT_FROZEN = "2";
    /** 冻结类型,贷方冻结-3 */
    public static final String FRZ_TYPE_CREDIT_FROZEN = "3";
    /** 冻结类型,金额冻结-4 */
    public static final String FRZ_AMT = "4";

    /** 第三方鉴权绑定变更卡接口交易码：SI_ACC10002 */
    public static final String TRANS_CODE_CHANG_CARD = "SI_ACC1002";
    /** 打款验证开通待激活账户接口交易码：SI_ACC10003 */
    public static final String TRANS_CODE_AMT_OPEN_CARD = "SI_ACC10003";
    /** 打款验证开通待激活账户接口交易码：SI_ACC10004 */
    public static final String TRANS_CODE_AMT_CHANG_CARD = "SI_ACC10004";
    /** 烟草二级清算转账接口交易码：SI_ACC10006 */
    public static final String TRANS_CODE_SMOKE_TRANSFER = "SI_ACC1006";
    /** 电子账户变更绑定卡前检查接口交易码：SI_ACC10007 */
    public static final String TRANS_CODE_AMT_CHANG_BINDCARD = "SI_ACC1007";
    /** 充值 接口 */
    public static final String TRANS_CODE_RECHARGE = "SI_ACC8001";
    /** 转账 接口 */
    public static final String TRANS_CODE_TRANSFER = "SI_ACC8002";
    /** 提现 接口 */
    public static final String TRANS_CODE_WITHDRAW = "SI_ACC8003";
    /** 单笔代收   接口交易码：SI_PAY0019 */
    public static final String TRANS_CODE_COLLECTION = "SI_PAY0019";
    /** 单笔代付   接口交易码：SI_PAY0020 */
    public static final String TRANS_CODE_PAYMENT = "SI_PAY0020";
    /** 快速注册下单，返回二维码链接   接口交易码：SI_PAY1009 */
    public static final String TRANS_CODE_WXPAY = "SI_PAY1009";
    /** 本行网银充值   接口交易码：SI_PAY0024 */
    public static final String TRANS_CODE_LOCAL_COLLECTION = "SI_PAY0024";
    /** 第三方鉴权开户接口交易码：SI_ACC10001 */
    public static final String TRANS_CODE_ = "SI_ACC1001";
    
    /** 第三方鉴权开户接口交易码：SI_ACC10001 */
    public static final String TRANS_CODE_OPEN_CARD = "SI_ACC1001";

    /** 默认绑卡标志-1:仅允许绑定一个账户时，默认为1 */
    public static final String BIND_DEFAULT_FLAG_YES = "1";
    /** 银行标志:03：核心系统 */
//    public static final String THIRD_AUTH_CHNL_CORE = "03";
    public static final String THIRD_AUTH_CHNL_CORE ="1";

    /** 电子账户绑定账户银行类别:1：本行账户 */
    public static final String BIND_BANK_FLAG_THIS = "1";
    /** 电子账户绑定账户银行类别:2：他行账户 */
    public static final String BIND_BANK_FLAG_OTHER = "2";

    /** 账户类型,虚拟账户-02 */
    public static final String ACCT_TYPE_VIRTUAL_ACCT = "02";
    /** 账户类型,虚拟账户-01 */
    public static final String ACCT_TYPE_ELECT_ACCT = "01";
    /** 账户类型,积分主账户-03 */
    public static final String ACCT_TYPE_MAIN_POINT_ACCT = "03";
    /** 账户类型,专项积分账户-04 */
    public static final String ACCT_TYPE_DEDICATED_ACCT = "04";
    /** 账户类型,红包账户-05 */
    public static final String ACCT_TYPE_RED_ACCT = "05";
    /** 账户类型,优惠券账户-06 */
    public static final String ACCT_TYPE_COUPONS_ACCT = "06";
    /** 账户类型,借记卡-11 */
    public static final String ACCT_TYPE_DEBIT_CARD = "11";
    /** 账户类型,贷记卡-12 */
    public static final String ACCT_TYPE_CREDIT_CARD = "12";
    /** 账户类型,个人结算户-13 */
    public static final String ACCT_TYPE_PER_SETTLEMENT_CARD = "13";
    /** 账户类型,第三方平台账户-14 */
    public static final String ACCT_TYPE_THIRD_ACCT = "14";
    /** 账户类型,单位结算户-15 */
    public static final String ACCT_TYPE_ENTITY_SETTLEMENT_ACCT = "15";
    /** 账户类型,内部账户-21 */
    public static final String ACCT_TYPE_INTERNAL_ACCT = "21";

    /** 解绑原因,绑定卡变更-1 */
    public static final String UNBIND_ACCT_CHANG = "1";
    /** 解绑原因,电子账户销户-2 */
    public static final String UNBIND_ACCT_CANCEL = "2";
    /** 解绑原因,普通解绑-3 */
    public static final String UNBIND_ACCT = "3";
    
    /** 是否金融交易 */
	public static final String AMT_FLAG_YES = "1";
	/** 账户限额类型，普通限额01 */
	public static final String LMP_TPYE_NORMAL = "01";
	 /** 账户系统限额 */
    public static final String LMT_ACCOUNT_FLAG_SYS = "1";
	/** 账户限额状态 1:启用 */
	public static final String LMT_STAT_ENABLE = "1";
	/** 服务状态,正常-0 */
	public static final String SERVICE_STAT_NORMAL = "0";
	
	/** 卡类型,1 借记卡 */
    public static final String BANK_CARD_TYPE_BORROW = "11";
    /** 卡类型,2 贷记卡 */
    public static final String BANK_CARD_TYPE_LEND = "12";
    /**---------add by xuxin-----------------------*/
    /** 账户权限状态,关闭-0 */
    public static final String PRM_STAT_CLOSED = "0";
    /** 账户权限状态,开通-1 */
    public static final String PRM_STAT_OPENING = "1";
    
    /** 虚拟账户信息表 止付方式 1-临时止付 */
    public static final String STP_MODE_SNAP = "1";
    /** 虚拟账户信息表 止付方式 2-非临时止付 */
    public static final String STP_MODE_NOT_SNAP = "2";
    /** 虚拟账户信息表 解付原因:临时止付时间到期 */
    public static final String STP_UNSTP_REASON = "1";
    /** 借贷标识,借-1 */
    public static final String DC_FLAG_DEBIT = "1";
    /** 借贷标识,贷-2 */
    public static final String DC_FLAG_CREDIT = "2";
   /**---------add by xuxin-----------------------*/
    
    /** 绑卡方式 1：第三方鉴权 */
    public static final String E_BIND_FLAG_THDVERIFY = "1";
    
    /**交易类型---充值**/
    public static final String TRANS_TYPE_RECHARGE = "1";
    
    /**交易类型---充值**/
    public static final String TRANS_TYPE_RECHARGE_TEXT = "充值";
    
    /**交易类型---提现**/
    public static final String TRANS_TYPE_WITHDRAW = "2";
    
    public static final String TRANS_TYPE_WITHDRAW_TEXT = "提现";
    
    public static final String TRANS_TYPE_TRANSFER_TEXT_OUT = "转出";
    public static final String TRANS_TYPE_TRANSFER_TEXT_IN = "转入";
    /**核心  交易类型---提现**/
    public static final String CODE_TRANS_TYPE_WITHDRAW="3";
    /**核心  交易类型---卡到卡**/
    public static final String CODE_TRANS_TYPE_CARD_TO_CARD="5";
    
    /**核心  交易类型---内部户到对公账户**/
    public static final String CODE_TRANS_TYPE_INSIDE_TO_PUBLIC="6";
}
