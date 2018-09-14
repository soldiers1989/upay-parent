package com.upay.commons.constants;

/**
 * 数据库对应的常量
 * 
 * @author USR
 * 
 */
public interface DataBaseConstants_USR {
	/**密码控件开关**/
	public final String ENCRYPTOR_FLAG="ENCRYPTOR_FLAG";
	/**企业登录密码重置，发送邮件**/
	public final String TRANS_CODE_SEND_MAIL="SI_USR0025";
	/**企业注册，发送邮件**/
	public final String TRANS_CODE_SENDMAIL="SI_USR0023";
    /** 用户状态,0 正常 */
    public static final String USR_STAT_NORMAL = "0";
    /** 用户状态,1 待激活 */
    public static final String USR_STAT_WAIT = "1";
    /** 用户状态,9 注销 */
    public static final String USR_STAT_LOGOFF = "9";

    /** 用户登录状态,0 失败 */
    public static final String USR_LOGIN_STAT_FAIL = "0";
    /** 用户登录状态,1 成功 */
    public static final String USR_LOGIN_STAT_SUCCUESS = "1";

    /** 密码类型,1 登录密码 */
    public static final String PWD_FLAG_LOGIN_PWD = "1";
    /** 密码类型,2 交易密码 */
    public static final String PWD_FLAG_TRADE_PWD = "2";

    /** 锁定标志,1 锁定 */
    public static final String LOCK_FLAG_LOCK = "1";
    /** 锁定标志,2 解锁 */
    public static final String LOCK_FLAG_UNLOCK = "2";

    /** 锁定时效,1 临时锁定 */
    public static final String LOCK_MODE_TEMPORARY_LOCK = "1";
    /** 锁定时效,2 非临时锁定 */
    public static final String LOCK_MODE_UNTEMPORARY_LOCK = "2";

    /** 是否单点登录 0:否 */
    public static final String IS_MUTIPLUE_LOGIN_NO = "0";
    /** 是否单点登录 1:是 */
    public static final String IS_MUTIPLUE_LOGIN_YES = "1";

    /** 黑名单标志,1 黑名单 */
    public static final String BLACK_LIST_FLAG_YES = "1";
    /** 黑名单标志,0 正常 */
    public static final String BLACK_LIST_FLAG_NO = "0";

    /** 身份认证方式,1 实时认证 */
    public static final String CERT_FLAG_ACTUAL_CERT = "1";
    /** 身份认证方式,2 审核认证 */
    public static final String CERT_FLAG_VERIFY = "2";
    /** 身份认证方式,3 联机认证 */
    public static final String CERT_FLAG_ONLINE_CERT = "3";

    /** 登录标识 1：手机号 */
    public static final String LOGIN_FLAG_MOBILE = "1";
    /** 登录标识 2：身份证 */
    public static final String LOGIN_FLAG_CERT = "2";
    /** 登录标识 3：合作平台 */
    public static final String LOGIN_FLAG_UNION = "3";

    /** 注册类型 1：手机号 */
    public static final String REG_FLAG_MOBILE = "1";
    /** 注册类型 2：身份证 */
    public static final String REG_FLAG_CERT = "2";
    /** 注册类型 3：合作平台 */
    public static final String REG_FLAG_UNION = "3";

    /** 注册类型 1：联机修改 */
    public static final String APPLY_TYPE_ONLINE = "1";
    /** 注册类型 2：审核修改 */
    public static final String APPLY_TYPE_VERIFY = "2";

    /** 用户状态 0：正常 */
    public static final String USER_STAT_NORMAL = "0";
    /** 用户状态 1：待激活 */
    public static final String USER_STAT_UNACTIVATE = "1";
    /** 用户状态 9：注销 */
    public static final String USER_STAT_LOGOFF = "9";

    /** 用户操作权限 0：不可操作 */
    public static final String OP_PERMISSION_NO = "0";
    /** 用户操作权限 1：可以操作 */
    public static final String OP_PERMISSION_YES = "1";

    /** 弱实名认证方式 01：临柜 */
    public static final String CERT_WEAK_WAY_BANKING_TERMS = "01";
    /** 弱实名认证方式 02：核查身份证信息 */
    public static final String CERT_WEAK_WAY_CERT = "02";
    /** 弱实名认证方式 03：上传身份证 */
    public static final String CERT_WEAK_WAY_UPLOAD_CERT = "03";

    /** 用户认证等级 02：手机认证 */
    public static final String USER_CERT_LEVEL_MOBILE = "02";
    /** 用户认证等级 03：弱实名认证 */
    public static final String USER_CERT_LEVEL_WEAK_AUTH = "03";
    /** 用户认证等级 03：强实名认证 */
    public static final String USER_CERT_LEVEL_STRONG_AUTH = "04";

    /** 实名认证申请状态 0：已通过 */
    public static final String CERT_STAT_YES = "0";
    /** 实名认证申请状态 1：待审核 */
    public static final String CERT_STAT_WAIT = "1";
    /** 实名认证申请状态 2：已拒绝 */
    public static final String CERT_STAT_NO = "2";

    /** 手机号存在标识 0：否 */
    public static final String MOBILE_FLAG_NO = "0";
    /** 手机号存在标识 1：是 */
    public static final String MOBILE_FLAG_YES = "1";

    /** 普通登录标识 0：否 */
    public static final String LOG_FLAG_NO = "0";
    /** 普通登录标识 1：是 */
    public static final String LOG_FLAG_YES = "1";

    /** 登录状态 1：成功 */
    public static final String LOGIN_STAT_SUCCESS = "1";
    /** 登录状态 0：失败 */
    public static final String LOGIN_STAT_FAIL = "0";

    /** 锁定方式 1：日连续错误锁定 */
    public static final String LOCK_WAY_DAY_ERR = "1";
    /** 锁定方式 2：累计连续错误锁定 */
    public static final String LOCK_WAY_TOT_ERR = "2";
    /** 锁定方式 3：后台管理员锁定 */
    public static final String LOCK_WAY_ADMIN = "3";

    /** 锁定功能 1：登录密码锁定 */
    public static final String LOCK_FUNCTION_LOGIN_PWD_LOCK = "1";
    /** 锁定功能 2：交易密码锁定 */
    public static final String LOCK_FUNCTION_TRADE_PWD_LOCK = "2";

    /** 锁定功能 1：系统自动锁定 */
    public static final String LOCK_SOURCE_SYSTEM_LOCK = "1";
    /** 锁定功能 2：操作员锁定 */
    public static final String LOCK_SOURCE_ADMIN_LOCK = "2";

    /** 是否需要修改密码 0：否 */
    public static final String IF_NO_MOD_PWD_NO = "0";
    /** 是否需要修改密码 1：是 */
    public static final String IF_NO_MOD_PWD_YES = "1";

    /** 审批状态 0：待审批 */
    public static final String APPROVE_STAT_WAIT = "0";
    /** 审批状态 1：已审批 */
    public static final String APPROVE_STAT_YES = "1";
    /** 审批状态 2：已拒绝 */
    public static final String APPROVE_STAT_NO = "2";

    /** 证件类型 01：身份证 */
    public static final String CERT_TYPE_CERT = "01";
    public static final String CERT_TYPE_ID_CARD = "0";

    /** 锁定标志 000000：初始化 */
    public static final String USER_LOCK_FLAG_INIT = "000000";
    /** 锁定标志 0：正常 */
    public static final String USER_LOCK_FLAG_NORMAL = "0";
    /** 锁定标志 1：锁定 */
    public static final String USER_LOCK_FLAG_LOCK = "1";

    /** 锁定标识 000000 */
    public static final String USER_LOCK_FLAG = "000000";

    /** 密码是否通过标识 P 通过 */
    public static final String PWD_CHECK_FLAG = "P";
    /** 密码是否通过标识 N 未通过 */
    public static final String PWD_CHECK_FLAG_N = "N";

    /** 是否需要密码错误次数统计标识 是 Y */
    public static final String PWD_ERROR_COUNT_Y = "Y";
    /** 是否需要密码错误次数统计标识 否 N */
    public static final String PWD_ERROR_COUNT_N = "N";

    /** 是否需要支付密码错误次数统计标识 是 Y */
    public static final String TRA_ERROR_COUNT_Y = "Y";
    /** 是否需要支付密码错误次数统计标识 否 N */
    public static final String TRA_ERROR_COUNT_N = "N";

    /** 地址状态标志 0：未获取 */
    public static final String ADDR_GET_FLAG_NO = "0";
    /** 地址状态标志 1：已获取 */
    public static final String ADDR_GET_FLAG_YES = "1";
    /** 地址状态标志 2：无法获取 */
    public static final String ADDR_GET_FLAG_UNCAN = "2";

    /** 用户微信绑定状态 1：已绑定 */
    public static final String BIND_WECHAT_STAT_YES = "1";
    /** 用户微信绑定状态 2：已解绑 */
    public static final String BIND_WECHAT_STAT_NO = "2";

    /** 默认地址标志 1:是 */
    public static final String DEFAULT_FLAG_YES = "1";
    /** 默认地址标志 1:否 */
    public static final String DEFAULT_FLAG_NO = "0";

    /** 密码锁定状态 0:不锁定 */
    public static final String PWD_LOCK_NO = "0";
    /** 密码锁定状态 1:锁定 */
    public static final String PWD_LOCK_YES = "1";

    /** 核查结果 0:成功 */
    public static final String CERT_CHECK_RESULT_SUCCESS = "0";
    /** 核查结果 1:失败 */
    public static final String CERT_CHECK_RESULT_FAIL = "1";

    /** 授权免密绑定 0待激活 */
    public static final String MERCHANT_BIND_STAT_UNACTIVE = "0";
    /** 授权免密绑定 1已绑定 */
    public static final String MERCHANT_BIND_STAT_BIND = "1";
    /** 授权免密绑定 2过期 */
    public static final String MERCHANT_BIND_STAT_OVERDUE = "2";

    /** 登录方式:1 登录密码登录 */
    public static final String USER_LOGIN_MODE_PWD = "1";
    /** 登录方式:2 手势密码登录 */
    public static final String USER_LOGIN_MODE_GESTURE = "2";
    /** 登录方式:3 邮箱登录 */
    public static final String USER_LOGIN_MODE_EMAIL = "3";
    /** 登录方式:4 登录名登录 */
    public static final String USER_LOGIN_MODE_NAME = "4";
    /** 登录方式:5 人脸识别登录 */
    public static final String USER_LOGIN_MODE_FACE = "5";
    /** 登录方式:6 扫码登录 */
    public static final String USER_LOGIN_MODE_CODE = "6";
    /** 登录方式:7 免密登录 */
    public static final String USER_LOGIN_MODE_EXEMPT = "7";

    /** 注册会员类型:1 免密注册 */
    public static final String USER_REG_TYPE_EXEMPT = "01";
    /** 注册会员类型:2 普通会员注册 */
    public static final String USER_REG_TYPE_COMMON = "02";
    /** 注册会员类型:3 商户会员注册 */
    public static final String USER_REG_TYPE_MEMBER = "03";

    /** 登陆状态：0失败 */
    public static final String USER_LOGIN_FAIL = "0";
    /** 登陆状态：1成功 */
    public static final String USER_LOGIN_SUCCESS = "1";

    /** 商户授权免密绑定标志 ：0 非授权免密 */
    public static final String Mer_WITHOUT_PWD_SIGN_NO = "0";
    /** 商户授权免密绑定标志 ：1 授权免密 */
    public static final String Mer_WITHOUT_PWD_SIGN_YES = "1";

    /** 渠道,门户网站-02 */
    public static final String CHNL_ID_WEB = "02";
    /** 渠道,手机-01 */
    public static final String CHNL_ID_APP = "01";
    /** 渠道,微信-03 */
    public static final String CHNL_ID_WEIXIN = "03";

    /** 密码修改 */
    public static final String PWD_CHG = "01";
    /** 密码重置 */
    public static final String PWD_RESET = "02";

    /** 登录方式:手机登录 */
    public static final String LOGIN_MODE_MOBILE = "1";
    /** 登录方式:身份证登录 */
    public static final String LOGIN_MODE_CERTNO = "2";

    /** 支付密码标识:未开户且没有设置支付密码 */
    public static final String PAY_PWD_NO1 = "01";
    /** 支付密码标识:开户但没有设置支付密码 */
    public static final String PAY_PWD_NO2 = "02";
    /** 支付密码标识:已经设置支付密码 */
    public static final String PAY_PWD_YES = "03";

    /** 交易返回码,交易成功-0000000000 */
    public static final String RSP_CODE_SUCCESS = "0000000000";
    /** 交易返回码,交易成功-'交易成功' */
    public static final String RSP_CODE_SUCCESS_DESC = "交易成功";
    /** 交易返回码,交易失败-999999999 */
    public static final String RSP_CODE_FAIL = "999999999";
    /** 交易返回码,交易失败-'交易失败' */
    public static final String RSP_CODE_FAIL_DESC = "交易失败";

    /** 检查手机号 */
    public static final String CHECK_MOBILE_PHONE_YES = "Y";
    /** 检查账户名 */
    public static final String CHECK_ACCOUNT_NAME_YES = "Y";
    /** 检查证件号 */
    public static final String CHECK_CERTIFICATENO_YES = "Y";

    /** 登录密码新旧密码是否相同01 */
    public static final String LOGIN_REPEAT_CHECK = "01";
    /** 支付密码新旧密码是否相同 02 */
    public static final String TRADE_REPEAT_CHECK = "02";

    /** 工作流状态：待审核 */
    public static final String WF_STATUS_1 = "10";

    /** (企业)是否已生成用户 0-没有 */
    public static final String GENERATE_FLAG_NO = "0";
    /** (企业)是否已生成用户 1-已生成 */
    public static final String GENERATE_FLAG_YES = "1";
    /** (企业)未使用该链接重置密码 */
    public static final String GENERATE_FLAG_NO1 = "2";
    /** (企业)已经使用该链接重置密码 */
    public static final String GENERATE_FLAG_YES1 = "3";

    /** 功能类型：企业注册 */
    public static final String FUNC_TYPE_REG = "01";
    /** 功能类型：邮箱重置登录密码 */
    public static final String FUNC_TYPE_RES = "02";

    /** 商户级别00：新注册商户 */
    public static final String MER_LEVER_0 = "00";
    /** 商户级别01：一级商户 */
    public static final String MER_LEVER_1 = "01";
    /** 商户级别02：二级商户 */
    public static final String MER_LEVER_2 = "02"; 
    /** 重置用户登录密码 验证码验证通过*/
    public static final String VERIFY_IS_PASS = "IS_PASS";
    
    /** 不是软件盘*/
    public static final String PASS_IS_MITYPE = "1";
}
