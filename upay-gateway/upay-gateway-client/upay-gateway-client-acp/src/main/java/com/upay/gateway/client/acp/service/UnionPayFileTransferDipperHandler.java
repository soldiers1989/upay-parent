/**
 *
 */
package com.upay.gateway.client.acp.service;

import com.pactera.dipper.core.bean.Fault;

import java.util.Date;
import java.util.Map;


/**
 * 文件传输接口（对账文件下载）
 *
 * @author liudan
 */
public class UnionPayFileTransferDipperHandler extends AbstractAcpClientDipperHandler {
    /**接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）*/
    private String accessType;
    /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    /**字符集编码 可以使用UTF-8,GBK两种方式*/
    /**填写000000*/
    private String bizType;
    /**交易类型 01:消费*/
    private String txnType;
    /**交易子类 07：申请消费二维码*/
    private String txnSubType;
    /**交易码*/
    private String tranCode;

    private String settleDate;



    // 依据实际业务情况定义参考附录：商户索取的文件类型约定
    private String fileType;



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
        // 取值:76
        init.put("tranCode", tranCode);// M
        // 01：对账文件下载
        init.put("txnSubType", txnSubType);// M
        // 默认:000000
        init.put("bizType", bizType);// M
        // 0：普通商户直连接入1. 收单机构接入
        init.put("accessType", accessType);// M
        init.put("settleDate", settleDate);// M
        // 依据实际业务情况定义参考附录：商户索取的文件类型约定
        init.put("fileType",  fileType);// M
        init.put("txnTime", new Date());// M

    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        target.putAll(source);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {

    }


    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
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

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


}
