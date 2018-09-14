package com.dubhe.common.util;

/**
 * 常量表
 * 
 * @author weizhao.dong
 */
public interface ArmConstants extends GlobalConstants {

    /**
     * 启用状态<br>
     * 1:启用
     */
    public static final String ENABLED_Y = "1";

    /**
     * 启用状态<br>
     * 0:停用
     */
    public static final String ENABLED_N = "0";

    /**
     * 编辑模式<br>
     * 1:可编辑
     */
    public static final String EDITMODE_Y = "1";

    /**
     * 编辑模式<br>
     * 0:只读
     */
    public static final String EDITMODE_N = "0";

    /**
     * 锁定态<br>
     * 1:锁定
     */
    public static final String LOCK_Y = "1";

    /**
     * 锁定状态<br>
     * 0:解锁
     */
    public static final String LOCK_N = "0";

    /**
     * 强制类加载<br>
     * 1:强制
     */
    public static final String FORCELOAD_Y = "1";

    /**
     * 强制类加载<br>
     * 0:不强制
     */
    public static final String FORCELOAD_N = "0";

    /**
     * 树节点类型<br>
     * 1:叶子节点
     */
    public static final String LEAF_Y = "1";

    /**
     * 树节点类型<br>
     * 0:树枝节点
     */
    public static final String LEAF_N = "0";
    /**
     * 业务菜单 0：业务菜单
     */
    public static final String MENUTYPE_BUSINESS = "0";
    /**
     * 系统菜单 1：系统菜单
     */
    public static final String MENUTYPE_ADMIN = "1";
    /**
     * 角色类型<br>
     * 1:业务角色
     */
    public static final String ROLETYPE_BUSINESS = "1";

    /**
     * 角色类型<br>
     * 2:管理角色
     */
    public static final String ROLETYPE_ADMIN = "2";

    /**
     * 角色类型<br>
     * 3:系统内置角色
     */
    public static final String ROLETYPE_EMBED = "3";

    /**
     * 角色审核标志<br>
     * 1是
     */
    public static final String ROLEAUDIT_FLAG = "1";
    /**
     * 权限级别<br>
     * 1:访问权限
     */
    public static final String AUTHORIZELEVEL_ACCESS = "1";

    /**
     * 权限级别<br>
     * 2:管理权限
     */
    public static final String AUTHORIZELEVEL_ADMIN = "2";

    /**
     * 用户类型<br>
     * 1:经办员
     */
    public static final String USERTYPE_BUSINESS = "1";

    /**
     * 用户类型<br>
     * 2:管理员
     */
    public static final String USERTYPE_ADMIN = "2";

    /**
     * 用户类型<br>
     * 3:系统内置用户
     */
    public static final String USERTYPE_EMBED = "3";

    /**
     * 用户标示<br>
     * 1 内部用户
     */
    public static final String USERFLAG_INSIDE = "1";

    /**
     * 用户标示<br>
     * 2 企业用户
     */
    public static final String USERFLAG_COMPANY = "2";
    /**
     * 用户标示<br>
     * 3 银行用户
     */
    public static final String USERFLAG_BANK = "3";
    
    
    /**
     * 根节点ID<br>
     * 01:菜单树
     */
    public static final String ROORID_MENU = "01";

    /**
     * 帐户类型<br>
     * 1:常规帐户
     */
    public static final String ACCOUNTTYPE_NORMAL = "1";

    /**
     * 帐户类型<br>
     * 2:SUPER帐户
     */
    public static final String ACCOUNTTYPE_SUPER = "2";

    /**
     * 帐户类型<br>
     * 3:DEVELOPER帐户
     */
    public static final String ACCOUNTTYPE_DEVELOPER = "3";

    /**
     * 子公司标示<br>
     * 1：子公司
     */
    public static final String CHILD_COMPANY_FLAG = "1";

    /**
     * super帐户<br>
     * 1:super帐户
     */
    public static final String ACCOUNT_SUPER = "sys";

    /**
     * developer帐户<br>
     * 2:developer帐户
     */
    public static final String ACCOUNT_DEVELOPER = "developer";

    /**
     * 操作员事件跟踪监控开关[1:打开;0:关闭]<br>
     * 1:打开
     */
    public static final String EVENTMONITOR_ENABLE_Y = "1";

    /**
     * 操作员事件跟踪监控开关[1:打开;0:关闭]<br>
     * 0:关闭
     */
    public static final String EVENTMONITOR_ENABLE_N = "0";

    /**
     * 切入点类型[1:BPO切入;2:DAO切入]<br>
     * 1:BPO切入
     */
    public static final String POINTCUTTYPE_BPO = "1";

    /**
     * 切入点类型[1:BPO切入;2:DAO切入]<br>
     * 2:DAO切入
     */
    public static final String POINTCUTTYPE_DAO = "2";

    /**
     * 通知类型[1:方法调用通知;2:异常捕获通知]<br>
     * 1:方法调用通知
     */
    public static final String ADVISETYPE_CALL = "1";

    /**
     * 通知类型[1:方法调用通知;2:异常捕获通知]<br>
     * 2:异常捕获通知
     */
    public static final String ADVISETYPE_CATCH = "2";

    /**
     * 用户登录密码锁定FLG
     * 
     */
    public static final String PWD_LOCK_TYPE_01 = "1";
    /**
     * 用户支付密码锁定FLG
     * 
     */
    public static final String PWD_LOCK_TYPE_02 = "2";

    /**
     * 日期格式 yyyy.MM.dd
     */
    public static final String DATE_TYPE = "yyyy.MM.dd";

    /**
     * 频繁交易标志<br>
     * 0:非频繁交易
     */
    public static final String FREQUENT_FLAG_OFF = "0";

    /**
     * 频繁交易标志 <br>
     * 1：频繁交易
     */
    public static final String FREQUENT_FLAG_ON = "1";

    /**
     * 状态 <br>
     * 1：正常
     */
    public static final String STAT_NORMAL = "1";

    /**
     * 状态 <br>
     * 2：全部
     */
    public static final String STAT_ALL = "2";

    /**
     * 参数类型<br>
     * 00：技术参数
     */
    public static final String PARM_FLAG_SYS = "00";

    /**
     * 参数类型<br>
     * 01：业务参数
     */
    public static final String PARM_FLAG_BUS = "01";

    /**
     * 分页参数<br>
     * total：数据条数
     */
    public static final String PAGE_TOTAL = "total";

    /**
     * 分页参数<br>
     * rows：数据列表
     */
    public static final String PAGE_ROWS = "rows";
	
	/**
	 * 审核通过
	 */
	public static final String AUDIT_PASS = "1";
	
	/**
	 * 审核拒绝
	 */
	public static final String AUDIT_REFUSE = "0";
	/**
	 * 删除标志 不删除 0
	 */
	public static final String DELETE_FLAG_FALSE="0";
    /**
     * 删除标志 已删除1
     */
	public static final String DELETE_FLAG_TRUE="1";
	 /**
     * 系统类型  运维支撑
     */
    public static final String SYS_TYPE_INSIDE="1";
    /**
     * 内部角色 1
     */
    public static final String ROLE_FLAG_INSIDE="1";
    /**
     * 企业角色2
     */
    public static final String ROLE_FLAG_COMPANY="2";
    /**
     * 银行角色3
     */
    public static final String ROLE_FLAG_BANK="3";
    
    /**
     * 系统类型  门户
     */
    public static final String SYS_TYPE_PORTAL="2";    
    /**
     * 内部操作   1
     * 
     */
    public static final String OPER_FLAG_INSIDE="1";
    
    /**
     * 公司操作  2
     * 
     */
    public static final String OPER_FLAG_COMPANY="2";
    /**
     * 银行操作 3
     * 
     */
    public static final String OPER_FLAG_BANK="3";
    /**
     * 是否是银联 1是
     */
    public static final String UNIPAY_FLAG_TRUE="1";
    
    /**
     * 是否是银联 0否
     */
    public static final String UNIPAY_FLAG_FALSE="0";
    
    
    
    /**
     * 是否是虚拟 0否
     */
    public static final String VIRTUAL_FLAG_FALSE="0";
    
    /**
     * 是否是虚拟 1是
     */
    public static final String VIRTUAL_FLAG_TRUE="1";
    
    /**
     * 是否是第一次登录 1是
     */
    public static final String IS_FIRST_LOG_TRUE="1";
    
    /**
     * 是否是第一次登录 1否
     */
    public static final String IS_FIRST_LOG_FALSE="0";
    
    /**
     * 操作类型日志
     */
    public static final String LOG_TYPE_OPER="1";

    /**
     * 访问类型日志
     */
    public static final String LOG_TYPE_LOGIN="0";
}
