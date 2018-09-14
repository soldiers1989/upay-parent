package com.upay.gateway.client.acp.test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;
import com.upay.gateway.client.acp.run.Startup;
import com.upay.gateway.client.acp.service.AbstractAcpClientDipperHandler;


/**
 * 公共模块交易
 * 
 * @author freeplato
 * 
 */
public class GnrServiceImplTest extends BaseTest {
	private static final Logger LOG = LoggerFactory.getLogger(GnrServiceImplTest.class);
    private IDipperHandler<Message> acpService;
    public static String encoding = "UTF-8";

    /**
     * 5.0.0
     */
    public static String version = "5.0.0";

    public static String backUrl = "http://yhy19950611.vicp.io:13698/service/acp/post";// 受理方和发卡方自选填写的域[O]--后台通知地址


    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        logger.debug("===================test start!!");


    }


    @After
    public void after() {
        logger.debug("===================test end!!");
    }


    /**
     * 银联代收
     * 
     * @throws Exception
     */
//    @Test
//    public void testReceiveByAcp() throws Exception {
//        String merId = "802290049000180";
//        Date txnTime = new Date();// --订单发送时间
//        String orderId = "201408201508395217";// --商户订单号
//
//        Map<String, Object> contentData = new HashMap<String, Object>();
//        // 固定填写
//        contentData.put("version", version);// M
//
//        // 默认取值：UTF-8
//        contentData.put("encoding", encoding);// M
//
//        // 01RSA02 MD5 (暂不支持)
//        contentData.put("signMethod", "01");// M
//
//        // 取值：11
//        contentData.put("tranCode", "11");// M
//
//        // 默认：00
//        contentData.put("txnSubType", "00");// M
//
//        contentData.put("bizType", "000401");// M//000401 代付
//
//        contentData.put("channelType", "07");// M
//
//        // 交易后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
//        contentData.put("backUrl", backUrl);// M
//
//        // 0：普通商户直连接入2：平台类商户接入
//        contentData.put("accessType", "0");// M
//
//        // 交易
//        contentData.put("merId", merId);// M
//
//        // 交易
//        contentData.put("orderId", orderId);// M
//
//        // 交易
//        contentData.put("txnTime", txnTime);// M
//
//        // 非绑定类交易时需上送卡号
//        contentData.put("accNo", "123455678");// M
//
//        // 　
//        contentData.put("txnAmt", new BigDecimal("100"));// M
//
//        // 默认为156交易，填写参考公参
//        contentData.put("currencyCode", "156");// M
//
//        acpService.handle(getMessage(contentData, null));
//    }


    /**
     * 银联代付
     * 
     * @throws Exception
     */

//    @Test
//    public void testForm12() throws Exception {
//        String merId = "802290049000180";
//        Date txnTime = new Date();// --订单发送时间
//        BigDecimal txnAmt = new BigDecimal("999.00"); // --交易金额
//        String orderId = "201408201508395217";// --商户订单号
//
//        Map<String, Object> contentData = new HashMap<String, Object>();
//
//        // 固定填写
//        contentData.put("version", version);// M
//
//        // 默认取值：UTF-8
//        contentData.put("encoding", encoding);// M
//
//        // //通过MPI插件获取
//        // contentData.put("certId", certId);//M
//        //
//        // //填写对报文摘要的签名
//        // contentData.put("signature", signature);//M
//
//        // 01RSA02 MD5 (暂不支持)
//        contentData.put("signMethod", "01");// M
//
//        // 取值：12
//        contentData.put("tranCode", "12");// M
//
//        // 默认：00
//        contentData.put("txnSubType", "00");// M
//
//        contentData.put("bizType", "000401");// M 代付000401
//
//        contentData.put("channelType", "07");// M
//
//        contentData.put("customerName", "全渠道");// M
//
//        // 交易后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
//        contentData.put("backUrl", backUrl);// M
//
//        // 0：普通商户直连接入2：平台类商户接入
//        contentData.put("accessType", "0");// M
//
//        // 交易
//        contentData.put("merId", merId);// M
//        //
//        // //商户类型为平台类商户接入时必须上送
//        // contentData.put("subMerId", subMerId);//C
//        //
//        // //商户类型为平台类商户接入时必须上送
//        // contentData.put("subMerName", subMerName);//C
//        //
//        // //商户类型为平台类商户接入时必须上送
//        // contentData.put("subMerAbbr", subMerAbbr);//C
//
//        // 交易
//        contentData.put("orderId", orderId);// M
//
//        // 交易
//        contentData.put("txnTime", txnTime);// M
//
//        // //　01：银行卡02：存折03：IC卡帐号类型(卡介质)
//        //
//        // contentData.put("accType", accType);//O
//
//        // 非绑定类交易时需上送卡号
//        contentData.put("accNo", "6216261000000000018");// M
//
//        // 　
//        contentData.put("txnAmt", txnAmt);// M
//
//        // 默认为156交易，填写参考公参
//        contentData.put("currencyCode", "156");// M
//
//        // //　
//        // contentData.put("customerInfo", getCustomer(encoding));//O
//        //
//        // //用于唯一标识绑定关系
//        // contentData.put("bindId", bindId);//O
//        //
//        // contentData.put("billType", billType);//O
//        //
//        // //账单查询/支付类交易中填写具体账单号码用法一：账单查询/支付类交易中网上缴税用法，填写纳税人编码用法二：账单查询/支付类交易中信用卡还款用法，填写信用卡卡号
//        // contentData.put("billNo", billNo);//O
//        //
//        // //格式为：yyyyMMdd-yyyyMMdd
//        // contentData.put("billPeriod", billPeriod);//O
//        //
//        // //商户自定义保留域，交易应答时会原样返回
//        // contentData.put("reqReserved", reqReserved);//O
//        //
//        // //格式如下：{子域名1=值&子域名2=值&子域名3=值} 移动支付参考消费
//        // contentData.put("reserved", reserved);//O
//        //
//        // //格式如下：{子域名1=值&子域名2=值&子域名3=值}有风险级别要求的商户必填 风险级别 {riskLevel=XX}
//        // contentData.put("riskRateInfo", riskRateInfo);//O
//        //
//        // //当使用银联公钥加密密码等信息时，需上送加密证书的CertID
//        // contentData.put("encryptCertId", encryptCertId);//C
//        //
//        // //　
//        // contentData.put("termId", termId);//O
//        //
//        // //有卡交易必填
//        // contentData.put("cardTransData", cardTransData);//C
//
//        acpService.handle(getMessage(contentData, null));
//    }


//    /**
//     * 银联交易状态查询
//     * 
//     * @throws Exception
//     */
//
    @Test
    public void testQueryByAcp() throws Exception {

    	acpService = (IDipperHandler<Message>) applicationContext.getBean("SA_PMT_UnionPayTranStatQuery");
        Map<String, Object> contentData = new HashMap<String, Object>();
//        contentData.put("reqType", "0350000903");
        contentData.put("orderNo", "20180123000011332260");
        contentData.put("orderTime", "20180123135801");
        contentData.put("voucherNum", "20180123457755699472");
        contentData.put("reqType", "0350000903");
        acpService.handle(getMessage(contentData, null));
    }


//    /**
//     * 银联消费撤销类交易
//     * 
//     * @throws Exception
//     */

//    @Test
//    public void testForm31() throws Exception {
//    	acpService = (IDipperHandler) applicationContext.getBean("SA_PMT_UnionRufund");
//        Map<String, Object> contentData = new HashMap<String, Object>();
//
//        String merId = "802290049000180";
//        String txnTime = "20140920145625";// --订单发送时间
//        String orderId = "201409201508395217";// --商户订单号
//
//        String origQryId = "201409201508395217";
//        // 固定填写
//        contentData.put("version", version);// M
//
//        // 默认取值：UTF-8
//        contentData.put("encoding", encoding);// M
//
//        // //通过MPI插件获取
//        // contentData.put("certId", certId);//M
//
//        // 填写对报文摘要的签名
//        // contentData.put("signature", signature);//M
//
//        // 01RSA02 MD5 (暂不支持)
//        contentData.put("signMethod", "01");// M
//
//        // 取值：31
//        contentData.put("txnType", "31");// M
//
//        // 默认:00
//        contentData.put("txnSubType", "00");// M
//
//        contentData.put("bizType", "00");// M
//
//        contentData.put("channelType", "07");// M
//
//        // 后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
//        contentData.put("backUrl", backUrl);// M
//
//        // 0：普通商户直连接入2：平台类商户接入
//        contentData.put("accessType", "0");// M
//
//        // 　
//        contentData.put("merId", merId);// M
//
//        // 商户类型为平台类商户接入时必须上送
//        contentData.put("subMerId", "");// C
//
//        // 商户类型为平台类商户接入时必须上送
//        contentData.put("subMerName", "");// C
//
//        // 商户类型为平台类商户接入时必须上送
//        contentData.put("subMerAbbr", "");// C
//
//        // 消费撤销的订单号，由商户生成
//        contentData.put("orderId", orderId);// M
//
//        // 原始交易的queryId
//        contentData.put("origQryId", origQryId);// M
//
//        // 　
//        contentData.put("txnTime", txnTime);// M
//
//        // 与原消费交易一致
//        contentData.put("txnAmt", "1");// M
//
//        // 　
//        contentData.put("termId", "");// O
//
//        // 商户自定义保留域，交易应答时会原样返回
//        contentData.put("reqReserved", "");// O
//
//        // 格式如下：{子域名1=值&子域名2=值&子域名3=值} 移动支付参考消费
//        contentData.put("reserved", "");// O
//
//        // 渠道类型为语音支付时使用用法见VPC交易信息组合域子域用法
//        contentData.put("vpcTransData", "");// C
//
//        acpService.handle(getMessage(contentData, null));
//    }


    /**
     * 银联实名认证
     * 
     * @throws Exception
     */

//    @Test
//    public void testForm72() throws Exception {
//        Map<String, Object> contentData = new HashMap<String, Object>();
//
//        String merId = "802290049000180";
//        // String txnTime = "20140820145625";// --订单发送时间
//        String orderId = "201508201508395219";// --商户订单号
//        Date txnTime = new Date();// --订单发送时间
//        // 固定填写
//        contentData.put("version", version);// M
//
//        // 默认取值：UTF-8
//        contentData.put("encoding", encoding);// M
//
//        // //通过MPI插件获取
//        // contentData.put("certId", certId);//M
//        //
//        // //填写对报文摘要的签名
//        // contentData.put("signature", signature);//M
//
//        // 01：RSA02： MD5 (暂不支持)
//        contentData.put("signMethod", "01");// M
//
//        // 取值:72
//        contentData.put("tranCode", "72");// M
//
//        // 01：实名认证
//        contentData.put("txnSubType", "01");// M
//
//        contentData.put("bizType", "000501");// M
//
//        contentData.put("channelType", "07");// M
//
//        // 后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
//        contentData.put("backUrl", backUrl);// C
//
//        // 0：普通商户直连接入2：平台类商户接入
//        contentData.put("accessType", "0");// M
//
//        // 　
//        contentData.put("merId", merId);// M
//
//        // //商户类型为平台类商户接入时必须上送
//        // contentData.put("subMerId", subMerId);//C
//        //
//        // //商户类型为平台类商户接入时必须上送
//        // contentData.put("subMerName", subMerName);//C
//        //
//        // //商户类型为平台类商户接入时必须上送
//        // contentData.put("subMerAbbr", subMerAbbr);//C
//
//        // 　
//        contentData.put("orderId", orderId);// M
//
//        // 　
//        contentData.put("txnTime", txnTime);// M
//
//        contentData.put("customerName", "全渠道");// M
//        contentData.put("certifTp", "01");// M
//        contentData.put("certifId", "341126197709218366");// M
//        contentData.put("phoneNo", "13552535506");// M
//        // //　
//        // contentData.put("accType", accType);//O
//        //
//        // //对于前台类交易，返回卡号后4位，后台类交易，原样返回
//        contentData.put("accNo", "6216261000000000018");// O
//        //
//        // //　
//        // contentData.put("customerInfo", getCustomer(encoding));//O
//        //
//        // //商户自定义保留域，交易应答时会原样返回
//        // contentData.put("reqReserved", reqReserved);//O
//        //
//        // //格式如下：{子域名1=值&子域名2=值&子域名3=值} 移动支付参考消费绑定关系信息 {bindInfo=XXXXX} 特殊商户上送
//        // contentData.put("reserved", reserved);//O
//        //
//        // //格式如下：{子域名1=值&子域名2=值&子域名3=值}有风险级别要求的商户必填 风险级别 {riskLevel=XX}
//        // contentData.put("riskRateInfo", riskRateInfo);//O
//        //
//        // //当使用银联公钥加密密码等信息时，需上送加密证书的CertID
//        // contentData.put("encryptCertId", encryptCertId);//C
//        //
//        // //移动支付业务需要上送
//        // contentData.put("userMac", userMac);//O
//        //
//        // //需做建立绑定关系交易时填写
//        // contentData.put("bindId", bindId);//C
//        //
//        // //用于填写关联业务类型01：消费
//        // contentData.put("relTxnType", relTxnType);//C
//        //
//        // //　
//        // contentData.put("payCardType", payCardType);//O
//        //
//        // //　
//        // contentData.put("issInsCode", issInsCode);//O
//        //
//        // //渠道类型为语音支付时使用用法见VPC交易信息组合域子域用法
//        // contentData.put("vpcTransData", vpcTransData);//C
//        //
//
//        acpService.handle(getMessage(contentData, null));
//    }


    /**
     * 银联文件传输类交易
     * 
     * @throws Exception
     */

//    @Test
//    public void testForm76() throws Exception {
//    	acpService = (IDipperHandler) applicationContext.getBean("defaultAcpPayClientDipperHandler");
//        Map<String, Object> contentData = new HashMap<String, Object>();
//        String merId = "700000000000001";
//        Date txnTime = new Date();// --订单发送时间
//
//        String settleDate = "0119";
//        // Date settleDate = new Date("0616");
//
//        String fileType = "00";
//
//        // 固定填写
//        contentData.put("version", version);// M
//
//        // 默认取值：UTF-8
//        contentData.put("encoding", encoding);// M
//
//        // //通过SDK插件获取
//        // contentData.put("certId", certId);//M
//        //
//        // //填写对报文摘要的签名
//        // contentData.put("signature", signature);//M
//
//        // 01RSA02 MD5 (暂不支持)
//        contentData.put("signMethod", "01");// M
//
//        // 取值:76
//        contentData.put("tranCode", "76");// M
//
//        // 01：对账文件下载
//        contentData.put("txnSubType", "01");// M
//
//        // 默认:000000
//        contentData.put("bizType", "000000");// M
//
//        // 0：普通商户直连接入1. 收单机构接入
//        contentData.put("accessType", "0");// M
//
//        // //商户类型为收单接入时必须上送
//        // contentData.put("acqInsCode", acqInsCode);//C
//
//        // 　商户类型为商户接入时必须上送
//        contentData.put("merId", merId);// C
//
//        // 　
//        contentData.put("settleDate", settleDate);// M
//
//        // 　
//        contentData.put("txnTime", txnTime);// M
//
//        // 依据实际业务情况定义参考附录：商户索取的文件类型约定
//        contentData.put("fileType", fileType);// M
//
//        // //商户自定义保留域，交易应答时会原样返回
//        // contentData.put("reqReserved", reqReserved);//O
//
//        acpService.handle(getMessage(contentData, null));
//    }
//    
//    
    /**
     * 银联退货交易
     * 
     * @throws Exception
     */

//    @Test
//    public void testFormRufund() throws Exception {
//    	acpService = (IDipperHandler) applicationContext.getBean("SA_PMT_UnionRufund");
//        Map<String, Object> contentData = new HashMap<String, Object>();
//        String orderId = "20170924000011388005";// --商户订单号
//        String origQryId = "201708241052245441618";
//        // 固定填写
//        contentData.put("version", version);// M
//        // 默认取值：UTF-8
//        contentData.put("encoding", encoding);// M
        // //通过MPI插件获取

        // 填写对报文摘要的签名
        // contentData.put("signature", signature);//M
        // 01RSA02 MD5 (暂不支持)
 //       contentData.put("signMethod", "01");// M
        // 交易类型 04
     //   contentData.put("tranCode", "04");// M
        // 取值：04
    //    contentData.put("txnType", "04");// M
        // 默认:00
   //     contentData.put("txnSubType", "00");// M

 //       contentData.put("bizType", "000201");// M

    //    contentData.put("channelType", "07");// M

        // 后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
    //    contentData.put("backUrl", backUrl);// M

        // 0：普通商户直连接入2：平台类商户接入
  //      contentData.put("accessType", "0");// M

        // 　
  //      contentData.put("merId", merId);// M

//        // 消费撤销的订单号，由商户生成
//        contentData.put("orderId", orderId);// M
//
//        // 原始交易的queryId
//        contentData.put("origQryId", origQryId);// M
//
//        contentData.put("txnTime", new Date());// M
//
//        contentData.put("txnAmt", "100");// M
//
//
//        // 商户自定义保留域，交易应答时会原样返回
//  //      contentData.put("reqReserved", "");// O
//
//        acpService.handle(getMessage(contentData, null));
//    }
//    @Test
//    public void testFile() throws Exception {
//    	URL url = Startup.class.getClassLoader().getResource("acp_sdk.properties");
//        File file = new File(url.getPath());
//        SDKConfig.getConfig().loadPropertiesFromPath(file.getAbsoluteFile().getParent());
//		
//		Map<String, String> data = new HashMap<String, String>();
//		
//		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
//		data.put("version", "5.0.0");               //版本号 全渠道默认值
//		data.put("encoding", "utf-8");             //字符集编码 可以使用UTF-8,GBK两种方式
//		data.put("signMethod", "01");                        //签名方法 目前只支持01-RSA方式证书加密
//		data.put("txnType", "76");                           //交易类型 76-对账文件下载
//		data.put("txnSubType", "01");                        //交易子类型 01-对账文件下载
//		data.put("bizType", "000000");                       //业务类型，固定
//		
//		/***商户接入参数***/
//		data.put("accessType", "0");                         //接入类型，商户接入填0，不需修改
//		data.put("merId", "700000000000001");                	         //商户代码，请替换正式商户号测试，如使用的是自助化平台注册的777开头的商户号，该商户号没有权限测文件下载接口的，请使用测试参数里写的文件下载的商户号和日期测。如需777商户号的真实交易的对账文件，请使用自助化平台下载文件。
//		data.put("settleDate", "0119");                  //清算日期，如果使用正式商户号测试则要修改成自己想要获取对账文件的日期， 测试环境如果使用700000000000001商户号则固定填写0119
//		data.put("txnTime","20170912135122");       //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
//		data.put("fileType", "00");                          //文件类型，一般商户填写00即可
//		
//		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
//		
//		Map<String, String> reqData = AcpService.sign(data,"utf-8");//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
//		String urlF = "https://filedownload.test.95516.com/";//获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.fileTransUrl
//		Map<String, String> rspData =  AcpService.post(reqData,urlF,"utf-8");
//		if(!rspData.isEmpty()){
//			if(AcpService.validate(rspData, "utf-8")){
//				LOG.info("验证签名成功");
//				String respCode = rspData.get("respCode");
//				if("00".equals(respCode)){
//					//交易成功，解析返回报文中的fileContent并落地
//					AcpService.deCodeFileContent(rspData,"D:\\","utf-8");
//					//TODO
//				}else{
//					//其他应答码为失败请排查原因
//					//TODO
//				}
//			}else{
//				LOG.error("验证签名失败");
//				//TODO 检查验证签名失败的原因
//			}
//		}else{
//			//未返回正确的http状态
//			LOG.error("未获取到返回报文或返回http状态码非200");
//		}
//    }
}
