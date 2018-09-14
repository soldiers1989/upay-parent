/**
 * 
 */
package com.upay.gateway.client.acpmer.service;

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
import com.unionpay.acpmer.sdk.SDKConfig;
import com.upay.commons.constants.CmparmConstants;
import com.upay.gateway.client.acpmer.util.AcpToolUtil;


/**
 * 银联在线交易
 * 
 * @author zacks
 * 
 */
public abstract class AbstractAcpClientDipperHandler extends AbstractClientDipperHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractAcpClientDipperHandler.class);
    private static final String FLAG_FAILURE = "failure";
    private static String ACP_VERSION = "version";

    private String channelId;// 银联渠道
    private String acpMerId;
    private String acpConnectionTimeout;
    private String acpReadTimeout;
    private String backUrl;
    private String acpFileRoot;
    private String acpVersion;
    private String RnUrl = "https://upverify.test.95516.com/gateway/api/backTransReq.do";
    
    

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
            LOG.debug("client[channelId={}", channelId);
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
         
            targetBodyMap.put(CmparmConstants.ACP_BACK_URL, backUrl);
        	String requestBackUrl="";
            /** 请求地址 */
            if(targetBodyMap.get("bizType")!=null&&targetBodyMap.get("bizType").equals("000803")){
            	requestBackUrl= RnUrl;
            }else{
            	
                requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();
            }
            Map<String, String> constantMap = new HashMap<String, String>();
            constantMap.put(CmparmConstants.REQUEST_BACK_URL, requestBackUrl);
            LOG.info("无跳转商户号[{}]",acpMerId);
            constantMap.put(CmparmConstants.ACP_MER_ID, acpMerId);
            constantMap.put(ACP_VERSION, acpVersion);
            constantMap.put(CmparmConstants.ACP_CONNECTION_TIMEOUT, acpConnectionTimeout);
            constantMap.put(CmparmConstants.ACP_READ_TIMEOUT, acpReadTimeout);
            constantMap.put(CmparmConstants.ACP_SIGN_METHOD, "01");

            Message message =
                    MessageFactory.create(m.getId(),
                        MessageFactory.createSimpleMessage(targetHeaderMap, targetBodyMap),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));

            setInitParam((Map<String, Object>) message.getTarget().getBodys());

            LOG.debug("client request message={}", message);
            // send request
            resultMap = AcpToolUtil.send(targetBodyMap, CmparmConstants.ENCODING, acpFileRoot, constantMap);

            if (resultMap == null || resultMap.size() == 0) {
                LOG.info("相应报文不能为空!!");
                throw new Exception("response is null!!");
            }

            String respCode = (String) resultMap.get("respCode");
            String respMsg = (String) resultMap.get("respMsg");
            if (StringUtils.isBlank(respCode)) {
                LOG.info("响应码不能为空,响应报文[{}]", resultMap);
                throw new Exception("respCode is null!!");
            }
            if (!CmparmConstants.ACP_RESP_CODE.equals(respCode)) {
                message.getFault().setCode(respCode);
                message.getFault().setMsg(respMsg);
            }

            targetBodyMap.putAll(resultMap);

            message.getTarget().setBody(targetBodyMap);

            LOG.debug("client receive message={}", message);

            if (Constants.ResponseCode.SUCCESS.equals(message.getFault().getCode())) {
                doSuccessHandle((Map<String, Object>) message.getTarget().getBodys(), (Map<String, Object>) m
                    .getTarget().getBodys(), message.getFault());
            } else if (null != message.getFault().getE()) {
                throw (Exception) message.getFault().getE();
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


	public void setAcpMerId(String acpMerId)
    {

		this.acpMerId = acpMerId;
        LOG.info("无跳转商户号[{}] setAcpMerId",	this.acpMerId );
	}


    public void setAcpConnectionTimeout(String acpConnectionTimeout) {

        this.acpConnectionTimeout = acpConnectionTimeout;
    }


    public void setAcpReadTimeout(String acpReadTimeout) {
        this.acpReadTimeout = acpReadTimeout;
    }


    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }


    public void setAcpFileRoot(String acpFileRoot) {
        this.acpFileRoot = acpFileRoot;
    }


    public void setAcpVersion(String acpVersion) {
        this.acpVersion = acpVersion;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

}
