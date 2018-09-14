package com.upay.busi.pay.service.dto;

import java.io.Serializable;

import com.upay.commons.dto.BaseDto;


/**
 * 支付流水查询订单状态 ---单笔
 * 
 * @author liu
 * 
 */
public class PayStateQryDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 上送
    private String sysSeq; // 交易流水

    private String orderNo; // 订单号
    private String merNo; // 商户号
    private String subMerNo; // 二级商户号
    private String transStat; // 内部交易状态


    public String getSysSeq() {
        return sysSeq;
    }


    public void setSysSeq(String sysSeq) {
        this.sysSeq = sysSeq;
    }


    public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getSubMerNo() {
        return subMerNo;
    }


    public void setSubMerNo(String subMerNo) {
        this.subMerNo = subMerNo;
    }


    public String getTransStat() {
        return transStat;
    }


    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }


    @Override
    public String toString() {
        return "sysSeqCheckDto [sysSeq=" + sysSeq + ", orderNo=" + orderNo + ", merNo=" + merNo
                + ", subMerNo=" + subMerNo + ", transStat=" + transStat + "]";
    }

}
