package com.upay.gateway.client.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;


public class CoreTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        String channel = "corecli";
        final String charsetName = "UTF-8";
        @SuppressWarnings("unchecked")
        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("coreCliDipperHandler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        "交易成功"));
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
        // 对账
        // initReconciliation(body);

        // 记账
        // initAccount(body);

        // 借记卡客户信息验证 08009---错误信息 已验证（电话号码，客户名，证件号码均已测试）
        // custCheck(body);

        // 核心对账文件 08010---对账OK
//         coreCheck(body);

        // 中间账户查询 08007 ---待清算账户和资金池账户均已OK
//         interAccCheck(body);

        // 客户信息查询 08008 --测试OK
//         custMsgCheck(body);

        // 借记卡记账 08001-----1-支付；2-退货；3-提现 ；4-内转内
//         accCount(body);

        // 借记卡记账冲正 08002
        // chargeReversal(body);

        // 单笔交易实时查询 08003
         singleQueryCheck(body);

        // 贷记卡记账 08011
//         accCountCredit(body);

        // 贷记卡记账冲正 08013
//         chargeReversalCredit(body);

        // 贷记卡客户信息查询 08014
//        custCheckCredit(body);

        m = handler.handle(m);
        System.out.println(m);

    }


    /**
     * 借记卡记账 trantype --1-支付；2-退货；3-提现 ；4-内转内
     * 
     * @param body
     */
    public static void accCount(Map<String, Object> body) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08001");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
        body.put("bizSerialNo", "2018102401234567893045");

        // body.put("transCode", "08001"); // 交易码
//        body.put("bankCardNo", "5324019901301000058699"); // 卡号
        body.put("bankCardNo", "1010001300118000002"); // 付款方
        body.put("setAccount", "1019911000315138"); //收款方（二类账户）
//        body.put("setAccount", "1019911010320845"); //收款方（二类账户）
        body.put("currency", "156"); // 币种
        body.put("trantype", "9"); // 交易类型 1-支付；2-退货；3-提现 ；4-内转内  5卡到卡  6 内部户到对公账户
                                   // 内转内是为待清算账户转向资金池账户
        body.put("amount", "13"); // 交易金额
        
        body.put("thirdFlag", "1");
        body.put("thirdAccount", "6216727600000012431");
        body.put("acountName", "牛牛");
        body.put("memoCode", "1095");
//        body.put("thirdAccount", "6216727600000002433");
        // body.put("charge", "0"); //手续费
//        body.put("setAccount", "1010001300119000002"); // 内部账户--提现为资金池账户1019901201123001000--支付、退货为待清算账户1010001300119000001
        // body.put("orgBizDate", dateFormat.format(date)); // 原交易日期
        // body.put("orgBizSerialNo", "2016110101234567890001"); // 原交易流水
    }


    /**
     * 借记卡记账冲正08002
     * 
     * @param body
     */
    public static void chargeReversal(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08002");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2016090501234567890014");

        // body.put("transCode", "08002"); // 交易码
        body.put("bankCardNo", "6229807711600003194"); // 银行卡号
        body.put("bankCardNoMd5", "1567890345678"); // 银行卡号校验值
        body.put("setAccount", "1010001300119000001"); // 内部账号
        body.put("currency", "156"); // 货币代码
        body.put("amount", "100"); // 交易金额
        body.put("orgBizDate", dateFormat.format(date)); // 原正交易前置业务日期
        body.put("orgBizSerialNo", "2016090501234567890001"); // 原正交易流水号
        // body.put("charge", "0"); //手续费

    }


    /**
     * 中间账户查询 08007
     * 
     * @param body
     */
    public static void interAccCheck(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08007");// 内部交易代码，获取模板用
        body.put("bankDate", dateFormat.format(date)); // 查询业务日期
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2016090501234567890017");

        // body.put("transCode", "08007");
        body.put("currency", "156"); // 币种
        // body.put("charge", "0"); //费用
        body.put("setAccount", "1010001300119000002"); // 内部帐号----待清算账户1010001300119000001和资金池账户1019901201123001000都要
    }


    /**
     * 客户信息查询 08008
     * 
     * @param body
     */
    public static void custMsgCheck(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08008");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2017102401234567890019");
        // body.put("transCode", "08008");// 交易码
        body.put("bankCardNo", "6229807711600013706"); // 银行卡号bankCardNo

    }


    /**
     * 单笔交易实时查询 08003
     * 
     * @param body
     */
    public static void singleQueryCheck(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08003");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "20180416000011418585");
        // body.put("transCode", "08003");// 交易码
        body.put("orgBizDate", "20180415"); // 原正交易业务日期
        body.put("orgBizSerialNo", "20180416000011418383"); // 原正交易流水号

    }


    /**
     * 核心对账文件 08010
     * 
     * @param body
     */
    public static void coreCheck(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08010");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2017090501234567890023");
        // body.put("transCode", "08010");// 交易码
        body.put("bankDate", dateFormat.format(date)); // 业务日期
        body.put("bizType", "0"); // 0-借记卡快捷支付 1-贷记卡快捷支付
        body.put("setAccount", "1010001300119000001"); // 内部账户

    }


    /**
     * 借记卡客户信息验证 08009
     * 
     * @param body
     */
    public static void custCheck(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08009");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2016110201234567892011");
        // body.put("transCode", "08009");// 交易码
        body.put("accountName", "张三");
        body.put("bankCardNo", "6229807711500381179"); // 银行卡号
        body.put("certType", "01"); // 证件类型 01-身份证
        body.put("certNo", "530423198711231662");// 证件号
        body.put("mobilePhone", "13764238132");// 电话
        body.put("isCheckmobilePhone", "Y");
        body.put("isCheckAccountName", "Y");
        body.put("isCheckCertificateNo", "Y");

    }


    /**
     * 贷记卡记账 08011 trantype --1-支付；2-退货;
     * 
     * @param body
     */
    public static void accCountCredit(Map<String, Object> body) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08011");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2017110201234567893141");

        // body.put("transCode", "08001"); // 交易码
        body.put("bankCardNo", "6283511600005236"); // 卡号
        body.put("currency", "156"); // 币种
        body.put("trantype", "2"); // 交易类型 1-支付；2-退货；
        body.put("amount", "0.5"); // 交易金额
        // body.put("charge", "0"); //手续费
        body.put("setAccount", "1010001300119000001"); // 支付、退货为待清算账户1010001300119000001
        body.put("cvv2", "783");
        body.put("validdate", "202712");
         body.put("orgBizDate", dateFormat.format(date)); // 原交易日期
         body.put("orgBizSerialNo", "2017110201234567893140"); // 原交易流水
    }


    /**
     * 贷记卡记账冲正 08013
     * 
     * @param body
     */
    public static void chargeReversalCredit(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08013");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2017092801234567892719");

        // body.put("transCode", "08002"); // 交易码
        body.put("bankCardNo", "6283511600005236"); // 银行卡号
        body.put("bankCardNoMd5", "055"); // 银行卡号校验值
        body.put("setAccount", "1010001300119000001"); // 内部账号
        body.put("currency", "156"); // 货币代码
        body.put("amount", "100"); // 交易金额
        body.put("orgBizDate", dateFormat.format(date)); // 原正交易前置业务日期
        body.put("orgBizSerialNo", "2017092801234567893128"); // 原正交易流水号
        // body.put("charge", "0"); //手续费

    }


    /**
     * 贷记卡客户信息验证 08014
     * 
     * @param body
     */
    public static void custCheckCredit(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08014");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
        body.put("bizSerialNo", "2016110301234567890019");
        // body.put("transCode", "08009");// 交易码
        body.put("accountName", "戴一");
        body.put("CVV2", "176");
        body.put("EXPIREDATE", "202712");
        body.put("bankCardNo", "6283511600005236"); // 银行卡号
        body.put("certType", "01"); // 证件类型 01-身份证
        body.put("certNo", "532425197409171865");// 证件号
        body.put("mobilePhone", "17700000001");// 电话
        body.put("isCheckmobilePhone", "Y");
        body.put("isCheckAccountName", "Y");
        body.put("isCheckCertificateNo", "Y");

    }


    /**
     * 对账请求参数初始化
     * 
     * @param body
     */
    public static void initReconciliation(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("tranCode", "08001");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2016070419521002045673");
        body.put("transCode", "08001");// 交易码
        body.put("bankCardNo", "4567890345678");
        body.put("currency", "156");
        body.put("trantype", "1");
        body.put("amount", "10000");
        // body.put("charge", "0");
        body.put("setAccount", "1234567894560");
    }


    /**
     * 记账请求参数初始化
     * 
     * @param body
     */
    public static void initAccount(Map<String, Object> body) {

    }
}
