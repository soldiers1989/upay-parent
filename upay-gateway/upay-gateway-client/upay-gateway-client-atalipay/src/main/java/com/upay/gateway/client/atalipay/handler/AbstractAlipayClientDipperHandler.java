/**
 * 
 */
package com.upay.gateway.client.atalipay.handler;

import static com.pactera.dipper.core.utils.Constants.Channel._IS_CLIENT;
import static com.pactera.dipper.core.utils.Constants.Channel._TRAN_CODE_NAME;
import static com.pactera.dipper.core.utils.Constants.MDC.STEP;
import static com.pactera.dipper.exception.SysErrCode.SYS001;
import static com.pactera.dipper.exception.SysErrCode.SYS003;
import static com.pactera.dipper.exception.SysErrCode.SYS003_MSG;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.pactera.dipper.presys.mp.json.configuration.ConfigurationRegistry;
import com.pactera.dipper.presys.mp.json.model.ConfigDef;
import com.pactera.dipper.presys.mp.json.model.ElementDef;
import com.pactera.dipper.presys.mp.json.model.MappingDef;
import com.pactera.dipper.presys.mp.json.model.MappingsDef;
import com.pactera.dipper.utils.net.ExceptionUtils;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.TransUtil;
import com.upay.gateway.client.atalipay.util.Alipay;
import com.upay.gateway.client.atalipay.util.AlipayConstants;


/**
 * 
 * 支付宝
 * 
 * @author: 
 * @CreateDate:2017年11月14日
 * 
 * 
 * 
 * 
 */
public abstract  class AbstractAlipayClientDipperHandler extends AbstractClientDipperHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractAlipayClientDipperHandler.class);

    private static final String FLAG_FAILURE = "failure";
    private String channelId = "alipay";// 渠道
    private Alipay alipay;
    private String appId;//支付宝分配给开发者的应用ID
    
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
            

            Message message =
                    MessageFactory.create(m.getId(),
                        MessageFactory.createSimpleMessage(targetHeaderMap, targetBodyMap),
                        FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));

            setInitParam((Map<String, Object>) message.getTarget().getBodys());

            LOG.debug("client request message={}", message);
            Map<String, Object> body = (Map<String, Object>) message.getTarget().getBodys();
            String tranCode = (String) body.get("_TRAN_CODE");
            Map<String, Object> reqParam =
                    TransUtil.parseValueBeforeTransForAlipay(tranCode, body, CmparmConstants.TRANS_TYPE_BEFORE);
            Map<String, Object> rspData = null;
            switch (tranCode) {
            case "pay":
            	rspData = alipay.pay(reqParam,message, appId);
                break;
            case "query":
            	rspData = alipay.query(reqParam,message, appId);
                break;
            case "precreate":
            	rspData = alipay.precreate(reqParam,message, appId);
                break;
            case "create":
            	rspData = alipay.create(reqParam,message, appId);
                break;
            case "refundQuery":
            	rspData = alipay.refundQuery(reqParam,message, appId);
                break;
            case "orderSettle":
            	rspData = alipay.orderSettle(reqParam,message, appId);
                break;
            case "close":
            	rspData = alipay.close(reqParam,message, appId);
                break;
            case "cancel":
            	rspData = alipay.cancel(reqParam,message, appId);
                break;
            case "refund":
            	rspData = alipay.refund(reqParam,message, appId);
                break;
            case "subMchAdd":
            	rspData = alipay.subMchAdd(reqParam,message, appId);
                break;
            case "subMchQuery":
            	rspData = alipay.subMchQuery(reqParam,message, appId);
                break;
            case "subMchUpdate":
            	rspData = alipay.subMchUpdate(reqParam,message, appId);
                break;
            case "billDownload":
            	rspData = alipay.billDownload(reqParam,message, appId);
                break;
            default:
                message.getFault().setCodeAll("BISPAY9999");
                message.getFault().setMsgAll("无效的的tranCode：" + tranCode);
                break;
            }

    		message.getFault().setCode((String) rspData.get("code"));
    		message.getFault().setMsg((String) rspData.get("msg"));
    		message.getFault().setOutCode((String) rspData.get("sub_code"));
    		message.getFault().setOutMsg((String) rspData.get("sub_msg"));
    		Map<String, Object> headMap = TransUtil.parseValueBeforeTransForAlipay("head", rspData, CmparmConstants.TRANS_TYPE_AFTER);
            if (AlipayConstants.SUCCESS.equals(message.getFault().getCode())) {
                Map<String, Object> respMap = TransUtil.parseValueBeforeTransForAlipay(tranCode, rspData, CmparmConstants.TRANS_TYPE_AFTER);
                respMap.putAll(headMap);
                message.getTarget().setBody(respMap);
                doSuccessHandle((Map<String, Object>) message.getTarget().getBodys(), (Map<String, Object>) m
                    .getTarget().getBodys(), message.getFault());
            } else if (null != message.getFault().getE()) { // 处理error
                throw (Exception) message.getFault().getE();
            } else { // 处理Fault
                //message.setFault(new Fault(message.getFault().getCode(), message.getFault().getMsg()));
            	message.getTarget().setBody(headMap);
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


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public void setAlipay(Alipay alipay) {
		this.alipay = alipay;
	}


	
	
}
