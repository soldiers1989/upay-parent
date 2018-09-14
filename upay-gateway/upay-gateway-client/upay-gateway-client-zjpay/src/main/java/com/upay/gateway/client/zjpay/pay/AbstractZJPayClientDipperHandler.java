/**
 * 
 */
package com.upay.gateway.client.zjpay.pay;

import static com.pactera.dipper.core.utils.Constants.Channel._IS_CLIENT;
import static com.pactera.dipper.core.utils.Constants.Channel._TRAN_CODE_NAME;
import static com.pactera.dipper.core.utils.Constants.MDC.STEP;
import static com.pactera.dipper.exception.SysErrCode.SYS001;
import static com.pactera.dipper.exception.SysErrCode.SYS003;
import static com.pactera.dipper.exception.SysErrCode.SYS003_MSG;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;
import com.pactera.dipper.utils.net.ExceptionUtils;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.util.CommonMethodUtils;


/**
 * 
 * 中金支付处理
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
public abstract class AbstractZJPayClientDipperHandler extends AbstractClientDipperHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractZJPayClientDipperHandler.class);

    private static final String FLAG_FAILURE = "failure";
    private String channelId;// 渠道


    @Override
    @SuppressWarnings("all")
    public Message handle(Message m) throws Exception {
        MDC.put(Constants.MDC.ID, m.getId());
        MDC.put(STEP, m.getFlowStep() + "");
        Date clientTime = new Date();
        try {
            Map<String, Object> targetHeaderMap = new HashMap<String, Object>(3);
            targetHeaderMap.put(_IS_CLIENT, true);
            targetHeaderMap.put("PRESYS", "PRESYS");

            Map<String, Object> targetBodyMap = new HashMap<String, Object>();

            targetBodyMap.putAll((Map<String, Object>) m.getTarget().getBodys());
            // LOG.debug("client[channelId={}", channelId);
            if (null != m.getOther()) {
                // 此处 _TRAN_CODE 只用于在不需要打包时使用
                String headTranCode = (String) m.getOther().get(_TRAN_CODE_NAME);
                if (m.getOther().containsKey(_TRAN_CODE_NAME) && StringUtils.isNotBlank(headTranCode)) {
                    targetHeaderMap.put(_TRAN_CODE_NAME, headTranCode);
                    m.getOther().remove(_TRAN_CODE_NAME);
                }
                LOG.debug("param-ref[{}]", m.getOther());
                targetBodyMap.putAll(m.getOther());
            }

            // //生成中金流水号
            // String TxSN=CommonMethodUtils.getZJSubSeq();
            // targetBodyMap.put("TxSN", TxSN);

            Message message =
                    MessageFactory.create(m.getId(),
                        MessageFactory.createSimpleMessage(targetHeaderMap, targetBodyMap),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));

            setInitParam((Map<String, Object>) message.getTarget().getBodys());

            LOG.debug("client request message={}", message);
            String tranCode = (String) targetBodyMap.get("_TRAN_CODE");
            Map<String, Object> resultMap = new HashMap<String, Object>(2);

            if (CmparmConstants.GATEWAY_ZJPAY_2501.equals(tranCode)) {
                ZJPayCommons.do2501Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2502.equals(tranCode)) {
                ZJPayCommons.do2502Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2503.equals(tranCode)) {
                ZJPayCommons.do2503Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2511.equals(tranCode)) {
                ZJPayCommons.do2511Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2512.equals(tranCode)) {
                ZJPayCommons.do2512Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2521.equals(tranCode)) {
                ZJPayCommons.do2521Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2522.equals(tranCode)) {
                ZJPayCommons.do2522Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1810.equals(tranCode)) {
                ZJPayCommons.do1810Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1811.equals(tranCode)) {
                ZJPayCommons.do1811Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_4510.equals(tranCode)) {
                ZJPayCommons.do4510Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_4512.equals(tranCode)) {
                ZJPayCommons.do4512Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_4530.equals(tranCode)) {
                ZJPayCommons.do4530Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_4532.equals(tranCode)) {
                ZJPayCommons.do5432Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2310.equals(tranCode)) {
                ZJPayCommons.do2310Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2320.equals(tranCode)) {
                ZJPayCommons.do2320Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1120.equals(tranCode)) {
                ZJPayCommons.do1120Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1131.equals(tranCode)) {
                ZJPayCommons.do1131Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1132.equals(tranCode)) {
                ZJPayCommons.do1132Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1133.equals(tranCode)) {
                ZJPayCommons.do1133Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1111.equals(tranCode)) {
                ZJPayCommons.do1111Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2561.equals(tranCode)) {
                ZJPayCommons.do2561Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2340.equals(tranCode)) {
                ZJPayCommons.do2340Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2011.equals(tranCode)) {
                ZJPayCommons.do2011Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_2020.equals(tranCode)) {
                ZJPayCommons.do2020Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1610.equals(tranCode)) {
                ZJPayCommons.do1610Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1620.equals(tranCode)) {
                ZJPayCommons.do1620Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1630.equals(tranCode)) {
                ZJPayCommons.do1630Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1650.equals(tranCode)) {
                ZJPayCommons.do1650Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1510.equals(tranCode)) {
                ZJPayCommons.do1510Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1520.equals(tranCode)) {
                ZJPayCommons.do1520Request(targetBodyMap, message, resultMap);
            } else if (CmparmConstants.GATEWAY_ZJPAY_1550.equals(tranCode)) {
                ZJPayCommons.do1550Request(targetBodyMap, message, resultMap);
            }else if (CmparmConstants.GATEWAY_ZJPAY_4534.equals(tranCode)) {
                ZJPayCommons.do1550Request(targetBodyMap, message, resultMap);
            }else if (CmparmConstants.GATEWAY_ZJPAY_4538.equals(tranCode)) {
                ZJPayCommons.do1550Request(targetBodyMap, message, resultMap);
            }

            if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(message.getFault().getCode())) { // code=2000代表已处理，但不代表该请求状态是成功状态
                doSuccessHandle((Map<String, Object>) message.getTarget().getBodys(), (Map<String, Object>) m
                    .getTarget().getBodys(), message.getFault());
            } else if (null != message.getFault().getE()) { // 处理error
                throw (Exception) message.getFault().getE();
            } else { // 处理Fault
                message.setFault(new Fault(message.getFault().getCode(), message.getFault().getMsg()));
                if (message.getTarget().getBodys() instanceof Map) {
                    doFailureHandle((Map<String, Object>) message.getTarget().getBodys(),
                        (Map<String, Object>) m.getTarget().getBodys(), message.getFault());
                    if (isFailureThrow()) {
                        throw new DipperException(message.getFault(), FLAG_FAILURE);
                    }
                } else {
                    throw new DipperException(message.getFault()); // FLAG_ERROR
                }
            }

        } catch (Exception e) {
            exceptionHandle(m, e);
        }

        LOG.debug("clientService waste time:[{}]ms", new Date().getTime() - clientTime.getTime());
        return m;
    }


    @SuppressWarnings("unchecked")
    private void exceptionHandle(Message m, Exception e) {

        if (e instanceof DipperException) {
            DipperException de = (DipperException) e;
            LOG.error("{}", de.getObject());
            // failure
            if (FLAG_FAILURE.equals(de.getFlag())) {
                throw de;
            }
            doErrorHandle((Map<String, Object>) m.getTarget().getBodys(), (Fault) de.getObject());
            if (isErrorThrow()) {
                throw de;
            }
        } else if (e instanceof RuntimeException) {
            LOG.error("", e);
            Fault fault = FaultFactory.create(SYS001, "系统异常[cause:send]:" + e.getMessage() + "!!");
            doErrorHandle((Map<String, Object>) m.getTarget().getBodys(), fault);
            if (isErrorThrow()) {
                throw new DipperException(fault);
            }
        } else {
            LOG.error("", e);
            Fault fault = FaultFactory.create(SYS003, ExceptionUtils.convertCN(e, channelId, SYS003_MSG));
            doErrorHandle((Map<String, Object>) m.getTarget().getBodys(), fault);
            if (isErrorThrow()) {
                throw new DipperException(fault);
            }
        }
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
