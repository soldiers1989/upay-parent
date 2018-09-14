package com.dubhe.common.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.exception.IllegalmessageException;
import com.dubhe.common.executor.ExecutorContext;
import com.dubhe.common.executor.ExecutorDTO;
import com.dubhe.common.json.AppHead;
import com.dubhe.common.json.ChnlMessage;
import com.dubhe.common.json.SysHead;
import com.dubhe.common.util.AnnotationUtil;
import com.dubhe.common.util.BaseMappingUtils;
import com.dubhe.common.util.DateConvertUtils;
import com.dubhe.common.util.StreamUtil;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.exception.SysErrCode;

/**
 * 渠道接入
 * @author leo
 *
 * @param <REQ>
 * @param <RSP>
 */
public abstract class AbstractSingleController<REQ, RSP> extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(AbstractSingleController.class);

    private static final String CURRENT_NUM = "currentNum";
    private static final String TOTAL_NUM = "totalNum";
    private static final String PAGE_INDEX = "pageIndex";
    
    /**
     * 服务请求报文体
     */
    private Class<REQ> req;
    /**
     * 服务响应报文体
     */
    private Class<RSP> rsp;

    /**
     * 交易前处理
     */
    @Autowired
    @Qualifier("preTrasationExecutors")
    private ExecutorContext preTrasationExecutors;


    @SuppressWarnings("unchecked")
    public AbstractSingleController() {
        Type[] t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        req = (Class<REQ>) t[0];
        rsp = (Class<RSP>) t[1];
    }


    @RequestMapping("/execute")
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        
        ChnlMessage<SysHead, AppHead, REQ> reqMsg = null;
        // 初始化响应对象
        ChnlMessage<SysHead, AppHead, RSP> rspMsg = new ChnlMessage<SysHead, AppHead, RSP>();
        SysHead rspSysHead = new SysHead();
        rspMsg.setSyshead(rspSysHead);
        try {
            reqMsg = gainLegitimateMsg(request);
            REQ body = null;
            if (reqMsg.getBody() != null) {
                ExecutorDTO executorDTO = new ExecutorDTO();
                executorDTO = BaseMappingUtils.populateTbyDByApache(reqMsg.getSyshead(), executorDTO);
                executorDTO.setMsg(reqMsg.getMsg());
                Map<String, Object> sysHead = BaseMappingUtils.bean2Map(reqMsg.getSyshead());
                executorDTO.setSysHead(sysHead);
                executorDTO.setRspCode(ReqRspConstants.RSP_CODE_SUCCESS);
                executorDTO.setRspMsg(ReqRspConstants.RSP_CODE_SUCCESS_DESC);
                executorDTO.setRequest(request);
                executorDTO = preTrasationExecutors.execute(executorDTO);
                if (!ReqRspConstants.RSP_CODE_SUCCESS.equals(executorDTO.getRspCode())) {
                    Fault fault = new Fault();
                    fault.setCode(executorDTO.getRspCode());
                    fault.setMsg(executorDTO.getRspMsg());
                    throw new DipperException(fault);
                }

                body = JSONObject.parseObject(reqMsg.getBody().toString(), req);// 转换为泛型的实际类型
                reqMsg.setBody(body);
            }
            // 报文头非空字段检查
            SysHead reqSysHead = reqMsg.getSyshead();
            AnnotationUtil.checkField(SysHead.class, reqSysHead, null, null);

            if (reqMsg.getBody() != null) {
                // 报文体转换成Json对象
                rspMsg.setBody(rsp.newInstance());
                AnnotationUtil.checkField(req, body, null, null);
                Message message = getDubboMessage(reqMsg);
                reqMsg.getSyshead().setSysDate(new Date());
                Map<String, Object> headers = (Map<String, Object>) message.getTarget().getHeaders();
                message.getTarget().setHeaders(headers);
                message.getSource().setHeaders(headers);
                message = execute(message, reqMsg, rspMsg);
                if (message != null) {
                    //后台已返回系统异常,则中断流程,将"系统异常"错误信息统一返回为内部的"系统繁忙"
                    if(message.getFault() != null 
                            && SysErrCode.SYS001.equals(message.getFault().getCode())){
                        throw new Exception(message.getFault().getMsg());
                    }
                    
                    Map<String, Object> bodys = (Map<String, Object>) message.getTarget().getBodys();
                    if (reqMsg.getApphead() != null) {
                        if (bodys.containsKey(CURRENT_NUM)) {
                            bodys.remove(bodys.get(CURRENT_NUM));
                        }
                        if (bodys.containsKey(PAGE_INDEX)) {
                            bodys.remove(bodys.get(PAGE_INDEX));
                        }
                    }
                    BaseMappingUtils.populateTbyDByApache(bodys, rspMsg.getBody());

                    // 返回后台的响应码和响应信息
                    rspSysHead.setTransTime(
                    		DateConvertUtils.format((Date)headers.get(ReqRspConstants.SYS_TIME), "yyyy-MM-dd HH:mm:ss"));
                    rspSysHead.setRspCode(message.getFault().getOutCode());
                    rspSysHead.setRspMsg(message.getFault().getOutMsg());
                    Object objTotalNum = bodys.get(TOTAL_NUM);
                    if (objTotalNum != null && reqMsg.getApphead() != null) {
                        int totalNum = (int) objTotalNum;
                        AppHead appHead = new AppHead();
                        appHead.setTotalNum((int) totalNum);
                        rspMsg.setApphead(appHead);
                    }
                }
                if (ReqRspConstants.RSP_CODE_SUCCESS.equals(rspMsg.getSyshead().getRspCode())) {
                    AnnotationUtil.checkField(rsp, rspMsg.getBody(), reqSysHead.getSessionId(), request);
                }
            } else {
                rspSysHead.setRspCode(ReqRspConstants.RSP_CODE_MSGBODYNULL);
                rspSysHead.setRspMsg(ReqRspConstants.RSP_CODE_MSGBODYNULL_DESC);
            }

        } catch (DipperException ex) {
            Fault fault = (Fault) ex.getObject();
            rspSysHead.setRspCode(fault.getCode());
            rspSysHead.setRspMsg(fault.getMsg());
            logger.error("", fault.getMsg());

        } catch (IllegalArgumentException ex) {
            rspMsg.setBody(null);
            rspMsg.setApphead(null);
            rspSysHead.setRspCode(ReqRspConstants.RSP_CODE_FIELDNULL);
            rspSysHead.setRspMsg(ex.getMessage());
            logger.error("", ex);

        } catch (IllegalmessageException ex) {
            rspMsg.setBody(null);
            rspMsg.setApphead(null);
            rspSysHead.setRspCode(ReqRspConstants.RSP_CODE_ILLEGALMSG);
            rspSysHead.setRspMsg(ReqRspConstants.RSP_CODE_ILLEGALMSG_DESC);
            logger.error("", ex);
        } catch (Exception ex) {
            rspMsg.setBody(null);
            rspMsg.setApphead(null);
            rspSysHead.setRspCode(ReqRspConstants.RSP_CODE_FAIL);
            rspSysHead.setRspMsg(ReqRspConstants.RSP_CODE_FAIL_DESC);
            rspMsg.setBody(null);
            logger.error("", ex);
        } finally {
            try {
                StreamUtil.writeStream(response, rspMsg);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return null;
    }


    /**
     * 转换Dubbo接口服务识别的传入对象
     * 
     * @param reqMsg
     * @return
     * @throws Exception
     */
    private Message getDubboMessage(ChnlMessage<SysHead, AppHead, REQ> reqMsg) throws Exception {
        String id = UUID.randomUUID().toString().replaceAll("-", "");

        Map<String, Object> sysHead = BaseMappingUtils.bean2Map(reqMsg.getSyshead());
        if (reqMsg.getApphead() != null) {
            Map<String, Object> appHead = new HashMap<String, Object>();
            appHead.put(CURRENT_NUM, reqMsg.getApphead().getCurrentNum());
            appHead.put(PAGE_INDEX, reqMsg.getApphead().getPageIndex());
            sysHead.putAll(appHead);
        }

        Message message =
                MessageFactory.create(id, MessageFactory.createSimpleMessage(sysHead, null), MessageFactory
                    .createSimpleMessage(sysHead, null), FaultFactory.create(
                    		ReqRspConstants.RSP_CODE_SUCCESS, ReqRspConstants.RSP_CODE_SUCCESS_DESC),
                    new LinkedList<Store>());

        message.getSource().setBody(BeanCopyUtil.copyBean2MapStrObjNoClass(reqMsg.getBody()));
        message.getTarget().setBody(BeanCopyUtil.copyBean2MapStrObjNoClass(reqMsg.getBody()));

        return message;
    }


    /**
     * 合法报文检测
     * 
     * @param request
     * @return
     * @throws Exception
     */
    private ChnlMessage<SysHead, AppHead, REQ> gainLegitimateMsg(HttpServletRequest request) {

        // 读取请求报文数据，转换成Json对象
        String receive = request.getParameter("msg");
        if (receive == null) {
            throw new IllegalmessageException();
        }
        logger.info("receive:{}", "\n" + JSON.toJSONString(JSON.parse(receive), true));
        ChnlMessage<SysHead, AppHead, REQ> reqMsg =
                JSON.parseObject(receive, new TypeReference<ChnlMessage<SysHead, AppHead, REQ>>() {
                });
        reqMsg.setMsg(receive);
        reqMsg.getSyshead().setHttpSessionId(request.getSession().getId());
        return reqMsg;
    }

    /**
     * 业务处理方法
     */
    protected abstract Message execute(Message message, ChnlMessage<SysHead, AppHead, REQ> reqMsg,
            ChnlMessage<SysHead, AppHead, RSP> rspMsg) throws Exception;

}
