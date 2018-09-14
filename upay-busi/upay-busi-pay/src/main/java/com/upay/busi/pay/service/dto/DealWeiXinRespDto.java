package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class DealWeiXinRespDto extends BaseDto{
    private String returnCode;
    private String returnMsg;
    private String resultCode;
    private String transSubSeq;
    private String sendListid;
    
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getTransSubSeq() {
		return transSubSeq;
	}
	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}
	public String getSendListid() {
		return sendListid;
	}
	public void setSendListid(String sendListid) {
		this.sendListid = sendListid;
	}
   

}
