package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 
 * 中金绑卡状态判断
 * 
 * @author: David
 * @CreateDate:2016年3月23日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年3月23日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class BindCoreCardStatChkDto extends BaseDto {

    private String cardStat;//卡状态
    
    private String bindAcctType;//绑定卡类型a
    
    
    
	public String getBindAcctType() {
		return bindAcctType;
	}

	public void setBindAcctType(String bindAcctType) {
		this.bindAcctType = bindAcctType;
	}

	public String getCardStat() {
		return cardStat;
	}

	public void setCardStat(String cardStat) {
		this.cardStat = cardStat;
	}

	
}
