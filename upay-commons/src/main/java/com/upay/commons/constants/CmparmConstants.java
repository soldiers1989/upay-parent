package com.upay.commons.constants;

/**
 * 参数配置表的常量定义
 * 
 * @author freeplato
 * 
 */
public interface CmparmConstants {

    /** 芝麻信用 请求成功 结果码 核实正确 ***/
    public static final String ZM_RESULTCODE_CHECKPASS = "CG1001";

    // *********************中金常量配置***********************************
    /** 1810接口处理 对账 */
    public static final String GATEWAY_ZJPAY_1810 = "1810";

    /** 1811接口处理 分页对账 */
    public static final String GATEWAY_ZJPAY_1811 = "1811";

    /** 2501接口处理 建立绑定关系 */
    public static final String GATEWAY_ZJPAY_2501 = "2501";

    /** 2502接口处理 查询绑定关系 */
    public static final String GATEWAY_ZJPAY_2502 = "2502";

    /** 2503接口处理 解除绑定关系 */
    public static final String GATEWAY_ZJPAY_2503 = "2503";

    /** 2511接口处理 快捷支付 */
    public static final String GATEWAY_ZJPAY_2511 = "2511";

    /** 2512接口处理 快捷支付查询 */
    public static final String GATEWAY_ZJPAY_2512 = "2512";

    /** 2521接口处理 快捷支付退款 */
    public static final String GATEWAY_ZJPAY_2521 = "2521";

    /** 2521接口处理 快捷支付退款查询 */
    public static final String GATEWAY_ZJPAY_2522 = "2522";

    /** 2561接口处理 快捷支付（一键支付，无验证短信） */
    public static final String GATEWAY_ZJPAY_2561 = "2561";

    /** 4510接口处理 机构支付账户余额查询 */
    public static final String GATEWAY_ZJPAY_4510 = "4510";

    /** 4512接口处理 机构支付账户交易明细查询 */
    public static final String GATEWAY_ZJPAY_4512 = "4512";

    /** 4530接口处理 代付 */
    public static final String GATEWAY_ZJPAY_4530 = "4530";

    /** 4532接口处理 代付查询 */
    public static final String GATEWAY_ZJPAY_4532 = "4532";

    /** 2310接口处理 账户验证 */
    public static final String GATEWAY_ZJPAY_2310 = "2310";

    /** 2320接口处理 身份验证 */
    public static final String GATEWAY_ZJPAY_2320 = "2320";

    /** 2340 身份验证四要素 */
    public static final String GATEWAY_ZJPAY_2340 = "2340";

    /** 1111接口处理 商户订单支付（ 直通车 ） */
    public static final String GATEWAY_ZJPAY_1111 = "1111";

    /** 1112接口处理 商户订单支付（不要确认） */
    public static final String GATEWAY_ZJPAY_1112 = "1112";

    /** 1120- 商户订单支付查询 */
    public static final String GATEWAY_ZJPAY_1120 = "1120";

    /** 1131- 商户订单退款 */
    public static final String GATEWAY_ZJPAY_1131 = "1131";

    /** 1132-商户订单退款查询 */
    public static final String GATEWAY_ZJPAY_1132 = "1132";
    
    /** 1133-商户订单原路退款 */
    public static final String GATEWAY_ZJPAY_1133 = "1133";

    /** 2011-单笔代收 */
    public static final String GATEWAY_ZJPAY_2011 = "2011";

    /** 2020-单笔代收查询 */
    public static final String GATEWAY_ZJPAY_2020 = "2020";

    /** 1610-批量代扣 */
    public static final String GATEWAY_ZJPAY_1610 = "1610";

    /** 1620-批量代扣查询 */
    public static final String GATEWAY_ZJPAY_1620 = "1620";

    /** 1630-批量代扣明细查询 */
    public static final String GATEWAY_ZJPAY_1630 = "1630";

    /** 1650-批量代扣 交易对账 */
    public static final String GATEWAY_ZJPAY_1650 = "1650";

    /** 1510-批量代付 */
    public static final String GATEWAY_ZJPAY_1510 = "1510";

    /** 1520-批量代付查询 */
    public static final String GATEWAY_ZJPAY_1520 = "1520";

    /** 1550-批量代付交易对账 */
    public static final String GATEWAY_ZJPAY_1550 = "1550";
    
    /** 4534-机构支付账户批量 代付 （实时） */
    public static final String GATEWAY_ZJPAY_4534 = "4534";
    
    /** 4538-4538- 机构支付账户批量 代付 查询 */
    public static final String GATEWAY_ZJPAY_4538 = "4538";

    /** 处理成功状态：2000 */
    public static final String GATEWAY_ZJPAY_HANDLE_2000 = "2000";

    /** 红塔银行统一支付机构码 002367 */
    public static final String GATEWAY_ZJPAY_INSTITUTION_ID = "002367";
    /** 直销银行 002366 */
    public static final String GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK = "002366";
    /** 企业资金归集 002368  Enterprise fund collection*/
    public static final String GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION = "002368";
    /** 民生缴费 002402  Livelihood payment*/
    public static final String GATEWAY_ZJPAY_INSTITUTION_ID_LIVELIHOOD_PAYMENT = "002402";

    public static final String RECOMMEND_PAKG_NUM = "RECOMMEND_PAKG_NUM";
    /** 银联商户号 */
    public static final String ACP_MER_ID = "merId";
    
    public static final String ACP_ISSCODE = "issCode";
    public static final String ACP_ACQCODE = "acqCode";

    /** 银联编码方式 */
    public static final String ENCODING = "utf-8";

    public static final String REQUEST_BACK_URL = "requestBackUrl";

    /** 请求超时时间 */
    public static final String ACP_CONNECTION_TIMEOUT = "connectionTimeout";
    /** 响应超时时间 */
    public static final String ACP_READ_TIMEOUT = "readTimeout";
    /** 后台通知地址 */
    public static final String ACP_BACK_URL = "backUrl";
    /** 银联在线版本号 */
    public static final String ACP_VERSION = "acpVersion";
    /** 银联文件传输类交易文件路径 */
    public static final String ACP_FILE_ROOT = "acpFileRoot";

    /** 银联应答码 '00'-成功 */
    public static final String ACP_RESP_CODE = "00";

    /** 商户类型 '0'-普通商户;'1'-收单类商户;'2'-平台类商户 */
    public static final String ACP_ACCESS_TYPE_GNR = "0";
    public static final String ACP_ACCESS_TYPE_BILL = "1";
    public static final String ACP_ACCESS_TYPE_PLAT = "2";
    /** 银联签名方式 */
    public static final String ACP_SIGN_METHOD = "signMethod";

    /** 8f8交易码-单笔代收 */
    public static final String BFB_SINGLE_COLLECTION = "singleCollection";
    /** 8f8交易码-单笔代付 */
    public static final String BFB_SINGLE_PAYMENT = "singlePayment";
    /** 8f8交易码-订单查询 */
    public static final String BFB_ORDER_QUERY = "orderQuery";
    /** 8f8交易码-对账 */
    public static final String BFB_SETTLEMENT = "settlement";
    /** 8f8交易码-代付撤销 */
    public static final String BFB_PAY_CANCEL = "payCancel";;
    /** 8f8交易码-批量交易 */
    public static final String BFB_BATCH_TRANS = "batchTrans";
    /** 8f8交易码-批量查询 */
    public static final String BFB_BATCH_QUERY = "batchQuery";

    /** 8f8交易码-批量查询 */
    public static final String BFB_BATCH_AUTH = "UserAuth";

    /** 帮付宝答码 'B0'-成功 */
    public static final String BFB_RESP_CODE = "000000";

    /** 网站SESSION失效时间(分钟) */
    public static final String SESSION_WEB_INVALID_MINUTE = "SESSION_WEB_INVALID_MINUTE";
    /** 手机APPSESSION失效时间(分钟) */
    public static final String SESSION_APP_INVALID_MINUTE = "SESSION_APP_INVALID_MINUTE";
    /** 单点登录控制 */
    public static final String SINGLE_LOGIN_FLAG = "SINGLE_LOGIN_FLAG";
    /** 单点登录控制 1:单点登录 2:多渠道协同登录 */
    public static final String SINGLE_LOGIN_FLAG_YES = "1";
    /** 登录密码日累计连续错误次数上限 */
    public static final String LOG_TOT_MAX_ERR = "LOG_TOT_MAX_ERR";
    /** 登录密码每日连续错误次数上限 */
    public static final String LOG_DAY_MAX_ERR = "LOG_DAY_MAX_ERR";
    /** 登录密码锁定时效 */
    public static final String LOG_TEMP_HOUR = "LOG_TEMP_HOUR";
    /** 交易密码日累计连续错误次数上限 */
    public static final String TRADE_TOT_MAX_ERR = "TRADE_TOT_MAX_ERR";
    /** 支付密码每日连续错误次数上限 */
    public static final String TRADE_DAY_MAX_ERR = "TRADE_DAY_MAX_ERR";
    /** 支付密码临时锁定时效 */
    public static final String TRADE_TEMP_HOUR = "TRADE_TEMP_HOUR";

    public static final String TRANS_TYPE_BEFORE = "IN";// 交易前转换
    public static final String TRANS_TYPE_AFTER = "OUT";// 交易后转换

    /** 登录错误需要验证验证码次数 */
    public static final String LOGIN_VERIFY_TIMES = "LOGIN_VERIFY_TIMES";

    /** 日终清分赎回文件本地路径 */
    public static final String AMOUNT_CLEARING_ROOT = "AMOUNT_CLEARING_ROOT";

    /** 差错文件本地路径 */
    public static final String DIFF_TRADE_ADJUST_ROOT = "DIFF_TRADE_ADJUST_ROOT";

    /** 生成对账文件本地路径 */
    public static final String CREATE_CHECK_FILE_ROOT = "CREATE_CHECK_FILE_ROOT";

    /** 日终调用程序开关 */
    public static final String HX_KG = "HX_KG";
    /** 日终调用程序开关 1:调用该方法 */
    public static final String HX_KG_K = "1";
    /** 日终调用程序开关 0：忽略该刚方法 */
    public static final String HX_KG_G = "0";

    /** 图形验证码失效时间 */
    public static final String VALIDATE_CODE_TIMEOUT = "VALIDATE_CODE_TIMEOUT";
    /** 对账日期 */
    public static final String CHK_DATE_SET = "chkDateSet";
    /** 通道号 */
    public static final String ROUTE_CODE = "rountCode";
    /** 付款通道号 */
    public static final String PAYER_ROUTE_CODE = "payerRouteCode";

    /** 图片上传路径 ***/
    public static final String IMG_UPLOAD_PATH = "IMG_UPLOAD_PATH";

    /** 图片服务器域名 ***/
    public static final String IMG_DOMAIN_NAME = "IMG_DOMAIN_NAME";

    /** 优惠活动HTML文件上传路径 ***/
    public static final String HTML_UPLOAD_PATH = "HTML_UPLOAD_PATH";

    /** HTML文件域名 ***/
    public static final String HTML_DOMAIN_NAME = "HTML_DOMAIN_NAME";

    /** 自动确认收货时效 ***/
    public static final String CONFIRM_TIME = "CONFIRMTIME";

    /** 邮箱验证时间 ***/
    public static final String VALIED_TERM = "VALIEDTERM";

    /** 发送邮件拼接（本地ip） ***/
    public static final String LOCAL_IP = "LOCAL_IP";

    /** 发送邮件邮箱 ***/
    public static final String MAIL_FROM = "MAIL_FROM";

    /** 邮件图标 ***/
    public static final String MAIL_IMG = "MAIL_IMG";

}
