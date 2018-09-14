package com.upay.gateway.client.notify;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.core.utils.PacketUtil;
import com.upay.gateway.client.notify.util.DataSignUtil;

public class NotifySignHandler implements IDipperHandler<Message>{
	
    private String keyName = "key";
//    private String merNoName;
//    private String key;
    
//    public String getMerNoName() {
//		return merNoName;
//	}
//
//
//
//	public void setMerNoName(String merNoName) {
//		this.merNoName = merNoName;
//	}





	private Map<String, Map<String, Object>> columnsMap;
	@Override
	public Message handle(Message m) throws Exception {
		Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
		body.put("signType", "MD5");
		String merKey = (String) body.get("key");
		Map<String, Object> paramMap = columnsMap.get("UNIFIEDORDER");
		Set<String> paramSet = paramMap.keySet();
		for(String key: paramSet){
			paramMap.put(key, body.get(key));
		}
		String signData = DataSignUtil.signData(paramMap, keyName, merKey);
        body.put("sign", signData);
        Map<String,Object> targetBodys=(Map<String,Object>)m.getTarget().getBodys();
        String notifyUrl=(String)targetBodys.get("notifyUrl");
        PacketUtil.setValueOfTargetHeaders(m, Constants.Channel._REQ_URL, notifyUrl);
		return m;
		
	}

	
	



	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Map<String, Map<String, Object>> getColumnsMap() {
		return columnsMap;
	}

	public void setColumnsMap(Map<String, Map<String, Object>> columnsMap) {
		this.columnsMap = columnsMap;
	}






	
	
	
}
