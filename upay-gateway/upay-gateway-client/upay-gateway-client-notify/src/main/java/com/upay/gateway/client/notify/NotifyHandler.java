package com.upay.gateway.client.notify;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;

public class NotifyHandler extends DefaultNotifyClientHandler {
	
    private static final Logger LOG = LoggerFactory.getLogger(NotifyHandler.class);

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		LOG.debug("doErrorHandle");
		super.doErrorHandle(target, fault);
	}

	@Override
	protected void setInitParam(Map<String, Object> init) {
		init.put("channelId", "notifyclidbank");
		init.put("tranCode", "notifydbank");
		LOG.debug("setInitParam");
		if(init.size()>0){
			Set<String> keys = init.keySet();
			for(String key:keys){
				LOG.debug("key:"+key+"\tvalue:"+init.get(key));
			}
		}
		super.setInitParam(init);
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		LOG.debug("doSuccessHandle");
		super.doSuccessHandle(source, target, fault);
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		LOG.debug("doFailureHandle");
		super.doFailureHandle(source, target, fault);
	}
    
    
    

}
