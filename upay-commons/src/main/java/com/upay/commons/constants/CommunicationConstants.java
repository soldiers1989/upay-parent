package com.upay.commons.constants;

/**
 * 接口服务自端常量设置
 * 
 * @author freeplato
 * 
 */
public interface CommunicationConstants {

    /** 微信OPNEID */
    public static final String OPEN_ID = "OPEN_ID";

    /** USER_ID */
    public static final String USER_ID = "USER_ID";

    /** CHNL_ID */
    public static final String CHNL_ID = "CHNL_ID";

    /** SYS_DATE */
    public static final String SYS_DATE = "sysDate";

    /** SYS_TIME */
    public static final String SYS_TIME = "sysTime";

    /** VERIFY_KEY */
    public static final String VERIFY_KEY = "VERIFY_KEY";

    /** 验证码 */
    public static final Object VERIFY_CODE = "VERIFY_CODE";

    /** 手机号 */
    public static final String MOBILE = "MOBILE";

    /** 证件号码 */
    public static final String CERT_NO = "CERT_NO";

    /** tokenId防重复标示值 */
    public static final String TOKEN_ID = "TOKEN_ID";
    /** 身份证正面照 */
    public static final String FILE_TYPE_CERTFRONT = "1";

    /** 身份证反面照 */
    public static final String FILE_TYPE_CERTBACK = "2";

    /** 个人手持身份证正面照 */
    public static final String FILE_TYPE_CERTPERSON = "3";

    /** 是否需要增加图形验证码的验证 0:不需要 */
    public static final String VERIFY_CODE_FLAG_NO = "0";
    /** 是否需要增加图形验证码的验证 1:需要 */
    public static final String VERIFY_CODE_FLAG_YES = "1";

    /** BODY */
    public static final String BODY = "BODY";

    /** 是否记录密码统计 0:否 */
    public static final String PWDCOUNTDEALFLAG_NO = "0";
    /** 是否记录密码统计 1:是 */
    public static final String PWDCOUNTDEALFLAG_YES = "1";

    /** 查询条件 ：起始日期标志 */
    public static final String QUERY_FLAG_START = "1";
    /** 查询条件：终止日期标志 */
    public static final String QUERY_FLAG_END = "2";

    /** 查询范围 01:今天 */
    public static final String QUERY_SCOPE_TODAY = "01";
    /** 查询范围 02:近一周 */
    public static final String QUERY_SCOPE_WEEK = "02";
    /** 查询范围 03:近一个月 */
    public static final String QUERY_SCOPE_MONTH = "03";
    /** 查询范围 04:近三个月 */
    public static final String QUERY_SCOPE_THDMONTH = "04";

    /** 查询范围 05:近三个月 */
    public static final String QUERY_SCOPE_THDYEAR= "05";

    /** 日期处理 1:将当前年月日日期补上时分秒00:00:00 */
    public static final String PRE_TIME = "1";
    /** 日期处理 2:将当前年月日日期补上时分秒23:59:59 */
    public static final String POST_TIME = "2";

    /** 上传方式 1：FTP上传 */
    public static final String UPLOAD_FLAG_FTP = "1";
    /** 上传方式 2：BASE6转码字符 */
    public static final String UPLOAD_FLAG_BASE64STR = "2";

    /** 是否需要联网核查 */
    public static final String IS_ONLINE_CERT_CHECK_YES = "1";

    /** 用户注册 */
    public static final String TRANS_CODE_REG = "SI_USR00002";

    /** 弱实名认证 */
    public static final String TRANS_CODE_WEAK_CERT_AUTH = "SI_USR00006";

    /** 限额查询标志 1:借贷方向 */
    public static final String SYS_LMT_DC_FLAG = "1";
    /** 限额查询标志 2:交易代码 */
    public static final String SYS_LMT_TRANS_CODE = "2";

    /** 微信消息类型 event:事件 */
    public static final String WEIXIN_MSGTYPE_EVENT = "event";
    /** 微信事件类型 CLICK:点击事件 */
    public static final String WEIXIN_EVENT_CLICK = "CLICK";
    
    /**图片上传路径 */
    public static final String HEADPIC_UPLOAD_FLAG = "uploadFlag";
    

}
