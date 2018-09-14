package com.upay.gateway.client.zjpay.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
 * 中金支付单笔代收
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
public class Pay2011Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay2011Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> body) {
    	log.info("中金支付单笔代付初始化开始");
		body.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_2011);
//		String txSN = CommonMethodUtils.getZJSubSeq();
		String txSN = body.get("zjSeq").toString();
		body.put("txSN", txSN);
		if (StringUtils.isBlank((String) body.get("institutionID"))) {
			body.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION);
		}
		
		body.put("settlementFlag", "0001");
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金支付单笔代收doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金2011接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金支付单笔代收doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金2011接口，返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金支付单笔代收doSuccessHandle start!");
        target.putAll(source);
    }
}
