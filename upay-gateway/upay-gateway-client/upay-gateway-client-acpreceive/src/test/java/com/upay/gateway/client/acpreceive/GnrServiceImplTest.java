package com.upay.gateway.client.acpreceive;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.commons.util.DateUtil;
import com.upay.gateway.client.acpreceive.BaseTest;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共模块交易
 * 
 * @author freeplato
 * 
 */
public class GnrServiceImplTest extends BaseTest {
	private static final Logger LOG = LoggerFactory
			.getLogger(com.upay.gateway.client.acpreceive.GnrServiceImplTest.class);
	private IDipperHandler<Message> acpService;
	public static String encoding = "UTF-8";

	/**
	 * 5.0.0
	 */
	public static String version = "5.1.0";

	public static String backUrl = "http://yhy19950611.vicp.io:13698/service/acp/post";// 受理方和发卡方自选填写的域[O]--后台通知地址


	@After
	public void after() {
		logger.debug("===================test end!!");
	}

	
	 /**
	 * 银联代收授权
	 *
	 * @throws Exception
	 */
	 @Test
	 public void testReceiveByAcp() throws Exception {
	 Map<String, Object> contentData = new HashMap<String, Object>();
	 acpService = (IDipperHandler<Message>)
	 applicationContext.getBean("unionPayReceiveAuthorizeClientDipperHandler");
	 contentData.put("accNo", "6216261000000000018");
	 contentData.put("customerNm", "全渠道");
	 contentData.put("certifTp", "01");
	 contentData.put("certifId", "341126197709218366");
	 contentData.put("phoneNo", "13552535506");
//	 contentData.put("bizType", "000501");
//	 contentData.put("cvn2", "");
//	 contentData.put("expired", "");
	 acpService.handle(getMessage(contentData, null));
	 }
	// /**
	// * 银联代收消费
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp1() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayReceiveConsumeClientDipperHandler");
	// contentData.put("accNo", "6216261000000000018");
	// contentData.put("orderId", "20180504154023968");
	// contentData.put("txnTime", "20180504154023");
	// contentData.put("txnAmt", "3000");
	// acpService.handle(getMessage(contentData, null));
	// }

	 /**
	 * 银联代收交易查询
	 *
	 * @throws Exception
	 */
	 @Test
	 public void testReceiveByAcp2() throws Exception {

	 Map<String, Object> contentData = new HashMap<String, Object>();
	 acpService = (IDipperHandler<Message>)
	 applicationContext.getBean("unionPayReceiveTranQueryClientDipperHandler");
	 contentData.put("orderId", "20180504154023968");
	// contentData.put("txnTime", "20180504154023");
	//contentData.put("bizType", "000501");
	 acpService.handle(getMessage(contentData, null));
	 }
	//
	//
	// /**
	// * 银联代收交易撤销
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp3() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayConsumeUndoClientDipperHandler");
	// contentData.put("origQryId", "061805041508264361028");
	// contentData.put("txnAmt", "2000");
	// contentData.put("bizType", "000501");
	// acpService.handle(getMessage(contentData, null));
	// }
	//

	// /**
	// * 银联代收交易退货
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp5() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayRufundClientDipperHandler");
	// contentData.put("origQryId", "801805041540234637038");
	// contentData.put("txnAmt", "3000");
	// contentData.put("bizType", "000501");
	// acpService.handle(getMessage(contentData, null));
	// }
	//

	// /**
	// * 银联代收授权撤销
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp6() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayReceiveAuthorizeUndoClientDipperHandler");
	// contentData.put("orderId", "20180504155627032");
	// contentData.put("txnTime", "20180504155627");
	// contentData.put("accNo", "621626100000000001 8");
	// acpService.handle(getMessage(contentData, null));
	// }

	// /**
	// * 银联对账文件下载
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp6() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayFileTransferDipperHandler");
	// contentData.put("settleDate", "0119");
	// acpService.handle(getMessage(contentData, null));
	// }
//
//	 /**
//	 * 银联代付
//	 *
//	 * @throws Exception
//	 */
//	 @Test
//	 public void testReceiveByAcp7() throws Exception {
//	
//	 Map<String, Object> contentData = new HashMap<String, Object>();
//	 acpService = (IDipperHandler<Message>)
//	 applicationContext.getBean("unionPayCollectionClientDipperHandler");
//	 contentData.put("accNo", "6216261000000000018");
//	 contentData.put("certifTp", "01");
//	 contentData.put("certifId", "341126197709218366");
//	 contentData.put("transSubSeq", "20180521150236");
//	 contentData.put("txnAmt", "2000");
//	 acpService.handle(getMessage(contentData, null));
//	 }
	//

	// /**
	// * 银联代付交易查询
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp8() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayTranQueryClientDipperHandler");
	// contentData.put("orderId", "20180514164148261");
	// contentData.put("txnTime", "20180514234148");
	// contentData.put("bizType", "000401");
	// acpService.handle(getMessage(contentData, null));
	// }

	 //
//	 /**
//	 * 银联无跳转授权
//	 *
//	 * @throws Exception
//	 */
//	 @Test
//	 public void testReceiveByAcp9() throws Exception {
//	
//	 Map<String, Object> contentData = new HashMap<String, Object>();
//	 acpService = (IDipperHandler<Message>)
//	 applicationContext.getBean("unionPayTokenOpenClientDipperHandler");
//	 contentData.put("phone", "13552535506");
//	 contentData.put("cvn2", "123");
//	 contentData.put("expired", "2311");
//	 contentData.put("bindacctno", "6221558812340000");
//	 acpService.handle(getMessage(contentData, null));
//	 }

//	 //
//	 /**
//	 * 银联无跳转授权更新
//	 *
//	 * @throws Exception
//	 */
//	 @Test
//	 public void testReceiveByAcp9() throws Exception {
//	
//	 Map<String, Object> contentData = new HashMap<String, Object>();
//	 acpService = (IDipperHandler<Message>)
//	 applicationContext.getBean("unionPayTokenUpdateClientDipperHandler");
//	 contentData.put("phone", "13552535506");
//	 contentData.put("cvn2", "123");
//	 contentData.put("expired", "2311");
//	 contentData.put("bindacctno", "6221558812340000");
//	 contentData.put("token", "6251640001508288");
//	 acpService.handle(getMessage(contentData, null));
//	 }

//	/**
//	 * 银联无跳转授权删除
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testReceiveByAcp12() throws Exception {
//
//		Map<String, Object> contentData = new HashMap<String, Object>();
//		acpService = (IDipperHandler<Message>) applicationContext
//				.getBean("unionPayTokenDeleteClientDipperHandler");
//		contentData.put("token", "6251640001507215");
//		acpService.handle(getMessage(contentData, null));
//	}
	 


	// /**
	// * 银联无跳转支付授权查询
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp11() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayTokenQueryClientDipperHandler");
	// contentData.put("orderId", "20180516164128183");
	// contentData.put("txnTime", "20180516164128");
	// acpService.handle(getMessage(contentData, null));
	// }

	// /**
	// * 银联无跳转消费
	// *
	// * @throws Exception
	// */
	// @Test
	// public void testReceiveByAcp9() throws Exception {
	//
	// Map<String, Object> contentData = new HashMap<String, Object>();
	// acpService = (IDipperHandler<Message>)
	// applicationContext.getBean("unionPayTokenConsumeClientDipperHandler");
	// contentData.put("token", "6251640001520275");
	// contentData.put("txnAmt", "1230");
	// contentData.put("transSubSeq", "20181206000011391464");
	// contentData.put("smsCode", "111111");
	// acpService.handle(getMessage(contentData, null));
	// }
	//
	 
//		 /**
//		 * 银联无跳转消费查询
//		 *
//		 * @throws Exception
//		 */
//		 @Test
//		 public void testReceiveByAcp9() throws Exception {
//		Map<String, Object> contentData = new HashMap<String, Object>();
//		acpService = (IDipperHandler<Message>) applicationContext
//				.getBean("unionPayTranQueryClientDipperHandler");
//		contentData.put("orderId", "20180514164148261");
//		contentData.put("txnTime", "20180514234148");
//		contentData.put("bizType", "000902");
//		acpService.handle(getMessage(contentData, null));
//		 }
		
//	 /**
//	 * 银联无跳转消费退货
//	 *
//	 * @throws Exception
//	 */
//	 @Test
//	 public void testReceiveByAcp9() throws Exception {
//	Map<String, Object> contentData = new HashMap<String, Object>();
//	 acpService = (IDipperHandler<Message>)
//	 applicationContext.getBean("unionPayRufundClientDipperHandler");
//	 contentData.put("origQryId", "801805041540234637038");
//	 contentData.put("txnAmt", "3000");
//	 contentData.put("bizType", "000902");
//	 acpService.handle(getMessage(contentData, null));
//	 }
//	
	
//	 /**
//	 * 银联无跳转消费撤销
//	 *
//	 * @throws Exception
//	 */
//	 @Test
//	 public void testReceiveByAcp9() throws Exception {
//		Map<String, Object> contentData = new HashMap<String, Object>();
//		acpService = (IDipperHandler<Message>) applicationContext
//				.getBean("unionPayConsumeUndoClientDipperHandler");
//		contentData.put("origQryId", "061805041508264361028");
//		contentData.put("txnAmt", "2000");
//		contentData.put("bizType", "000902");
//		acpService.handle(getMessage(contentData, null));
//	 }
	 /**
	     * 银联实名认证
	     * 
	     * @throws Exception
	     */

	    @Test
	    public void testForm72() throws Exception {
	    	Map<String, Object> contentData = new HashMap<String, Object>();

	    	contentData.put("bizType", "000803");// M
			contentData.put("accNo", "6225682141000002950");// O
			// 　
			contentData.put("certifTp", "01");// 证件类型
			contentData.put("certifId", "630105198506020131");// 证件号码
			contentData.put("customerName", "陶燕");// 姓名
			contentData.put("phoneNo", "18977771111"); // 手机号
//			contentData.put("smsCode", "123311");// 短信验证码
//			contentData.put("pin", "123123");// 持卡人密码
//			contentData.put("cvn2", "400");// cvn2
//			contentData.put("expired", "1212");// 有效期

			acpService.handle(getMessage(contentData, null));
	    }	
		public static String getOrderId() {
			return DateUtil.format(new Date(), "yyyyMMddHHmmss");
		}
}
