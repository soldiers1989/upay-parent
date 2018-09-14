package com.upay.commons.constants;
/**
 * @author shangqiankun
 * @version 创建时间：2016年7月29日 下午2:54:32
 */
public interface CommonConstants_GNR {
	//是否删除短信验证码  0不删除
    public static final String NO_DELETE="0";
	
    /** 是否存在 */
    public static final String IF_EXIST_Y="Y";
    public static final String IF_EXIST_N="N";
    
    /** 是否 */
    public static final String IS_TRUE = "Y";
    public static final String IS_FALSE = "N";
    
    /** 往来标识   */
    public static final String PAY_DIRECTION_OUT="2";
    /** 手机验证码功能开关 */
    public static final String MOBILE_VERI_CODE_SWITCH="GET_SMS_FLAG";
    /** 交易流水号sequences的name */
    public static final String PAY_FLOW_LIST_SUBSEQ="PAY_FLOW_LIST_SUBSEQ";
    /** 代码执行状态 ：0000000000 成功 */
    public static final String SERVICE_STAT="0000000000";
    /** 本行核心状态码 ：000000成功 */
    public static final String T_PAY_BANK_HT_SUCCESS="000000";
    /** 消息状态 1：启用 */
    public static final String NOTICE_STAT_ENABLE = "1";
    /** 短信状态 1：启用 */
    public static final String SMS_STAT_ENABLE = "1";
    /** 短信/事件发送对象类型 1:本人 */
    public static final String SEND_OBJ_MYSELF = "1";
    /** 短信/事件发送对象类型 2:关系人 */
    public static final String SEND_OBJ_RALATIONSHIP = "2";
    /** 短信验证码事件编号 */
    public static final String EVENT_NO_SMSNO = "00000000000000000000";
    /** 短信通道号 01：核心 */
    public static final String SMS_SEND_NO_CORE = "01";
    /** 短信验证码标志 1：验证 */
    public static final String SMS_CODE_VERIFY_FLAG_YES = "1";
    /** 用户操作流水状态 ,交易成功-01 */
    public static final String OPERATE_STAT_SUCCESS = "01";
    /** 用户操作流水状态,交易失败-02 */
    public static final String OPERATE_STAT_FAIL = "02";
    /** 是否金融交易 */
    public static final String AMT_FLAG_YES = "1";
    /** 服务状态,正常-0 */
    public static final String SERVICE_STAT_NORMAL = "0";
    /** 渠道,门户网站-02 */
    public static final String CHNL_ID_WEB = "02";
    /** 渠道,手机-01 */
    public static final String CHNL_ID_APP = "01";
    /** 渠道,微信-03 */
    public static final String CHNL_ID_WEIXIN = "03";
    /** 渠道,批量 - 05 */
    public static final String CHNL_ID_BATCH = "05";

    /** 渠道,本行网银 - 06 */
    public static final String CHNL_ID_ONLINE_BANK = "06";
    
    /** 渠道,柜面/柜台   - 12 */
    public static final String CHNL_ID_TELLER= "12";

    /** 交易返回码,交易失败-9999999999 */
    public static final String RSP_CODE_FAIL = "9999999999";
    /** 交易返回码,交易失败-'交易失败' */
    public static final String RSP_CODE_FAIL_DESC = "交易失败";
    /** 交易返回码,交易成功-0000000000 */
    public static final String RSP_CODE_SUCCESS = "0000000000";
    /** 交易返回码,交易成功-'交易成功' */
    public static final String RSP_CODE_SUCCESS_DESC = "交易成功";
    
    

    /** 交易代码 : 发送验证码 */
    public static final String TRANS_TYPE_SEND_MOBILE_CHKMSG = "SI_GNR0003";
    /** 交易代码 : 1 线上支付 */
    public static final String TRANS_TYPE_ONLINE_PAY = "SI_PAY0009";
    /** 交易代码 : 1 绑卡支付 */
    public static final String TRANS_TYPE_BIND_PAY = "SI_PAY0010";
    /** 交易代码 : 1 微信支付 */
    public static final String TRANS_TYPE_WEIXIN_PAY = "SI_PAY2001";
    /** 交易代码 : 2 转账 */
    public static final String TRANS_TYPE_TRA_ACC = "";
    /** 交易代码 : 3 提现 */
    public static final String TRANS_TYPE_CASH = "SI_ACC8003";
    /** 交易代码 : 3 充值 */
    public static final String TRANS_TYPE_RECHARGE = "SI_ACC8001";
    /** 交易代码 : 4 退款*/
    public static final String TRANS_TYPE_REFUND = "SI_PAY0015";
    /** 交易代码 : 4 注册*/
    public static final String TRANS_TYPE_REGISTE = "SI_USR0001";
    /** 交易代码 : 4 订单预生成*/
    public static final String TRANS_TYPE_CREORD = "SI_PAY1001";
    /** 交易代码 : 4 商户登陆标识检查*/
    public static final String TRANS_TYPE_CHKLOGIN = "SI_USR0027";
    /** 交易代码 : 4 确认收货*/
    public static final String TRANS_TYPE_CONREC = "SI_PAY0014";
    /** 交易代码 : 4 商户订单查询*/
    public static final String TRANS_TYPE_MERQRYORD = "SI_PAY0013";
    /** 交易代码 : 4 商户对账单下载*/
    public static final String TRANS_TYPE_MERDOWNCHK = "X";
    /** 交易代码 : 4 商户通知ID查询*/
    public static final String TRANS_TYPE_MERQRYNOTIFY = "SI_PAY0011";
    /** 交易代码 : 4 网银支付回调处理*/
    public static final String TRANS_TYPE_UNIONPAY_RETURN = "SI_PAY3001";
    
    /** 交易代码 : 4 网银线下主扫回调处理*/
    public static final String TRANS_TYPE_UNIONPAY_OFFLINE_RETURN = "SI_PAY3009";
    
    /** 交易代码 : 4 网银支付回调处理*/
    public static final String TRANS_TYPE_UNIONPAY_CODE_RETURN = "SI_PAY3010";
    
    /** 交易代码 : 4 查询是否含有流水*/
    public static final String TRANS_TYPE_UNIONPAY_CHECK_FLOWLIST = "SI_PAY3020";
    
    /** 交易代码 : 4 网关跳转预生成流水*/
    public static final String TRANS_TYPE_GATEWAY_PAYFLOW = "SI_PAY0012";
    /** 交易代码 : 4 网银支付回调处理*/
    public static final String TRANS_TYPE_ACP_RUFUND_RETURN = "SI_PAY3005";
    
    
    /** 核心接口 : CORE_BANK_PAY 支付 */
    public static final String CORE_BANK_PAY= "CORE_BANK_PAY";
    
    
    /** 平台在核心内部账户（待清算） : CORE_BANK_SETTLE_ACCNO  */
    public static final String CORE_BANK_SETTLE_ACCNO= "CORE_BANK_SETTLE_ACCNO";
    
    /** 通道超时状态    key */
    public static final String ROUTE_TIME_OUT_KEY="routeTimeout";
    /** 通道超时状态    0 超时 */
    public static final String ROUTE_TIME_OUT_VALUE="0";
    
    /** 树枝节点 */
    public static final String MENU_INFO_LEAF = "0";

    /**订单默认时效*/
    public static final String ORDER_LMT_TIME = "ORDER_LMT_TIME";
    /** 顶级地区代码 （查询所有省） */
    public static final String AREA_CODE_TOP = "000000";
    
    /**
     * 交易状态码，成功：0000000000
     */
    public static final String TRANS_SUCCESS="0000000000";
    /**
     * 交易状态码，成功：000000
     */
    public static final String TRANS_FAULT_SUCCESS="000000";
    
    
    
    /**
     * 中金结算标识：0001单个结算账户固定0001
     */
    public static final String ZJ_SETTLEMENTFLAG="0001";
    
    
    /**
     * 核心接口交易代码：08001：记账
     */
    public static final String CORE_BANK_PAY_CODE_08001="08001";
    /**
     * 核心接口交易代码：08002：记账冲正
     */
    public static final String CORE_BANK_PAY_CODE_08002="08002";
    /**
     * 核心接口交易代码：08003：单笔交易实时查询
     */
    public static final String CORE_BANK_PAY_CODE_08003="08003";
    /**
     * 核心接口交易代码：08007：中间账户查询
     */
    public static final String CORE_BANK_PAY_CODE_08007="08007";
    /**
     * 核心接口交易代码：08008：客户信息查询
     */
    public static final String CORE_BANK_PAY_CODE_08008="08008";
    /**
     * 核心接口交易代码：08009：客户信息验证
     */
    public static final String CORE_BANK_PAY_CODE_08009="08009";
    /**
     * 核心接口交易代码：08010：核心对账文件
     */
    public static final String CORE_BANK_PAY_CODE_08010="08010";
    /**
     * 核心接口交易代码：08011：贷记卡记账
     */
    public static final String CORE_BANK_PAY_CODE_08011="08011";
    /**
     * 单笔交易实时查询 801006
     */
    public static final String CORE_BANK_PAY_CODE_801006="801006";
    
    /**
     * 外围前置系统记帐交易 801001
     */
    public static final String CORE_BANK_PAY_CODE_801001="801001";
    
    
    /**
     * 核心接口交易代码：08011：贷记卡冲正
     */
    public static final String CORE_BANK_PAY_CODE_08013="08013";
    
    
    /**
     * 核心交易状态   0:成功
     */
    public static final String CORE_BANK_PAY_STAT_SUCCESS="0";
    /**
     * 核心交易状态   1：无此订单
     */
    public static final String CORE_BANK_PAY_STAT_NO="1";
    /**
     * 核心交易状态   2：已冲正
     */
    public static final String CORE_BANK_PAY_STAT_WASHED="2";
    
    

    /** 核心交易类型：1支付，2退货，3提现，4内传内 6内转对公账户 7-卡转公  8-公转内 9-行内账户相互转账*/
    public static final String CORE_BANK_PAY_TYPE_PAY="1";
    public static final String CORE_BANK_PAY_TYPE_REFUND="2";
    public static final String CORE_BANK_PAY_TYPE_CASH="3";
    public static final String CORE_BANK_PAY_TYPE_ITI="4";
    public static final String CORE_BANK_PAY_TYPE_PUBLIC="6";
    public static final String CORE_BANK_PAY_TYPE_CARDTOPUBLIC="7";
    public static final String CORE_BANK_PAY_TYPE_PUBLICTOIN="8";
    public static final String CORE_BANK_PAY_TYPE_ANYTHING="9";

    /** 中金接口名称      4530银企直连（单笔代付）*/
    public static final String ZJ_TRANS_CODE_4530="4530";
    /** 中金接口名称      2011银企直连（单笔代收）*/
    public static final String ZJ_TRANS_CODE_2011="2011";
    
    /** 中金快捷支付交易状态  10：处理中，20支付成功，30：支付失败 */
    public static final String ZJ_Q_PAY_STAT_ING="10";
    public static final String ZJ_Q_PAY_STAT_SUCCESS="20";
    public static final String ZJ_Q_PAY_STAT_FAIL="30";
    
    /** 中金快捷支付退款交易状态  10：已受理，20正在退款，30：退款成功，40退款失败 */
    public static final String ZJ_Q_REFUND_STAT_BIGIN="10";
    public static final String ZJ_Q_REFUND_STAT_ING="20";
    public static final String ZJ_Q_REFUND_STAT_SUCCESS="30";
    public static final String ZJ_Q_REFUND_STAT_FAIL="40";
    
    /** 中金网银 退款交易状态  10：已受理，20退款失败，30：正在退款，40已经执行(已经发送转账指令) */
    public static final String ZJ_Q_1132_STAT_BIGIN="10";
    public static final String ZJ_Q_1132_STAT_FAIL="20";
    public static final String ZJ_Q_1132_STAT_ING="30";
    public static final String ZJ_Q_1132_STAT_SUCCESS="40";
    
    /** 中金银企直连交易状态 20=正在处理 30=代付成功 40=代付失败*/
    public static final String ZJ_C_PAY_STAT_ING="20";
    public static final String ZJ_C_PAY_STAT_SUCCESS="30";
    public static final String ZJ_C_PAY_STAT_FAIL="40";
    
    /** 中金网银支付交易状态：10-未支付，20-已支付 */
    public static final String ZJ_C_EBANK_STAT_NO="10";
    public static final String ZJ_C_EBANK_STAT_YES="20";
    
    /** 外部接口交易状态    0：成功，1失败，2处理中 */
    public static final String OUT_PAY_STAT_SUCCESS="0";
    public static final String OUT_PAY_STAT_FAIL="1";
    public static final String OUT_PAY_STAT_ING="2";
    
    
    
    /** 限额累计    new新建，update更新 */
    public static final String FEE_DETAIL_INSERT="new";
    public static final String FEE_DETAIL_UPDATE="update";
    

    /** 交易类型：01支付 */
    public static final String SYS_TRANS_TYPE_PAY="01";
    /** 交易类型：02充值 */
    public static final String SYS_TRANS_TYPE_REC="02";
    /** 交易类型：03提现 */
    public static final String SYS_TRANS_TYPE_CAS="03";
    /** 交易类型：04转账 */
    public static final String SYS_TRANS_TYPE_TRA="04";
    /** 交易类型：05代收 */
    public static final String SYS_TRANS_TYPE_COLLECTION="05";
    /** 交易类型：06代付 */
    public static final String SYS_TRANS_TYPE_PAYMENT="06";
    /** 交易类型：09退款 */
    public static final String SYS_TRANS_TYPE_REF="09";
    
    /** 订单状态锁定标识，当微信，网银，银联支付的时候，订单状态 不要锁定 ,Y锁定，N不锁定*/
    public static final String LOCK_ORDER_STAT_YES="Y";
    public static final String LOCK_ORDER_STAT_NO="N";
    
    
    /** 商户退款时间限制天数 */
    public static final String MER_REFUND_OUT_DAYS="MER_REFUND_OUT_DAYS";
    
    public static final String DEFAULT_HEAD_PIC="DEFULT_HEAD_PIC";
    
    
    
    /** 交易代码 : 单笔代收 接口 */
    public static final String TRANS_CODE_SINGLE_COLLECTION = "SI_PAY0019";
    /** 交易代码 : 单笔代付 接口 */
    public static final String TRANS_CODE_SINGLE_PAYMENT = "SI_PAY0020";
    /** 交易代码 : 快捷支付 */
    public static final String TRANS_TYPE_QUICK_PAY = "SI_PAY0009";
    /** 交易代码 : 快捷支付(验证短信并验证信息) */
    public static final String TRANS_TYPE_QUICK_PAY_VERIFY_CODE = "SI_PAY0010";
    /*交易代码 无跳转签约和 代收签约  */
    public static final String TRANS_TYPE_QUICK_PAY_TOKEN_CODE = "SI_PAY3011";

    
    /** 文件信息汇总(T_FILE_INFO)文件类型 - 0 上传 */
    public static final String T_FILE_INFO_FILE_TYPE_UPLOAD = "0";
    /** 文件信息汇总(T_FILE_INFO)文件类型 - 1 下传 */
    public static final String T_FILE_INFO_FILE_TYPE_DOWNLOAD = "1";
    
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 0 初始化 */
    public static final String T_FILE_INFO_FILE_STAT_INIT = "0";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 1 文件创建成功 */
    public static final String T_FILE_INFO_FILE_STAT_CREATE_SUC = "1";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 2 文件创建失败 */
    public static final String T_FILE_INFO_FILE_STAT_CREATE_FAIL = "2";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 3 文件上传成功 */
    public static final String T_FILE_INFO_FILE_STAT_UPLOAD_SUC = "3";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 4 文件上传失败 */
    public static final String T_FILE_INFO_FILE_STAT_UPLOAD_FAIL = "4";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 5 文件下载成功 */
    public static final String T_FILE_INFO_FILE_STAT_DOWNLOAD_SUC = "5";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 6 文件下载失败 */
    public static final String T_FILE_INFO_FILE_STAT_DOWNLOAD_FAIL = "6";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 7 入库完成 */
    public static final String T_FILE_INFO_FILE_STAT_IN_DB = "7";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - 8 已处理 */
    public static final String T_FILE_INFO_FILE_STAT_PROCESSED = "8";
    /** 文件信息汇总(T_FILE_INFO) 文件状态 - B 回盘文件登记完成 */
    public static final String T_FILE_INFO_FILE_STAT_BACK_RECORD = "B";
    /** 文件信息汇总（T_FILE_INFO） 文件状态 -R 文件记录成功 */
    public static final String T_FILE_INFO_FILE_STAT_RECORDSUC = "R";

    /** 文件信息汇总(T_FILE_INFO) 任务类型 0001 下载批量转账文件 */
    public static final String T_FILE_INFO_TASK_CODE_TRANSFER = "0001";
    /** 文件信息汇总(T_FILE_INFO) 任务类型 0002 批量回盘转账文件 */
    public static final String T_FILE_INFO_TASK_CODE_BACK_FILE = "0002";
}   
