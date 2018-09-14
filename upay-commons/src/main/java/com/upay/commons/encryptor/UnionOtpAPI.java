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
import com.union.api.UnionOtpsAPI;


/**
 * 科友动态口令接口工具类
 * 
 * @author zgr
 * 
 */
public class UnionOtpAPI {
    private static final Logger LOG = LoggerFactory.getLogger(UnionOtpAPI.class);

    private List<String> ipList;
    private List<Integer> portList;
    private static String timeout;
    private static String sysID;
    private static String appID;
    private static String connNum;
//
    private UnionOtpsAPI api;
    private static String unionOtpIP;
    private static String unionOtpPort; 

    /**
     * 连接动态口令平台(短链)
     */
    public void getShortConnection(IDipperCached idipperCached,String getSmsCode) {
    	Map<String,Object> smsCodeMap = idipperCached.get("SMSCODE_MAP");
    	LOG.debug("缓存中的连接动态验证码的服务器信息：："+smsCodeMap);
		if(smsCodeMap==null||smsCodeMap.size()==0){
			
			LOG.debug("缓存中的连接动态验证码的服务器信息字符：："+smsCodeMap);
			if(StringUtils.isNotBlank(getSmsCode)){
				JSONObject parse=JSONObject.parseObject(getSmsCode);
				smsCodeMap=(Map<String, Object>) parse;
				idipperCached.add("SMSCODE_MAP", smsCodeMap);
				
				//设置加密机参数
				LOG.debug("重新设置缓存中的连接动态验证码的服务器信息：："+smsCodeMap);
				setParm(smsCodeMap);
			}
		}else{
			setParm(smsCodeMap);
		}
		
    	if(ipList==null){
			ipList=new ArrayList<String>();
			ipList.add(unionOtpIP);
		}
		if(portList==null){
			portList=new ArrayList<Integer>();
			portList.add(Integer.valueOf(unionOtpPort));
		}
        api = new UnionOtpsAPI(ipList, portList, Integer.valueOf(timeout), sysID, appID);
        LOG.info("连接动态口令平台初始化成功");
    }


    /**
     * 连接动态口令平台(长链)
     */
    public void getLongConnection() {
    	if(ipList==null){
			ipList=new ArrayList<String>();
			ipList.add(unionOtpIP);
		}
		if(portList==null){
			portList=new ArrayList<Integer>();
			portList.add(Integer.valueOf(unionOtpPort));
		}
        api = new UnionOtpsAPI(ipList, portList, Integer.valueOf(timeout), sysID, appID, Integer.valueOf(connNum));
    }


    /**
     * 调用动态码获取方法
     * 
     * @param userID
     *            手机号
     * @return
     * @throws Exception
     */
    public String getSmsVerificationCode(String userID) throws Exception {
        TUnionTransInfo transInfo = api.unionAPIServiceD200(userID);
        // 密文数据格式
        if (0 == transInfo.getResponseCode()) {
            return transInfo.getIsSuccess() == 1 ? transInfo.getReturnBody().getPasswd() : null;
        } else {
            LOG.error(transInfo.getLog());
            throw new Exception("调用动态口令平台返回错误, 响应码为:[" + transInfo.getResponseCode() + "], 响应信息:["
                    + transInfo.getResponseRemark() + "]");
        }
    }


    /**
     * 调用验证动态码方法
     * 
     * @param userID
     *            手机号 passwd 动态码
     * 
     * @return
     * @throws Exception
     */
    public boolean VerifySmsVerificationCode(String userID, String passwd) throws Exception {

        TUnionTransInfo transInfo = api.unionAPIServiceD201(userID, passwd);
        if (0 == transInfo.getResponseCode()) {
            return transInfo.getIsSuccess() == 1 ? true : false;
        } else {
        	LOG.error(transInfo.getLog());
            LOG.error(("调用动态口令平台返回错误, 响应码为:[" + transInfo.getResponseCode() + "], 响应信息:["
                    + transInfo.getResponseRemark() + "]"));
        	return false; 
        }
    }
    
    private void setParm(Map<String,Object> encryptorMap){
    	unionOtpIP=(String)encryptorMap.get("unionOtpIP");
        unionOtpPort=(String)encryptorMap.get("unionOtpPort"); 
    	timeout=(String)encryptorMap.get("timeout");
    	sysID=(String)encryptorMap.get("sysID");
    	appID=(String)encryptorMap.get("appID");
    	connNum=(String)encryptorMap.get("connNum");
    	
    }
}
