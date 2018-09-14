/**
 * 
 */
package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年12月12日
 */
public class CheckOrderStatForReceiptDto extends BaseDto {
    
    private String orderNo;// 订单号
    private String payPwdFlag;// 支付密码标识
    private String userName;// 用户姓名
    private String mobile;// 用户手机号
    private String userCertLevel;// 用户认证等级
    private String certNo;// 身份证号
    private String unionPlatNo;// 合作平台账号
    private String merNo;// 商户代码
    private String certName;// 姓名
    private String regType;// 注册类型
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getPayPwdFlag() {
        return payPwdFlag;
    }
    public void setPayPwdFlag(String payPwdFlag) {
        this.payPwdFlag = payPwdFlag;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getUserCertLevel() {
        return userCertLevel;
    }
    public void setUserCertLevel(String userCertLevel) {
        this.userCertLevel = userCertLevel;
    }
    public String getCertNo() {
        return certNo;
    }
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    public String getUnionPlatNo() {
        return unionPlatNo;
    }
    public void setUnionPlatNo(String unionPlatNo) {
        this.unionPlatNo = unionPlatNo;
    }
    public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    public String getCertName() {
        return certName;
    }
    public void setCertName(String certName) {
        this.certName = certName;
    }
    public String getRegType() {
        return regType;
    }
    public void setRegType(String regType) {
        this.regType = regType;
    }
    
    
    
}
