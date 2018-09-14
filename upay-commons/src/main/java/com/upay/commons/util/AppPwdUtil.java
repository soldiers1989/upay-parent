package com.upay.commons.util;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperCached;
import com.upay.commons.constants.DataBaseConstants_USR;

/**
 * 威通app key 工具类
 * 
 * @author jian.liu
 *  
 *  
 */
public class AppPwdUtil {
	private static final Logger log = LoggerFactory.getLogger(AppPwdUtil.class);

	public static String decrypt(IDipperCached cache,
			String inputKey, String aesKey) throws DecoderException {
//		log.debug("APP 私钥："+pwdPrivateKey);
		log.debug("密码控件ID   "+aesKey);
		String randKey = cache.get(aesKey + "_RANDKEY");
		String[] mapArr = cache.get(aesKey + "_MAPARR");
		StringBuffer sb=new StringBuffer("");
		if(null!=mapArr&&mapArr.length>0){
			for(String aa:mapArr){
				sb.append(aa+",");
			}
		}
		log.debug("缓存：    "+aesKey + "_RANDKEY:"+randKey+"              "+aesKey + "_MAPARR:"+sb.toString());
//		// 解密AES层
//		String rsaStr = AES.decrypt(inputKey, randKey);
//		log.debug("RSA密文:"+rsaStr);
//		// String filepath="D:/1.pfx";;
//		// 获得rsa私钥
//		RSAPrivateCrtKey rsaPri = RSAUtil
//				.GetPrivateKey1(certFilePath, "111111");
//		// 解密RSA层
//		String mapStr = RSAUtil.RSADncryptHex(rsaStr, rsaPri);
//		log.debug("映射层密文:"+mapStr);
//		// 解密映射层
//		String str = Util.mappingStr(mapArr, mapStr);
//		log.debug("明文:"+str);
		
		//解密SM4层
		String rsaStr = ocxkeyboard.SMUtil.decryptSM4Two(randKey,inputKey);
		log.debug("SM2密文"+rsaStr);
//		//解密SM2层
//		String mapStr = SMUtil.decryptSM2(pwdPrivateKey,rsaStr,0);
//		log.debug("映射层密文"+mapStr);
//		log.debug("mapArr"+mapArr);
//		String str = "";
//		if(mapStr == null){
//			str = "";
//		}else {
////		//解密映射层
//		    str = Util.mappingStr(mapArr,mapStr);
//		}
//		log.debug("明文:"+str);
		return rsaStr;
	}
	
	public static String decryptToSM2(String aesKey,String pwd,String miType) throws DecoderException{
		String passdata1="";
		if(DataBaseConstants_USR.PASS_IS_MITYPE.equals(miType)){
			passdata1=ocx.SMUtil.decryptSM4(aesKey,pwd);
			log.debug("PC 解密============================");
		}else{
			passdata1=ocxkeyboard.SMUtil.decryptSM4Two(aesKey,pwd);
			log.debug("软键盘 解密============================");
		}
		
		log.debug("SM2密文:"+passdata1);
//        String passdata=ocx.SMUtil.decryptSM2(privateKey,"04"+passdata1);
//        log.debug("SM2密文:"+passdata);
        return passdata1;
	}
//	public static void main(String [] args){
//		String[] a={"asdf","asd","adsf1"};
//		StringBuffer sb=new StringBuffer();
//		for(String aa:a){
//			sb.append(aa+",");
//		}
//		System.out.println(sb.toString());
//		String aa="kb1?asdfasdf";
//		System.out.println(aa.split("")[0]);
//	}
}
