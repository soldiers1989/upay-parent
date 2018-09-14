package com.upay.commons.constants;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月8日 上午10:14:37
 */
public interface DataBaseConstants_BATCH {
    
    /** 微信对账单下载错误码 ：：账单不存在 */
    public static String T_CHK_WEIXIN_DOWNLOAD_NO_EXIST = "No Bill Exist";
    
    
    /** 第三方对账标志 ： 0 未对账 */
    public static String T_CHK_FLAG_NO = "0";
    /** 第三方对账标志 ： 1 对账成功 */
    public static String T_CHK_FLAG_SUCCESS = "1";
    /** 第三方对账标志 ： 2 对方多 */
    public static String T_CHK_FLAG_LESS = "2";
    /** 第三方对账标志 ： 3 本系统多 */
    public static String T_CHK_FLAG_MORE = "3";
    /** 第三方对账标志 ： 4 金额不 符 */
    public static String T_CHK_FLAG_NOT_BALANCED = "4";
    
    /** 主机对账标志 ： 0-	主机无 */
    public static String T_CHK_HOST_FLAG_HOST_LESS = "0";
    /** 主机对账标志 ： 1-	主机有 */
    public static String T_CHK_HOST_FLAG_FLAG_MORE = "1";
    /** 主机对账标志 ： 2-	帐不平 */
    public static String T_CHK_HOST_FLAG_NOT_BALANCED  = "2";
    /** 主机对账标志 ： 3-	主机有，平台无 */
    public static String T_CHK_HOST_FLAG_LESS= "3";

    /** 结算标志 ： 0 未结算 */
    public static String T_STL_FLAG_NO = "0";
    /** 结算标志 ： 1 结算中 */
    public static String T_STL_FLAG_ING = "1";
    /** 结算标志 ： 2 已结算 */
    public static String T_STL_FLAG_END = "2";

    /**
     * 清算标志 0-未清算 
     */
    public static final String T_CLEAR_FLAG_NO = "0";
    /**
     * 清算标志  1-一级清算中
     */
    public static final String T_CLEAR_FLAG_ONEING = "1";
    /**
     * 清算标志  2-一级清算失败
     */
    public static final String T_CLEAR_FLAG_ONEFAIL = "2";
    /**
     * 清算标志  3-一级清算完成
     */
    public static final String T_CLEAR_FLAG_ONESUC = "3";
    /**
     * 清算标志 4-二级清算中
     */
    public static final String T_CLEAR_FLAG_TWOING = "4";
    /**
     * 清算标志 5-二级清算失败
     */
    public static final String T_CLEAR_FLAG_TWOFAIL = "5";
    /**
     * 清算标志 5-二级清算成功
     */
    public static final String T_CLEAR_FLAG_TWOSUC = "6";
    /**
     * 清算标志 7-清算成功
     */
    public static final String T_CLEAR_FLAG_SUC = "7";
    /**
     * 清算标志 8-待清算
     */
    public static final String T_CLEAR_FLAG_READY = "8";
    /**
     * 清算标志  9-清算中
     */
    public static final String T_CLEAR_FLAG_ING = "9";

    /** 系统状态:N-正常 */
    public static final String SYS_STAT_NORMAL = "N";
    /** 系统状态:C-日切 */
    public static final String SYS_STAT_CUT = "C";

    // 对账明细表:对账状态
    /** 对账状态 ： 0：对账处理中 */
    public static String T_CHK_STAT_CHECKING = "0";
    /** 对账状态 ： 1：对账完成 */
    public static String T_CHK_STAT_SUCCESS = "1";
    /** 对账状态 ： 2：对账失败 */
    public static String T_CHK_STAT_FAIL = "2";
    /** 对账状态 ： 3：未对账 */
    public static String T_CHK_STAT_NO = "3";

    /** 对账文件类型（T_CHK_INFO.FILE_TYPE） ：0 下载 */
    public static String T_CHK_FILE_TYPE_DOWNLOAD = "0";
    /** 对账文件类型（T_CHK_INFO.FILE_TYPE）：1 上传 */
    public static String T_CHK_FILE_TYPE_UPLOAD = "1";

    /** 对账文件处理状态（T_CHK_INFO.FILE_DOWN_STAT）：0 文件请求发送 */
    public static String T_CHK_FILE_DEAL_STAT_REQUEST_SEND = "0";
    /** 对账文件处理状态（T_CHK_INFO.FILE_DOWN_STAT）：1 文件请求登记 */
    public static String T_CHK_FILE_DEAL_STAT_REQUEST_RECORD = "1";
    /** 对账文件处理状态（T_CHK_INFO.FILE_DOWN_STAT）：2 文件下载中 */
    public static String T_CHK_FILE_DEAL_STAT_DOWNLOADING = "2";
    /** 对账文件处理状态（T_CHK_INFO.FILE_DOWN_STAT）：3 文件下载成功 */
    public static String T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC = "3";
    /** 对账文件处理状态（T_CHK_INFO.FILE_DOWN_STAT）：9 文件下载失败 */
    public static String T_CHK_FILE_DEAL_STAT_DOWNLOAD_FAIL = "9";

    /** 对账数据基准标准 '01'-第三方为主 */
    public static final String T_CHK_BENCHMARK_FLAG_THIRD = "01";
    /** 对账数据基准标准 '02'-核心为主 */
    public static final String T_CHK_BENCHMARK_FLAG_CORE = "02";
    /** 对账数据基准标准 '03'-支付平台为主 */
    public static final String T_CHK_BENCHMARK_FLAG_UPAY = "03";
    
    
    /** 单位类型：001：统一支付平台 002：资金通道 003：银行机构 004：合作商户  */
    public String UNIT_TYPE_001="001";
    public String UNIT_TYPE_002="002";
    public String UNIT_TYPE_003="003";
    public String UNIT_TYPE_004="004";

    
    /** 对账明细撤销标志    0否，1是 */
    public static final String T_CHK_CANCEL_FLAG_N="0";
    /** 对账明细撤销标志    0否，1是 */
    public static final String T_CHK_CANCEL_FLAG_Y="1";
    
    
    /** 对账明细冲正标志    0否，1是 */
    public static final String T_CHK_REV_FLAG_N="0";
    /** 对账明细冲正标志    0否，1是 */
    public static final String T_CHK_REV_FLAG_Y="1";
    
    /** 对账日切开始时间 */
    public static final String CHK_CUT_DAY_START="CHKCUTDAYSTART";
    /** 对账日切结束时间 */
    public static final String CHK_CUT_DAY_END="CHKCUTDAYEND";
    
    /** 资金结算，结算状态初始值（T_STL_BOOK.SATA） */
    public static final String STL_STAT_INIT_VALUE = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第1位 应付本金； */
    public static final int STL_STAT_PAY_AMT_IDX = 0;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第2位 应付手续费； */
    public static final int STL_STAT_PAY_FEE_IDX = 1;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第3位 平台应付手续费； */
    public static final int STL_STAT_PLAT_PAY_FEE_IDX = 2;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第4位 应收本金； */
    public static final int STL_STAT_REV_AMT_IDX = 3;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第5位 应收手续费； */
    public static final int STL_STAT_REV_FEE_IDX = 4;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第6位 平台应收手续费； */
    public static final int STL_STAT_PLAT_REV_FEE_IDX = 5;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第7位 发卡机构应付手续费； */
    public static final int STL_STAT_CARD_PAY_FEE_IDX = 6;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第8位 发卡机构应收手续费； */
    public static final int STL_STAT_CARD_REV_FEE_IDX = 7;
    /** 资金结算，结算状态位标识（T_STL_BOOK.SATA）第9位 汇总扎差； */
    public static final int STL_STAT_BAR_AMT_IDX = 8;

    /** 资金结算，结算状态位取值（T_STL_BOOK.STAT）：X 无任何处理 */
    public static final String STL_STAT_NONE = "X";
    /** 资金结算，结算状态位取值（T_STL_BOOK.STAT）：0 登记 */
    public static final String STL_STAT_CHECK_IN = "0";
    /** 资金结算，结算状态位取值（T_STL_BOOK.STAT）：1 完成入账 */
    public static final String STL_STAT_COMPLETED = "1";
    /** 资金结算，结算状态位取值（T_STL_BOOK.STAT）：2 入账异常 */
    public static final String STL_STAT_EXCEPTION = "2";

    /** 资金结算，结算类型（T_STL_BOOK.MER_FLAG）：01 特约商户 */
    public static final String MER_FLAG_SPECIAL_BUSINESS = "01";
    /** 资金结算，结算类型（T_STL_BOOK.MER_FLAG）：02 核心 */
    public static final String MER_FLAG_CORE = "02";
    /** 资金结算，结算类型（T_STL_BOOK.MER_FLAG）：03 第三方支付公司 */
    public static final String MER_FLAG_THIRD_PARTY = "03";
    /** 资金结算，结算类型（T_STL_BOOK.MER_FLAG）：04 二级商户 */
    public static final String MER_FLAG_SEC_BUSINESS = "04";

    /** 资金结算，结算方标识（T_STL_BOOK.STL_MER_FLAG）：1 平台结算 */
    public static final String STL_MER_FLAG_PLATFORM = "1";
    /** 资金结算，结算方标识（T_STL_BOOK.STL_MER_FLAG）：2 第三方结算 */
    public static final String STL_MER_FLAG_THIRD_PARTY = "2";
    
    /** 批量转账标志（T_PAY_FLOW_DETAIL.TRANS_FLAG）：1 代扣 */
    public static final String BATCH_TRANSFER_FLAG_COLLECTION = "1";
    /** 批量转账标志（T_PAY_FLOW_DETAIL.TRANS_FLAG）：2 代付 */
    public static final String BATCH_TRANSFER_FLAG_PAYMENT = "2";

    //代收
    public static final String COLLECTION="PAY_PRIORITY_COLLECTION";
    //代付
    public static final String PAYMENT="PAY_PRIORITY_PAY";



    public static final String WECHAT_USE_AT_ROUTE = "WECHAT_USE_AT_ROUTE";//走AT微信对账Or 微信对账参数
    public static final String ALIPAY_USE_AT_ROUTE = "ALIPAY_USE_AT_ROUTE";//走AT支付宝对账Or 支付对账参数

    public static final String USE_AT_ROUTE_Y = "Y";//使用AT微信对账参数 or  AT 支付宝对账
    public static final String USE_AT_ROUTE_N = "N";//使用微信对账参数  or  支付宝对账


}
