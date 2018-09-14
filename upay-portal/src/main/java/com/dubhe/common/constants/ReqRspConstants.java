package com.dubhe.common.constants;

/**
 * 中台接受与响应常量设置
 */
public interface ReqRspConstants {

	public static final String RSP_CODE_SUCCESS = "0000000000";
	
	public static final String RSP_CODE_SUCCESS_DESC = "交易成功";

	public static final String RSP_CODE_FAIL = "NEPSYS9999";
	
	public static final String RSP_CODE_FAIL_DESC = "系统繁忙，请您稍后再试";
	
	public static final String RSP_CODE_ILLEGALMSG = "NEPSYS0000";
	
	public static final String RSP_CODE_ILLEGALMSG_DESC = "非法报文";
	
	public static final String RSP_CODE_MSGBODYNULL = "NEPSYS9998";
	
	public static final String RSP_CODE_MSGBODYNULL_DESC = "报文体为空";
	
	public static final String RSP_CODE_FIELDNULL = "NEPSYS0001";
	
	public static final String SYS_DATE = "SYS_DATE";

	public static final String SYS_TIME = "SYS_TIME";

	public static final String RSP_KEY = "rsp_code";
	
	public static final String RSP_MSG = "rsp_msg";

}
