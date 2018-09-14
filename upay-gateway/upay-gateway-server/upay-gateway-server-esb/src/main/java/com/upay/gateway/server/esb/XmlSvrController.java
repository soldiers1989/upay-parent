package com.upay.gateway.server.esb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;


/**
 * @author hing
 * 
 */
@Controller
@RequestMapping("/esb")
public class XmlSvrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSvrController.class);

    private static final String channelid = "esbsvr";


    @RequestMapping(value = "/esbServer", method = RequestMethod.POST)
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
            String SvcScn=context.substring(context.indexOf("<SvcScn>")+8, context.indexOf("</SvcScn>"));
            String transCode = context.substring(context.indexOf("<SvcCd>")+7, context.indexOf("</SvcCd>"))+SvcScn;
            LOGGER.debug("*********esb Server Trans Code:"+transCode);
            context=context.insert(context.indexOf("</service>"), "<tran_code>"+transCode+"</tran_code>");
            String text= context.toString().replace("<service>", "<xml>");
            text= text.toString().replace("</service>", "</xml>");
            text= text.toString().replace("<SYS_HEAD>", "");
            text= text.toString().replace("</SYS_HEAD>", "");
            text= text.toString().replace("<APP_HEAD>", "");
            text= text.toString().replace("</APP_HEAD>", "");
            text= text.toString().replace("<BODY>", "");
            text= text.toString().replace("</BODY>", "");
            
            payload = text.getBytes(charset);
            Map<String, Object> headers = new HashMap<String, Object>();
            Message m =
                    MessageFactory.create(IdGenerateFactory.generateId(), channelid, "XML", charset,
                        MessageFactory.createSimpleMessage(headers, payload), FaultFactory.create(
                            com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS, "success"));
            @SuppressWarnings("unchecked")
            IDipperHandler<Message> httpsvrFlowHandler =
                    Bootstrap.getApplicationContext().getBean("SI_httpEsbSvrFlow", IDipperHandler.class);
            m = httpsvrFlowHandler.handle(m);

            Object obj = m.getTarget().getBodys();
            if (obj != null && obj instanceof byte[]) {
                resPayload = (byte[]) obj;
            } else {
                LOGGER.error("响应结果为空 !!");
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
                    LOGGER.info("channel[{}] response:[{}]", channelid, new String(resPayload, charset));
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

}
