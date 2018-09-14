package com.dubhe.common.constants;

/**
 * Created by Guo on 2016/9/27.
 * 网关配置常量
 */
public class PayGateConstants {

    /**移动端错误跳转页面*/
    public static final String APP_FAIL_REDIRECT_URL = "app.fail.redirect.url";

    /**PC端错误跳转页面*/
    public static final String WEB_FAIL_REDIRECT_URL = "web.fail.redirect.url";

    /**移动端收银台跳转页面*/
    public static final String APP_REDIRECT_URL = "app.redirect.url";
    
    /**跳转协议*/
    public static final String HTTP = "redirect.http";
    /**跳转IP*/
    public static final String REDIRECT_IP = "redirect.ip";
    /**跳转端口*/
    public static final String REDIRECT_PORT = "redirect.port";

    /**PC端收银台跳转页面*/
    public static final String WEB_REDIRECT_URL = "web.redirect.url";

    /** 移动端确认收货跳转页面 */
    public static final String APP_CONFIRM_DELIVERY_URL = "app.confirm.delivery.url";
    /** PC端确认收货跳转页面 */
    public static final String WEB_CONFIRM_DELIVERY_URL = "web.confirm.delivery.url";
    /**支付网关支付接口服务名称*/
    public static final String PAY_SERVICE_NAME = "pay";
    /**快速下单支付接口服务名称*/
    public static final String QUICK_PAY_SERVICE_NAME = "quickPay";

    /**支付网关确认收货接口服务名称*/
    public static final String CONFIRM_DELIVERY_SERVICE_NAME = "confirmDelivery";
    /**新增和更新商户接口服务名称*/
    public static final String ADDORUPDATE_MER_SERVICE_NAME = "createOrUpdateFirstMer";
    

}
