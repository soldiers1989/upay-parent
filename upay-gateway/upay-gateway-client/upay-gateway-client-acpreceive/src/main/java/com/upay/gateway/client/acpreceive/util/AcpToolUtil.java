/**
 * 
 */
package com.upay.gateway.client.acpreceive.util;

import com.unionpay.acpmer.sdk.*;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.util.ToolUtil;
import com.upay.commons.util.TransUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


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
        if (TRANS_CODE.equals(tranCode)) {
            requestBackUrl = SDKConfig.getConfig().getSingleQueryUrl();
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
	 * 敏感信息加密并做base64(卡号，手机号，cvn2,有效期）<br>
	 * @param data 送 phoneNo,cvn2,有效期<br>
	 * @param encoding<br>
	 * @return 加密的密文<br>
	 */
	public static String encryptData(String data, String encoding) {
		return SecureUtil.encryptData(data, encoding, CertUtil
				.getEncryptCertPublicKey());
	}
	
	/**
	 * 获取敏感信息加密证书的物理序列号<br>
	 * @return
	 */
	public static String getEncryptCertId(){
		return CertUtil.getEncryptCertId();
	}
	
	/**
	 * 功能：持卡人信息域customerInfo构造，勾选对敏感信息加密权限 适用新加密规范，对pin和phoneNo，cvn2，expired加密 <br>
	 * 适用到的交易： <br>
	 * @param customerInfoMap 信息域请求参数 key送域名value送值,必送 <br>
	 *        例如：customerInfoMap.put("certifTp", "01");					//证件类型 <br>
				  customerInfoMap.put("certifId", "341126197709218366");	//证件号码 <br>
				  customerInfoMap.put("customerNm", "互联网");				//姓名 <br>
				  customerInfoMap.put("smsCode", "123456");					//短信验证码 <br>
				  customerInfoMap.put("pin", "111111");						//密码（加密） <br>
				  customerInfoMap.put("phoneNo", "13552535506");			//手机号（加密） <br>
				  customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字（加密） <br>
				  customerInfoMap.put("expired", "1711");  				    //有效期 年在前月在后（加密） <br>
	 * @param accNo  customerInfoMap送了密码那么卡号必送,如果customerInfoMap未送密码PIN，此字段可以不送<br>
	 * @param encoding 上送请求报文域encoding字段的值
	 * @return base64后的持卡人信息域字段 <br>
	 */
	public static String getCustomerInfoWithEncrypt(Map<String,String> customerInfoMap,String accNo,String encoding) {
		if(customerInfoMap.isEmpty())
			return "{}";
		StringBuffer sf = new StringBuffer("{");
		//敏感信息加密域
		StringBuffer encryptedInfoSb = new StringBuffer("");
		
		for(Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = customerInfoMap.get(key);
			if(key.equals("phoneNo") || key.equals("cvn2") || key.equals("expired")){
				encryptedInfoSb.append(key).append(SDKConstants.EQUAL).append(value).append(SDKConstants.AMPERSAND);
			}else{
				if(key.equals("pin")){
					if(null == accNo || "".equals(accNo.trim())){
						log.info("送了密码（PIN），必须在getCustomerInfoWithEncrypt参数中上传卡号");
						throw new RuntimeException("加密PIN没送卡号无法后续处理");
					}else{
						value = encryptPin(accNo,value,encoding);
					}
				}
				sf.append(key).append(SDKConstants.EQUAL).append(value).append(SDKConstants.AMPERSAND);
			}
		}
		
		if(!encryptedInfoSb.toString().equals("")){
			encryptedInfoSb.setLength(encryptedInfoSb.length()-1);//去掉最后一个&符号
			log.info("组装的customerInfo encryptedInfo明文："+ encryptedInfoSb.toString());
			sf.append("encryptedInfo").append(SDKConstants.EQUAL).append(encryptData(encryptedInfoSb.toString(), encoding));
		}else{
			sf.setLength(sf.length()-1);
		}
		
		String customerInfo = sf.append("}").toString();
		log.info("组装的customerInfo明文："+customerInfo);
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)),encoding);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return customerInfo;
	}
	
	
	/**
	 * 密码加密并做base64<br>
	 * @param accNo 卡号<br>
	 * @param pwd 密码<br>
	 * @param encoding<br>
	 * @return 加密的内容<br>
	 */
	public static String encryptPin(String accNo, String pin, String encoding) {
		return SecureUtil.encryptPin(accNo, pin, encoding, CertUtil
				.getEncryptCertPublicKey());
	}
	/**
	 * 功能：持卡人信息域customerInfo构造<br>
	 * 说明：不勾选对敏感信息加密权限使用旧的构造customerInfo域方式，不对敏感信息进行加密（对 phoneNo，cvn2， expired不加密），但如果送pin的话则加密<br>
	 * @param customerInfoMap 信息域请求参数 key送域名value送值,必送<br>
	 *        例如：customerInfoMap.put("certifTp", "01");					//证件类型<br>
				  customerInfoMap.put("certifId", "341126197709218366");	//证件号码<br>
				  customerInfoMap.put("customerNm", "互联网");				//姓名<br>
				  customerInfoMap.put("phoneNo", "13552535506");			//手机号<br>
				  customerInfoMap.put("smsCode", "123456");					//短信验证码<br>
				  customerInfoMap.put("pin", "111111");						//密码（加密）<br>
				  customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字（不加密）<br>
				  customerInfoMap.put("expired", "1711");  				    //有效期 年在前月在后（不加密)<br>
	 * @param accNo  customerInfoMap送了密码那么卡号必送,如果customerInfoMap未送密码pin，此字段可以不送<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>				  
	 * @return base64后的持卡人信息域字段<br>
	 */
	public static String getCustomerInfo(Map<String,String> customerInfoMap,String accNo,String encoding) {
		
		if(customerInfoMap.isEmpty())
			return "{}";
		StringBuffer sf = new StringBuffer("{");
		for(Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = customerInfoMap.get(key);
			if(key.equals("pin")){
				if(null == accNo || "".equals(accNo.trim())){
					log.info("送了密码（PIN），必须在getCustomerInfo参数中上传卡号");
					throw new RuntimeException("加密PIN没送卡号无法后续处理");
				}else{
					value = encryptPin(accNo,value,encoding);
				}
			}
			sf.append(key).append(SDKConstants.EQUAL).append(value);
			if(it.hasNext())
				sf.append(SDKConstants.AMPERSAND);
		}
		String customerInfo = sf.append("}").toString();
		log.info("组装的customerInfo明文："+customerInfo);
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)),encoding);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return customerInfo;
	}
}
