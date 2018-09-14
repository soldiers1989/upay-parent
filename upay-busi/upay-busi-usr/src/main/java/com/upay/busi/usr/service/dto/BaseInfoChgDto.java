package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 个人信息修改
 * 
 * @author liyulong
 * 
 */
public class BaseInfoChgDto extends BaseDto {

    /** 用户头像图片地址 */
    private String headPic;

    /** 邮箱 */
    private String email;

    /** qq */
    private String qq;

    /** 微信 */
    private String weixin;

    /** 国籍 */
    private String country;

    /** 民族 */
    private String nation;

    /** 政治面貌 */
    private String backGround;

    /** 宗教信仰 */
    private String religion;

    /** 婚姻状况 */
    private String marriage;

    /** 教育程度 */
    private String eduBg;

    /** 职业 */
    private String job;

    /** 省市区地址代码 */
    private String addressCode;

    /** 实际地址 */
    private String addressReal;


    public String getHeadPic() {
        return headPic;
    }


    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getQq() {
        return qq;
    }


    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getWeixin() {
        return weixin;
    }


    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getNation() {
        return nation;
    }


    public void setNation(String nation) {
        this.nation = nation;
    }


    public String getBackGround() {
        return backGround;
    }


    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }


    public String getReligion() {
        return religion;
    }


    public void setReligion(String religion) {
        this.religion = religion;
    }


    public String getMarriage() {
        return marriage;
    }


    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }


    public String getEduBg() {
        return eduBg;
    }


    public void setEduBg(String eduBg) {
        this.eduBg = eduBg;
    }


    public String getJob() {
        return job;
    }


    public void setJob(String job) {
        this.job = job;
    }


    public String getAddressCode() {
        return addressCode;
    }


    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }


    public String getAddressReal() {
        return addressReal;
    }


    public void setAddressReal(String addressReal) {
        this.addressReal = addressReal;
    }

}
