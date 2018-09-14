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
 * 中金支付    商户订单支付（ 直通车 ）
 * 
 * @author: zhangggr
 * @CreateDate:2016年9月6日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年9月6日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class Pay1111Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay1111Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金支付商户订单支付（ 直通车 ）初始化start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_1111);
        arg0.put("InstitutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK);
        // body
        arg0.put("TxSN", arg0.get("TxSN"));
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金支付商户订单支付（ 直通车 ）doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金1111接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金支付商户订单支付（ 直通车 ）doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金1111接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金支付商户订单支付（ 直通车 ）doSuccessHandle start!");
        target.put("Status", source.get("Status"));
        target.put("ResponseCode", source.get("BankResponseCode"));
        target.put("ResponseMessage", source.get("BankResponseMessage"));
        target.put("TxSN", source.get("TxSN"));
    }
}
