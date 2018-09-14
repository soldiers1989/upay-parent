package com.upay.busi.mer.service.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;


/**
 * 查询一级商户维护的费率规则信息
 * 
 * @author yanzixiong
 * 
 */
public class QueryFeeKindDto extends BaseDto {
    /** 收费代码 */
    private String feeCode;

    /** 收费名称 */
    private String feeName;

    /** 手续费收取方式 */
    private String feeMode;

    /** 单笔固定金额 */
    private BigDecimal fixFee;

    /** 按交易金额比例,该值要除以100展示，如15，表示15% */
    private BigDecimal rationFee;

    /** 手续费上限 */
    private BigDecimal highFee;

    /** 手续费下限 */
    private BigDecimal lowFee;

    /** 备用 */
    private String memo;

    private List<Map<String, Object>> feeKindList;


    public String getFeeCode() {
        return feeCode;
    }


    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }


    public String getFeeName() {
        return feeName;
    }


    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }


    public String getFeeMode() {
        return feeMode;
    }


    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode;
    }


	public BigDecimal getFixFee() {
		return fixFee;
	}


	public void setFixFee(BigDecimal fixFee) {
		this.fixFee = fixFee;
	}


	public BigDecimal getRationFee() {
		return rationFee;
	}


	public void setRationFee(BigDecimal rationFee) {
		this.rationFee = rationFee;
	}


	public BigDecimal getHighFee() {
		return highFee;
	}


	public void setHighFee(BigDecimal highFee) {
		this.highFee = highFee;
	}


	public BigDecimal getLowFee() {
		return lowFee;
	}


	public void setLowFee(BigDecimal lowFee) {
		this.lowFee = lowFee;
	}


	public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }


    public List<Map<String, Object>> getFeeKindList() {
        return feeKindList;
    }


    public void setFeeKindList(List<Map<String, Object>> feeKindList) {
        this.feeKindList = feeKindList;
    }

}
