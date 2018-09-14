/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 资金通道信息查询
 * 
 * @author zhanggr
 * 
 */
public class PayRouteInfoQryDto extends BaseDto {
    private String routeCode;// 资金通道代码
    private String appId;// 微信公众号ID
    private String orgNo;// 机构号、商户号
    private String transAcctNo;// 往来帐户
    private String clrAcctNo;// 清算账户
    private String feeCode;// 费率代码
    private String assCode;// 分润代码
    private String terminalId;// 清算方式
    private String publicKeyPath;// 公钥文件路径
    private String certFilePath;// 私钥证书路径
    private String callbackRul;// 异步回调接收服务地址
    private String serviceVersion;// 版本号
    private String appSecret;
    private String remark1;//微信支付方式限制
    private String clrType;// 清算方式
 
    private String isAt;

    public String getIsAt() {
		return isAt;
	}


	public void setIsAt(String isAt) {
		this.isAt = isAt;
	}


	public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public String getAppId() {
        return appId;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getOrgNo() {
        return orgNo;
    }


    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }


    public String getTransAcctNo() {
        return transAcctNo;
    }


    public void setTransAcctNo(String transAcctNo) {
        this.transAcctNo = transAcctNo;
    }


    public String getClrAcctNo() {
        return clrAcctNo;
    }


    public void setClrAcctNo(String clrAcctNo) {
        this.clrAcctNo = clrAcctNo;
    }


    public String getFeeCode() {
        return feeCode;
    }


    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }


    public String getAssCode() {
        return assCode;
    }


    public void setAssCode(String assCode) {
        this.assCode = assCode;
    }


    public String getTerminalId() {
        return terminalId;
    }


    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }


    public String getPublicKeyPath() {
        return publicKeyPath;
    }


    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }


    public String getCertFilePath() {
        return certFilePath;
    }


    public void setCertFilePath(String certFilePath) {
        this.certFilePath = certFilePath;
    }


    public String getCallbackRul() {
        return callbackRul;
    }


    public void setCallbackRul(String callbackRul) {
        this.callbackRul = callbackRul;
    }


    public String getServiceVersion() {
        return serviceVersion;
    }


    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }


    public String getAppSecret() {
        return appSecret;
    }


    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }


    public String getRemark1() {
        return remark1;
    }


    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }


    public String getClrType() {
        return clrType;
    }


    public void setClrType(String clrType) {
        this.clrType = clrType;
    }

}
