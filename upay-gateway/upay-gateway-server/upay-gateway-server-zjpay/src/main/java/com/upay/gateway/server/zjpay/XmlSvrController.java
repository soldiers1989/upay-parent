package com.upay.gateway.server.zjpay;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import payment.api.notice.Notice1118Request;
import payment.api.notice.Notice1119Request;
import payment.api.notice.Notice1119Response;
import payment.api.notice.NoticeRequest;
import payment.api.notice.NoticeResponse;
import payment.tools.util.Base64;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;


/**
 * @author hing
 * 
 */
@Controller
@RequestMapping("/zjEbankPay")
public class XmlSvrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSvrController.class);

    private static final String channelid = "zhongjinsvr";


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        
        PrintWriter out;
        try {
            out = response.getWriter();
            String xmlString  = new NoticeResponse().getMessage();
            String base64String = new String(Base64.encode(xmlString.getBytes("UTF-8")));
            out.print(base64String);
            out.flush();
            out.close();            
        } catch (IOException e) {
            LOGGER.error("返回中金结果发生错误！！！",e);
        }
        String charset = request.getCharacterEncoding(); // 字符集
        if (charset == null) {
            charset = "utf-8";
        }
        String message = request.getParameter("message");
        String signature = request.getParameter("signature");
        try {
            NoticeRequest noticeRequest = new NoticeRequest(message, signature);
//            String txcode=noticeRequest.getTxCode();
            if("1118".equals(noticeRequest.getTxCode())){                
                Notice1118Request nr = new Notice1118Request(noticeRequest.getDocument());
                String ins=nr.getInstitutionID();
                String payment=nr.getPaymentNo();
                long amount=nr.getAmount();
                int stat=nr.getStatus();
                String time=nr.getBankNotificationTime();
                int zjStat=nr.getStatus();
                
                Map<String,Object> body=new HashMap<String,Object>();
                body.put("routeSeq", payment);
                body.put("transCode", CommonConstants_GNR.TRANS_TYPE_UNIONPAY_RETURN);
                body.put("totalFee", amount+"");
                body.put("routeTransStat",stat+"" );
                body.put("timeStr", time);
                body.put("zjEbankStat", zjStat+"");
                body.put("routeCode", "0004");
                Map<String, Object> headers = new HashMap<String, Object>();
                Message m =
                        MessageFactory.create(IdGenerateFactory.generateId(),
                            MessageFactory.createSimpleMessage(headers, body), FaultFactory.create(
                                com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS, "success"));
                @SuppressWarnings("unchecked")
                IDipperHandler<Message> httpsvrFlowHandler =
                Bootstrap.getApplicationContext().getBean("SI_PAY3001", IDipperHandler.class);
                m = httpsvrFlowHandler.handle(m);
            }
        } catch (Exception e1) {
            LOGGER.error("系统错误！！！",e1);
        }
    }

}
