/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author Administrator
 *
 */
public class RegTransferFileDto extends BaseDto{
	private String fileNames;//转账文件名，多个以逗号隔开

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
	
}
