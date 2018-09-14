package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

import java.util.List;
import java.util.Map;

public class MerQueryListDto extends BaseDto{

    /**
     * 联系人手机       db_column: CONTACT_MOBILE
     */
	private String contactMobile;
    /**
     * 联系人邮件       db_column: CONTACT_EMAIL
     */
	private String contactEmail;




	private List<Map<String, Object>> usrList;

	public List<Map<String, Object>> getUsrList() {
		return usrList;
	}

	public void setUsrList(List<Map<String, Object>> usrList) {
		this.usrList = usrList;
	}

	private List<Map<String, Object>> merList;


	public List<Map<String, Object>> getMerList() {
		return merList;
	}

	public void setMerList(List<Map<String, Object>> merList) {
		this.merList = merList;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}





}
