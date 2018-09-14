package com.upay.gateway.server.acppay;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;
import com.unionpay.acpmer.sdk.AcpService;
import com.unionpay.acpmer.sdk.LogUtil;
import com.unionpay.acpmer.sdk.SDKConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author shang
 */
@Controller
@RequestMapping("/acp")
public class XmlSvrController extends MultiActionController {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(XmlSvrController.class);
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> body = new HashMap<String, String>(32);
            LogUtil.writeLog("BackRcvResponse接收后台通知开始");
            String encoding = request.getParameter(SDKConstants.param_encoding);
            // 获取银联通知服务器发送的后台通知参数
            Map<String, String> reqParam = getAllRequestParam(request);

            LogUtil.printRequestLog(reqParam);
            Map<String, String> valideData = null;
            if (null != reqParam && !reqParam.isEmpty()) {
                Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
                valideData = new HashMap<>(reqParam.size());
                while (it.hasNext()) {
                    Entry<String, String> e = it.next();
                    String key = e.getKey();
                    String value = e.getValue();
                    //TODO:存在换行符导致 银联签名错误  模拟银联报文测试使用  正式测试 需要注释
//                    key=key.replace("\r\n", "");
//                    value=value.replace("\r\n", "");
                    valideData.put(key, value);
                }
            }
            //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
            if (!AcpService.validate(valideData, encoding)) {
                LogUtil.writeLog("验证签名结果[失败].");
                //验签失败，需解决验签问题
            } else {
                LogUtil.writeLog("验证签名结果[成功].");
                //交易成功，更新商户订单状态
                String customerInfo = valideData.get("customerInfo");
                if (null != customerInfo) {
                    Map<String, String> customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
                    LogUtil.writeLog("customerInfoMap明文: " + customerInfoMap);
                }
                String accNo = valideData.get("accNo");
                //如果配置了敏感信息加密证书，可以用以下方法解密
                if (null != accNo) {
                    accNo = AcpService.decryptData(accNo, "UTF-8");
                    LogUtil.writeLog("accNo明文: " + accNo);

                }

                String orderId = valideData.get("orderId");
                String queryId = valideData.get("queryId");
                String respCode = valideData.get("respCode");
                String respMsg = valideData.get("respMsg");
                String txnTime = valideData.get("txnTime");
                String txnAmt = valideData.get("txnAmt");
                //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
                body.put("orderId", orderId);
                body.put("voucherNum", queryId);
                body.put("queryId", queryId);
                body.put("routeSeq", queryId);
                body.put("respCode", respCode);
                body.put("respMsg", respMsg);
                body.put("txnTime", txnTime);
                body.put("txnAmt", txnAmt);
                body.put("transCode", CommonConstants_GNR.TRANS_TYPE_UNIONPAY_RETURN);
                body.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
                Map<String, Object> headers = new HashMap<String, Object>();
                Message m =
                        MessageFactory.create(IdGenerateFactory.generateId(),
                                MessageFactory.createSimpleMessage(headers, body), FaultFactory.create(
                                        com.pactera.dipper.core.utils.Constants.ResponseCode.SUCCESS, "success"));


                @SuppressWarnings("unchecked")
                IDipperHandler<Message> httpsvrFlowHandler =
                        Bootstrap.getApplicationContext().getBean("SI_PAY7002", IDipperHandler.class);
                m = httpsvrFlowHandler.handle(m);
                LogUtil.writeLog(m.toString());
            }
            LogUtil.writeLog("BackRcvResponse接收后台通知结束");
            //返回给银联服务器http 200状态码
            response.getWriter().print("ok");
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }
    }


    /**
     * 将request请求参数转换为map
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }


}
