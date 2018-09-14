package com.dubhe.common.controller;

import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dubhe.common.constants.PayGateConstants;
import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.util.G4Utils;
import com.dubhe.common.util.StreamUtil;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.core.utils.Constants.ResponseCode;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.exception.FlowException;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;

/**
 * JSON报文入口
 * 
 * @author dongweizhao
 * 
 */
@Controller
@RequestMapping("JSONServer")
public class JSONController extends AbstractController {
	private static Logger LOG = LoggerFactory.getLogger(JSONController.class);
//	private IDipperCached cache;
	@Resource(name = "SI_AppFlow")
	private IDipperHandler<Message> commonHandler;
	private IDipperCached cacheClient;
	/**
	 * app交易入口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/execute")
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) {
//		JSONObject jsonObject = JSONArray.parseObject(request.getParameter("msg"));
//		JSONObject head=(JSONObject) jsonObject.get("syshead");
//		JSONObject body=(JSONObject) jsonObject.get("body");
//    	String tokenValue = (String) body.get("tokenId");
//    	String sessionId=(String)head.getString("session_id");
//    	LOG.debug("前端传入的token ID:::::::::::::::"+tokenValue);
//    	
//		
//		//当传入的token ID 并且sesseionId不能空时 验证是否是重复点击    
//		if(StringUtils.isNotBlank(tokenValue)&&StringUtils.isNotBlank(sessionId)){
//			LOG.debug("验证是否重复1 :::::::::::::::");
//			cacheClient = (IDipperCached) WebApplicationContextUtils
//					.getWebApplicationContext(this.getServletContext()).getBean(
//							"DIPPER_REDIS_CLIENT");
//			String cacheTokenKey = CacheConstants.TOKEN_CODE.concat(sessionId);
//			String cacheTokenValue = cacheClient.get(cacheTokenKey);
//			LOG.debug("缓存中的的token ID:::::::::::::::"+tokenValue);
//			
//			if (StringUtils.isNotBlank(cacheTokenValue)) {
//				LOG.debug("验证是否重复结束2 :::::::::::::::");
//				if (tokenValue.equals(cacheTokenValue)) {
//					cacheClient.delete(cacheTokenKey);
//					LOG.debug("验证是否重复3 :::::::::::::::");
//				} else {
//					LOG.debug("验证是否重复4 :::::::::::::::");
//					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0010,"TOKEN_ID");
//				}
//			} else {
//				LOG.debug("验证是否重复5 :::::::::::::::");
//				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0010, "TOKEN_ID");
//			}
//		}
//		LOG.debug("验证是否重复结束 :::::::::::::::");
		return this.handler(request, response, "1");
	}
	
	
	//非标准码  即本平台的微信和支付宝  聚合
	@RequestMapping(value = "/dealCallback")
    public String dealCallback(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String agent = request.getHeader("User-Agent").toLowerCase();
		String merNo = (String) request.getParameter("merNo");
		logger.debug("响应头的类型："+agent);
		String url =  null;
		if (agent.indexOf("micromessenger") > 0) {
			String weChatUrl = PropertiesUtil.getProperty("weiXinOpenUrl");
			String endUrl = PropertiesUtil.getProperty("weiXinEndUrl");
			url = weChatUrl+merNo+endUrl;
			LOG.debug("微信 支付宝聚合          微信授权,授权url为："+url);
		} else if (agent.indexOf("alipayclient") > 0) {
			String aliPayUrl = PropertiesUtil.getProperty("alipay.auth.url");
			url = aliPayUrl+merNo;
			LOG.debug("微信 支付宝聚合          支付宝授权,授权url为："+url);
		}else{
			//生产的错误页面
			url="https://epay.ynhtbank.com/PayTreasure/page/notFound.html";
		}
		return "redirect:" + url;
    }

	
	//银联标准码  银联处理不了，跳转到本平台做支付宝微信的授权
	@RequestMapping(value = "/unionPayCode")
    public String unionPayCode(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		String qrCode = request.getParameter("qrCode");
		LOG.debug("前端传入qrCode：："+qrCode);
		if(StringUtils.isBlank(qrCode)){
			LOG.debug("没有找到参数[qrCode]");
			return null;
		}
		String qrCodeAf = URLDecoder.decode(qrCode,"utf-8");
		Map<String, Object> headers = new HashMap<String, Object>();
		Map<String, Object> bodys = new HashMap<String, Object>();
		bodys.put("qrCode", qrCodeAf);
		
		IDipperHandler<Message> handler = (IDipperHandler<Message>) this.getApplicationContext().getBean("SI_MER0029");
		Message message =
	                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(),
	                        MessageFactory.createSimpleMessage(headers, bodys),FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
		Message handle = handler.handle(message);
		Fault fault = handle.getFault();
        if((!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getCode()))||(!CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getOutCode()))){
            //这里不继承DipperException，将从fault中获取错误信息
            throw new Exception();
        }
        Map<String, Object> resMap = (Map<String, Object>)handle.getTarget().getBodys();
        String merNo = (String) resMap.get("merNo");
        logger.debug("银联对应商户号："+merNo);
		String agent = request.getHeader("User-Agent").toLowerCase();
		logger.debug("响应头的类型："+agent);
		String url =  null;
		
		//TODO 这里要修改为杰安那边的地址。  待和杰安科技对接
		if (agent.indexOf("micromessenger") > 0) {
			String weChatUrl = PropertiesUtil.getProperty("weiXinOpenUrl");
			String endUrl = PropertiesUtil.getProperty("weiXinEndUrl");
			url = weChatUrl+merNo+endUrl;
			LOG.debug("银联聚合   微信授权,授权url为："+url);
		} else if (agent.indexOf("alipayclient") > 0) {
			
			String aliPayUrl = PropertiesUtil.getProperty("alipay.auth.url");
			url = aliPayUrl+merNo;
			LOG.debug("银联聚合    支付宝授权,授权url为："+url);
		}else{
			//生产的错误页面
			url="https://epay.ynhtbank.com/PayTreasure/page/notFound.html";
		}
		
		return "redirect:" + url;
    }

	// app渠道
	private ModelAndView handler(HttpServletRequest request,
			HttpServletResponse response, String type) {
		if (LOG.isDebugEnabled()) {
			Map<String, String[]> map = request.getParameterMap();
			Set<String> str = map.keySet();
			for (String s : str) {
				LOG.debug("body：key：" + s + ",value:" + request.getParameter(s));
			}
			Enumeration<String> head = request.getHeaderNames();
			while (head.hasMoreElements()) {
				String key = head.nextElement();
				LOG.debug("header key:" + key + "value:"
						+ request.getHeader(key));
			}
		}
		String charset = request.getCharacterEncoding();
		charset = (G4Utils.isEmpty(charset)) ? "utf-8" : charset;
		Map<String, Object> rspMsg = new HashMap<String, Object>();
		Message message = null;
		try {
			String json = request.getParameter("msg");
			this.LOG.info(">>receive:[{}]", json);
			message = MessageFactory.create(IdGenerateFactory.generateId(),
					"appcli", "JSON", charset, MessageFactory
							.createSimpleMessage(new HashMap<String, Object>(),
									json.getBytes()), FaultFactory.create(
							ResponseCode.SUCCESS, "SUCCESS"));
			message = (Message) this.commonHandler.handle(message);
			LOG.debug("Message result ： [{}]", message);
			if (!ResponseCode.SUCCESS.equals(message.getFault().getCode())) {
				throw new FlowException(message);
			}
			StreamUtil.writeByteStream(response, (byte[]) message.getTarget()
					.getBodys());
			return null;
		} catch (DipperException ex) {
			Fault fault = (Fault) ex.getObject();
			this.LOG.error("", fault.getMsg());
			// HashMap<String,Object> sysHead=new HashMap<String,Object>();

			// rspMsg.put(ReqRspConstants.RSP_KEY, fault.getCode());
			// rspMsg.put(ReqRspConstants.RSP_MSG, fault.getMsg());
			rspMsg.put("body", "");
			HashMap<String, Object> sysHead = new HashMap<String, Object>();
			sysHead.put(ReqRspConstants.RSP_KEY, fault.getCode());
			sysHead.put(ReqRspConstants.RSP_MSG, fault.getMsg());
			rspMsg.put("syshead", sysHead);
		} catch (FlowException ex) {
			Fault fault = ((Message) ex.getObject()).getFault();
			this.LOG.error("", fault.getMsg());
			rspMsg.put("body", "");
			// rspMsg.put(ReqRspConstants.RSP_KEY, fault.getCode());
			// rspMsg.put(ReqRspConstants.RSP_MSG, fault.getMsg());
			HashMap<String, Object> sysHead = new HashMap<String, Object>();
			sysHead.put(ReqRspConstants.RSP_KEY, fault.getCode());
			sysHead.put(ReqRspConstants.RSP_MSG, fault.getMsg());
			rspMsg.put("syshead", sysHead);

		} catch (Exception ex) {
			this.LOG.error("", ex);
			// rspMsg.put(ReqRspConstants.RSP_KEY,
			// ReqRspConstants.RSP_CODE_FAIL);
			// rspMsg.put(ReqRspConstants.RSP_MSG,
			// ReqRspConstants.RSP_CODE_FAIL_DESC);
			rspMsg.put("body", "");
			HashMap<String, Object> sysHead = new HashMap<String, Object>();
			sysHead.put(ReqRspConstants.RSP_KEY, ReqRspConstants.RSP_CODE_FAIL);
			sysHead.put(ReqRspConstants.RSP_MSG,
					ReqRspConstants.RSP_CODE_FAIL_DESC);
			rspMsg.put("syshead", sysHead);
		}
		try {
			StreamUtil.writeStream(response, rspMsg);
		} catch (Exception e) {
			this.LOG.error("", e);
		}
		return null;
	}

}