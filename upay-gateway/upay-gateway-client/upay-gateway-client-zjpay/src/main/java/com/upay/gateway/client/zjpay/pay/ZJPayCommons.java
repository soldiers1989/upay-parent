package com.upay.gateway.client.zjpay.pay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import payment.api.system.PaymentEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.accountvalidation.Tx2310Request;
import payment.api.tx.accountvalidation.Tx2310Response;
import payment.api.tx.accountvalidation.Tx2320Request;
import payment.api.tx.accountvalidation.Tx2320Response;
import payment.api.tx.accountvalidation.Tx2340Request;
import payment.api.tx.accountvalidation.Tx2340Response;
import payment.api.tx.bankcorp.Tx4510Request;
import payment.api.tx.bankcorp.Tx4510Response;
import payment.api.tx.bankcorp.Tx4512Request;
import payment.api.tx.bankcorp.Tx4512Response;
import payment.api.tx.bankcorp.Tx4530Request;
import payment.api.tx.bankcorp.Tx4530Response;
import payment.api.tx.bankcorp.Tx4532Request;
import payment.api.tx.bankcorp.Tx4532Response;
import payment.api.tx.bankcorp.Tx4534Request;
import payment.api.tx.bankcorp.Tx4534Response;
import payment.api.tx.bankcorp.Tx4538Request;
import payment.api.tx.bankcorp.Tx4538Response;
import payment.api.tx.gatheringbatch.Tx1610Request;
import payment.api.tx.gatheringbatch.Tx1610Response;
import payment.api.tx.gatheringbatch.Tx1620Request;
import payment.api.tx.gatheringbatch.Tx1620Response;
import payment.api.tx.gatheringbatch.Tx1630Request;
import payment.api.tx.gatheringbatch.Tx1630Response;
import payment.api.tx.gatheringbatch.Tx1650Request;
import payment.api.tx.gatheringbatch.Tx1650Response;
import payment.api.tx.merchantorder.Tx1111Request;
import payment.api.tx.merchantorder.Tx1120Request;
import payment.api.tx.merchantorder.Tx1120Response;
import payment.api.tx.merchantorder.Tx1131Request;
import payment.api.tx.merchantorder.Tx1131Response;
import payment.api.tx.merchantorder.Tx1132Request;
import payment.api.tx.merchantorder.Tx1132Response;
import payment.api.tx.merchantorder.Tx1133Request;
import payment.api.tx.merchantorder.Tx1133Response;
import payment.api.tx.paymentbatch.Tx1510Request;
import payment.api.tx.paymentbatch.Tx1510Response;
import payment.api.tx.paymentbatch.Tx1520Request;
import payment.api.tx.paymentbatch.Tx1520Response;
import payment.api.tx.paymentbatch.Tx1550Request;
import payment.api.tx.paymentbatch.Tx1550Response;
import payment.api.tx.paymentbinding.Tx2501Request;
import payment.api.tx.paymentbinding.Tx2501Response;
import payment.api.tx.paymentbinding.Tx2502Request;
import payment.api.tx.paymentbinding.Tx2502Response;
import payment.api.tx.paymentbinding.Tx2503Request;
import payment.api.tx.paymentbinding.Tx2503Response;
import payment.api.tx.paymentbinding.Tx2511Request;
import payment.api.tx.paymentbinding.Tx2511Response;
import payment.api.tx.paymentbinding.Tx2512Request;
import payment.api.tx.paymentbinding.Tx2512Response;
import payment.api.tx.paymentbinding.Tx2521Request;
import payment.api.tx.paymentbinding.Tx2521Response;
import payment.api.tx.paymentbinding.Tx2522Request;
import payment.api.tx.paymentbinding.Tx2522Response;
import payment.api.tx.paymentbinding.Tx2531Request;
import payment.api.tx.paymentbinding.Tx2531Response;
import payment.api.tx.paymentbinding.Tx2532Request;
import payment.api.tx.paymentbinding.Tx2532Response;
import payment.api.tx.paymentbinding.Tx2541Request;
import payment.api.tx.paymentbinding.Tx2541Response;
import payment.api.tx.paymentbinding.Tx2561Request;
import payment.api.tx.paymentbinding.Tx2561Response;
import payment.api.tx.realgathering.Tx2011Request;
import payment.api.tx.realgathering.Tx2011Response;
import payment.api.tx.realgathering.Tx2020Request;
import payment.api.tx.realgathering.Tx2020Response;
import payment.api.tx.statement.Tx1810Request;
import payment.api.tx.statement.Tx1810Response;
import payment.api.tx.statement.Tx1811Request;
import payment.api.tx.statement.Tx1811Response;
import payment.api.vo.BankAccount;
import payment.api.vo.GatheringItem;
import payment.api.vo.PaymentAccount;
import payment.api.vo.PaymentAccountTx;
import payment.api.vo.Tx;

import com.alibaba.dubbo.common.json.ParseException;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.Message;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.dict.AppCodeDict;


/**
 * 
 * 中金接口调用
 * 
 * @author: David
 * @CreateDate:2016年3月27日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2016年3月27日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class ZJPayCommons {

    private static final Logger LOG = LoggerFactory.getLogger(ZJPayCommons.class);


    /**
     * 2340接口处理 身份验证 四要素
     * 
     * @param targetBodyMap
     *            请求数据
     * @param message
     * @param resultMap
     *            返回数据
     * @throws Exception
     */
    public static void do2340Request(Map<String, Object> targetBodyMap, Message message,
            Map<String, Object> resultMap) throws Exception {

        LOG.info("*************do2320Request start**************");
        String institutionID = (String) targetBodyMap.get("institutionID");
        String txSN = (String) targetBodyMap.get("txSNBinding");
        String accountName = (String) targetBodyMap.get("accountName");
        String accountNumber = (String) targetBodyMap.get("accountNumber");
        String identificationNumber = (String) targetBodyMap.get("identificationNumber");
        String identificationType = (String) targetBodyMap.get("identificationType");
        String phoneNumber = (String) targetBodyMap.get("phoneNumber");
        Tx2340Request tx2340Request = new Tx2340Request();

        tx2340Request.setInstitutionID(institutionID); // 机构编号
        tx2340Request.setTxSN(txSN);// 交易流水
        tx2340Request.setAccountName(accountName);// 姓名
        tx2340Request.setAccountNumber(accountNumber);// 银行账号
        tx2340Request.setIdentificationNumber(identificationNumber);// 证件号码
        tx2340Request.setIdentificationType(identificationType);// 开户证件类型
        tx2340Request.setPhoneNumber(phoneNumber);// 手机号码

        tx2340Request.process();// 将信息组装成xml

        LOG.info("组装明文 [{}]", tx2340Request.getRequestPlainText());

        String msg = tx2340Request.getRequestMessage();
        String signature = tx2340Request.getRequestSignature();

        LOG.info("组装明文msg: [{}]", msg);
        LOG.info("组装明文signature: [{}]", signature);
        LOG.info("請求URL: [{}]", PaymentEnvironment.txURL);
        LOG.info("請求paymentConfigPathURL: [{}]", PaymentEnvironment.paymentConfigPath);
        LOG.info("請求paymentURL: [{}]", PaymentEnvironment.paymentURL);
        String[] respMessage = send(msg, signature);

        if (respMessage == null || respMessage.length == 0) {
            LOG.info("响应报文不能为空!!");
            throw new Exception("response is null!!");
        }
        Tx2340Response tx2320Response = new Tx2340Response(respMessage[0], respMessage[1]);
        Object respCode = tx2320Response.getCode(); // 响应代码
        Object respMsg = tx2320Response.getMessage(); // 响应信息
        String status = tx2320Response.getStatus();//交易状态 
        if (StringUtils.isBlank((String) respCode)) {
            LOG.info("响应码不能为空,响应报文[{}]", tx2320Response.getResponsePlainText());
            throw new Exception("respCode is null!!");
        }
        LOG.info("返回报文明文 [{}]", tx2320Response.getResponsePlainText());
        if(StringUtils.isNotBlank(status)&&CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)&&status.equals("20")) { // 返回成功
            message.getFault().setCode((String) respCode);
            message.getFault().setMsg((String) respMsg);
            resultMap.put("respCode", respCode);
            resultMap.put("respMsg", respMsg);
            resultMap.put("bankId", tx2320Response.getIssueBankID());
            resultMap.put("cardType", tx2320Response.getIssueCardType());
        }else{
        	if(StringUtils.isNotBlank(tx2320Response.getResponseCode())){
                message.getFault().setCode(tx2320Response.getResponseCode());
        	}else{
        	    message.getFault().setCode((String) respCode);
        	}
        	if(StringUtils.isNotBlank(tx2320Response.getResponseMessage())){
                message.getFault().setMsg(tx2320Response.getResponseMessage());
            }else{
            	message.getFault().setMsg((String) respMsg);
            }
            resultMap.put("respCode", message.getFault().getCode());
            resultMap.put("respMsg", message.getFault().getMsg());
            resultMap.put("bankId", "");
            resultMap.put("cardType", "");
            
        }
        targetBodyMap.putAll(resultMap);
        message.getTarget().setBody(targetBodyMap);
        LOG.debug("client receive message={}", message);
        LOG.info("*************do2320Request start**************");
    }


    /**
     * 快捷支付（一键支付，无验证短信）
     * 
     * @param targetBodyMap
     *            请求数据
     * @param message
     * @param resultMap
     *            返回数据
     * @throws Exception
     */
    public static void do2561Request(Map<String, Object> targetBodyMap, Message message,
            Map<String, Object> resultMap) throws Exception {
        LOG.info("*************快捷支付（一键支付，无验证短信） Start**************");
        String institutionID = (String) targetBodyMap.get("institutionID");// 机构号
        // String txCode = (String) targetBodyMap.get("TxCode");
        String txCode = (String) targetBodyMap.get("_TRAN_CODE");
        String txSNBinding = (String) targetBodyMap.get("txSNBinding");// 绑定流水号
                                                                       // 规则： 17
                                                                       // 位时间戳+10
                                                                       // 位随机数
        String paymentNo = (String) targetBodyMap.get("paymentNo");// 支付交易流水号
                                                                   // 规则： 17
                                                                   // 位时间戳+10
                                                                   // 位随机数
        String bankID = (String) targetBodyMap.get("bankID");// 银行 ID，参考《银行编码表》
                                                             // 首次支付时必填
        String accountName = (String) targetBodyMap.get("accountName");// 账户名称
                                                                       // 首次支付时必填
        String accountNumber = (String) targetBodyMap.get("accountNumber");// 账户号码
                                                                           // 首次支付时必填
        // 0=身份证 1=户口簿 2=护照 3=军官证 4=士兵证 5=港澳居民来往内地通行证 6=台湾同胞来往内地通行证 7=临时身份证
        // 8=外国人居留证 9=警官证 X=其他证件 首次支付时必填
        String identificationType = (String) targetBodyMap.get("identificationType");// 开户证件类型
        String identificationNumber = (String) targetBodyMap.get("identificationNumber");// 证件号码
        String phoneNumber = (String) targetBodyMap.get("phoneNumber");// 手机号
                                                                       // 首次支付时必填
        String cardType = (String) targetBodyMap.get("cardType");// 卡类型： 10=个人借记
                                                                 // 20=个人贷记
        String validDate = (String) targetBodyMap.get("validDate");// 信用卡有效期，格式
                                                                   // YYMM
                                                                   // 首次支付且卡类型为
                                                                   // 20=个人贷记时必填
        String cvn2 = (String) targetBodyMap.get("cvn2");// 信用卡背面的末 3 位数字
                                                         // 首次支付且卡类型为 20=个人贷记时必填
        String amount = (String) targetBodyMap.get("amount");// 支付金额，单位：分
        String settlementFlag = (String) targetBodyMap.get("settlementFlag");// 结算标识
        String remark = (String) targetBodyMap.get("remark");// 备注

        Tx2561Request txRequest = new Tx2561Request();
        txRequest.setInstitutionID(institutionID); // 机构编号
        txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
        txRequest.setPaymentNo(paymentNo);// 支付
        txRequest.setBankID(bankID); // 银行ID(参考银行编码表)
        txRequest.setAccountName(accountName); // 账户名称
        txRequest.setAccountNumber(accountNumber); // 账户号码
        txRequest.setIdentificationNumber(identificationNumber); // 证件号码
        // 0:身份证 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民 来往内地通行证 6：台湾同胞来往内地通行证 7：临时身份证
        // 8：外国人居留证 9：警官证 X:其他证件
        txRequest.setIdentificationType(identificationType); // 开户证件类型
        txRequest.setPhoneNumber(phoneNumber); // 手机号
        txRequest.setCardType(cardType); // 卡类型 10：个人借记卡 20：个人贷记
        txRequest.setValidDate(validDate); // 行用卡有效期 格式yyMM 绑定行用卡必填
        txRequest.setCvn2(cvn2); // 行用卡背面的末3位数字 绑定行用卡必填
        txRequest.setAmount(amount == null ? 0 : Long.valueOf(amount));// 金额
        txRequest.setSettlementFlag(settlementFlag);// 结算标识
        txRequest.setRemark(remark);// 备注
        txRequest.process();// 将信息组装成xml
        LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

        String msg = txRequest.getRequestMessage();
        String signature = txRequest.getRequestSignature();
        String[] respMessage = send(msg, signature);
        if (respMessage == null || respMessage.length == 0) {
            LOG.info("响应报文不能为空!!");
            throw new Exception("response is null!!");
        }

        Tx2561Response tx2561Response = new Tx2561Response(respMessage[0], respMessage[1]);
        LOG.info("返回报文明文 [{}]", tx2561Response.getResponsePlainText());
        Object respCode = tx2561Response.getCode(); // 响应代码
        Object respMsg = tx2561Response.getMessage(); // 响应信息
        if (StringUtils.isBlank((String) respCode)) {
            LOG.info("响应码不能为空,响应报文[{}]", tx2561Response.getResponsePlainText());
            throw new Exception("respCode is null!!");
        }
        message.getFault().setCode((String) respCode);
        message.getFault().setMsg((String) respMsg);
        if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
            resultMap.put("institutionID", tx2561Response.getInstitutionID());
            resultMap.put("txSNBinding", tx2561Response.getTxSNBinding());
            resultMap.put("status", tx2561Response.getStatus() + "");
            resultMap.put("bankTxTime", tx2561Response.getBankTxTime());
            resultMap.put("responseCode", tx2561Response.getResponseCode());
            resultMap.put("responseMessage", tx2561Response.getResponseMessage());
            resultMap.put("paymentNo", tx2561Response.getPaymentNo());
        }
        resultMap.put("respCode", respCode);
        resultMap.put("respMsg", respMsg);
        targetBodyMap.putAll(resultMap);
        message.getTarget().setBody(targetBodyMap);
        LOG.info("client receive message={}", message);
        LOG.info("*************快捷支付（一键支付，无验证短信） End**************");
    }


    /**
     * 2501接口处理 中金建立绑定卡关系
     * 
     * @param targetBodyMap
     *            请求数据
     * @param message
     * @param resultMap
     *            返回数据
     * @throws Exception
     */
    public static void do2501Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************中金建立绑定卡关系 Start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String bankID = (String) targetBodyMap.get("bankID");
		String accountName = (String) targetBodyMap.get("accountName");
		String accountNumber = (String) targetBodyMap.get("accountNumber");
		String identificationType = (String) targetBodyMap
				.get("identificationType");
		String identificationNumber = (String) targetBodyMap
				.get("identificationNumber");
		String phoneNumber = (String) targetBodyMap.get("phoneNumber");
		String cardType = (String) targetBodyMap.get("cardType");
		String validDate = (String) targetBodyMap.get("validDate");
		String cvn2 = (String) targetBodyMap.get("cvn2");
		// !"".equals((String) targetBodyMap.get("CVN2")) ? ((String)
		// targetBodyMap.get("CVN2")).trim()
		// : null;

		Tx2501Request txRequest = new Tx2501Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.setBankID(bankID); // 银行ID(参考银行编码表)
		txRequest.setAccountName(accountName); // 账户名称
		txRequest.setAccountNumber(accountNumber); // 账户号码
		txRequest.setIdentificationNumber(identificationNumber); // 证件号码
		// 0:身份证 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民 来往内地通行证 6：台湾同胞来往内地通行证 7：临时身份证
		// 8：外国人居留证 9：警官证 X:其他证件
		txRequest.setIdentificationType(identificationType); // 开户证件类型
		txRequest.setPhoneNumber(phoneNumber); // 手机号
		txRequest.setCardType(cardType); // 卡类型 10：个人借记卡 20：个人贷记
		txRequest.setValidDate(validDate); // 行用卡有效期 格式yyMM 绑定行用卡必填
		txRequest.setCvn2(cvn2); // 行用卡背面的末3位数字 绑定行用卡必填
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2501Response tx2501Response = new Tx2501Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2501Response.getResponsePlainText());
		Object respCode = tx2501Response.getCode(); // 响应代码
		Object respMsg = tx2501Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2501Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2501Response.getInstitutionID());
			resultMap.put("txSNBinding", tx2501Response.getTxSNBinding());
			resultMap.put("status", tx2501Response.getStatus() + "");
			resultMap.put("bankTxTime", tx2501Response.getBankTxTime());
			resultMap.put("responseCode", tx2501Response.getResponseCode());
			resultMap.put("responseMessage",
					tx2501Response.getResponseMessage());
			resultMap.put("issueBankID", tx2501Response.getIssueBankID());
			resultMap.put("issueCardType", tx2501Response.getIssueCardType());
			resultMap.put("issInsCode", tx2501Response.getIssInsCode());
			resultMap.put("payCardType", tx2501Response.getPayCardType());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金建立绑定卡关系 End**************");
	}

	/**
	 * 2502接口处理 中金查询绑定卡关系
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2502Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************中金查询绑定卡关系 Start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");

		Tx2502Request txRequest = new Tx2502Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2502Response tx2502Response = new Tx2502Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2502Response.getResponsePlainText());
		Object respCode = tx2502Response.getCode(); // 响应代码
		Object respMsg = tx2502Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2502Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2502Response.getInstitutionID());
			resultMap.put("txSNBinding", tx2502Response.getTxSNBinding());
			resultMap.put("status", tx2502Response.getStatus() + "");
			resultMap.put("responseCode", tx2502Response.getResponseCode());
			resultMap.put("responseMessage",
					tx2502Response.getResponseMessage());
			resultMap.put("issueBankID", tx2502Response.getIssueBankID());
			resultMap.put("issueCardType", tx2502Response.getIssueCardType());
			resultMap.put("issInsCode", tx2502Response.getIssInsCode());
			resultMap.put("payCardType", tx2502Response.getPayCardType());
			resultMap.put("bankTxTime", tx2502Response.getBankTxTime());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金查询绑定卡关系 End**************");
	}

	/**
	 * 2503接口处理 中金解除绑定卡关系
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2503Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************中金解除绑定卡关系 Start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String txSNUnBinding = (String) targetBodyMap.get("txSNUnBinding");

		Tx2503Request txRequest = new Tx2503Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSNBinding(txSNBinding); // 解绑流水号
		txRequest.setTxSNUnBinding(txSNUnBinding); // 原绑定流水号
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2503Response tx2503Response = new Tx2503Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2503Response.getResponsePlainText());
		Object respCode = tx2503Response.getCode(); // 响应代码
		Object respMsg = tx2503Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2503Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2503Response.getInstitutionID());
			resultMap.put("txSNUnBinding", tx2503Response.getTxSNUnBinding());
			resultMap.put("txSNBinding", tx2503Response.getTxSNBinding());
			resultMap.put("status", tx2503Response.getStatus() + "");
			resultMap.put("responseMessage",
					tx2503Response.getResponseMessage());
			resultMap.put("responseCode", tx2503Response.getResponseCode());
			resultMap.put("bankTxTime", tx2503Response.getBankTxTime());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金解除绑定卡关系 End**************");
	}

	/**
	 * 2511接口处理 中金快捷支付
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2511Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************中金快捷支付 Start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		Object object = targetBodyMap.get("amount");
		LOG.info("=================" + object + ":::::::" + object.getClass()
				+ "====================");

		// BigDecimal amount1 = new BigDecimal();

		long amount = Long.valueOf(object.toString());
		LOG.info("=============================================转换后的金额:::::::"
				+ amount);
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String settlementFlag = (String) targetBodyMap.get("settlementFlag");
		String remark = (String) targetBodyMap.get("remark");
		/*
		 * !"".equals((String) targetBodyMap.get("Remark")) ? ((String)
		 * targetBodyMap.get("Remark")) .trim() : null;
		 */
		Tx2511Request txRequest = new Tx2511Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		txRequest.setPaymentNo(paymentNo); // 支付交易流水号
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.setAmount(amount); // 结算金额 单位 分
		txRequest.setSettlementFlag(settlementFlag); // 结算标识
		txRequest.setRemark(remark); // 备注
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2511Response tx2511Response = new Tx2511Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2511Response.getResponsePlainText());
		Object respCode = tx2511Response.getCode(); // 响应代码
		Object respMsg = tx2511Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2511Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2511Response.getInstitutionID());
			resultMap.put("paymentNo", tx2511Response.getPaymentNo());
			resultMap.put("status", tx2511Response.getStatus() + "");
			resultMap.put("responseMessage",
					tx2511Response.getResponseMessage());
			resultMap.put("responseCode", tx2511Response.getResponseCode());
			resultMap.put("bankTxTime", tx2511Response.getBankTxTime());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金快捷支付 End**************");
	}

	/**
	 * 2512接口处理 中金快捷支付查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2512Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************中金快捷支付查询 Start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String paymentNo = (String) targetBodyMap.get("paymentNo");

		Tx2512Request txRequest = new Tx2512Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setPaymentNo(paymentNo); // 支付交易流水号
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2512Response tx2512Response = new Tx2512Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2512Response.getResponsePlainText());
		Object respCode = tx2512Response.getCode(); // 响应代码
		Object respMsg = tx2512Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2512Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2512Response.getInstitutionID());
			resultMap.put("paymentNo", tx2512Response.getPaymentNo());
			resultMap.put("status", tx2512Response.getStatus() + "");
			resultMap.put("responseMessage",
					tx2512Response.getResponseMessage());
			resultMap.put("responseCode", tx2512Response.getResponseCode());
			resultMap.put("bankTxTime", tx2512Response.getBankTxTime());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金快捷支付查询 End**************");
	}

	/**
	 * 4530接口处理 中金代付
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do4530Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************中金代付 Start**************");
		String phoneNumber = (String) targetBodyMap.get("phoneNumber");
		String remark = (String) targetBodyMap.get("remark");
		if (StringUtils.isBlank(phoneNumber)) {
			phoneNumber = "";
		}
		if (StringUtils.isBlank(remark)) {
			remark = "";
		}

		Tx4530Request tx4530Request = new Tx4530Request();
		tx4530Request.setInstitutionID((String) targetBodyMap
				.get("institutionID"));
		tx4530Request.setTxSN((String) targetBodyMap.get("txSN"));
		// String val = targetBodyMap.get("amount").toString();
		// BigDecimal amountDecimal = new BigDecimal(val);
		// LOG.info("amountDecimal=" + amountDecimal);
		// tx4530Request.setAmount(amountDecimal.multiply(new
		// BigDecimal(100)).longValue());
		String amount = targetBodyMap.get("amount").toString();
		tx4530Request.setAmount(new BigDecimal(amount).longValue());
		tx4530Request.setRemark(remark);
		PaymentAccount payer = new PaymentAccount();
		payer.setPaymentAccountName((String) targetBodyMap
				.get("paymentAccountName"));
		payer.setPaymentAccountNumber((String) targetBodyMap
				.get("paymentAccountNumber"));
		tx4530Request.setPayer(payer);
		BankAccount payee = new BankAccount();
		payee.setAccountType(Integer.parseInt((String) targetBodyMap
				.get("accountType")));
		payee.setBankID((String) targetBodyMap.get("bankID"));
		payee.setAccountName((String) targetBodyMap.get("bankAccountName"));
		payee.setAccountNumber((String) targetBodyMap.get("bankAccountNumber"));
		payee.setPhoneNumber(phoneNumber);
		tx4530Request.setPayee(payee);
		tx4530Request.setRemark("10");
		tx4530Request.process(); // 将信息组装成xml
		LOG.info("组装明文 [{}]", tx4530Request.getRequestPlainText());

		String msg = tx4530Request.getRequestMessage();
		String signature = tx4530Request.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx4530Response txResponse = new Tx4530Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", txResponse.getResponsePlainText());
		Object respCode = txResponse.getCode(); // 响应代码
		Object respMsg = txResponse.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", txResponse.getInstitutionID());
			resultMap.put("txSN", txResponse.getTxSN());
			resultMap.put("status", txResponse.getStatus() + "");
			resultMap.put("bankResponseCode", txResponse.getBankResponseCode());
			resultMap.put("bankResponseMessage",
					txResponse.getBankResponseMessage());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金代付 End**************");
	}

	/**
	 * 4532接口处理 中金代付查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do5432Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception,
			UnsupportedEncodingException {
		LOG.info("*************中金代付查询 Start**************");
		Tx4532Request tx4532Request = new Tx4532Request();
		tx4532Request.setInstitutionID((String) targetBodyMap
				.get("institutionID"));
		tx4532Request.setTxSN((String) targetBodyMap.get("txSN"));
		tx4532Request.process(); // 将信息组装成xml
		LOG.info("组装明文 [{}]", tx4532Request.getRequestPlainText());

		String msg = tx4532Request.getRequestMessage();
		String signature = tx4532Request.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空");
			throw new Exception("response is null!!");
		}
		// String plainText = new
		// String(payment.tools.util.Base64.decode(respMessage[0]), "UTF-8");
		Tx4532Response txResponse = new Tx4532Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", txResponse.getResponsePlainText());
		Object respCode = txResponse.getCode(); // Code
		Object respMsg = txResponse.getMessage(); // Message
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 处理成功
			resultMap.put("institutionID", txResponse.getInstitutionID());
			resultMap.put("txSN", txResponse.getTxSN());
			resultMap.put("paymentAccountName", txResponse.getPayer()
					.getPaymentAccountName());
			resultMap.put("paymentAccountNumber", txResponse.getPayer()
					.getPaymentAccountNumber());
			resultMap
					.put("accountType", txResponse.getPayee().getAccountType());
			resultMap.put("bankID", txResponse.getPayee().getBankID());
			resultMap.put("bankAccountName", txResponse.getPayee()
					.getAccountName());
			resultMap.put("bankAccountNumber", txResponse.getPayee()
					.getAccountNumber());
			resultMap.put("amount", txResponse.getAmount());
			resultMap.put("status", txResponse.getStatus() + "");
			resultMap.put("bankResponseCode", txResponse.getBankResponseCode());
			resultMap.put("bankResponseMessage",
					txResponse.getBankResponseMessage());
			resultMap.put("bankTxTime", txResponse.getBankTxTime());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金代付查询 End**************");
	}

	/**
	 * 1810接口处理 中金对账
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1810Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap)
			throws ParseException, Exception, UnsupportedEncodingException {
		LOG.info("*************中金对账 Start**************");
		Tx1810Request tx1810Request = new Tx1810Request();
		tx1810Request.setInstitutionID((String) targetBodyMap
				.get("institutionID"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// 对账日期获得
		Date serchDate = sdf.parse((String) targetBodyMap.get("searchDate"));
		tx1810Request.setDate(serchDate);
		tx1810Request.process(); // 将信息组装成xml
		LOG.info("组装明文 [{}]", tx1810Request.getRequestPlainText());

		String msg = tx1810Request.getRequestMessage();
		String signature = tx1810Request.getRequestSignature();
		String[] respMsg = send(msg, signature);
		if (respMsg == null || respMsg.length == 0) {
			LOG.info("响应报文不能为空");
			throw new Exception("response is null!!");
		}
		// String plainText = new
		// String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
		// LOG.debug("[message]=[" + respMsg[0] + "]");
		// LOG.debug("[signature]=[" + respMsg[1] + "]");
		// LOG.debug("[plainText]=[" + plainText + "]");

		Tx1810Response txResponse = new Tx1810Response(respMsg[0], respMsg[1]);
		Object respCode = txResponse.getCode(); // 响应代码
		Object respMessage = txResponse.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMessage);
		List<Map<String, String>> acountBill = new ArrayList<>();
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) {
			LOG.info("[Message]=[" + txResponse.getMessage() + "]");
			List<Tx> txList = txResponse.getTxList();
			if (txList != null) {
				int size = txList.size();
				for (int i = 0; i < size; i++) {
					Map<String, String> subBill = new HashMap<>();
					Tx tx = txList.get(i);
					subBill.put("txType", tx.getTxType());// 交易类型
					subBill.put("txSn", tx.getTxSn());// 交易编号 流水号
					subBill.put("txAmount", Long.toString(tx.getTxAmount()));// 交易金额
					subBill.put("institutionAmount",
							Long.toString(tx.getInstitutionAmount()));// 机构应收的金额
																		// 单位：分
					subBill.put("paymentAmount",
							Long.toString(tx.getPaymentAmount()));// 支付平台应收的金额
																	// 单位：分
					subBill.put("institutionFee",
							Long.toString(tx.getInstitutionFee()));// 机构手续费
																	// 单位：分
					subBill.put("remark", tx.getRemark());// 备注
					subBill.put("settlementFlag", tx.getSettlementFlag()); // 支付平台收到的银行通知时间
																			// 格式
																			// YYYYMMDDhhmmss
					subBill.put("bankNotificationTime",
							tx.getBankNotificationTime());
					acountBill.add(subBill);
					// 结算标识
					LOG.info("[TxType]=[" + tx.getTxType() + "]");
					LOG.info("[TxSn]=[" + tx.getTxSn() + "]");
					LOG.info("[TxAmount]=[" + tx.getTxAmount() + "]");
					LOG.info("[InstitutionAmount]=["
							+ tx.getInstitutionAmount() + "]");
					LOG.info("[PaymentAmount]=[" + tx.getPaymentAmount() + "]");
					LOG.info("[InstitutionFee]=[" + tx.getInstitutionFee()
							+ "]");
					LOG.info("[Remark]=[" + tx.getRemark() + "]");
					LOG.info("[SettlementFlag]=[" + tx.getSettlementFlag()
							+ "]");
					LOG.info("[BankNotificationTime]=["
							+ tx.getBankNotificationTime() + "]");
				}
			}
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		resultMap.put("acountBill", acountBill);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金对账 End**************");
	}

	/**
	 * 1811接口处理 中金分页对账
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1811Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap)
			throws ParseException, Exception, UnsupportedEncodingException {
		LOG.info("*************中金分页对账 Start**************");
		Tx1811Request tx1811Request = new Tx1811Request();
		tx1811Request.setInstitutionID((String) targetBodyMap
				.get("institutionID"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date serchDate = sdf.parse((String) targetBodyMap.get("searchDate")); // 对账日期获得
		tx1811Request.setDate(serchDate);
		tx1811Request.setPageNO((String) targetBodyMap.get("pageNo"));// 对账页数
		tx1811Request.setCountPerPage((String) targetBodyMap
				.get("countPerPage"));// 单页对账条目数
		tx1811Request.process();// 3.执行报文处理
		LOG.info("组装明文 [{}]", tx1811Request.getRequestPlainText());

		String msg = tx1811Request.getRequestMessage();
		String signature = tx1811Request.getRequestSignature();
		String[] respMsg = send(msg, signature);
		if (respMsg == null || respMsg.length == 0) {
			LOG.info("响应报文不能为空");
			throw new Exception("response is null!!");
		}

		// String plainText = new
		// String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
		// LOG.debug("[message]=[" + respMsg[0] + "]");
		// LOG.debug("[signature]=[" + respMsg[1] + "]");
		// LOG.debug("[plainText]=[" + plainText + "]");
		Tx1811Response txResponse = new Tx1811Response(respMsg[0], respMsg[1]);
		Object respCode = txResponse.getCode(); // 响应代码
		Object respMessage = txResponse.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMessage);
		List<Map<String, String>> acountBill = new ArrayList<>();
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(txResponse
				.getCode())) {
			LOG.info("[Message]=[" + txResponse.getMessage() + "]");
			List<Tx> txList = txResponse.getTxList();
			if (txList != null) {
				int size = txList.size();
				for (int i = 0; i < size; i++) {
					Map<String, String> subBill = new HashMap<>();
					Tx tx = txList.get(i);
					subBill.put("txType", tx.getTxType());// 交易类型
					subBill.put("txSn", tx.getTxSn()); // 交易编号 流水号
					subBill.put("txAmount", Long.toString(tx.getTxAmount())); // 交易金额
					subBill.put("institutionAmount",
							Long.toString(tx.getInstitutionAmount()));// 机构应收的金额
																		// 单位：分
					subBill.put("paymentAmount",
							Long.toString(tx.getPaymentAmount()));// 支付平台应收的金额
																	// 单位：分
					subBill.put("institutionFee",
							Long.toString(tx.getInstitutionFee()));// 机构手续费
																	// 单位：分
					subBill.put("remark", tx.getRemark());// 备注
					subBill.put("settlementFlag", tx.getSettlementFlag());// 支付平台收到的银行通知时间
																			// 格式
																			// YYYYMMDDhhmmss
					acountBill.add(subBill);
					subBill.put("bankNotificationTime",
							tx.getBankNotificationTime());// 结算标识
					LOG.info("[TxType]=[" + tx.getTxType() + "]");
					LOG.info("[TxSn]=[" + tx.getTxSn() + "]");
					LOG.info("[TxAmount]=[" + tx.getTxAmount() + "]");
					LOG.info("[InstitutionAmount]=["
							+ tx.getInstitutionAmount() + "]");
					LOG.info("[PaymentAmount]=[" + tx.getPaymentAmount() + "]");
					LOG.info("[InstitutionFee]=[" + tx.getInstitutionFee()
							+ "]");
					LOG.info("[Remark]=[" + tx.getRemark() + "]");
					LOG.info("[SettlementFlag]=[" + tx.getSettlementFlag()
							+ "]");
					LOG.info("[BankNotificationTime]=["
							+ tx.getBankNotificationTime() + "]");
				}
			}
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		resultMap.put("acountBill", acountBill);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("*************中金分页对账 End**************");
	}

	/**
	 * 2521接口处理 快捷支付退款
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2521Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do2521Request start**************");

		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSN = (String) targetBodyMap.get("txSN");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		long amount = Long.parseLong((String) targetBodyMap.get("amount"));
		String remark = null;
		/*
		 * !((String) targetBodyMap.get("Remark")).equals("") ? ((String)
		 * targetBodyMap.get("Remark")) .trim() : null;
		 */

		Tx2521Request txRequest = new Tx2521Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSN(txSN); // 退款交易流水号
		txRequest.setPaymentNo(paymentNo); // 原交易流水号（即2511接口中的paymentNo）
		txRequest.setAmount(amount);// 退款金额 分
		txRequest.setRemark(remark); // 备注
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());
		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}
		Tx2521Response tx2521Response = new Tx2521Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2521Response.getResponsePlainText());
		Object respCode = tx2521Response.getCode(); // 响应代码
		Object respMsg = tx2521Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2521Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			// message.getFault().setCode((String) respCode);
			// message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2521Request start**************");
	}

	/**
	 * 2522接口处理 快捷支付退款查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2522Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do2522Request start**************");

		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSN = (String) targetBodyMap.get("txSN");

		Tx2522Request txRequest = new Tx2522Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSN(txSN); // 退款交易流水号
		txRequest.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());
		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}
		Tx2522Response tx2522Response = new Tx2522Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx2522Response.getResponsePlainText());
		Object respCode = tx2522Response.getCode(); // 响应代码
		Object respMsg = tx2522Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2522Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if ("2000".equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2522Response.getInstitutionID());
			resultMap.put("txSN", tx2522Response.getTxSN());
			resultMap.put("paymentNo", tx2522Response.getPaymentNo());
			resultMap.put("status", tx2522Response.getStatus() + "");
			// message.getFault().setCode((String) respCode);
			// message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2522Request start**************");
	}

	/**
	 * 2531接口处理 建立绑定关系（发送验证短息）
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	@Deprecated
	public static void do2531Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do2531Request start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String bankID = (String) targetBodyMap.get("bankID");
		String accountName = (String) targetBodyMap.get("accountName");
		String accountNumber = (String) targetBodyMap.get("accountNumber");
		String identificationType = (String) targetBodyMap
				.get("identificationType");
		String identificationNumber = (String) targetBodyMap
				.get("identificationNumber");
		String phoneNumber = (String) targetBodyMap.get("phoneNumber");
		String cardType = (String) targetBodyMap.get("cardType");
		String validDate = !"".equals((String) targetBodyMap.get("validDate")) ? ((String) targetBodyMap
				.get("validDate")).trim() : null;
		String cvn2 = !"".equals((String) targetBodyMap.get("cvn2")) ? ((String) targetBodyMap
				.get("cvn2")).trim() : null;

		Tx2531Request txRequest = new Tx2531Request();

		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.setBankID(bankID); // 银行ID(参考银行编码表)
		txRequest.setAccountName(accountName); // 账户名称
		txRequest.setAccountNumber(accountNumber); // 账户号码
		txRequest.setIdentificationNumber(identificationNumber); // 证件号码
		// 0:身份证 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民 来往内地通行证 6：台湾同胞来往内地通行证 7：临时身份证
		// 8：外国人居留证 9：警官证 X:其他证件
		txRequest.setIdentificationType(identificationType); // 开户证件类型
		txRequest.setPhoneNumber(phoneNumber); // 手机号
		txRequest.setCardType(cardType); // 卡类型 10：个人借记卡 20：个人贷记
		txRequest.setValidDate(validDate); // 行用卡有效期 格式yyMM 绑定行用卡必填
		txRequest.setCvn2(cvn2); // 行用卡背面的末3位数字 绑定行用卡必填
		txRequest.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2531Response txResponse = new Tx2531Response(respMessage[0],
				respMessage[1]);
		Object respCode = txResponse.getCode(); // 响应代码
		Object respMsg = txResponse.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if ("2000".equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2531Request start**************");
	}

	/**
	 * 2532接口处理 建立绑定关系（验证并绑定）
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	@Deprecated
	public static void do2532Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do2532Request start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txCode = (String) targetBodyMap.get("_TRAN_CODE");
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String SMSValidationCode = (String) targetBodyMap
				.get("SMSValidationCode");

		Tx2532Request txRequest = new Tx2532Request();

		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.setSMSValidationCode(SMSValidationCode); // 短息验证码
		txRequest.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2532Response tx2532Response = new Tx2532Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx2532Response.getCode(); // 响应代码
		Object respMsg = tx2532Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2532Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		String InstitutionID = "";
		String TxSNBinding = "";
		Integer VerifyStatus;
		Integer Status;
		String BankTxTime = "";
		String ResponseMessage = "";
		String IssueBankID = "";
		String IssueCardType = "";
		String ResponseCode = "";
		String IssInsCode = "";
		String PayCardType = "";

		if ("2000".equals(respCode)) { // 返回成功
			// tx2532Response.getMessage();
			InstitutionID = tx2532Response.getInstitutionID();
			TxSNBinding = tx2532Response.getTxSNBinding();
			VerifyStatus = tx2532Response.getVerifyStatus();
			Status = tx2532Response.getStatus();
			BankTxTime = tx2532Response.getBankTxTime();
			ResponseMessage = tx2532Response.getResponseMessage();
			IssueBankID = tx2532Response.getIssueBankID();
			IssueCardType = tx2532Response.getIssueCardType();
			ResponseCode = tx2532Response.getResponseCode();
			IssInsCode = tx2532Response.getIssInsCode();
			PayCardType = tx2532Response.getPayCardType();
			resultMap.put("institutionID", InstitutionID);
			resultMap.put("txSNBinding", TxSNBinding);
			resultMap.put("verifyStatus", VerifyStatus);
			resultMap.put("status", Status + "");
			resultMap.put("bankTxTime", BankTxTime);
			resultMap.put("responseMessage", ResponseMessage);
			resultMap.put("issueBankID", IssueBankID);
			resultMap.put("issueCardType", IssueCardType);
			resultMap.put("responseCode", ResponseCode);
			resultMap.put("issInsCode", IssInsCode);
			resultMap.put("payCardType", PayCardType);
			// message.getFault().setCode((String) respCode);
			// message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2532Request start**************");
	}

	/**
	 * 2541接口处理 快捷支付（发送验证短信）
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	@Deprecated
	public static void do2541Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do2541Request start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		long amount = Long.parseLong((String) targetBodyMap.get("amount"));
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String settlementFlag = (String) targetBodyMap.get("settlementFlag");
		String remark = null;
		/*
		 * !"".equals((String) targetBodyMap.get("Remark")) ? ((String)
		 * targetBodyMap.get("Remark")) .trim() : null;
		 */
		Tx2541Request txRequest = new Tx2541Request();

		txRequest.setInstitutionID(institutionID); // 机构编号
		txRequest.setPaymentNo(paymentNo); // 支付交易流水号
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.setAmount(amount); // 结算金额 单位 分
		txRequest.setSettlementFlag(settlementFlag); // 结算标识
		txRequest.setRemark(remark); // 备注

		txRequest.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2541Response tx2541Response = new Tx2541Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx2541Response.getCode(); // 响应代码
		Object respMsg = tx2541Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2541Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if ("2000".equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2541Request start**************");
	}

	/**
	 * 2542接口处理 快捷支付（验证并绑定）
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	@Deprecated
	public static void do2542Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do2541Request start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		long amount = Long.parseLong((String) targetBodyMap.get("amount"));
		String txSNBinding = (String) targetBodyMap.get("txSNBinding");
		String settlementFlag = (String) targetBodyMap.get("settlementFlag");
		String remark = null;
		/*
		 * !"".equals((String) targetBodyMap.get("Remark")) ? ((String)
		 * targetBodyMap.get("Remark")) .trim() : null;
		 */

		Tx2541Request txRequest = new Tx2541Request();

		txRequest.setInstitutionID(institutionID); // 机构编号
		txRequest.setPaymentNo(paymentNo); // 支付交易流水号
		txRequest.setTxSNBinding(txSNBinding); // 绑定流水号
		txRequest.setAmount(amount); // 结算金额 单位 分
		txRequest.setSettlementFlag(settlementFlag); // 结算标识
		txRequest.setRemark(remark); //

		txRequest.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2532Response tx2532Response = new Tx2532Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx2532Response.getCode(); // 响应代码
		Object respMsg = tx2532Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2532Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if ("2000".equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2532Request start**************");
	}

	/**
	 * 4510接口处理 机构支付账户余额查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do4510Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************机构支付账户余额查询 Start**************");
		Tx4510Request tx4510Request = new Tx4510Request();
		tx4510Request.setInstitutionID((String) targetBodyMap
				.get("institutionID"));
		tx4510Request.setPaymentAccountName((String) targetBodyMap
				.get("paymentAccountName"));
		tx4510Request.setPaymentAccountNumber((String) targetBodyMap
				.get("paymentAccountNumber"));
		tx4510Request.process(); // 将信息组装成xml
		LOG.info("组装明文 [{}]", tx4510Request.getRequestPlainText());

		String msg = tx4510Request.getRequestMessage();
		String signature = tx4510Request.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx4510Response txResponse = new Tx4510Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", txResponse.getResponsePlainText());
		Object respCode = txResponse.getCode(); // 响应代码
		Object respMsg = txResponse.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", txResponse.getInstitutionID());
			resultMap.put("paymentAccountName",
					txResponse.getPaymentAccountName());
			resultMap.put("paymentAccountNumber",
					txResponse.getPaymentAccountNumber());
			resultMap.put("paymentAccountUsage",
					txResponse.getPaymentAccountUsage());
			resultMap.put("balance", (long) txResponse.getBalance());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************机构支付账户余额查询 End**************");
	}

	/**
	 * 4512接口处理 机构支付账户交易明细查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do4512Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************机构支付账户交易明细查询 Start**************");
		Tx4512Request tx4512Request = new Tx4512Request();
		tx4512Request.setInstitutionID((String) targetBodyMap
				.get("institutionID"));
		tx4512Request.setPaymentAccountName((String) targetBodyMap
				.get("paymentAccountName"));
		tx4512Request.setPaymentAccountNumber((String) targetBodyMap
				.get("paymentAccountNumber"));
		tx4512Request.setStartDate((String) targetBodyMap.get("startDate"));
		tx4512Request.setEndDate((String) targetBodyMap.get("endDate"));
		tx4512Request.process(); // 将信息组装成xml
		LOG.info("组装明文 [{}]", tx4512Request.getRequestPlainText());

		String msg = tx4512Request.getRequestMessage();
		String signature = tx4512Request.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}
		Tx4512Response txResponse = new Tx4512Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", txResponse.getResponsePlainText());
		Object respCode = txResponse.getCode(); // 响应代码
		Object respMsg = txResponse.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", txResponse.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		List<Map<String, Object>> acountTx = new ArrayList<Map<String, Object>>();
		if (CmparmConstants.ZM_RESULTCODE_CHECKPASS.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", txResponse.getInstitutionID());
			resultMap.put("paymentAccountName",
					txResponse.getPaymentAccountName());
			resultMap.put("paymentAccountNumber",
					txResponse.getPaymentAccountNumber());
			List<PaymentAccountTx> txList = txResponse.getTx();
			if (txList != null) {
				for (int i = 0; i < txList.size(); i++) {
					Map<String, Object> subTx = new HashMap<String, Object>();
					PaymentAccountTx paymentaccounttx = txList.get(i);
					subTx.put("txType", paymentaccounttx.getTxType());
					subTx.put("txTime", paymentaccounttx.getTxTime());
					subTx.put("amount", paymentaccounttx.getAmount());
					subTx.put("balance", paymentaccounttx.getBalance());
					subTx.put("remark", paymentaccounttx.getRemark());
					acountTx.add(subTx);
				}
			}
		}
		resultMap.put("acountTx", acountTx);
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************机构支付账户交易明细查询 End**************");
	}

	/**
	 * 2310接口处理 账户验证
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2310Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do2310Request start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		String bankID = (String) targetBodyMap.get("bankID");
		String accountType = (String) targetBodyMap.get("accountType");
		String accountName = (String) targetBodyMap.get("accountName");
		String accountNumber = (String) targetBodyMap.get("accountNumber");
		String identificationType = (String) targetBodyMap
				.get("identificationType");
		String identificationNumber = (String) targetBodyMap
				.get("identificationNumber");
		String validDate = (String) targetBodyMap.get("validDate");
		String cvn2 = (String) targetBodyMap.get("cvn2");
		String remark = (String) targetBodyMap.get("remark");
		String phoneNumber = (String) targetBodyMap.get("phoneNumber");
		String email = (String) targetBodyMap.get("email");

		Tx2310Request tx2310Request = new Tx2310Request();

		tx2310Request.setInstitutionID(institutionID); // 机构编号
		// tx2310Request.set(txCode);
		tx2310Request.setTxSN(txSN);// 交易流水
		tx2310Request.setBankID(bankID);// 银行ID
		tx2310Request.setAccountType(accountType);// 账户类型11：个人账户 12：企业账户
		tx2310Request.setAccountName(accountName);// 账户名称
		tx2310Request.setAccountNumber(accountNumber);// 账户号码
		tx2310Request.setIdentificationNumber(identificationNumber);// 证件号码
		tx2310Request.setIdentificationType(identificationType);// 证件类型
		tx2310Request.setValidDate(validDate);// 信用卡有效期
		tx2310Request.setCvn2(cvn2);// 信用卡背面3位
		tx2310Request.setRemark(remark);// 备注
		tx2310Request.setPhoneNumber(phoneNumber);// 手机号
		tx2310Request.setEmail(email);// 电子邮箱

		tx2310Request.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", tx2310Request.getRequestPlainText());

		String msg = tx2310Request.getRequestMessage();
		String signature = tx2310Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2310Response tx2310Response = new Tx2310Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx2310Response.getCode(); // 响应代码
		Object respMsg = tx2310Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2310Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		resultMap.put("status", tx2310Response.getStatus());
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2310Request start**************");
	}

	/**
	 * 2320接口处理 身份验证
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2320Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do2320Request start**************");
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		String userName = (String) targetBodyMap.get("userName");
		String identificationNumber = (String) targetBodyMap
				.get("identificationNumber");

		Tx2320Request tx2320Request = new Tx2320Request();

		tx2320Request.setInstitutionID(institutionID); // 机构编号
		tx2320Request.setTxSN(txSN);// 交易流水
		tx2320Request.setUserName(userName);// 姓名
		tx2320Request.setIdentificationNumber(identificationNumber);// 身份证号

		tx2320Request.process();// 将信息组装成xml

		LOG.info("组装明文 [{}]", tx2320Request.getRequestPlainText());

		String msg = tx2320Request.getRequestMessage();
		String signature = tx2320Request.getRequestSignature();

		LOG.info("组装明文msg: [{}]", msg);
		LOG.info("组装明文signature: [{}]", signature);
		LOG.info("請求URL: [{}]", PaymentEnvironment.txURL);
		LOG.info("請求paymentConfigPathURL: [{}]",
				PaymentEnvironment.paymentConfigPath);
		LOG.info("請求paymentURL: [{}]", PaymentEnvironment.paymentURL);
		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2320Response tx2320Response = new Tx2320Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx2320Response.getCode(); // 响应代码
		Object respMsg = tx2320Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2320Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2320Request start**************");
	}

	/**
	 * 1111接口处理 商户订单支付接口（直通车）
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1111Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1111Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		long amount = Long.parseLong((String) targetBodyMap.get("amount"));
		long fee = Long.parseLong((String) targetBodyMap.get("fee"));
		String payerID = !targetBodyMap.get("payerID").equals("") ? ((String) targetBodyMap
				.get("payerID")).trim() : null;
		String payerName = !targetBodyMap.get("payerName").equals("") ? ((String) targetBodyMap
				.get("payerName")).trim() : null;
		String settlementFlag = !targetBodyMap.get("settlementFlag").equals("") ? ((String) targetBodyMap
				.get("settlementFlag")).trim() : null;
		String usage = !targetBodyMap.get("usage").equals("") ? ((String) targetBodyMap
				.get("usage")).trim() : null;
		String remark = !targetBodyMap.get("remark").equals("") ? ((String) targetBodyMap
				.get("remark")).trim() : null;
		String notificationURL = !targetBodyMap.get("notificationURL").equals(
				"") ? ((String) targetBodyMap.get("notificationURL")).trim()
				: null;
		String bankID = (String) targetBodyMap.get("bankID");
		int accountType = Integer.parseInt((String) targetBodyMap
				.get("accountType"));
		String cardType = (String) targetBodyMap.get("cardType");

		// 2.创建交易请求对象
		Tx1111Request tx1111Request = new Tx1111Request();
		tx1111Request.setInstitutionID(institutionID);
		tx1111Request.setPaymentNo(paymentNo);
		tx1111Request.setAmount(amount);
		tx1111Request.setFee(fee);
		tx1111Request.setPayerID(payerID);
		tx1111Request.setPayerName(payerName);
		tx1111Request.setSettlementFlag(settlementFlag);
		tx1111Request.setUsage(usage);
		tx1111Request.setRemark(remark);
		tx1111Request.setNotificationURL(notificationURL);
		tx1111Request.setBankID(bankID);
		tx1111Request.setAccountType(accountType);
		tx1111Request.setCardType(cardType);

		// 3.执行报文处理
		tx1111Request.process();

		LOG.info("组装明文 [{}]", tx1111Request.getRequestPlainText());

		String msg = tx1111Request.getRequestMessage();
		String signature = tx1111Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		/*
		 * Tx1111Response tx2320Response = new Tx1111Response(respMessage[0],
		 * respMessage[1]); Object respCode = tx2320Response.getCode(); // 响应代码
		 * Object respMsg = tx2320Response.getMessage(); // 响应信息 if
		 * (StringUtils.isBlank((String) respCode)) {
		 * LOG.info("响应码不能为空,响应报文[{}]", tx2320Response.getResponsePlainText());
		 * throw new Exception("respCode is null!!"); }
		 * message.getFault().setCode((String) respCode);
		 * message.getFault().setMsg((String) respMsg);
		 * 
		 * if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { //
		 * 返回成功 message.getFault().setCode((String) respCode);
		 * message.getFault().setMsg((String) respMsg); }
		 * resultMap.put("respCode", respCode); resultMap.put("respMsg",
		 * respMsg); targetBodyMap.putAll(resultMap);
		 */
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2320Request start**************");
	}

	/**
	 * 1120接口处理 商户订单支付查询接口
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1120Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1120Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		// 2.创建交易请求对象
		Tx1120Request tx1120Request = new Tx1120Request();
		tx1120Request.setInstitutionID(institutionID);
		tx1120Request.setPaymentNo(paymentNo);
		// 3.执行报文处理
		tx1120Request.process();
		LOG.info("组装明文 [{}]", tx1120Request.getRequestPlainText());

		String msg = tx1120Request.getRequestMessage();
		String signature = tx1120Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1120Response tx1120Response = new Tx1120Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1120Response.getCode(); // 响应代码
		Object respMsg = tx1120Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1120Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
			resultMap.put("institutionID", tx1120Response.getInstitutionID());
			resultMap.put("paymentNo", tx1120Response.getPaymentNo());
			resultMap.put("amount", tx1120Response.getAmount());
			resultMap.put("refundAmount", tx1120Response.getRefundAmount());
			resultMap.put("status", tx1120Response.getStatus());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1120Request start**************");
	}

	/**
	 * 1131接口处理 商户订单退款
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1131Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1131Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String serialNumber = (String) targetBodyMap.get("serialNumber");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		long amount = Long.parseLong((String) targetBodyMap.get("amount"));
		String remark = !targetBodyMap.get("remark").equals("") ? ((String) targetBodyMap
				.get("remark")).trim() : null;
		int accountType = Integer.parseInt((String) targetBodyMap
				.get("accountType"));
		String paymentAccountName = !targetBodyMap.get("paymentAccountName")
				.equals("") ? ((String) targetBodyMap.get("paymentAccountName"))
				.trim() : null;
		String paymentAccountNumber = !targetBodyMap
				.get("paymentAccountNumber").equals("") ? ((String) targetBodyMap
				.get("paymentAccountNumber")).trim() : null;
		String bankID = !targetBodyMap.get("bankID").equals("") ? ((String) targetBodyMap
				.get("bankID")).trim() : null;
		String accountName = !targetBodyMap.get("accountName").equals("") ? ((String) targetBodyMap
				.get("accountName")).trim() : null;
		String accountNumber = !targetBodyMap.get("accountNumber").equals("") ? ((String) targetBodyMap
				.get("accountNumber")).trim() : null;
		String branchName = !targetBodyMap.get("branchName").equals("") ? ((String) targetBodyMap
				.get("branchName")).trim() : null;
		String province = !targetBodyMap.get("province").equals("") ? ((String) targetBodyMap
				.get("province")).trim() : null;
		String city = !targetBodyMap.get("city").equals("") ? ((String) targetBodyMap
				.get("city")).trim() : null;
		int refundType = Integer.parseInt((String) targetBodyMap
				.get("refundType") == null ? "0" : (String) targetBodyMap
				.get("refundType"));

		// 2.创建交易请求对象
		Tx1131Request tx1131Request = new Tx1131Request();
		tx1131Request.setInstitutionID(institutionID);
		tx1131Request.setSerialNumber(serialNumber);
		tx1131Request.setPaymentNo(paymentNo);
		tx1131Request.setAmount(amount);
		tx1131Request.setRemark(remark);
		tx1131Request.setAccountType(accountType);
		tx1131Request.setPaymentAccountName(paymentAccountName);
		tx1131Request.setPaymentAccountNumber(paymentAccountNumber);

		BankAccount bankAccount = new BankAccount();
		bankAccount.setBankID(bankID);
		bankAccount.setAccountName(accountName);
		bankAccount.setAccountNumber(accountNumber);
		bankAccount.setBranchName(branchName);
		bankAccount.setProvince(province);
		bankAccount.setCity(city);
		tx1131Request.setBankAccount(bankAccount);
		tx1131Request.setRefundType(refundType);

		// 3.执行报文处理
		tx1131Request.process();
		LOG.info("组装明文 [{}]", tx1131Request.getRequestPlainText());

		String msg = tx1131Request.getRequestMessage();
		String signature = tx1131Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1131Response tx1131Response = new Tx1131Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1131Response.getCode(); // 响应代码
		Object respMsg = tx1131Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1131Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1120Request start**************");
	}

	/**
	 * 1132接口处理 商户订单退款
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1132Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1132Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String serialNumber = (String) targetBodyMap.get("serialNumber");
		// 2.创建交易请求对象
		Tx1132Request tx1132Request = new Tx1132Request();
		tx1132Request.setInstitutionID(institutionID);
		tx1132Request.setSerialNumber(serialNumber);
		// 3.执行报文处理
		tx1132Request.process();

		LOG.info("组装明文 [{}]", tx1132Request.getRequestPlainText());

		String msg = tx1132Request.getRequestMessage();
		String signature = tx1132Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1132Response tx1132Response = new Tx1132Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1132Response.getCode(); // 响应代码
		Object respMsg = tx1132Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1132Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
			resultMap.put("institutionID", tx1132Response.getInstitutionID());
			resultMap.put("serialNumber", tx1132Response.getSerialNumber());
			resultMap.put("paymentNo", tx1132Response.getPaymentNo());
			resultMap.put("amount", tx1132Response.getAmount());
			resultMap.put("accountType", tx1132Response.getAccountType());
			resultMap.put("paymentAccountName",
					tx1132Response.getPaymentAccountName());
			resultMap.put("paymentAccountNumber",
					tx1132Response.getPaymentAccountNumber());
			resultMap
					.put("bankID", tx1132Response.getBankAccount().getBankID());
			resultMap.put("accountName", tx1132Response.getBankAccount()
					.getAccountName());
			resultMap.put("accountNumber", tx1132Response.getBankAccount()
					.getAccountNumber());
			resultMap.put("branchName", tx1132Response.getBankAccount()
					.getBranchName());
			resultMap.put("city", tx1132Response.getBankAccount().getCity());
			resultMap.put("status", tx1132Response.getStatus() + "");
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1132Request start**************");
	}

	/**
	 * 1133接口处理 商户订单原路退款
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1133Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("*************do1133Request start**************");

		String institutionID = (String) targetBodyMap.get("institutionID");
		// String txCode = (String) targetBodyMap.get("TxCode");
		String txSN = (String) targetBodyMap.get("txSN");
		String paymentNo = (String) targetBodyMap.get("paymentNo");
		long amount = Long.parseLong((String) targetBodyMap.get("amount"));
		String remark = null;
		/*
		 * !((String) targetBodyMap.get("Remark")).equals("") ? ((String)
		 * targetBodyMap.get("Remark")) .trim() : null;
		 */

		Tx1133Request txRequest = new Tx1133Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		// txRequest.setTxCode(txCode); // 交易码
		txRequest.setSerialNumber(txSN); // 退款交易流水号
		txRequest.setPaymentNo(paymentNo); // 原交易流水号（即2511接口中的paymentNo）
		txRequest.setAmount(amount);// 退款金额 分
		txRequest.setRemark(remark); // 备注
		txRequest.process();// 将信息组装成xml
		LOG.info("组装明文 [{}]", txRequest.getRequestPlainText());
		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}
		Tx1133Response tx1133Response = new Tx1133Response(respMessage[0],
				respMessage[1]);
		LOG.info("返回报文明文 [{}]", tx1133Response.getResponsePlainText());
		Object respCode = tx1133Response.getCode(); // 响应代码
		Object respMsg = tx1133Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1133Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);
		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			// message.getFault().setCode((String) respCode);
			// message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1133Request end**************");
	}

	/**
	 * 单笔单收
	 * 
	 * @param targetBodyMap
	 * @param message
	 * @param resultMap
	 * @throws Exception
	 */
	public static void do2011Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {
		LOG.info("************* 中金单笔代扣 Start **************");

		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		String amount = (String) targetBodyMap.get("amount");
		String bankID = (String) targetBodyMap.get("logoId");
		String accountType = (String) targetBodyMap.get("accountType");
		String validDate = (String) targetBodyMap.get("validDate");
		String cvn2 = (String) targetBodyMap.get("cvn2");
		String accountName = (String) targetBodyMap.get("accountName");
		String accountNumber = (String) targetBodyMap.get("accountNumber");
		String branchName = (String) targetBodyMap.get("branchName");
		String province = (String) targetBodyMap.get("province");
		String city = (String) targetBodyMap.get("city");
		String email = (String) targetBodyMap.get("email");
		String identificationType = (String) targetBodyMap.get("routeCertType");
		String identificationNumber = (String) targetBodyMap.get("certNo");
		String note = (String) targetBodyMap.get("note");
		String contractUserID = (String) targetBodyMap.get("contractUserID");
		String phoneNumber = (String) targetBodyMap.get("phoneNumber");
		String settlementFlag = (String) targetBodyMap.get("settlementFlag");
		String cardMediaType = (String) targetBodyMap.get("cardMediaType");
		// 如果账户类型为11 个人账户时 卡介质类型不上送默认为10 借记卡
		// if (cardMediaType == null && accountType.equals("11")) {
		// cardMediaType = "10";
		// }
		Tx2011Request txRequest = new Tx2011Request();
		txRequest.setInstitutionID(institutionID); // 机构编号
		txRequest.setTxSN(txSN); // 交易流水
		txRequest.setAmount(Long.valueOf(amount)); // 金额，单位:分
		txRequest.setBankID(bankID); // 银行ID(参考银行编码表)
		txRequest.setAccountType(Integer.valueOf(accountType)); // 账户类型: 11=个人账户
		// 12=企业账户
		txRequest.setValidDate(validDate); // 行用卡有效期，格式 YYMM 当代扣信用卡时，该项必填
		txRequest.setCvn2(cvn2); // 信用卡背面的末 3 位数字 当代扣信用卡时，该项必填
		txRequest.setAccountName(accountName); // 账户名称
		txRequest.setAccountNumber(accountNumber); // 账户号码
		txRequest.setBranchName(branchName); // 分支行
		txRequest.setProvince(province); // 分支行省份
		txRequest.setCity(city); // 分支行城市
		/*
		 * 个人证件类型： 0:身份证 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民 来往内地通行证 6：台湾同胞来往内地通行证
		 * 7：临时身份证 8：外国人居留证 9：警官证 X:其他证件 企业证件类型： A-组织机构代码证号 B-营业执照号码 C-登记证书
		 * D-国税登记证号码 E-地税登记证号码 F-开户许可证 G-事业单位编号 I-金融许可证编号 X=其他证件
		 */
		txRequest.setIdentificationType(identificationType); // 开户证件类型
		txRequest.setIdentificationNumber(identificationNumber); // 证件号码
		// 测试版本
		txRequest.setNote(note); // 备注 测试时 10-返回成功状态 20-返回失败状态 30-返回处理中状态
		txRequest.setContractUserID(contractUserID); // 协议用户编号。目前允许为空
		txRequest.setPhoneNumber(phoneNumber); // 手机号
		txRequest.setEmail(email); // 电子邮箱
		txRequest.setSettlementFlag(settlementFlag); // 结算标识， 默认
		// SettlementFlag=0001
		txRequest.setCardMediaType(cardMediaType); // 卡介质类型:10=借记卡 20=贷记卡
		// 30=存折；账户类型为
		// 11=个人账户时，不上送默认为借记卡 账户类型为
		// 12=企业账户时，该字段为空

		txRequest.process();// 将信息组装成xml
		LOG.info("调用中金2011单笔代扣接口组装明文\n[{}]", txRequest.getRequestPlainText());

		String msg = txRequest.getRequestMessage();
		String signature = txRequest.getRequestSignature();
		String[] respMessage = send(msg, signature);
		if (respMessage == null || respMessage.length == 0) {
			LOG.error("调用中金2011单笔代扣接口响应报文为空");
			throw new Exception("调用中金2011单笔代扣接口响应报文为空");
		}
		Tx2011Response txResponse = new Tx2011Response(respMessage[0],
				respMessage[1]);
		LOG.info("调用中金2011单笔代扣接口返回报文明文\n[{}]",
				txResponse.getResponsePlainText());

		Object respCode = txResponse.getCode(); // 响应代码
		Object respMsg = txResponse.getMessage(); // 响应信息

		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("调用中金2011单笔代扣接口响应码为空");
			throw new Exception("调用中金2011单笔代扣接口响应码为空");
		}

		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		resultMap.put("institutionID", txResponse.getInstitutionID());
		resultMap.put("txSN", txResponse.getTxSN());
		// 测试 状态修改为20 成功

		// int stast = 20;
		// resultMap.put("status", String.valueOf(stast));
		resultMap.put("status", txResponse.getStatus() + "");
		resultMap.put("bankTxTime", txResponse.getBankTxTime());
		resultMap.put("responseCode", txResponse.getResponseCode());
		resultMap.put("responseMessage", txResponse.getResponseMessage());
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);

		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.info("client receive message={}", message);
		LOG.info("************* 中金单笔代扣 End **************");
	}

	/**
	 * 2020接口处理单笔代收(代扣)查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do2020Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do2020Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx2020Request tx2020Request = new Tx2020Request();

		tx2020Request.setInstitutionID(institutionID);
		tx2020Request.setTxSN(txSN);
		// 3.执行报文处理
		tx2020Request.process();

		LOG.info("组装明文 [{}]", tx2020Request.getRequestPlainText());

		String msg = tx2020Request.getRequestMessage();
		String signature = tx2020Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx2020Response tx2020Response = new Tx2020Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx2020Response.getCode(); // 响应代码
		Object respMsg = tx2020Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx2020Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			resultMap.put("institutionID", tx2020Response.getInstitutionID());
			resultMap.put("txSN", tx2020Response.getTxSN());
			resultMap.put("accountName", tx2020Response.getAccountName());
			resultMap.put("accountNumber", tx2020Response.getAccountNumber());
			resultMap.put("accountType", tx2020Response.getAccountType());
			resultMap.put("bankID", tx2020Response.getBankID());
			resultMap.put("amount", tx2020Response.getAmount());
			resultMap.put("status", tx2020Response.getStatus() + "");
			resultMap.put("responseCode", tx2020Response.getResponseCode());
			resultMap.put("responseMessage",
					tx2020Response.getResponseMessage());
			resultMap.put("bankTxTime", tx2020Response.getBankTxTime());
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do2020Request start**************");
	}

	/**
	 * 1610接口处理批量 代扣
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1610Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1610Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String batchNo = (String) targetBodyMap.get("batchNo");
		long totalAmount = (long) targetBodyMap.get("totalAmount");
		int totalCount = (int) targetBodyMap.get("totalCount");
		String remark = (String) targetBodyMap.get("remark");
		ArrayList<GatheringItem> paramArrayList = (ArrayList<GatheringItem>) targetBodyMap
				.get("paramArrayList");
		// 2.创建交易请求对象
		Tx1610Request tx1610Request = new Tx1610Request();

		tx1610Request.setInstitutionID(institutionID);
		tx1610Request.setBatchNo(batchNo);
		tx1610Request.setTotalAmount(totalAmount);
		tx1610Request.setTotalCount(totalCount);
		tx1610Request.setRemark(remark);
		tx1610Request.setGatheringItemList(paramArrayList);
		// 3.执行报文处理
		tx1610Request.process();

		LOG.info("组装明文 [{}]", tx1610Request.getRequestPlainText());

		String msg = tx1610Request.getRequestMessage();
		String signature = tx1610Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1610Response tx1610Response = new Tx1610Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1610Response.getCode(); // 响应代码
		Object respMsg = tx1610Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1610Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1610Request end**************");
	}

	/**
	 * 1620接口处理批量 代扣查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1620Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1620Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx1620Request tx1620Request = new Tx1620Request();

		tx1620Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx1620Request.process();

		LOG.info("组装明文 [{}]", tx1620Request.getRequestPlainText());

		String msg = tx1620Request.getRequestMessage();
		String signature = tx1620Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1620Response tx1620Response = new Tx1620Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1620Response.getCode(); // 响应代码
		Object respMsg = tx1620Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1620Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1620Request end**************");
	}

	/**
	 * 1630接口处理批量代收明细查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1630Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1630Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx1630Request tx1630Request = new Tx1630Request();

		tx1630Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx1630Request.process();

		LOG.info("组装明文 [{}]", tx1630Request.getRequestPlainText());

		String msg = tx1630Request.getRequestMessage();
		String signature = tx1630Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1630Response tx1630Response = new Tx1630Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1630Response.getCode(); // 响应代码
		Object respMsg = tx1630Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1630Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1630Request end**************");
	}

	/**
	 * 1650接口处理 批量代扣交易对账
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1650Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1650Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx1650Request tx1650Request = new Tx1650Request();

		tx1650Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx1650Request.process();

		LOG.info("组装明文 [{}]", tx1650Request.getRequestPlainText());

		String msg = tx1650Request.getRequestMessage();
		String signature = tx1650Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1650Response tx1650Response = new Tx1650Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1650Response.getCode(); // 响应代码
		Object respMsg = tx1650Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1650Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1650Request end**************");
	}

	/**
	 * 1510接口处理 批量 代付
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1510Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1510Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx1510Request tx1510Request = new Tx1510Request();

		tx1510Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx1510Request.process();

		LOG.info("组装明文 [{}]", tx1510Request.getRequestPlainText());

		String msg = tx1510Request.getRequestMessage();
		String signature = tx1510Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1510Response tx1510Response = new Tx1510Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1510Response.getCode(); // 响应代码
		Object respMsg = tx1510Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1510Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1510Request end**************");
	}

	/**
	 * 1520接口处理 批量 代付查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1520Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1520Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx1520Request tx1520Request = new Tx1520Request();

		tx1520Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx1520Request.process();

		LOG.info("组装明文 [{}]", tx1520Request.getRequestPlainText());

		String msg = tx1520Request.getRequestMessage();
		String signature = tx1520Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1520Response tx1520Response = new Tx1520Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1520Response.getCode(); // 响应代码
		Object respMsg = tx1520Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1520Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1520Request end**************");
	}

	/**
	 * 1550接口处理 批量 代付交易对账
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do1550Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do1550Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx1550Request tx1550Request = new Tx1550Request();

		tx1550Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx1550Request.process();

		LOG.info("组装明文 [{}]", tx1550Request.getRequestPlainText());

		String msg = tx1550Request.getRequestMessage();
		String signature = tx1550Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx1550Response tx1550Response = new Tx1550Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx1550Response.getCode(); // 响应代码
		Object respMsg = tx1550Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx1550Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do1550Request end**************");
	}

	/**
	 * 4534接口处理机构支付账户批量 代付（实时）
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do4534Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do4534Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx4534Request tx4534Request = new Tx4534Request();

		tx4534Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx4534Request.process();

		LOG.info("组装明文 [{}]", tx4534Request.getRequestPlainText());

		String msg = tx4534Request.getRequestMessage();
		String signature = tx4534Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx4534Response tx4534Response = new Tx4534Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx4534Response.getCode(); // 响应代码
		Object respMsg = tx4534Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx4534Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do4534Request end**************");
	}

	/**
	 * 4538接口处理机构支付账户批量 代付 查询
	 * 
	 * @param targetBodyMap
	 *            请求数据
	 * @param message
	 * @param resultMap
	 *            返回数据
	 * @throws Exception
	 */
	public static void do4538Request(Map<String, Object> targetBodyMap,
			Message message, Map<String, Object> resultMap) throws Exception {

		LOG.info("*************do4538Request start**************");
		// 1.取得参数
		String institutionID = (String) targetBodyMap.get("institutionID");
		String txSN = (String) targetBodyMap.get("txSN");
		// 2.创建交易请求对象
		Tx4538Request tx4538Request = new Tx4538Request();

		tx4538Request.setInstitutionID(institutionID);

		// 3.执行报文处理
		tx4538Request.process();

		LOG.info("组装明文 [{}]", tx4538Request.getRequestPlainText());

		String msg = tx4538Request.getRequestMessage();
		String signature = tx4538Request.getRequestSignature();

		String[] respMessage = send(msg, signature);

		if (respMessage == null || respMessage.length == 0) {
			LOG.info("响应报文不能为空!!");
			throw new Exception("response is null!!");
		}

		Tx4538Response tx4538Response = new Tx4538Response(respMessage[0],
				respMessage[1]);
		Object respCode = tx4538Response.getCode(); // 响应代码
		Object respMsg = tx4538Response.getMessage(); // 响应信息
		if (StringUtils.isBlank((String) respCode)) {
			LOG.info("响应码不能为空,响应报文[{}]", tx4538Response.getResponsePlainText());
			throw new Exception("respCode is null!!");
		}
		message.getFault().setCode((String) respCode);
		message.getFault().setMsg((String) respMsg);

		if (CmparmConstants.GATEWAY_ZJPAY_HANDLE_2000.equals(respCode)) { // 返回成功
			message.getFault().setCode((String) respCode);
			message.getFault().setMsg((String) respMsg);
		}
		resultMap.put("respCode", respCode);
		resultMap.put("respMsg", respMsg);
		targetBodyMap.putAll(resultMap);
		message.getTarget().setBody(targetBodyMap);
		LOG.debug("client receive message={}", message);
		LOG.info("*************do4538Request end**************");
	}

	/**
	 * 请求报文发送 公用方法
	 * 
	 * @param msg
	 * @param signature
	 * @return
	 * @throws Exception
	 */
	private static String[] send(String msg, String signature) {
		if (StringUtils.isBlank(msg)) {
			ExInfo.throwDipperEx(AppCodeDict.EBBC130039, "msg");
		}
		if (StringUtils.isBlank(signature)) {
			ExInfo.throwDipperEx(AppCodeDict.EBBC130039, "signature");
		}
		TxMessenger txMessenger = new TxMessenger();
		try {
			return txMessenger.send(msg, signature);// 0:message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
