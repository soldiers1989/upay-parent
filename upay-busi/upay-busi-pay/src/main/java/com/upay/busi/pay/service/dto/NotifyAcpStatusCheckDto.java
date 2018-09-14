package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;


/**
 * Created by hry
 */
public class NotifyAcpStatusCheckDto extends BaseDto {
	private String outRouteSeq;

	private String settleKey;
	
	private String orderTime;

	private String reqType;
	
	private String queryId;

	private String routeSeq;
	
	private String timeEnd;
	
	private String voucherNum;
	
	private String txnTime;
	
	private String routeDate;
	
	private String respCode;
	
	private String respMsg;
	

	public String getRespCode() {
		return respCode;
	}


	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}


	public String getRespMsg() {
		return respMsg;
	}


	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}


	public String getTxnTime() {
		return txnTime;
	}


	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}


	/** 平台订单号,对应外部商户系统订单号 */
    private String orderNo;

    /** 平台明细流水,提供给外部系统的订单号 */
    private String transSubSeq;

    /** 支付通知结果 */
    private String rspCode;

    /** 是否通知核心系统 */
    private String isNotifyCoreSys;

    /** 往来标识 */
    private String srFlag;

    /** 收款账号 */
    private String payeeAccNo;

    /** 付款账号 */
    private String payerAccNo;

    private String merNo;

    /** 通道代码 */
    private String routeCode;

    /** 支付金额 */
    private BigDecimal transAmt;

    private String orderDes;

    private String refundFlag;// 是否订单重复，发起退款

    private String payServicType;// 支付服务类型


    public String getOrderDes() {
        return orderDes;
    }


    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }




    public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getTransSubSeq() {
        return transSubSeq;
    }


    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }


    public String getRspCode() {
        return rspCode;
    }


    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }


    public String getIsNotifyCoreSys() {
        return isNotifyCoreSys;
    }


    public void setIsNotifyCoreSys(String isNotifyCoreSys) {
        this.isNotifyCoreSys = isNotifyCoreSys;
    }


    public String getSrFlag() {
        return srFlag;
    }


    public void setSrFlag(String srFlag) {
        this.srFlag = srFlag;
    }


    public String getPayeeAccNo() {
        return payeeAccNo;
    }


    public void setPayeeAccNo(String payeeAccNo) {
        this.payeeAccNo = payeeAccNo;
    }


    public String getPayerAccNo() {
        return payerAccNo;
    }


    public void setPayerAccNo(String payerAccNo) {
        this.payerAccNo = payerAccNo;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public String getRefundFlag() {
        return refundFlag;
    }


    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag;
    }


    public String getPayServicType() {
        return payServicType;
    }


    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }
    

  	public String getRouteSeq() {
  		return routeSeq;
  	}


  	public void setRouteSeq(String routeSeq) {
  		this.routeSeq = routeSeq;
  	}


  	public String getTimeEnd() {
  		return timeEnd;
  	}


  	public void setTimeEnd(String timeEnd) {
  		this.timeEnd = timeEnd;
  	}


  	public String getVoucherNum() {
  		return voucherNum;
  	}


  	public void setVoucherNum(String voucherNum) {
  		this.voucherNum = voucherNum;
  	}
  	
	public String getQueryId() {
		return queryId;
	}


	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getRouteDate() {
		return routeDate;
	}


	public void setRouteDate(String routeDate) {
		this.routeDate = routeDate;
	}

	public String getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	public String getReqType() {
		return reqType;
	}


	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	
	public String getSettleKey() {
		return settleKey;
	}


	public void setSettleKey(String settleKey) {
		this.settleKey = settleKey;
	}
	public String getOutRouteSeq() {
		return outRouteSeq;
	}


	public void setOutRouteSeq(String outRouteSeq) {
		this.outRouteSeq = outRouteSeq;
	}
}
