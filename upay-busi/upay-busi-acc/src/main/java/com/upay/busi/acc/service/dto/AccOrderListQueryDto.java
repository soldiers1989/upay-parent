package com.upay.busi.acc.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseQueryDto;


/**
 * 获取渠道节点
 * 
 * @author xuxin
 * 
 */
public class AccOrderListQueryDto extends BaseQueryDto {
    
	
	private String startDate;
	private String endDate;
	private String merNo;

//    二级商户号
	private String secMerNo;
//	渠道代码
	private String routeCode;

    //结算标志
    private String stlFlag;

    //内部订单号
    private String orderNo;
    //外部订单号
    private String outerOrderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOuterOrderNo() {
        return outerOrderNo;
    }

    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }

    public String getStlFlag() {
        return stlFlag;
    }

    public void setStlFlag(String stlFlag) {
        this.stlFlag = stlFlag;
    }




    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	private String acctType;
    
    private String orderStat;
    
    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    private String transFlag;


    public String getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(String transFlag) {
        this.transFlag = transFlag;
    }

    private List<Map<String,Object>> transList;

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<Map<String, Object>> getTransList() {
        return transList;
    }

    public void setTransList(List<Map<String, Object>> transList) {
        this.transList = transList;
    }
    /*
     * 交易类型
     * 01支付
	 * 02充值
     * 03提现
     * 04转账
     * 09退款
     */
    private String transType;

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
    
    
  

}
