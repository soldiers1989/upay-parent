package com.upay.gateway.client.zjpay.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.flow.utils.Constants.FlowStatus;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.gateway.client.zjpay.pay.DefaultZJPayClientHandler;
import com.upay.gateway.client.zjpay.utils.CommonUtil;


/**
 * 
 * 中金快捷支付
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
public class Pay2511Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay2511Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金快捷支付 start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_2511); // 交易码
        arg0.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID);// 机构编号
        // body
        // arg0.put("PaymentNo", arg0.get("PaymentNo"));// 支付交易流水号
        // arg0.put("TxSNBinding", arg0.get("oldGateWay_zjpay_sysSeq")); //
        // 绑定流水号
        // // 即2501的绑定流水号
        // arg0.put("Amount", arg0.get("transAmt")); // 结算金额 单位 分 long
        // arg0.put("SettlementFlag", arg0.get("settlementFlag")); // 结算标识
        // arg0.put("Remark", arg0.get("Remark")); // 备注
        // arg0.put("SettlementFlag", "0001");
        // arg0.put("Remark", "充值");
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金快捷支付doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金2511接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金快捷支付doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金2511接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金快捷支付doSuccessHandle start!");
        target.put("coreStat", CoreStat.SUCESS);
        target.put("routeSeq", source.get("queryId"));
        target.put("status", source.get("status"));
        String bankTxTime=(String)source.get("bankTxTime");
        if(StringUtils.isBlank(bankTxTime)){
        	bankTxTime=DateUtil.format(new Date(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1);
        }
        target.put("bankTxTime", bankTxTime);
    }

}
