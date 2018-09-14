package com.upay.commons.constants;

/**
 * @author yanzixiong
 * @version 创建时间：2016年8月15日10:34:04
 */
public class DateBaseConstants_MER {
	/**交易权限模版默认**/
	public static final String MER_TRANS_CTRL_TEMPLATE_DEFAULT = "1";
	/**商户交易权限启用**/
	public static final String MER_TRANS_TEMPLATE_OPEN = "0";
	
	/** 商户默认加密串   */
    public static final String MER_DEFAULT_PRIVATE_3DSKEY="MER_DEFAULT_PRIVATE_3DSKEY";
	/** 商户默认密码   */
    public static final String MER_DEFAULT_PASSWORD="MER_DEFAULT_PASSWORD";
	/** 0-是一级商户*/
    public static final String IS_PARENT_MER_YES = "0";
    /** 1-不是一级商户 */
    public static final String IS_PARENT_MER_NO = "1";
    /** 申请状态,0 待审核 */
    public static final String MER_APPLY_STAT_WAIT = "0";
    /** 申请状态,1 审核中 */
    public static final String MER_APPLY_STAT_REVIEW = "1";
    /** 申请状态,2 审核通过 */
    public static final String MER_APPLY_STAT_YES = "2";
    /** 申请状态,3 审核未通过 */
    public static final String MER_APPLY_STAT_NO = "3";

    /** 商户状态,0 正常 */
    public static final String MER_STAT_NORMAL = "0";
    /** 商户状态,1 停用 */
    public static final String MER_STAT_STOP = "1";

    /** 支付功能开通标志,0：开通 */
    public static final String MER_PAYOPENFLAG_OPEN = "0";
    /** 支付功能开通标志,1：不开通 */
    public static final String MER_PAYOPENFLAG_CLOSE = "1";

    /** 对账状态: 0 未对账 */
    public static final String CHK_FLAG_NOT = "0";
    /** 对账状态: 1 对账成功 */
    public static final String CHK_FLAG_SUCCESS = "1";
    /** 对账状态: 2 对账不平 */
    public static final String CHK_FLAG_NOT_BALANCED = "2";

    /** 工作流状态：10 草稿 */
    public static final String MER_WF_STAT = "10";
    /** 工作流状态：12 退回 */
    public static final String MER_WF_BACK = "12";

    /** 查询二级商户状态,审核中 */
    public static final String MER_QRY_STAT_REVIEW = "0";
    /** 查询二级商户状态,审核通过 */
    public static final String MER_QRY_STAT_PASS = "1";
    /** 查询二级商户状态,审核失败 */
    public static final String MER_QRY_STAT_FAIL = "2";

    /** 商户申请检查,注册 */
    public static final String MER_APPLY_CHK_REG = "0";
    /** 商户申请检查,实名认证 */
    public static final String MER_APPLY_CHK_CARD = "1";
    /** 商户申请检查,商户申请 */
    public static final String MER_APPLY_CHK_APPLY = "2";
    /** 商户申请检查,商户审核通过 */
    public static final String MER_APPLY_CHK_PASS = "3";
    /** 商户申请检查,商户审核拒绝 */
    public static final String MER_APPLY_CHK_REFUSE = "4";

    /** 商户级别00：新注册商户 */
    public static final String MER_LEVEL_REG = "00";
    /** 商户级别01：一级商户 */
    public static final String MER_LEVEL_FIRST_MER = "01";
    /** 商户级别02：二级商户 */
    public static final String MER_LEVEL_SECOND_MER = "02";

    /** 操作方式 01：查询 */
    public static final String MER_OPERATION_QUERY = "01";
    /** 操作方式 01：新增 */
    public static final String MER_OPERATION_ADD = "01";
    /** 操作方式 02：删除 */
    public static final String MER_OPERATION_DELETE = "02";
    /** 操作方式 03：修改 */
    public static final String MER_OPERATION_UPDATE = "03";

    /** 生效状态 1.即将生效 */
    public static final String DATE_STATE_1 = "1";
    /** 生效状态 2.生效中 */
    public static final String DATE_STATE_2 = "2";
    /** 生效状态 3.已经失效 */
    public static final String DATE_STATE_3 = "3";

    /** 商户交易限额控制状态 0启用，1关闭 */
    public static final String MER_TRANS_LIMIT_STAT_OPEN = "0";
    public static final String MER_TRANS_LIMIT_STAT_CLOSE = "1";

    /** 增删改查 0：增加 1：删除 2：改 3：查 */
    public static final String CONTROL_MERPLAT_SETTING_FLAG_ZERO = "0";
    /** 增删改查 0：增加 1：删除 2：改 3：查 */
    public static final String CONTROL_MERPLAT_SETTING_FLAG_ONE = "1";
    /** 增删改查 0：增加 1：删除 2：改 3：查 */
    public static final String CONTROL_MERPLAT_SETTING_FLAG_TWO = "2";
    /** 增删改查 0：增加 1：删除 2：改 3：查 */
    public static final String CONTROL_MERPLAT_SETTING_FLAG_THREE = "3";

    /** 商户类型 01特约商户 */
    public static final String MER_TYPE_SPECIAL_BUSINESS = "01";
    /** 商户类型 02发卡机构 */
    public static final String MER_TYPE_CARD_ISSUER = "02";
    /** 商户类型 03合作公司 */
    public static final String MER_TYPE_PARTNER_COMPANY = "03";
    
    
    /** 商户业务类型   01:电商平台  */
    public static final String MER_BUSI_TYPE_E_COMMERCE_PLATFORM = "01";
    /** 商户业务类型   02:线下门店 */
    public static final String MER_BUSI_TYPE_OFFLINE_SHOP = "02";
    /** 商户业务类型   03:金融机构 */
    public static final String MER_BUSI_TYPE_FINANCIAL_INSTITUTIONS = "03";
    /** 商户业务类型   04:直销银行 */
    public static final String MER_BUSI_TYPE_DIRECT_BANK = "04";
    /** 商户业务类型   05:资金存管/P2P平台 */
    public static final String MER_BUSI_TYPE_P2P = "05";
    
    /** 商户二维码支付使用渠道   直接*/
    public static final String QRCODE_USR_WAY_DIRECT = "DIRECT";
    
    /** 商户二维码支付使用渠道   银联*/
    public static final String QRCODE_USR_WAY_UNIONPAY = "UNIONPAY";
    
    /** 商户二维码支付使用渠道   网联*/
    public static final String QRCODE_USR_WAY_NETUNION = "NETUNION";
}
