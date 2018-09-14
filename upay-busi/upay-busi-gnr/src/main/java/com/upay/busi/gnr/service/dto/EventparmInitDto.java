package com.upay.busi.gnr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 事件参数初始化
 * @author freeplato
 *
 */
public class EventparmInitDto extends BaseDto {

	/**
	 * 发送本人短信对象
	 * 格式为本人的手机号的字段
	 */
	private String sendSmsObjMyself;
	
	/**
	 * 发送关系人短信对象
	 * 格式为关系人的手机号，可以为多组，以“|”分割
	 */
	private String sendSmsObjRalationShip;
	
	/**
	 * 发送本人消息对象
	 * 格式为本人的用户号的字段
	 */
	private String sendNoticeObjMyself;
	
	/**
	 * 发送关系人消息对象
	 * 格式为关系人的用户号，可以为多组，以“|”分割
	 */
	private String sendNoticeObjRalationShip;
	
	/**
	 * 发送本人短信对象
	 * 当交易发送的短信需要进行选择发送时，需要上送指定的短信序号
	 */
	private String chooseSmsSeqMyself;
	
	/**
	 * 发送关系人短信对象
	 * 当交易发送的短信需要进行选择发送时，需要上送指定的短信序号
	 */
	private String chooseSmsSeqRalationShip;
	
	/**
	 * 发送本人消息对象
	 * 当交易发送的消息需要进行选择发送时，需要上送指定的消息序号
	 */
	private String chooseNoticeSeqMyself;
	
	/**
	 * 发送关系人消息对象
	 * 当交易发送的消息需要进行选择发送时，需要上送指定的消息序号
	 */
	private String chooseNoticeSeqRalationShip;

	public String getSendSmsObjMyself() {
		return sendSmsObjMyself;
	}

	public void setSendSmsObjMyself(String sendSmsObjMyself) {
		this.sendSmsObjMyself = sendSmsObjMyself;
	}

	public String getSendSmsObjRalationShip() {
		return sendSmsObjRalationShip;
	}

	public void setSendSmsObjRalationShip(String sendSmsObjRalationShip) {
		this.sendSmsObjRalationShip = sendSmsObjRalationShip;
	}

	public String getSendNoticeObjMyself() {
		return sendNoticeObjMyself;
	}

	public void setSendNoticeObjMyself(String sendNoticeObjMyself) {
		this.sendNoticeObjMyself = sendNoticeObjMyself;
	}

	public String getSendNoticeObjRalationShip() {
		return sendNoticeObjRalationShip;
	}

	public void setSendNoticeObjRalationShip(String sendNoticeObjRalationShip) {
		this.sendNoticeObjRalationShip = sendNoticeObjRalationShip;
	}

	public String getChooseSmsSeqMyself() {
		return chooseSmsSeqMyself;
	}

	public void setChooseSmsSeqMyself(String chooseSmsSeqMyself) {
		this.chooseSmsSeqMyself = chooseSmsSeqMyself;
	}

	public String getChooseSmsSeqRalationShip() {
		return chooseSmsSeqRalationShip;
	}

	public void setChooseSmsSeqRalationShip(String chooseSmsSeqRalationShip) {
		this.chooseSmsSeqRalationShip = chooseSmsSeqRalationShip;
	}

	public String getChooseNoticeSeqMyself() {
		return chooseNoticeSeqMyself;
	}

	public void setChooseNoticeSeqMyself(String chooseNoticeSeqMyself) {
		this.chooseNoticeSeqMyself = chooseNoticeSeqMyself;
	}

	public String getChooseNoticeSeqRalationShip() {
		return chooseNoticeSeqRalationShip;
	}

	public void setChooseNoticeSeqRalationShip(String chooseNoticeSeqRalationShip) {
		this.chooseNoticeSeqRalationShip = chooseNoticeSeqRalationShip;
	}

}
