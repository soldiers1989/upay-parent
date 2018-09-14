/**
 * 
 */
package com.dubhe.common.controller.zjEBank;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import payment.api.system.PaymentEnvironment;
import payment.api.tx.merchantorder.Tx1111Request;

import com.dubhe.common.constants.AcpConstants;
import com.dubhe.common.constants.ReqRspConstants;
import com.dubhe.common.controller.AbstractController;
import com.dubhe.common.controller.unionpay.AcpConsumeController;
import com.dubhe.common.properties.PropertiesUtil;
import com.dubhe.common.util.G4Utils;
import com.dubhe.common.util.StreamUtil;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.exception.FlowException;
import com.unionpay.acp.sdk.AcpService;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_PAY;

/**
 * @author shang
 * 2016年11月21日
 */
@Controller
@RequestMapping("zjEBankPay")
public class ZjpayEBannkController extends AbstractController{
    
    private static final Logger LOG = LoggerFactory.getLogger(AcpConsumeController.class);
    
    @Resource(name = "SI_AcpFrontFlow")
    private IDipperHandler<Message> commonHandler;
    
    @RequestMapping(value = "/execute")
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String charset = request.getCharacterEncoding();
        charset = (G4Utils.isEmpty(charset)) ? "utf-8" : charset;
        Map<String, Object> rspMsg = new HashMap<>();
        Message message = null;
        try{
            String json = request.getParameter("msg");
            LOG.info(">>receive:[{}]", json);
            message = MessageFactory.create(
                    IdGenerateFactory.generateId(),
                    "appcli",
                    "JSON",
                    charset,
                    MessageFactory.createSimpleMessage(new HashMap<String,Object>(), json.getBytes()),
                    FaultFactory.create(Constants.ResponseCode.SUCCESS, "SUCCESS"));
            message = commonHandler.handle(message);
            LOG.debug("Message result ： [{}]" , message);
            if(!Constants.ResponseCode.SUCCESS.equals(message.getFault().getCode())){
                throw new FlowException(message);
            }
            Map<String, Object> bodys = (Map<String, Object>) message.getTarget().getBodys();
            
            Tx1111Request tx1111Request = new Tx1111Request();
            tx1111Request.setInstitutionID(CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK);//机构编号
            tx1111Request.setPaymentNo((String) bodys.get("routeSeq"));//支付交易流水号
            tx1111Request.setAmount(Long.parseLong(bodys.get("totalFee").toString()));//订单
            tx1111Request.setFee(0);//支付服务手续费
            tx1111Request.setPayerID("");//付款人ID
            tx1111Request.setPayerName("");//付款方名称
            tx1111Request.setSettlementFlag("0001");//结算标识
            tx1111Request.setUsage("");//资金 用途
            tx1111Request.setRemark("银易付支付平台");//备注
            tx1111Request.setNotificationURL(bodys.get("callBack004").toString());//接受 支付成功通知的URL
            tx1111Request.setBankID(bodys.get("bankId")==null?"":bodys.get("bankId").toString());//付款银行标识
            int accType=bodys.get("payType")==null?11:DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(bodys.get("payType").toString())?12:11; 
            tx1111Request.setAccountType(accType);//账户类型：11个人账户，12企业账户
            tx1111Request.setCardType("");//卡类型:01借记卡，02贷记卡
            
            //执行报文处理
            tx1111Request.process();
            
            //组装参数
            Map<String, String> requestData = new HashMap<String, String>();
//            requestData.put("plainText", tx1111Request.getRequestPlainText());
            requestData.put("message", tx1111Request.getRequestMessage());
            requestData.put("signature", tx1111Request.getRequestSignature());
            requestData.put("txCode", "1111");
            requestData.put("txName", "商户订单支付（直通车）");
            String html=AcpService.createAutoFormHtml(PaymentEnvironment.paymentURL, requestData, AcpConstants.encoding_UTF8);
            
            LOG.debug("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
            
            response.getWriter().write(html);
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
        }catch (Exception ex) {
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
