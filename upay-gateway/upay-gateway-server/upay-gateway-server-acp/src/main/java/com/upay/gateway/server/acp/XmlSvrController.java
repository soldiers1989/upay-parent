package com.upay.gateway.server.acp;

import java.io.IOException;
import java.util.Map.Entry;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.gateway.server.acp.util.AcpService;
import com.upay.gateway.server.acp.util.SDKConfig;

/**
 * @author shang
 * 
 */
@Controller
@RequestMapping("/acp")
public class XmlSvrController extends MultiActionController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(XmlSvrController.class);

	// @Resource(name="SI_PAY3001")
	// IDipperHandler<Message> SI_PAY3001;

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> body = new HashMap<String, String>(32);
		SDKConfig.getConfig().loadPropertiesFromPath("");
		String resPayload = null; // 响应报文
		String charset = request.getCharacterEncoding(); // 字符集
		if (charset == null) {
			charset = "utf-8";
		}
		try {
			InputStream in = request.getInputStream();
			int length = request.getContentLength();
			byte[] buf = null;
			if (length == -1) {
				buf = new byte[256];
			} else {
				buf = new byte[length];
			}

			byte[] payload = new byte[0];
			while ((length = in.read(buf)) != -1) {
				int destPos = payload.length;
				payload = Arrays.copyOf(payload, destPos + length);
				System.arraycopy(buf, 0, payload, destPos, length);
			}
			StringBuffer context = new StringBuffer(
					new String(payload, charset));
			System.out.println(context.toString());
			if (StringUtils.isBlank(context == null ? "" : context.toString())) {

			}
			String[] dataStr = context.toString().split("&");
			for (int i = 0; i < dataStr.length; i++) {
				body.put(
						dataStr[i].split("=")[0].trim(),
						dataStr[i].split("=")[1] == null ? "" : URLDecoder
								.decode(dataStr[i].split("=")[1].trim(),
										charset));
			}
			boolean checkSign = AcpService.validate(body, charset);
			if (!checkSign) {
				LOGGER.info("-------------验签错误-------------");
			}
			String trancode = null;
		    String reqType=body.get("reqType");
		    
		    //主扫回调
			if ("0530000903".equals(reqType)) {
			    trancode = CommonConstants_GNR.TRANS_TYPE_UNIONPAY_CHECK_FLOWLIST;
				body.put("transCode", trancode);
				Map<String, Object> headers = new HashMap<String, Object>();
				Message m = MessageFactory
						.create(IdGenerateFactory.generateId(),
								MessageFactory.createSimpleMessage(headers, body),
								FaultFactory
										.create(com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS,
												"success"));
				@SuppressWarnings("unchecked")
				IDipperHandler<Message> httpsvrFlowHandler = Bootstrap
						.getApplicationContext().getBean(trancode,
								IDipperHandler.class);
				m = httpsvrFlowHandler.handle(m);

				Map<?, ?> temMap = (Map<?, ?>) m.getTarget().getBodys();
				if(org.apache.commons.lang.StringUtils.isEmpty((String) temMap.get("transSubSeq"))){
					trancode = CommonConstants_GNR.TRANS_TYPE_UNIONPAY_OFFLINE_RETURN;
					body.put("transCode", trancode);
					body.put("payType", DataBaseConstants_PAY.T_PAY_TYPE_ACP_XWR_CODE);
				}else{
					trancode = CommonConstants_GNR.TRANS_TYPE_UNIONPAY_CODE_RETURN;
					body.put("transCode", trancode);
					body.put("payType", DataBaseConstants_PAY.T_PAY_TYPE_ACP_WR_CODE);
				}
				body.put("reqType", "0540000903");
				
			}
			//被扫回调
			if( "0360000903".equals(reqType)||"0320000903".equals(reqType)||"0310000903".equals(reqType)||"0250000903".equals(reqType)) { 
				trancode = CommonConstants_GNR.TRANS_TYPE_UNIONPAY_CODE_RETURN;
				body.put("transCode", trancode);
				body.put("payType", DataBaseConstants_PAY.T_PAY_TYPE_ACP_QR_CODE);
				body.put("reqType", "0350000903");
			}else if ("0540000903".equals(reqType)) {
				trancode = CommonConstants_GNR.TRANS_TYPE_ACP_RUFUND_RETURN;
				body.put("transCode", trancode);
			}
			body.put("routeCode", "0002");

			// payload = context.toString().getBytes(charset);
			Map<String, Object> headers = new HashMap<String, Object>();
			Message m = MessageFactory
					.create(IdGenerateFactory.generateId(),
							MessageFactory.createSimpleMessage(headers, body),
							FaultFactory
									.create(com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS,
											"success"));
			@SuppressWarnings("unchecked")
			IDipperHandler<Message> httpsvrFlowHandler = Bootstrap
					.getApplicationContext().getBean(trancode,
							IDipperHandler.class);
			m = httpsvrFlowHandler.handle(m);

//			Object obj = m.getTarget().getBodys();
//			if (obj != null && obj instanceof byte[]) {
//				resPayload = (byte[]) obj;
//			} else {
//				LOGGER.error("响应结果为空 !!");
//			}
			resPayload=payload.toString();

		} catch (Exception e) {
			LOGGER.error("Error: ", e);

//			if (e.getMessage() != null) {
//				resPayload = e.getMessage().getBytes();
//			} else {
//				resPayload = "System Error !".getBytes();
//			}
		} finally {
//
//			response.setCharacterEncoding(charset);
//			response.setContentType("text/xml");
//			response.setDateHeader("Expires", 0);
//			OutputStream out = null;
//			try {
//				resPayload=URLDecoder.decode(resPayload, charset);  
//				out = response.getOutputStream();
//				if (resPayload != null) {
//					LOGGER.info("channel[{}] response:[{}]",resPayload);
//					out.write(resPayload.getBytes());
//				}
//				out.flush();
//			} catch (IOException e) {
//				LOGGER.error("Error: ", e);
//			} finally {
//				if (out != null) {
//					try {
//						out.close();
//					} catch (IOException e) {
//					}
//				}
//			}
			response.setCharacterEncoding(charset);
			response.setContentType("text/html;charset=" + charset);
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("reqType", body.get("reqType"));
			respMap.put("version", body.get("version"));
			respMap.put("respCode", "00");
			respMap.put("respMsg", "成功");
			Map<String, String> rspData = AcpService.sign(respMap,charset);
			try {
				response.getWriter().write(getRequestParamString(rspData));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 将Map存储的对象，转换为key=value&key=value的字符(通知返回报文不做url编码)
	 *
	 * @param requestParam
	 * @param coder
	 * @return
	 */
	private static String getRequestParamString(Map<String, String> respParam) {

		StringBuffer sf = new StringBuffer("");
		String rspstr = "";
		if (null != respParam && 0 != respParam.size()) {
			for (Entry<String, String> en : respParam.entrySet()) {
				sf.append(en.getKey()
						+ "="
						+ (null == en.getValue() || "".equals(en.getValue()) ? "" : en.getValue()) + "&");
			}
			rspstr = sf.substring(0, sf.length() - 1);
		}
		System.out.println("Resp Message:[" + rspstr + "]");
		return rspstr;
	}

}
