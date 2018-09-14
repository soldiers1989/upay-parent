/**
 * 
 */
package com.upay.gateway.client.acp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.util.ToolUtil;
import com.upay.commons.util.TransUtil;


/**
 * 银联公共交易方法
 * 
 * @author zacks
 * 
 */
public class AcpToolUtil {

    private static final Logger log = LoggerFactory.getLogger(AcpToolUtil.class);
    private static String TRAN_CODE = "tranCode";// 交易码

    private static String CUSTOMER_NM = "customerName";// 客户姓名
    private static String CERTIF_TP = "certifTp";// 证件类型
    private static String CERTIF_ID = "certifId"; // 证件号码
    private static String PHONE_NO = "phoneNo"; // 手机

    private static String CUSTOMER_INFO = "customerInfo";// 客户姓名加密信息

    private static String TRANS_CODE = "00";// 交易状态查询

    private static String QRTRANS_CODE = "01";// 主扫
    
    private static String C2BTRANS_CODE = "02";// 被扫

    private static String REFOUNDTRANS_CODE = "03";// 退款
    
    private static String CONSUMEUNDO_CODE = "04";// 交易撤销
    
    private static String PAYWASHED_CODE = "05";// 冲正
    
    private static String CONSUMEUNDOQRCODE_CODE = "06";// 二维码撤销
    
    private static String FILE_TRANS_CODE = "76";// 文件传输入对账交易

    private static String RECEIVE = "11";// 代收交易
    private static String PAY = "12";// 代付交易


    /**
     * 持卡人信息域操作
     * 
     * @param encoding
     *            编码方式
     * @return base64后的持卡人信息域字段
     */
    public static String getCustomer(String tranCode, String encoding, String customerNm, String certifTp,
            String certifId, String phoneNo) {

        StringBuffer sf = new StringBuffer("{");
        if (tranCode != "" && (tranCode.equals(RECEIVE) || tranCode.equals(PAY))) {// 代收:tranCode==11
            sf.append("customerNm=" + customerNm);
        } else {
            sf.append("customerNm=" + customerNm + SDKConstants.AMPERSAND);
            sf.append("certifTp=" + certifTp + SDKConstants.AMPERSAND);
            sf.append("certifId=" + certifId + SDKConstants.AMPERSAND);
            sf.append("phoneNo=" + phoneNo);
        }
        sf.append("}");
        String customerInfo = sf.toString();
        try {
            return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
        return customerInfo;
    }


    /** 银联交易1 */
    public static Map<String, Object> send(Map<String, Object> paramMap, String encoding, String fileRoot,
            Map<String, String> constantMap) throws Exception {

        String tranCode = (String) paramMap.get(TRAN_CODE);
        Map<String, String> reqData =
                TransUtil.parseValueBeforeTrans(tranCode, paramMap, CmparmConstants.TRANS_TYPE_BEFORE);
        String connectionTimeout = constantMap.get(CmparmConstants.ACP_CONNECTION_TIMEOUT);
        String readTimeout = constantMap.get(CmparmConstants.ACP_READ_TIMEOUT);
        String requestBackUrl = constantMap.get(CmparmConstants.REQUEST_BACK_URL);

        if (FILE_TRANS_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getFileTransUrl();
        }
        if (QRTRANS_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        if (TRANS_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        if (C2BTRANS_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        if (CONSUMEUNDO_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        if (PAYWASHED_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        if (CONSUMEUNDOQRCODE_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        if (REFOUNDTRANS_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getQrcB2cMerBackTransUrl();
        }
        constantMap.remove(CmparmConstants.ACP_CONNECTION_TIMEOUT);
        constantMap.remove(CmparmConstants.ACP_READ_TIMEOUT);
        constantMap.remove(CmparmConstants.REQUEST_BACK_URL);
        String customerNm = (String) paramMap.get(CUSTOMER_NM);
        String certifTp = (String) paramMap.get(CERTIF_TP);
        String certifId = (String) paramMap.get(CERTIF_ID);
        String phoneNo = (String) paramMap.get(PHONE_NO);
        if (StringUtils.isNotBlank(customerNm)) {

            reqData.put(CUSTOMER_INFO,
                getCustomer(tranCode, encoding, customerNm, certifTp, certifId, phoneNo));
            reqData.remove(CUSTOMER_NM);
            reqData.remove(CERTIF_TP);
            reqData.remove(CERTIF_ID);
            reqData.remove(PHONE_NO);

        }
        reqData.putAll(constantMap);
        reqData.put("encoding", encoding);
        Map<String, String> requestData = ToolUtil.removeBlank(reqData);
        log.info("reqData签名前：" + requestData.toString());
        SDKUtil.sign(requestData, encoding);
        log.info(requestBackUrl);
        log.info("reqData签名后：" + requestData.toString());
        Map<String, String> resData = AcpService.post(requestData, requestBackUrl, encoding);
        // 打印返回报文
        log.info("打印返回报文：[{}]", resData);
        if (null != resData) {
            //resData = new HashMap<String, String>();
            // 将返回结果转换为map
            // resData = SDKUtil.convertResultStringToMap(resultString);

            if (AcpService.validate(resData, encoding)) {
                log.info("验证签名成功");
            } else {
                log.info("验证签名失败");
            }
            /** 文件传输类交易解析文件 */
            if (StringUtils.isNotBlank(fileRoot)) {
                if (FILE_TRANS_CODE.equals(tranCode)) {
                    deCodeFileContent(resData, encoding, fileRoot);
                }

            }
            return TransUtil.parseValueAfterTrans(tranCode, resData, CmparmConstants.TRANS_TYPE_AFTER);

        }
        return null;
    }


    /**
     * 解析返回文件
     */
    public static void deCodeFileContent(Map<String, String> resData, String encoding, String fileRoot) {
        // 解析返回文件
        String fileContent = resData.get(SDKConstants.param_fileContent);
        if (null != fileContent && !"".equals(fileContent)) {
            try {
                byte[] fileArray =
                        SecureUtil.inflater(SecureUtil.base64Decode(fileContent.getBytes(encoding)));
                String filePath = null;
                if (SDKUtil.isEmpty(resData.get("fileName"))) {
                    filePath =
                            fileRoot + File.separator + resData.get("merId") + "_" + resData.get("batchNo")
                                    + "_" + resData.get("txnTime") + ".txt";
                } else {
                    filePath = fileRoot + File.separator + resData.get("fileName");
                }
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                out.write(fileArray, 0, fileArray.length);
                out.flush();
                out.close();

            } catch (UnsupportedEncodingException e) {
                log.error("", e);
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }
    public static String getOrderId() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
    
	/**
     * 组装付款方信息
     * @param encoding 编码方式
     * @return 用{}连接并base64后的付款方信息
     */
	public static String getPayerInfo(Map<String, String> payarInfoMap, String encoding) {
		return formInfoBase64(payarInfoMap,encoding);
    }
 	/**
	 * 组装收款方信息
	 * @param encoding 编码方式
	 * @return 用{}连接并base64后的收款方信息
	 */
	public static String getPayeeInfo(Map<String, String> payeeInfoMap,String encoding) {
		return formInfoBase64(payeeInfoMap,encoding);
    }
	
	/**
	 * 用{}连接并base64
	 * @param map
	 * @param encoding
	 * @return
	 */
	public static String formInfoBase64(Map<String, String> map,String encoding){
		StringBuffer sf = new StringBuffer();
        String info = sf.append(SDKConstants.LEFT_BRACE).append(SDKUtil.coverMap2String(map)).append(SDKConstants.RIGHT_BRACE).toString();
        try {
        	info = new String(AcpService.base64Encode(info, encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return info;
	}
	
	/**
	 * 对字符串做base64<br>
	 * @param rawStr<br>
	 * @param encoding<br>
	 * @return<br>
	 * @throws IOException
	 */
	public static String base64Encode(String rawStr,String encoding) throws IOException{
		byte [] rawByte = rawStr.getBytes(encoding);
		return new String(SecureUtil.base64Encode(rawByte),encoding);
	}
	/**
     * 组装附加处理条件
     * @param encoding 编码方式
     * @return 用{}连接并base64后的附加处理条件
     */
	public static String getAddnCond(Map<String, String> addnCondMap,String encoding) {
		return formInfoBase64(addnCondMap,encoding);
    }
    
	
	/**
	 * 对base64的字符串解base64<br>
	 * @param base64Str<br>
	 * @param encoding<br>
	 * @return<br>
	 * @throws IOException
	 */
	public static String base64Decode(String base64Str,String encoding) throws IOException{
		byte [] rawByte = base64Str.getBytes(encoding);
		return new String(SecureUtil.base64Decode(rawByte),encoding);	
	}
	
	/**
     * 组装付款方信息(接入机构配置了敏感信息加密)
     * @param encoding 编码方式
     * @return 用{}连接并base64后的付款方信息
     */
	public static String getPayerInfoWithEncrpyt(Map<String, String> payarInfoMap, String encoding) {
		return formInfoBase64WithEncrpyt(payarInfoMap,encoding);
    }
	
	/**
	 * 用{}连接并base64(接入机构配置了敏感信息加密)
	 * @param map
	 * @param encoding
	 * @return
	 */
	public static String formInfoBase64WithEncrpyt(Map<String, String> map,String encoding){
		StringBuffer sf = new StringBuffer();
        String info = sf.append(SDKConstants.LEFT_BRACE).append(SDKUtil.coverMap2String(map)).append(SDKConstants.RIGHT_BRACE).toString();
        info = SecureUtil.encryptData(info, encoding, CertUtil.getEncryptCertPublicKey());
        return info;
	}
    
}
