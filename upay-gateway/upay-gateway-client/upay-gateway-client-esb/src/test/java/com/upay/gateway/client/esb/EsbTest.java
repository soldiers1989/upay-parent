package com.upay.gateway.client.esb;

import java.text.ParseException;
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


public class EsbTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath*:META-INF/spring/**/*.xml",
                    "classpath*:META-INF/beans/*.xml", "classpath*:META-INF/comm/*.xml",
                    "classpath*:META-INF/flow/*.xml", "classpath*:META-INF/dubbo/*.xml");
        String channel = "esbcli";
        final String charsetName = "UTF-8";
        @SuppressWarnings("unchecked")
        IDipperHandler<Message> handler = (IDipperHandler<Message>) ctx.getBean("esbCliDipperHandler");

        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", charsetName,
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        "交易成功"));
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
        
        // 借记卡记账冲正或撤销 818888  y);
//        chargeReversalCredit(body);
        
        // 借记卡客户信息验证 180028
//         custCheckCredit(body);
        
//      // 中间账户查询 990001 ---
      interAccCheck(body);
        
        
        // 借记卡单笔记账820711--测试OK 
//      accCount(body);
     
     
     // 借记卡单笔退货801052--测试OK 
//       accCount1(body);

        // 单笔交易实时查询 801006
//         singleQueryCheck(body);
        

        // 核心对账文件 801015---对账OK
//         coreCheck(body);
        
  


//         贷记卡记账 08011
//         accCountCredit(body);
//        
//         // 贷记卡冲正 08013
//        chargeReversalCredit1(body);
 
        // 贷记卡客户信息查询 08014
//        custCheckCredit1(body);
        
        // 贷记卡交易状态查询 08003
//       singleQueryCheck1(body);
        
        
        //他行贷款文件回盘通知  
//        dkNotify(body);
        
        
        //他行贷款还款状态更新
//        dkNotifyUpdate(body);
        
        
        // 外围特殊贷款冲正 818899
//        chargetoReversalCredit(body);        
        
      
      //外围前置系统记帐交易801001 
       
//        prepositionAccountTrade(body);   
       // smsSend(body);
        m = handler.handle(m);
        System.out.println(m);

    }
    /* 
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDhhMMssmmm");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 借记卡记账 trantype --1-支付；2-退货；3-提现 ；4-内转内
     * 
     * @param body
     */
    public static void accCount(Map<String, Object> body) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
//        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date date = new Date();
        body.put("transCode", "820711");
        body.put("svcCd", "30110002");
        body.put("svcScn", "01");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("Flg4", "0");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
//        System.out.println(dateFormat1.format(date));
        body.put("bizSerialNo", "UPP0000120180305183055320041039995");
        body.put("ntcDrawDt", dateFormat.format(date));
        body.put("dsc2", "UPP0000120180305183055320041039995");
        // body.put("transCode", "08001"); // 交易码
//        body.put("bankCardNo", "5324019901301000058699"); // 卡号
        body.put("bankCardNo", "6229807711600013706"); // 付款方
        body.put("bankCardName", "许霞"); 
        body.put("PyrAcctFlg", "4"); 
        
        body.put("setAccount", "1010001300119000001"); //收款方（二类账户）
        body.put("setAccountName", "李四"); //收款方（二类账户）
        body.put("PyeAcctFlg", "2"); 
//        body.put("setAccount", "1019911010320845"); //收款方（二类账户）
        body.put("currency", "01"); // 币种
        body.put("fileFlg", "0");
                                   // 内转内是为待清算账户转向资金池账户
        body.put("amount", "0.01"); // 交易金额
        // body.put("charge", "0"); //手续费
//        body.put("setAccount", "1010001300119000002"); // 内部账户--提现为资金池账户1019901201123001000--支付、退货为待清算账户1010001300119000001
        // body.put("orgBizDate", dateFormat.format(date)); // 原交易日期
        // body.put("orgBizSerialNo", "2016110101234567890001"); // 原交易流水
    }

    
    /**
     * 外围前置系统记帐交易801001 
     * 
     * @param body
     */
    public static void prepositionAccountTrade(Map<String, Object> body) {
    	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
          SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
          Date date = new Date();
          body.put("transCode", "801001");
          body.put("svcCd", "30110001");
          body.put("svcScn", "15");
          body.put("machineDate", dateFormat.format(date));
          body.put("machineTime", timeFormat.format(date));
          body.put("channelId", "74");
          body.put("fileFlg", "0");
          body.put("transSubSeq", "UPP20180903000011701773");
          body.put("bankCardNo", "1010001300118000001");
          body.put("ccy", "01");
          body.put("acctNoDataSrcFlg", "2"); 
          
          body.put("setAccount", "1010001300119000001");
          body.put("AcctNoSrc", "2"); 
          
          body.put("amount", "120.00"); 
          body.put("wthdwMd", "0"); 
    }
    
    
    /**
     * 退货
     * 
     * @param body
     */
    public static void accCount1(Map<String, Object> body) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
//        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date date = new Date();
        body.put("transCode", "801052");
        body.put("svcCd", "30110001");
        body.put("svcScn", "03");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
//        System.out.println(dateFormat1.format(date));
        body.put("bizSerialNo", "UPP0000120180305183055320041039996");
        body.put("origTxnSeqNo", "UPP0000120180305183055320041039995");
        body.put("origFrontTxnDt", "20180307");
        body.put("cnclFlg", "1");
        // body.put("transCode", "08001"); // 交易码d
//        body.put("bankCardNo", "5324019901301000058699"); // 卡号
        //借方
        body.put("dbAcctNo", "1010001300119000001");
        body.put("dbCcy", "01"); 
        body.put("txnAmt", "15"); 
        body.put("dbAcctNoSrcFlg", "2"); 
        
        //贷方
        body.put("crAcctNo", "6229807711600013706"); 
        body.put("crCcy", "01");
        body.put("crTxnAmt", "15"); 
        body.put("crAcctNoSrcFlg", "4"); 

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
        body.put("transCode", "990001");
        body.put("svcCd", "30130001");
        body.put("svcScn", "03");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizSerialNo", "UPP0000120180305183055320000000026");
        body.put("AcctChkFlg", "11111111111111");//CMS:卡 DPS:账号 GLS:内部账 LNS:贷款账号
        body.put("fileFlg", "0");
        body.put("channelId", "74");
        // body.put("transCode", "08007");
        body.put("currency", "156"); // 币种
        // body.put("charge", "0"); //费用
        body.put("setAccount", "1050111000009966"); // 内部帐号----待清算账户1010001300119000001和资金池账户1019901201123001000都要
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
        body.put("tranCode", "820711");// 内部交易代码，获取模板用
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("bizDate", dateFormat.format(date));
        body.put("channelId", "74");
        body.put("bizSerialNo", "2017102401234567890019");
        // body.put("transCode", "08008");// 交易码
        body.put("bankCardNo", "6229807711600013706"); // 银行卡号bankCardNo

    }


    /**
     * 单笔交易实时查询 801006
     * 
     * @param body
     */
    public static void singleQueryCheck(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "801006");
        body.put("svcCd", "30150004");
        body.put("svcScn", "02");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
//        body.put("bizSerialNo", "UPP0000120180305183055320041165433");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
//        System.out.println(dateFormat1.format(date));
        body.put("qryTp", "1");
        body.put("origTxnDt", "20180903");
        body.put("origTxnSeqNo", "UPP20180903000011701773");
        
    }


    /**
     * 核心对账文件 801015
     * 
     * @param body
     */
    public static void coreCheck(Map<String, Object> body) {
    	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
          SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
          Date date = new Date();
          body.put("transCode", "801015");
          body.put("svcCd", "30150004");
          body.put("svcScn", "01");
          body.put("machineDate", dateFormat.format(date));
          body.put("machineTime", timeFormat.format(date));
          body.put("channelId", "74");
          body.put("fileFlg", "0");
          body.put("strtRcnclDt", "20180828");
          body.put("endRcnclDt", "20180828");
          

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
        body.put("transCode", "08011");// 内部交易代码，获取模板用
        body.put("svcCd", "30310003");
        body.put("svcScn", "04");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("transSubSeq", "UPP0000120180904102612356");
        body.put("bsnSeqNo", "UPP0000120180904102756325");
        body.put("bsnDt", dateFormat.format(date));
        // body.put("transCode", "08001"); // 交易码
        body.put("bankCardNo", "6283511600005236"); // 卡号
        body.put("currency", "156"); // 币种
        body.put("trantype", "1"); // 交易类型 1-支付；2-退货；
        body.put("amount", "18"); // 交易金额
        // body.put("charge", "0"); //手续费
        body.put("setAccount", "1010001300119000001"); // 支付、退货为待清算账户1010001300119000001
        body.put("cvv2", "783");
        body.put("validdate", "202712");
//         body.put("orgBizDate", dateFormat.format(date)); // 原交易日期
//         body.put("orgBizSerialNo", "UPP0000120180305183055320041140444"); // 原交易流水
    }


    /**
     * 贷记卡记账冲正 818888
     * 
     * @param body
     */
    public static void chargeReversalCredit(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "818888");
        body.put("svcCd", "30140001");
        body.put("svcScn", "01");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("transSubSeq", "UPP20180903000011701774");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
//        System.out.println(dateFormat1.format(date));
        body.put("bizSerialNo", "UPP000012018090318305532001100010003");

        body.put("origTxnDt", "20180903"); //原交易日期
        body.put("origTxnSeqNo", "UPP20180903000011701773"); //原业务流水号 取后6位
        body.put("rvrsFlg", "2"); //1 撤销；2 冲正 


    }

    
    /**
     * 贷记卡记账冲正 818899
     * 
     * @param body
     */
    public static void chargetoReversalCredit(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "818899");
        body.put("svcCd", "30140001");
        body.put("svcScn", "03");
        body.put("machineDate", timeFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("transSubSeq", "UPP20180903000011701775");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
//        System.out.println(dateFormat1.format(date));

        body.put("OrigDate", "20180903"); //原交易日期
        body.put("OrigDevStan", "UPP20180903000011701773"); //原业务流水号 取后6位


    }
    

    /**
     * 借记卡客户信息验证 180028
     * 
     * @param body
     */
    public static void custCheckCredit(Map<String, Object> body) {
           body.put("transCode", "180028");
           body.put("svcCd", "20130001");
           body.put("svcScn", "11");

           body.put("bizSerialNo", "UPP0000120180305183055320000000036");
           body.put("IdentTp", "01");
           body.put("IdentNo", "530421198608140321");


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
     * 贷记卡记账冲正 08013
     * 
     * @param body
     */
    public static void chargeReversalCredit1(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "08013");// 内部交易代码，获取模板用
        body.put("svcCd", "30340002");
        body.put("svcScn", "06");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("bizSerialNo", "UPP0000120180904102756326");
        body.put("bsnSeqNo", "UPP0000120180904102756325");
        body.put("bsnDt", dateFormat.format(date));
        // body.put("transCode", "08002"); // 交易码
        body.put("bankCardNo", "6283511600005236"); // 银行卡号
        body.put("bankCardNoMd5", "055"); // 银行卡号校验值
        body.put("setAccount", "1010001300119000001"); // 内部账号
        body.put("currency", "156"); // 货币代码
        body.put("amount", "18"); // 交易金额
        body.put("orgBizDate", dateFormat.format(date)); // 原正交易前置业务日期
        body.put("orgBizSerialNo", "UPP0000120180904102756325"); // 原正交易流水号
        // body.put("charge", "0"); //手续费

    }
    /**
     * 贷记卡客户信息验证 08014
     * 
     * @param body
     */
    public static void custCheckCredit1(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "08013");// 内部交易代码，获取模板用
        body.put("svcCd", "30330005");
        body.put("svcScn", "03");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("transSubSeq", "UPP0000120180305183055320041040110");
        // body.put("transCode", "08009");// 交易码
        body.put("accountName", "戴一二");
        body.put("CVV2", "176");
        body.put("EXPIREDATE", "202812");
        body.put("bankCardNo", "6283511600005236"); // 银行卡号
        body.put("certType", "01"); // 证件类型 01-身份证
        body.put("certNo", "532425197409171865");// 证件号
        body.put("mobilePhone", "17700000001");// 电话
        body.put("isCheckmobilePhone", "Y");
        body.put("isCheckAccountName", "Y");
        body.put("isCheckCertificateNo", "Y");

    }

    
    /**
     * 单笔交易实时查询 08003
     * 
     * @param body
     */
    public static void singleQueryCheck1(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "08003");// 内部交易代码，获取模板用
        body.put("svcCd", "30330007");
        body.put("svcScn", "03");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
        body.put("channelId", "74");
        body.put("fileFlg", "0");
        body.put("bizSerialNo", "UPP0000120180305183055320041165443");
        
        
        // body.put("transCode", "08003");// 交易码
        body.put("orgBizDate", dateFormat.format(date)); // 原正交易业务日期
        body.put("orgBizSerialNo", "UPP0000120180305183055320041165442"); // 原正交易流水号

    }
    
    
    
    /**
     * 他行贷款文件回盘通知 471012
     * 
     * @param body
     */
    public static void dkNotify(Map<String, Object> body) {
    	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
          SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
          Date date = new Date();
          body.put("transCode", "471012");
          body.put("svcCd", "30250003");
          body.put("svcScn", "01");
          body.put("machineDate", dateFormat.format(date));
          body.put("machineTime", timeFormat.format(date));
          body.put("channelId", "74");
          body.put("fileFlg", "0");
          body.put("FileName", "");
          

    }
    
    /**
     * 他行贷款还款状态更新 471011
     * 
     * @param body
     */
    public static void dkNotifyUpdate(Map<String, Object> body) {
    	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
          SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
          Date date = new Date();
          body.put("transCode", "471011");
          body.put("svcCd", "30220010");
          body.put("svcScn", "01");
          body.put("machineDate", dateFormat.format(date));
          body.put("machineTime", timeFormat.format(date));
          body.put("channelId", "74");
          body.put("fileFlg", "0");
          
          body.put("TranDate1", "20180525");
          body.put("SndSeqNo1", "20180525180435");
          body.put("OrdrNo", "20180525");
          body.put("ProcStatus", "0");
          
    }
    
    /**
     * 短信发送 610006
     * 
     * @param body
     */
    public static void smsSend(Map<String, Object> body) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        body.put("transCode", "610006");// 内部交易代码，获取模板用
        body.put("svcCd", "60120001");
        body.put("svcScn", "01");
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("channelId", "74");
        // body.put("Brc", "10100");
        // body.put("Teller", "TYUPAY");
        body.put("fileFlg", "0");
        body.put("transSubSeq", "UPP0000120180315183055320041165443");
        
        
        body.put("acctNm", "admin"); // 
        body.put("pswd", "4QrcOUm6Wau+VuBX8g+IPg=="); // 
        body.put("sndTp", "0"); // 
        body.put("msgTp", "1"); //
        
        body.put("sndNo", "13888013467"); //
        body.put("sndCntnt", "测试"); //
    }
    
    
    /**
     * 记账请求参数初始化
     * 
     * @param body
     */
    public static void initAccount(Map<String, Object> body) {

    }
}
