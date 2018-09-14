/**
 * 
 */
package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**新增微信特约商户，校验商户信息
 * @author zhanggr
 *
 */
public class MerOpenWechatPayChkDto extends BaseDto {
    private String merNo;//商户号
    private String merchantName;//商户名称
    private String merchantShortname;//商户简称
    private String servicePhone;//客服电话
    private String contact;//联系人
    private String contactPhone;//联系电话
    private String contactEmail;//联系邮箱
    private String business;//经营类目
    private String merchantRemark;//商户备注
    private String channelId;//微信渠道商号
    
    
    
    
    public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getMerchantShortname() {
        return merchantShortname;
    }
    public void setMerchantShortname(String merchantShortname) {
        this.merchantShortname = merchantShortname;
    }
    public String getServicePhone() {
        return servicePhone;
    }
    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public String getBusiness() {
        return business;
    }
    public void setBusiness(String business) {
        this.business = business;
    }
    public String getMerchantRemark() {
        return merchantRemark;
    }
    public void setMerchantRemark(String merchantRemark) {
        this.merchantRemark = merchantRemark;
    }
}
