package com.upay.gateway.client.zjpay.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.flow.utils.Constants.FlowStatus;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.constants.CmparmConstants;
import com.upay.gateway.client.zjpay.pay.DefaultZJPayClientHandler;
import com.upay.gateway.client.zjpay.utils.CommonUtil;


/**
 * 
 * 中金支付单笔代付
 * 
 * @author: David
 * @CreateDate:2016年3月27日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年3月27日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class Pay4530Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay4530Handler.class);
    private String paymentAccountName;
    private String paymentAccountNumber;
    private String accountType;


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金支付单笔代付初始化start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_4530);
        arg0.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK);
        // body
        // arg0.put("TxSN", arg0.get("gateWay_zjpay_sysSeq"));
        arg0.put("txSN", arg0.get("routeSeq")); // 代付流水号
        arg0.put("paymentAccountName", arg0.get("paymentAccountName"));
        arg0.put("paymentAccountNumber", arg0.get("paymentAccountNumber"));
        arg0.put("accountType", arg0.get("accountType"));
        arg0.put("bankID", arg0.get("bankId"));
        arg0.put("bankAccountName", arg0.get("certName"));
        arg0.put("bankAccountNumber", arg0.get("vbindAcctNo"));
         arg0.put("phoneNumber", arg0.get("phoneNumber"));
        arg0.put("amount", arg0.get("totalFee"));
        // arg0.put("Remark", arg0.get("Remark"));
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金支付单笔代付doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金4530接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金支付单笔代付doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金4530接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金支付单笔代付doSuccessHandle start!");
        target.putAll(source);
    }


    public String getPaymentAccountName() {
        return paymentAccountName;
    }


    public void setPaymentAccountName(String paymentAccountName) {
        this.paymentAccountName = paymentAccountName;
    }


    public String getPaymentAccountNumber() {
        return paymentAccountNumber;
    }


    public void setPaymentAccountNumber(String paymentAccountNumber) {
        this.paymentAccountNumber = paymentAccountNumber;
    }


    public String getAccountType() {
        return accountType;
    }


    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
