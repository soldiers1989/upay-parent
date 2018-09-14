/**
 *
 */
package com.upay.gateway.client.acp.service;

import com.pactera.dipper.core.bean.Fault;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 银联之营销活动余额查询
 * 目前Apple Pay做营销时，为避免造成用户支付时页面上显示有优惠，
 * 实际上支付没有享受到优惠。 通过此接口可以知道活动剩余名额，当该”营销活动”还有优惠时，商户APP需要自动的展示当面优惠活动，
 * 从而引导用户使用Apple Pay进行远程支付。建议查询间隔时间至少1分钟。
 *
 * @author liudan
 */
public class UnionPayMarketingActivityBalanceInquiryDipperHandler extends AbstractAcpClientDipperHandler {
    /**
     * 商户接入参数
     */
    /**商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试*/
    private String merId;
    /**交易金额 单位为分，不能带小数点*/
    private String txnAmt;
    /**商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则*/
    private String orderId;
    /**订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效*/
    private String txnTime;
    /**接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）*/
    private String accessType;
    /**境内商户固定 156 人民币*/
    private String currencyCode;

    /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    /**字符集编码 可以使用UTF-8,GBK两种方式*/
    private String encoding;
    /**签名方法*/
    private String signMethod;
    /**填写000000*/
    private String bizType;
    /**渠道类型 08手机*/
    private String channelType;
    /**版本号 全渠道默认值*/
    private String version;
    /**交易类型 01:消费*/
    private String txnType;
    /**交易子类 07：申请消费二维码*/
    private String txnSubType;
    /**交易码*/
    private String tranCode;
    /**回调地址*/
    private String backUrl;


    // 超时不抛异常
    @Override
    public boolean isErrorThrow() {
        return false;
    }


    // 失败抛异常
    @Override
    public boolean isFailureThrow() {
        return true;
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {

    }


    @Override
    protected void setInitParam(Map<String, Object> init) {
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //填写000000
        init.put("bizType", bizType);
        //交易时间
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        init.put("txnTime", txnTime);
        //交易类型 99-冲正
        init.put("txnType", txnType);
        //交易子类型 	 01
        init.put("txnSubType", txnSubType);
        /***商户接入参数***/
        //接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
        init.put("accessType", accessType);
        //交易码
        init.put("tranCode", tranCode);
        //商户号
        init.put("merId", merId);
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        target.putAll(source);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {

    }


    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getBackUrl() {
        return backUrl;
    }

    @Override
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
}
