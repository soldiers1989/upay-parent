package com.dubhe.common.executor;


import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.util.BaseMappingUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author freeplato
 */
public class ExecutorDTO {

    /**
     * 交易码
     */
    private String transCode;

    /**
     * SESSION_ID
     */
    private String sessionId;

    /**
     * 用户号
     */
    private String userId;

    /**
     * 渠道
     */
    private String chnlId;

    /**
     * 请求对象
     */
    private HttpServletRequest request;

    /**
     * 响应码
     */
    private String rspCode = ReqRspConstants.RSP_CODE_SUCCESS;


    /**
     * 验证码
     */
    private String verifKey;

    /**
     * 响应信息
     */
    private String rspMsg = ReqRspConstants.RSP_CODE_SUCCESS_DESC;

    private Map<String, Object> body;

    private String msg;

    /**
     * 报文头
     */
    private Map<String, Object> sysHead;


    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChnlId() {
        return chnlId;
    }

    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, Object> getSysHead() {
        return sysHead;
    }

    public void setSysHead(Map<String, Object> sysHead) {
        this.sysHead = sysHead;
    }

    public String getVerifKey() {
        return verifKey;
    }

    public void setVerifKey(String verifKey) {
        this.verifKey = verifKey;
    }

    public void setBody(Map<String, Object> body) {
        BaseMappingUtils.populateTbyDByApache(body, this);
        this.body = body;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
