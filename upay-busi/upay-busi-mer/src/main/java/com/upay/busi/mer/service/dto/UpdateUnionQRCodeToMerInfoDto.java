/**
 * 
 */
package com.upay.busi.mer.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;

/**商户申请线下主扫银联标码，更新商户线下二维码
 * @author hry
 *
 */
public class UpdateUnionQRCodeToMerInfoDto extends BaseDto {
    private String merNo;//商户号
    private String qrCode;//商户二维码
    private String limitCount;//二维码支付次数
    private String orderTime;//订单时间
    private String qrValidTime;//二维码有效时间
    private String orderNo;//订单号
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getQrValidTime() {
		return qrValidTime;
	}
	public void setQrValidTime(String qrValidTime) {
		this.qrValidTime = qrValidTime;
	}
    
}
