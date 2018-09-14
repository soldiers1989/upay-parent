package com.upay.commons.constants;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午8:43:54
 */
public interface CommonBaseConstants_USR {
    /**
     * 日期格式化
     */
    public static final String DATE_EXPRESSION_YMD="yyyyMMdd";
    public static final String DATE_EXPRESSION_YMDHM="yyyyMMddHHmm";
    public static final String DATE_EXPRESSION_YMDHMS="yyyyMMddHHmmss";
    public static final String DATE_EXPRESSION_YMDHMSS="yyyyMMddHHmmssSSS";
    public static final String DATE_EXPRESSION_Y_M_D_H_M_S="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_EXPRESSION_M_D="MMdd";

//    public static final SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
//    public static final SimpleDateFormat SIM_YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
//    public static final SimpleDateFormat SIM_YMDHMSS=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    public static final SimpleDateFormat SIM_HMS=new SimpleDateFormat("HHmmss");

    /**
     * 
     */
    
    public static final String MOBILE_CHECK_REGIT="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
}
