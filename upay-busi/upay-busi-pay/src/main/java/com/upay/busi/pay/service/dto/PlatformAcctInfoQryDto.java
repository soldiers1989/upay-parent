/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 资金通道信息查询
 * 
 * @author zhanggr
 * 
 */
public class PlatformAcctInfoQryDto extends BaseDto {

    private String clearlyAcctNo; // 清算账户
    private String capitalPoolAcctNo; // 平台资金池账号
    private String feeExpenseeAcctNo; // 手续费支出账户
    private String feeIncomeAcctNo; // 手续费收入账户
    private String matEndowmentAcct;// 平台垫资账户
    private String payerName;//清算帐户名称
   

	public String getPayerName() {
		return payerName;
	}


	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}


	public String getClearlyAcctNo() {
        return clearlyAcctNo;
    }


    public void setClearlyAcctNo(String clearlyAcctNo) {
        this.clearlyAcctNo = clearlyAcctNo;
    }


    public String getCapitalPoolAcctNo() {
        return capitalPoolAcctNo;
    }


    public void setCapitalPoolAcctNo(String capitalPoolAcctNo) {
        this.capitalPoolAcctNo = capitalPoolAcctNo;
    }


    public String getFeeExpenseeAcctNo() {
        return feeExpenseeAcctNo;
    }


    public void setFeeExpenseeAcctNo(String feeExpenseeAcctNo) {
        this.feeExpenseeAcctNo = feeExpenseeAcctNo;
    }


    public String getFeeIncomeAcctNo() {
        return feeIncomeAcctNo;
    }


    public void setFeeIncomeAcctNo(String feeIncomeAcctNo) {
        this.feeIncomeAcctNo = feeIncomeAcctNo;
    }


    public String getMatEndowmentAcct() {
        return matEndowmentAcct;
    }


    public void setMatEndowmentAcct(String matEndowmentAcct) {
        this.matEndowmentAcct = matEndowmentAcct;
    }

}
