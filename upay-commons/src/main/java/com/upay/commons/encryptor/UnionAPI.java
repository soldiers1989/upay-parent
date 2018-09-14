package com.upay.commons.encryptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.pactera.dipper.core.IDipperCached;
import com.union.api.TUnionTransInfo;
import com.union.api.UnionEsscAPI;

/**
 * 科友加密机接口工具类
 * 
 * @author Guo
 * 
 */
public class UnionAPI {
	private static final Logger LOG = LoggerFactory.getLogger(UnionAPI.class);

	private static List<String> ipList;
	private static List<Integer> portList;
	private static String timeout;
	private static String sysID;
	private static String appID;
	private static String connNum;

	private static String keyName;
//
	private UnionEsscAPI api;
//	
	private static String encryptIp;
	private static String encryptPort;
	private static String format;
//	
	
//	public UnionAPI() {
//		ipList.add(keyName);
//	}

	

	

	//威通加密机解密添加
	private static String encryptkeyName,cipherDataLen,algorthmID,dataFillMode,cipherData;
	private static int vKindex;
	/**
	 * 连接加密机(短链)
	 */
	public void getShortConnection(IDipperCached idipperCached,String encryptorLocalBank) {
		Map<String,Object> encryptorMap = idipperCached.get("ENCRYPTOR_MAP");
		LOG.debug("缓存中的加机密信息：："+encryptorMap);
		if(encryptorMap==null||encryptorMap.size()==0){
			LOG.debug("缓存中的加机密信息字符：："+encryptorLocalBank);
			if(StringUtils.isNotBlank(encryptorLocalBank)){
				JSONObject parse=JSONObject.parseObject(encryptorLocalBank);
				encryptorMap=(Map<String, Object>) parse;
				idipperCached.add("ENCRYPTOR_MAP", encryptorMap);
				
				//设置加密机参数
				LOG.debug("重新设置缓存中的加机密信息：："+encryptorMap);
				setParm(encryptorMap);
			}
		}else{
			setParm(encryptorMap);
		}
		
		
		
		if(ipList==null){
			ipList=new ArrayList<String>();
			ipList.add(encryptIp);
		}
		if(portList==null){
			portList=new ArrayList<Integer>();
			portList.add(Integer.valueOf(encryptPort));
		}
		api = new UnionEsscAPI(ipList, portList, Integer.valueOf(timeout), sysID, appID);
		LOG.info("连接加密机初始化成功");
	}

	/**
	 * 连接加密机(长链)
	 */
	public void getLongConnection() {
		if(ipList==null){
			ipList=new ArrayList<String>();
			ipList.add(encryptIp);
		}
		if(portList==null){
			portList=new ArrayList<Integer>();
			portList.add(Integer.valueOf(encryptPort));
		}
		api = new UnionEsscAPI(ipList, portList, Integer.valueOf(timeout), sysID, appID, Integer.valueOf(connNum));
	}

	/**
	 * 调用加密机加密数据
	 * 
	 * @param data
	 *            待加密明文
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String data) throws Exception {
		/*
		 * unionAPIServiceE160方法参数说明: mode 1N 模式 可选，默认为1 1：指定密钥名称 2：指定密钥密文
		 * keyName 128A 密钥名称
		 * mode为1时，当为DES算法时，支持edk、zek或wwk密钥名称；当为SM4时，只支持为zek密钥名称
		 * mode为2时，为zmk密钥名称 keyValue 48A 密钥密文 可选，mode为2是存在，由ZMK保护的zek类型
		 * algorithmID 1N 算法标识 可选，默认为0, 0：ECB, 1：CBC, 当密钥类型为edk时，不存在 dataType
		 * 数据类型, 次域为1时,压缩数据为二进制,可选 data 8192H 明文数据 iv 16H/32H 初始向量
		 * 可选，当algorithmID为1时存在，且当算法为SM4时，iv=32H; 当算法为DES时，iv = 16H format 1N
		 * 密文数据格式 可选，默认为0, 0：不带填充; 1：4字节明文长度+明文+补位‘0’; 2：填充0x00
		 */
		LOG.debug("----------------------------data5:" + data);
		TUnionTransInfo transInfo = api.unionAPIServiceE160(1, keyName, null,
				0, null, data, null, Integer.valueOf(format)); // 密文数据格式
		if (0 == transInfo.getResponseCode()) {
			LOG.debug("----------------------------data6:"
					+ transInfo.getResponseCode());
			LOG.debug("----------------------------data7:"
					+ transInfo.getReturnBody().getData());
			LOG.debug("----------------------------data8:"
					+ transInfo.getReturnBody().getEncryptFillData());
			return transInfo.getReturnBody().getData();
			// return transInfo.getReturnBody().getEncryptFillData();
		} else {
			LOG.error(transInfo.getLog());
			throw new Exception("调用加密机返回错误, 响应码为:["
					+ transInfo.getResponseCode() + "], 响应信息:["
					+ transInfo.getResponseRemark() + "]");
		}
	}

	/**
	 * 调用加密机解密
	 * 
	 * @param encrypt
	 *            待解密密文
	 * @return
	 * @throws Exception
	 */
	public String decipher(String encrypt) throws Exception {
		/*
		 * mode 1N 模式 可选，默认为1, 1：指定密钥名称; 2：指定密钥密文 keyName 128A 密钥名称
		 * mode为1时，支持edk、zek或wwk密钥名称, 当为SM4时，只支持为zek密钥名称 mode为2时，为zmk密钥名称
		 * keyValue 48A 密钥密文 可选，mode为2是存在，由ZMK保护的zek algorithmID 1N 算法标识
		 * 可选，默认为0, 当密钥类型为edk时，不存在，0：ECB; 1：CBC data 8192*2/8192*2+16H 密文数据 iv
		 * 16H 初始向量 可选，当algorithmID为1时存在，且当算法为SM4时，iv=32H; 当算法为DES时，iv = 16H
		 * format 1N 密文数据格式 可选，默认为0, 0：不带填充; 1：4字节明文长度+明文+补位‘0’; 2：填充0x00
		 */
		TUnionTransInfo transInfo = api.unionAPIServiceE161(1, keyName, null,
				0, 0, encrypt, null, Integer.valueOf(format));
		if (0 == transInfo.getResponseCode()) {
			return transInfo.getReturnBody().getData();
		} else {
			LOG.error(transInfo.getLog());
			throw new Exception("调用加密机返回错误, 响应码为:["
					+ transInfo.getResponseCode() + "], 响应信息:["
					+ transInfo.getResponseRemark() + "]");
		}
	}

	public String encryptWT(String pwd) throws Exception {
		LOG.debug("SM2--------" + pwd);
		TUnionTransInfo transInfo = api.unionAPIServiceE173(
				encryptkeyName, cipherDataLen, 01,algorthmID, dataFillMode, pwd);
				
		if (0 == transInfo.getResponseCode()) {
			LOG.debug("----------------------------data6:"
					+ transInfo.getResponseCode());
			LOG.debug("----------------------------data7:"
					+ transInfo.getReturnBody().getData());
			LOG.debug("----------------------------data8:"
					+ transInfo.getReturnBody().getEncryptFillData());
			LOG.debug("----------------------------data9:"
					+ transInfo.getReturnBody().getPlainData());
			return transInfo.getReturnBody().getPlainData();
		} else {
			LOG.error(transInfo.getLog());
			throw new Exception("调用加密机返回错误, 响应码为:["
					+ transInfo.getResponseCode() + "], 响应信息:["
					+ transInfo.getResponseRemark() + "]");
		}
	}

	private void setParm(Map<String,Object> encryptorMap){
		encryptIp=(String)encryptorMap.get("encryptIp");
		
		encryptPort=(String)encryptorMap.get("encryptPort");
		timeout=(String)encryptorMap.get("timeout");
		appID=(String)encryptorMap.get("appID");
		
		sysID=(String)encryptorMap.get("sysID");
		connNum=(String)encryptorMap.get("connNum");
		keyName=(String)encryptorMap.get("keyName");
		encryptkeyName=(String)encryptorMap.get("encryptkeyName");
		cipherDataLen=(String)encryptorMap.get("cipherDataLen");
//		vKindex=(int)encryptorMap.get("vKindex");
		algorthmID=(String)encryptorMap.get("algorthmID");
		dataFillMode=(String)encryptorMap.get("dataFillMode");
		format=(String)encryptorMap.get("format");
	}

	public UnionEsscAPI getApi() {
		return api;
	}

	public void setApi(UnionEsscAPI api) {
		this.api = api;
	}
}
