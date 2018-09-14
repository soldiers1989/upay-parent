package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月25日 下午6:29:49
 */
public class SubtractAccountAmtDto extends BaseDto {
    private BigDecimal updateAmt;//支付金额
    private String addOrSub;//0-增加，1-减去
    private String accNo;//账户号
    private boolean updateUserAccountAmt;//是否已经更改用户余额
    private BigDecimal feeAmt;//手续费
    private String getType;//手续费收起方式：0内扣，1外扣
    private String isPayeeAcct;// N：不加手续费扣减账户余额   转账时收款方余额扣减不需要加手续费
    private String totalFee;//当内扣的情况下交易金额需要减去手续费
    private String isAddFeeAmt;//1加手续费  null或2 不加手续费
    
	public String getIsAddFeeAmt() {
		return isAddFeeAmt;
	}

	public void setIsAddFeeAmt(String isAddFeeAmt) {
		this.isAddFeeAmt = isAddFeeAmt;
	}

	public String getIsPayeeAcct() {
		return isPayeeAcct;
	}

	public void setIsPayeeAcct(String isPayeeAcct) {
		this.isPayeeAcct = isPayeeAcct;
	}

	public String getAddOrSub() {
        return addOrSub;
    }

    public void setAddOrSub(String addOrSub) {
        this.addOrSub = addOrSub;
    }

    public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getGetType() {
		return getType;
	}

	public void setGetType(String getType) {
		this.getType = getType;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public boolean isUpdateUserAccountAmt() {
        return updateUserAccountAmt;
    }

    public void setUpdateUserAccountAmt(boolean updateUserAccountAmt) {
        this.updateUserAccountAmt = updateUserAccountAmt;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public BigDecimal getUpdateAmt() {
        return updateAmt;
    }

    public void setUpdateAmt(BigDecimal updateAmt) {
        this.updateAmt = updateAmt;
    }

    
    

    
    
    
    
}
