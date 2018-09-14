package com.upay.gateway.client.acpreceive.service;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;

import java.util.Map;

/**
 * 银联代收
 * 
 * @author SunOlny
 * 
 */
public class DefaultAcpClientDipperHandler implements
		IDipperHandler<Message> {


	/** 渠道类型 */
	private String channelType;

	/** 接入方式 */
	private String accessType;

	/** 收单机构代码 */
	private String acqInsCode;

	/** 商户类别 */
	private String merCatCode;

	/** 商户名称 */
	private String merName;

	/** 商户简称 */
	private String merAbbr;

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

	public String getAcqInsCode() {
		return acqInsCode;
	}

	public void setAcqInsCode(String acqInsCode) {
		this.acqInsCode = acqInsCode;
	}

	public String getMerCatCode() {
		return merCatCode;
	}

	public void setMerCatCode(String merCatCode) {
		this.merCatCode = merCatCode;
	}


	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerAbbr() {
		return merAbbr;
	}

	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
	}


	@Override
	public Message handle(Message message) throws Exception {

		Map<String, Object> init = (Map<String, Object>) message.getTarget().getBodys();
		// 渠道类型
		init.put("channelType", channelType);
		// 0：普通商户直连接入1：收单机构接入2：平台类商户接入
		init.put("accessType", accessType);
		// 收单机构代码
		init.put("acqInsCode", acqInsCode);
		// 接入类型为收单机构接入时需上送
		init.put("merCatCode", merCatCode);
		// 接入类型为收单机构接入时需上送
		init.put("merName", merName);
		// 接入类型为收单机构接入时需上送
		init.put("merAbbr", merAbbr);

		return message;

	}
}
