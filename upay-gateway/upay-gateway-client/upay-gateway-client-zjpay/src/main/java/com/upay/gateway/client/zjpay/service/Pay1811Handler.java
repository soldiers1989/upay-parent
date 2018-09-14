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
 * 中金支付交易分页对账
 * 
 * @author: 作者信息
 * @CreateDate:2016年3月27日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年3月27日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class Pay1811Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay1811Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金支付交易对账单接口（分页查询）1811初始化start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_1811);
        // 机构编号
        arg0.put("InstitutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID);
        // 对账时间
        arg0.put("searchDate", arg0.get("searchDate"));
        // 对账分页数
        arg0.put("pageNo", arg0.get("pageNo"));
        // 单页 明细条目数
        arg0.put("countPerPage", arg0.get("countPerPage"));
        // body
        arg0.put("TxSN", arg0.get("TxSN"));
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金支付交易分页对账doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金接口（分页查询）1811 返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金支付交易分页对账doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金接口（分页查询）1811 返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金支付交易分页对账doSuccessHandle start!");
        target.put("Status", source.get("Status"));
    }

}
