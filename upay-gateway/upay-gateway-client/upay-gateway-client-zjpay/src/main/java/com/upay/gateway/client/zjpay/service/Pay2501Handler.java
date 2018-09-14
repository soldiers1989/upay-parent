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
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.gateway.client.zjpay.pay.DefaultZJPayClientHandler;
import com.upay.gateway.client.zjpay.utils.CommonUtil;


/**
 * 
 * 中金建立绑定卡关系
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
public class Pay2501Handler extends DefaultZJPayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(Pay2501Handler.class);


    @Override
    protected void setInitParam(Map<String, Object> arg0) {
        log.info("中金建立绑定卡关系 start");
        // head
        arg0.put(Channel._TRAN_CODE_NAME, CmparmConstants.GATEWAY_ZJPAY_2501);// 交易码
        arg0.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID); // 机构编号
        // body
        arg0.put("txSNBinding", arg0.get("txSNBinding"));// 绑定流水号
        arg0.put("bankID", arg0.get("bankID")); // 银行ID(参考银行编码表)
        arg0.put("accountName", arg0.get("certName"));// 账户名称
        arg0.put("accountNumber", arg0.get("eBindAcctNo"));// 账户号码
        // 0:身份证 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民 来往内地通行证 6：台湾同胞来往内地通行证 7：临时身份证
        // 8：外国人居留证 9：警官证 X:其他证件
        String certType = (String) arg0.get("certType");
        if ("01".equals(certType.trim())) {
            certType = "0"; // 第三方接口 0 表示身份证
        }
        arg0.put("identificationType", certType);// 开户证件类型
        arg0.put("identificationNumber", arg0.get("certNo")); // 证件号码
        arg0.put("phoneNumber", arg0.get("mobile")); // 手机号

        // 绑定卡类型
        String cardType = (String) arg0.get("bindAcctType");
        if (DataBaseConstans_ACC.ACCT_TYPE_DEBIT_CARD.equals(cardType)) {
            cardType = "10";// 10：个人借记卡
        } else if (DataBaseConstans_ACC.ACCT_TYPE_CREDIT_CARD.equals(cardType)) {
            cardType = "20";// 20：个人贷记

            arg0.put("validDate", arg0.get("validDate"));// 有效期
            arg0.put("cvn2", arg0.get("cvn2"));// 末三位
        }
        arg0.put("cardType", cardType);// 卡类型

    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        log.info("中金建立绑定卡关系doErrorHandle start!");
        target.put("coreStat", CoreStat.TIME_OUT);
        target.put(FlowStatus.CONFIRMATION_STATUS, Boolean.TRUE);
        target.put("errCode", fault.getOutCode());
        target.put("errMsg", fault.getOutMsg());
        String backCode = fault.getCode();
        log.info("中金2501接口,返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        log.info("中金建立绑定卡关系doFailureHandle start!");
        String backCode = fault.getCode();
        log.info("中金2501接口,返回值[{}]", backCode);
        CommonUtil.returnMsg(fault, backCode);
        target.put("errCode", fault.getCode());
        target.put("errMsg", fault.getMsg());
        throw new DipperException(fault, "failure");
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault arg2) {
        log.info("中金建立绑定卡关系doSuccessHandle start!");
        log.info("---->中金状态[{}]", source.get("status"));
        target.put("zjPayStatus", source.get("status"));
        target.put("responseMessage", source.get("responseMessage"));
    }
}
