package com.dubhe.common.controller;

import com.dubhe.common.constants.PayGateConstants;
import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.util.CheckMobile;
import com.dubhe.common.util.G4Utils;
import com.dubhe.common.util.StreamUtil;
import com.dubhe.common.util.WebUtils;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.core.utils.PacketUtil;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.exception.SysErrCode;
import com.pactera.dipper.presys.mp.agilexml.utils.TemplateUtils;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.util.DateUtil;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.upay.commons.dict.AppCodeDict.GWSPAY9999;

/**
 * 支付网关入口控制类，用于采用后台调用交互模式的接口调用
 * @author Guo
 * @since 2016-08-08
 *
 */
@Controller
@RequestMapping(value = "upaygate")
public class PayGateController extends AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(PayGateController.class);

//	private static final String PRESYS = "PRESYS";

	@RequestMapping(value = "/{serviceName}", method = RequestMethod.POST)
    public ModelAndView handleRequest(@PathVariable String serviceName, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		if (StringUtils.isBlank(serviceName)) {
//			ExInfo.throwDipperEx(GWSPAY2404, "serviceName");
            throw new IllegalArgumentException("serviceName不能为空,非法的请求!!");
        }
        if (PayGateConstants.PAY_SERVICE_NAME.equals(serviceName) 
        			|| PayGateConstants.CONFIRM_DELIVERY_SERVICE_NAME.equals(serviceName)
        			|| PayGateConstants.QUICK_PAY_SERVICE_NAME.equals(serviceName)) {
        	return new ModelAndView("forward:_handlerPageRequest/" + serviceName + ".do");//转发到收银台跳转处理
		}
        String id = UUIDGenerator.randomUUID();
        MDC.put(Constants.MDC.ID, id);
        MDC.put(Constants.MDC.STEP, "0");
        byte[] responseBytes = null;
        try {
            Message m = executeRemote(request, id, serviceName);
            responseBytes = (byte[]) m.getTarget().getBodys();
        } catch (Exception e) {
            LOG.error("server[{}] error!!", getChannel(), e);
            responseBytes = getErrorXml(e, null).getBytes(getCharset());
        } finally {
            StreamUtil.writeStream(response, responseBytes, getChannel());
        }

        return null;
    }
	
	
	/**
	 * 支付、确认收货入口（处理从商户页面的请求）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "_handlerPageRequest/{serviceName}", method = RequestMethod.POST)
	public ModelAndView handlerPageRequest(@PathVariable String serviceName, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = UUIDGenerator.randomUUID();
        MDC.put(Constants.MDC.ID, id);
        MDC.put(Constants.MDC.STEP, "0");
        String charset = request.getCharacterEncoding();
		charset = (G4Utils.isEmpty(charset)) ? "utf-8" : charset;
		/*
		  根据渠道代码判断要跳转的页面:01-app;02-web
		 */
		boolean isMobile = CheckMobile.check(request.getHeader("User-Agent").toLowerCase());
		String payViewName = "";
		String errorViewName = "";
		if (isMobile) {//跳转到h5页面
		    if(PayGateConstants.PAY_SERVICE_NAME.equals(serviceName)||PayGateConstants.QUICK_PAY_SERVICE_NAME.equals(serviceName)){
		        payViewName = PropertiesUtil.getProperty(PayGateConstants.APP_REDIRECT_URL);
		    }else{
		        payViewName = PropertiesUtil.getProperty(PayGateConstants.APP_CONFIRM_DELIVERY_URL);
		    }
			errorViewName = PropertiesUtil.getProperty(PayGateConstants.APP_FAIL_REDIRECT_URL);
		} else {//跳转到web页面
		    if(PayGateConstants.PAY_SERVICE_NAME.equals(serviceName)||PayGateConstants.QUICK_PAY_SERVICE_NAME.equals(serviceName)){		        
		        payViewName = PropertiesUtil.getProperty(PayGateConstants.WEB_REDIRECT_URL);
		    }else{
		        payViewName = PropertiesUtil.getProperty(PayGateConstants.WEB_CONFIRM_DELIVERY_URL);
		    }
			errorViewName = PropertiesUtil.getProperty(PayGateConstants.WEB_FAIL_REDIRECT_URL);
		}
		Fault fault=null;
		try {
            Message m = executeRemote2(request, id, serviceName);
            fault=m.getFault();
            if((!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getCode()))||(!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getOutCode()))){
                //这里不继承DipperException，将从fault中获取错误信息
                throw new Exception();
            }
            Map<String, Object> resMap = (Map<String, Object>) m.getTarget().getBodys();
            model.put("orderNo", resMap.get("orderNo"));
			//跳转页面
			WebUtils.forward(request, response, payViewName, model);//收银台页面展示
		} catch (Exception e) {
			WebUtils.forward(request, response, errorViewName, getErrModel(e, model, fault));//错误页面展示
		}

		return null;
	}

    /**
     * 调用后台dubbo服务
     * @param id
     * @param serviceName
     * @return
     * @throws Exception
     */
	private Message executeRemote(HttpServletRequest request, String id, String serviceName) throws Exception {
        byte[] receiveBytes = StreamUtil.readStream(request, getChannel(), false);
        String charset = request.getCharacterEncoding(); // 字符集
        if (charset == null) {
            charset = "utf-8";
        }
        String context = new String(receiveBytes, charset);
        LOG.info(context);
        //System.out.println(context);
        Message m = initRemoteMessage(id, receiveBytes, serviceName);
        IDipperHandler<Message> flowService = getFlowService();
        LOG.debug("invoke[{}] message[{}] start", getFlowServiceName(), m);
        m = flowService.handle(m);
        LOG.debug("invoke[{}] message[{}] end", getFlowServiceName(), m);
        
        int chnl = context.indexOf("ChnlTp");//检查是否是ESB发起
        String outCode=m.getFault().getCode();
        String faultOutCode=m.getFault().getOutCode();
//        byte[] a=(byte[])m.getTarget().getBodys();
//        String test=new String(a);
        if (!Constants.ResponseCode.SUCCESS.equals(m.getFault().getCode())&&chnl==-1) {
            if (null != m.getFault().getE()) {
                ExInfo.throwDipperEx(GWSPAY9999);
            }
            //后台系统异常或通讯异常统一返回系统繁忙
            if (SysErrCode.SYS003.equals(m.getFault().getCode()) || SysErrCode.SYS001.equals(m.getFault().getCode())) {
                ExInfo.throwDipperEx(GWSPAY9999);
            }
//    			responseBytes = getErrorXml(null, m.getFault()).getBytes(getCharset());
            ExInfo.throwDipperEx(m.getFault());
        }else if ((!Constants.ResponseCode.SUCCESS.equals(outCode)||!Constants.ResponseCode.SUCCESS.equals(faultOutCode))
        			&&chnl>-1) {//本行网银
        	 TemplateUtils xmlUtils = new TemplateUtils();
        	 XMLConfiguration xmlcfg = xmlUtils.readxml(receiveBytes);
        	//检查是否是ESB发起,是的话需要重组报文
         	StringBuffer sb=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
     		sb.append("<service>");
     		sb.append("<SYS_HEAD>");
     		
         	//得到文档名称为Student的元素的节点列表
             NodeList sysHead = xmlcfg.getDocument().getElementsByTagName("SYS_HEAD");
             if(sysHead.getLength()==0){
             	return null;
             }
             
             String retStat="S";
         	 String retCode=CommonConstants_GNR.TRANS_FAULT_SUCCESS;
         	
             //遍历该集合，显示结合中的元素及其子元素的名字
             for(int i = 0; i< sysHead.getLength() ; i ++){
                 Element node = (Element)sysHead.item(i);
                 String chnlStr=node.getElementsByTagName("ChnlTp").item(0).getFirstChild().getNodeValue();
                 if(!CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(chnlStr)){
                 	return null;
                 }
                 Date now=new Date();
                sb.append("<SvcCd>"+node.getElementsByTagName("SvcCd").item(0).getFirstChild().getNodeValue()+"</SvcCd>");
	    		sb.append("<SvcScn>"+node.getElementsByTagName("SvcScn").item(0).getFirstChild().getNodeValue()+"</SvcScn>");
	    		sb.append("<CnsmSysId>EBS</CnsmSysId>");
	    		sb.append("<PrvdSysId>UPP</PrvdSysId>");
	    		sb.append("<CnsmSysSeqNo>"+node.getElementsByTagName("CnsmSysSeqNo").item(0).getFirstChild().getNodeValue()+"</CnsmSysSeqNo>");
	    		sb.append("<PrvdSysSeqNo>UPAY"+DateUtil.format(now, "yyyyMMddhhmmss")+"</PrvdSysSeqNo>");
	    		sb.append("<SrcSysSeqNo>"+node.getElementsByTagName("SrcSysSeqNo").item(0).getFirstChild().getNodeValue()+"</SrcSysSeqNo>");
	    		
	    		sb.append("<TranDt>"+DateUtil.format(now, "yyyyMMdd")+"</TranDt>");
	    		sb.append("<TranTm>"+DateUtil.format(now, "hhmmss")+"</TranTm>");
	    		if(!CommonConstants_GNR.TRANS_FAULT_SUCCESS.equals(outCode)&&
	    				!CommonConstants_GNR.SERVICE_STAT.equals(faultOutCode)){
	    			retStat="F";
	    			retCode="UPP000"+outCode;
	    		}
	    		sb.append("<TranRetSt>"+retStat+"</TranRetSt>");
	    		sb.append("<RetMsgArryList>");
	    		sb.append("<RetMsgArry>");
	    		sb.append(" <RetCd>"+retCode+"</RetCd>");
	    		sb.append(" <RetMsg>"+m.getFault().getMsg()+"</RetMsg>");
	    		sb.append("</RetMsgArry>");
	    		sb.append("</RetMsgArryList>");
	    		sb.append(" <FileFlg>0</FileFlg>");
	    		sb.append("</SYS_HEAD>");
	    		sb.append("<APP_HEAD>");
	//    		sb.append("<TlrNo>00012545</TlrNo>");
	//    		sb.append("<BrId>0099</BrId>");
	//    		sb.append("<TlrPwsd>800800</TlrPwsd>");
	//    		sb.append("<TlrLvl>1</TlrLvl>");
	//    		sb.append("<AthrzInd>0</AthrzInd>");
	    		sb.append("</APP_HEAD>");
	    		sb.append("</service>");
	    		LOG.debug("ESB 接口  支付平台系统异常返回{}",sb.toString());
             }
             m.getTarget().setBody(sb.toString().getBytes());
        }
        return m;
    }
	/**
     * 调用后台dubbo服务
     * @param id
     * @param serviceName
     * @return
     * @throws Exception
     */
    private Message executeRemote2(HttpServletRequest request, String id, String serviceName) throws Exception {
      byte[] receiveBytes = StreamUtil.readStream(request, getChannel(), false);
      Message m = initRemoteMessage2(id, receiveBytes, serviceName);
//        Message m = initRemoteMessage3(id, request, serviceName);
        IDipperHandler<Message> flowService = getFlowService();
        LOG.debug("invoke[{}] message[{}] start", getFlowServiceName(), m);
        m = flowService.handle(m);
        LOG.debug("invoke[{}] message[{}] end", getFlowServiceName(), m);

        if (!Constants.ResponseCode.SUCCESS.equals(m.getFault().getCode())) {
            if (null != m.getFault().getE()) {
                ExInfo.throwDipperEx(GWSPAY9999);
            }
            //后台系统异常或通讯异常统一返回系统繁忙
            if (SysErrCode.SYS003.equals(m.getFault().getCode()) || SysErrCode.SYS001.equals(m.getFault().getCode())) {
                ExInfo.throwDipperEx(GWSPAY9999);
            }
//              responseBytes = getErrorXml(null, m.getFault()).getBytes(getCharset());
            ExInfo.throwDipperEx(m.getFault());
        }
        return m;
    }
    /**
     * 初始化dubbo调用
     * @param id
     * @param receiveBytes
     * @return
     */
    private Message initRemoteMessage3(String id,HttpServletRequest request, String serviceName) {
        Map<String, Object> targetHeaderMap = new HashMap<String, Object>();
//      targetHeaderMap.put(PRESYS, PRESYS);
        Map<String,Object> map=new HashMap<String,Object>();
        Enumeration<String> e=request.getParameterNames();
        while (e.hasMoreElements()) {
            String string = (String) e.nextElement();
            map.put(string, request.getParameter(string));
        }
        Message m =
                MessageFactory.create(id, getChannel(), "HTTP", "UTF-8",
                    MessageFactory.createSimpleMessage(targetHeaderMap, map),
                    FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        PacketUtil.setValueOfTargetHeaders(m, Channel._TRAN_CODE_NAME, serviceName);// 交易码
        return m;
    }
    /**
     * 初始化dubbo调用
     * @param id
     * @param receiveBytes
     * @return
     */
    private Message initRemoteMessage2(String id, byte[] receiveBytes, String serviceName) {
        Map<String, Object> targetHeaderMap = new HashMap<String, Object>();
//      targetHeaderMap.put(PRESYS, PRESYS);

        Message m =
                MessageFactory.create(id, getChannel(), "HTTP", "UTF-8",
                    MessageFactory.createSimpleMessage(targetHeaderMap, receiveBytes),
                    FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        PacketUtil.setValueOfTargetHeaders(m, Channel._TRAN_CODE_NAME, serviceName);// 交易码
        return m;
    }

	/**
	 * 初始化dubbo调用
	 * @param id
	 * @param receiveBytes
	 * @return
	 */
	private Message initRemoteMessage(String id, byte[] receiveBytes, String serviceName) {
		Map<String, Object> targetHeaderMap = new HashMap<String, Object>();
//		targetHeaderMap.put(PRESYS, PRESYS);

		Message m =
                MessageFactory.create(id, getChannel(), "HTTP", getCharset(),
                    MessageFactory.createSimpleMessage(targetHeaderMap, receiveBytes),
                    FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        PacketUtil.setValueOfTargetHeaders(m, Channel._TRAN_CODE_NAME, serviceName);// 交易码
        return m;
	}

	@SuppressWarnings("unchecked")
	private IDipperHandler<Message> getFlowService() {
		return (IDipperHandler<Message>) this.getApplicationContext().getBean(getFlowServiceName());
	}

	public String getChannel() {
		return "paysvr";
	}

	public String getFormat() {
		return "XML";
	}

	public String getCharset() {
		return "UTF-8";
	}

	public String getFlowServiceName() {
		return "SI_PaySvrFlow";
	}

	protected ModelMap getErrModel(Exception e, ModelMap model, Fault fault) {
		fault = getErrorMsg(e, fault);
		String rspCode =null;
		String rspMsg =null;
		if(!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getCode())){		    
		    rspCode=fault.getCode();
		    rspMsg=fault.getMsg();
		}else{
		    rspCode=fault.getOutCode();
		    rspMsg=fault.getOutMsg();
		}
		model.put("rspCode", rspCode);
		model.put("rspMsg", rspMsg);
		return model;
	}

	private String getErrorXml(Exception e, Fault fault) {
		fault = getErrorMsg(e, fault);
		String rspCode = fault.getOutCode();
		String rspMsg = fault.getMsg();
		StringBuilder stringBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuilder.append("<epay>")
				.append("<rspCode>").append(rspCode).append("</rspCode>")
				.append("<rspMsg>").append(rspMsg).append("</rspMsg>")
				.append("</epay>");

		return stringBuilder.toString();
	}

	private Fault getErrorMsg(Exception e, Fault fault) {
		String rspCode = "";
		String rspMsg = "";
		if (null != e && e instanceof DipperException) {
			fault = (Fault) ((DipperException) e).getObject();
			rspCode = fault.getOutCode();
			rspMsg = fault.getMsg();
		} else if(null != fault) {
			rspCode = fault.getOutCode();
			rspMsg = fault.getMsg();
		} else {
			LOG.error("", e);
			rspCode = "9999";
			rspMsg = "系统繁忙,请稍后再试";
		}
		fault.setOutCode(rspCode);
		fault.setOutMsg(rspMsg);
		return fault;
	}

}
