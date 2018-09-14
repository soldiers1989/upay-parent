package com.upay.busi.gnr.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;



/**
 * 接口服务后处理数据传输结构
 * 
 * @author freeplato
 * 
 */
public class SrvPostProcessingDto extends BaseDto{

    /**
     * 短信发送标志
     */
    private String smsSendFlag;

//    /**
//     * 短信发送数据
//     */
//    private Map<String, List<String>> smsSendMsgs;

    /**
     * 消息发送标志
     */
    private String noticeSendFlag;
    
    /**
     * 消息发送数据
     */
    private Map<String, List<String>> noticeSendMsgs;
    
	public String getSmsSendFlag() {
		return smsSendFlag;
	}
	
	public void setSmsSendFlag(String smsSendFlag) {
		this.smsSendFlag = smsSendFlag;
	}
	
//	public Map<String, List<String>> getSmsSendMsgs() {
//		return smsSendMsgs;
//	}
//	
//	public void setSmsSendMsgs(Map<String, List<String>> smsSendMsgs) {
//		this.smsSendMsgs = smsSendMsgs;
//	}
	
	public String getNoticeSendFlag() {
		return noticeSendFlag;
	}
	
	public void setNoticeSendFlag(String noticeSendFlag) {
		this.noticeSendFlag = noticeSendFlag;
	}
	
	public Map<String, List<String>> getNoticeSendMsgs() {
		return noticeSendMsgs;
	}
	
	public void setNoticeSendMsgs(Map<String, List<String>> noticeSendMsgs) {
		this.noticeSendMsgs = noticeSendMsgs;
	}

}
