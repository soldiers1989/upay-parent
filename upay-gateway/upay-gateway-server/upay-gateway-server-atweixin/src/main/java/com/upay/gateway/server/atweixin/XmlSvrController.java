package com.upay.gateway.server.atweixin;

import java.io.IOException;
import java.util.Map.Entry;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.TransUtil;
import com.upay.gateway.server.atweixin.util.SDKConfig;
import com.upay.gateway.server.atweixin.util.WXPay;
import com.upay.gateway.server.atweixin.util.WXPayUtil;

/**
 * @author shang
 * 
 */
@Controller
@RequestMapping("/atweixin")
public class XmlSvrController extends MultiActionController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(XmlSvrController.class);
	private static WXPay wxpay;
	// @Resource(name="SI_PAY3001")
	// IDipperHandler<Message> SI_PAY3001;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		wxpay = new WXPay();
		Map<String, Object> body = new HashMap<String, Object>();
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
//			String[] dataStr = context.toString().split("&");
//			for (int i = 0; i < dataStr.length; i++) {
//				body.put(
//						dataStr[i].split("=")[0].trim(),
//						dataStr[i].split("=")[1] == null ? "" : URLDecoder
//								.decode(dataStr[i].split("=")[1].trim(),
//										charset));
//			}
//			boolean checkSign = AcpService.validate(body, charset);
//			if (!checkSign) {
//				LOGGER.info("-------------验签错误-------------");
//			}
			
			String transCode="SI_PAY2002";
			Map<String, Object>  reqData = wxpay.processResponseXml(context.toString());
			
//			if(!wxpay.isResponseSignatureValid(reqData)){
//				 ExInfo.throwDipperEx(AppCodeDict.BISMER0034,"");
//			}
			Map<String, Object>  rspData =new HashMap<String, Object>();
			for (String str : reqData.keySet()) { 
		             if(str.indexOf("_")>0){
		            	String[] strtep=str.split("_");
		            	String mapstr="";
		            	for(int i=0;i<strtep.length;i++){
		            		if(i>0){
		            		mapstr=mapstr+upperCase(strtep[i]);
		            		}else{
		            			mapstr=strtep[i];
		            		}
		            	}
		            	rspData.put(mapstr, reqData.get(str));
		             }else{
		            	 rspData.put(str, reqData.get(str));
		             }
				}
				
			rspData.put("transSubSeq", rspData.get("outTradeNo"));
			rspData.remove("outTradeNo");
	        body.putAll(rspData);
	        body.put("transCode",transCode);
			body.put("routeCode", "0003");
			body.put("tradeState", "SUCCESS");
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
					.getApplicationContext().getBean(transCode,
							IDipperHandler.class);
			m = httpsvrFlowHandler.handle(m);

			
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("return_code", "SUCCESS");
			responseMap.put("return_msg", "OK");
	
			resPayload=WXPayUtil.mapToXml(responseMap);
	        } catch (Exception e) {
	            LOGGER.error("Error: ", e);

	            if (e.getMessage() != null) {
	                resPayload = e.getMessage();
	            } else {
	                resPayload = "System Error !";
	            }
	        } finally {

//	            response.setCharacterEncoding(charset);
//	            response.setContentType("text/xml");
//	            response.setDateHeader("Expires", 0);
//	            OutputStream out = null;
	            try {
//	                out = response.getOutputStream();
	                if (resPayload != null) {
	                    LOGGER.info("response:[{}]", resPayload);
	                    response.getWriter().write(resPayload);
	                    response.getWriter().flush();
	                }
	            } catch (IOException e) {
	                LOGGER.error("Error: ", e);
	            } finally {
	                if (response.getWriter() != null) {
	                    try {
	                    	response.getWriter().close();
	                    } catch (IOException e) {
	                    }
	                }
	            }

	        }
	    }
	
	public String upperCase(String str) {  
	    char[] ch = str.toCharArray();  
	    if (ch[0] >= 'a' && ch[0] <= 'z') {  
	        ch[0] = (char) (ch[0] - 32);  
	    }  
	    return new String(ch);  
	}  
	

	}

