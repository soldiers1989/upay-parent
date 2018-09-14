package com.upay.busi.mer.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;


/**
 * 查询一级商户维护的二级商户的手续费收取方法
 * 
 * @author yanzixiong
 * 
 */
public class SecondQueryFeeDto extends BaseDto {
    /** 费用方法ID */
    private String feeId;

    /** 二级商户代码 */
    private String secMerNo;

    /** 收费名称 */
    private String feeName;

    /** 收费方法名称 */
    private String feeMethodName;

    /** 账户类型 */
    private String acctType;

    /** 渠道代码 */
    private String chlId;

    /** 商户代码 */
    private String merNo;

    /** 交易代码 */
    private String txnCode;

    /** 收费代码 */
    private String feeCode;

    /** 分润代码 */
    private String assCode;

    /** 费用收取起始日期 */
    private String startDate;

    /** 费用收取终止日期 */
    private String endDate;

    /** 免收周期 */
    private String freeCycle;

    /** 免收次数 */
    private Integer freeCount;

    /** 优惠折扣率 */
    private Integer perFee;

    /** 手续费收起方式 */
    private String getType;

    /** 最后修改操作员 */
    private String lastUpdUserId;

    /** 最后修改时间 */
    private String lastUpdateTime;

    /** 备用 */
    private String memo;

    /** 通道代码 */
    private String routeCode;

    /** 商户名称 */
    private String merName;

    /** 生效状态 1.即将生效2.生效中3.已经失效 */
    private String dateState;

    /** 手续费收取方法List */
    private List<Map<String, Object>> feeGetList;


    public String getFeeId() {
		return feeId;
	}


	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}


	public String getSecMerNo() {
        return secMerNo;
    }


    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }


    public String getFeeName() {
        return feeName;
    }


    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }


    public String getFeeMethodName() {
        return feeMethodName;
    }


    public void setFeeMethodName(String feeMethodName) {
        this.feeMethodName = feeMethodName;
    }


    public String getAcctType() {
        return acctType;
    }


    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }


    public String getDateState() {
        return dateState;
    }


    public void setDateState(String dateState) {
        this.dateState = dateState;
    }


    public String getChlId() {
        return chlId;
    }


    public void setChlId(String chlId) {
        this.chlId = chlId;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getTxnCode() {
        return txnCode;
    }


    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }


    public String getFeeCode() {
        return feeCode;
    }


    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }


    public String getAssCode() {
        return assCode;
    }


    public void setAssCode(String assCode) {
        this.assCode = assCode;
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


    public String getFreeCycle() {
        return freeCycle;
    }


    public void setFreeCycle(String freeCycle) {
        this.freeCycle = freeCycle;
    }


    public Integer getFreeCount() {
        return freeCount;
    }


    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }


    public Integer getPerFee() {
        return perFee;
    }


    public void setPerFee(Integer perFee) {
        this.perFee = perFee;
    }


    public String getGetType() {
        return getType;
    }


    public void setGetType(String getType) {
        this.getType = getType;
    }


    public String getLastUpdUserId() {
        return lastUpdUserId;
    }


    public void setLastUpdUserId(String lastUpdUserId) {
        this.lastUpdUserId = lastUpdUserId;
    }


    public String getLastUpdateTime() {
        return lastUpdateTime;
    }


    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public String getMerName() {
        return merName;
    }


    public void setMerName(String merName) {
        this.merName = merName;
    }


    public List<Map<String, Object>> getFeeGetList() {
        return feeGetList;
    }


    public void setFeeGetList(List<Map<String, Object>> feeGetList) {
        this.feeGetList = feeGetList;
    }

}
