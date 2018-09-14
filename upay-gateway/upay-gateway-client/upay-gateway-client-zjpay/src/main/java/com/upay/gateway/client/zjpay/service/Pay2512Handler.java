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
 * 中金快捷支付查询
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
public class Pay2512Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay2512Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金快捷支付查询 start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_2512);// 交易码
        arg0.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID);// 机构编号
        // body
        arg0.put("paymentNo", arg0.get("paymentNo"));// 支付交易流水号
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金快捷支付查询doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金2512接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金快捷支付查询doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金2512接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金快捷支付查询doSuccessHandle start!");
        target.put("status", source.get("status"));
        target.put("responseCode", source.get("responseCode"));
        target.put("responseMessage", source.get("responseMessage"));
        target.put("paymentNo", source.get("paymentNo"));
    }
}
