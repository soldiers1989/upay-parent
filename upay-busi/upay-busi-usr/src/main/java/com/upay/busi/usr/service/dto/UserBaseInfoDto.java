package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年7月20日 下午4:42:18
 */
public class UserBaseInfoDto extends BaseDto {
//    private String userId;//用户id
    private String userName;//用户名
    
    private String platformType;//合作平台类型
    private String platformUserNo;//合作平台账号
    private String certNo;//证件号码
    private String certType;//证件类型
    private String mobile;//手机号
    
    
    
    private String certName;//真实姓名
    private String certBegin;//证件有效开始时间
    private String certEnd;//证件有效结束时间
    private String certFlag;//证件有效期类型
    private String sex;//性别
    private String birthday;//生日
    private String country;//国家
    private String nation;//民族
    private String politically;//政治面貌
    private String faith;//宗教信仰
    private String marriage;//婚姻状况
    private String education;//教育程度
    private String position;//职位
    private String addrCode;//地区代码
    private String addrReal;//真实地址
    private String email;//邮箱
    private String qq;//qq
    private String weixin;//微信
    private String sign;//个性签名
    private String reserveInfo;//预留信息
    private String ECIF;//ECIF客户号
    private String lastUpdate;//最后更新时间
    
    
    
//    public String getUserId() {
//        return userId;
//    }
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
    public String getUserName() {
        return userName;
    }
    public String getCertName() {
        return certName;
    }
    public String getCertBegin() {
        return certBegin;
    }
    public String getCertEnd() {
        return certEnd;
    }
    public String getCertFlag() {
        return certFlag;
    }
    public String getSex() {
        return sex;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getCountry() {
        return country;
    }
    public String getNation() {
        return nation;
    }
    public String getPolitically() {
        return politically;
    }
    public String getFaith() {
        return faith;
    }
    public String getMarriage() {
        return marriage;
    }
    public String getEducation() {
        return education;
    }
    public String getPosition() {
        return position;
    }
    public String getAddrCode() {
        return addrCode;
    }
    public String getAddrReal() {
        return addrReal;
    }
    public String getEmail() {
        return email;
    }
    public String getQq() {
        return qq;
    }
    public String getWeixin() {
        return weixin;
    }
    public String getSign() {
        return sign;
    }
    public String getReserveInfo() {
        return reserveInfo;
    }
    public String getECIF() {
        return ECIF;
    }
    public String getLastUpdate() {
        return lastUpdate;
    }
    public void setCertName(String certName) {
        this.certName = certName;
    }
    public void setCertBegin(String certBegin) {
        this.certBegin = certBegin;
    }
    public void setCertEnd(String certEnd) {
        this.certEnd = certEnd;
    }
    public void setCertFlag(String certFlag) {
        this.certFlag = certFlag;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public void setPolitically(String politically) {
        this.politically = politically;
    }
    public void setFaith(String faith) {
        this.faith = faith;
    }
    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
    }
    public void setAddrReal(String addrReal) {
        this.addrReal = addrReal;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public void setReserveInfo(String reserveInfo) {
        this.reserveInfo = reserveInfo;
    }
    public void setECIF(String eCIF) {
        ECIF = eCIF;
    }
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPlatformType() {
        return platformType;
    }
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }
    public String getPlatformUserNo() {
        return platformUserNo;
    }
    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }
    public String getCertNo() {
        return certNo;
    }
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    public String getCertType() {
        return certType;
    }
    public void setCertType(String certType) {
        this.certType = certType;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Override
    public String toString() {
        return "UserBaseInfoDto [userName=" + userName + ", platformType=" + platformType
                + ", platformUserNo=" + platformUserNo + ", certNo=" + certNo + ", certType=" + certType
                + ", mobile=" + mobile + ", certName=" + certName + ", certBegin=" + certBegin + ", certEnd="
                + certEnd + ", certFlag=" + certFlag + ", sex=" + sex + ", birthday=" + birthday
                + ", country=" + country + ", nation=" + nation + ", politically=" + politically + ", faith="
                + faith + ", marriage=" + marriage + ", education=" + education + ", position=" + position
                + ", addrCode=" + addrCode + ", addrReal=" + addrReal + ", email=" + email + ", qq=" + qq
                + ", weixin=" + weixin + ", sign=" + sign + ", reserveInfo=" + reserveInfo + ", ECIF=" + ECIF
                + ", lastUpdate=" + lastUpdate + "]";
    }
    
    
    
    
    
}
