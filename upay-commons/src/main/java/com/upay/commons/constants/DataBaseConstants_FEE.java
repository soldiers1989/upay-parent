package com.upay.commons.constants;

/**
 * FEE常用静态变量
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
public interface DataBaseConstants_FEE {
	/** 免收周期,0 不免 */
    public static final String FEE_FREE_CYCLE_NO = "0";
    /** 免收周期,1 日 */
    public static final String FEE_FREE_CYCLE_DAY = "1";
    /** 免收周期,2 月 */
    public static final String FEE_FREE_CYCLE_MONTH = "2";
    /** 免收周期,3 年 */
    public static final String FEE_FREE_CYCLE_YEAR = "3";
    /** 免收周期,9 全免 */
    public static final String FEE_FREE_CYCLE_ALL = "9";
    
    /** 手续费内扣 */
    public static final String FEE_GET_TYPE_INNER = "0";
    /** 手续费外扣 */
    public static final String FEE_GET_TYPE_OUTTER = "1";

    /** 手续费收取方式,1 单笔固定 */
    public static final String FEE_MODE_ONE = "1";
    /** 手续费收取方式,2 单笔固定+按交易金额比例 */
    public static final String FEE_MODE_TWO = "2";
    /** 手续费收取方式,3 按交易金额比例收取 */
    public static final String FEE_MODE_THREE = "3";
    /** 手续费收取方式,4 分段收取 */
    public static final String FEE_MODE_FOUR = "4";
    
    
    
    /** 是否进行账户限额   Y 是     N 不是 */
    public static final String CHECK_USER_LMT_Y="Y";
    public static final String CHECK_USER_LMT_N="N";
    
    /** 资金通道手续费标志  1:  资金通道手续费 */
    public static final String ROUTE_FEE_YES = "1";
}
