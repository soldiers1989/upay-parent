/**
 * 
 */
package com.upay.gateway.client.sms;

import static com.pactera.dipper.core.utils.Constants.Channel._IS_CLIENT;
import static com.pactera.dipper.core.utils.Constants.Channel._TRAN_CODE_NAME;
import static com.pactera.dipper.core.utils.Constants.MDC.STEP;
import static com.pactera.dipper.exception.SysErrCode.SYS001;
import static com.pactera.dipper.exception.SysErrCode.SYS003;
import static com.pactera.dipper.exception.SysErrCode.SYS003_MSG;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.presys.cp.http.handler.AbstractHttpClientCommHandler;
import com.pactera.dipper.utils.net.ExceptionUtils;
import com.upay.gateway.client.sms.util.SmsSendUtil;


/**
 * 短信发送
 * 
 * @author zacks
 * 
 */
public abstract class AbstractSmsHttpClientDipperHandler extends AbstractHttpClientCommHandler implements
        ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSmsHttpClientDipperHandler.class);
    private static final String FLAG_FAILURE = "failure";

    private static String SMS_SEND_MSGS = "smsSendMsgs";

    private String smsSendFlag;

    private ApplicationContext appCtx;

    private String channelId;// 短信渠道


    @Override
    @SuppressWarnings("all")
    public Message handle(Message m) throws Exception {

        MDC.put(Constants.MDC.ID, m.getId());
        MDC.put(STEP, m.getFlowStep() + "");
        Date clientTime = new Date();

        Map<String, Object> resultMap = null;

        try {

            Map<String, Object> targetHeaderMap = new HashMap<String, Object>(3);
            targetHeaderMap.put(_IS_CLIENT, true);
            targetHeaderMap.put("PRESYS", "PRESYS");

            Map<String, Object> targetBodyMap = new HashMap<String, Object>();
            targetBodyMap.putAll((Map<String, Object>) m.getTarget().getBodys());
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

            Message message =
                    MessageFactory.create(m.getId(),
                        MessageFactory.createSimpleMessage(targetHeaderMap, targetBodyMap),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));

            setInitParam((Map<String, Object>) message.getTarget().getBodys());

            LOG.debug("client request message={}", message);

            // 测试用代码 by 车
            // smsSendFlag = "true";

            // send request
            LOG.info("功能开关:smsSendFlag = [{}]", smsSendFlag);

            /** 短信发送 */
            if (Boolean.parseBoolean(smsSendFlag)) {
                Map<String, List<String>> smsMap =
                        (Map<String, List<String>>) targetBodyMap.get(SMS_SEND_MSGS);
                SmsSendUtil.SendShortMessage(smsMap, appCtx);
            }
            if (Constants.ResponseCode.SUCCESS.equals(message.getFault().getCode())) {
                doSuccessHandle((Map<String, Object>) message.getTarget().getBodys(), (Map<String, Object>) m
                    .getTarget().getBodys(), message.getFault());
            } else {
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

            LOG.debug("client receive message={}", message);

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


    protected boolean isErrorThrow() {
        // TODO Auto-generated method stub
        return false;
    }


    protected void doErrorHandle(Map<String, Object> bodys, Fault object) {
        // TODO Auto-generated method stub

    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    @Override
    public void setApplicationContext(ApplicationContext appCtx) throws BeansException {
        this.appCtx = appCtx;
    }


    public void setSmsSendFlag(String smsSendFlag) {
        this.smsSendFlag = smsSendFlag;
    }


    protected boolean isFailureThrow() {
        // TODO Auto-generated method stub
        return false;
    }


    protected void doFailureHandle(Map<String, Object> bodys, Map<String, Object> bodys2, Fault fault) {
        // TODO Auto-generated method stub

    }


    protected void doSuccessHandle(Map<String, Object> bodys, Map<String, Object> bodys2, Fault fault) {
        // TODO Auto-generated method stub

    }


    protected void setInitParam(Map<String, Object> bodys) {
        // TODO Auto-generated method stub

    }
}
