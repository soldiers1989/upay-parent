package com.upay.gateway.server.atalipay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alipay.api.internal.util.AlipaySignature;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.gateway.server.atalipay.util.AlipayConfig;


/**
 * @author shang
 * 
 */
@Controller
@RequestMapping("/atalipay")
public class XmlSvrController extends MultiActionController  {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSvrController.class);

//    @Resource(name="SI_PAY3001")
//    IDipperHandler<Message> SI_PAY3001;
    
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        byte[] resPayload = null; // 响应报文
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
            StringBuffer context = new StringBuffer(new String(payload, charset));
            LOGGER.info("----------支付宝返回报文："+context.toString());
            if(StringUtils.isBlank(context==null?"":context.toString())){
            	LOGGER.error("通知结果为空!!!");
            	return;
            }
            String[] dataStr=context.toString().split("&");
            Map<String,String> body=new HashMap<String,String>();
            for(int i=0;i<dataStr.length;i++){
                body.put(dataStr[i].split("=")[0].trim(), dataStr[i].split("=")[1]==null?"":URLDecoder.decode(dataStr[i].split("=")[1].trim(), charset));
            }
            
            boolean rsaCheckV1 = AlipaySignature.rsaCheckV1(body, AlipayConfig.publicKey, charset, body.get("sign_type"));
            if(!rsaCheckV1){
                LOGGER.info("-------------验签错误-------------");
            }
            //退款通知不处理，之间返回成功！
            if(!body.containsKey("gmt_refund")){
            	String transCode = "SI_PAY2023";
            	body.put("transCode", transCode);
            	body.put("routeCode", "0005");
            	body.put("tradeNo", body.get("trade_no"));
            	body.put("outTradeNo", body.get("out_trade_no"));
            	body.put("buyerId", body.get("buyer_id"));
            	body.put("tradeStatus", body.get("trade_status"));
            	body.put("totalAmount", body.get("total_amount"));
            	body.put("gmtPayment", body.get("gmt_payment"));
            	LOGGER.info("---------交易参数信息："+body);
            	Map<String, Object> headers = new HashMap<String, Object>();
            	Message m =
            			MessageFactory.create(IdGenerateFactory.generateId(),
            					MessageFactory.createSimpleMessage(headers, body), FaultFactory.create(
            							com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS, "success"));
            	@SuppressWarnings("unchecked")
            	IDipperHandler<Message> httpsvrFlowHandler =
            	Bootstrap.getApplicationContext().getBean(transCode, IDipperHandler.class);
            	m = httpsvrFlowHandler.handle(m);
            	String code = m.getFault().getCode();
            	LOGGER.info("---------处理响应码："+code);
            	if(CommonConstants_GNR.TRANS_SUCCESS.equals(code)){
            		resPayload = "success".getBytes();
            	}
            }else{
            	LOGGER.info("---------退款回调不处理！-----------");
            	resPayload = "success".getBytes();
            }

        } catch (Exception e) {
            LOGGER.error("Error: ", e);

            if (e.getMessage() != null) {
                resPayload = e.getMessage().getBytes();
            } else {
                resPayload = "System Error !".getBytes();
            }
        } finally {

            response.setCharacterEncoding(charset);
            response.setContentType("text/xml");
            response.setDateHeader("Expires", 0);
            OutputStream out = null;
            try {
                out = response.getOutputStream();
                if (resPayload != null) {
                    LOGGER.info("response[{}] charset:[{}]", new String(resPayload, charset));
                    out.write(resPayload);
                }
                out.flush();
            } catch (IOException e) {
                LOGGER.error("Error: ", e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }

        }
    }
    
    public static void main(String[] args) {
		System.out.println("aa,a".indexOf(","));
	}

}
