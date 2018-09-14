package com.dubhe.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.util.G4Utils;
import com.dubhe.common.util.MD5Utils;
import com.dubhe.common.util.StreamUtil;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.exception.FlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付交易后台通知控制器
 * @author Guo
 *
 */
@Controller
@RequestMapping(value = "/notify")
public class PayNotifyController extends AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(PayNotifyController.class);
	
	private static final String RETURN_URL = "returnUrl";
	private static final String NOTIFY_URL = "notifyUrl";

	@Resource(name = "SI_PageNotifyFlow")
	private IDipperHandler<Message> commonHandler;

	/**
	 * 后台通知（请求商户==>同步返回）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/background")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
    }
	
	/**
	 * 前台结果（异步返回给商户）
	 * @since 思路:前端请求此controller,此controller负责组装返回商户的报文,包括签名,JSON格式返回给前端,前端需要传送返回商户的接口字段,前端负责跳转
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/page")
	public ModelAndView result(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String charset = request.getCharacterEncoding();
		charset = (G4Utils.isEmpty(charset)) ? "utf-8" : charset;
		Map<String, Object> rspMsg = new HashMap<>();
		Message message = null;
		try {
			String json = request.getParameter("msg");
			LOG.info(">>receive:[{}]", json);
			message = MessageFactory.create(
					IdGenerateFactory.generateId(),
					"appcli",
					"JSON",
					charset,
					MessageFactory.createSimpleMessage(new HashMap<String,Object>(),
							json.getBytes()),
					FaultFactory.create(Constants.ResponseCode.SUCCESS, "SUCCESS"));
			message = commonHandler.handle(message);
			LOG.debug("Message result ： [{}]" , message);
			if(!Constants.ResponseCode.SUCCESS.equals(message.getFault().getCode())){
				throw new FlowException(message);
			}
			StreamUtil.writeByteStream(response, (byte[]) message.getTarget().getBodys());
			return null;
		} catch (DipperException ex) {
			Fault fault = (Fault) ex.getObject();
			LOG.error("", fault.getMsg());
			rspMsg.put("body", "");
			HashMap<String,Object> sysHead=new HashMap<>();
			sysHead.put(ReqRspConstants.RSP_KEY, fault.getCode());
			sysHead.put(ReqRspConstants.RSP_MSG, fault.getMsg());
			rspMsg.put("syshead", sysHead);
		} catch (FlowException ex) {
			Fault fault = ((Message)ex.getObject()).getFault();
			LOG.error("",fault.getMsg());
			rspMsg.put("body", "");
			HashMap<String,Object> sysHead=new HashMap<>();
			sysHead.put(ReqRspConstants.RSP_KEY, fault.getCode());
			sysHead.put(ReqRspConstants.RSP_MSG, fault.getMsg());
			rspMsg.put("syshead", sysHead);

		} catch (Exception ex) {
			LOG.error("", ex);
			rspMsg.put("body", "");
			HashMap<String,Object> sysHead=new HashMap<>();
			sysHead.put(ReqRspConstants.RSP_KEY,
					ReqRspConstants.RSP_CODE_FAIL);
			sysHead.put(ReqRspConstants.RSP_MSG,
					ReqRspConstants.RSP_CODE_FAIL_DESC);
			rspMsg.put("syshead", sysHead);
		}
		try {
			StreamUtil.writeStream(response, rspMsg);
		} catch (Exception e) {
			LOG.error("", e);
		}
		return null;
    }

}
