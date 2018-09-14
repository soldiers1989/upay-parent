package com.upay.commons.constants;

/**
 * 支付常用静态变量
 * 
 * @author: 作者信息
 * @CreateDate:2015年2月5日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2015年2月5日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public interface DataBaseConstants_PAY {
	/** 二维码支付全使用渠道 ：DIRECT:直连*/
	public static final String DIRECT="DIRECT";
	
	/** 二维码支付全使用渠道 ： UNIONPAY:银联*/
	public static final String UNIONPAY="UNIONPAY";
	
	/** 二维码支付全使用渠道 ：NETUNION:网联*/
	public static final String NETUNION="NETUNION";
	
	public static final String THIRD_FLAG="1";
	public static final String MEMO_CODE_TRANSFER="1095";
    // ADD BY zgr 2016/08/08
    /** 用户类型 ：0普通用户*/
    public static final String USER_TYPE_PLAIN = "0";
    /** 用户类型 ：1商户用户 */
    public static final String USER_TYPE_MER = "1";
    /** 用户类型 ：2不限制 */
    public static final String USER_TYPE_ALL = "2";

    /** 通道状态,停用-0 */
    public static final String ROUTE_STAT_STOP = "0";
    /** 通道状态,正常-1 */
    public static final String ROUTE_STAT_NORMAL = "1";
    // ADD BY zgr 2016/08/08
    /** 功能代码,账户鉴权-01 */
    public static final String ROUTE_FUNC_CODE_AAA = "01";
    /** 功能代码,充值-02 */
    public static final String ROUTE_FUNC_CODE_RECHARGE = "02";
    /** 功能代码,提现-03 */
    public static final String ROUTE_FUNC_CODE_WITHDRAW = "03";
    /** 功能代码,转账-04 */
    public static final String ROUTE_FUNC_CODE_TRANSFER = "04";
    /** 功能代码,退款-05 */
    public static final String ROUTE_FUNC_CODE_REFUND = "05";

    // add by zhangguorong 20160819
    /** 金额转换标识,0：分到元 */
    public static final String MONEY_CHANG_FLAG_ZERO = "0";
    /** 金额转换标识,1:元到分 */
    public static final String MONEY_CHANG_FLAG_ONE = "1";

    // add by zhangguorong 20160807
    /** 交易状态-成功 */
    public static final String T_PAY_TX_SUCCESS = "N";
    /** 交易状态-初始 */
    public static final String T_PAY_TX_BEGIN = "0";
    /** 交易状态-失败 */
    public static final String T_PAY_TX_FAL = "2";
    /** 交易状态-交易已撤销 */
    public static final String T_PAY_TX_CANCEL = "5";
    /** 交易状态-冲正 */
    public static final String T_PAY_TX_RUSH = "6";
    /** 交易状态-交易处理中 */
    public static final String T_PAY_TX_PROING = "8";
    /** 交易状态-未知 */
    public static final String T_PAY_TX_UNKNOWN = "9";
    /** 交易状态-无此交易 */
    public static final String T_PAY_TX_NOONE = "X";
    /** 交易状态-手动抹账 */
    public static final String T_PAY_TX_WIPE = "E";
    /** 支付通讯状态 -成功 */
    public static final String PAY_RETURN_SUCCES = "SUCCESS";
    /** 支付通讯状态 -失败 */
    public static final String PAY_RETURN_FAIL = "FAIL";
    /** 支付交易状态 -成功 */
    public static final String PAY_RESULT_SUCCES = "SUCCESS";
    /** (查询)微信交易状态 -失败 */
    public static final String WECHAT_RESULT_PAYERROR = "PAYERROR";
    
    /** (查询)微信交易状态 -支付中 */
    public static final String WECHAT_USER_PAYING = "USERPAYING";
    /** 支付交易状态 -失败 */
    public static final String PAY_RESULT_FAIL = "FAIL";

    // ADD BY MIQIN 2015/05/07
    /** 通道代码,核心系统-0001 */
    public static final String ROUTE_CODE_HOST = "0001";
    /** 通道代码,银联-0002 */
    public static final String ROUTE_CODE_CNAPSHVPS = "0002";
    /** 通道代码,微信-0003 */
    public static final String ROUTE_CODE_CNAPSBEPS = "0003";
    /** 通道代码,中金支付-0004 */
    public static final String ROUTE_CODE_UNIONPAY = "0004";
    /** 通道代码,支付宝-0005 */
    public static final String ROUTE_CODE_ALIPAY = "0005";
    /** 通道代码,本平台资金通道代码 用于记录平台账户信息 */
    public static final String ROUTE_CODE_PLATFORM = "9999";
    /** 大小额标识,大额-HVPS */
    public static final String ROUTE_CODE_HVPS = "HVPS";
    /** 大小额标识,小额-BEPS */
    public static final String ROUTE_CODE_BEPS = "BEPS";
    
    /** 超网标识,小额-IBPS */
    public static final String ROUTE_CODE_IBPS_FLAG = "IBPS";
    /** 通道代码,微信-0003 */
    public static final String ROUTE_CODE_WECHAT = "0003";
    /** 通道代码,中金支付-0004 */
    public static final String ROUTE_CODE_CPCN = "0004";
    /** 通道代码，二代资金通道代码 */
    public static final String ROUTE_CODE_CNAPS2 = "0005";
    /** 通道代码，超级网银通道代码 */
    public static final String ROUTE_CODE_IBPS = "0006";

    /** 行内外标识,行内-1 */
    public static final String BANK_FLAG_IN = "1";
    /** 行内外标识,行外-2 */
    public static final String BANK_FLAG_OUT = "2";

    // ADD BY MIQIN 2015/05/07
    /** 往来标识,往（直销银行发起）-1 */
    public static final String SR_FLAG_NOSTRO = "1";
    /** 往来标识,来（直销银行接收）-2 */
    public static final String SR_FLAG_VOSTRO = "2";

    // ADD BY MIQIN 2015/05/07
    /** 账户类型,虚拟账户-01 */
    public static final String ACCT_TYPE_EPAY = "01";
    /** 账户类型,积分主账户-02 */
    public static final String ACCT_TYPE_MAIN_POINT_ACCT = "02";
    /** 账户类型,积分专项账户-03 */
    public static final String ACCT_TYPE_DEDICATED_ACCT = "03";
    /** 账户类型,红包账户-04 */
    public static final String ACCT_TYPE_RED_ACCT = "04";
    /** 账户类型,优惠券账户-05 */
    public static final String ACCT_TYPE_COUPONS_ACCT = "05";
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
    /** 账户类型,他行借记卡-22 */
    public static final String ACCT_TYPE_OTHERDEBIT_ACCT = "22";
    /** 账户类型,本行对公账户-23 */
    public static final String ACCT_TYPE_PUBLIC_ACCT = "23";
    /** 账户类型,他行对公账户-24 */
    public static final String ACCT_TYPE_OTHERPUBLIC_ACCT = "24";
    /** 个人网银 */
    public static final String ACCT_TYPE_PERSONAL_ACCT = "06";
    /** 企业网银 */
    public static final String ACCT_TYPE_ENTERPRISE_ACCT = "16";
    /** 二类账户 */
    public static final String TWO_TYPES_OF_ACCOUNT = "17";

    /** 三类账户 */
    public static final String THREE_TYPES_OF_ACCOUNT = "18";


    // ADD BY MIQIN 2015/05/07
    /** 银行类型,直销银行-01 */
    public static final String BANK_FLAG_DIRECT_BANK = "01";
    /** 银行类型,他行-02 */
    public static final String BANK_FLAG_THIRD_BANK = "02";
    /** 银行类型,本行核心系统-03 */
    public static final String BANK_FLAG_HOST_BANK = "03";

    /** 信息文件名时间戳格式 **/
    public static final String FILENAME_DATE_FORMAT = "yyyyMMdd";

    /** 标准省市信息文件前缀 **/
    public static final String T_IPW_GBCITYBOOK_PREFIX = "T_IPW_GBCITYBOOK_";
    /** 标准省市信息文件后缀 **/
    public static final String T_IPW_GBCITYBOOK_SUFFIX = ".txt";
    /** 标准省市信息文件存放路径参数编号 **/
    public static final String T_IPW_GBCITYBOOK_FILEPATH_PARMID = "IPW_GBCITYBOOK_PATH";

    /** 统一行名行号文件前缀 **/
    public static final String T_IPW_UNIBANKINFO_PREFIX = "T_IPW_UNIBANKINFO_";
    /** 统一行名行号文件后缀 **/
    public static final String T_IPW_UNIBANKINFO_SUFFIX = ".txt";
    /** 统一行名行号文件存放路径参数编号 **/
    public static final String T_IPW_UNIBANKINFO_FILEPATH_PARMID = "IPW_UNIBANKINFO_PATH";

    /** 银联文件传输存放路径参数编号 **/
    public static final String T_PAY__FILEPATH_FLOW = "T_PAY_PATH";
    public static final String T_RECEIVE__FILEPATH_FLOW = "T_RECEIVE_PATH";
    /** 日期切换 **/
    public static final String T_FALG = "T_DATE";

    /** 下载 **/
    public static final String T_FILE_TYPE = "0";
    /** 对账处理中 **/
    public static final String T_CHK_STAT = "0";

    /** 网银互联支付系统行号文件前缀 **/
    public static final String T_IBPS_BANKINFO_PREFIX = "T_IBPS_BANKINFO_";
    /** 网银互联支付系统行号文件后缀 **/
    public static final String T_IBPS_BANKINFO_SUFFIX = ".txt";
    /** 网银互联支付系统行号文件存放路径参数编号 **/
    public static final String T_IBPS_BANKINFO_FILEPATH_PARMID = "IBPS_BANKINFO_PATH";

    /** 行别代码文件前缀 **/
    public static final String T_IPW_BANKCLSBOOK_PREFIX = "T_IPW_BANKCLSBOOK_";
    /** 行别代码文件后缀 **/
    public static final String T_IPW_BANKCLSBOOK_SUFFIX = ".txt";
    /** 行别代码文件存放路径参数编号 **/
    public static final String T_IPW_BANKCLSBOOK_FILEPATH_PARMID = "IPW_BANKCLSBOOK_PATH";

    /**
     * 支付转账前处理 转账订单支付处理流程：1、账户权限检查 2、账户状态检查； 3、协议检查； 4、限额检查； 5、费用预算
     * 交易标识32位，每一位代表一个控制项：0-否 1-是 2-处理付款方 3-处理收款方 1、账户权限是否检查 2、账户状态是否检查
     * 3、协议是否检查； 4、限额是否检查； 5、费用是否处理； 交易码 交易名称 账户权限 账户状态 协议检查 限额检查 费用计算
     * ebbc_pmt_pay_0001 支付 2 1 2 2 1 ebbc_pmt_pay_0003 组合支付归集部分 2 1 2 2 0
     * ebbc_pmt_pay_0002 单笔转账 2 1 2 2 1 ebbc_pmt_pay_0025 好友转账 2 1 2 2 1
     * ebbc_pmt_pay_0024 AA付款 2 1 2 2 1 ebbc_pmt_pay_0023 随e转 2 1 2 2 1
     * ebbc_pmt_pay_0018 红包领取 3 3 0 0 0 ebbc_pmt_pay_0021 随e转收款 3 3 0 0 0
     * ebbc_pmt_pay_0019 红包退回 0 3 0 0 0 ebbc_pmt_pay_0022 随e转退回 0 3 0 0 0
     * ebbc_pmt_pay_0017 红包创建 2 2 2 2 1 ebbc_pmt_pay_0020 随e转发起 2 2 2 2 1
     * ebbc_pmt_pay_0030 收费易付款 2 1 2 2 1 ebbc_pmt_pay_0026 虚拟账户充值 2 1 0 0 0
     * ebbc_pmt_pay_0027 虚拟账户提现 3 1 0 2 2 ebbc_pmt_pay_0028 电子账户充值 2 1 0 0 0
     * ebbc_pmt_pay_0029 电子账户提现 3 1 0 2 2
     **/
    public static final String T_PMT_EBBC_PMT_PAY_0001 = "21221";
    public static final String T_PMT_EBBC_PMT_PAY_0003 = "21220";
    public static final String T_PMT_EBBC_PMT_PAY_0002 = "21221";
    public static final String T_PMT_EBBC_PMT_PAY_0025 = "21221";
    public static final String T_PMT_EBBC_PMT_PAY_0024 = "21221";
    public static final String T_PMT_EBBC_PMT_PAY_0023 = "21221";
    public static final String T_PMT_EBBC_PMT_PAY_0018 = "33000";
    public static final String T_PMT_EBBC_PMT_PAY_0021 = "33000";
    public static final String T_PMT_EBBC_PMT_PAY_0019 = "03000";
    public static final String T_PMT_EBBC_PMT_PAY_0022 = "03000";
    public static final String T_PMT_EBBC_PMT_PAY_0017 = "22221";
    public static final String T_PMT_EBBC_PMT_PAY_0020 = "22221";
    public static final String T_PMT_EBBC_PMT_PAY_0030 = "21221";
    public static final String T_PMT_EBBC_PMT_PAY_0026 = "21000";
    public static final String T_PMT_EBBC_PMT_PAY_0027 = "31022";
    public static final String T_PMT_EBBC_PMT_PAY_0028 = "21000";
    public static final String T_PMT_EBBC_PMT_PAY_0029 = "31022";
    public static final String T_PMT_EBBC_PMT_PAY_0032 = "00000";
    public static final String T_PMT_RECV_ACCT_TYPE = "21";// 收款账号类型是内部账号账号类型是固定值21
    public static final String T_PMT_PAY_ACCT_TYPE = "21";// 付款账号类型是内部账号账号类型是固定值21
    public static final String T_PMT_RECV_ACCT_BANK_TYPE = "1";// 收款账号银行类型(1行内)
    public static final String T_PMT_PAY_ACCT_BANK_TYPE = "1";// 付款账号银行类型(1行内)
    public static final String T_PMT_REDPK_INNER_ACCT_TYPE = "004";// 红包账户内部账号类型004
    public static final String T_PMT_CASUL_INNER_ACCT_TYPE = "003";// 随e转账户内部账号类型003
    public static final String T_PMT_VUAL_INNER_ACCT_TYPE = "002";// 虚拟账户内部账号类型是002
    public static final String T_PMT_INTER_ACCT_CHNL_NO = "99";// 内部帐渠道编号是99
    public static final String T_PMT_VUAL_ACCT_TYPE = "01";// 虚拟账号类型是01

    /*
     * 协议类型 0003 平台支付协议 0009 他人账户支付协议 0014 家庭账户签约协议
     */
    public static final String T_PMT_CONTYPE_OWN = "0003";
    public static final String T_PMT_CONTYPE_OTHER = "0009";
    public static final String T_PMT_CONTYPE_FAMILY = "0014";
    /** 交易码-二代来账 */
    public static final String T_PMT_TRANS_CODE_CA = "SI_PMT00009";

    /** 差错处理状态-未处理 */
    public static final String T_PMT_DEELERR_NOT = "0";
    /** 差错处理状态-成功 */
    public static final String T_PMT_DEELERR_SUC = "1";
    /** 差错处理状态-失败 */
    public static final String T_PMT_DEELERR_FAL = "2";

/*    *//** 差错处理方式-平台补账 *//*
    public static final String T_PMT_DEELERR_WAY_PLA_ADD = "1";
    *//** 差错处理方式-平台冲账 *//*
    public static final String T_PMT_DEELERR_WAY_PLA_RUS = "2";
    *//** 差错处理方式-平台调状态 *//*
    public static final String T_PMT_DEELERR_WAY_PLA_ADJ = "3";
    *//** 差错处理方式-渠道补账 *//*
    public static final String T_PMT_DEELERR_WAY_CHN_ADD = "4";
    *//** 差错处理方式-渠道冲账 *//*
    public static final String T_PMT_DEELERR_WAY_CHN_RUS = "5";
    *//** 差错处理方式-手工处理 *//*
    public static final String T_PMT_DEELERR_WAY_HLD = "6";
    *//** 差错处理方式-柜员选择 *//*
    public static final String T_PMT_DEELERR_WAY_DONT = "7";
    *//** 差错处理方式-来账处理 *//*
    public static final String T_PMT_DEELERR_WAY_NOR_INN = "8";
    *//** 差错处理方式-退汇处理处理 *//*
    public static final String T_PMT_DEELERR_WAY_CONT = "9";*/

    
    
    /** 差错处理方式-补核心账 */
    public static final String T_PMT_DEELERR_WAY_HOST_ADD = "01";
    /** 差错处理方式-冲核心账 */
    public static final String T_PMT_DEELERR_WAY_HOST_RUS = "02";
    /** 差错处理方式-调平台、补核心 */
    public static final String T_PMT_DEELERR_WAY_WeChat_ADD = "03";
    /** 差错处理方式-调平台、冲核心 */
    public static final String T_PMT_DEELERR_WAY_WeChat_RUS = "04";
    /** 差错处理方式-冲微信 */
    public static final String T_PMT_DEELERR_WAY_WECHAT_STRIKE = "05";
    /** 差错处理方式-冲中金 */
    public static final String T_PMT_DEELERR_WAY_ZJ_STRIKE = "06";
    /** 差错处理方式-调整平台流水状态 */
    public static final String T_PMT_DEELERR_WAY_PLAT_ADJUST = "07";
    /** 差错处理方式-冲银联 */
    public static final String T_PMT_DEELERR_WAY_UNION_STRIKE = "08";
    /** 差错处理方式-差错校正 */
    public static final String T_PMT_DEELERR_WAY_PLAT_CRRECTION= "10";
    
    /** 差错类型-第三方有 */
    public static final String T_PMT_DEELERR_ERRTYPE_SEC_HAS = "0";
    /** 差错类型-第三方无 */
    public static final String T_PMT_DEELERR_ERRTYPE_SEC_NOT = "1";
    /** 差错类型-账不平 */
    public static final String T_PMT_DEELERR_ERRTYPE_ZBP = "2";

    /** 对账规则状态 '1'-正常 */
    public static final String T_CHK_RULE_STAT_NORMAL = "1";

    /** 合作机构类型 '00'-支付 */
    public static final String T_CHK_RULE_ORGTYPE_PAY = "00";
    /** 合作机构类型 '01'-资金划转 */
    public static final String T_CHK_RULE_ORGTYPE_FINANCE = "01";

    /** 合作机构类型 '11'-支付+资金划转 */
    public static final String T_CHK_RULE_ORGTYPE_PF = "11";

    /** 对账数据基准标准 '01'-第三方为主 */
    public static final String T_CHK_BENCHMARK_FLAG_THIRD = "01";

    /** 对账状态 '0'-未对账 */
    public static final String T_CHK_STAT_PRE = "0";
    /** 对账状态 '1'-对账成功 */
    public static final String T_CHK_STAT_SUC = "1";
    /** 对账状态 '2'-对方多 */
    public static final String T_CHK_STAT_THIRD = "2";
    /** 对账状态 '3'-本系统多 */
    public static final String T_CHK_STAT_SYS = "3";
    /** 对账状态 '4'-金额不符 */
    public static final String T_CHK_STAT_AMTERR = "4";

    /** 第三方状态 '0'-第三方无 */
    public static final String T_THIRD_STAT_LESS = "0";
    /** 第三方状态 '1'-第三方有 */
    public static final String T_THIRD_STAT_MORE = "1";
    /** 第三方状态 '2'-第三方帐不平 */
    public static final String T_THIRD_STAT_AMTERR = "2";

    /** 币种 'CNY'-人民币 （平台）*/
    public static final String T_CCY_CNY = "CNY";
    /** 币种 '156'-人民币 （核心）*/
    public static final String T_CORE_CCY_CNY = "156";

    /** 自动处理标志 'Y'-自动处理 */
    public static final String T_AUTO_FLAG_AUTO = "Y";
    /** 自动处理标志 'N'-手工处理 */
    public static final String T_AUTO_FLAG_HAND = "N";

    /** 处理方式 '7'-柜员选择 */
    public static final String T_PROC_FLAG_NODEAL = "7";

    /** 借贷标识 'D'-借方：付款方 */
    public static final String T_DC_FLAG_D = "D";
    /** 借贷标识 'C'-贷方：收款方 */
    public static final String T_DC_FLAG_C = "C";
    /** 借贷标识 'X'-未知 */
    public static final String T_DC_FLAG_X = "X";

    /** 文件处理状态 '3'-文件下载成功 */
    public static final String T_FILE_DEAL_STAT_DOWNSUC = "3";
    /** 文件处理状态 '6'-导入数据成功 */
    public static final String T_FILE_DEAL_STAT_SAVESUC = "6";

    /** 对账日期类型 '1'-平台日期 */
    public static final String T_CHK_DATE_FLAG_SYS = "1";
    /** 对账日期类型 '2'-通道日期 */
    public static final String T_CHK_DATE_FLAG_ROUT = "2";

    /** 该用户已经设置了支付密码 */
    public static final String T_CHK_USR_PAYPWD_YES = "1";
    /** 该用户未设置支付密码 */
    public static final String T_CHK_USR_PAYPWD_NO = "0";

    // 支付状态
    /** “0”支付成功 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_Y = "0";
    /** “1”未支付 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_N = "1";
    /** “2”支付中 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_ING = "2";
    /** “3”支付失败 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_FAIL = "3";
    /** “4”超时关闭 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_TOC = "4";
    /** “5”手工关闭 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_HC = "5";
    /** “6”确认收货 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_CP = "6";
    /** “7”预约支付 说明一般 大额支付使用 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_AP = "7";
    /** “8”待确认收货 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_WCP = "8";
    /** “9”退款中 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_REFING = "9";
    /** “10”退款失败 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL = "10";
    /** “11”退款成功 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC = "11";
    /** “12”部分退款 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_REFPRO = "12";
    /** “13”全额退款 */
    public static final String T_PAY_ORDER_LIST_ORDER_STAT_REFALL = "13";

    
    /** 支付方式：00 本行快捷支付 */
    public static final String T_PAY_TYPE_BANK_QUICK_DESC = "快捷支付";
    /** 支付方式：00 本行快捷支付 */
    public static final String T_PAY_TYPE_BANK_QUICK = "00";
    /** 支付方式：01 他行快捷支付 */
    public static final String T_PAY_TYPE_OTHER_QUICK = "01";
    /** 支付方式：02 平台账户 */
    public static final String T_PAY_TYPE_PLATFORM_ACC = "02";
    /** 支付方式：单笔代收 */
    public static final String T_PAY_TYPE_SINGLE_COLLECTION = "03";
    /** 支付方式：单笔代付 */
    public static final String T_PAY_TYPE_SINGLE_PAYMENT = "04"; 
    /** 支付方式：无跳转支付 */
    public static final String T_PAY_TYPE_SINGLE_WTPAY = "05";  
    /** 支付方式：日终结算 */
    public static final String T_PAY_TYPE_STL = "09";
    
    /** 支付方式：10 微信支付（扫码支付） */
    public static final String T_PAY_TYPE_WEIXIN_QR_CODE = "10";
    /** 支付方式：11 微信支付（公众号支付） */
    public static final String T_PAY_TYPE_PUBLIC_NO = "11";
    /** 支付方式：12 微信支付（刷卡支付） */
    public static final String T_PAY_TYPE_CARD_PAY = "12";
    /** 支付方式：20 银联网关支付 */
    public static final String T_PAY_TYPE_UNIONPAY_GATEWAY = "20";
    
    /** 支付方式：30-支付宝扫码支付 */
    public static final String T_PAY_TYPE_ALIPAY_QR_CODE = "30";
    /** 支付方式：31-支付宝刷卡支付 */
    public static final String T_PAY_TYPE_ALIPAY_CARD_PAY  = "31";
    /** 支付方式：32-支付宝聚合支付 */
    public static final String T_PAY_TYPE_ALIPAY_TOGETHER = "32";

    /** 支付方式：60 网银支付（个人） */
    public static final String T_PAY_TYPE_ONLINE_BANK_PERSONAL = "60";
    /** 支付方式：61 网银支付（企业） */
    public static final String T_PAY_TYPE_ONLINE_BANK_COMPANY = "61";
    /** 支付方式：40 银联支付（线上主扫支付） */
    public static final String T_PAY_TYPE_ACP_WR_CODE = "40";
    /** 支付方式：42 银联支付（线下主扫支付） */
    public static final String T_PAY_TYPE_ACP_XWR_CODE = "42";
    /** 支付方式：41 银联支付（被扫支付） */
    public static final String T_PAY_TYPE_ACP_QR_CODE = "41";

    /** 支付方式：77 退款 */
    public static final String T_PAY_TYPE_REFUND_AMOUNT = "77";

    /** 支付服务类型： 0001 中间担保交易 */
    public static final String T_MER_PAY_SERVER_TYPE_GUARANTEE = "0001";
    /** 支付服务类型： 0002 即时到账 */
    public static final String T_MER_PAY_SERVER_TYPE_FORTHWITH = "0002";
    /** 支付服务类型： 0003 都开通 */
    public static final String T_MER_PAY_SERVER_TYPE_ALL = "0003";

    /** 商户交易权限控制状态： 0 启用 */
    public static final String T_MER_TRANS_CTRL_STAT_OPEN = "0";
    /** 商户交易权限控制状态： 1 停用 */
    public static final String T_MER_TRANS_CTRL_STAT_CLOSE = "1";

    /** 免收周期 ：0 不免 */
    public static final String T_FEE_GET_FREE_CYCLE_NO = "0";
    /** 免收周期 ：0 日 */
    public static final String T_FEE_GET_FREE_CYCLE_DAY = "1";
    /** 免收周期 ：0 月 */
    public static final String T_FEE_GET_FREE_CYCLE_MONTH = "2";
    /** 免收周期 ：0 年 */
    public static final String T_FEE_GET_FREE_CYCLE_YEAR = "3";
    /** 免收周期 ：0 全免 */
    public static final String T_FEE_GET_FREE_CYCLE_ALL = "9";

    /** 免收标志 ：0 不免 */
    public static final String T_FEE_DETAIL_FREE_FLAG_NO = "0";
    /** 免收标志 ：1 免收 */
    public static final String T_FEE_DETAIL_FREE_FLAG_YES = "1";

    /** 手续费收取方式：1单笔固定 */
    public static final String T_FEE_KIND_FEE_MODE_FIXED = "1";
    /** 手续费收取方式：2单笔固定加按交易金额比例 */
    public static final String T_FEE_KIND_FEE_MODE_ALL = "2";
    /** 手续费收取方式：3按交易金额比例 */
    public static final String T_FEE_KIND_FEE_MODE_PROPERTION = "3";
    /** 手续费收取方式：4分段配置 */
    public static final String T_FEE_KIND_FEE_MODE_SUBSECTION = "4";

    /** 手续费是否分润：0000 不分润 */
    public static final String T_FEE_GET_ASS_CODE_NO = "000000";

    /** 分润方式：1按比率 */
    public static final String T_FEE_ASS_ASS_TYPE_RATIO = "1";
    /** 分润方式：2固定 */
    public static final String T_FEE_ASS_ASS_TYPE_FIXED = "2";

    /** 分润类型：0应收 */
    public static final String T_FEE_ASS_ASS_KIND_PUT = "0";
    /** 分润类型：1应付 */
    public static final String T_FEE_ASS_ASS_KIND_OUT = "1";

    /** 分润方：001支付平台 */
    public static final String T_FEE_ASS_ASS_ID_PALTFORM = "001";
    /** 分润方：002 资金通道 */
    public static final String T_FEE_ASS_ASS_ID_ROUTE = "002";
    /** 分润方：003 银行机构 */
    public static final String T_FEE_ASS_ASS_ID_BANK = "003";
    /** 分润方：003 合作商户 */
    public static final String T_FEE_ASS_ASS_ID_MERCHANT = "004";

    /** 分润方代码：0000000000 默认(平台) */
    public static final String T_FEE_ASS_ORG_CODE = "0000000000";

    /** 分润明细的借贷标志 : D 借(应付) */
    public static final String T_FEE_ASS_DC_FLAG_BORROW = "D";
    /** 分润明细的借贷标志 : C 贷(应收) */
    public static final String T_FEE_ASS_DC_FLAG_LOAN = "C";

    /** 资金通道的通道状态 : 1 正常 */
    public static final String T_PAY_ROTE_PERM_NORMAL = "1";
    /** 资金通道的通道状态 : 0 停用 */
    public static final String T_PAY_ROTE_PERM_STOP = "0";

    // /** 订单状态：0支付成功 */
    // public static final String ORDER_STAT_SUCCESS = "0";
    // /** 订单状态：1未支付 */
    // public static final String ORDER_STAT_NO = "1";
    // /** 订单状态：2支付中 */
    // public static final String ORDER_STAT_PAYING = "2";
    // /** 订单状态：3支付失败 */
    // public static final String ORDER_STAT_FAIL = "3";
    // /** 订单状态：4超时关闭 */
    // public static final String ORDER_STAT_TIMEOUT = "4";
    // /** 订单状态：5手工关闭 */
    // public static final String ORDER_STAT_HAND_CLOSE = "5";
    // /** 订单状态：6预约支付 */
    // public static final String ORDER_STAT_APPOINTMENT = "6";

    /** 记录流水次数 ： 1 第一次 */
    public static final String T_PAY_FLOW_TIMES_ONCE = "1";
    /** 记录流水次数 ： 2 第二次 */
    public static final String T_PAY_FLOW_TIMES_TWICE = "2";

    /** 通道的的交易状态：0 成功 */
    public static final String ROUTE_TRANS_TYPE_SUCCESS = "0";
    /** 通道的的交易状态：1 失败 */
    public static final String ROUTE_TRANS_TYPE_FAIL = "1";

    /** 订单类型 : 00 非订单管理 */
    public static final String T_ORDER_LIST_TYPE_NO = "00";

    /** 订单类型 : 01 平台订单 */
    public static final String T_ORDER_LIST_TYPE_UPAY = "01";

    /** 订单类型 : 02 外部预生成订单 */
    public static final String T_ORDER_LIST_TYPE_OUT_PRE = "02";

    /** 订单类型 : 03 外部联机生成订单 */
    public static final String T_ORDER_LIST_TYPE_OUT_ONLINE = "03";

    /** 退款渠道 0 退款到支付平台 */
    public static final String REFUND_HCNL_UPAY = "0";
    /** 退款渠道 1 退款到银行 */
    public static final String REFUND_CHNL_BANK = "1";
    /** 退款渠道 2 退款到支付宝 */
    public static final String REFUND_CHNL_ALIPAY = "2";
    /** 退款渠道 3 退款财付通 */
    public static final String REFUND_CHNL_TENPAY = "3";
    /** 退款渠道 4 退款到银联 */
    public static final String REFUND_CHNL_CUP = "4";

    /** 通知状态 0 通知成功 */
    public static final String MER_NOTIFY_STATUS_TRUE = "0";
    /** 通知状态 1 通知失败 */
    public static final String MER_NOTIFY_STATUS_FALSE = "1";
    /** 通知状态 通知成功返回码 */
    public static final String MER_NOTIFY_RESCODE_TRUE = "0000";
    /** 通知状态 通知失败返回码 */
    public static final String MER_NOTIFY_RESCODE_FALSE = "0001";
    /** 通知状态 通知成功返回信息 */
    public static final String MER_NOTIFY_RESMSG_TRUE = "TRUE";
    /** 通知状态 通知失败返回信息 */
    public static final String MER_NOTIFY_RESMSG_FALSE = "FALSE";

    /** 微信支付回调状态 */
    public static final String WEIXIN_RESULT_STATUS_SUCCESS = "SUCCESS";
    /** 微信支付回调状态 */
    public static final String WEIXIN_RESULT_STATUS_FAIL = "FAIL";

    /** 核心单笔查询交易状态：0，成功 */
    public static final String CORE_BANK_SINGLE_QUERY_STATUS_SUCCESS = "0";
    /** 核心单笔查询交易状态：1，无此订单 */
    public static final String CORE_BANK_SINGLE_QUERY_STATUS_NOTHIND = "1";
    /** 核心单笔查询交易状态：2，已冲正 */
    public static final String CORE_BANK_SINGLE_QUERY_STATUS_CORRECTED = "2";

    /** 限额类型 01普通限额，02手机免密限额 */
    public static final String SYS_LMT_TYPE_GENERAL = "01";
    /** 限额类型 01普通限额，02手机免密限额 */
    public static final String SYS_LMT_TYPE_MOBILE = "02";

    /** 对账单下载：订单类型：0001：当日所有订单，0002当日支付成功 的 订单，0003：当日退款的订单 */
    public static final String MER_RECON_DOWNLOAD_ALL = "0001";
    /** 对账单下载：订单类型：0001：当日所有订单，0002当日支付成功 的 订单，0003：当日退款的订单 */
    public static final String MER_RECON_DOWNLOAD_SUC = "0002";
    /** 对账单下载：订单类型：0001：当日所有订单，0002当日支付成功 的 订单，0003：当日退款的订单 */
    public static final String MER_RECON_DOWNLOAD_REF = "0003";

    /** 通道方清算方式 0、无清算1、实时清算2、实时代理清算3、日终清算4、日终代理清算 */
    public static final String ROUTE_CLR_TYPE_NO = "0";
    /** 通道方清算方式 0、无清算1、实时清算2、实时代理清算3、日终清算4、日终代理清算 */
    public static final String ROUTE_CLR_TYPE_NOW = "1";
    /** 通道方清算方式 0、无清算1、实时清算2、实时代理清算3、日终清算4、日终代理清算 */
    public static final String ROUTE_CLR_TYPE_NOWA = "2";
    /** 通道方清算方式 0、无清算1、实时清算2、实时代理清算3、日终清算4、日终代理清算 */
    public static final String ROUTE_CLR_TYPE_DAY = "3";
    /** 通道方清算方式 0、无清算1、实时清算2、实时代理清算3、日终清算4、日终代理清算 */
    public static final String ROUTE_CLR_TYPE_DAYA = "4";

    /** 手续费分段配置有效 状态 0失效，1有效 */
    public static final String FEE_STAND_STAT_NO = "0";
    /** 手续费分段配置有效 状态 0失效，1有效 */
    public static final String FEE_STAND_STAT_YES = "1";

    /** 商户状态：0正常，1停用 */
    public static final String T_MER_BASE_STATUS_NOMAL = "0";
    /** 商户状态：0正常，1停用 */
    public static final String T_MER_BASE_STATUS_STOP = "1";

    /** 通知类型：暂定00 */
    public static final String T_MER_NOTIFY_TYPE = "00";
    /** 通知状态：0-TRUE，1-FALSE */
    public static final String T_MER_NOTIFY_STATUS_TRUE = "0";
    /** 通知状态：0-TRUE，1-FALSE */
    public static final String T_MER_NOTIFY_STATUS_FALSE = "1";

    // 核心对账文件中的记账状态
    /** 交易状态-记账成功 */
    public static final String T_PAY_CORE_TX_SUCCESS = "1";
    /** 交易状态-记账失败 */
    public static final String T_PAY_CORE_TX_FAIL = "2";
    /** 交易状态-记账中 */
    public static final String T_PAY_CORE_TX_PROING = "3";

    /** 代收类型 1-内部转账 */
    public static final String COLLECTION_TYPE_IN_TRANSFERS = "1";
    /** 代收类型 2-非内部转账 */
    public static final String COLLECTION_TYPE_NON_IN_TRANSFERS = "2";
    
    /** 代付类型 1-内部转账 */
    public static final String SINGLE_PAY_TYPE_IN_TRANSFERS = "1";
    /** 代付类型 2-非内部转账 */
    public static final String SINGLE_PAY_TYPE_NON_IN_TRANSFERS = "2";
    
    
    /** 代扣代付账户类型：个人账户 */
    public static final String ACCT_TYPE_PERSON = "11";
    /** 代扣代付账户类型：企业账户 */
    public static final String ACCT_TYPE_BUSINESS = "12";
    
   
    
    /** 通道的的交易状态：0 成功 */
    public static final String ROUTE_TRANS_STAT_SUCCESS = "0";
    /** 通道的的交易状态：1 失败 */
    public static final String ROUTE_TRANS_STAT_FAIL = "1";
    /** 通道的的交易状态：2 处理中 */
    public static final String ROUTE_TRANS_STAT_PROING = "2";
    /** 通道的的交易状态：3 未知 */
    public static final String ROUTE_TRANS_STAT_UNKNOWN = "3";
    /** 通道的的交易状态：4 撤销 */
    public static final String ROUTE_TRANS_STAT_RE = "4";
    
    /** 手续费类型 用户 00 */
    public static final String FEE_TYPE_PERSON = "00";
    /** 手续费类型 通道01 */
    public static final String FEE_TYPE_ROUTE = "01";
    /** 手续费类型 商户02 */
    public static final String FEE_TYPE_MER = "02";
    
    /** 烟草转账状态   成功 */
    public static final String SMOKE_TRANSFER_SUCCESS = "1";
    /** 烟草转账状态   失败 */
    public static final String SMOKE_TRANSFER_FAIL = "2";
    /** 烟草转账状态   处理中 */
    public static final String SMOKE_TRANSFER_PROCESSING = "3";
    /** 通道手续费标志 */
    public static final String ROUTE_FEE_FLAG = "1";
    /** 分润费标志 */
    public static final String ASS_AMT_FLAG = "2";
    /** 分润方  资金通道 */
    public static final String ASS_ROUTE = "002";

    /** 流水标志 本平台流水 */
    public static final String FLOW_FLAG_PLATE = "0";
    /** 流水标志 通道方流水  */
    public static final String FLOW_FLAG_OTHER  = "1";
    /** 核心贷记卡接口 交易类型 1 支付 */
    public static final String CORE_DEBIT_TRANS_TYPE_PAY = "1";
    /** 核心贷记卡接口 交易类型 2 退款 */
    public static final String CORE_DEBIT_TRANS_TYPE_REFUND  = "2";
    
    
    /** 转账类型，电信转账01 **/
    public static final String TRANSFER_TYPE_DIANXIN = "01";
    /** 转账类型，微信转账02 **/
    public static final String TRANSFER_TYPE_WEIXIN = "02";
    /** 转账类型，商户批量清分 **/
    public static final String TRANSFER_TYPE_MERDEBIT = "03";
    /** 转账类型，本代本转账 **/
    public static final String TRANSFER_TYPE_WAGE = "04";
    /** 转账类型，商户结算批量 **/
    public static final String TRANSFER_TYPE_MERSTL = "05";

    
    /** 银联交易状态 00-成功 */
    public static final String UNION_STAT_SUCC  = "00";
    /** 银联交易状态 A6-有缺陷的成功 */
    public static final String UNION_STAT_LESS_SUCC  = "A6";
    /** 银联交易状态 02-系统未开放或暂时关闭，请稍后再试 */
    public static final String UNION_STAT_CLOSE  = "02";
    /** 银联交易状态 03-交易通讯超时，请发起查询交易 */
    public static final String UNION_STAT_TIMEOUT  = "03";
    /** 银联交易状态 04-交易状态未明，请查询对账结果 */
    public static final String UNION_STAT_UNKNOWN  = "04";
    /** 银联交易状态 05-交易已受理，请稍后查询交易结果 */
    public static final String UNION_STAT_HANDLING  = "05";
    /** 银联交易状态 06-系统繁忙，请稍后再试 */
    public static final String UNION_STAT_BUSY  = "06";
    /** 银联交易状态 12-重复交易 */
    public static final String UNION_STAT_REPEAT  = "12";
    /** 银联交易状态 83-发卡行（渠道）处理中 */
    public static final String UNION_STAT_BANK_HANDLING  = "83";
    /** 银联交易状态 00-成功 */
    public static final String UNION_RES_MESSAGE = "成功[0000000]";
    /** 银联交易状态 00-成功 */
    public static final String UNION_RE_MESSAGE = "成功";
    
    /** 网银交易状态 10-未支付 */
    public static final String ZJ_STAT_UNPAID  = "10";
    /** 网银交易状态 20-已支付 */
    public static final String ZJ_STAT_PAID  = "20";
    
    /** 支付明细状态 R - 重复交易记录 */
    public static final String T_PAY_FLOW_DETAIL_STAT_REPEAT = "R";
    
    /** 红包类型 single-普通红包 */
    public static final String RED_PACKET_SINGLE  = "single";
    /** 红包类型 group-裂变红包 */
    public static final String RED_PACKET_GROUP  = "group";
    
    /** 红包状态 0-初始 */
    public static final String RED_PACKET_STATE_BEGIN  = "0";
    /** 红包状态 1-发送成功 */
    public static final String RED_PACKET_STATE_SUCC  = "1";
    /** 红包状态 2-发送失败 */
    public static final String RED_PACKET_STATE_FAIL = "2";

    /** 支付宝响应码 10000-成功 */
    public static final String PAY_ALIPAY_SUCC = "10000";
    /** 支付宝退款 资金是否发生变化 Y-是 N-否*/
    public static final String FUND_CHANGE_Y = "Y";
    /** 支付宝退款 资金是否发生变化 Y-是 N-否*/
    public static final String FUND_CHANGE_N = "N";
    
    
    /** 支付宝交易状态 -交易创建，等待买家付款 */
    public static final String ALIPAY_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    /**支付宝交易状态 -未付款交易超时关闭，或支付完成后全额退款 */
    public static final String ALIPAY_STATUS_TRADE_CLOSED = "TRADE_CLOSED";
    /** 支付宝交易状态 -	交易支付成功*/
    public static final String ALIPAY_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
    /** 支付宝交易状态 -	交易结束，不可退款 */
    public static final String ALIPAY_STATUS_TRADE_FINISHED = "TRADE_FINISHED";

    /*批量代收代付 处理标志     N未处理  Y已处理*/
    public static final String PAY_PROCESS_N = "N";
    public static final String  PAY_PROCESS_Y = "Y";




}
